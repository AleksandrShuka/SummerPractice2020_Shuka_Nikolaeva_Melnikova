package GUI;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MenuBar extends JMenuBar {
    private final JMenuItem open;
    private final JMenuItem exit;
    private final JMenuItem help;
    private final JMenuItem about;

    public MenuBar() {
        open = new JMenuItem("Open");
        exit = new JMenuItem(new ExitAction());
        help = new JMenuItem("Help");
        about = new JMenuItem("About");

        add(createFileMenu());
        add(createHelpMenu());
    }

    private @NotNull JMenu createFileMenu() {
        JMenu file = new JMenu("File");

        file.add(open);
        file.addSeparator();
        file.add(exit);

        return file;
    }

    private @NotNull JMenu createHelpMenu() {
        JMenu help = new JMenu("Help");

        help.add(this.help);
        help.add(about);

        return help;
    }

    public JMenuItem getOpen() {
        return open;
    }

    private static class ExitAction extends AbstractAction {
        ExitAction() {
            putValue(NAME, "Exit");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
