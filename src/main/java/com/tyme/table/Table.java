package com.tyme.table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Table {

    private List<Row> rows = new ArrayList<>();
    private List<Header> rowHeaders = new ArrayList<>();
    private List<Header> columnHeaders = new ArrayList<>();
    public Table addRow(Row row) {
        insertRow(rows.size(), row);
        return this;
    }

    public Table insertRow(int index, Row row) {
        row.getHeader().setIndex(index);
        rowHeaders.add(index, row.getHeader());
        rows.add(index, row);
        return this;
    }

    public Table removeRow(int index) {
        rows.remove(index);
        return this;
    }

    public Table addColumn(Column column) {
        insertColumn(columnHeaders.size(), column);
        return this;
    }

    public Table insertColumn(int index, Column column) {
        Header columnHeader = column.getHeader();
        columnHeader.setIndex(index);
        columnHeaders.add(index, column.getHeader());
        for (int i=0;i<rows.size();i++) {
            Row row  = rows.get(i);
            row.insertCell(index, (Cell)column.getCells().get(i));
        }
        return this;
    }

    public Table removeColumn(int index) {
        columnHeaders.remove(index);
        for (int i=0;i<rows.size();i++) {
            Row row  = rows.get(i);
            row.removeCell(index);
        }
        return this;
    }

    public Column getColumnByName(String columnName) {
        Header cloumnHeader = columnHeaders.stream()
                .filter(column -> column.getName().equals(columnName))
                .collect(Collectors.toList()).get(0);
        List<Cell> cells = rows.stream()
                .map(row -> row.getCells().get(cloumnHeader.getIndex()))
                .collect(Collectors.toList());
        return new Column(cloumnHeader, cells);
    }
    public Row getRowByName(String rowName) {
        List<Header> headers = rowHeaders.stream()
                .filter(header -> header.getName().equals(rowName))
                .collect(Collectors.toList());
        Header rowHeader = headers.get(0);
        return rows.get(rowHeader.getIndex());
    }

    public void addCellAt(int rowIndex, int columnIndex, Cell cell) {
        rows.get(rowIndex).insertCell(columnIndex, cell);
    }

    public void printTable() {
        // 打印表头
        StringBuilder headerBuilder = new StringBuilder();
        for (Header columnHeader : this.columnHeaders) {
            headerBuilder.append(columnHeader.getColor().getCode());
            headerBuilder.append(columnHeader.getName()).append(" | ");
        }
        headerBuilder.append(Color.RESET.getCode());
        System.out.println(headerBuilder.toString());

        // 打印表格数据
        for (int i=0;i<rows.size();i++) {
            Row row = rows.get(i);
            StringBuilder rowBuilder = new StringBuilder();
            Header rowHeader = rowHeaders.get(i);
            rowBuilder.append(rowHeader.getColor().getCode())
                    .append(rowHeader.getName()).append(" | ");;
            for (int j=0;j<row.getCells().size();j++) {
                Cell cell = row.getCells().get(j);
                rowBuilder.append(cell.getColor().getCode())
                        .append(cell.getValue())
                        .append(Color.RESET.getCode())
                        .append(" | ");
            }
            System.out.println(rowBuilder.toString());
        }
    }
}
