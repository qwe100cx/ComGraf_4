package com.cgvsu.math;

public class Matrix4f {
    private float[][] elements;

    public Matrix4f() {
        elements = new float[4][4];
    }

    public Matrix4f(float[][] elements) {
        if (elements.length != 4 || elements[0].length != 4) {
            throw new IllegalArgumentException("Матрица должна быть размером 4x4");
        }
        this.elements = elements;
    }

    public Matrix4f(Matrix4f matrix) {
        this.elements = matrix.elements;
    }

    // Умножение матрицы на вектор-столбец
    public Vector3f multiply(Vector3f vector) {
        float newX = elements[0][0] * vector.x + elements[0][1] * vector.y + elements[0][2] * vector.z + elements[0][3];
        float newY = elements[1][0] * vector.x + elements[1][1] * vector.y + elements[1][2] * vector.z + elements[1][3];
        float newZ = elements[2][0] * vector.x + elements[2][1] * vector.y + elements[2][2] * vector.z + elements[2][3];
        return new Vector3f(newX, newY, newZ);
    }

    // Умножение матрицы на матрицу
    public Matrix4f multiply(Matrix4f other) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) { // Строки текущей матрицы
            for (int j = 0; j < 4; j++) { // Столбцы другой матрицы
                result[i][j] = elements[i][0] * other.elements[0][j] +
                        elements[i][1] * other.elements[1][j] +
                        elements[i][2] * other.elements[2][j] +
                        elements[i][3] * other.elements[3][j];
            }
        }
        return new Matrix4f(result);
    }

    // Транспонирование матрицы
    public Matrix4f transpose() {
        float[][] transposed = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposed[i][j] = elements[j][i];
            }
        }
        return new Matrix4f(transposed);
    }

    // Статический метод: Создание единичной матрицы
    public static Matrix4f identity() {
        return new Matrix4f(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    // Статический метод: Создание матрицы переноса
    public static Matrix4f translation(float dx, float dy, float dz) {
        return new Matrix4f(new float[][]{
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        });
    }

    // Статический метод: Создание матрицы масштабирования
    public static Matrix4f scaling(float sx, float sy, float sz) {
        return new Matrix4f(new float[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        });
    }

    // Статический метод: Создание матрицы вращения вокруг оси X
    public static Matrix4f rotationX(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4f(new float[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        });
    }

    // Статический метод: Создание матрицы вращения вокруг оси Y
    public static Matrix4f rotationY(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4f(new float[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        });
    }

    // Статический метод: Создание матрицы вращения вокруг оси Z
    public static Matrix4f rotationZ(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4f(new float[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }
}
