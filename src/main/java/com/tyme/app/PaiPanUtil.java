package com.tyme.app;

import com.tyme.ditu.Geography;
import com.tyme.ditu.GeographyUtil;
import com.tyme.eightchar.EightChar;
import com.tyme.lunar.LunarHour;
import com.tyme.solar.SolarTime;
import com.tyme.solar.TrueSolarTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-20
 **/
@Slf4j
public class PaiPanUtil {
    /*public static Table getPaiPan(Birth birth) {
        Table table = TableBuilder.builder()
                .addHeader("Name")
                .addHeader("Age")
                .addRow(new Row()
                        .addCell(new Cell("Alice", Color.RED))
                        .addCell(new Cell("25", Color.YELLOW)))
                .addRow(new Row()
                        .addCell(new Cell("Bob", Color.MAGENTA))
                        .addCell(new Cell("30", Color.CYAN)))
                .build();


        // 在第二行的第一列添加一个 Cell
        Cell newCell = new Cell("Charlie", Color.BLACK);
        table.addCellAt(1, 0, newCell); // 注意索引从 0 开始

        // 再次打印表格
        table.printTable();

        Table paiPan =
                Table.create("paipan")
                        .addColumns(
                                StringColumn.create("年柱", animals),
                                StringColumn.create("月柱", animals),
                                StringColumn.create("日柱", animals),
                                StringColumn.create("时柱", animals),
                                StringColumn.create("大运", animals),
                                StringColumn.create("流年", animals),
                                StringColumn.create("流月", animals));
        EightChar eightChar = getEightChar(birth);
        paiPan.addRow(0, Table.create().addColumns(StringColumn..create("xxxx", "a"), DoubleColumn.create("xx", 1.0)));
        paiPan.setHeavenStem(new TimeDimension(eightChar.getYear().getHeavenStem().getName(), eightChar.getMonth().getHeavenStem().getName(), eightChar.getDay().getHeavenStem().getName(), eightChar.getHour().getHeavenStem().getName()));
        paiPan.setEarthBranch(new TimeDimension(eightChar.getYear().getEarthBranch().getName(), eightChar.getMonth().getEarthBranch().getName(), eightChar.getDay().getEarthBranch().getName(), eightChar.getHour().getEarthBranch().getName()));
        return paiPan;
    }*/


    public static EightChar getEightChar(Birth birth){
        String address = StringUtils.join(birth.getAddress().getProvince() , birth.getAddress().getCity() , birth.getAddress().getDistrict());
        String birthDay = birth.getBirthDay();
        //获取经纬度
        // 1、根据地址获取经纬度
        Geography geo = GeographyUtil.getLonAndLat(address, null);
        LocalDateTime localDateTime = LocalDateTime.parse(birthDay, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LunarHour lunarHour1 = LunarHour.fromYmdHms(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        // 输出城市的真太阳时
        localDateTime = LocalDateTime.parse(lunarHour1.getSolarTime().toString(), DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of(birth.getTimeZone().getId()));
        LocalDateTime trueSolarTime = TrueSolarTime.getTrueSolarTime(geo.getLongitude(), geo.getLatitude(), localZonedDateTime);
        //从真太阳时获取农历时辰
        SolarTime solarTimeAfter = SolarTime.fromYmdHms(trueSolarTime.getYear(), trueSolarTime.getMonthValue(), trueSolarTime.getDayOfMonth(), trueSolarTime.getHour(), trueSolarTime.getMinute(), trueSolarTime.getSecond());
        LunarHour lunarHour = solarTimeAfter.getLunarHour();
        log.debug("真太阳时农历：{}", lunarHour.format());
        log.debug(" {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(0).getName());
        return lunarHour.getEightChar();
    }

}
