package GUI;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import graph.Algorithm;
import graph.Edge;
import graph.Vertex;
import logger.Logs;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

/**
 * Класс основного окна. Наследуется от JFrame
 *
 * @see JFrame
 * <p>
 * Содержит в себе: алгоритм {@code algorithm}, коммандную панель {@code commandPanel}, граф {@code graph},
 * меню {@code menuBar}, текстовое поле {@code scrollTextPane}, {@code layout},
 * {@code graphComponent}, длину {@code height} и ширину {@code width}.
 * <p>
 * Имеет метод инициализации {@code init}, а также методы инициализации меню {@code initMenuBar}, текстового
 * поля {@code initScrollTextPane}, графа {@code initGraph}, коммандной панели {@code initCommandPanel} и
 * алгоритма {@code initAlgorithm}.
 * Содержит метод для установки состояния кнопкам при остановке {@code setButtonsStateWhenStop}
 */
public class MainWindow extends JFrame {
    private boolean isRun;
    /**
     * Алгоритм.
     */
    private Algorithm algorithm;
    /**
     * Коммандная панель.
     */
    private CommandPanel commandPanel;
    /**
     * Граф.
     */
    private Graph graph;
    /**
     * Меню.
     */
    private MenuBar menuBar;
    /**
     * Прокручивающееся текстовое поле.
     */
    private ScrollTextPane scrollTextPane;
    /**
     * Поле, значением которого будет true в случае, когда выполнение поставлено на паузу.
     */
    private boolean isPaused;
    /**
     * Высота окна.
     */
    private int height;
    /**
     * Ширина окна.
     */
    private int width;
    private mxCircleLayout layout;
    private mxGraphComponent graphComponent;

    /**
     * Конструктор. Вызывает метод инициализации {@code init}.
     */
    public MainWindow() {
        init();
    }

    /**
     * Метод инициализации. Инициализирует поля класса, устанавливает минимальный размер окна.
     * Вызывает методы инициализации графа, коммандной панели, меню и текстового поля.
     * Устанавливает меню, добавляет текстовое поле, команндную панель.
     */
    private void init() {
        setTitle("Kosaraju's algorithm visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = dimension.width;
        height = dimension.height;

        isRun = false;

        setBounds(width / 6, height / 6, 2 * width / 3, 2 * height / 3);
        setMinimumSize(new Dimension(2 * width / 3, height / 2));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        menuBar = new MenuBar();
        commandPanel = new CommandPanel();
        scrollTextPane = new ScrollTextPane();
        graph = new Graph();
        graphComponent = new mxGraphComponent(graph);

        initGraph();
        initCommandPanel();
        initMenuBar();
        initScrollTextPane();

        setJMenuBar(menuBar);
        add(scrollTextPane);
        add(graphComponent);
        add(commandPanel);
    }

    /**
     * Метод инициализации меню. Добавляет слушателей для кнопок Open, About, Help.
     */
    private void initMenuBar() {
        menuBar.getOpen().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                try (FileReader fileReader = new FileReader(fileChooser.getSelectedFile())) {
                    Scanner scanner = new Scanner(fileReader);

                    Set<Pair<Integer, Integer>> edgeSet = new HashSet<>();

                    int count = scanner.nextInt();

                    if (count > Colors.size() || count < 0) {
                        throw new IndexOutOfBoundsException("Incorrect count of vertexes");
                    }

                    while (scanner.hasNext()) {
                        int source = scanner.nextInt();
                        if (source >= Colors.size() || source < 0) {
                            throw new IndexOutOfBoundsException("Incorrect source vertex: " + source);
                        }
                        int target = scanner.nextInt();
                        if (target >= Colors.size() || target < 0) {
                            throw new IndexOutOfBoundsException("Incorrect target vertex: " + target);
                        }
                        edgeSet.add(new Pair<>(source, target));
                    }

                    graph.createGraph(count, edgeSet);
                    executeGraph();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    scrollTextPane.getTextArea().setText("Can't read data from file");
                }
            }
        });

        menuBar.getAbout().addActionListener(e -> {
            Scanner scanner = new Scanner(getClass().getResourceAsStream("/HTML/about.html"));
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            JOptionPane.showMessageDialog(this,
                    sb.toString(), "About", JOptionPane.PLAIN_MESSAGE);
        });

        menuBar.getHelp().addActionListener(e -> {
            JEditorPane editorPane = null;
            try {
                editorPane = new JEditorPane(getClass().getResource("/HTML/help.html"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            assert editorPane != null;
            editorPane.setEditable(false);
            editorPane.setMaximumSize(new Dimension(width / 2, height / 2));

            JScrollPane scrollPane = new JScrollPane(editorPane);
            scrollPane.setPreferredSize(new Dimension(width / 2, 2 * height / 3));
            editorPane.setBackground(new Color(0xEEEEEE));
            JOptionPane.showMessageDialog(this,
                    scrollPane, "Help", JOptionPane.PLAIN_MESSAGE);
        });

        menuBar.getSort().addActionListener(e -> layout.execute(graph.getDefaultParent()));
    }

    /**
     * Метод инициализации текстового поля. Задает свойства поля.
     */
    private void initScrollTextPane() {
        scrollTextPane.getTextArea().setFocusable(false);
        scrollTextPane.setMaximumSize(new Dimension(width / 5, height));
        scrollTextPane.setMinimumSize(new Dimension(width / 5, height));
        scrollTextPane.setPreferredSize(new Dimension(width / 5, height));
    }

    /**
     * Метод инициализации графа. Добавляет слушателей. Задает свойства graphComponent (цвет, размер, границы).
     */
    private void initGraph() {
        layout = new mxCircleLayout(graph);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int radius = Math.min(graphComponent.getHeight(), graphComponent.getWidth()) / 2 - 40;
                layout.setRadius(radius);
                layout.setX0(graphComponent.getWidth() / 2.0 - radius - 30);
                layout.setY0(graphComponent.getHeight() / 2.0 - radius - 30);
                executeGraph();
            }
        });

        graphComponent.getViewport().setBackground(Colors.getSecondBackgroundColor());
        graphComponent.setBackground(Colors.getSecondBackgroundColor());
        graphComponent.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mxSwingConstants.VERTEX_SELECTION_COLOR = Colors.getSecondBackgroundColor();

        graph.getSelectionModel().addListener(mxEvent.CHANGE, (o, mxEventObject) -> {
            ArrayList<mxCell> list = (ArrayList<mxCell>) mxEventObject.getProperty("added");
            if (list != null) {
                for (mxCell elem : list) {
                    elem.setStyle(mxConstants.STYLE_FILLCOLOR + "=#FFFFFF");
                }
            }

            list = (ArrayList<mxCell>) mxEventObject.getProperties().get("removed");
            if (list != null) {
                for (mxCell elem : list) {
                    elem.setStyle(mxConstants.STYLE_FILLCOLOR + "=#DAA520");
                }
            }

            graph.refresh();
            graph.repaint();
        });

        graphComponent.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Метод, инициализирующий коммандную панель. Задает свойства коммандной панели. Добавляет слушателей для
     * кнопки увеличения/уменьшения скорости, остановки, добавления вершины, отчистки графа, паузы, удаления, старта.
     */
    private void initCommandPanel() {
        commandPanel.getProgressBar().setMinimum(0);
        commandPanel.getProgressBar().setMaximum(Algorithm.MAX_DELAY + Algorithm.DELTA_DELAY);
        commandPanel.getProgressBar().setValue((Algorithm.MAX_DELAY +
                Algorithm.MIN_DELAY + Algorithm.DELTA_DELAY) / 2);

        commandPanel.getIncreaseSpeedButton().addActionListener(e -> {
            if (isRun || isPaused) {
                algorithm.decreaseDelay();
            }
            int value = commandPanel.getProgressBar().getValue();
            if (value + Algorithm.DELTA_DELAY <= Algorithm.MAX_DELAY) {
                commandPanel.getProgressBar().setValue(value + Algorithm.DELTA_DELAY);
            }
        });

        commandPanel.getDecreaseSpeedButton().addActionListener(e -> {
            if (isRun || isPaused) {
                algorithm.increaseDelay();
            }
            int value = commandPanel.getProgressBar().getValue();
            if (value - Algorithm.DELTA_DELAY > Algorithm.MIN_DELAY) {
                commandPanel.getProgressBar().setValue(value - Algorithm.DELTA_DELAY);
            }
        });

        commandPanel.getStopButton().addActionListener(e -> {
            scrollTextPane.getTextArea().setText("");
            for (PropertyChangeListener listener : algorithm.getPropertyChangeSupport().getPropertyChangeListeners()) {
                algorithm.removePropertyChangeListener(listener);
            }
            algorithm.cancel(true);
            graphComponent.setEnabled(true);
            graph.resetVertexValues();
            isPaused = false;
            isRun = false;
            graph.load();
            setButtonsStateWhenStop(true);
            executeGraph();
        });

        commandPanel.getAddVertexButton().addActionListener(e -> {
            graph.insertVertex();
            executeGraph();
        });

        commandPanel.getClearButton().addActionListener(e -> {
            graph.clear();
            scrollTextPane.getTextArea().setText("");
            executeGraph();
        });

        commandPanel.getDeleteButton().addActionListener(e -> {
            Object cell = graph.getSelectionCell();
            if (graph.getModel().isVertex(cell)) {
                graph.deleteVertex((Integer) graph.getModel().getValue(cell));
            } else if (graph.getModel().isEdge(cell)) {
                graph.removeCells();
            }
            executeGraph();
        });

        commandPanel.getStartPauseButton().addActionListener(e -> {
            if (!isRun) {
                commandPanel.getStartPauseButton().setText("Pause");
                isRun = true;
                if (isPaused) {
                    algorithm.setDelay(Algorithm.MAX_DELAY - commandPanel.getProgressBar().getValue() + 50);
                    algorithm.unSleep();
                    graphComponent.setEnabled(false);
                    isPaused = false;
                } else {
                    graph.paintDefault();
                    graph.save();
                    graphComponent.setEnabled(false);
                    graph.setSelectionCells(new Object[]{});
                    algorithm = new Algorithm(createGraph(),
                            Algorithm.MAX_DELAY - commandPanel.getProgressBar().getValue() + 50);
                    initAlgorithm();
                    algorithm.execute();
                }
                setButtonsStateWhenStop(false);
            } else {
                commandPanel.getStartPauseButton().setText("Start");
                isRun = false;
                graphComponent.setEnabled(true);
                algorithm.setRun(false);
                isPaused = true;
            }
        });

        commandPanel.setMaximumSize(new Dimension(width / 7, height));
    }

    /**
     * Метод, инициализирующий алгоритм. Добавляет слушателей для следующих событий:
     * транспонирвоание графа, окончание работы алгоритма, добавление текста, помечания отработанной вершины, ребра,
     * отчистки текстового поля.
     */
    private void initAlgorithm() {
        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.TRANSPOSE_GRAPH)) {
                graph.transpose();
                executeGraph();
            }
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.ALGORITHM_ENDED)) {
                for (Vertex vertex : algorithm.getGraph().getVertexList()) {
                    graph.paintVertex(vertex.getId(), Colors.get(vertex.getComponentId()));
                }
                isPaused = false;
                isRun = false;
                setButtonsStateWhenStop(true);
                graphComponent.setEnabled(true);
                executeGraph();
            }
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.ADD_TEXT)) {
                scrollTextPane.getTextArea().append(evt.getNewValue().toString());
            }
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.MARK_VISITED_VERTEX)) {
                if (evt.getOldValue() != null) {
                    graph.paintVertex(((Vertex) evt.getNewValue()).getId(), Colors.get((Integer) evt.getOldValue()));
                } else {
                    graph.paintVertex(((Vertex) evt.getNewValue()).getId(), "#DAA520");
                }
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.MARK_FINISHED_VERTEX)) {
                graph.paintVertex(((Vertex) evt.getNewValue()).getId(), "#B8860B");
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.UNMARK_VERTEX)) {
                graph.paintVertex(((Vertex) evt.getNewValue()).getId(), "#FFFFFF");
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.MARK_VISITED_EDGE)) {
                graph.paintEdge(((Vertex) evt.getOldValue()).getId(),
                        ((Vertex) evt.getNewValue()).getId(), "#B8860B");
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.MARK_UNVISITED_EDGE)) {
                graph.paintEdge(((Vertex) evt.getOldValue()).getId(),
                        ((Vertex) evt.getNewValue()).getId(), "#FFDEAD");
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.UNMARK_EDGE)) {
                graph.paintEdge(((Vertex) evt.getOldValue()).getId(),
                        ((Vertex) evt.getNewValue()).getId(), "#000000");
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.SET_VERTEX_VALUE)) {
                Pair<Integer, Integer> pair = (Pair<Integer, Integer>) evt.getNewValue();
                graph.setVertexValue(pair.first, pair.second);
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.RESET_VERTEX_VALUES)) {
                graph.resetVertexValues();
            }
            executeGraph();
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.CLEAR_TEXT_PANE)) {
                scrollTextPane.getTextArea().setText("");
            }
        });
    }

    /**
     * Метод, устанавливающий статус кнопкам во время остановки.
     *
     * @param isStop {@code true}, если работа алгоритма остановлена.
     */
    private void setButtonsStateWhenStop(boolean isStop) {
        commandPanel.getStopButton().setEnabled(!isStop);

        commandPanel.getAddVertexButton().setEnabled(isStop);
        commandPanel.getClearButton().setEnabled(isStop);
        commandPanel.getDeleteButton().setEnabled(isStop);

        if (isStop) {
            commandPanel.getStartPauseButton().setText("Start");
        } else {
            commandPanel.getStartPauseButton().setText("Pause");
        }
    }

    /**
     * Метод, перерисовывающий граф.
     */
    private void executeGraph() {
        graph.refresh();
        graph.repaint();
    }

    /**
     * Метод создания алгоритмического графа на основе визуализированного.
     *
     * @return алгоритмический граф.
     */
    @Contract(" -> new")
    private graph.@NotNull Graph createGraph() {
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        LinkedList<Vertex> vertexList = new LinkedList<>();
        LinkedList<Edge> edgeList = new LinkedList<>();

        for (Object cell : graph.getAllVertex()) {
            int source = (Integer) ((mxCell) cell).getValue();
            Vertex vertex = new Vertex(source);
            vertexList.add(vertex);
            vertexMap.put(source, vertex);
        }

        for (Object cell : graph.getAllVertex()) {
            int source = (Integer) ((mxCell) cell).getValue();

            for (Object edge : graph.getOutgoingEdges(cell)) {
                int target = (Integer) ((mxCell) edge).getTarget().getValue();
                edgeList.add(new Edge(vertexMap.get(source), vertexMap.get(target)));
            }
        }

        return new graph.Graph(vertexList, edgeList);
    }
}