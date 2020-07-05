package GUI;

import java.awt.*;

/**
 * Класс, используемый для удобного задания значений цветов в окне приложения, а также
 * для задания цвета вершинам.
 * <p>
 * Солержит в себе:
 *
 * @value firstBackgroundColor - первый фоновый цвет
 * @value secondBackgroundColor - второй фоновый цвет
 * @value colorArray - массив различных цветов для окрашивания вершин
 * <p>
 * Имеет методы {@code getFirstBackgroundColor}, {@code getSecondBackgroundColor} для получения первого фонового
 * цвета и второго фонового цвета, а также метод {@code get} для получения цвета из массива цветов
 */

public class Colors {
    private static final Color firstBackgroundColor = new Color(0XE6E6FA);
    private static final Color secondBackgroundColor = new Color(0xB0B0BB);

    private static final String[] colorArray = {
            "#FFC0CB", "#808080", "#004400", "#FF5C00",
            "#FF00FF", "#800080", "#FF0000", "#800000",
            "#FFFF00", "#808000", "#00FF00", "#008000",
            "#00FFFF", "#008080", "#0000FF", "#000080",
            "#8B4513", "#F08080", "#00FF7F", "#D2691E",
            "#DAA520", "#87CEEB", "#FFA07A", "#BDB76B",
            "#8A2BE2", "#1E90FF", "#111111", "#778899"
    };

    /**
     * Метод для получения цвета из массива цветов {@code colorArray}.
     *
     * @param index - индекс в массиве.
     * @return строковое представление цвета
     */
    public static String get(int index) {
        return colorArray[index];
    }

    /**
     * Метод, для получения значения {@code firstBackgroundColor}.
     *
     * @return первый фоновый цвет.
     */
    public static Color getFirstBackgroundColor() {
        return firstBackgroundColor;
    }

    /**
     * Метод, для получения значения {@code secondBackgroundColor}.
     *
     * @return второй фоновый цвет.
     */
    public static Color getSecondBackgroundColor() {
        return secondBackgroundColor;
    }

    /**
     * Метод, для получения размера массива цветов.
     *
     * @return размер массива цветов.
     */
    public static int size() {
        return colorArray.length;
    }

    /**
     * Приватный конструктор, для исключения возможности создания экземпляра класса вне его методов.
     */
    private Colors() {
    }
}
