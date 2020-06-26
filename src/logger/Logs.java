package logger;

import com.sun.tools.javac.Main;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Logs {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void writeToLog(String message, Level level) {
        logger.log(level, message);
    }
}
