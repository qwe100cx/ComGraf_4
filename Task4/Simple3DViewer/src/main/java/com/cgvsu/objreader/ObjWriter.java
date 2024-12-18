package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjWriter {

    public static void write(Model model, String filePath) {
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            // Записываем вершины
            for (Vector3f vertex : model.vertices) {
                writer.write("v " + vertex.x + " " + vertex.y + " " + vertex.z + "\n");
            }

            // Записываем текстурные вершины
            for (Vector2f textureVertex : model.textureVertices) {
                writer.write("vt " + textureVertex.x + " " + textureVertex.y + "\n");
            }

            // Записываем нормали
            for (Vector3f normal : model.normals) {
                writer.write("vn " + normal.x + " " + normal.y + " " + normal.z + "\n");
            }

            // Записываем полигоны
            for (Polygon polygon : model.polygons) {
                writer.write("f ");
                List<Integer> vertexIndices = polygon.getVertexIndices();
                List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
                List<Integer> normalIndices = polygon.getNormalIndices();

                for (int i = 0; i < vertexIndices.size(); i++) {
                    String vertexData = (vertexIndices.get(i) + 1) + "/" +
                            (textureVertexIndices.size() > i ? textureVertexIndices.get(i) + 1 : "") + "/" +
                            (normalIndices.size() > i ? normalIndices.get(i) + 1 : "");

                    writer.write(vertexData + " ");
                }

                writer.write("\n");
            }

            System.out.println("Model successfully written to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to write the model to " + filePath);
        }
    }
}
