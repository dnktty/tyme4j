package com.tyme.table;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Column<T> {
    public Column(Header header, List<Cell<T>> cells){
        this.header = header;
        this.cells = cells;
    }
    public Column(Header header){
        this.header = header;
        this.cells = new ArrayList<>();
    }
    private int index;
    private Header<T> header;
    private List<Cell<T>> cells;
}

