package com.cgvsu.math.Matrix;

import com.cgvsu.math.Vector.Vector2f;

public class Matrix3f {
    public float[][] elements;

    public Matrix3f() {
        elements = new float[3][3];
        // Инициализация единичной матрицы
        for (int i = 0; i < 3; i++) {
            elements[i][i] = 1;
        }
    }

    // Метод для умножения матрицы на вектор
    public Vector2f multiply(Vector2f v) {
        float x = elements[0][0] * v.x + elements[0][1] * v.y + elements[0][2];
        float y = elements[1][0] * v.x + elements[1][1] * v.y + elements[1][2];
        return new Vector2f(y, x); // Порядок (y, x)
    }

    // Метод для создания матрицы масштабирования
    public static Matrix3f scale(float sx, float sy) {
        Matrix3f result = new Matrix3f();
        result.elements[0][0] = sx;
        result.elements[1][1] = sy;
        return result;
    }

    // Метод для создания матрицы вращения
    public static Matrix3f rotate(float angle) {
        Matrix3f result = new Matrix3f();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        result.elements[0][0] = cos;
        result.elements[0][1] = -sin;
        result.elements[1][0] = sin;
        result.elements[1][1] = cos;
        return result;
    }

    // Метод для создания матрицы переноса
    public static Matrix3f translate(float tx, float ty) {
        Matrix3f result = new Matrix3f();
        result.elements[0][2] = tx;
        result.elements[1][2] = ty;
        return result;
    }
}