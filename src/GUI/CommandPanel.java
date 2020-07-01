package GUI;

import javax.swing.*;
import java.awt.*;


public class CommandPanel extends JPanel {
    private final JButton startButton;
    private final JButton addVertexButton;
    private final JButton deleteButton;
    private final JButton clearButton;
    private final JButton stopButton;

    CommandPanel() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout(0, 1, 0, 40));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        stopButton = new JButton("stop");
        startButton = new JButton("start");
        addVertexButton = new JButton("add vertex");
        deleteButton = new JButton("delete");
        clearButton = new JButton("clear");

        stopButton.setEnabled(false);

        add(startButton);
        add(stopButton);
        add(addVertexButton);
        add(deleteButton);
        add(clearButton);
    }

    public JButton getAddVertexButton() {
        return addVertexButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getStartButton() {
        return startButton;
    }
}
