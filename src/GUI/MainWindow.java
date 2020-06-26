package GUI;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ext.JGraphXAdapter;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CommandPanel commandPanel;
    private ListenableGraph listenableGraph;
    private ScrollTextPane scrollPane;

    public MainWindow() {
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

        //FIX IT
        listenableGraph.testBuild();

        //FIX IT
        JGraphXAdapter<Integer, EmptyEdge> graphAdapter = new JGraphXAdapter<Integer, EmptyEdge>(listenableGraph);
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.setX0((double) dimension.width / 8);
        layout.setRadius((double) dimension.height / 5);
        layout.execute(graphAdapter.getDefaultParent());
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        graphComponent.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        graphComponent.setEnabled(false);

        //FIX IT
        add(graphComponent, BorderLayout.CENTER);

        add(commandPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
    }
}
