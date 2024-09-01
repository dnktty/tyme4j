package com.tyme.app;

import com.tyme.app.rule.HeavenStreamRule;
import com.tyme.app.table.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-06
 **/
@Slf4j
public class HeavenStreamRuleTest {
    @Test
    public void testHeavenStemTable(){
        Table heavenStemTable = HeavenStreamRule.getHeavenStemTable();
        TableUtil.printTable(heavenStemTable, 10);
    }
    @Test
    public void testHeavenStemTableRelation(){
        Table heavenStemTable = HeavenStreamRule.getHeavenStemTableRelation();
        TableUtil.printTable(heavenStemTable, 4);

    }
}
