package com.cgvsu.math.Matrix;

// Класс для представления и работы с 3x3 матрицами
public class Matrix3f {
    private float[][] elements;

    // Конструктор для создания матрицы 3x3
    public Matrix3f(float a, float b, float c, float d, float e, float f, float g, float h, float i) {
        elements = new float[3][3];
        elements[0][0] = a; // Первый столбец
        elements[1][0] = b; // Второй столбец
        elements[2][0] = c; // Третий столбец
        elements[0][1] = d; // Первый столбец
        elements[1][1] = e; // Второй столбец
        elements[2][1] = f; // Третий столбец
        elements[0][2] = g; // Первый столбец
        elements[1][2] = h; // Второй столбец
        elements[2][2] = i; // Третий столбец
    }

    // Метод для получения элемента матрицы
    public float get(int row, int col) {
        return elements[row][col];
    }

    // Метод для установки элемента матрицы
    public void set(int row, int col, float value) {
        elements[row][col] = value;
    }

    // Умножение матриц
    public Matrix3f multiply(Matrix3f other) {
        Matrix3f result = new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.set(i, j, this.get(i, 0) * other.get(0, j) +
                        this.get(i, 1) * other.get(1, j) +
                        this.get(i, 2) * other.get(2, j));
            }
        }
        return result;
    }

    // Метод для транспонирования матрицы
    public Matrix3f transpose() {
        return new Matrix3f(elements[0][0], elements[1][0], elements[2][0],
                elements[0][1], elements[1][1], elements[2][1],
                elements[0][2], elements[1][2], elements[2][2]);
    }

    // Метод для вычисления определителя матрицы
    public float determinant() {
        return elements[0][0] * (elements[1][1] * elements[2][2] - elements[1][2] * elements[2][1]) -
                elements[0][1] * (elements[1][0] * elements[2][2] - elements[1][2] * elements[2][0]) +
                elements[0][2] * (elements[1][0] * elements[2][1] - elements[1][1] * elements[2][0]);
    }

    // Метод для получения строкового представления матрицы
    @Override
    public String toString() {
        return "[" + elements[0][0] + ", " + elements[1][0] + ", " + elements[2][0] + "]\n" +
                "[" + elements[0][1] + ", " + elements[1][1] + ", " + elements[2][1] + "]\n" +
                "[" + elements[0][2] + ", " + elements[1][2] + ", " + elements[2][2] + "]";
    }
}
