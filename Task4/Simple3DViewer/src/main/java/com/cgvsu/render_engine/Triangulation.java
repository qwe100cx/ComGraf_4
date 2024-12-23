package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Triangulation {
    public static TriangulatedModel triangulate(String name, List<double[]> vertices) {
        if (!isSimplePolygon(vertices)) {
            throw new IllegalStateException("Input polygon is not simple.");
        }

        if (isClockwise(vertices)) {
            System.out.println("Reversing vertices to ensure counterclockwise order.");
            reverseVertices(vertices);
        }

        TriangulatedModel model = new TriangulatedModel(name);

        int n = vertices.size();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indices.add(i);
        }

        while (indices.size() > 2) {
            boolean earFound = false;
            for (int i = 0; i < indices.size(); i++) {
                int prev = indices.get((i - 1 + indices.size()) % indices.size());
                int curr = indices.get(i);
                int next = indices.get((i + 1) % indices.size());

                System.out.println("Trying ear: prev=" + prev + ", curr=" + curr + ", next=" + next);
                if (isEar(vertices, prev, curr, next, indices)) {
                    System.out.println("Found ear: " + prev + ", " + curr + ", " + next);
                    model.addTriangle(prev, curr, next);
                    indices.remove(i);
                    earFound = true;
                    break;
                }
            }

            if (!earFound) {
                throw new IllegalStateException("Triangulation failed. Could not find an ear.");
            }
        }

        return model;
    }

    private static boolean isEar(List<double[]> vertices, int prev, int curr, int next, List<Integer> indices) {
        double[] a = vertices.get(prev);
        double[] b = vertices.get(curr);
        double[] c = vertices.get(next);

        if (!isConvex(a, b, c)) {
            System.out.println("Not convex: prev=" + Arrays.toString(a) + ", curr=" + Arrays.toString(b) + ", next=" + Arrays.toString(c));
            return false;
        }

        for (int index : indices) {
            if (index != prev && index != curr && index != next) {
                double[] p = vertices.get(index);
                if (isPointInTriangle(p, a, b, c)) {
                    System.out.println("Point " + Arrays.toString(p) + " is inside triangle formed by " + Arrays.toString(a) + ", " + Arrays.toString(b) + ", " + Arrays.toString(c));
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isConvex(double[] a, double[] b, double[] c) {
        double cross = (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
        return cross > 0; // Для против часовой стрелки
    }

    private static boolean isPointInTriangle(double[] p, double[] a, double[] b, double[] c) {
        double areaOrig = Math.abs(cross(a, b) + cross(b, c) + cross(c, a));
        double area1 = Math.abs(cross(p, a) + cross(a, b) + cross(b, p));
        double area2 = Math.abs(cross(p, b) + cross(b, c) + cross(c, p));
        double area3 = Math.abs(cross(p, c) + cross(c, a) + cross(a, p));

        return Math.abs(areaOrig - (area1 + area2 + area3)) < 1e-6 && area1 > 0 && area2 > 0 && area3 > 0;
    }

    private static double cross(double[] p1, double[] p2) {
        return p1[0] * p2[1] - p1[1] * p2[0];
    }

    private static boolean isClockwise(List<double[]> vertices) {
        double sum = 0;
        for (int i = 0; i < vertices.size(); i++) {
            double[] curr = vertices.get(i);
            double[] next = vertices.get((i + 1) % vertices.size());
            sum += (next[0] - curr[0]) * (next[1] + curr[1]);
        }
        return sum > 0;
    }

    private static void reverseVertices(List<double[]> vertices) {
        for (int i = 0, j = vertices.size() - 1; i < j; i++, j--) {
            double[] temp = vertices.get(i);
            vertices.set(i, vertices.get(j));
            vertices.set(j, temp);
        }
    }

    private static boolean isSimplePolygon(List<double[]> vertices) {
        // Реализация проверки простоты полигона
        return true;
    }
}
