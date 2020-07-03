package graph;

import logger.Logs;

/**
 * Класс, представляющий собой ориентированное ребро графа.
 * Содержит в себе информацию о начальной {@code sourceVertex} и конечной вершине {@code targetVertex}.
 * <p>
 * Класс предоставляет методы для получения значений полей класса:
 * {@code getSourceVertex}, {@code getTargetVertex}.
 * <p>
 * Класс предоставляет методы для установки значений полей класса:
 * {@code setSourceVertex}, {@code setTargetVertex}.
 */

public class Edge {
    /**
     * Начальная вершина.
     */
    private Vertex sourceVertex;

    /**
     * Конечная вершина.
     */
    private Vertex targetVertex;

    /**
     * Конструктор ориентированного ребра, принимающий начальную вершину {@code startVertex} и
     * конечную вершину{@code targetVertex}.
     *
     * @param (startVertex,targetVertex) начальная и конечная вершина.
     */
    public Edge(Vertex startVertex, Vertex targetVertex) {
        Logs.writeToLog("Created edge from vertex " + startVertex.getId() + " to vertex " +
                        targetVertex.getId());

        this.sourceVertex = startVertex;
        this.targetVertex = targetVertex;
    }

    /**
     * Возвращает начальную вершину {@code sourceVertex}.
     *
     * @return начальная вершина ребра.
     */
    public Vertex getSourceVertex() {
        return sourceVertex;
    }

    /**
     * Устанавливает начальную вершину {@code sourceVertex}.
     *
     * @param sourceVertex начальная вершина.
     */
    public void setSourceVertex(Vertex sourceVertex) {
        Logs.writeToLog("Source vertex of " + this.toString() + " changed to " + sourceVertex);

        this.sourceVertex = sourceVertex;
    }

    /**
     * Возвращает конечную вершину {@code targetVertex} .
     *
     * @return конечная вершина ребра.
     */
    public Vertex getTargetVertex() {
        return targetVertex;
    }

    /**
     * Устанавливает конечную вершину {@code targetVertex}.
     *
     * @param targetVertex конечная вершина.
     */
    public void setTargetVertex(Vertex targetVertex) {
        Logs.writeToLog("Target vertex of " + this.toString() + " changed to " + targetVertex);

        this.targetVertex = targetVertex;
    }

    /**
     * Возвращает строковое представление ребра {@code String}, которое содержит информацию о всех
     * его полях.
     *
     * @return строка, содержащая информацию о ребре.
     */
    @Override
    public String toString() {
        return "Edge{" +
                "source=" + sourceVertex +
                ", target=" + targetVertex +
                '}';
    }
}
