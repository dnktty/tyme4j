package com.tyme.app;

import com.tyme.app.rule.EarthBranchRule;
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
public class EarthBranchRuleTest {
    @Test
    public void testGetEarthBranchRelation(){
        Table earthBranchRelationTable = EarthBranchRule.getEarthBranchRelationTable();
        TableUtil.printTable(earthBranchRelationTable,8);
    }
    @Test
    public void testGetEarthBranchRelation2(){
        Table earthBranchRelation2Table = EarthBranchRule.getEarthBranchRelation2Table();
        TableUtil.printTable(earthBranchRelation2Table,4);
    }
    @Test
    public void testGetEarthBranchTable(){
        Table earthBranchTable = EarthBranchRule.getEarthBranchTable();
        TableUtil.printTable(earthBranchTable,8);
    }
    @Test
    public void testGetTerrainTable(){
        Table terrainTable = EarthBranchRule.getTerrainTable();
        TableUtil.printTable(terrainTable,4);
    }
}
