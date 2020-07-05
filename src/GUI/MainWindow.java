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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class MainWindow extends JFrame {
    private Algorithm algorithm;
    private CommandPanel commandPanel;
    private Graph graph;
    private MenuBar menuBar;
    private ScrollTextPane scrollTextPane;
    private boolean isPaused;
    private int height;
    private int width;
    private mxCircleLayout layout;
    private mxGraphComponent graphComponent;

    public MainWindow() {
        init();
    }

    private void init() {
        setTitle("Kosaraju's algorithm visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = dimension.width;
        height = dimension.height;

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

    //FIX
    private void initMenuBar() {
        menuBar.getOpen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ДОБАВИТЬ ВВОД ИЗ ФАЙЛА
            }
        });
    }

    private void initScrollTextPane() {
        scrollTextPane.getTextArea().setFocusable(false);
        scrollTextPane.setMaximumSize(new Dimension(width / 5, height));
        scrollTextPane.setMinimumSize(new Dimension(width / 5, height));
        scrollTextPane.setPreferredSize(new Dimension(width / 5, height));
    }

    private void initGraph() {
        layout = new mxCircleLayout(graph);
        layout.setX0(((double) width) / 20);
        layout.setY0(((double) height) / 30);
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

    private void initCommandPanel() {
        commandPanel.getIncreaseSpeedButton().addActionListener(e -> algorithm.decreaseDelay());

        commandPanel.getDecreaseSpeedButton().addActionListener(e -> algorithm.increaseDelay());

        commandPanel.getStopButton().addActionListener(e -> {
            algorithm.cancel(true);
            graphComponent.setEnabled(true);
            isPaused = false;
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
            commandPanel.getStartButton().setEnabled(true);
            scrollTextPane.getTextArea().setText("");
            executeGraph();
        });

        commandPanel.getPauseButton().addActionListener(e -> {
            algorithm.setRun(false);
            isPaused = true;

            commandPanel.getDecreaseSpeedButton().setEnabled(false);
            commandPanel.getIncreaseSpeedButton().setEnabled(false);
            commandPanel.getPauseButton().setEnabled(false);
            commandPanel.getStartButton().setEnabled(true);
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

        commandPanel.getStartButton().addActionListener(e -> {
            if (isPaused) {
                algorithm.unSleep();
                isPaused = false;
            } else {
                graph.paintDefault();
                graph.save();
                graphComponent.setEnabled(false);
                graph.setSelectionCells(new Object[]{});
                algorithm = new Algorithm(createGraph());
                initAlgorithm();
                algorithm.execute();
            }
            setButtonsStateWhenStop(false);
        });

        commandPanel.setMaximumSize(new Dimension(width / 7, height));
    }

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
            if (evt.getPropertyName().equals(Algorithm.MARK_EDGE)) {
                graph.paintEdge(((Vertex) evt.getOldValue()).getId(),
                        ((Vertex) evt.getNewValue()).getId(), "#B8860B");
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
            if (evt.getPropertyName().equals(Algorithm.CLEAR_TEXT_PANE)) {
                scrollTextPane.getTextArea().setText("");
            }
        });
    }

    private void setButtonsStateWhenStop(boolean isStop) {
        commandPanel.getIncreaseSpeedButton().setEnabled(!isStop);
        commandPanel.getDecreaseSpeedButton().setEnabled(!isStop);
        commandPanel.getStopButton().setEnabled(!isStop);
        commandPanel.getPauseButton().setEnabled(!isStop);

        commandPanel.getAddVertexButton().setEnabled(isStop);
        commandPanel.getClearButton().setEnabled(isStop);
        commandPanel.getDeleteButton().setEnabled(isStop);
        commandPanel.getStartButton().setEnabled(isStop);
    }

    private void executeGraph() {
        graph.setCellsMovable(true);
        graph.refresh();
        graph.repaint();
        layout.execute(graph.getDefaultParent());
        graph.setCellsMovable(false);
    }

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