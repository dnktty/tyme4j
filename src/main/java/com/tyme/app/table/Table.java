package com.tyme.app.table;

import com.alibaba.fastjson.JSON;
import com.tyme.culture.Color;
import com.tyme.exception.BizException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class Table {
    private String name;
    private String title;
    private List<Row> rows = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
    public Table addRow(Row row) {
        insertRow(rows.size(), row);
        return this;
    }

    public Table insertRow(int index, Row row) {
        row.setRowIndex(index);
        rows.add(index, row);
        //行没有单元格，则根据列数来初始化单元格
        if(0==row.getCells().size()){
            for(int i=0;i<headers.size();i++){
                row.addCell(new Cell());
            }
        }
        return this;
    }

    public Table removeRow(int index) {
        rows.remove(index);
        return this;
    }

    public Table addHeader(Header header) {
        insertHeader(headers.size(), header);
        return this;
    }
    public Header getHeaderByName(String headerName) {
        for(Header header: headers){
            if(header.getValue().equals(headerName)){
                return header;
            }
        }
        return null;
    }

    public Table insertHeader(int index, Header header) {
        header.setColIndex(headers.size());
        header.setRowIndex(0);
        headers.add(index, header);
        //预初始化单元格
        for(Row row:rows){
            row.insertCell(index, new Cell());
        }
        return this;
    }
    public Table insertColumn(int index, Column column) {
        //添加头信息
        insertHeader(index, column.getHeader());
        //添加行
        for (int i=0;i<column.getCells().size();i++) {
            Row row  = rows.size() > i ? rows.get(i) : null;
            if(null==row){
                row = new Row();
                rows.add(row);
                row.insertCell(index, column.getCells().get(i));
            }else{
                row.getCells().set(index, column.getCells().get(i));
            }
        }
        return this;
    }

    public Table addColumn(Column column){
        insertColumn(headers.size(), column);
        return this;
    }
    public Table removeColumn(int index) {
        headers.remove(index);
        for (int i=0;i<rows.size();i++) {
            Row row  = rows.get(i);
            row.removeCell(index);
        }
        return this;
    }

    public Column getColumnByName(String headerName) {
        Header selectHeader = headers.stream()
                .filter(header -> header.getValue().equals(headerName))
                .collect(Collectors.toList()).get(0);
        List<Cell> cells = rows.stream()
                .map(row -> row.getCells().get(selectHeader.getColIndex()))
                .collect(Collectors.toList());
        return new Column(selectHeader, cells);
    }
    public List<Column> getColumns() {
        List<Column> columns = new ArrayList<>();
        for(int i=0;i<headers.size();i++){
            Header header = headers.get(i);
            List<Cell> cells = new ArrayList<>();
            for(Row row: rows){
                cells.add(row.getCells().get(i));
            }
            columns.add(new Column(header, cells));
        }
        return columns;
    }
    public Row getRowByIndex(int index) {
        return rows.get(index);
    }

    public void addCellAt(int rowIndex, int columnIndex, Cell cell) {
        rows.get(rowIndex).insertCell(columnIndex, cell);
    }
}
