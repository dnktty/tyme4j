package com.tyme.app.paipan;

import com.tyme.app.table.*;
import com.tyme.constants.CharConstant;
import com.tyme.ditu.Geography;
import com.tyme.ditu.GeographyUtil;
import com.tyme.eightchar.ChildLimit;
import com.tyme.eightchar.DecadeFortune;
import com.tyme.eightchar.EightChar;
import com.tyme.lunar.LunarHour;
import com.tyme.lunar.LunarMonth;
import com.tyme.lunar.LunarYear;
import com.tyme.sixtycycle.EarthBranch;
import com.tyme.sixtycycle.HeavenStem;
import com.tyme.sixtycycle.SixtyCycle;
import com.tyme.solar.SolarTerm;
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
    private static final int FORTURE_SIZE = 9;
    private static final int FLOW_SIZE = 10;
    private static final String CHAR_AGE = "岁";
    private static final String CHAR_MONTH = "月";

    public static void getPaiPan(Birth birth) {

        SolarTime solarTime = getTrueSolarTime(birth);
        //八字
        EightChar eightChar = getEightChar(solarTime);
        //流年
        ChildLimit childLimit = ChildLimit.fromSolarTime(solarTime, birth.getGender());

        Table baZiTable = getBaZi(solarTime, eightChar, childLimit);
        baZiTable.printTable();
        Table decadeFortuneTable = getDecadeFortune(solarTime, childLimit, eightChar);
        decadeFortuneTable.printTable();
        Table flowYearTable = getFlowYear(solarTime, childLimit, eightChar);
        flowYearTable.printTable();
        Table flowMonthTable = getFlowMonth(solarTime, childLimit, eightChar);
        flowMonthTable.printTable();




    }

    /**
     * 流月
     *
     * @param solarTime
     * @param childLimit
     * @return
     */
    public static Table getFlowMonth(SolarTime solarTime, ChildLimit childLimit, EightChar eightChar) {
        Table table = TableBuilder.builder().build();
        table.setName("流月");
        table.addRow(new Row(new Header("月")))
                .addRow(new Row(new Header("节气日")))
                .addRow(new Row(new Header("天干")))
                .addRow(new Row(new Header("天神")))
                .addRow(new Row(new Header("地支")))
                .addRow(new Row(new Header("地藏-主")))
                .addRow(new Row(new Header("地藏-中")))
                .addRow(new Row(new Header("地藏-次")));
        LocalDateTime localDateTime = LocalDateTime.now();
        LunarYear currentlunarYear = LunarYear.fromYear(SolarTime.fromYmdHms(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()).getLunarHour().getYear());
        for (LunarMonth flowLunarMonth : currentlunarYear.getMonths()) {
            SixtyCycle flowLunarMonthSixtyCycle = flowLunarMonth.getSixtyCycle();
            SolarTerm solarTerm = flowLunarMonth.getFirstJulianDay().getSolarDay().getTerm();
            table.getRowByName("月").addCell(new Cell(StringUtils.join(solarTerm.getName(), CHAR_MONTH), Color.BLUE));
            table.getRowByName("节气日").addCell(new Cell(StringUtils.join(solarTerm.getJulianDay().getSolarDay().getMonth(), CharConstant.PERIOD, solarTerm.getJulianDay().getSolarDay().getDay()), Color.BLUE));
            table.getRowByName("天干").addCell(new Cell(flowLunarMonth.getSixtyCycle().getHeavenStem(), Color.MAGENTA));
            table.getRowByName("天神").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarMonthSixtyCycle.getHeavenStem()), Color.CYAN));
            table.getRowByName("地支").addCell(new Cell(flowLunarMonth.getSixtyCycle().getEarthBranch(), Color.YELLOW));
            table.getRowByName("地藏-主").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemMain()), Color.CYAN));
            table.getRowByName("地藏-中").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemMiddle()), Color.CYAN));
            table.getRowByName("地藏-次").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemResidual()), Color.CYAN));
        }
        return table;
    }

    /**
     * 流年
     *
     * @param solarTime
     * @param childLimit
     * @return
     */
    public static Table getFlowYear(SolarTime solarTime, ChildLimit childLimit, EightChar eightChar) {

        Table table = TableBuilder.builder().build();
        table.setName("流年");
        table.addRow(new Row(new Header("岁")))
                .addRow(new Row(new Header("年")))
                .addRow(new Row(new Header("天干")))
                .addRow(new Row(new Header("天神")))
                .addRow(new Row(new Header("地支")))
                .addRow(new Row(new Header("地藏-主")))
                .addRow(new Row(new Header("地藏-中")))
                .addRow(new Row(new Header("地藏-次")));
        DecadeFortune currentDecadeFortune = childLimit.getStartDecadeFortune().getCurrentDecadeFortune();
        int flowStartAge = currentDecadeFortune.getStartAge();
        LunarYear flowLunarYear = currentDecadeFortune.getStartLunarYear();
        for (int i = 0; i < FLOW_SIZE; i++) {
            SixtyCycle flowLunarYearSixtyCycle = flowLunarYear.getSixtyCycle();
            table.getRowByName("岁").addCell(new Cell(StringUtils.join(flowStartAge + i, CHAR_AGE), Color.BLUE));
            table.getRowByName("年").addCell(new Cell(flowLunarYear.getYear(), Color.GREEN));
            table.getRowByName("天干").addCell(new Cell(flowLunarYearSixtyCycle.getHeavenStem(), Color.BLUE));
            table.getRowByName("天神").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarYearSixtyCycle.getHeavenStem()), Color.CYAN));
            table.getRowByName("地支").addCell(new Cell(flowLunarYearSixtyCycle.getEarthBranch(), Color.YELLOW));
            table.getRowByName("地藏-主").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemMain()), Color.CYAN));
            table.getRowByName("地藏-中").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemMiddle()), Color.CYAN));
            table.getRowByName("地藏-次").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemResidual()), Color.CYAN));
            flowLunarYear = flowLunarYear.next(1);
        }
        return table;
    }

    /**
     * 大运
     *
     * @param solarTime
     * @param childLimit
     * @return
     */
    public static Table getDecadeFortune(SolarTime solarTime, ChildLimit childLimit, EightChar eightChar) {

        Table table = TableBuilder.builder().build();
        table.setName("大运");
        DecadeFortune decadeFortune = childLimit.getStartDecadeFortune();
        SixtyCycle startDecaFortuneSixtyCycle = decadeFortune.getSixtyCycle().next(-1);
        table.setTitle(String.format("%s年%s月%s日（%s.%s）岁 起运", childLimit.getEndTime().getYear(), childLimit.getEndTime().getMonth(), childLimit.getEndTime().getDay(), childLimit.getYearCount(), childLimit.getMonthCount()));
        table.addRow(new Row(new Header("岁", Color.BOLD))
                .addCell(new Cell(StringUtils.join("0~", decadeFortune.getStartAge() == 0 ? decadeFortune.getStartAge() : decadeFortune.getStartAge() - 1, CHAR_AGE), Color.BLUE)));
        table.addRow(new Row(new Header("年", Color.BOLD))
        .addCell(new Cell(solarTime.getYear(), Color.BLUE)));
        table.addRow(new Row(new Header("天干", Color.BOLD))
        .addCell(new Cell(startDecaFortuneSixtyCycle.getHeavenStem(), Color.BLUE)));
        table.addRow(new Row(new Header("天神", Color.BOLD))
        .addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(startDecaFortuneSixtyCycle.getHeavenStem()), Color.CYAN)));
        table.addRow(new Row(new Header("地支", Color.BOLD))
        .addCell(new Cell(startDecaFortuneSixtyCycle.getEarthBranch(), Color.BLUE)));
        table.addRow(new Row(new Header("藏干-主", Color.BOLD))
        .addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMain()), Color.CYAN)));
        table.addRow(new Row(new Header("藏干-中", Color.BOLD))
        .addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMiddle()), Color.CYAN)));
        table.addRow(new Row(new Header("藏干-次", Color.BOLD))
        .addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemResidual()), Color.CYAN)));
        for (int i = 0; i < FORTURE_SIZE; i++) {
            SixtyCycle decadeFortuneSixtyCycle = decadeFortune.getSixtyCycle();
            table.getRowByName("岁").addCell(new Cell(StringUtils.join(decadeFortune.getStartAge(), CHAR_AGE), Color.BLUE));
            table.getRowByName("年").addCell(new Cell(decadeFortune.getStartLunarYear().getYear(), Color.BLUE));
            table.getRowByName("天干").addCell(new Cell(decadeFortune.getSixtyCycle().getHeavenStem(), Color.BLUE));
            table.getRowByName("天神").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(decadeFortuneSixtyCycle.getHeavenStem()), Color.CYAN));
            table.getRowByName("地支").addCell(new Cell(decadeFortune.getSixtyCycle().getEarthBranch(), Color.BLUE));
            table.getRowByName("藏干-主").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMain()), Color.CYAN));
            table.getRowByName("藏干-中").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMiddle()), Color.CYAN));
            table.getRowByName("藏干-次").addCell(new Cell(eightChar.getDay().getHeavenStem().getTenStar(decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemResidual()), Color.CYAN));
            decadeFortune = decadeFortune.next(1);
        }
        return table;
    }

    public static Table getBaZi(SolarTime solarTime, EightChar eightChar, ChildLimit childLimit) {
        Table table = TableBuilder.builder().build().addColumn(new Column(new Header("类目", Color.BOLD))).addColumn(new Column(new Header("年柱", Color.BOLD))).addColumn(new Column(new Header("月柱", Color.BOLD))).addColumn(new Column(new Header("日柱", Color.BOLD))).addColumn(new Column(new Header("时柱", Color.BOLD))).addColumn(new Column(new Header("大运", Color.BOLD))).addColumn(new Column(new Header("流年", Color.BOLD))).addColumn(new Column(new Header("流月", Color.BOLD)));

        table.setName("八字");
        DecadeFortune currentDecadeFortune = childLimit.getStartDecadeFortune().getCurrentDecadeFortune();

        SixtyCycle yearPillar = eightChar.getYear();
        SixtyCycle monthPillar = eightChar.getMonth();
        SixtyCycle dayPillar = eightChar.getDay();
        SixtyCycle hourPillar = eightChar.getHour();
        SixtyCycle decadeFuturePillar = currentDecadeFortune.getSixtyCycle();


        table.addRow(new Row(new Header("干神", Color.BOLD)).addCell(new Cell(dayPillar.getHeavenStem().getTenStar(yearPillar.getHeavenStem()), Color.RED)).addCell(new Cell(dayPillar.getHeavenStem().getTenStar(monthPillar.getHeavenStem()), Color.BLUE)).addCell(new Cell(StringUtils.join(childLimit.getGender().getName(), "主"), Color.CYAN)).addCell(new Cell(dayPillar.getHeavenStem().getTenStar(hourPillar.getHeavenStem()), Color.YELLOW)).addCell(new Cell(decadeFuturePillar.getHeavenStem().getTenStar(hourPillar.getHeavenStem()), Color.YELLOW)));
        table.addRow(new Row(new Header("天干", Color.BOLD)).addCell(new Cell(yearPillar.getHeavenStem(), Color.RED)).addCell(new Cell(monthPillar.getHeavenStem(), Color.BLUE)).addCell(new Cell(dayPillar.getHeavenStem(), Color.CYAN)).addCell(new Cell(hourPillar.getHeavenStem(), Color.YELLOW)).addCell(new Cell(decadeFuturePillar.getHeavenStem(), Color.YELLOW)));
        table.addRow(new Row(new Header("地支", Color.BOLD)).addCell(new Cell(yearPillar.getEarthBranch(), Color.RED)).addCell(new Cell(monthPillar.getEarthBranch(), Color.MAGENTA)).addCell(new Cell(dayPillar.getEarthBranch(), Color.GREEN)).addCell(new Cell(hourPillar.getEarthBranch(), Color.YELLOW)).addCell(new Cell(decadeFuturePillar.getEarthBranch(), Color.YELLOW)));
        table.addRow(new Row(new Header("藏干", Color.BOLD)).addCell(new Cell(hideHeavenStemStr(yearPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()), Color.RED)).addCell(new Cell(hideHeavenStemStr(monthPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()), Color.MAGENTA)).addCell(new Cell(hideHeavenStemStr(dayPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()), Color.GREEN)).addCell(new Cell(hideHeavenStemStr(hourPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()), Color.YELLOW)).addCell(new Cell(hideHeavenStemStr(decadeFuturePillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()), Color.YELLOW)));
        table.addRow(new Row(new Header("藏干", Color.BOLD)).addCell(new Cell(hideHeavenStemStr(yearPillar.getEarthBranch().getHideHeavenStemMiddle(), dayPillar.getHeavenStem()), Color.RED)).addCell(new Cell(hideHeavenStemStr(monthPillar.getEarthBranch().getHideHeavenStemMiddle(), dayPillar.getHeavenStem()), Color.MAGENTA)).addCell(new Cell(hideHeavenStemStr(dayPillar.getEarthBranch().getHideHeavenStemMiddle(), dayPillar.getHeavenStem()), Color.GREEN)).addCell(new Cell(hideHeavenStemStr(hourPillar.getEarthBranch().getHideHeavenStemMiddle(), dayPillar.getHeavenStem()), Color.YELLOW)).addCell(new Cell(hideHeavenStemStr(decadeFuturePillar.getEarthBranch().getHideHeavenStemMiddle(), dayPillar.getHeavenStem()), Color.YELLOW)));
        table.addRow(new Row(new Header("藏干", Color.BOLD)).addCell(new Cell(hideHeavenStemStr(yearPillar.getEarthBranch().getHideHeavenStemResidual(), dayPillar.getHeavenStem()), Color.RED)).addCell(new Cell(hideHeavenStemStr(monthPillar.getEarthBranch().getHideHeavenStemResidual(), dayPillar.getHeavenStem()), Color.MAGENTA)).addCell(new Cell(hideHeavenStemStr(dayPillar.getEarthBranch().getHideHeavenStemResidual(), dayPillar.getHeavenStem()), Color.GREEN)).addCell(new Cell(hideHeavenStemStr(hourPillar.getEarthBranch().getHideHeavenStemResidual(), dayPillar.getHeavenStem()), Color.YELLOW)).addCell(new Cell(hideHeavenStemStr(decadeFuturePillar.getEarthBranch().getHideHeavenStemResidual(), dayPillar.getHeavenStem()), Color.YELLOW)));
        table.addRow(new Row(new Header("地势", Color.BOLD)).addCell(new Cell(dayPillar.getHeavenStem().getTerrain(yearPillar.getEarthBranch()), Color.RED)).addCell(new Cell(dayPillar.getHeavenStem().getTerrain(monthPillar.getEarthBranch()), Color.BLUE)).addCell(new Cell(dayPillar.getHeavenStem().getTerrain(dayPillar.getEarthBranch()), Color.BLUE)).addCell(new Cell(dayPillar.getHeavenStem().getTerrain(hourPillar.getEarthBranch()), Color.YELLOW)).addCell(new Cell(dayPillar.getHeavenStem().getTerrain(decadeFuturePillar.getEarthBranch()), Color.YELLOW)));
        table.addRow(new Row(new Header("纳音", Color.BOLD)).addCell(new Cell(yearPillar.getSound(), Color.RED)).addCell(new Cell(monthPillar.getSound(), Color.MAGENTA)).addCell(new Cell(dayPillar.getSound(), Color.GREEN)).addCell(new Cell(hourPillar.getSound(), Color.YELLOW)).addCell(new Cell(CharConstant.MIDDLE, Color.YELLOW)));
        table.addRow(new Row(new Header("空亡", Color.BOLD)).addCell(new Cell(extraEarthBranchStr(yearPillar.getExtraEarthBranches()), Color.RED)).addCell(new Cell(extraEarthBranchStr(monthPillar.getExtraEarthBranches()), Color.MAGENTA)).addCell(new Cell(extraEarthBranchStr(dayPillar.getExtraEarthBranches()), Color.GREEN)).addCell(new Cell(extraEarthBranchStr(hourPillar.getExtraEarthBranches()), Color.YELLOW)).addCell(new Cell(CharConstant.MIDDLE, Color.YELLOW)));


        return table;
    }

    private static String extraEarthBranchStr(EarthBranch[] extraEarthBranches) {
        if (null == extraEarthBranches) {
            return StringUtils.EMPTY;
        }
        String res = StringUtils.EMPTY;
        //stream拼接name
        for (EarthBranch earthBranch : extraEarthBranches) {
            res = StringUtils.join(res, earthBranch.getName());
        }
        return res;
    }

    private static String hideHeavenStemStr(HeavenStem heavenStem, HeavenStem dayHeavenStem) {
        if (null == heavenStem) {
            return StringUtils.EMPTY;
        }
        return StringUtils.join(heavenStem.getName(), CharConstant.PERIOD, heavenStem.getElement(), CharConstant.PERIOD, dayHeavenStem.getTenStar(heavenStem));
    }


    public static SolarTime getTrueSolarTime(Birth birth) {
        String address = StringUtils.join(birth.getAddress().getProvince(), birth.getAddress().getCity(), birth.getAddress().getDistrict());
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
        return solarTimeAfter;
    }

    public static EightChar getEightChar(SolarTime solarTime) {
        LunarHour lunarHour = solarTime.getLunarHour();
        return lunarHour.getEightChar();
    }

}
