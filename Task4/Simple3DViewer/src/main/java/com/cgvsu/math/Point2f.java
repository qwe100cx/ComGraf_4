package com.cgvsu.math;

public class Point2f {
    public float x; // Координата X
    public float y; // Координата Y

    // Конструктор
    public Point2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Метод для получения строки с координатами
    @Override
    public String toString() {
        return "Point2f(" + x + ", " + y + ")";
    }

    // Метод для сложения двух точек
    public Point2f add(Point2f other) {
        return new Point2f(this.x + other.x, this.y + other.y);
    }

    // Метод для вычитания двух точек
    public Point2f subtract(Point2f other) {
        return new Point2f(this.x - other.x, this.y - other.y);
    }

    // Метод для умножения точки на скаляр
    public Point2f multiply(float scalar) {
        return new Point2f(this.x * scalar, this.y * scalar);
    }

    // Метод для деления точки на скаляр
    public Point2f divide(float scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Скаляр не может быть равен нулю.");
        }
        return new Point2f(this.x / scalar, this.y / scalar);
    }
}
