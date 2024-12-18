package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            final GraphicConveyor graphicConveyor
    ) {
        // Получаем матрицу преобразования из GraphicConveyor
        Matrix4f modelMatrix = graphicConveyor.getTransformationMatrix();

        // Получаем матрицу вида из камеры
        Matrix4f viewMatrix = camera.getViewMatrix();

        // Получаем перспективную матрицу из камеры
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        // Объединяем матрицы: видовую и проекционную
        Matrix4f viewProjectionMatrix = Matrix4f.identity();
        viewProjectionMatrix = viewProjectionMatrix.multiply(viewMatrix); // Видовая матрица
        viewProjectionMatrix = viewProjectionMatrix.multiply(projectionMatrix); // Проекционная матрица

        // Рендерим все полигоны
        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            renderPolygon(graphicsContext, mesh, polygonInd, viewProjectionMatrix, modelMatrix, width, height, graphicConveyor);
        }
    }

    private static void renderPolygon(
            final GraphicsContext graphicsContext,
            final Model mesh,
            final int polygonInd,
            final Matrix4f viewProjectionMatrix,
            final Matrix4f modelMatrix,
            final int width,
            final int height,
            final GraphicConveyor graphicConveyor
    ) {
        final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();
        ArrayList<Vector2f> resultPoints = new ArrayList<>();

        // Преобразуем все вершины полигона и добавляем их в resultPoints
        for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
            Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

            Vector3f v = modelMatrix.multiply(vertex);

            // Преобразуем локальные координаты в мировые с использованием GraphicConveyor
            Vector3f worldCoordinates = graphicConveyor.toWorldCoordinates(v);

            // Применяем видовую и проекционную матрицы к мировым координатам
            Vector3f transformedVertex = viewProjectionMatrix.multiply(worldCoordinates);

            // Преобразуем мировые координаты в экранные
            Vector2f resultPoint = graphicConveyor.toScreenCoordinates(transformedVertex, width, height);
            resultPoints.add(resultPoint);
        }

        // Рисуем линии для каждого полигона
        drawPolygonLines(graphicsContext, resultPoints);
    }

    private static void drawPolygonLines(GraphicsContext graphicsContext, ArrayList<Vector2f> resultPoints) {
        int nVerticesInPolygon = resultPoints.size();

        // Рисуем линии для каждого полигона
        for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
            graphicsContext.strokeLine(
                    resultPoints.get(vertexInPolygonInd - 1).x,
                    resultPoints.get(vertexInPolygonInd - 1).y,
                    resultPoints.get(vertexInPolygonInd).x,
                    resultPoints.get(vertexInPolygonInd).y);
        }

        if (nVerticesInPolygon > 0) {
            graphicsContext.strokeLine(
                    resultPoints.get(nVerticesInPolygon - 1).x,
                    resultPoints.get(nVerticesInPolygon - 1).y,
                    resultPoints.get(0).x,
                    resultPoints.get(0).y);
        }
    }
}

