package graph;

import java.util.ArrayList;
import java.util.List;


public class Graph {
    private List<Vertex> vertexList;
    private List<Edge> edgeList;

    public Graph() {
        super();
    }

    public Graph(List<Vertex> vertexList, List<Edge> edgeList) {
        super();
        this.vertexList = vertexList;
        this.edgeList = edgeList;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public Graph getTransposedGraph() {
        Graph transposed = new Graph();

        List<Vertex> transposedVertexList = new ArrayList<>(this.vertexList);

        for (Edge edge : this.edgeList) {
            transposedVertexList.get(transposedVertexList.indexOf(edge.getTargetVertex()))
                    .addNeighbour(edge.getSourceVertex());
        }

        transposed.setVertexList(transposedVertexList);

        return transposed;
    }
}
