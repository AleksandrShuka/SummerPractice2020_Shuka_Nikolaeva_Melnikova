package logger;

import com.sun.tools.javac.Main;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
public class Logs {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void writeToLog(String message, Level level) {
        logger.log(level, message);
    }
}
*/

/**
 * Класс, реализующий логирование.
 * Содержит в себе:
 * @value logger - экземпляр класса Logger.
 * @value handler - экзэмпляр класса Handler, экспортирующий сообщения логера на консоль.
 * <p>
 * Класс предоставляет методы для получения значения уровня логирования, хранящегося в поле класса {@code logger}:
 * {@code getLevel} и для установки уровня {@code setLevel}.
 * <p>
 * Класс предоставляет метод для установки уровня логирования, ниже которого сообщения
 * не будут выводиться в консоль {@code setLevelOfOutput}, {@code setEdgeList}.
 * <p>
 * Класс предоставляет методы для записи сообщения в log {@code writeToLog}.
 */

public class Logs {
    /**
     * Логгер.
     */
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    /**
     * Хэндлер.
     */
    private final static Handler handler = new ConsoleHandler();

    /** Приватный конструктор, для исключения возможности создания
     * объекта класса.
     */
    private Logs() {
    }

    static {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

    }

    /**
     * Метод для получения значения уровня, хранящегося у логера {@code logger}.
     *
     * @return возвращает значение уровня.
     */
    public static Level getLevel() {
        return logger.getLevel();
    }

    /**
     * Метод для задания установки уровня в логере {@code loggger}.
     *
     * @param level уровень.
     */
    public static void setLevel(Level level) {
        logger.setLevel(level);
    }

    /**
     * Устанавливает уровень логирования, вниже которого логи не будут выводиться в консоль.
     *
     * @param level уровень.
     */
    public static void setLogLevelForOutput(Level level) {
        handler.setLevel(level);
    }

    /**
     * Метод для записи сообщения в лог. Принимающий строку-сообщение {@code message}
     *
     * @param message сообщение.
     */
    public static void writeToLog(String message) {
        logger.log(logger.getLevel(), Thread.currentThread().getStackTrace()[2].getClassName() + "::"+
                Thread.currentThread().getStackTrace()[2].getMethodName() + "  \"" +
                message + "\"\n" ) ;
    }

    /**
     * Метод для записи сообщения в лог. Принимающий строку-сообщение {@code message} и
     * уровень лога {@code level}.
     *
     * @param message сообщение.
     * @param level уровень.
     */
    public static void writeToLog(String message, Level level) {
        logger.log(level,Thread.currentThread().getStackTrace()[2].getClassName() + "::"+
                         Thread.currentThread().getStackTrace()[2].getMethodName() + "  \"" +
                        message + "\"\n" );
    }
}
