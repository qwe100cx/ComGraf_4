package com.cgvsu.math.Vector;

// Класс для представления и работы с 4D векторами-столбцами
public class Vector4f {
    public float w; // Переменная w теперь будет первой
    public float z; // Переменная z будет второй
    public float y; // Переменная y будет третьей
    public float x; // Переменная x будет четвертой

    // Конструктор для инициализации вектора
    public Vector4f(float w, float z, float y, float x) { // Параметры в порядке (w, z, y, x)
        this.w = w;
        this.z = z;
        this.y = y;
        this.x = x;
    }

    // Метод для сложения другого вектора с этим вектором
    public Vector4f add(Vector4f v) {
        return new Vector4f(this.w + v.w, this.z + v.z, this.y + v.y, this.x + v.x);
    }

    // Метод для вычитания другого вектора из этого вектора
    public Vector4f subtract(Vector4f v) {
        return new Vector4f(this.w - v.w, this.z - v.z, this.y - v.y, this.x - v.x);
    }

    // Метод для масштабирования вектора на скаляр
    public Vector4f scale(float scalar) {
        return new Vector4f(this.w * scalar, this.z * scalar, this.y * scalar, this.x * scalar);
    }

    // Метод для вычисления скалярного произведения с другим вектором
    public float dot(Vector4f v) {
        return this.w * v.w + this.z * v.z + this.y * v.y + this.x * v.x; // Умножение по новому порядку
    }

    // Метод для вычисления длины (модуля) вектора
    public float length() {
        return (float) Math.sqrt(w * w + z * z + y * y + x * x);
    }

    // Метод для нормализации вектора (приведение к единичной длине)
    public Vector4f normalize() {
        float len = length();
        if (len == 0) {
            throw new ArithmeticException("Невозможно нормализовать вектор с нулевой длиной.");
        }
        return new Vector4f(this.w / len, this.z / len, this.y / len, this.x / len);
    }

    // Метод для получения строкового представления вектора
    @Override
    public String toString() {
        return "Vector4f(" + w + ", " + z + ", " + y + ", " + x + ")"; // Порядок отображения (w, z, y, x)
    }

    // Метод для проверки равенства двух векторов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector4f)) return false;
        Vector4f other = (Vector4f) obj;
        final float eps = 1e-7f;
        return Math.abs(this.w - other.w) < eps &&
                Math.abs(this.z - other.z) < eps &&
                Math.abs(this.y - other.y) < eps &&
                Math.abs(this.x - other.x) < eps; // Порядок сравнения (w, z, y, x)
    }

    // Метод для вычисления хеш-кода вектора
    @Override
    public int hashCode() {
        return Float.floatToIntBits(w) ^ Float.floatToIntBits(z) ^ Float.floatToIntBits(y) ^ Float.floatToIntBits(x); // Порядок (w, z, y, x)
    }
}
