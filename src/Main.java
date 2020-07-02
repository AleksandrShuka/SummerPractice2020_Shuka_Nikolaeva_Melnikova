import GUI.MainWindow;
import logger.Logs;

import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        Logs.writeToLog("Start program", Level.INFO);
        Logs.setLogLevelForOutput(Level.INFO);
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
