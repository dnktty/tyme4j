package com.tyme.app.table;

import com.tyme.culture.Color;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Header<T> extends Cell<T>{
    //列值获取key
    private String key;
    //列宽度
    private int width;
    public Header(T value, String key, Color color, int width) {
        this.value = value;
        this.key = key;
        this.color = color;
        this.width = width;
    }
    public Header(T value, Color color, int width) {
        this.value = value;
        this.color = color;
        this.width = width;
    }
    public Header(T value, String key, Color color) {
        super.value = value;
        this.key = key;
        super.color = color;
    }
    public Header(T value, Color color) {
        super.value = value;
        super.color = color;
    }
    public Header(T value, String key) {
        super.value = value;
        this.key = key;
    }
    public Header(T value) {
        super.value = value;
    }

}
