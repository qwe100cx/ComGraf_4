package com.cgvsu;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.objreader.ObjWriter;
import com.cgvsu.render_engine.RenderEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.GraphicConveyor;

import javax.swing.*;

public class GuiController {

    public final float SCALE_FACTOR = 1.1F;
    public final float SCALE_DECREASE = 0.9F;

    final private float ROTATE_ANGLE = 10.0F;

    public final float TRANSLATION = 0.8F;

    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float scaleZ = 1.0f;

    public float rotateX = 0.0f;
    public float rotateY = 0.0f;
    public float rotateZ = 0.0f;

    public float translateX = 0.0f;
    private float translateY = 0.0f;
    private float translateZ = 0.0f;

    public boolean isMousePressed = false;
    public double lastMouseX;
    public double lastMouseY;

    private float initialScaleX = 1.0f;
    private float initialScaleY = 1.0f;
    private float initialScaleZ = 1.0f;

    private float initialRotateX = 0.0f;
    private float initialRotateY = 0.0f;
    private float initialRotateZ = 0.0f;

    private float initialTranslateX = 0.0f;
    private float initialTranslateY = 0.0f;
    private float initialTranslateZ = 0.0f;

    @FXML
    public void onResetTransformationsClick(ActionEvent actionEvent) {
        scaleX = initialScaleX;
        scaleY = initialScaleY;
        scaleZ = initialScaleZ;

        rotateX = initialRotateX;
        rotateY = initialRotateY;
        rotateZ = initialRotateZ;

        translateX = initialTranslateX;
        translateY = initialTranslateY;
        translateZ = initialTranslateZ;

        graphicConveyor.scale(scaleX, scaleY, scaleZ);
        graphicConveyor.rotate(rotateX, rotateY, rotateZ);
        graphicConveyor.translate(translateX, translateY, translateZ);
    }


    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private GraphicConveyor graphicConveyor = new GraphicConveyor();

    public Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    public void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height, graphicConveyor);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMouseReleased(this::handleMouseReleased);

    }

    @FXML
    public void onResetCameraClick(ActionEvent actionEvent) {

        camera.setPosition(new Vector3f(0, 0, 100));

        graphicConveyor.resetTransformations();
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            isMousePressed = true;
            lastMouseX = event.getX();
            lastMouseY = event.getY();
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (isMousePressed) {
            double deltaX = event.getX() - lastMouseX;
            double deltaY = event.getY() - lastMouseY;

            float sensitivity = 0.003f;

            translateX += deltaX * sensitivity;
            translateY += deltaY * sensitivity;

            graphicConveyor.translate(translateX, translateY, translateZ);

            lastMouseX = event.getX();
            lastMouseY = event.getY();
        }
    }


    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            isMousePressed = false;
        }
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            graphicConveyor.resetTransformations();
            camera = new Camera(
                    new Vector3f(0, 0, 100),
                    new Vector3f(0, 0, 0),
                    (float) Math.toRadians(60), 1,  0.01F, 1000);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    public void saveModel(Model model) {
        if (model == null || model.vertices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Model is empty or null, cannot save.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Model");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("OBJ Files", "obj"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getName().endsWith(".obj")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".obj");
                }
                ObjWriter.write(model, fileToSave.getAbsolutePath());
            }
        }
    }

    public Model applyTransformationsToModel(Model originalModel, GraphicConveyor graphicConveyor) {
        Model transformedModel = new Model();

        Matrix4f transformationMatrix = graphicConveyor.getTransformationMatrix();

        for (int i = 0; i < originalModel.vertices.size(); i++) {
            Vector3f vertex = originalModel.vertices.get(i);

            Vector3f transformedVertex = transformationMatrix.multiply(vertex);

            Vector3f worldCoordinates = graphicConveyor.toWorldCoordinates(transformedVertex);

            transformedModel.vertices.add(worldCoordinates);
        }

        transformedModel.textureVertices = originalModel.textureVertices;
        transformedModel.normals = originalModel.normals;
        transformedModel.polygons = originalModel.polygons;

        return transformedModel;
    }


    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            isMousePressed = true;
            lastMouseX = event.getX();
            lastMouseY = event.getY();
        }
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        if (isMousePressed) {
            double deltaX = event.getX() - lastMouseX;
            double deltaY = event.getY() - lastMouseY;

            translateX += deltaX * TRANSLATION;
            translateY -= deltaY * TRANSLATION;

            lastMouseX = event.getX();
            lastMouseY = event.getY();

            graphicConveyor.translate(translateX, translateY, translateZ);
        }
    }

    public void onSaveWithTransformationClick(ActionEvent actionEvent) {
        Model trans = applyTransformationsToModel(mesh, graphicConveyor);
        saveModel(trans);
    }

    public void onSaveWithoutTransformationClick(ActionEvent actionEvent) {
        saveModel(mesh);
    }

    public void onExitMenuItemClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onScaleUpClick(ActionEvent actionEvent) {
        scaleX *= SCALE_FACTOR;
        scaleY *= SCALE_FACTOR;
        scaleZ *= SCALE_FACTOR;
        graphicConveyor.scale(scaleX, scaleY, scaleZ);
    }

    public void onScaleDownClick(ActionEvent actionEvent) {
        scaleX *= SCALE_DECREASE;
        scaleY *= SCALE_DECREASE;
        scaleZ *= SCALE_DECREASE;
        graphicConveyor.scale(scaleX, scaleY, scaleZ);
    }

    public void onRotateXPosClick(ActionEvent actionEvent) {
        rotateX += ROTATE_ANGLE;
        graphicConveyor.rotate(rotateX, 0.0f, 0.0f);
    }

    public void onRotateXNegClick(ActionEvent actionEvent) {
        rotateX -= ROTATE_ANGLE;
        graphicConveyor.rotate(rotateX, 0.0f, 0.0f);
    }

    public void onRotateYPosClick(ActionEvent actionEvent) {
        rotateY += ROTATE_ANGLE;
        graphicConveyor.rotate(0.0f, rotateY, 0.0f);
    }

    public void onRotateYNegClick(ActionEvent actionEvent) {
        rotateY -= ROTATE_ANGLE;
        graphicConveyor.rotate(0.0f, rotateY, 0.0f);
    }

    public void onRotateZPosClick(ActionEvent actionEvent) {
        rotateZ += ROTATE_ANGLE;
        graphicConveyor.rotate(0.0f, 0.0f, rotateZ);
    }

    public void onRotateZNegClick(ActionEvent actionEvent) {
        rotateZ -= ROTATE_ANGLE;
        graphicConveyor.rotate(0.0f, 0.0f, rotateZ);
    }

    public void onTranslateXPosClick(ActionEvent actionEvent) {
        translateX += TRANSLATION;
        graphicConveyor.translate(translateX, 0.0f, 0.0f);
    }

    public void onTranslateXNegClick(ActionEvent actionEvent) {
        translateX -= TRANSLATION;
        graphicConveyor.translate(translateX, 0.0f, 0.0f);
    }

    public void onTranslateYPosClick(ActionEvent actionEvent) {
        translateY += TRANSLATION;
        graphicConveyor.translate(0.0f, translateY, 0.0f);
    }

    public void onTranslateYNegClick(ActionEvent actionEvent) {
        translateY -= TRANSLATION;
        graphicConveyor.translate(0.0f, translateY, 0.0f);
    }

    public void onTranslateZPosClick(ActionEvent actionEvent) {
        translateZ += TRANSLATION;
        graphicConveyor.translate(0.0f, 0.0f, translateZ);
    }

    public void onTranslateZNegClick(ActionEvent actionEvent) {
        translateZ -= TRANSLATION;
        graphicConveyor.translate(0.0f, 0.0f, translateZ);
    }
}