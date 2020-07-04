package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Класс, представляющий собой коммандную панель.
 * Наследуется от класса {@code JPanel}
 * @see JPanel
 * <p>
 * Содержит в себе экземпляры класса {@code JButton}:
 * @see JButton
 *
 * @value startButton - кнопка для запуска работы алгоритма
 * @value increaseSpeedButton - кнопка для увеличения скорости визуализаии
 * @value decreaseSpeedButton - кнопка для уменьшения скорости визуализации
 * @value addVertexButton - кнопка для добавления вершин
 * @value deleteButton - кнопка для удаления выбранной вершины/ребра
 * @value clearButton - кнопка для очищения окна
 * @value stopButton - кнопка для остановки работы алгоритма
 * @value pauseButton - кнопка, с помощью которой визуализацию можно поставить на паузу
 */

public class CommandPanel extends JPanel {
    private final JButton startButton;
    private final JButton increaseSpeedButton;
    private final JButton decreaseSpeedButton;
    private final JButton addVertexButton;
    private final JButton deleteButton;
    private final JButton clearButton;
    private final JButton stopButton;
    private final JButton pauseButton;

    /**
     * Конструктор панели, который инициализирует переменные,
     * устанавливает свойства и размещает компоненты на панели.
     */
    CommandPanel() {
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        setBackground(Colors.getSecondBackgroundColor());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon iconPlus = null;
        ImageIcon iconMinus = null;
        try {
            Image imagePlus = ImageIO.read(getClass().getResourceAsStream("/images/plus.png"));
            Image imageMinus = ImageIO.read(getClass().getResourceAsStream("/images/minus.png"));

            iconPlus = new ImageIcon(imagePlus.getScaledInstance(20,
                    20, java.awt.Image.SCALE_SMOOTH));

            iconMinus = new ImageIcon(imageMinus.getScaledInstance(20,
                    20, java.awt.Image.SCALE_SMOOTH));

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

        increaseSpeedButton.setEnabled(false);
        decreaseSpeedButton.setEnabled(false);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);

        startButton.setPreferredSize(new Dimension(0, (int) height / 20));
        stopButton.setPreferredSize(new Dimension(0, (int) height / 20));
        pauseButton.setPreferredSize(new Dimension(0, (int) height / 20));
        addVertexButton.setPreferredSize(new Dimension(0, (int) height / 20));
        deleteButton.setPreferredSize(new Dimension(0, (int) height / 20));
        clearButton.setPreferredSize(new Dimension(0, (int) height / 20));

        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) height / 15));
        stopButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) height / 15));
        pauseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) height / 15));
        addVertexButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) height / 15));
        deleteButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) height / 15));
        clearButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) height / 15));

        stopButton.setBackground(Colors.getFirstBackgroundColor());
        startButton.setBackground(Colors.getFirstBackgroundColor());
        increaseSpeedButton.setBackground(Colors.getFirstBackgroundColor());
        decreaseSpeedButton.setBackground(Colors.getFirstBackgroundColor());
        deleteButton.setBackground(Colors.getFirstBackgroundColor());
        addVertexButton.setBackground(Colors.getFirstBackgroundColor());
        clearButton.setBackground(Colors.getFirstBackgroundColor());
        pauseButton.setBackground(Colors.getFirstBackgroundColor());

        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.LINE_AXIS));

        increaseSpeedButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        decreaseSpeedButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        speedLabel.setBackground(Colors.getSecondBackgroundColor());
        speedPanel.setBackground(Colors.getSecondBackgroundColor());

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

    /**
     * Возвращает {@code addVertexButton}.
     *
     * @return кнопка для добавления вершин.
     */
    public JButton getAddVertexButton() {
        return addVertexButton;
    }

    /**
     * Возвращает {@code stopButton}.
     *
     * @return кнопка для остановки работы алгоритма.
     */
    public JButton getStopButton() {
        return stopButton;
    }

    /**
     * Возвращает {@code deleteButton}.
     *
     * @return кнопка для удаления выбранной вершины/ребра.
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Возвращает {@code clearButton}.
     *
     * @return кнопка для очищения окна.
     */
    public JButton getClearButton() {
        return clearButton;
    }

    /**
     * Возвращает {@code startButton}.
     *
     * @return кнопка для запуска работы алгоритма.
     */
    public JButton getStartButton() {
        return startButton;
    }

    /**
     * Возвращает {@code pauseButton}.
     *
     * @return кнопка, с помощью которой визуализацию можно поставить на паузу.
     */
    public JButton getPauseButton() {
        return pauseButton;
    }

    /**
     * Возвращает {@code decreaseSpeedButton}.
     *
     * @return кнопка для уменьшения скорости визуализации.
     */
    public JButton getDecreaseSpeedButton() {
        return decreaseSpeedButton;
    }

    /**
     * Возвращает {@code increaseSpeedButton}.
     *
     * @return кнопка для увеличения скорости визуализаии.
     */
    public JButton getIncreaseSpeedButton() {
        return increaseSpeedButton;
    }
}
