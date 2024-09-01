package com.tyme.app.table;

import com.tyme.culture.Color;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
public class Cell<T> {
    public Cell(T value) {
        this.value = value;
    }
    //显示值
    T value;
    //颜色
    Color color;
    String colorVal = "";
    //列索引
    int colIndex;
    //行索引
    int rowIndex;
    public Cell(T value, Color color, int colIndex, int rowIndex) {
        this.value = value;
        this.color = color;
        this.colorVal = color.getStyle();
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
    }
    public Cell(T value, Color color) {
        this.value = value;
        this.color = color;
        this.colorVal = color.getStyle();
    }

}

