package GUI;

import javax.swing.*;
import java.awt.*;


public class CommandPanel extends JPanel {
    private final JButton startButton;
    private final JButton addVertexButton;
    private final JButton deleteEdgeButton;
    private final JButton deleteVertexButton;

    CommandPanel() {
        setLayout(new GridLayout(0, 1, 0, 40));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        startButton = new JButton("start");
        addVertexButton = new JButton("add vertex");
        deleteEdgeButton = new JButton("delete edge");
        deleteVertexButton = new JButton("delete vertex");

        add(startButton);
        add(addVertexButton);
        add(deleteEdgeButton);
        add(deleteVertexButton);
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
