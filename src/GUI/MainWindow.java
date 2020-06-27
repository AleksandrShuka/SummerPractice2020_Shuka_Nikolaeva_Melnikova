package GUI;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ext.JGraphXAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
        graphComponent.setEnabled(false);

        add(graphComponent, BorderLayout.CENTER);
        add(commandPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);

        commandPanel.getAddVertexButton().addActionListener(new AddVertexButtonActionListener());
        commandPanel.getDeleteVertexButton().addActionListener(new DeleteVertexButtonActionListener());
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
