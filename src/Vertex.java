
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int id;
    private String name;
    private boolean isVisited;
    private List<Vertex> adjacenciesList;
    private int componentId;

    public Vertex(int id, String name) {
        this.id = id;
        this.name = name;
        this.adjacenciesList = new ArrayList<Vertex>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public List<Vertex> getAdjacenciesList() {
        return adjacenciesList;
    }

    public void setAdjacenciesList(List<Vertex> adjacenciesList) {
        this.adjacenciesList = adjacenciesList;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    @Override
    public String toString() {
        return "Vertex [name=" + name + "]";
    }

    public void addNeighbour(Vertex vertex) {
        this.adjacenciesList.add(vertex);
    }
}
