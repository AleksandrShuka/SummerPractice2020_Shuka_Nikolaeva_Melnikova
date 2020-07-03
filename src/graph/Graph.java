package graph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий собой граф.
 * Содержит в себе список верщшин {@code vertexList} и
 * список ребер {@code edgeList}
 * <p>
 * Класс предоставляет методы для получения значений полей класса:
 * {@code getVertexList}, {@code getEdgeList}.
 * <p>
 * Класс предоставляет методы для установки значений полей класса:
 * {@code setVertexList}, {@code setEdgeList}.
 * <p>
 * Класс предоставляет метод для транспонирования графа {@code transpose}.
 */

public class Graph {
    /**
     * Список вершин графа.
     */
    private List<Vertex> vertexList;
    /**
     * Список ребер графа.
     */
    private List<Edge> edgeList;

    /**
     * Конструктор для создания пустого графа.
     */
    public Graph() {
        super();
    }

    /**
     * Конструктор графа, принимающий список вершин {@code vertexList} и
     * список ребер {@code edgeList}.
     *
     * @param vertexList список вершин.
     * @param edgeList   список ребер.
     */
    public Graph(List<Vertex> vertexList, @NotNull List<Edge> edgeList) {
        super();
        this.vertexList = vertexList;
        this.edgeList = edgeList;

        for (Edge edge : edgeList) {
            vertexList.get(vertexList.indexOf(edge.getSourceVertex())).addNeighbour(edge.getTargetVertex());
        }
    }

    /**
     * Возвращает список вершин графа {@code vertexList}.
     *
     * @return список вершин графа.
     */
    public List<Vertex> getVertexList() {
        return vertexList;
    }

    /**
     * Устанавливает список вершин {@code vertexList}.
     *
     * @param vertexList список вершин.
     */
    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    /**
     * Возвращает список ориентированных ребер графа {@code edgeList}.
     *
     * @return список ребер графа.
     */
    public List<Edge> getEdgeList() {
        return edgeList;
    }

    /**
     * Устанавливает список ребер {@code edgeList}.
     *
     * @param edgeList список ребер
     */
    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    /**
     * Транспонирует граф: ребра исходного графа ориентируются в противоположном направлении.
     */
    public void transpose() {
        List<Vertex> transposedVertexList = new ArrayList<>(this.vertexList);

        for (Vertex vertex : transposedVertexList) {
            vertex.getAdjacencyList().clear();
        }

        for (Edge edge : this.edgeList) {
            transposedVertexList.get(transposedVertexList.indexOf(edge.getTargetVertex()))
                    .addNeighbour(edge.getSourceVertex());

            Vertex tmpSourceVertex = edge.getSourceVertex();
            edge.setSourceVertex(edge.getTargetVertex());
            edge.setTargetVertex(tmpSourceVertex);
        }

        setVertexList(transposedVertexList);
    }
}
