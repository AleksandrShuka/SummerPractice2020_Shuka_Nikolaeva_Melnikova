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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;


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
        width = 2 * dimension.width / 3;
        height = 2 * dimension.height / 3;

        setBounds(dimension.width / 6, dimension.height / 6, width, height);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        menuBar = new MenuBar();
        commandPanel = new CommandPanel();
        scrollTextPane = new ScrollTextPane();
        graph = new Graph();
        layout = new mxCircleLayout(graph);
        graphComponent = new mxGraphComponent(graph);

        initGraph();
        initCommandPanel();
        initScrollTextPane();

        setJMenuBar(menuBar);
        add(scrollTextPane);
        add(graphComponent);
        add(commandPanel);
    }

    private void initScrollTextPane() {
        scrollTextPane.getTextArea().setFocusable(false);
        scrollTextPane.setMaximumSize(new Dimension((int) (0.3 * width), height));
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

        graphComponent.setMaximumSize(new Dimension((int) (0.55 * width), height));
    }

    private void initCommandPanel() {
        commandPanel.getIncreaseSpeedButton().addActionListener(e -> algorithm.decreaseDelay());

        commandPanel.getDecreaseSpeedButton().addActionListener(e -> algorithm.increaseDelay());

        commandPanel.getStopButton().addActionListener(e -> {
            algorithm.cancel(true);
            algorithm = new Algorithm(createGraph());
        });

        commandPanel.getAddVertexButton().addActionListener(e -> {
            graph.insertVertex();
            executeGraph();
        });

        commandPanel.getClearButton().addActionListener(e -> graph.clear());

        commandPanel.getPauseButton().addActionListener(e -> {
            algorithm.setRun(false);
            isPaused = true;
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
            } else {
                algorithm = new Algorithm(createGraph());
                algorithm.addPropertyChangeListener(evt -> {
                    if (evt.getPropertyName().equals(Algorithm.TRANSPOSE_GRAPH)) {
                        graph.transpose();
                    }
                });
            }

            algorithm.execute();
        });

        commandPanel.setMaximumSize(new Dimension((int) (0.15 * width), height));
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