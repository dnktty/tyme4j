package com.tyme.table;
import lombok.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    private List<Row> rows = new ArrayList<>();
    private List<Column> columns = new ArrayList<>();
    private Header header;
    private Map<Column, Integer> columnIndices = new HashMap<>();

    public Table addRow(Row row) {
        this.getRows().add(row);
        return this;
    }

    public Table insertRow(int index, Row row) {
        this.getRows().add(index, row);
        return this;
    }

    public Table removeRow(int index) {
        this.getRows().remove(index);
        return this;
    }

    public Table addColumn(Column column) {
        int columnIndex = this.getColumns().size();
        this.getColumns().add(column);
        this.columnIndices.put(column, columnIndex);
        for (Row row : this.getRows()) {
            row.addCell(new Cell());
        }
        return this;
    }

    public Table insertColumn(int index, Column column) {
        this.getColumns().add(index, column);
        this.columnIndices.put(column, index);
        for (Row row : this.getRows()) {
            row.insertCell(index, new Cell());
        }
        return this;
    }

    public Table removeColumn(int index) {
        Column removedColumn = this.getColumns().remove(index);
        this.columnIndices.remove(removedColumn);
        for (Row row : this.getRows()) {
            row.removeCell(index);
        }
        updateColumnIndices();
        return this;
    }

    private void updateColumnIndices() {
        for (int i = 0; i < this.getColumns().size(); i++) {
            this.columnIndices.put(this.getColumns().get(i), i);
        }
    }

    public List<Cell> getCellsForColumn(Column column) {
        int columnIndex = this.columnIndices.get(column);
        return this.getRows().stream()
                .map(row -> row.getCells().get(columnIndex))
                .collect(Collectors.toList());
    }

    public List<Column> getColumnsByName(String columnName) {
        return this.getColumns().stream()
                .filter(column -> column.getName().equals(columnName))
                .collect(Collectors.toList());
    }

    public void addCellAt(int rowIndex, Column column, Cell cell) {
        int columnIndex = this.columnIndices.get(column);
        Row row = this.getRows().get(rowIndex);
        int currentCellCount = row.getCells().size();
        if (currentCellCount <= columnIndex) {
            for (int i = currentCellCount; i <= columnIndex; i++) {
                row.addCell(new Cell());
            }
        }
        row.getCells().set(columnIndex, cell);
    }

    public void printTable() {
        // 打印表头
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(Color.BOLD.getCode());
        for (String headerText : this.header.getHeaders()) {
            headerBuilder.append(headerText).append(" | ");
        }
        headerBuilder.append(Color.RESET.getCode()).append(System.lineSeparator());
        System.out.println(headerBuilder.toString());

        // 打印表格数据
        for (Row row : this.getRows()) {
            StringBuilder rowBuilder = new StringBuilder();
            for (Cell cell : row.getCells()) {
                rowBuilder.append(cell.getColor().getCode())
                        .append(cell.getValue())
                        .append(Color.RESET.getCode())
                        .append(" | ");
            }
            System.out.println(rowBuilder.toString());
        }
    }
}
