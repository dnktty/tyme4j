package com.tyme.app.rule;

import com.tyme.app.table.*;
import com.tyme.constants.TermEnum;
import com.tyme.culture.Element;

public class ElementRule {
  /**
   * 获取五行信息
   *
   * @return
   */
  public static Table getElementTable() {
    Table table = TableBuilder.builder().build("五行表", "五行及其意义");
    table.addColumn(
        new Column(new Header(TermEnum.WU_XING.show()))
            .addCell((new Header(TermEnum.FANG_WEI.show())))
            .addCell((new Header(TermEnum.SHEN_TI.show())))
            .addCell((new Header(TermEnum.YAN_SE.show())))
            .addCell((new Header(TermEnum.JI_JIE.show())))
            .addCell((new Header(TermEnum.WU_CHANG.show())))
            .addCell((new Header(TermEnum.XING_GE.show()))));
    ;
    for (String elementStr : Element.NAMES) {
      Element element = Element.fromName(elementStr);
      table.addColumn(
          new Column(new Header(element.getName(), element.getColorVal()))
              .addCell(new Cell(element.getDirection(), element.getColorVal()))
              .addCell(new Cell(element.getOrgan(), element.getColorVal()))
              .addCell(new Cell(element.getColorRes(), element.getColorVal()))
              .addCell(new Cell(element.getSeason(), element.getColorVal()))
              .addCell(new Cell(element.getConstant(), element.getColorVal()))
              .addCell(new Cell(element.getCharacter(), element.getColorVal())));
    }
    return table;
  }
}
