package graph;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.LinkedList;


public class Algorithm extends SwingWorker<Void, Void> {
    public static final String MARK_EDGE = "markEdge";
    public static final String UNMARK_EDGE = "unmarkEdge";
    public static final String MARK_VERTEX = "markVertex";
    public static final String UNMARK_VERTEX = "unmarkVertex";
    public static final String TRANSPOSE_GRAPH = "transposeGraph";

    private final Graph graph;
    private int count;
    private final LinkedList<Vertex> orderList;

    public Algorithm(@NotNull Graph graph) {
        this.graph = graph;
        orderList = new LinkedList<>();
    }

    @Override
    protected Void doInBackground() throws Exception {
        unVisit(graph);
        for (Vertex vertex : graph.getVertexList()) {
            if (!vertex.isVisited()) {
                firstDFS(vertex);
            }
        }

        Thread.sleep(1000);
        graph.transpose();
        firePropertyChange(TRANSPOSE_GRAPH, null, null);

        unVisit(graph);
        for (Vertex vertex : orderList) {
            if (!vertex.isVisited()) {
                secondDFS(vertex);
                ++count;
            }
        }

        Thread.sleep(1000);
        firePropertyChange(TRANSPOSE_GRAPH, null, null);
        graph.transpose();

        unVisit(graph);

        return null;
    }

    private void firstDFS(@NotNull Vertex vertex) {
        vertex.setVisited(true);

        for (Vertex neighbour : vertex.getAdjacencyList()) {
            if (!neighbour.isVisited()) {
                firstDFS(neighbour);
            }
        }

        orderList.addFirst(vertex);
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
