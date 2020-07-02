package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class CommandPanel extends JPanel {
    private final JButton startButton;
    private final JButton increaseSpeedButton;
    private final JButton decreaseSpeedButton;
    private final JButton addVertexButton;
    private final JButton deleteButton;
    private final JButton clearButton;
    private final JButton stopButton;
    private final JButton pauseButton;

    CommandPanel() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon iconPlus = null;
        ImageIcon iconMinus = null;
        try {
            Image imagePlus = ImageIO.read(new File("resources\\images\\plus.png"));
            Image imageMinus = ImageIO.read(new File("resources\\images\\minus.png"));

            iconPlus = new ImageIcon(imagePlus.getScaledInstance(25,
                    25, java.awt.Image.SCALE_SMOOTH));

            iconMinus = new ImageIcon(imageMinus.getScaledInstance(25,
                    25, java.awt.Image.SCALE_SMOOTH));

        } catch (IOException e) {
            e.printStackTrace();
        }

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        addVertexButton = new JButton("Add vertex");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        JLabel speedLabel = new JLabel("Speed:");
        increaseSpeedButton = new JButton(iconPlus);
        decreaseSpeedButton = new JButton(iconMinus);

        startButton.setAlignmentX(CENTER_ALIGNMENT);
        pauseButton.setAlignmentX(CENTER_ALIGNMENT);
        stopButton.setAlignmentX(CENTER_ALIGNMENT);
        addVertexButton.setAlignmentX(CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(CENTER_ALIGNMENT);
        clearButton.setAlignmentX(CENTER_ALIGNMENT);

        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        stopButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        pauseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        addVertexButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        deleteButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        clearButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));

        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.LINE_AXIS));

        increaseSpeedButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        decreaseSpeedButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        speedLabel.setBackground(Color.LIGHT_GRAY);
        speedPanel.setBackground(Color.LIGHT_GRAY);

        speedPanel.add(speedLabel);
        speedPanel.add(Box.createHorizontalGlue());
        speedPanel.add(decreaseSpeedButton);
        speedPanel.add(Box.createHorizontalGlue());
        speedPanel.add(increaseSpeedButton);
        speedPanel.add(Box.createHorizontalGlue());

        add(startButton);
        add(Box.createVerticalGlue());
        add(pauseButton);
        add(Box.createVerticalGlue());
        add(stopButton);
        add(Box.createVerticalGlue());
        add(speedPanel);
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        add(addVertexButton);
        add(Box.createVerticalGlue());
        add(deleteButton);
        add(Box.createVerticalGlue());
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

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getDecreaseSpeedButton() {
        return decreaseSpeedButton;
    }

    public JButton getIncreaseSpeedButton() {
        return increaseSpeedButton;
    }
}
