package GUI;

import java.awt.*;

public class Colors {
    private static final String[] colorArray = {
            "#000000", "#FFC0CB", "#808080", "#C0C0C0",
            "#FF00FF", "#800080", "#FF0000", "#800000",
            "#FFFF00", "#808000", "#00FF00", "#008000",
            "#00FFFF", "#008080", "#0000FF", "#000080",
            "#8B4513", "#FF8C00", "#FFFFFF"
    };

    public static String get(int index) {
        return colorArray[index];
    }

    public static int size() {
        return colorArray.length;
    }

    private Colors() {
    }
}
