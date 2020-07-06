package graph;

import logger.Logs;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Logs.writeToLog("Created vertex number " + id);

        this.id = id;
        this.adjacencyList = new ArrayList<>();
    }

    /**
     * Возвращает {@code id} вершины.
     *
     * @return номер вершины.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает номер вершины {@code id}.
     *
     * @param id номер вершины.
     */
    public void setId(int id) {
        Logs.writeToLog("Vertex number changed from " + this.id + " to " + id);

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
     * Устанавливает значение статуса посещенности вершины {@code isVisited}.
     *
     * @param isVisited значение статуса посещенности вершины.
     */
    public void setVisited(boolean isVisited) {
        Logs.writeToLog("Vertex " + this.id + " is visited. Status changes from " + this.isVisited +
                " to " + isVisited);

        this.isVisited = isVisited;
    }

    /**
     * Возвращает список смежных вершин {@code adjacencyList}.
     *
     * @return список смежных вершин.
     */
    public List<Vertex> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Устанавливает список смежных ребер {@code adjacencyList}.
     *
     * @param adjacencyList список смежных вершин.
     */
    public void setAdjacencyList(@NotNull List<Vertex> adjacencyList) {
        Logs.writeToLog("Vertex number " + this.id + " added adjacent vertices " + adjacencyList.toString());

        this.adjacencyList = adjacencyList;
    }

    /**
     * Возвращает {@code componentId} вершины.
     *
     * @return номер компоненты сильной связности, к которой принадлежит вершина.
     */
    public int getComponentId() {
        return componentId;
    }

    /**
     * Устанавливает номер компоненты сильной связности {@code componentId}.
     *
     * @param componentId номер компоненты сильной связности.
     */
    public void setComponentId(int componentId) {
        Logs.writeToLog("Set component id " + componentId + " to vertex number " + this.id);

        this.componentId = componentId;
    }

    /**
     * Метод добавляет вершину {@code vertex} в список смежных вершин.
     *
     * @param vertex смежная вершина.
     */
    public void addNeighbour(@NotNull Vertex vertex) {
        Logs.writeToLog("Vertex number " + this.id + " added neighbour vertex " + vertex.id);
        this.adjacencyList.add(vertex);
    }

    /**
     * Возвращает строковое представление вершины {@code String}, которое содержит информацию о всех
     * ее полях.
     *
     * @return строка, содержащая информацию о вершине.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < adjacencyList.size(); ++i) {
            stringBuilder.append(adjacencyList.get(i).getId());

            if (i != adjacencyList.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");

        return "Vertex{" +
                "id=" + id +
                ", isVisited=" + isVisited +
                ", adjacencyList=" + stringBuilder +
                ", componentId=" + componentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id &&
                isVisited == vertex.isVisited &&
                componentId == vertex.componentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isVisited, componentId);
    }
}
