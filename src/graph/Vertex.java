package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий собой вершину графа.
 * Содержит в себе информацию о номере {@code id} вершины,
 * посещенности {@code isVisited}, о смежных вершинах {@code adjacencyList},
 * и о номере компоненты сильной связности {@code componentId}, которой принадлежит вершина.
 * <p>
 * Класс предоставляет методы для получения значений полей класса:
 * {@code getId}, {@code isVisited}, {@code getAdjacencyList}, {@code getComponentId}.
 * <p>
 * Класс предоставляет методы для установки значений полей класса:
 * {@code setId}, {@code setVisited}, {@code setAdjacencyList}, {@code setComponentId}.
 * <p>
 * Класс предоставляет метод для добавления смежных вершин {@code addNeighbour}.
 */

public class Vertex {
    /**
     * Номер вершины.
     */
    private int id;

    /**
     * Статус посещенности вершины.
     */
    private boolean isVisited;

    /**
     * Список смежных вершин.
     */
    private List<Vertex> adjacencyList;

    /**
     * Номер компоненты сильной связности, к которой принадлежит вершина.
     */
    private int componentId;

    /**
     * Конструктор вершины, принимающий ее номер {@code id}.
     *
     * @param id номер вершины.
     */
    public Vertex(int id) {
        this.id = id;
        this.adjacencyList = new ArrayList<>();
    }

    /**
     * Возвращает {@code id} вершины.
     *
     * @return {@code id}.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id номер вершины.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает {@code true}, если вершина была помечена.
     *
     * @return {@code true}, если вершина была помечена.
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * @param isVisited значение статуса посещенности вершины.
     */
    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    /**
     * Возвращает список смежных вершин {@code adjacencyList}.
     *
     * @return {@code adjacencyList}.
     */
    public List<Vertex> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * @param adjacencyList список смежных вершин.
     */
    public void setAdjacencyList(List<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    /**
     * Возвращает {@code componentId} вершины.
     *
     * @return {@code componentId}.
     */
    public int getComponentId() {
        return componentId;
    }

    /**
     * @param componentId номер компоненты сильной связности, к которой принадлежит вершина.
     */
    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    /**
     * Метод добавляет вершину {@code vertex} в список смежных вершин.
     *
     * @param vertex смежная вершина.
     */
    public void addNeighbour(Vertex vertex) {
        this.adjacencyList.add(vertex);
    }

    /**
     * Возвращает строковое представление вершины, которое содержит информацию о всех
     * ее полях.
     *
     * @return {@code String}.
     */
    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", isVisited=" + isVisited +
                ", adjacencyList=" + adjacencyList +
                ", componentId=" + componentId +
                '}';
    }
}
