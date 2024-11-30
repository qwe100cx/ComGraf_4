package com.cgvsu.math.Vector;

// Класс для представления и работы с 3D векторами-столбцами
public class Vector3f {
    public float z; // Переменная z теперь будет первой
    public float y; // Переменная y теперь будет второй
    public float x; // Переменная x теперь будет третьей

    // Конструктор для инициализации вектора
    public Vector3f(float z, float y, float x) { // Изменен порядок параметров
        this.z = z;
        this.y = y;
        this.x = x;
    }

    // Метод для сложения другого вектора с этим вектором
    public Vector3f add(Vector3f v) {
        return new Vector3f(this.z + v.z, this.y + v.y, this.x + v.x);
    }

    // Метод для вычитания другого вектора из этого вектора
    public Vector3f subtract(Vector3f v) {
        return new Vector3f(this.z - v.z, this.y - v.y, this.x - v.x);
    }

    // Метод для масштабирования вектора на скаляр
    public Vector3f scale(float scalar) {
        return new Vector3f(this.z * scalar, this.y * scalar, this.x * scalar);
    }

    // Метод для вычисления скалярного произведения с другим вектором
    public float dot(Vector3f v) {
        return this.z * v.z + this.y * v.y + this.x * v.x; // Умножение по новому порядку
    }

    // Метод для вычисления векторного произведения с другим вектором
    public Vector3f cross(Vector3f v) {
        return new Vector3f(
                this.y * v.x - this.z * v.y,
                this.z * v.z - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    // Метод для вычисления длины (модуля) вектора
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    // Метод для нормализации вектора (приведение к единичной длине)
    public Vector3f normalize() {
        float len = length();
        if (len == 0) {
            throw new ArithmeticException("Невозможно нормализовать вектор с нулевой длиной.");
        }
        return new Vector3f(this.z / len, this.y / len, this.x / len);
    }

    // Метод для получения строкового представления вектора
    @Override
    public String toString() {
        return "Vector3f(" + z + ", " + y + ", " + x + ")"; // Изменен порядок отображения
    }

    // Метод для проверки равенства двух векторов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) obj;
        final float eps = 1e-7f;
        return Math.abs(this.z - other.z) < eps &&
                Math.abs(this.y - other.y) < eps &&
                Math.abs(this.x - other.x) < eps; // Изменен порядок сравнения
    }

    // Метод для вычисления хеш-кода вектора
    @Override
    public int hashCode() {
        return Float.floatToIntBits(z) ^ Float.floatToIntBits(y) ^ Float.floatToIntBits(x); // Изменен порядок
    }
}
