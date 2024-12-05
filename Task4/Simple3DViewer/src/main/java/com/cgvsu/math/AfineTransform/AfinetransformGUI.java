package com.cgvsu.math.AfineTransform;

import com.cgvsu.math.Vector.Vector3f;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AfinetransformGUI extends JFrame {
    private AffineTransform logic = new AffineTransform();
    private JTextField scaleXField, scaleYField, scaleZField;
    private JTextField rotateField;
    private JTextField translateXField, translateYField, translateZField;
    private JTextArea outputArea;

    public AfinetransformGUI() {
        setTitle("Affine Transformations");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));

        // Поля для ввода параметров
        scaleXField = new JTextField();
        scaleYField = new JTextField();
        scaleZField = new JTextField();
        rotateField = new JTextField();
        translateXField = new JTextField();
        translateYField = new JTextField();
        translateZField = new JTextField();

        // Кнопка для загрузки файла
        JButton loadButton = new JButton("Load OBJ");
        loadButton.addActionListener(e -> {
            try {
                loadObjFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                outputArea.append("Error loading file: " + ex.getMessage() + "\n");
            }
        });

        // Кнопка для применения преобразований
        JButton applyButton = new JButton("Apply Transformations");
        applyButton.addActionListener(e -> applyTransformations());

        // Поля для ввода и кнопки
        add(new JLabel("Scale X:"));
        add(scaleXField);
        add(new JLabel("Scale Y:"));
        add(scaleYField);
        add(new JLabel("Scale Z:"));
        add(scaleZField);
        add(new JLabel("Rotate (degrees):"));
        add(rotateField);
        add(new JLabel("Translate X:"));
        add(translateXField);
        add(new JLabel("Translate Y:"));
        add(translateYField);
        add(new JLabel("Translate Z:"));
        add(translateZField);
        add(loadButton);
        add(applyButton);

        // Область для вывода результатов
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    private void loadObjFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select OBJ File");

        // Открыть диалог выбора файла
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String filePath = fileToLoad.getAbsolutePath();
            try {
                logic.loadObjFile(filePath);
                outputArea.append("Loaded " + logic.getPoints3D().size() + " points from OBJ file.\n");
            } catch (IOException e) {
                e.printStackTrace();
                outputArea.append("Error loading file: " + e.getMessage() + "\n");
            }
        } else {
            outputArea.append("File selection canceled.\n");
        }
    }

    private void applyTransformations() {
        outputArea.append("\nApplying transformations...\n");

        try {
            // Получение значений из текстовых полей
            float scaleX = Float.parseFloat(scaleXField.getText());
            float scaleY = Float.parseFloat(scaleYField.getText());
            float scaleZ = Float.parseFloat(scaleZField.getText());
            float rotateAngle = Float.parseFloat(rotateField.getText());float translateX = Float.parseFloat(translateXField.getText());
            float translateY = Float.parseFloat(translateYField.getText());
            float translateZ = Float.parseFloat(translateZField.getText());

            // Применение преобразований к каждой точке
            for (Vector3f point3D : logic.getPoints3D()) {
                Vector3f scaledPoint3D = logic.scale(point3D, scaleX, scaleY, scaleZ);
                Vector3f rotatedPoint3D = logic.rotate(scaledPoint3D, rotateAngle);
                Vector3f translatedPoint3D = logic.translate(rotatedPoint3D, translateX, translateY, translateZ);

                // Вывод результатов
                outputArea.append("Original: " + point3D + " -> Transformed: " + translatedPoint3D + "\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Error: Please enter valid numeric values for transformations.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AfinetransformGUI::new);
    }
}