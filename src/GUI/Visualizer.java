package GUI;

import javax.swing.*;
import java.awt.*;

public class Visualizer {
    private JFrame jFrame;
    private int windowWidth;
    private int windowHeight;

    public Visualizer(int windowWidth, int windowHeight) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setBounds((dimension.width - windowWidth) / 2, (dimension.height - windowHeight) / 2,
                windowWidth, windowHeight);
    }

    public void setVisible(boolean visible) {
        jFrame.setVisible(visible);
    }
}
