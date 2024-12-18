package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

public class GraphicConveyor {

    private Matrix4f scaleMatrix;
    private Matrix4f rotateMatrix;
    private Matrix4f translationMatrix;

    // Хранение углов для вращения
    private float angleX = 0;
    private float angleY = 0;
    private float angleZ = 0;

    // Конструктор по умолчанию (единичные матрицы)
    public GraphicConveyor() {
        this.scaleMatrix = Matrix4f.identity();
        this.rotateMatrix = Matrix4f.identity();
        this.translationMatrix = Matrix4f.identity();
    }

    // Применение масштабирования
    public void scale(float scaleX, float scaleY, float scaleZ) {
        Matrix4f scalingMatrix = Matrix4f.scaling(scaleX, scaleY, scaleZ);
        this.scaleMatrix = scalingMatrix;
    }

    // Применение вращения
    public void rotate(float deltaX, float deltaY, float deltaZ) {
        // Обновляем углы
        this.angleX += deltaX;
        this.angleY += deltaY;
        this.angleZ += deltaZ;
        Matrix4f rotationX = Matrix4f.rotationX((float) Math.toRadians(angleX));
        Matrix4f rotationY = Matrix4f.rotationY((float) Math.toRadians(angleY));
        Matrix4f rotationZ = Matrix4f.rotationZ((float) Math.toRadians(angleZ));
        this.rotateMatrix = this.rotateMatrix.multiply(rotationZ.multiply(rotationY).multiply(rotationX));
    }

    // Применение переноса
    public void translate(float dx, float dy, float dz) {
        Matrix4f translation = Matrix4f.translation(dx, dy, dz);
        this.translationMatrix = translation;
    }

    // Преобразование локальных координат в мировые
    public Vector3f toWorldCoordinates(Vector3f localCoordinates) {
        // Возвращаем преобразованные координаты через объединение матриц
        Matrix4f finalTransformation = this.scaleMatrix
                .multiply(this.rotateMatrix)
                .multiply(this.translationMatrix);

        return finalTransformation.multiply(localCoordinates);
    }

    // Преобразование мировых координат в экранные
    public Vector2f toScreenCoordinates(Vector3f worldCoordinates, int screenWidth, int screenHeight) {
        return new Vector2f(
                worldCoordinates.x * screenWidth / 2.0f + screenWidth / 2.0f,
                -worldCoordinates.y * screenHeight / 2.0f + screenHeight / 2.0f
        );
    }

    // Сброс матрицы преобразований к единичной
    public void resetTransformations() {
        this.scaleMatrix = Matrix4f.identity();
        this.rotateMatrix = Matrix4f.identity();
        this.translationMatrix = Matrix4f.identity();
    }

    // Получение текущей матрицы преобразований (объединение всех)
    public Matrix4f getTransformationMatrix() {
        return this.rotateMatrix.multiply(this.scaleMatrix).multiply(this.translationMatrix);
    }

    // Метод "смотри на" (матрица вида) для использования Matrix4f
    public static Matrix4f lookAt(Vector3f position, Vector3f target, Vector3f up) {
        Vector3f forward = target.subtract(position).normalize(); // Вектор направления камеры
        Vector3f right = up.cross(forward).normalize();           // Вектор вправо
        Vector3f newUp = forward.cross(right).normalize();        // Пересчитанный вектор вверх

        // Матрица ориентации камеры
        Matrix4f orientationMatrix = new Matrix4f(new float[][]{
                {right.x, newUp.x, -forward.x, 0},
                {right.y, newUp.y, -forward.y, 0},
                {right.z, newUp.z, -forward.z, 0},
                {0, 0, 0, 1}
        });

        // Матрица переноса камеры
        Matrix4f translationMatrix = new Matrix4f(new float[][]{
                {1, 0, 0, -position.x},
                {0, 1, 0, -position.y},
                {0, 0, 1, -position.z},
                {0, 0, 0, 1}
        });

        return orientationMatrix.multiply(translationMatrix);
    }

    // Метод для создания перспективной матрицы для использования Matrix4f
    public static Matrix4f perspective(float fov, float aspectRatio, float nearPlane, float farPlane) {
        float tanHalfFov = (float) Math.tan(fov / 2.0); // Тангенс половинного угла обзора
        float range = nearPlane - farPlane;

        // Создаем матрицу перспективы с использованием Matrix4f
        return new Matrix4f(new float[][]{
                {1.0f / (aspectRatio * tanHalfFov), 0, 0, 0},
                {0, 1.0f / tanHalfFov, 0, 0},
                {0, 0, -(farPlane + nearPlane) / range, 2 * farPlane * nearPlane / range},
                {0, 0, -1, 0}
        });
    }
}


