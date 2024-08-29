package com.tyme.app.table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BLACK("\u001B[30m"),
    BOLD("\u001B[1m"),
    RESET("\u001B[0m");

    private final String code;
}
