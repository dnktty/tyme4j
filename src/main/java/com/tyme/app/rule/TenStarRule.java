package com.tyme.app.rule;

import com.tyme.app.table.*;

public class TenStarRule {
  public static Table getTenStarTable() {
      Table table = TableUtil.loadFromJSON("extends/ten-star.json");
      return table;
  }
  public static Table getTenStarGroupTable() {
      Table table = TableUtil.loadFromJSON("extends/ten-star-group.json");
      return table;
  }
}
