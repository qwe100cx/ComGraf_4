package com.cgvsu.math.Matrix;

// Класс для представления и работы с 2x2 матрицами
public class Matrix2f {
    private float[][] elements;

    // Конструктор для создания матрицы 2x2
    public Matrix2f(float a, float b, float c, float d) {
        elements = new float[2][2];
        elements[0][0] = a; // Первый элемент (0,0)
        elements[0][1] = b; // Второй элемент (0,1)
        elements[1][0] = c; // Третий элемент (1,0)
        elements[1][1] = d; // Четвертый элемент (1,1)
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
    public Matrix2f multiply(Matrix2f other) {
        Matrix2f result = new Matrix2f(0, 0, 0, 0);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result.set(i, j, this.get(i, 0) * other.get(0, j) +
                        this.get(i, 1) * other.get(1, j));
            }
        }
        return result;
    }

    // Метод для транспонирования матрицы
    public Matrix2f transpose() {
        return new Matrix2f(elements[0][0], elements[1][0],
                elements[0][1], elements[1][1]);
    }

    // Метод для вычисления определителя матрицы
    public float determinant() {
        return elements[0][0] * elements[1][1] - elements[0][1] * elements[1][0];
    }

    // Метод для получения строкового представления матрицы
    @Override
    public String toString() {
        return "[" + elements[0][0] + ", " + elements[0][1] + "]\n" +
                "[" + elements[1][0] + ", " + elements[1][1] + "]";
    }
}
