package com.tyme.app;

import com.tyme.app.rule.ElementRule;
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
public class ElementRuleTest {
    @Test
    public void testElementTable(){
        Table elementTable = ElementRule.getElementTable();
        TableUtil.printTable(elementTable, 7);

    }
}
