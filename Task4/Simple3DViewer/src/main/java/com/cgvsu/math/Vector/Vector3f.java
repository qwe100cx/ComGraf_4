package com.cgvsu.math.Vector;

// Класс для представления и работы с 3D векторами-столбцами
public class Vector3f {
    /**
     * The x component.
     */
    public float x;

    /**
     * The y component.
     */
    public float y;

    /**
     * The z component.
     */
    public float z;

    /**
     * Create a new vector representing the zero vector.
     * i.e. all the components are <code>0</code>.
     */
    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Create a new vector with the specified components.
     *
     * @param x the x component.
     * @param y the y component.
     * @param z the z component.
     */
    public Vector3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Геттеры для координат
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    // Метод для сложения другого вектора с этим вектором
    public Vector3f add(Vector3f v) {
        return new Vector3f(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    // Метод для вычитания другого вектора из этого вектора
    public Vector3f subtract(Vector3f v) {
        return new Vector3f(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    // Метод для вычисления скалярного произведения с другим вектором
    public float dot(Vector3f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z; // Умножение по новому порядку
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
        return new Vector3f(x / len, y / len, z / len);
    }

    // Метод для получения строкового представления вектора
    @Override
    public String toString() {
        return "Vector3f(" + x + ", " + y + ", " + z + ")"; // Изменен порядок отображения
    }

    // Метод для проверки равенства двух векторов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) obj;
        final float eps = 1e-7f;
        return Math.abs(this.x - other.x) < eps &&
                Math.abs(this.y - other.y) < eps &&
                Math.abs(this.z - other.z) < eps; // Изменен порядок сравнения
    }

    // Метод для вычисления хеш-кода вектора
    @Override
    public int hashCode() {
        return Float.floatToIntBits(x) ^ Float.floatToIntBits(y) ^ Float.floatToIntBits(z); // Изменен порядок
    }

    // Метод для вычисления векторного произведения с другим вектором
    public Vector3f cross(Vector3f v) {
        return new Vector3f(
                this.y * v.z - this.z * v.y, // x-координата результата
                this.z * v.x - this.x * v.z, // y-координата результата
                this.x * v.y - this.y * v.x   // z-координата результата
        );
    }

    // Метод для масштабирования вектора на скаляр
    public Vector3f scale(float scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }
}
