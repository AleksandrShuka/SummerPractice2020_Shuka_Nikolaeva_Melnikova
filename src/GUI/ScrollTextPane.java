package GUI;

import javax.swing.*;

public class ScrollTextPane extends JScrollPane {
    private JTextArea textArea;

    ScrollTextPane() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        //FIX
        textArea.setText("HELLO");

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setViewportView(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
