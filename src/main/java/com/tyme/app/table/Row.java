package com.tyme.app.table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Row {
    public Row(Header header, List<Cell> cells){
        this.header = header;
        this.cells = cells;
    }
    public Row(Header header){
        this.header = header;
        this.cells = new ArrayList<>();
    }
    public Row(){
        this.cells = new ArrayList<>();
    }
    private Header header;
    private int index;
    private List<Cell> cells;

    public Row addCell(Cell cell) {
        if (cells == null) {
            cells = new ArrayList<>();
        }
        cells.add(cell);
        return this;
    }

    public Row insertCell(int index, Cell cell) {
        if (cells == null) {
            cells = new ArrayList<>();
        }
        cells.add(index, cell);
        return this;
    }

    public Row removeCell(int index) {
        if (cells != null) {
            cells.remove(index);
        }
        return this;
    }
}

