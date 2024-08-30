package com.tyme.app;

import com.tyme.app.paipan.Address;
import com.tyme.app.paipan.Birth;
import com.tyme.app.paipan.PaiPanUtil;
import com.tyme.app.rule.BaseRule;
import com.tyme.app.table.*;
import com.tyme.ditu.Geography;
import com.tyme.ditu.GeographyUtil;
import com.tyme.eightchar.ChildLimit;
import com.tyme.eightchar.DecadeFortune;
import com.tyme.eightchar.Fortune;
import com.tyme.enums.Gender;
import com.tyme.lunar.LunarHour;
import com.tyme.solar.SolarTime;
import com.tyme.solar.TrueSolarTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-06
 **/
@Slf4j
public class BaseRuleTest {
    @Test
    public void testHeavenStemTable(){
        Table heavenStemTable = BaseRule.getHeavenStemTable();
        heavenStemTable.setMaxLength(10);
        heavenStemTable.printTable();
    }
    @Test
    public void testHeavenStemTableRelation(){
        Table heavenStemTable = BaseRule.getHeavenStemTableRelation();
        heavenStemTable.setMaxLength(4);
        heavenStemTable.printTable();
    }
    @Test
    public void testElementTable(){
        Table elementTable = BaseRule.getElementTable();
        elementTable.setMaxLength(7);
        elementTable.printTable();
    }
}
