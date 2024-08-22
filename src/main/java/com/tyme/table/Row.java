package com.tyme.table;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Row {
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

