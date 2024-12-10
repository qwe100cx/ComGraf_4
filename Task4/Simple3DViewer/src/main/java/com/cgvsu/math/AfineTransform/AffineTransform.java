package com.cgvsu.math.AfineTransform;

import com.cgvsu.math.Matrix.Matrix4f;
import com.cgvsu.math.Vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AffineTransform {
    private List<Vector3f> points3D = new ArrayList<>();

    public List<Vector3f> getPoints3D() {
        return points3D;
    }

    public void loadObjFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("v ")) { // Строка с вершиной
                    String[] parts = line.split(" ");
                    if (parts.length >= 4) { // Убедимся, что есть хотя бы 3 координаты
                        float x = Float.parseFloat(parts[1]);
                        float y = Float.parseFloat(parts[2]);
                        float z = Float.parseFloat(parts[3]);
                        points3D.add(new Vector3f()); // Обратите внимание на порядок (z, y, x)
                    }
                }
            }
        }
    }

    public Vector3f scale(Vector3f point, float sx, float sy, float sz) {
        Matrix4f scaleMatrix = Matrix4f.scale(sx, sy, sz);
        return scaleMatrix.multiply(point); // Убедитесь, что метод multiply работает с Vector3f
    }

    public Vector3f rotate(Vector3f point, float angle) {
        Matrix4f rotationMatrix = Matrix4f.rotate((float) Math.toRadians(angle));
        return rotationMatrix.multiply(point); // Убедитесь, что метод multiply работает с Vector3f
    }

    public Vector3f translate(Vector3f point, float tx, float ty, float tz) {
        Matrix4f translationMatrix = Matrix4f.translate(tx, ty, tz);
        return translationMatrix.multiply(point); // Убедитесь, что метод multiply работает с Vector3f
    }
}
