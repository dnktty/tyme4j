package com.tyme.app.table;

import com.tyme.culture.Color;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cell<T> {
    public Cell(T value, Color color, int columnIndex, int rowIndex) {
        this.value = value;
        this.color = color;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }
    public Cell(T value, Color color) {
        this.value = value;
        this.color = color;
    }
    public Cell(T value) {
        this.value = value;
    }
    private T value;
    private Color color = Color.RESET;
    private int columnIndex;
    private int rowIndex;
}

