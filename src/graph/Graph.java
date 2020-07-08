package graph;

import logger.Logs;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий собой граф.
 * Содержит в себе список вершин {@code vertexList} и
 * список ребер {@code edgeList}
 * <p>
 * Класс предоставляет методы для получения значений полей класса:
 * {@code getVertexList}, {@code getEdgeList}.
 * <p>
 * Класс предоставляет методы для установки значений полей класса:
 * {@code setVertexList}, {@code setEdgeList}.
 * <p>
 * Класс предоставляет метод для получения транспонированного графа {@code getTransposedGraph}.
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
    public Graph(@NotNull List<Vertex> vertexList, @NotNull List<Edge> edgeList) {
        super();
        Logs.writeToLog("Created graph with vertexes: " + vertexList.toString() +
                System.lineSeparator() + "edges:" + edgeList.toString());

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
    public void setVertexList(@NotNull List<Vertex> vertexList) {
        Logs.writeToLog("Added list of vertexes to graph: " + vertexList.toString());

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
    public void setEdgeList(@NotNull List<Edge> edgeList) {
        Logs.writeToLog("Added list of edges to graph: " + edgeList.toString());

        this.edgeList = edgeList;
    }

    /**
     * Создает транспонированный граф (с тем же набором вершин и с теми же ребрами, что и у исходного,
     * но ориентация ребер этого графа противоположна ориентации ребер исходного графа)
     *
     * @return - транспонированный граф.
     */
    public Graph getTransposedGraph() {
        Logs.writeToLog("Created transposed graph with vertexes: " + vertexList.toString() +
                System.lineSeparator() + "edges:" + edgeList.toString());

        List<Vertex> newVertexList = new ArrayList<>();
        for (Vertex vertex : vertexList) {
            Vertex newVertex = new Vertex(vertex.getId());
            newVertex.setVisited(vertex.isVisited());
            newVertex.setComponentId(vertex.getComponentId());
            newVertexList.add(newVertex);
        }

        List<Edge> newEdgeList = new ArrayList<>();
        for (Edge edge : edgeList) {
            Vertex sourceVertex = null;
            Vertex targetVertex = null;

            for (Vertex vertex : newVertexList) {
                if (vertex.getId() == edge.getTargetVertex().getId()) {
                    sourceVertex = vertex;
                }
                if (vertex.getId() == edge.getSourceVertex().getId()) {
                    targetVertex = vertex;
                }
            }

            assert targetVertex != null;
            assert sourceVertex != null;
            Edge newEdge = new Edge(sourceVertex, targetVertex);
            newEdgeList.add(newEdge);
        }

        return new Graph(newVertexList, newEdgeList);
    }
}
