package graph;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;


public class Algorithm {
    private final Graph graph;
    private int count;
    private final LinkedList<Vertex> orderList;

    public Algorithm(@NotNull Graph graph) {
        this.graph = graph;
        orderList = new LinkedList<>();
    }

    public void run() {
        unVisit(graph);
        for (Vertex vertex : graph.getVertexList()) {
            if (!vertex.isVisited()) {
                firstDFS(vertex);
            }
        }

        graph.transpose();

        unVisit(graph);
        for (Vertex vertex : orderList) {
            if (!vertex.isVisited()) {
                secondDFS(vertex);
                ++count;
            }
        }

        graph.transpose();
        unVisit(graph);
    }

    private void firstDFS(@NotNull Vertex vertex) {
        vertex.setVisited(true);

        for (Vertex neighbour : vertex.getAdjacencyList()) {
            if (!neighbour.isVisited()) {
                firstDFS(neighbour);
            }
        }

        orderList.push(vertex);
    }

    private void secondDFS(@NotNull Vertex vertex) {
        vertex.setVisited(true);
        vertex.setComponentId(count);

        for (Vertex neighbour : vertex.getAdjacencyList()) {
            if (!neighbour.isVisited()) {
                secondDFS(neighbour);
            }
        }
    }

    private void unVisit(@NotNull Graph graph) {
        for (Vertex vertex : graph.getVertexList()) {
            vertex.setVisited(false);
        }
    }

    public int getCount() {
        return this.count;
    }
}
