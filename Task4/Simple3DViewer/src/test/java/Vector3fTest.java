import com.cgvsu.math.MathConstants;
import com.cgvsu.math.Vector3f;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Vector3fTest {

    @Test
    public void testVectorCreation() {
        Vector3f vector = new Vector3f(1, 2, 3);

        System.out.println("Expected: (1, 2, 3)");
        System.out.println("Actual: (" + vector.x + ", " + vector.y + ", " + vector.z + ")");

        Assert.assertEquals(1, vector.x, MathConstants.EPSILON);
        assertEquals(2, vector.y, MathConstants.EPSILON);
        assertEquals(3, vector.z, MathConstants.EPSILON);
    }

    @Test
    public void testAdd() {
        Vector3f vectorA = new Vector3f(1, 2, 3);
        Vector3f vectorB = new Vector3f(4, 5, 6);
        Vector3f result = vectorA.add(vectorB);

        System.out.println("Expected: (5, 7, 9)");
        System.out.println("Actual: (" + result.x + ", " + result.y + ", " + result.z + ")");

        assertEquals(5, result.x, MathConstants.EPSILON);
        assertEquals(7, result.y, MathConstants.EPSILON);
        assertEquals(9, result.z, MathConstants.EPSILON);
    }

    @Test
    public void testSubtract() {
        Vector3f vectorA = new Vector3f(4, 5, 6);
        Vector3f vectorB = new Vector3f(1, 2, 3);
        Vector3f result = vectorA.subtract(vectorB);

        System.out.println("Expected: (3, 3, 3)");
        System.out.println("Actual: (" + result.x + ", " + result.y + ", " + result.z + ")");

        assertEquals(3, result.x, MathConstants.EPSILON);
        assertEquals(3, result.y, MathConstants.EPSILON);
        assertEquals(3, result.z, MathConstants.EPSILON);
    }

    @Test
    public void testScale() {
        Vector3f vector = new Vector3f(1, 2, 3);
        Vector3f result = vector.scale(2);

        System.out.println("Expected: (2, 4, 6)");
        System.out.println("Actual: (" + result.x + ", " + result.y + ", " + result.z + ")");

        assertEquals(2, result.x, MathConstants.EPSILON);
        assertEquals(4, result.y, MathConstants.EPSILON);
        assertEquals(6, result.z, MathConstants.EPSILON);
    }

    @Test
    public void testNormalize() {
        Vector3f vector = new Vector3f(1, 2, 2);
        Vector3f result = vector.normalize();

        float length = (float) Math.sqrt(1 * 1 + 2 * 2 + 2 * 2);
        System.out.println("Expected: (" + (1 / length) + ", " + (2 / length) + ", " + (2 / length) + ")");
        System.out.println("Actual: (" + result.x + ", " + result.y + ", " + result.z + ")");

        assertEquals(1 / length, result.x, MathConstants.EPSILON);
        assertEquals(2 / length, result.y, MathConstants.EPSILON);
        assertEquals(2 / length, result.z, MathConstants.EPSILON);
    }

    @Test
    public void testDot() {
        Vector3f vectorA = new Vector3f(1, 2, 3);
        Vector3f vectorB = new Vector3f(4, 5, 6);
        float result = vectorA.dot(vectorB);

        System.out.println("Expected: 32");
        System.out.println("Actual: " + result);

        assertEquals(32, result, MathConstants.EPSILON);
    }

    @Test
    public void testCross() {
        Vector3f vectorA = new Vector3f(1, 2, 3);
        Vector3f vectorB = new Vector3f(4, 5, 6);
        Vector3f result = vectorA.cross(vectorB);

        System.out.println("Expected: (-3, 6, -3)");
        System.out.println("Actual: (" + result.x + ", " + result.y + ", " + result.z + ")");

        assertEquals(-3, result.x, MathConstants.EPSILON);
        assertEquals(6, result.y, MathConstants.EPSILON);
        assertEquals(-3, result.z, MathConstants.EPSILON);
    }

    @Test
    public void testEquals() {
        Vector3f vectorA = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vectorB = new Vector3f(1.0f + MathConstants.EPSILON / 2, 2.0f, 3.0f);

        System.out.println("Vector A: (" + vectorA.x + ", " + vectorA.y + ", " + vectorA.z + ")");
        System.out.println("Vector B: (" + vectorB.x + ", " + vectorB.y + ", " + vectorB.z + ")");
        System.out.println("Vectors are equal: " + vectorA.equals(vectorB));

        assertTrue(vectorA.equals(vectorB));
    }
}