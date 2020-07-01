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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;


public class MainWindow extends JFrame {
    private CommandPanel commandPanel;
    private ScrollTextPane scrollPane;
    private mxGraphComponent graphComponent;
    private mxCircleLayout layout;
    private Graph graph;

    public MainWindow() {
        init();
    }

    private void init() {
        setTitle("Kosaraju's algorithm visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(dimension.width / 6, dimension.height / 6,
                2 * dimension.width / 3, 2 * dimension.height / 3);
        setResizable(false);
        setLayout(new BorderLayout());

        commandPanel = new CommandPanel();
        scrollPane = new ScrollTextPane();
        graph = new Graph();
        layout = new mxCircleLayout(graph);

        layout.setX0((double) dimension.width / 8);
        layout.setY0(20.0);
        layout.setRadius((double) dimension.height / 5);

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setBackground(Color.LIGHT_GRAY);
        graphComponent.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mxSwingConstants.VERTEX_SELECTION_COLOR = new Color(0xEEEEEE);

        graph.getSelectionModel().addListener(mxEvent.CHANGE, (o, mxEventObject) -> {
            ArrayList<mxCell> list = (ArrayList<mxCell>) mxEventObject.getProperty("added");

            if (list.size() == 0) {
                list = (ArrayList<mxCell>) mxEventObject.getProperties().get("removed");
                list.get(0).setStyle(mxConstants.STYLE_FILLCOLOR + "=" + "#00FF00");
            } else {
                list.get(0).setStyle(mxConstants.STYLE_FILLCOLOR + "=" + "#FFFFFF");
            }

            graph.refresh();
            graph.repaint();
        });

        commandPanel.getAddVertexButton().addActionListener(e -> {
            graph.insertVertex();
            executeGraph();
        });

        commandPanel.getClearButton().addActionListener(e -> graph.clear());

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
            Algorithm algo = new Algorithm(createGraph());
            algo.addPropertyChangeListener(evt -> {
                if (evt.getPropertyName().equals(Algorithm.TRANSPOSE_GRAPH)) {
                    graph.transpose();
                }
            });

            algo.execute();
        });

        add(graphComponent, BorderLayout.CENTER);
        add(commandPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
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