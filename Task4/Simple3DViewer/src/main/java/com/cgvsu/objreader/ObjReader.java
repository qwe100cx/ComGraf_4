package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ObjReader {

	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	public static Model read(String fileContent) {
		Model result = new Model();

		int lineInd = 0;
		Scanner scanner = new Scanner(fileContent);
		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) {
				continue;
			}

			final String token = wordsInLine.get(0);
			wordsInLine.remove(0);

			++lineInd;
			switch (token) {
				// Для структур типа вершин методы написаны так, чтобы ничего не знать о внешней среде.
				// Они принимают только то, что им нужно для работы, а возвращают только то, что могут создать.
				// Исключение - индекс строки. Он прокидывается, чтобы выводить сообщение об ошибке.
				// Могло быть иначе. Например, метод parseVertex мог вместо возвращения вершины принимать вектор вершин
				// модели или сам класс модели, работать с ним.
				// Но такой подход может привести к большему количеству ошибок в коде. Например, в нем что-то может
				// тайно сделаться с классом модели.
				// А еще это портит читаемость
				// И не стоит забывать про тесты. Чем проще вам задать данные для теста, проверить, что метод рабочий,
				// тем лучше.
				case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
				case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
				case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
				case OBJ_FACE_TOKEN -> result.polygons.add(parseFace(wordsInLine, lineInd));
				default -> {}
			}
		}

		return result;
	}

	// Всем методам кроме основного я поставил модификатор доступа protected, чтобы обращаться к ним в тестах
	protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few vertex arguments.", lineInd);
		}
	}

	protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			return new Vector2f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)));

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
		}
	}

	protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few normal arguments.", lineInd);
		}
	}

	protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		ArrayList<Integer> onePolygonVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();

		for (String s : wordsInLineWithoutToken) {
			parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
		}

		Polygon result = new Polygon();
		result.setVertexIndices(onePolygonVertexIndices);
		result.setTextureVertexIndices(onePolygonTextureVertexIndices);
		result.setNormalIndices(onePolygonNormalIndices);
		return result;
	}

	// Обратите внимание, что для чтения полигонов я выделил еще один вспомогательный метод.
	// Это бывает очень полезно и с точки зрения структурирования алгоритма в голове, и с точки зрения тестирования.
	// В радикальных случаях не бойтесь выносить в отдельные методы и тестировать код из одной-двух строчек.
	protected static void parseFaceWord(
			String wordInLine,
			ArrayList<Integer> onePolygonVertexIndices,
			ArrayList<Integer> onePolygonTextureVertexIndices,
			ArrayList<Integer> onePolygonNormalIndices,
			int lineInd) {
		try {
			String[] wordIndices = wordInLine.split("/");
			switch (wordIndices.length) {
				case 1 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
				}
				case 2 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
					onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
				}
				case 3 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
					onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
					if (!wordIndices[1].equals("")) {
						onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
					}
				}
				default -> {
					throw new ObjReaderException("Invalid element size.", lineInd);
				}
			}

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse int value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few arguments.", lineInd);
		}
	}
}
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

public abstract class BaseModel {
	protected String name;

	public BaseModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void displayInfo();
}

public class TriangulatedModel extends BaseModel {
	private List<int[]> triangles;

	public TriangulatedModel(String name) {
		super(name);
		this.triangles = new ArrayList<>();
	}

	public void addTriangle(int v1, int v2, int v3) {
		triangles.add(new int[]{v1, v2, v3});
	}

	public List<int[]> getTriangles() {
		return triangles;
	}

	@Override
	public void displayInfo() {
		System.out.println("Model Name: " + name);
		System.out.println("Triangles:");
		for (int[] triangle : triangles) {
			System.out.println(Arrays.toString(triangle));
		}
	}
}
