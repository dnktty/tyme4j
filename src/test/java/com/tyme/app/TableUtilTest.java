package com.tyme.app;

import com.tyme.app.table.Table;
import com.tyme.app.table.TableUtil;
import org.junit.Test;

public class TableUtilTest {
    @Test
    public void testLoadFromJSON() {
        Table table = TableUtil.loadFromJSON("extends/template.json");
        TableUtil.printTable(table);
    }
    @Test
    public void testMergeRows() {
        Table mainTable = TableUtil.loadFromJSON("extends/template.json");
        Table targetTable = TableUtil.loadFromJSON("extends/template-merge-rows.json");
        TableUtil.printTable(TableUtil.mergeRows(mainTable, targetTable));
    }
    @Test
    public void testMergeCols() {
        Table mainTable = TableUtil.loadFromJSON("extends/template.json");
        Table targetTable = TableUtil.loadFromJSON("extends/template-merge-cols.json");
        TableUtil.printTable(TableUtil.mergeColumns(mainTable, targetTable));
    }
}
