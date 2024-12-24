package com.cgvsu.math;

public class Matrix3f {
    public float[][] elements;

    public Matrix3f() {
        this.elements = new float[3][3];
    }

    public Matrix3f(float[][] elements) {
        if (elements.length != 3 || elements[0].length != 3) {
            throw new IllegalArgumentException("Матрица должна быть размером 3x3");
        }
        this.elements = elements;
    }

    public Vector2f multiply(Vector2f vector) {
        float newX = elements[0][0] * vector.x + elements[0][1] * vector.y + elements[0][2];
        float newY = elements[1][0] * vector.x + elements[1][1] * vector.y + elements[1][2];
        return new Vector2f(newX, newY);
    }

    public Matrix3f multiply(Matrix3f other) {
        float[][] result = new float[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = elements[i][0] * other.elements[0][j]
                        + elements[i][1] * other.elements[1][j]
                        + elements[i][2] * other.elements[2][j];
            }
        }
        return new Matrix3f(result);
    }


    public Matrix3f transpose() {
        float[][] transposed = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                transposed[i][j] = elements[j][i];
            }
        }
        return new Matrix3f(transposed);
    }

    public static Matrix3f scaling(float scaleX, float scaleY) {
        return new Matrix3f(new float[][]{
                {scaleX, 0, 0},
                {0, scaleY, 0},
                {0, 0, 1}
        });
    }

    public static Matrix3f translation(float dx, float dy) {
        return new Matrix3f(new float[][]{
                {1, 0, dx},
                {0, 1, dy},
                {0, 0, 1}
        });
    }

    public static Matrix3f rotation(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix3f(new float[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}
        });
    }
}
