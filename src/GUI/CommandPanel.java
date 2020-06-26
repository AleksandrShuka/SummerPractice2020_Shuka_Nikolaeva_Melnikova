package GUI;

import javax.swing.*;
import java.awt.*;


public class CommandPanel extends JPanel {
    private JButton startButton;
    private JButton addEdgeButton;
    private JButton addVertexButton;
    private JButton deleteEdgeButton;
    private JButton deleteVertexButton;

    CommandPanel() {
        setLayout(new GridLayout(0, 1, 0, 40));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        startButton = new JButton("start");
        addVertexButton = new JButton("add vertex");
        addEdgeButton = new JButton("add edge");
        deleteEdgeButton = new JButton("delete edge");
        deleteVertexButton = new JButton("delete vertex");

        add(startButton);
        add(addEdgeButton);
        add(addVertexButton);
        add(deleteEdgeButton);
        add(deleteVertexButton);
    }

    public JButton getAddEdgeButton() {
        return addEdgeButton;
    }

    public JButton getAddVertexButton() {
        return addVertexButton;
    }

    public JButton getDeleteEdgeButton() {
        return deleteEdgeButton;
    }

    public JButton getDeleteVertexButton() {
        return deleteVertexButton;
    }

    public JButton getStartButton() {
        return startButton;
    }
}
