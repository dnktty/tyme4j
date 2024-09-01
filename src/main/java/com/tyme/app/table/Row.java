package com.tyme.app.table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@ToString
public class Row {
  public Row(List<Cell> cells) {
    this.cells = cells;
  }

  public Row() {
    this.cells = new ArrayList<>();
  }
  //行索引
  private int rowIndex;
  //填充数据
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
