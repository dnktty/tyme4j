package com.tyme.culture;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    GREEN(0, "\u001B[32m"),
    RED(1, "\u001B[31m"),
    CYAN(2, "\u001B[37m"),
    YELLOW(3, "\u001B[33m"),
    BLUE(4, "\u001B[34m"),
    MAGENTA(5, "\u001B[35m"),
    WHITE(6, "\u001B[37m"),
    BLACK(7, "\u001B[30m"),
    BOLD(8, "\u001B[1m"),
    RESET(9, "\u001B[0m");

    private final int index;
    private final String style;

    public static Color fromIndex(int index) {
        for (Color color : Color.values()) {
            if (color.index == index) {
                return color;
            }
        }
        return null;
    }
}
