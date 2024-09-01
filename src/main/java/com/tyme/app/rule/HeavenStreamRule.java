package com.tyme.app.rule;

import com.tyme.app.table.*;
import com.tyme.constants.TermEnum;
import com.tyme.culture.Element;
import com.tyme.sixtycycle.HeavenStem;

public class HeavenStreamRule {


  public static Table getHeavenStemTable() {
    Table table = TableBuilder.builder().build("天干表", TermEnum.TIAN_GAN.show());
    table.addColumn(
        new Column((new Header(TermEnum.TIAN_GAN.show())))
            .addCell(new Cell(TermEnum.FANG_WEI.show()))
            .addCell(new Cell(TermEnum.BA_GUA.show()))
            .addCell(new Cell(TermEnum.WU_XING.show()))
            .addCell(new Cell(TermEnum.YIN_YANG.show()))
            .addCell(new Cell(TermEnum.DAI_BIAO.show()))
            .addCell(new Cell(TermEnum.SHEN_TI.show())));
    for (String heavenStemStr : HeavenStem.NAMES) {
      HeavenStem heavenStem = HeavenStem.fromName(heavenStemStr);
      table.addColumn(
          new Column(new Header(heavenStem.getName()))
              .addCell(new Cell(heavenStem.getDirection(), heavenStem.getElement().getColorVal()))
              .addCell(
                  new Cell(
                      heavenStem.getDirection().getBaGua(), heavenStem.getElement().getColorVal()))
              .addCell(new Cell(heavenStem.getElement(), heavenStem.getElement().getColorVal()))
              .addCell(new Cell(heavenStem.getYinYang(), heavenStem.getElement().getColorVal()))
              .addCell(new Cell(heavenStem.getRepresent(), heavenStem.getElement().getColorVal()))
              .addCell(new Cell(heavenStem.getOrgan(), heavenStem.getElement().getColorVal())));
    }
    return table;
  }

  public static Table getHeavenStemTableRelation() {
    Table table = TableBuilder.builder().build("天干关系表", "天干克合关系");
    table.addColumn(
        new Column(new Header(TermEnum.TIAN_GAN.show()))
            .addCell(new Cell(TermEnum.WU_XING.show()))
            .addCell(new Cell(TermEnum.WU_HE.show()))
            .addCell(new Cell(TermEnum.HE_HUA.show())));
    for (String heavenStemStr : HeavenStem.NAMES) {
      HeavenStem heavenStem = HeavenStem.fromName(heavenStemStr);
      table.addColumn(
          new Column(new Header(heavenStem.getName(), heavenStem.getElement().getColorVal()))
              .addCell(new Cell(heavenStem.getElement(), heavenStem.getElement().getColorVal()))
              .addCell(new Cell(heavenStem.getCombine(), heavenStem.getElement().getColorVal()))
              .addCell(
                  new Cell(
                      heavenStem.combine(heavenStem.getCombine()),
                      heavenStem.getElement().getColorVal())));
    }
    return table;
  }
}
