package GUI;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Класс, представляющий собой строку меню.
 * Наследуется от {@code JMenuBar}.
 *
 * Содержит в себе экземпляры класса {@code JMenuItem}:
 * @value open - кнопка для выбора файла для ввода графа из файла.
 * @value exit - кнопка для выхода из приложения.
 * @value help - кнопка для вызова справки.
 * @value about - кнопка для вызова информации о программе.
 *
 * Содержит методы для создания меню File {@code createFileMenu} и меню Help {@code createHelpMenu}.
 */
public class MenuBar extends JMenuBar {
    private final JMenuItem open;
    private final JMenuItem exit;
    private final JMenuItem help;
    private final JMenuItem about;

    /**
     * Конструктор, устанавливающий фоновый цвет строке меню, инициализирующий поля класса и
     * и добавляющий два экземпляра класса JMenu {@code add()} на строку меню.
     */
    public MenuBar() {
        setBackground(Colors.getFirstBackgroundColor());

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

    public JMenuItem getHelp() {
        return help;
    }

    public JMenuItem getAbout() {
        return about;
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
