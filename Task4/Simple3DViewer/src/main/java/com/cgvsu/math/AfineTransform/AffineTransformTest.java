package com.cgvsu.math.AfineTransform;

import com.cgvsu.math.Vector.Vector3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AffineTransformTest {
    private AffineTransform affineTransform;

    @BeforeEach
    public void setUp() {
        affineTransform = new AffineTransform();
    }

    @Test
    public void testScale() {
        Vector3f point = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f scaledPoint = affineTransform.scale(point, 2.0f, 3.0f, 4.0f);

        assertEquals(2.0f, scaledPoint.getZ(), 1e-7f);
        assertEquals(6.0f, scaledPoint.getY(), 1e-7f);
        assertEquals(12.0f, scaledPoint.getX(), 1e-7f);
    }

    @Test
    public void testRotate() {
        Vector3f point = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f rotatedPoint = affineTransform.rotate(point, 90.0f);

        assertEquals(0.0f, rotatedPoint.getZ(), 1e-7f);
        assertEquals(1.0f, rotatedPoint.getY(), 1e-7f);
        assertEquals(0.0f, rotatedPoint.getX(), 1e-7f);
    }

    @Test
    public void testTranslate() {
        Vector3f point = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f translatedPoint = affineTransform.translate(point, 5.0f, -3.0f, 2.0f);

        assertEquals(6.0f, translatedPoint.getZ(), 1e-7f);
        assertEquals(-1.0f, translatedPoint.getY(), 1e-7f);
        assertEquals(5.0f, translatedPoint.getX(), 1e-7f);
    }

    @Test
    public void testLoadObjFile() throws IOException {
        String tempFilePath = "test.obj";
        try (FileWriter writer = new FileWriter(tempFilePath)) {
            writer.write("v 1.0 2.0 3.0\n");
            writer.write("v 4.0 5.0 6.0\n");
            writer.write("v 7.0 8.0 9.0\n");
        }

        affineTransform.loadObjFile(tempFilePath);
        List<Vector3f> points = affineTransform.getPoints3D();

        assertEquals(3, points.size());
        assertEquals(new Vector3f(3.0f, 2.0f, 1.0f), points.get(0));
        assertEquals(new Vector3f(6.0f, 5.0f, 4.0f), points.get(1));
        assertEquals(new Vector3f(9.0f, 8.0f, 7.0f), points.get(2));

        Files.deleteIfExists(Paths.get(tempFilePath));
    }

    @Test
    public void testLoadObjFileWithInvalidData() {
        assertThrows(IOException.class, () -> {
            affineTransform.loadObjFile("invalid_path.obj");
        });
    }
}