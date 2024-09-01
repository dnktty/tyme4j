package com.tyme.app;

import com.tyme.app.rule.TenStarRule;
import com.tyme.app.table.Table;
import com.tyme.app.table.TableUtil;
import org.junit.Test;

public class TenStarTest {
    @Test
    public void testTenStarTable(){
        Table tenStarTable = TenStarRule.getTenStarTable();
        TableUtil.printTable(tenStarTable, 7);
    }
    @Test
    public void testTenStarGroupTable(){
        Table tenStarGroupTable = TenStarRule.getTenStarGroupTable();
        TableUtil.printTable(tenStarGroupTable, 7);
    }
}
