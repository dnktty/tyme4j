package com.tyme.app.paipan;

import com.tyme.app.table.*;
import com.tyme.constants.CharConstant;
import com.tyme.culture.Color;
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
 */
@Slf4j
public class PaiPanUtil {
  private static final int FORTURE_SIZE = 9;
  private static final int FLOW_SIZE = 10;
  private static final String CHAR_AGE = "岁";
  private static final String CHAR_MONTH = "月";

  public static void getPaiPan(Birth birth) {

    SolarTime solarTime = getTrueSolarTime(birth);
    // 八字
    EightChar eightChar = getEightChar(solarTime);
    // 流年
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
  public static Table getFlowMonth(
      SolarTime solarTime, ChildLimit childLimit, EightChar eightChar) {
    Table table = TableBuilder.builder().build();
    table.setName("流月");
    table
        .addRow(new Row(new Header("月")))
        .addRow(new Row(new Header("节气日")))
        .addRow(new Row(new Header("天干")))
        .addRow(new Row(new Header("天神")))
        .addRow(new Row(new Header("地支")))
        .addRow(new Row(new Header("藏干-本气")))
        .addRow(new Row(new Header("藏干-中气")))
        .addRow(new Row(new Header("藏干-余气")));
    LocalDateTime localDateTime = LocalDateTime.now();
    LunarYear currentlunarYear =
        LunarYear.fromYear(
            SolarTime.fromYmdHms(
                    localDateTime.getYear(),
                    localDateTime.getMonthValue(),
                    localDateTime.getDayOfMonth(),
                    localDateTime.getHour(),
                    localDateTime.getMinute(),
                    localDateTime.getSecond())
                .getLunarHour()
                .getYear());
    for (LunarMonth flowLunarMonth : currentlunarYear.getMonths()) {
      SixtyCycle flowLunarMonthSixtyCycle = flowLunarMonth.getSixtyCycle();
      SolarTerm solarTerm = flowLunarMonth.getFirstJulianDay().getSolarDay().getTerm();
      table
          .getRowByName("月")
          .addCell(new Cell(StringUtils.join(solarTerm.getName(), CHAR_MONTH), Color.RESET));
      table
          .getRowByName("节气日")
          .addCell(
              new Cell(
                  StringUtils.join(
                      solarTerm.getJulianDay().getSolarDay().getMonth(),
                      CharConstant.PERIOD,
                      solarTerm.getJulianDay().getSolarDay().getDay()),
                  Color.RESET));
      table
          .getRowByName("天干")
          .addCell(
              new Cell(
                  flowLunarMonth.getSixtyCycle().getHeavenStem(),
                  flowLunarMonth.getSixtyCycle().getHeavenStem().getElement().getColor()));
      table
          .getRowByName("天神")
          .addCell(
              new Cell(
                  eightChar
                      .getDay()
                      .getHeavenStem()
                      .getTenStar(flowLunarMonthSixtyCycle.getHeavenStem()),
                  flowLunarMonth.getSixtyCycle().getHeavenStem().getElement().getColor()));
      table
          .getRowByName("地支")
          .addCell(
              new Cell(
                  flowLunarMonth.getSixtyCycle().getEarthBranch(),
                  flowLunarMonth.getSixtyCycle().getEarthBranch().getElement().getColor()));
      HeavenStem heavenStemMain = flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemMain();
      if (null != heavenStemMain) {
        table
            .getRowByName("藏干-本气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemMain),
                    heavenStemMain.getElement().getColor()));
      }
      HeavenStem heavenStemMiddle =
          flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemMiddle();
      if (null != heavenStemMiddle) {
        table
            .getRowByName("藏干-中气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemMiddle),
                    heavenStemMiddle.getElement().getColor()));
      }
      HeavenStem heavenStemResidual =
          flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemResidual();
      if (null != heavenStemResidual) {
        table
            .getRowByName("藏干-余气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemResidual),
                    heavenStemResidual.getElement().getColor()));
      }
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
    table
        .addRow(new Row(new Header("岁")))
        .addRow(new Row(new Header("年")))
        .addRow(new Row(new Header("天干")))
        .addRow(new Row(new Header("天神")))
        .addRow(new Row(new Header("地支")))
        .addRow(new Row(new Header("藏干-本气")))
        .addRow(new Row(new Header("藏干-中气")))
        .addRow(new Row(new Header("藏干-余气")));
    DecadeFortune currentDecadeFortune =
        childLimit.getStartDecadeFortune().getCurrentDecadeFortune();
    int flowStartAge = currentDecadeFortune.getStartAge();
    LunarYear flowLunarYear = currentDecadeFortune.getStartLunarYear();
    for (int i = 0; i < FLOW_SIZE; i++) {
      SixtyCycle flowLunarYearSixtyCycle = flowLunarYear.getSixtyCycle();
      table
          .getRowByName("岁")
          .addCell(new Cell(StringUtils.join(flowStartAge + i, CHAR_AGE), Color.RESET));
      table.getRowByName("年").addCell(new Cell(flowLunarYear.getYear(), Color.RESET));
      table
          .getRowByName("天干")
          .addCell(
              new Cell(
                  flowLunarYearSixtyCycle.getHeavenStem(),
                  flowLunarYearSixtyCycle.getHeavenStem().getElement().getColor()));
      table
          .getRowByName("天神")
          .addCell(
              new Cell(
                  eightChar
                      .getDay()
                      .getHeavenStem()
                      .getTenStar(flowLunarYearSixtyCycle.getHeavenStem()),
                  flowLunarYearSixtyCycle.getHeavenStem().getElement().getColor()));
      table
          .getRowByName("地支")
          .addCell(
              new Cell(
                  flowLunarYearSixtyCycle.getEarthBranch(),
                  flowLunarYearSixtyCycle.getEarthBranch().getElement().getColor()));
      HeavenStem heavenStemMain = flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemMain();
      if (null != heavenStemMain) {
        table
            .getRowByName("藏干-本气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemMain),
                    heavenStemMain.getElement().getColor()));
      }
      HeavenStem heavenStemMiddle =
          flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemMiddle();
      if (null != heavenStemMiddle) {
        table
            .getRowByName("藏干-中气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemMiddle),
                    heavenStemMiddle.getElement().getColor()));
      }
      HeavenStem heavenStemResidual =
          flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemResidual();
      if (null != heavenStemResidual) {
        table
            .getRowByName("藏干-余气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemResidual),
                    heavenStemResidual.getElement().getColor()));
      }
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
  public static Table getDecadeFortune(
      SolarTime solarTime, ChildLimit childLimit, EightChar eightChar) {

    Table table = TableBuilder.builder().build();
    table.setName("大运");
    DecadeFortune decadeFortune = childLimit.getStartDecadeFortune();
    SixtyCycle startDecaFortuneSixtyCycle = decadeFortune.getSixtyCycle().next(-1);
    table.setTitle(
        String.format(
            "%s年%s月%s日（%s.%s）岁 起运",
            childLimit.getEndTime().getYear(),
            childLimit.getEndTime().getMonth(),
            childLimit.getEndTime().getDay(),
            childLimit.getYearCount(),
            childLimit.getMonthCount()));
    table
        .addRow(new Row(new Header("岁", Color.BOLD)))
        .addRow(new Row(new Header("年", Color.BOLD)))
        .addRow(new Row(new Header("天干", Color.BOLD)))
        .addRow(new Row(new Header("天神", Color.BOLD)))
        .addRow(new Row(new Header("地支", Color.BOLD)))
        .addRow(new Row(new Header("藏干-本气", Color.BOLD)))
        .addRow(new Row(new Header("藏干-中气", Color.BOLD)))
        .addRow(new Row(new Header("藏干-余气", Color.BOLD)));
    table
        .getRowByName("岁")
        .addCell(
            new Cell(
                StringUtils.join(
                    "0~",
                    decadeFortune.getStartAge() == 0
                        ? decadeFortune.getStartAge()
                        : decadeFortune.getStartAge() - 1,
                    CHAR_AGE),
                Color.RESET));
    table.getRowByName("年").addCell(new Cell(solarTime.getYear(), Color.RESET));
    table
        .getRowByName("天干")
        .addCell(
            new Cell(
                startDecaFortuneSixtyCycle.getHeavenStem(),
                startDecaFortuneSixtyCycle.getHeavenStem().getElement().getColor()));
    table
        .getRowByName("天神")
        .addCell(
            new Cell(
                eightChar
                    .getDay()
                    .getHeavenStem()
                    .getTenStar(startDecaFortuneSixtyCycle.getHeavenStem()),
                startDecaFortuneSixtyCycle.getHeavenStem().getElement().getColor()));
    table
        .getRowByName("地支")
        .addCell(
            new Cell(
                startDecaFortuneSixtyCycle.getEarthBranch(),
                startDecaFortuneSixtyCycle.getEarthBranch().getElement().getColor()));

    HeavenStem heavenStemMain1 =
        startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMain();
    if (null != heavenStemMain1) {
      table
          .getRowByName("藏干-本气")
          .addCell(
              new Cell(
                  eightChar.getDay().getHeavenStem().getTenStar(heavenStemMain1),
                  heavenStemMain1.getElement().getColor()));
    }
    HeavenStem heavenStemMiddle1 =
        startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMiddle();
    if (null != heavenStemMiddle1) {
      table
          .getRowByName("藏干-中气")
          .addCell(
              new Cell(
                  eightChar.getDay().getHeavenStem().getTenStar(heavenStemMiddle1),
                  heavenStemMiddle1.getElement().getColor()));
    }
    HeavenStem heavenStemResidual1 =
        startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemResidual();
    if (null != heavenStemResidual1) {
      table
          .getRowByName("藏干-余气")
          .addCell(
              new Cell(
                  eightChar.getDay().getHeavenStem().getTenStar(heavenStemResidual1),
                  heavenStemResidual1.getElement().getColor()));
    }
    for (int i = 0; i < FORTURE_SIZE; i++) {
      SixtyCycle decadeFortuneSixtyCycle = decadeFortune.getSixtyCycle();
      table
          .getRowByName("岁")
          .addCell(new Cell(StringUtils.join(decadeFortune.getStartAge(), CHAR_AGE), Color.RESET));
      table
          .getRowByName("年")
          .addCell(new Cell(decadeFortune.getStartLunarYear().getYear(), Color.RESET));
      table
          .getRowByName("天干")
          .addCell(
              new Cell(
                  decadeFortune.getSixtyCycle().getHeavenStem(),
                  decadeFortune.getSixtyCycle().getHeavenStem().getElement().getColor()));
      table
          .getRowByName("天神")
          .addCell(
              new Cell(
                  eightChar
                      .getDay()
                      .getHeavenStem()
                      .getTenStar(decadeFortuneSixtyCycle.getHeavenStem()),
                  decadeFortune.getSixtyCycle().getHeavenStem().getElement().getColor()));
      table
          .getRowByName("地支")
          .addCell(
              new Cell(
                  decadeFortune.getSixtyCycle().getEarthBranch(),
                  decadeFortune.getSixtyCycle().getEarthBranch().getElement().getColor()));
      HeavenStem heavenStemMain = decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMain();
      if (null != heavenStemMain) {
        table
            .getRowByName("藏干-本气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemMain),
                    heavenStemMain.getElement().getColor()));
      }
      HeavenStem heavenStemMiddle =
          decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMiddle();
      if (null != heavenStemMiddle) {
        table
            .getRowByName("藏干-中气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemMiddle),
                    heavenStemMiddle.getElement().getColor()));
      }
      HeavenStem heavenStemResidual =
          decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemResidual();
      if (null != heavenStemResidual) {
        table
            .getRowByName("藏干-余气")
            .addCell(
                new Cell(
                    eightChar.getDay().getHeavenStem().getTenStar(heavenStemResidual),
                    heavenStemResidual.getElement().getColor()));
      }
      decadeFortune = decadeFortune.next(1);
    }
    return table;
  }

  public static Table getBaZi(SolarTime solarTime, EightChar eightChar, ChildLimit childLimit) {
    Table table =
        TableBuilder.builder()
            .build()
            .addColumn(new Column(new Header("类目", Color.BOLD)))
            .addColumn(new Column(new Header("年柱", Color.BOLD)))
            .addColumn(new Column(new Header("月柱", Color.BOLD)))
            .addColumn(new Column(new Header("日柱", Color.BOLD)))
            .addColumn(new Column(new Header("时柱", Color.BOLD)))
            .addColumn(new Column(new Header("大运", Color.BOLD)))
            .addColumn(new Column(new Header("流年", Color.BOLD)))
            .addColumn(new Column(new Header("流月", Color.BOLD)));

    table.setName("八字");
    DecadeFortune currentDecadeFortune =
        childLimit.getStartDecadeFortune().getCurrentDecadeFortune();

    SixtyCycle yearPillar = eightChar.getYear();
    SixtyCycle monthPillar = eightChar.getMonth();
    SixtyCycle dayPillar = eightChar.getDay();
    SixtyCycle hourPillar = eightChar.getHour();
    SixtyCycle decadeFuturePillar = currentDecadeFortune.getSixtyCycle();
    LocalDateTime localDateTime = LocalDateTime.now();

    SolarTime currentSolarTime =
        SolarTime.fromYmdHms(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond());
    SixtyCycle yearSixtyCycle =
        LunarYear.fromYear(currentSolarTime.getLunarHour().getYear()).getSixtyCycle();
    SixtyCycle monthSixtyCycle =
        LunarMonth.fromYm(
                currentSolarTime.getLunarHour().getYear(),
                currentSolarTime.getLunarHour().getMonth())
            .getSixtyCycle();
    table.addRow(
        new Row(new Header("干神", Color.BOLD))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(yearPillar.getHeavenStem()),
                    yearPillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(monthPillar.getHeavenStem()),
                    monthPillar.getHeavenStem().getElement().getColor()))
            .addCell(new Cell(StringUtils.join(childLimit.getGender().getName(), "主"), Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(hourPillar.getHeavenStem()),
                    hourPillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(decadeFuturePillar.getHeavenStem()),
                    decadeFuturePillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(yearSixtyCycle.getHeavenStem()),
                    yearSixtyCycle.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(monthSixtyCycle.getHeavenStem()),
                    monthSixtyCycle.getHeavenStem().getElement().getColor())));
    table.addRow(
        new Row(new Header("天干", Color.BOLD))
            .addCell(
                new Cell(
                    yearPillar.getHeavenStem(),
                    monthPillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    monthPillar.getHeavenStem(),
                    monthPillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem(), dayPillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    hourPillar.getHeavenStem(), hourPillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    decadeFuturePillar.getHeavenStem(),
                    decadeFuturePillar.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    yearSixtyCycle.getHeavenStem(),
                    yearSixtyCycle.getHeavenStem().getElement().getColor()))
            .addCell(
                new Cell(
                    monthSixtyCycle.getHeavenStem(),
                    monthSixtyCycle.getHeavenStem().getElement().getColor())));
    table.addRow(
        new Row(new Header("地支", Color.BOLD))
            .addCell(
                new Cell(
                    yearPillar.getEarthBranch(),
                    yearPillar.getEarthBranch().getElement().getColor()))
            .addCell(
                new Cell(
                    monthPillar.getEarthBranch(),
                    monthPillar.getEarthBranch().getElement().getColor()))
            .addCell(
                new Cell(
                    dayPillar.getEarthBranch(), dayPillar.getEarthBranch().getElement().getColor()))
            .addCell(
                new Cell(
                    hourPillar.getEarthBranch(),
                    hourPillar.getEarthBranch().getElement().getColor()))
            .addCell(
                new Cell(
                    decadeFuturePillar.getEarthBranch(),
                    decadeFuturePillar.getEarthBranch().getElement().getColor()))
            .addCell(
                new Cell(
                    yearSixtyCycle.getEarthBranch(),
                    yearSixtyCycle.getEarthBranch().getElement().getColor()))
            .addCell(
                new Cell(
                    monthSixtyCycle.getEarthBranch(),
                    monthSixtyCycle.getEarthBranch().getElement().getColor())));
    table.addRow(
        new Row(new Header("藏干-本气", Color.BOLD))
            .addCell(
                hideHeavenStemStr(
                    yearPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    monthPillar.getEarthBranch().getHideHeavenStemMain(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    dayPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    hourPillar.getEarthBranch().getHideHeavenStemMain(), dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    decadeFuturePillar.getEarthBranch().getHideHeavenStemMain(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    yearSixtyCycle.getEarthBranch().getHideHeavenStemMain(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    monthSixtyCycle.getEarthBranch().getHideHeavenStemMain(),
                    dayPillar.getHeavenStem()))
    );
    table.addRow(
        new Row(new Header("藏干-中气", Color.BOLD))
            .addCell(
                hideHeavenStemStr(
                    yearPillar.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    monthPillar.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    dayPillar.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    hourPillar.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    decadeFuturePillar.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    yearSixtyCycle.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    monthSixtyCycle.getEarthBranch().getHideHeavenStemMiddle(),
                    dayPillar.getHeavenStem()))
    );
    table.addRow(
        new Row(new Header("藏干-余气", Color.BOLD))
            .addCell(
                hideHeavenStemStr(
                    yearPillar.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    monthPillar.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    dayPillar.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    hourPillar.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    decadeFuturePillar.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    yearSixtyCycle.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    monthSixtyCycle.getEarthBranch().getHideHeavenStemResidual(),
                    dayPillar.getHeavenStem()))
    );
    table.addRow(
        new Row(new Header("地势", Color.BOLD))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(yearPillar.getEarthBranch()), Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(monthPillar.getEarthBranch()),
                    Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(dayPillar.getEarthBranch()), Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(hourPillar.getEarthBranch()), Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(decadeFuturePillar.getEarthBranch()),
                    Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(yearSixtyCycle.getEarthBranch()),
                    Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTerrain(monthSixtyCycle.getEarthBranch()),
                    Color.RESET)));
    ;
    table.addRow(
        new Row(new Header("纳音", Color.BOLD))
            .addCell(new Cell(yearPillar.getSound(), Color.RESET))
            .addCell(new Cell(monthPillar.getSound(), Color.RESET))
            .addCell(new Cell(dayPillar.getSound(), Color.RESET))
            .addCell(new Cell(hourPillar.getSound(), Color.RESET))
            .addCell(new Cell(CharConstant.MIDDLE, Color.RESET)));
    table.addRow(
        new Row(new Header("空亡", Color.BOLD))
            .addCell(new Cell(extraEarthBranchStr(yearPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(
                new Cell(extraEarthBranchStr(monthPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(new Cell(extraEarthBranchStr(dayPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(
                new Cell(extraEarthBranchStr(hourPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(new Cell(CharConstant.MIDDLE, Color.RESET)));

    return table;
  }

  private static String extraEarthBranchStr(EarthBranch[] extraEarthBranches) {
    if (null == extraEarthBranches) {
      return StringUtils.EMPTY;
    }
    String res = StringUtils.EMPTY;
    // stream拼接name
    for (EarthBranch earthBranch : extraEarthBranches) {
      res = StringUtils.join(res, earthBranch.getName());
    }
    return res;
  }

  private static Cell hideHeavenStemStr(HeavenStem heavenStem, HeavenStem dayHeavenStem) {
    if (null == heavenStem) {
      return new Cell(StringUtils.EMPTY, Color.RESET);
    }
    return new Cell(
        StringUtils.join(
            heavenStem.getName(),
            CharConstant.PERIOD,
            dayHeavenStem.getTenStar(heavenStem)),
        heavenStem.getElement().getColor());
  }

  public static SolarTime getTrueSolarTime(Birth birth) {
    String address =
        StringUtils.join(
            birth.getAddress().getProvince(),
            birth.getAddress().getCity(),
            birth.getAddress().getDistrict());
    String birthDay = birth.getBirthDay();
    // 获取经纬度
    // 1、根据地址获取经纬度
    Geography geo = GeographyUtil.getLonAndLat(address, null);
    LocalDateTime localDateTime =
        LocalDateTime.parse(birthDay, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LunarHour lunarHour1 =
        LunarHour.fromYmdHms(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond());
    // 输出城市的真太阳时
    localDateTime =
        LocalDateTime.parse(
            lunarHour1.getSolarTime().toString(),
            DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
    ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of(birth.getTimeZone().getId()));
    LocalDateTime trueSolarTime =
        TrueSolarTime.getTrueSolarTime(geo.getLongitude(), geo.getLatitude(), localZonedDateTime);
    // 从真太阳时获取农历时辰
    SolarTime solarTimeAfter =
        SolarTime.fromYmdHms(
            trueSolarTime.getYear(),
            trueSolarTime.getMonthValue(),
            trueSolarTime.getDayOfMonth(),
            trueSolarTime.getHour(),
            trueSolarTime.getMinute(),
            trueSolarTime.getSecond());
    return solarTimeAfter;
  }

  public static EightChar getEightChar(SolarTime solarTime) {
    LunarHour lunarHour = solarTime.getLunarHour();
    return lunarHour.getEightChar();
  }
}
