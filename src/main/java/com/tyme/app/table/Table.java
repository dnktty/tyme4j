package com.tyme.app.table;

import com.tyme.culture.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class Table {
    // 获取最长数字的长度，以便统一列宽
    private int maxLength = 10;
    private String name;
    private String title;
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
    public Row getRowByIndex(int index) {
        return rows.get(index);
    }

    public void addCellAt(int rowIndex, int columnIndex, Cell cell) {
        rows.get(rowIndex).insertCell(columnIndex, cell);
    }

    public void printTable() {
        log.info("==============================================================================");
        log.info("< {} - {} >", this.name, this.title);
        // 打印表头
        StringBuilder headerBuilder = new StringBuilder();
        if(columnHeaders.size()>0){
            for (Header columnHeader : this.columnHeaders) {
                headerBuilder.append(columnHeader.getColor().getStyle());
                headerBuilder.append(formatStr(columnHeader.getName())).append(" | ");
            }
            headerBuilder.append(Color.RESET.getStyle());
            log.info(headerBuilder.toString());
        }

        // 打印表格数据
        for (int i=0;i<rows.size();i++) {
            Row row = rows.get(i);
            StringBuilder rowBuilder = new StringBuilder();
            if(rowHeaders.size()>0){
                Header rowHeader = rowHeaders.get(i);
                rowBuilder.append(rowHeader.getColor().getStyle())
                        .append(formatStr(rowHeader.getName())).append(Color.RESET.getStyle()).append(" | ");;
            }
            for (int j=0;j<row.getCells().size();j++) {
                Cell cell = row.getCells().get(j);
                rowBuilder.append(cell.getColor().getStyle())
                        .append(formatStr(cell.getValue()))
                        .append(Color.RESET.getStyle())
                        .append(" | ");
            }
            log.info(rowBuilder.toString());
        }
    }
    private String formatStr(Object str){
        if(null == str){
            str = "";
        }
        String res = String.format("%-" + (maxLength-calculateWidth(str.toString())) + "s", str);
        return res;
    }

    // 计算字符串的实际宽度（考虑中文字符）
    private static int calculateWidth(String str) {
        int width = 0;
        for (char c : str.toCharArray()) {
            // 判断是否为全角字符
            if (c >= '\u4e00' && c <= '\u9fff') {
                width += 1; // 全角字符占两个位置
            } else {
                width += 0; // 半角字符占一个位置
            }
        }
//        log.info("str:{},width:{}",str,width);
        return width;
    }

}
