package com.tyme.app.table;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Data
public class Column {
    public Column(Header header, List<Cell> cells) {
        this.header = header;
        this.cells = cells;
    }

    public Column(Header header) {
        this.header = header;
    }
    private List<Cell> cells = new ArrayList<>();
    private Header header;


    public Column addCell(Cell cell) {
        cells.add(cell);
        return this;
    }
}
