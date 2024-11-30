package com.cgvsu.math.Vector;

// Класс для представления и работы с 2D векторами-столбцами
public class Vector2f {
    public float y; // Переменная y будет первой
    public float x; // Переменная x будет второй

    // Конструктор для инициализации вектора
    public Vector2f(float y, float x) { // Параметры в порядке (y, x)
        this.y = y;
        this.x = x;
    }

    // Метод для сложения другого вектора с этим вектором
    public Vector2f add(Vector2f v) {
        return new Vector2f(this.y + v.y, this.x + v.x);
    }

    // Метод для вычитания другого вектора из этого вектора
    public Vector2f subtract(Vector2f v) {
        return new Vector2f(this.y - v.y, this.x - v.x);
    }

    // Метод для масштабирования вектора на скаляр
    public Vector2f scale(float scalar) {
        return new Vector2f(this.y * scalar, this.x * scalar);
    }

    // Метод для вычисления скалярного произведения с другим вектором
    public float dot(Vector2f v) {
        return this.y * v.y + this.x * v.x; // Умножение по новому порядку
    }

    // Метод для вычисления длины (модуля) вектора
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Метод для нормализации вектора (приведение к единичной длине)
    public Vector2f normalize() {
        float len = length();
        if (len == 0) {
            throw new ArithmeticException("Невозможно нормализовать вектор с нулевой длиной.");
        }
        return new Vector2f(this.y / len, this.x / len);
    }

    // Метод для получения строкового представления вектора
    @Override
    public String toString() {
        return "Vector2f(" + y + ", " + x + ")"; // Порядок отображения (y, x)
    }

    // Метод для проверки равенства двух векторов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector2f)) return false;
        Vector2f other = (Vector2f) obj;
        final float eps = 1e-7f;
        return Math.abs(this.y - other.y) < eps &&
                Math.abs(this.x - other.x) < eps; // Порядок сравнения (y, x)
    }

    // Метод для вычисления хеш-кода вектора
    @Override
    public int hashCode() {
        return Float.floatToIntBits(y) ^ Float.floatToIntBits(x); // Порядок (y, x)
    }
}