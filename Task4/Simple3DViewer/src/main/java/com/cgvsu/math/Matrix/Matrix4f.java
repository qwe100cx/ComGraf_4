package com.cgvsu.math.Matrix;

import com.cgvsu.math.Vector.Vector3f;

public class Matrix4f {
    // Обозначения для элементов матрицы
    public float m00, m01, m02, m03; // Первая строка
    public float m10, m11, m12, m13; // Вторая строка
    public float m20, m21, m22, m23; // Третья строка
    public float m30, m31, m32, m33; // Четвертая строка

    public Matrix4f() {
        // Инициализация единичной матрицы
        m00 = 1; m01 = 0; m02 = 0; m03 = 0;
        m10 = 0; m11 = 1; m12 = 0; m13 = 0;
        m20 = 0; m21 = 0; m22 = 1; m23 = 0;
        m30 = 0; m31 = 0; m32 = 0; m33 = 1;
    }

    // Метод для умножения матрицы на вектор
    public Vector3f multiply(Vector3f v) {
        float x = m00 * v.x + m01 * v.y + m02 * v.z + m03;
        float y = m10 * v.x + m11 * v.y + m12 * v.z + m13;
        float z = m20 * v.x + m21 * v.y + m22 * v.z + m23;
        return new Vector3f(x, y, z); // Порядок (x, y, z)
    }

    // Метод для создания матрицы масштабирования
    public static Matrix4f scale(float sx, float sy, float sz) {
        Matrix4f result = new Matrix4f();
        result.m00 = sx;
        result.m11 = sy;
        result.m22 = sz;
        return result;
    }

    // Метод для создания матрицы вращения вокруг оси Z
    public static Matrix4f rotate(float angle) {
        Matrix4f result = new Matrix4f();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        result.m00 = cos;
        result.m01 = -sin;
        result.m10 = sin;
        result.m11 = cos;
        return result;
    }

    // Метод для создания матрицы переноса
    public static Matrix4f translate(float tx, float ty, float tz) {
        Matrix4f result = new Matrix4f();
        result.m03 = tx;
        result.m13 = ty;
        result.m23 = tz;
        return result;
    }
}
