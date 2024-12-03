package com.cgvsu.math.Matrix;

import com.cgvsu.math.Vector.Vector3f;

public class Matrix4f {
    public float[][] elements;

    public Matrix4f() {
        elements = new float[4][4];
        // Инициализация единичной матрицы
        for (int i = 0; i < 4; i++) {
            elements[i][i] = 1;
        }
    }

    // Метод для умножения матрицы на вектор
    public Vector3f multiply(Vector3f v) {
        float x = elements[0][0] * v.x + elements[0][1] * v.y + elements[0][2] * v.z + elements[0][3];
        float y = elements[1][0] * v.x + elements[1][1] * v.y + elements[1][2] * v.z + elements[1][3];
        float z = elements[2][0] * v.x + elements[2][1] * v.y + elements[2][2] * v.z + elements[2][3];
        return new Vector3f(z, y, x); // Порядок (z, y, x)
    }

    // Метод для создания матрицы масштабирования
    public static Matrix4f scale(float sx, float sy, float sz) {
        Matrix4f result = new Matrix4f();
        result.elements[0][0] = sx;
        result.elements[1][1] = sy;
        result.elements[2][2] = sz;
        return result;
    }

    // Метод для создания матрицы вращения вокруг оси Z
    public static Matrix4f rotate(float angle) {
        Matrix4f result = new Matrix4f();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        result.elements[0][0] = cos;
        result.elements[0][1] = -sin;
        result.elements[1][0] = sin;
        result.elements[1][1] = cos;
        return result;
    }

    // Метод для создания матрицы переноса
    public static Matrix4f translate(float tx, float ty, float tz) {
        Matrix4f result = new Matrix4f();
        result.elements[0][3] = tx;
        result.elements[1][3] = ty;
        result.elements[2][3] = tz;
        return result;
    }
}