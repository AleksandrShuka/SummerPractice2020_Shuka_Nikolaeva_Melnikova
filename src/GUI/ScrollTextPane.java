package GUI;

import javax.swing.*;


public class ScrollTextPane extends JScrollPane {
    private final JTextArea textArea;

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

    public JTextArea getTextArea() {
        return textArea;
    }
}
