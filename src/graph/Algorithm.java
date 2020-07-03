package graph;

import logger.Logs;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.LinkedList;


public class Algorithm extends SwingWorker<Void, Void> {
    public static final String MARK_EDGE = "markEdge";
    public static final String UNMARK_EDGE = "unmarkEdge";
    public static final String MARK_VERTEX = "markVertex";
    public static final String UNMARK_VERTEX = "unmarkVertex";
    public static final String TRANSPOSE_GRAPH = "transposeGraph";
    public static final int MAX_DELAY = 3000;
    public static final int MIN_DELAY = 50;
    public static final int DELTA_DELAY = 50;

    private volatile Boolean isRun;
    private volatile Integer delay;

    private final Graph graph;
    private int count;
    private final LinkedList<Vertex> orderList;

    public Algorithm(@NotNull Graph graph) {
        this.isRun = true;
        this.delay = (MAX_DELAY + MIN_DELAY) / 2;
        this.graph = graph;
        this.orderList = new LinkedList<>();
    }

    @Override
    protected Void doInBackground() throws Exception {
        unVisit(graph);
        for (Vertex vertex : graph.getVertexList()) {
            if (!vertex.isVisited()) {
                firstDFS(vertex);
            }
        }

        for (int i = 0; i < 1000; ++i) {
            Logs.writeToLog(Integer.toString(i));
            sleepOrWait();
            //      graph.transpose();
            //     firePropertyChange(TRANSPOSE_GRAPH, null, null);
        }

        graph.transpose();
        firePropertyChange(TRANSPOSE_GRAPH, null, null);

        unVisit(graph);
        for (Vertex vertex : orderList) {
            if (!vertex.isVisited()) {
                secondDFS(vertex);
                ++count;
            }
        }

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

    public synchronized void setRun(boolean run) {
        isRun = run;
    }

    private synchronized void sleepOrWait() throws InterruptedException {
        if (isRun) {
            Thread.sleep(delay);
        } else {
            wait();
        }
    }

    public synchronized void unSleep() {
        isRun = true;
        Logs.writeToLog("notify");
        notifyAll();
    }

    private void unVisit(@NotNull Graph graph) {
        for (Vertex vertex : graph.getVertexList()) {
            vertex.setVisited(false);
        }
    }

    public int getCount() {
        return this.count;
    }

    public synchronized void increaseDelay() {
        if (delay < MAX_DELAY - DELTA_DELAY) {
            delay += DELTA_DELAY;
        }
    }

    public synchronized void decreaseDelay() {
        if (delay - DELTA_DELAY > MIN_DELAY) {
            delay -= DELTA_DELAY;
        }
    }
}
