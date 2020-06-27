package graph;

import java.util.ArrayList;
import java.util.List;


public class Vertex {
    private int id;
    private boolean isVisited;
    private List<Vertex> adjacencyList;
    private int componentId;

    public Vertex(int id) {
        this.id = id;
        this.adjacencyList = new ArrayList<Vertex>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public List<Vertex> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    @Override
    public String toString() {
        return "Vertex [id=" + id + "]";
    }

    public void addNeighbour(Vertex vertex) {
        this.adjacencyList.add(vertex);
    }
}
