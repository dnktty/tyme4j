package com.tyme.table;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Header<T> {
    public Header(int index,int span, String name, Color color) {
        this.index = index;
        this.span = span;
        this.name = name;
        this.color = color;
    }
    public Header(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    public Header(String name) {
        this.name = name;
    }
    private int index = 0;
    private int span = 1;
    private String name;
    private Color color = Color.RESET;
    private T type;
}
