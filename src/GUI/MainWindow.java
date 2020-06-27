package GUI;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import logger.Logs;
import org.jgrapht.ext.JGraphXAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;


public class MainWindow extends JFrame {
    private CommandPanel commandPanel;
    private ListenableGraph listenableGraph;
    private ScrollTextPane scrollPane;
    private mxGraphComponent graphComponent;
    private mxCircleLayout layout;
    private JGraphXAdapter<Integer, EmptyEdge> graphAdapter;

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
        listenableGraph = new ListenableGraph();
        scrollPane = new ScrollTextPane();
        graphAdapter = new JGraphXAdapter<>(listenableGraph);
        layout = new mxCircleLayout(graphAdapter);

        layout.setX0((double) dimension.width / 8);
        layout.setRadius((double) dimension.height / 5);
        layout.execute(graphAdapter.getDefaultParent());

        graphComponent = new mxGraphComponent(graphAdapter);
        graphComponent.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        //  graphComponent.setEnabled(false);
        graphAdapter.setAllowDanglingEdges(false);

        graphAdapter.addListener(mxEvent.CELLS_ADDED, (o, eventObject) -> {
            mxCell source = (mxCell) eventObject.getProperty("source");
            mxCell target = (mxCell) eventObject.getProperty("target");
            if (source != null && target != null) {
                listenableGraph.addEdge((int) source.getValue(), (int) target.getValue());
            }
        });

        graphAdapter.setCellsSelectable(false);

        add(graphComponent, BorderLayout.CENTER);
        add(commandPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);

        commandPanel.getAddVertexButton().addActionListener(new AddVertexButtonActionListener());
        commandPanel.getDeleteVertexButton().addActionListener(new DeleteVertexButtonActionListener());
        commandPanel.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listenableGraph.getEdge(0, 1) != null) {
                    Logs.writeToLog("WOW", Level.INFO);
                }
            }
        });
    }

    class AddVertexButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listenableGraph.newVertex();
            layout.execute(graphAdapter.getDefaultParent());
        }
    }

    class DeleteVertexButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listenableGraph.deleteVertex();
            layout.execute(graphAdapter.getDefaultParent());
        }
    }
}