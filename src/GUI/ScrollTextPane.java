package GUI;

import javax.swing.*;

/**
 * Класс, представляющий собой прокручиваемую область,
 * на которую помещается {@code textArea}.
 * Наследуется от класса {@code JScrollPane}.
 *
 * @value textArea - текстовая панель
 * @see JScrollPane
 * <p>
 * Содержит в себе экземпляр класса {@code JTextArea}:
 * @see JTextArea
 */

public class ScrollTextPane extends JScrollPane {
    private final JTextArea textArea;

    /**
     * Конструктор панели, который инициализирует переменные,
     * устанавливает свойства и размещает компоненты на панели.
     */
    ScrollTextPane() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setBackground(Colors.getSecondBackgroundColor());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setViewportView(textArea);
        textArea.setBackground(Colors.getFirstBackgroundColor());
    }

    /**
     * Возвращает {@code textArea}.
     *
     * @return текстовая панель.
     */
    public JTextArea getTextArea() {
        return textArea;
    }
}
