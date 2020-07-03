package logger;

import com.sun.tools.javac.Main;

import java.sql.Time;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.*;

/**
 * Класс, реализующий логирование.
 * Содержит в себе:
 * @value logger - экземпляр класса Logger.
 * @value handler - экзэмпляр класса Handler, экспортирующий сообщения логера на консоль.
 * Класс, реализующий форматирвоание собщений {@code Forms}.
 * <p>
 * Класс предоставляет метод для получения значения уровня логирования, хранящегося в поле класса {@code logger}:
 * {@code getLevel}.
 * <p>
 * Класс предоставляет метод для установки уровня логирования, ниже которого сообщения
 * не будут выводиться в консоль {@code setLevelOfOutput}, {@code setEdgeList}.
 * <p>
 * Класс предоставляет методы для записи сообщения в log {@code writeToLog}.
 */

public class Logs {
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    private final static Handler handler = new ConsoleHandler();

    /**
     * Приватный конструктор, для исключения возможности создания
     * объекта класса.
     */
    private Logs() {
    }

    static {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        handler.setFormatter(new Forms());
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
     * Устанавливает уровень логирования, вниже которого логи не будут выводиться в консоль.
     *
     * @param level уровень.
     */
    public static void setLogLevelForOutput(Level level) {
        handler.setLevel(level);
    }

    /**
     * Метод для записи сообщения в лог. Принимающий строку-сообщение {@code message}.
     *
     * @param message сообщение.
     */
    public static void writeToLog(String message) {
        logger.log(logger.getLevel(), Thread.currentThread().getStackTrace()[2].getClassName() + "::" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + "  \"" +
                message + "\"" + System.lineSeparator());
    }

    /**
     * Метод для записи сообщения в лог. Принимающий строку-сообщение {@code message} и
     * уровень лога {@code level}.
     *
     * @param message сообщение.
     * @param level   уровень.
     */
    public static void writeToLog(String message, Level level) {
        logger.log(level, Thread.currentThread().getStackTrace()[2].getClassName() + "::" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " \"" +
                message + "\"" + System.lineSeparator());
    }

    /**
     * Класс, реализующий форматирование сообщений в логе.
     *
     * Содержит в себе метод для форматирования {@code format}.
     */
    static class Forms extends Formatter {
        /**
         * Метод для форматирования сообщений.
         *
         * @return строка-сообщение в нужном формате.
         */
        @Override
        public String format(LogRecord record){
            return  Time.from(record.getInstant()).toString() + System.lineSeparator() + record.getLevel() + ": "
                    + record.getMessage() + System.lineSeparator();
        }
    }

}
