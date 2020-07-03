package graph;

import logger.Logs;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Algorithm extends SwingWorker<Void, Void> {
    public static final String MARK_EDGE = "markEdge";
    public static final String UNMARK_EDGE = "unmarkEdge";
    public static final String MARK_VERTEX = "markVertex";
    public static final String UNMARK_VERTEX = "unmarkVertex";
    public static final String TRANSPOSE_GRAPH = "transposeGraph";
    public static final int MAX_DELAY = 3000;
    public static final int MIN_DELAY = 50;
    public static final int DELTA_DELAY = 50;

    private final AtomicBoolean isRun;
    private final AtomicInteger delay;

    private Graph graph;
    private int count;
    private final LinkedList<Vertex> orderList;

    public Algorithm(@NotNull Graph graph) {
        this.isRun = new AtomicBoolean(true);
        this.delay = new AtomicInteger((MAX_DELAY + MIN_DELAY) / 2);
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
            graph = graph.getTransposedGraph();
            firePropertyChange(TRANSPOSE_GRAPH, null, null);
        }

        graph = graph.getTransposedGraph();
        firePropertyChange(TRANSPOSE_GRAPH, null, null);

        unVisit(graph);
        for (Vertex vertex : orderList) {
            if (!vertex.isVisited()) {
                secondDFS(vertex);
                ++count;
            }
        }

        firePropertyChange(TRANSPOSE_GRAPH, null, null);
        graph = graph.getTransposedGraph();

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

    public void setRun(boolean run) {
        isRun.set(run);
    }

    private synchronized void sleepOrWait() throws InterruptedException {
        while (!isRun.get()) {
            wait();
        }

        if (isRun.get()) {
            Thread.sleep(delay.get());
        }
    }

    public synchronized void unSleep() {
        isRun.set(true);
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

    public void increaseDelay() {
        synchronized (this.delay) {
            if (delay.get() < MAX_DELAY - DELTA_DELAY) {
                delay.addAndGet(DELTA_DELAY);
            }
        }
    }

    public void decreaseDelay() {
        synchronized (this.delay) {
            if (delay.get() - DELTA_DELAY > MIN_DELAY) {
                delay.addAndGet(-DELTA_DELAY);
            }
        }
    }
}
