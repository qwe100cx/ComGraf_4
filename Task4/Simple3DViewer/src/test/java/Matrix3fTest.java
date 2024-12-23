import com.cgvsu.math.MathConstants;
import com.cgvsu.math.Matrix3f;
import com.cgvsu.math.Vector2f;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;

public class Matrix3fTest {

    @Test
    public void testMatrixCreation() {
        float[][] elements = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3f matrix = new Matrix3f(elements);

        System.out.println("Expected: " + Arrays.deepToString(elements));
        System.out.println("Actual: " + Arrays.deepToString(matrix.elements));

        assertArrayEquals(elements, matrix.elements, MathConstants.EPSILON);
    }

    @Test
    public void testMatrixCreationInvalid() {
        float[][] invalidElements = {
                {1, 2},
                {3, 4}
        };
        assertThrows(IllegalArgumentException.class, () -> new Matrix3f(invalidElements));
    }

    @Test
    public void testMultiplyMatrixByVector() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {0, 0, 1}
        });
        Vector2f vector = new Vector2f(1, 2);
        Vector2f result = matrix.multiply(vector);

        System.out.println("Expected: (8, 14)");
        System.out.println("Actual: (" + result.x + ", " + result.y + ")");

        assertEquals(8, result.x, MathConstants.EPSILON);
        assertEquals(14, result.y, MathConstants.EPSILON);
    }

    @Test
    public void testMultiplyMatrixByMatrix() {
        Matrix3f matrixA = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {0, 0, 1}
        });
        Matrix3f matrixB = new Matrix3f(new float[][]{
                {7, 8, 9},
                {10, 11, 12},
                {0, 0, 1}
        });
        Matrix3f result = matrixA.multiply(matrixB);

        float[][] expected = {
                {67, 76, 87},
                {97, 110, 123},
                {0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(result.elements));

        assertArrayEquals(expected, result.elements, MathConstants.EPSILON);
    }

    @Test
    public void testTranspose() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f transposed = matrix.transpose();
        float[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(transposed.elements));

        assertArrayEquals(expected, transposed.elements, MathConstants.EPSILON);
    }

    @Test
    public void testScaling() {
        Matrix3f scaling = Matrix3f.scaling(2, 3);
        float[][] expected = {
                {2, 0, 0},
                {0, 3, 0},
                {0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(scaling.elements));

        assertArrayEquals(expected, scaling.elements, MathConstants.EPSILON);
    }

    @Test
    public void testTranslation() {
        Matrix3f translation = Matrix3f.translation(1, 2);
        float[][] expected = {
                {1, 0, 1},
                {0, 1, 2},
                {0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(translation.elements));

        assertArrayEquals(expected,translation.elements, MathConstants.EPSILON);
    }

    @Test
    public void testRotation() {
        Matrix3f rotation = Matrix3f.rotation((float) Math.PI / 2);
        float[][] expected = {
                {0, -1, 0},
                {1, 0, 0},
                {0, 0, 1}
        };

        System.out.println("Expected: " + Arrays.deepToString(expected));
        System.out.println("Actual: " + Arrays.deepToString(rotation.elements));

        assertArrayEquals(expected, rotation.elements, MathConstants.EPSILON);
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