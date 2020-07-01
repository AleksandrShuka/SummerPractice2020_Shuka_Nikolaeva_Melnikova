package GUI;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    {
        JMenuItem item1 = new JMenuItem("Open...");
        JMenuItem item4 = new JMenuItem("Exit");

        JMenuItem item2 = new JMenuItem("Help");
        JMenuItem item3 = new JMenuItem("About");

        JMenu menu1 = new JMenu("Help");
        JMenu menu2 = new JMenu("File");

        menu2.add(item1);
        menu2.add(item4);

        menu1.add(item2);
        menu1.add(item3);

        add(menu2);
        add(menu1);
    }
}
