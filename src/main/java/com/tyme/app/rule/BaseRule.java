package com.tyme.app.rule;

import com.tyme.app.table.*;
import com.tyme.sixtycycle.HeavenStem;

public class BaseRule {
    /**
     *  获取天干表
     * @return
     */
    public static Table getHeavenStemTable(){
        Table table = TableBuilder.builder().build();
        table.addRow(new Row(new Header("天干")))
                .addRow(new Row(new Header("五行")))
                .addRow(new Row(new Header("五合")))
                .addRow(new Row(new Header("合化")))
                ;
        for(String heavenStemStr : HeavenStem.NAMES){
            HeavenStem heavenStem = HeavenStem.fromName(heavenStemStr);
            table.getRowByName("天干").addCell(new Cell(heavenStem.getName(), Color.BOLD));
            table.getRowByName("五行").addCell(new Cell(heavenStem.getElement(), Color.YELLOW));
            table.getRowByName("五合").addCell(new Cell(heavenStem.getCombine(), Color.GREEN));
            table.getRowByName("合化").addCell(new Cell(heavenStem.combine(heavenStem.getCombine()), Color.GREEN));
        }
        return table;
    }
}
