package com.cgvsu.render_engine;

import com.cgvsu.math.Vector.Vector2f;
import com.cgvsu.math.Vector.Vector3f;
import com.cgvsu.math.Matrix.Matrix4f;

public class GraphicConveyor {



    public static Matrix4f rotateScaleTranslate() {
        Matrix4f result = new Matrix4f();
        // Инициализация единичной матрицы
        result.m00 = 1; result.m01 = 0; result.m02 = 0; result.m03 = 0;
        result.m10 = 0; result.m11 = 1; result.m12 = 0; result.m13 = 0;
        result.m20 = 0; result.m21 = 0; result.m22 = 1; result.m23 = 0;
        result.m30 = 0; result.m31 = 0; result.m32 = 0; result.m33 = 1;
        return result;
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0, 1, 0)); // Указание вектора вверх по умолчанию
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = eye.subtract(target).normalize(); // Вектор направления
        Vector3f resultX = up.cross(resultZ).normalize(); // Вектор вправо
        Vector3f resultY = resultZ.cross(resultX).normalize(); // Вектор вверх

        Matrix4f result = new Matrix4f();
        result.m00 = resultX.x; // Используем x, y, z вместо getX(), getY(), getZ()
        result.m01 = resultY.x;
        result.m02 = resultZ.x;
        result.m03 = 0;
        result.m10 = resultX.y;
        result.m11 = resultY.y;
        result.m12 = resultZ.y;
        result.m13 = 0;
        result.m20 = resultX.z;
        result.m21 = resultY.z;
        result.m22 = resultZ.z;
        result.m23 = 0;
        result.m30 = -resultX.dot(eye);
        result.m31 = -resultY.dot(eye);
        result.m32 = -resultZ.dot(eye);
        result.m33 = 1;

        return result;
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        float tangentMinusOnDegree = (float) (1.0F / Math.tan(Math.toRadians(fov) * 0.5F));
        result.m00 = tangentMinusOnDegree / aspectRatio;
        result.m11 = tangentMinusOnDegree;
        result.m22 = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.m23 = 1.0F;
        result.m32 = (2 * nearPlane * farPlane) / (nearPlane - farPlane);
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        final float x = (vertex.x * matrix.m00) + (vertex.y * matrix.m10) + (vertex.z * matrix.m20) + matrix.m30;
        final float y = (vertex.x * matrix.m01) + (vertex.y * matrix.m11) + (vertex.z * matrix.m21) + matrix.m31;
        final float z = (vertex.x * matrix.m02) + (vertex.y * matrix.m12) + (vertex.z * matrix.m22) + matrix.m32;
        final float w = (vertex.x * matrix.m03) + (vertex.y * matrix.m13) + (vertex.z * matrix.m23) + matrix.m33;

        // Нормализуем вектор, если w не равно 0
        if (w != 0) {
            return new Vector3f(x / w, y / w, z / w);
        }
        return new Vector3f(x, y, z); // В случае, если w равно 0, возвращаем вектор без нормализации
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f((vertex.x + 1) * width / 2.0F, (-vertex.y + 1) * height / 2.0F);
    }


}
