package com.cgvsu.objreader;

import com.cgvsu.math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        // АГААА! Вот тест, который говорит, что у метода нет проверки на более, чем 3 числа
        // А такой случай лучше не игнорировать, а сообщать пользователю, что у него что-то не так
        // ассерт, чтобы не забыть про тест:
        Assertions.assertTrue(false);


        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testToWorldCoordinatesRotation() {
        // Создаем объект GraphicConveyor и задаем параметры поворота
        GraphicConveyor graphicConveyor = new GraphicConveyor();

        // Устанавливаем поворот вокруг оси X на 90 градусов
        graphicConveyor.rotate(90, 0, 0);

        // Входной вектор в локальных координатах (1, 0, 0) - по оси X
        Vector3f localCoordinates = new Vector3f(1.0f, 0.0f, 0.0f);

        // Преобразуем локальные координаты в мировые
        Vector3f worldCoordinates = graphicConveyor.toWorldCoordinates(localCoordinates);

        // Ожидаем, что вектор после поворота вокруг оси X на 90 градусов будет (1, 0, 0)
        // Вектор (1, 0, 0) после поворота на 90 градусов по оси X не изменится
        Vector3f expectedCoordinates = new Vector3f(1.0f, 0.0f, 0.0f);

        // Проверяем, что результат соответствует ожиданиям
        Assertions.assertTrue(worldCoordinates.equals(expectedCoordinates));
    }

    @Test
    public void testToWorldCoordinatesRotationY() {
        // Создаем объект GraphicConveyor и задаем параметры поворота
        GraphicConveyor graphicConveyor = new GraphicConveyor();

        // Устанавливаем поворот вокруг оси Y на 90 градусов
        graphicConveyor.rotate(0, 90, 0);

        // Входной вектор в локальных координатах (1, 0, 0) - по оси X
        Vector3f localCoordinates = new Vector3f(1.0f, 0.0f, 0.0f);

        // Преобразуем локальные координаты в мировые
        Vector3f worldCoordinates = graphicConveyor.toWorldCoordinates(localCoordinates);

        // Ожидаем, что вектор после поворота вокруг оси Y на 90 градусов будет (0, 0, -1)
        Vector3f expectedCoordinates = new Vector3f(0.0f, 0.0f, -1.0f);

        // Проверяем, что результат соответствует ожиданиям
        Assertions.assertTrue(worldCoordinates.equals(expectedCoordinates));
    }

    @Test
    public void testToWorldCoordinatesRotationZ() {
        // Создаем объект GraphicConveyor и задаем параметры поворота
        GraphicConveyor graphicConveyor = new GraphicConveyor();

        // Устанавливаем поворот вокруг оси Z на 90 градусов
        graphicConveyor.rotate(0, 0, 90);

        // Входной вектор в локальных координатах (1, 0, 0) - по оси X
        Vector3f localCoordinates = new Vector3f(1.0f, 0.0f, 0.0f);

        // Преобразуем локальные координаты в мировые
        Vector3f worldCoordinates = graphicConveyor.toWorldCoordinates(localCoordinates);

        // Ожидаем, что вектор после поворота вокруг оси Z на 90 градусов будет (0, 1, 0)
        Vector3f expectedCoordinates = new Vector3f(0.0f, 1.0f, 0.0f);

        // Проверяем, что результат соответствует ожиданиям
        Assertions.assertTrue(worldCoordinates.equals(expectedCoordinates));
    }
}