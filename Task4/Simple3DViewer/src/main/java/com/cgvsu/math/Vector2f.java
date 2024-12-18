package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector2f {
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x, y;

    // Сложение двух векторов
    public Vector2f add(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    // Вычитание двух векторов
    public Vector2f subtract(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    // Умножение вектора на скаляр
    public Vector2f multiply(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    // Вычисление длины вектора
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Нормализация вектора
    public Vector2f normalize() {
        float len = length();
        if (len == 0) {
            throw new IllegalStateException("Длина вектора равна нулю, нормализация невозможна");
        }
        return new Vector2f(x / len, y / len);
    }

    // Сравнение векторов с учетом погрешности
    public boolean equals(Vector2f other) {
        return Math.abs(this.x - other.x) < MathConstants.EPSILON &&
                Math.abs(this.y - other.y) < MathConstants.EPSILON;
    }
}
