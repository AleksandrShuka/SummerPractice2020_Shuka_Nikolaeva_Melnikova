package GUI;

public class Colors {
    private static final String[] colorArray = {
            "#FFC0CB", "#808080", "#FFFFF1", "#FF8C00",
            "#FF00FF", "#800080", "#FF0000", "#800000",
            "#FFFF00", "#808000", "#00FF00", "#008000",
            "#00FFFF", "#008080", "#0000FF", "#000080",
            "#8B4513", "#F08080", "#00FF7F", "#D2691E",
            "#DAA520", "#87CEEB", "#FFA07A", "#BDB76B",
            "#8A2BE2", "#1E90FF", "#FAEBD7", "#778899"
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
