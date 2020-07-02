package logger;

import com.sun.tools.javac.Main;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/*
public class Logs {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void writeToLog(String message, Level level) {
        logger.log(level, message);
    }
}
*/

public class Logs {
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    private final static Handler handler = new ConsoleHandler();

    private Logs() {
    }

    static {
        logger.setLevel(Level.ALL);

        handler.setLevel(Level.ALL);

        handler.setFormatter(new Forms());

        logger.setUseParentHandlers(false);
        logger.addHandler(handler);

    }

    public static Level getLevel() {
        return logger.getLevel();
    }

    public static void setHandlerLevel(Level level) {
        handler.setLevel(level);
    }

    public static void setLevel(Level level) {
        logger.setLevel(level);
    }

    public static void writeToLog (String message) {
        logger.log(logger.getLevel(), message);
    }

    public static void writeToLog(String message, Level level) {
        logger.log(level, message);
    }

    static class Forms extends Formatter {
        @Override
        public String format(LogRecord record){
            return record.getLevel() + ":" + record.getMessage() + "\n";
        }
    }

}
