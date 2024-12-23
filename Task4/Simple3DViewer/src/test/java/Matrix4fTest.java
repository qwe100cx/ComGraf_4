import com.cgvsu.math.MathConstants;
import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Vector3f;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

public class Matrix4fTest {

    @Test
    public void testMatrixCreation() {
        float[][] elements = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix = new Matrix4f(elements);

        System.out.println("Expected: " + Arrays.deepToString(elements));
        System.out.println("Actual: " + Arrays.deepToString(matrix.elements));

        assertArrayEquals(elements, matrix.elements, MathConstants.EPSILON);
    }

    @Test
    public void testMatrixCreationInvalid() {
        float[][] invalidElements = {
                {1, 2, 3},
                {4, 5, 6}
        };
        assertThrows(IllegalArgumentException.class, () -> new Matrix4f(invalidElements));
    }

    @Test
    public void testMultiplyMatrix() {
        float[][] elementsA = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        float[][] elementsB = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        Matrix4f matrixA = new Matrix4f(elementsA);
        Matrix4f matrixB = new Matrix4f(elementsB);
        Matrix4f result = matrixA.multiply(matrixB);

        System.out.println("Expected: " + Arrays.deepToString(elementsA));
        System.out.println("Actual: " + Arrays.deepToString(result.elements));

        assertArrayEquals(elementsA, result.elements, MathConstants.EPSILON);
    }

    @Test
    public void testMultiplyVector() {
        float[][] elements = {
                {1, 0, 0, 1},
                {0, 1, 0, 2},
                {0, 0, 1, 3},
                {0, 0, 0, 1}
        };
        Matrix4f matrix = new Matrix4f(elements);
        Vector3f vector = new Vector3f(1, 1, 1);
        Vector3f result = matrix.multiply(vector);

        System.out.println("Expected: (2, 3, 4)");
        System.out.println("Actual: (" + result.x + ", " + result.y + ", " + result.z + ")");

        assertEquals(2, result.x, MathConstants.EPSILON);
        assertEquals(3, result.y, MathConstants.EPSILON);
        assertEquals(4, result.z, MathConstants.EPSILON);
    }

    @Test
    public void testTranspose() {
        float[][] elements = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix = new Matrix4f(elements);
        Matrix4f transposed = matrix.transpose();
        float[][] expected = {
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(transposed.elements));

        assertArrayEquals(expected, transposed.elements, MathConstants.EPSILON);
    }

    @Test
    public void testIdentityMatrix() {
        Matrix4f identity = Matrix4f.identity();
        float[][] expected = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(identity.elements));

        assertArrayEquals(expected, identity.elements, MathConstants.EPSILON);
    }

    @Test
    public void testTranslationMatrix() {
        Matrix4f translation = Matrix4f.translation(1, 2, 3);
        float[][] expected = {{1, 0, 0, 1},
                {0, 1, 0, 2},
                {0, 0, 1, 3},
                {0, 0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(translation.elements));

        assertArrayEquals(expected, translation.elements, MathConstants.EPSILON);
    }

    @Test
    public void testScalingMatrix() {
        Matrix4f scaling = Matrix4f.scaling(2, 3, 4);
        float[][] expected = {
                {2, 0, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(scaling.elements));

        assertArrayEquals(expected, scaling.elements, MathConstants.EPSILON);
    }

    @Test
    public void testRotationXMatrix() {
        Matrix4f rotationX = Matrix4f.rotationX((float) Math.PI / 2);
        float[][] expected = {
                {1, 0, 0, 0},
                {0, 0, -1, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(rotationX.elements));

        assertArrayEquals(expected, rotationX.elements, MathConstants.EPSILON);
    }

    @Test
    public void testRotationYMatrix() {
        Matrix4f rotationY = Matrix4f.rotationY((float) Math.PI / 2);
        float[][] expected = {
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(rotationY.elements));

        assertArrayEquals(expected, rotationY.elements, MathConstants.EPSILON);
    }

    @Test
    public void testRotationZMatrix() {
        Matrix4f rotationZ = Matrix4f.rotationZ((float) Math.PI / 2);
        float[][] expected = {
                {0, -1, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(rotationZ.elements));

        assertArrayEquals(expected, rotationZ.elements, MathConstants.EPSILON);
    }

    private void assertArrayEquals(float[][] expected, float[][] actual, float epsilon) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                if (Math.abs(expected[i][j] - actual[i][j]) > epsilon) {
                    throw new AssertionError("Arrays differ at [" + i + "][" + j + "]: expected " + expected[i][j] + " but was " + actual[i][j]);
                }
            }
        }
    }
}