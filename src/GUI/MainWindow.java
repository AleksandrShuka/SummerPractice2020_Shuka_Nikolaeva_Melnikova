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
    private boolean isPaused;
    private CommandPanel commandPanel;
    private ScrollTextPane scrollTextPane;
    private mxGraphComponent graphComponent;
    private MenuBar menuBar;
    private Algorithm algorithm;
    private mxCircleLayout layout;
    private Graph graph;
    private int height;
    private int width;

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
        layout = new mxCircleLayout(graph);
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
    }

    private void initGraph() {
        layout.setX0(((double) width) / 20);
        layout.setY0(((double) height) / 30);
        graphComponent.setBackground(Color.LIGHT_GRAY);
        graphComponent.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mxSwingConstants.VERTEX_SELECTION_COLOR = new Color(0xEEEEEE);

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
                    elem.setStyle(mxConstants.STYLE_FILLCOLOR + "=#00FF00");
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
            algorithm = new Algorithm(createGraph());

            setButtonsStateWhenStop();
        });

        commandPanel.getAddVertexButton().addActionListener(e -> {
            graph.insertVertex();
            executeGraph();
        });

        commandPanel.getClearButton().addActionListener(e -> graph.clear());

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
                algorithm = new Algorithm(createGraph());
                initAlgorithm();
            }

            commandPanel.getDecreaseSpeedButton().setEnabled(true);
            commandPanel.getIncreaseSpeedButton().setEnabled(true);
            commandPanel.getPauseButton().setEnabled(true);
            commandPanel.getStopButton().setEnabled(true);

            commandPanel.getAddVertexButton().setEnabled(false);
            commandPanel.getClearButton().setEnabled(false);
            commandPanel.getDeleteButton().setEnabled(false);
            commandPanel.getStartButton().setEnabled(false);

            algorithm.execute();
        });

        commandPanel.setMaximumSize(new Dimension(width / 7, height));
    }

    private void initAlgorithm() {
        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.TRANSPOSE_GRAPH)) {
                graph.transpose();
            }
        });

        algorithm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Algorithm.ALGORITHM_ENDED)) {
                for (Vertex vertex : algorithm.getGraph().getVertexList()) {
                    graph.paintVertex(vertex.getId(), Colors.get(vertex.getComponentId()));
                }

                setButtonsStateWhenStop();
            }
        });

//        algorithm.addPropertyChangeListener(evt -> {
//            if (evt.getPropertyName().equals(Algorithm...)) {
//                scrollTextPane.addText(evt.getNewValue().toString());
//            }
//        });

    }

    private void setButtonsStateWhenStop() {
        commandPanel.getIncreaseSpeedButton().setEnabled(false);
        commandPanel.getDecreaseSpeedButton().setEnabled(false);
        commandPanel.getStopButton().setEnabled(false);
        commandPanel.getPauseButton().setEnabled(false);

        commandPanel.getAddVertexButton().setEnabled(true);
        commandPanel.getClearButton().setEnabled(true);
        commandPanel.getDeleteButton().setEnabled(true);
        commandPanel.getStartButton().setEnabled(true);
    }

    private void executeGraph() {
        graph.setCellsMovable(true);
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