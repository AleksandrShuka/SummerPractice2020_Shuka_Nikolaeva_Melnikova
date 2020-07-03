package GUI;

import javax.swing.*;
import java.awt.*;


public class ScrollTextPane extends JScrollPane {
    private final JTextArea textArea;

    ScrollTextPane() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setBackground(new Color(0xB0B0BB));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setViewportView(textArea);
        textArea.setBackground(new Color(0xE6E6FA));
    }

    public void addText(String text) {
        textArea.setText(text);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
