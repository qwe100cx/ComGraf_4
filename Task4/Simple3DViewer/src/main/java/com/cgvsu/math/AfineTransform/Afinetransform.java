package com.cgvsu.math.AfineTransform;

import com.cgvsu.math.Matrix.Matrix3f;
import com.cgvsu.math.Matrix.Matrix4f;
import com.cgvsu.math.Vector.Vector2f;
import com.cgvsu.math.Vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Afinetransform {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\gg\\Desktop\\obj-m\\test.obj"; // Укажите путь к вашему .obj файлу
        List<Vector3f> points3D = new ArrayList<>();

        // Чтение векторов из файла .obj
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("v ")) { // Строка с вершиной
                    String[] parts = line.split(" ");
                    if (parts.length >= 4) { // Убедимся, что есть хотя бы 3 координаты
                        float x = Float.parseFloat(parts[1]);
                        float y = Float.parseFloat(parts[2]);
                        float z = Float.parseFloat(parts[3]);
                        points3D.add(new Vector3f(x, y, z));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Применение трансформаций к загруженным 3D-векторам
        for (Vector3f point3D : points3D) {
            // Применение масштабирования
            Matrix4f scaleMatrix3D = Matrix4f.scale(2, 2, 2); // Пример масштабирования
            Vector3f scaledPoint3D = scaleMatrix3D.multiply(point3D);
            System.out.println("Scaled Point 3D: " + scaledPoint3D);

            // Применение вращения
            Matrix4f rotationMatrix3D = Matrix4f.rotate((float) Math.toRadians(45)); // 45 градусов
            Vector3f rotatedPoint3D = rotationMatrix3D.multiply(point3D);
            System.out.println("Rotated Point 3D: " + rotatedPoint3D);

            // Применение переноса
            Matrix4f translationMatrix3D = Matrix4f.translate(3, 3, 3); // Пример переноса
            Vector3f translatedPoint3D = translationMatrix3D.multiply(point3D);
            System.out.println("Translated Point 3D: " + translatedPoint3D);
        }
    }
}
