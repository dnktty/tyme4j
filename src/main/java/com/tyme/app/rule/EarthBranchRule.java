package com.tyme.app.rule;

import com.tyme.app.table.*;
import com.tyme.constants.CharConstant;
import com.tyme.constants.TermEnum;
import com.tyme.culture.Color;
import com.tyme.culture.Terrain;
import com.tyme.sixtycycle.EarthBranch;
import com.tyme.sixtycycle.HeavenStem;
import org.apache.commons.lang3.StringUtils;

public class EarthBranchRule {
  public static Table getEarthBranchRelation2Table() {
      Table table = TableUtil.loadFromJSON("extends/earth-branch-relation.json");
      return table;
  }
  public static Table getEarthBranchRelationTable() {
    Table table = TableBuilder.builder().build("地支关系", "合冲害关系");
    table.addHeader(new Header(TermEnum.DI_ZHI.show()));
    for (String earthBranchStr : EarthBranch.NAMES) {
      EarthBranch earthBranch = EarthBranch.fromName(earthBranchStr);
      table.addHeader(new Header(earthBranch.getName()));
    }

    for (String earthBranchStr : EarthBranch.NAMES) {
      EarthBranch earthBranch = EarthBranch.fromName(earthBranchStr);
      Row row = new Row();
      table.addRow(row);
      // 行头
      row.getCells().set(0, new Cell(earthBranch, Color.BOLD));
      // 合
      Header headerCombine = table.getHeaderByName(earthBranch.getCombine().getName());
      row.getCells()
          .set(
              headerCombine.getColIndex(),
              new Cell(
                  StringUtils.join(
                      earthBranch.getName(),
                      earthBranch.getCombine().getName(),
                      "合",
                      earthBranch.combine(earthBranch.getCombine()).getName()),
                  Color.GREEN));
      // 冲
      Header headerOpposite = table.getHeaderByName(earthBranch.getOpposite().getName());
      row.getCells()
          .set(
              headerOpposite.getColIndex(),
              new Cell(
                  StringUtils.join(earthBranch.getName(), earthBranch.getOpposite().getName(), "冲"),
                  Color.YELLOW));
      // 害
      Header headerHarm = table.getHeaderByName(earthBranch.getHarm().getName());
      row.getCells()
          .set(
              headerHarm.getColIndex(),
              new Cell(
                  StringUtils.join(earthBranch.getName(), earthBranch.getHarm().getName(), "害"),
                  Color.RED));
    }
    return table;
  }

  public static Table getEarthBranchTable() {
    Table table = TableBuilder.builder().build("地支表", TermEnum.DI_ZHI.show());
    table.addColumn(
        new Column(new Header(TermEnum.DI_ZHI.show()))
            .addCell(new Cell((TermEnum.WU_XING.show())))
            .addCell(new Cell((TermEnum.YIN_YANG.show())))
            .addCell(new Cell((TermEnum.FANG_WEI.show())))
            .addCell(new Cell((TermEnum.BA_GUA.show())))
            .addCell(new Cell((TermEnum.REPRESENT.show())))
            .addCell(new Cell((TermEnum.SHEN_XIAO.show())))
            .addCell(new Cell((TermEnum.SHI_CHEN.show())))
            .addCell(new Cell((TermEnum.MONTH.show()))));
    for (String earthBranchStr : EarthBranch.NAMES) {
      EarthBranch earthBranch = EarthBranch.fromName(earthBranchStr);
      table.addColumn(
          new Column(new Header(earthBranch.getName(), earthBranch.getElement().getColorVal()))
              .addCell(new Cell(earthBranch.getElement(), earthBranch.getElement().getColorVal()))
              .addCell(new Cell(earthBranch.getYinYang(), earthBranch.getElement().getColorVal()))
              .addCell(new Cell(earthBranch.getDirection(), earthBranch.getElement().getColorVal()))
              .addCell(
                  new Cell(
                      earthBranch.getDirection().getBaGua(),
                      earthBranch.getElement().getColorVal()))
              .addCell(
                  new Cell(
                      earthBranch.getDirection().getRepresent(),
                      earthBranch.getElement().getColorVal()))
              .addCell(new Cell(earthBranch.getZodiac(), earthBranch.getElement().getColorVal()))
              .addCell(new Cell(earthBranch.getOminous(), earthBranch.getElement().getColorVal()))
              .addCell(new Cell(earthBranch.getOminous(), earthBranch.getElement().getColorVal())));
    }
    return table;
  }

  /**
   * 获取地势表
   *
   * @return
   */
  public static Table getTerrainTable() {
    Table terrainTable = new Table();
    // 添加表头
    terrainTable.addHeader(
        new Header(
            StringUtils.join(
                TermEnum.TIAN_GAN.show(), CharConstant.SLASH, TermEnum.SHENG_WANG.show())));
    for (String terrainStr : Terrain.NAMES) {
      Terrain terrain = Terrain.fromName(terrainStr);
      terrainTable.addHeader(new Header(terrain.getName()));
    }
    // 添加行
    for (String heavenStreamStr : HeavenStem.NAMES) {
      HeavenStem heavenStem = HeavenStem.fromName(heavenStreamStr);
      Row row = new Row();
      terrainTable.addRow(row);
      // 为行初始化单元格
      row.getCells().set(0, new Cell(heavenStem, Color.BOLD));
      // 遍历地支
      for (String earthBranchStr : EarthBranch.NAMES) {
        EarthBranch earthBranch = EarthBranch.fromName(earthBranchStr);
        Terrain terrain = heavenStem.getTerrain(earthBranch);
        // 找到地势对应的单元格
        Header header = terrainTable.getHeaderByName(terrain.getName());
        row.getCells()
            .set(
                header.getColIndex(),
                new Cell(earthBranch, header.getColIndex() == 4 ? Color.YELLOW : Color.RESET));
      }
    }
    return terrainTable;
  }
}
