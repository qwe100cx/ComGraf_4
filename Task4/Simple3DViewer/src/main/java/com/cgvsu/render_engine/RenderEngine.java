package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.math.Point2f;
import com.cgvsu.math.Vector.Vector3f;
import com.cgvsu.math.Matrix.Matrix4f;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;

import static com.cgvsu.render_engine.GraphicConveyor.rotateScaleTranslate;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height)
    {
        // Создаем матрицы трансформации
        Matrix4f modelMatrix = rotateScaleTranslate(); // Предполагается, что этот метод возвращает Matrix4f
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        // Объединяем матрицы с использованием нового метода
        Matrix4f modelViewProjectionMatrix = combineMatrices(modelMatrix, viewMatrix, projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                // Преобразуем вектор в однородные координаты и умножаем на матрицу
                Vector3f transformedVertex = modelViewProjectionMatrix.multiply(vertex);

                Point2f resultPoint = vertexToPoint(transformedVertex, width, height);
                resultPoints.add(resultPoint);
            }

            // Рисуем полигоны
            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);
            }

            // Замыкаем полигон
            if (nVerticesInPolygon > 0) {
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);
            }
        }
    }

    // Новый метод для объединения матриц
    private static Matrix4f combineMatrices(Matrix4f model, Matrix4f view, Matrix4f projection) {
        // Сначала умножаем модельную матрицу на матрицу вида, затем результат на проекционную матрицу
        Matrix4f viewModelMatrix = model;
        return viewModelMatrix;
    }

    // Метод для преобразования вектора в точку
    private static Point2f vertexToPoint(Vector3f vertex, int width, int height) {
        // Преобразование вектора в 2D координаты на основе ширины и высоты
        return new Point2f((vertex.x + 1) * width / 2, (vertex.y + 1) * height / 2);
    }
}
