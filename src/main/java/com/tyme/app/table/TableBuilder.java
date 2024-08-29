package com.tyme.app.table;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TableBuilder {

    private Table table = new Table();

    public static TableBuilder builder() {
        return new TableBuilder();
    }

    public TableBuilder addRow(Row row) {
        table.addRow(row);
        return this;
    }

    public TableBuilder insertRow(int index, Row row) {
        table.insertRow(index, row);
        return this;
    }

    public TableBuilder removeRow(int index) {
        table.removeRow(index);
        return this;
    }

    public TableBuilder addColumn(Column column) {
        table.addColumn(column);
        return this;
    }

    public TableBuilder insertColumn(int index, Column column) {
        table.insertColumn(index, column);
        return this;
    }

    public TableBuilder removeColumn(int index) {
        table.removeColumn(index);
        return this;
    }


    public Table build(int rowSize) {
        for(int i=0;i<rowSize;i++){
            table.getRows().add(new Row());
        }
        return table;
    }
    public Table build() {
        return table;
    }
}
