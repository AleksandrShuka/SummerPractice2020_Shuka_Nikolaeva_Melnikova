package graph;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, представляющий собой реализацию алгоритма поиска компонент
 * сильной связности.
 * <p>
 * Наследуется от класса {@code SwingWorker}.
 * <p>
 * Содержит в себе:
 *
 * @value MARK_EDGE - ребро помечено
 * @value UNMARK_EDGE - ребро не помечено
 * @value MARK_VISITED_VERTEX - вершина посещена
 * @value MARK_FINISHED_VERTEX - вершина вышла из DFS
 * @value UNMARK_VERTEX - вершина не помечена
 * @value TRANSPOSE_GRAPH - граф транспонирован
 * @value ALGORITHM_ENDED - алгоритм закончил работу
 * @value ADD_TEXT - добавлен текст, поясняющий ход выполнения алгоритма
 * @value MAX_DELAY - максимальная задержка анимации алгоритма
 * @value MIN_DELAY - минимальная задержка анимации алгоритма
 * @value DELTA_DELAY - шаг изменения задержки анимации алгоритма
 * @see SwingWorker
 * <p>
 * Поле для хранения графа {@code graph}, количество компонент сильной связности в графе {@code count} и
 * список вершин, расположенных в порядке убывания времени выхода при первом обходе графа {@code orderList}.
 * <p>
 * Содержит методы для реализации алгоритма поиска компонент сильной связности Косарайю.
 */

public class Algorithm extends SwingWorker<Void, Void> {
    public static final String MARK_EDGE = "markEdge";
    public static final String UNMARK_EDGE = "unmarkEdge";
    public static final String MARK_VISITED_VERTEX = "markVisitedVertex";
    public static final String MARK_FINISHED_VERTEX = "markFinishedVertex";
    public static final String UNMARK_VERTEX = "unmarkVertex";
    public static final String TRANSPOSE_GRAPH = "transposeGraph";
    public static final String ALGORITHM_ENDED = "algorithmEnded";
    public static final String ADD_TEXT = "addText";
    public static final String CLEAR_TEXT_PANE = "clearTextPane";
    public static final int MAX_DELAY = 3000;
    public static final int MIN_DELAY = 50;
    public static final int DELTA_DELAY = 50;

    private final AtomicBoolean isRun;
    private final AtomicInteger delay;

    /**
     * Граф, на котором будет реализован алгоритм.
     */
    private Graph graph;
    /**
     * Количество компонент сильной связности в графе {@code graph}.
     */
    private int count;
    /**
     * Список вершин, расположенных в порядке убывания времени выхода при первом обходе графа.
     */
    private final LinkedList<Vertex> orderList;

    /**
     * Конструктор, принимающий граф {@code graph}, к которому будет
     * применяться алгоритм.
     *
     * @param (graph) граф.
     */
    public Algorithm(@NotNull Graph graph) {
        this.isRun = new AtomicBoolean(true);
        this.delay = new AtomicInteger((MAX_DELAY + MIN_DELAY) / 2);
        this.graph = graph;
        this.orderList = new LinkedList<>();
    }

    /**
     * Перегруженный метод родительского класса
     *
     * @see SwingWorker#doInBackground()
     * Метод выполняет алгоритм в новом потоке.
     * <p>
     * Метод реализует алгоритм Косайрайю поиска копонент сильной связности в графе.
     * Последовательно запускает первый обход графа в глубину, затем транспонирование графа и второй обход графа в
     * глубину в порядке, определенном при первом обходе в глубину.
     * После завершения работы алгоритма, граф возвращается в исходное состояние.
     */
    @Override
    protected Void doInBackground() {
        try {
            firePropertyChange(CLEAR_TEXT_PANE, null, null);
            firePropertyChange(ADD_TEXT, null, "START ALGORITHM" + System.lineSeparator());

            if (graph.getVertexList().size() == 0) {
                firePropertyChange(ADD_TEXT, null, "Error: graph is empty!" + System.lineSeparator());
                return null;
            }

            firePropertyChange(ADD_TEXT, null, "FIRST DFS STARTED" + System.lineSeparator());
            for (Vertex vertex : graph.getVertexList()) {
                if (!vertex.isVisited()) {
                    sleepOrWait();
                    firePropertyChange(ADD_TEXT, null, " Start from " + vertex.getId() +
                            System.lineSeparator());
                    firstDFS(vertex);
                }
            }

            sleepOrWait();
            firePropertyChange(ADD_TEXT, null, System.lineSeparator() +
                    "List of vertexes in order of decreasing exit time: " + System.lineSeparator() +
                    orderListToString() + System.lineSeparator());

            unVisit(graph);
            transposeGraph();

            firePropertyChange(ADD_TEXT, null, System.lineSeparator() + "SECOND DFS STARTED" +
                    System.lineSeparator());
            for (Vertex vertex : orderList) {
                if (!vertex.isVisited()) {
                    sleepOrWait();
                    firePropertyChange(ADD_TEXT, null, " Start from " + vertex.getId() +
                            " (" + (count + 1) + " component)" + System.lineSeparator());
                    secondDFS(vertex);
                    ++count;
                }
            }

            unVisit(graph);
            transposeGraph();
            return null;
        } catch (Exception ignored) {

        }

        return null;
    }

    /**
     * Вызывает метод транспонирования графа
     *
     * @throws InterruptedException если поток был прерван.
     * @see Graph#getTransposedGraph()
     * и посылает сигнал родительскому потоку об изменении состояния графа.
     */
    private void transposeGraph() throws InterruptedException {
        sleepOrWait();
        firePropertyChange(ADD_TEXT, null, System.lineSeparator() +
                "GRAPH TRANSPOSE STARTED" + System.lineSeparator());

        sleepOrWait();
        graph = graph.getTransposedGraph();
        firePropertyChange(TRANSPOSE_GRAPH, null, null);
        firePropertyChange(ADD_TEXT, null,
                "All edges of the graph were oriented in the opposite direction." + System.lineSeparator() +
                        "GRAPH TRANSPOSED" + System.lineSeparator());

        sleepOrWait();
    }

    /**
     * Переопределенный метод родительского класса, который будет вызван при завершинии алгоритма.
     * Посылает сигнал о том, что алгоритм завершен.
     *
     * @see SwingWorker#done()
     */
    @Override
    protected void done() {
        try {
            get();
            firePropertyChange(ADD_TEXT, null, System.lineSeparator() + count +
                    " STRONGLY CONNECTED COMPONENTS FOUND " +
                    System.lineSeparator());
            firePropertyChange(ALGORITHM_ENDED, null, null);
        } catch (Exception e) {
            firePropertyChange(CLEAR_TEXT_PANE, null, null);
        }
    }

    /**
     * Метод, реализующий обход графа в глубину, с сохранением порядка вершин по убыванию времени выхода в
     * {@code orderList}, для последующего использования в алгоритме.
     *
     * @param vertex - вершина, к которой применяем обход в глубину.
     * @see Algorithm#doInBackground()
     */
    private void firstDFS(@NotNull Vertex vertex) throws InterruptedException {
        sleepOrWait();
        vertex.setVisited(true);
        firePropertyChange(MARK_VISITED_VERTEX, null, vertex);
        firePropertyChange(ADD_TEXT, null, "    " + vertex.getId() + " is visited" +
                System.lineSeparator());

        for (Vertex neighbour : vertex.getAdjacencyList()) {
            if (!neighbour.isVisited()) {
                sleepOrWait();
                firePropertyChange(ADD_TEXT, null,
                        "      go to " + neighbour.getId() + System.lineSeparator());
                firePropertyChange(MARK_EDGE, vertex, neighbour);
                firstDFS(neighbour);
            }
        }

        sleepOrWait();
        firePropertyChange(ADD_TEXT, null, " The vertex " + vertex.getId() +
                " is worked out " + System.lineSeparator());
        firePropertyChange(MARK_FINISHED_VERTEX, null, vertex);
        orderList.addFirst(vertex);
    }

    /**
     * Метод, реализующий обход графа в глубину.
     * При обходе сохраняет в вершины {@code vertex.setComponentId}, входящие в одну компоненту сильной связности,
     * соответствующий номер компоненты.
     *
     * @param vertex - вершина, к которой применяем обход в глубину
     */
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

        firePropertyChange(ADD_TEXT, null, " The vertex " + vertex.getId() +
                " is worked out " + System.lineSeparator());
    }

    /**
     * Устанавливает флаг статуса выполнения алгоритма.
     * Если {@code false}, то алгоритм останавливается.
     *
     * @param run флаг статуса выполнения алгоритма.
     * @see Algorithm#sleepOrWait()
     */
    public void setRun(boolean run) {
        isRun.set(run);
    }

    /**
     * Приостанавливает поток выполнения на {@code delay} мс, если {@code isRun == true}.
     * <p>
     * Если {@code isRun == false}, то поток переходит в режим ожидания сигнала
     *
     * @see Object#wait()
     * @see Algorithm#unSleep()
     */
    private synchronized void sleepOrWait() throws InterruptedException {
        if (isRun.get()) {
            Thread.sleep(delay.get());
        } else {
            wait();
        }
    }

    /**
     * Возвращает значение поля {@code graph}.
     *
     * @return граф, к которому применяется алгоритм.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Устанавливает флаг статуса выполнения алгоритма {@code isRun = true}.
     * Вызывает метод {@code notifyAll()} для продолжения выполнения алгоритма.
     *
     * @see Object#notifyAll()
     */
    public synchronized void unSleep() {
        isRun.set(true);
        notifyAll();
    }

    /**
     * Метод, изменяющий статус посещенности вершин графа на "не посещённые".
     * Отправляет сигнал об изменении состояния графа.
     *
     * @param graph граф, в котором меняется статус посещенности
     * @throws InterruptedException если поток был прерван
     */
    private void unVisit(@NotNull Graph graph) throws InterruptedException {
        sleepOrWait();
        firePropertyChange(ADD_TEXT, null, System.lineSeparator() +
                "All vertexes are marked as not visited." + System.lineSeparator());

        for (Vertex vertex : graph.getVertexList()) {
            firePropertyChange(UNMARK_VERTEX, null, vertex);
            vertex.setVisited(false);

            for (Vertex neighbour : vertex.getAdjacencyList()) {
                firePropertyChange(UNMARK_EDGE, vertex, neighbour);
            }
        }
    }

    private @NotNull String orderListToString() {
        StringBuilder string = new StringBuilder("[");

        for (int i = 0; i < orderList.size(); ++i) {
            string.append(orderList.get(i).getId());
            if (i != orderList.size() - 1) {
                string.append(", ");
            }
        }
        string.append("]");

        return string.toString();
    }

    /**
     * Увеличивает задержку анимации алгоритма на заданную константу {@code DELTA_DELAY}.
     */
    public void increaseDelay() {
        synchronized (this.delay) {
            if (delay.get() < MAX_DELAY - DELTA_DELAY) {
                delay.addAndGet(DELTA_DELAY);
            }
        }
    }

    /**
     * Уменьшает задержку анимации алгоритма на заданную константу {@code DELTA_DELAY}.
     */
    public void decreaseDelay() {
        synchronized (this.delay) {
            if (delay.get() - DELTA_DELAY > MIN_DELAY) {
                delay.addAndGet(-DELTA_DELAY);
            }
        }
    }
}
