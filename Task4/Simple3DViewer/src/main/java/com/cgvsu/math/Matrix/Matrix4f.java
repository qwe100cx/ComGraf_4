package com.cgvsu.math.Matrix;

// Класс для представления и работы с 4x4 матрицами
public class Matrix4f {
    private float[][] elements;

    // Конструктор для создания матрицы 4x4
    public Matrix4f(float a, float b, float c, float d,
                    float e, float f, float g, float h,
                    float i, float j, float k, float l,
                    float m, float n, float o, float p) {
        elements = new float[4][4];
        elements[0][0] = a; // Первый элемент (0,0)
        elements[0][1] = b; // Второй элемент (0,1)
        elements[0][2] = c; // Третий элемент (0,2)
        elements[0][3] = d; // Четвертый элемент (0,3)
        elements[1][0] = e; // Пятый элемент (1,0)
        elements[1][1] = f; // Шестой элемент (1,1)
        elements[1][2] = g; // Седьмой элемент (1,2)
        elements[1][3] = h; // Восьмой элемент (1,3)
        elements[2][0] = i; // Девятый элемент (2,0)
        elements[2][1] = j; // Десятый элемент (2,1)
        elements[2][2] = k; // Одиннадцатый элемент (2,2)
        elements[2][3] = l; // Двенадцатый элемент (2,3)
        elements[3][0] = m; // Тринадцатый элемент (3,0)
        elements[3][1] = n; // Четырнадцатый элемент (3,1)
        elements[3][2] = o; // Пятнадцатый элемент (3,2)
        elements[3][3] = p; // Шестнадцатый элемент (3,3)
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
    public Matrix4f multiply(Matrix4f other) {
        Matrix4f result = new Matrix4f(0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.set(i, j, this.get(i, 0) * other.get(0, j) +
                        this.get(i, 1) * other.get(1, j) +
                        this.get(i, 2) * other.get(2, j) +
                        this.get(i, 3) * other.get(3, j));
            }
        }
        return result;
    }

    // Метод для транспонирования матрицы
    public Matrix4f transpose() {
        return new Matrix4f(elements[0][0], elements[1][0], elements[2][0], elements[3][0],
                elements[0][1], elements[1][1], elements[2][1], elements[3][1],
                elements[0][2], elements[1][2], elements[2][2], elements[3][2],
                elements[0][3], elements[1][3], elements[2][3], elements[3][3]);
    }

    // Метод для вычисления определителя матрицы
    public float determinant() {
        // Вычисление определителя 4x4 матрицы с использованием разложения по строке
        float det = 0;
        for (int i = 0; i < 4; i++) {
            det += (i % 2 == 0 ? 1 : -1) * elements[0][i] * minor(0, i).determinant();
        }
        return det;
    }

    // Метод для вычисления минорной матрицы
    private Matrix3f minor(int row, int col) {
        float[] values = new float[9];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != row && j != col) {
                    values[index++] = elements[i][j];
                }
            }
        }
        return new Matrix3f(values[0], values[1], values[2],
                values[3], values[4], values[5],
                values[6], values[7], values[8]);
    }

    // Метод для получения строкового представления матрицы
    @Override
    public String toString() {
        return "[" + elements[0][0] + ", " + elements[0][1] + ", " + elements[0][2] + ", " + elements[0][3] + "]\n" +
                "[" + elements[1][0] + ", " + elements[1][1] + ", " + elements[1][2] + ", " + elements[1][3] + "]\n" +
                "[" + elements[2][0] + ", " + elements[2][1] + ", " + elements[2][2] + ", " + elements[2][3] + "]\n" +
                "[" + elements[3][0] + ", " + elements[3][1] + ", " + elements[3][2] + ", " + elements[3][3] + "]";
    }
}