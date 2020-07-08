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
 * А также методы, возвращающие значение полей:
 * {@code getHelp}, {@code getAbout}, (@code getOpen}
 */
public class MenuBar extends JMenuBar {
    private final JMenuItem open;
    private final JMenuItem exit;
    private final JMenuItem sort;
    private final JMenuItem help;
    private final JMenuItem about;

    /**
     * Конструктор, устанавливающий фоновый цвет строке меню, инициализирующий поля класса и
     * и добавляющий два экземпляра класса JMenu {@code add()} на строку меню.
     */
    public MenuBar() {
        setBackground(Colors.getFirstBackgroundColor());

        sort = new JMenuItem("Sort graph");
        open = new JMenuItem("Open");
        exit = new JMenuItem(new ExitAction());
        help = new JMenuItem("Help");
        about = new JMenuItem("About");

        add(createFileMenu());
        add(createHelpMenu());
    }


    /**
     * Метод, создающий {@code file} экземпляр класса JMenu. Добавляет в него {@code open}, {@code exit}.
     * @see JMenu
     *
     * @return меню File
     */
    private @NotNull JMenu createFileMenu() {
        JMenu file = new JMenu("File");

        file.add(open);
        file.add(sort);
        file.addSeparator();
        file.add(exit);

        return file;
    }

    /**
     * Метод, создающий {@code help} экземпляр класса JMenu. Добавляет в него {@code help}, {@code about}.
     * @see JMenu
     *
     * @return меню Help.
     */
    private @NotNull JMenu createHelpMenu() {
        JMenu help = new JMenu("Help");

        help.add(this.help);
        help.add(about);

        return help;
    }

    /**
     * Метод, возвращающий значение поля {@code open}.
     *
     * @return кнопка open.
     */
    public JMenuItem getOpen() {
        return open;
    }

    /**
     * Метод, возвращающий значение поля {@code help}.
     *
     * @return кнопка kelp.
     */
    public JMenuItem getHelp() {
        return help;
    }

    /**
     * Метод, возвращающий значение поля {@code about}.
     *
     * @return кнопка about.
     */
    public JMenuItem getAbout() {
        return about;
    }

    /**
     * Класс-наследник AbstractAction, используется при инициализации кнопки {@code exit}.
     * @see AbstractAction
     */
    private static class ExitAction extends AbstractAction {
        ExitAction() {
            putValue(NAME, "Exit");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public JMenuItem getSort() {
        return sort;
    }
}
