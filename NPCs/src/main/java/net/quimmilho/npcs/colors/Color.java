package net.quimmilho.npcs.colors;

public enum Color {

    BLACK("black", '0'),
    DARK_BLUE("dark_blue", '1'),
    DARK_GREEN("dark_green", '2'),
    DARK_AQUA("dark_aqua", '3'),
    DARK_RED("dark_red", '4'),
    DARK_PURPLE("dark_purple", '5'),
    GOLD("gold", '6'),
    GRAY("gray", '7'),
    DARK_GRAY("dark_gray", '8'),
    BLUE("blue", '9'),
    GREEN("green", 'a'),
    AQUA("aqua", 'b'),
    RED("red", 'c'),
    LIGHT_PURPLE("light_purple", 'd'),
    YELLOW("yellow", 'e'),
    WHITE("white", 'f'),
    MAGIC("magic", 'k'),
    BOLD("bold", 'l'),
    LINE_OVER("line_over", 'm'),
    UNDERLINE("underline", 'n'),
    ITALIC("italic", 'o'),
    RESET("reset", 'r');

    private final String name;
    private final char code;

    Color(String name, char code) {
        this.name = name;
        this.code = code;
    }

    String getName() {
        return name;
    }

    char getCode() {
        return code;
    }

    String getColor() {
        return "\u00A7" + code;
    }

    public static String encode(String arg) {
        String[] args = arg.split(",");
        String str = "";
        for (String a : args) {
            Color c = Color.valueOf(a);
            str = str.concat(c.getColor());
        }
        return str;
    }

}
