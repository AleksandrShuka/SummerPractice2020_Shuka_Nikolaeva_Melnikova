package GUI;

import javax.swing.*;
import java.awt.*;

public class ScrollTextPane extends JScrollPane {
    private final JTextArea textArea;

    ScrollTextPane() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setViewportView(textArea);
    }

    public void updateText(String text) {
        textArea.setText(text);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
