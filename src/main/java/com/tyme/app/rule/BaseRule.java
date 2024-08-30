package com.tyme.app.rule;

import com.tyme.app.table.*;
import com.tyme.culture.Element;
import com.tyme.sixtycycle.HeavenStem;

public class BaseRule {
    /**
     *  获取五行信息
     * @return
     */
    public static Table getElementTable(){
        Table table = TableBuilder.builder().build("五行表", "五行及其意义");
        table.addRow(new Row(new Header("五行")))
                .addRow(new Row(new Header("方位")))
                .addRow(new Row(new Header("五脏")))
                .addRow(new Row(new Header("颜色")))
                .addRow(new Row(new Header("季节")))
                .addRow(new Row(new Header("五常")))
                .addRow(new Row(new Header("性格")))
                ;
        for(String elementStr : Element.NAMES){
            Element element = Element.fromName(elementStr);
            table.getRowByName("五行").addCell(new Cell(element.getName(), element.getColor()));
            table.getRowByName("方位").addCell(new Cell(element.getDirection(), element.getColor()));
            table.getRowByName("五脏").addCell(new Cell(element.getOrgan(), element.getColor()));
            table.getRowByName("颜色").addCell(new Cell(element.getColorRes(), element.getColor()));
            table.getRowByName("季节").addCell(new Cell(element.getSeason(), element.getColor()));
            table.getRowByName("五常").addCell(new Cell(element.getConstant(), element.getColor()));
            table.getRowByName("性格").addCell(new Cell(element.getCharacter(), element.getColor()))
            ;
        }
        return table;
    }
    public static Table getHeavenStemTable(){
        Table table = TableBuilder.builder().build("天干表", "天干");
        table.addRow(new Row(new Header("天干")))
                .addRow(new Row(new Header("方位")))
                .addRow(new Row(new Header("八卦")))
                .addRow(new Row(new Header("五行")))
                .addRow(new Row(new Header("阴阳")))
                .addRow(new Row(new Header("代表")))
                .addRow(new Row(new Header("身体")))
                ;
        for(String heavenStemStr : HeavenStem.NAMES){
            HeavenStem heavenStem = HeavenStem.fromName(heavenStemStr);
            table.getRowByName("天干").addCell(new Cell(heavenStem.getName(), heavenStem.getElement().getColor()));
            table.getRowByName("方位").addCell(new Cell(heavenStem.getDirection(), heavenStem.getElement().getColor()));
            table.getRowByName("八卦").addCell(new Cell(heavenStem.getDirection().getBaGua(), heavenStem.getElement().getColor()));
            table.getRowByName("五行").addCell(new Cell(heavenStem.getElement(), heavenStem.getElement().getColor()));
            table.getRowByName("阴阳").addCell(new Cell(heavenStem.getYinYang(), heavenStem.getElement().getColor()));
            table.getRowByName("代表").addCell(new Cell(heavenStem.getRepresent(), heavenStem.getElement().getColor()));
            table.getRowByName("身体").addCell(new Cell(heavenStem.getOrgan(), heavenStem.getElement().getColor()));
        }
        return table;
    }
    public static Table getHeavenStemTableRelation(){
        Table table = TableBuilder.builder().build("天干关系表", "天干克合关系");
        table.addRow(new Row(new Header("天干")))
                .addRow(new Row(new Header("五行")))
                .addRow(new Row(new Header("五合")))
                .addRow(new Row(new Header("合化")))
                ;
        for(String heavenStemStr : HeavenStem.NAMES){
            HeavenStem heavenStem = HeavenStem.fromName(heavenStemStr);
            table.getRowByName("天干").addCell(new Cell(heavenStem.getName(), heavenStem.getElement().getColor()));
            table.getRowByName("五行").addCell(new Cell(heavenStem.getElement(), heavenStem.getElement().getColor()));
            table.getRowByName("五合").addCell(new Cell(heavenStem.getCombine(), heavenStem.getElement().getColor()));
            table.getRowByName("合化").addCell(new Cell(heavenStem.combine(heavenStem.getCombine()), heavenStem.getElement().getColor()));
        }
        return table;
    }
}
