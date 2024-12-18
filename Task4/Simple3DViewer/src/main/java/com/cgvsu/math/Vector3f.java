package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector3f {
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Добавление векторов
    public Vector3f add(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Вычитание векторов
    public Vector3f subtract(Vector3f other) {
        return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Масштабирование вектора
    public Vector3f scale(float scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Нормализация вектора
    public Vector3f normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        return new Vector3f(x / length, y / length, z / length);
    }

    // Скалярное произведение
    public float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Векторное произведение
    public Vector3f cross(Vector3f other) {
        return new Vector3f(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    // Сравнение векторов с учетом погрешности
    public boolean equals(Vector3f other) {
        return Math.abs(x - other.x) < MathConstants.EPSILON &&
                Math.abs(y - other.y) < MathConstants.EPSILON &&
                Math.abs(z - other.z) < MathConstants.EPSILON;
    }

    public float x, y, z;
}
