package graph;

import logger.Logs;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;


public class Algorithm extends SwingWorker<Void, Void> {
    public static final String MARK_EDGE = "markEdge";
    public static final String UNMARK_EDGE = "unmarkEdge";
    public static final String MARK_VISITED_VERTEX = "markVisitedVertex";
    public static final String MARK_FINISHED_VERTEX = "markFinishedVertex";
    public static final String UNMARK_VERTEX = "unmarkVertex";
    public static final String TRANSPOSE_GRAPH = "transposeGraph";
    public static final String ALGORITHM_ENDED = "algorithmEnded";
    public static final String ADD_TEXT = "addText";
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
    protected Void doInBackground() {
        unVisit(graph);
        firePropertyChange(ADD_TEXT, null, "FIRST DFS STARTED:" + System.lineSeparator());
        sleepOrWait();

        for (Vertex vertex : graph.getVertexList()) {
            if (!vertex.isVisited()) {
                firePropertyChange(ADD_TEXT, null, " Start from " + vertex.getId() +
                        System.lineSeparator());
                firstDFS(vertex);

                firePropertyChange(ADD_TEXT, null, " The vertex "  + vertex.getId() +
                        " is worked out " + System.lineSeparator());
            }

        }

        firePropertyChange(ADD_TEXT, null, System.lineSeparator() +
                "All vertexes are marked as not visited." + System.lineSeparator() +
                System.lineSeparator() + "SECOND DFS STARTED:" + System.lineSeparator());
        unVisit(graph);
        sleepOrWait();


        StringBuilder string = new StringBuilder();
        for (Vertex t:orderList){
            string.append(t.getId() + " ");
        }
        firePropertyChange(ADD_TEXT, null, System.lineSeparator() + string +
                System.lineSeparator() + System.lineSeparator() + "GRAPH TRANSPOSE STARTED" + System.lineSeparator() +
                "All edges of the graph were oriented in the opposite direction." + System.lineSeparator() +
                "GRAPH TRANSPOSED" + System.lineSeparator());
                           
        graph = graph.getTransposedGraph();
        firePropertyChange(TRANSPOSE_GRAPH, null, null);
        sleepOrWait();

        for (Vertex vertex : orderList) {
            if (!vertex.isVisited()) {
                firePropertyChange(ADD_TEXT, null, " Start from " + vertex.getId() +
                        " (" + (count + 1) + " component)" + System.lineSeparator());

                secondDFS(vertex);
                ++count;
            }
        }

        firePropertyChange(ADD_TEXT, null, System.lineSeparator() + count +
                " strongly connected components found " +
                System.lineSeparator());

        graph = graph.getTransposedGraph();
        firePropertyChange(TRANSPOSE_GRAPH, null, null);
        sleepOrWait();
        unVisit(graph);

        return null;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            firePropertyChange(ALGORITHM_ENDED, null, null);
        }
    }

    private void firstDFS(@NotNull Vertex vertex) {
        vertex.setVisited(true);
        firePropertyChange(MARK_VISITED_VERTEX, null, vertex);
        firePropertyChange(ADD_TEXT, null, "    " + vertex.getId() + " is visited" +
                System.lineSeparator());
        sleepOrWait();

        for (Vertex neighbour : vertex.getAdjacencyList()) {
            if (!neighbour.isVisited()) {
                firePropertyChange(ADD_TEXT, null,
                        "      go to " + neighbour.getId() + System.lineSeparator());
                firePropertyChange(MARK_EDGE, vertex, neighbour);
                sleepOrWait();
                firstDFS(neighbour);
            }
        }

        firePropertyChange(MARK_FINISHED_VERTEX, null, vertex);
        sleepOrWait();
        orderList.addFirst(vertex);
    }

    private void secondDFS(@NotNull Vertex vertex) {
        vertex.setVisited(true);
        vertex.setComponentId(count);
        firePropertyChange(ADD_TEXT, null, "    " + vertex.getId() + " is visited " +
                System.lineSeparator());

        for (Vertex neighbour : vertex.getAdjacencyList()) {
            if (!neighbour.isVisited()) {
                firePropertyChange(ADD_TEXT, null,
                        "      go to " + neighbour.getId() + System.lineSeparator());
                secondDFS(neighbour);
            }
        }
    }

    public void setRun(boolean run) {
        isRun.set(run);
    }

    private synchronized void sleepOrWait() {
        while (!isRun.get()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (isRun.get()) {
            try {
                Thread.sleep(delay.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public synchronized void unSleep() {
        isRun.set(true);
        notifyAll();
    }

    private void unVisit(@NotNull Graph graph) {
        for (Vertex vertex : graph.getVertexList()) {
            firePropertyChange(UNMARK_VERTEX, null, vertex);
            vertex.setVisited(false);

            for (Vertex neighbour : vertex.getAdjacencyList()) {
                firePropertyChange(UNMARK_EDGE, vertex, neighbour);
            }
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
