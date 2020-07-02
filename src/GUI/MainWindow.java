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
    private ScrollTextPane scrollPane;
    private mxGraphComponent graphComponent;
    private MenuBar menuBar;
    private Algorithm algo;
    private mxCircleLayout layout;
    private Graph graph;

    public MainWindow() {
        init();
    }

    private void init() {
        setTitle("Kosaraju's algorithm visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int width = 2 * dimension.width / 3;
        int height = 2 * dimension.height / 3;

        setBounds(dimension.width / 6, dimension.height / 6,
                width, height);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        menuBar = new MenuBar();
        commandPanel = new CommandPanel();
        scrollPane = new ScrollTextPane();
        graph = new Graph();
        layout = new mxCircleLayout(graph);

        layout.setX0(((double) width) / 20);
        layout.setY0(((double) height) / 30);

        graphComponent = new mxGraphComponent(graph);
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

        commandPanel.getIncreaseSpeedButton().addActionListener(e -> algo.setSpeed(algo.getSpeed() - 50));

        commandPanel.getDecreaseSpeedButton().addActionListener(e -> algo.setSpeed(algo.getSpeed() + 50));

        commandPanel.getStopButton().addActionListener(e -> {
            algo.cancel(true);
            algo = new Algorithm(createGraph());
        });

        commandPanel.getAddVertexButton().addActionListener(e -> {
            graph.insertVertex();
            executeGraph();
        });

        commandPanel.getClearButton().addActionListener(e -> graph.clear());

        commandPanel.getPauseButton().addActionListener(e -> {
            algo.setRun(false);
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
                algo.unSleep();
            } else {
                algo = new Algorithm(createGraph());
                algo.addPropertyChangeListener(evt -> {
                    if (evt.getPropertyName().equals(Algorithm.TRANSPOSE_GRAPH)) {
                        graph.transpose();
                    }
                });
            }

            algo.execute();
        });

        setJMenuBar(menuBar);

        scrollPane.setMaximumSize(new Dimension(30 * width / 100, height));
        graphComponent.setMaximumSize(new Dimension(55 * width / 100, height));
        commandPanel.setMaximumSize(new Dimension(15 * width / 100, height));

        add(scrollPane);
        add(graphComponent);
        add(commandPanel);
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