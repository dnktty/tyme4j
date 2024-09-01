package com.tyme.app.paipan;

import com.tyme.app.table.*;
import com.tyme.constants.CharConstant;
import com.tyme.constants.TermEnum;
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
  private static final String CHAR_AGE = TermEnum.SUI.show();
  private static final String CHAR_MONTH = TermEnum.MONTH.show();

  public static void getPaiPan(Birth birth) {

    SolarTime solarTime = getTrueSolarTime(birth);
    // 八字
    EightChar eightChar = getEightChar(solarTime);
    // 流年
    ChildLimit childLimit = ChildLimit.fromSolarTime(solarTime, birth.getGender());

    Table baZiTable = getBaZi(solarTime, eightChar, childLimit);
    TableUtil.printTable(baZiTable);
    Table decadeFortuneTable = getDecadeFortune(solarTime, childLimit, eightChar);
    TableUtil.printTable(decadeFortuneTable);
    Table flowYearTable = getFlowYear(solarTime, childLimit, eightChar);
    TableUtil.printTable(flowYearTable);
    Table flowMonthTable = getFlowMonth(solarTime, childLimit, eightChar);
    TableUtil.printTable(flowMonthTable);
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
    table.setName(TermEnum.LIU_YUE.show());
    table.addColumn(
        new Column(new Header(TermEnum.MONTH.show()))
            .addCell(new Cell(TermEnum.MONTH.show()))
            .addCell(new Cell((TermEnum.JIE_QI_RI.show())))
            .addCell(new Cell((TermEnum.TIAN_GAN.show())))
            .addCell(new Cell((TermEnum.GAN_SHENG.show())))
            .addCell(new Cell((TermEnum.DI_ZHI.show())))
            .addCell(new Cell((TermEnum.BEN_QI.show())))
            .addCell(new Cell((TermEnum.ZHONG_QI.show())))
            .addCell(new Cell((TermEnum.YU_QI.show()))));
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
      table.addColumn(
          new Column(new Header(StringUtils.join(solarTerm.getName(), CHAR_MONTH), Color.RESET))
              .addCell(
                  new Cell(
                      StringUtils.join(
                          solarTerm.getJulianDay().getSolarDay().getMonth(),
                          CharConstant.PERIOD,
                          solarTerm.getJulianDay().getSolarDay().getDay()),
                      Color.RESET))
              .addCell(
                  new Cell(
                      flowLunarMonth.getSixtyCycle().getHeavenStem(),
                      flowLunarMonth.getSixtyCycle().getHeavenStem().getElement().getColorVal()))
              .addCell(
                  new Cell(
                      eightChar
                          .getDay()
                          .getHeavenStem()
                          .getTenStar(flowLunarMonthSixtyCycle.getHeavenStem()),
                      flowLunarMonth.getSixtyCycle().getHeavenStem().getElement().getColorVal()))
              .addCell(
                  new Cell(
                      flowLunarMonth.getSixtyCycle().getEarthBranch(),
                      flowLunarMonth.getSixtyCycle().getEarthBranch().getElement().getColorVal()))
              .addCell(
                  hideHeavenStemStr(
                      flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemMain(),
                      eightChar.getDay().getHeavenStem()))
              .addCell(
                  hideHeavenStemStr(
                      flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemMiddle(),
                      eightChar.getDay().getHeavenStem()))
              .addCell(
                  hideHeavenStemStr(
                      flowLunarMonthSixtyCycle.getEarthBranch().getHideHeavenStemResidual(),
                      eightChar.getDay().getHeavenStem())));
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
    table.setName(TermEnum.LIU_NIAN.show());
    table.addColumn(
        new Column(new Header(TermEnum.SUI.show()))
            .addCell(new Cell((TermEnum.NIAN.show())))
            .addCell(new Cell((TermEnum.TIAN_GAN.show())))
            .addCell(new Cell((TermEnum.GAN_SHENG.show())))
            .addCell(new Cell((TermEnum.DI_ZHI.show())))
            .addCell(new Cell((TermEnum.BEN_QI.show())))
            .addCell(new Cell((TermEnum.ZHONG_QI.show())))
            .addCell(new Cell((TermEnum.YU_QI.show()))));
    DecadeFortune currentDecadeFortune =
        childLimit.getStartDecadeFortune().getCurrentDecadeFortune();
    int flowStartAge = currentDecadeFortune.getStartAge();
    LunarYear flowLunarYear = currentDecadeFortune.getStartLunarYear();
    for (int i = 0; i < FLOW_SIZE; i++) {
      SixtyCycle flowLunarYearSixtyCycle = flowLunarYear.getSixtyCycle();
      table.addColumn(
          new Column(new Header(StringUtils.join(flowStartAge + i, CHAR_AGE)))
                  .addCell(new Cell(flowLunarYear.getYear(), Color.RESET))
                  .addCell(
                  new Cell(
                      flowLunarYearSixtyCycle.getHeavenStem(),
                      flowLunarYearSixtyCycle.getHeavenStem().getElement().getColorVal()))
              .addCell(
                  new Cell(
                      eightChar
                          .getDay()
                          .getHeavenStem()
                          .getTenStar(flowLunarYearSixtyCycle.getHeavenStem()),
                      flowLunarYearSixtyCycle.getHeavenStem().getElement().getColorVal()))
              .addCell(
                  new Cell(
                      flowLunarYearSixtyCycle.getEarthBranch(),
                      flowLunarYearSixtyCycle.getEarthBranch().getElement().getColorVal()))
              .addCell(
                  hideHeavenStemStr(
                      flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemMain(),
                      eightChar.getDay().getHeavenStem()))
              .addCell(
                  hideHeavenStemStr(
                      flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemMiddle(),
                      eightChar.getDay().getHeavenStem()))
              .addCell(
                  hideHeavenStemStr(
                      flowLunarYearSixtyCycle.getEarthBranch().getHideHeavenStemResidual(),
                      eightChar.getDay().getHeavenStem())));
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
    table.setName(TermEnum.DA_YUN.show());
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
    table.addColumn(
        new Column(new Header(TermEnum.SUI.show()))
            .addCell(new Cell(TermEnum.NIAN.show(), Color.BOLD))
            .addCell(new Cell(TermEnum.TIAN_GAN.show(), Color.BOLD))
            .addCell(new Cell(TermEnum.GAN_SHENG.show(), Color.BOLD))
            .addCell(new Cell(TermEnum.DI_ZHI.show(), Color.BOLD))
            .addCell(new Cell(TermEnum.BEN_QI.show(), Color.BOLD))
            .addCell(new Cell(TermEnum.ZHONG_QI.show(), Color.BOLD))
            .addCell(new Cell(TermEnum.YU_QI.show(), Color.BOLD)));
    table.addColumn(
        new Column(
                new Header(
                    StringUtils.join(
                        "0~",
                        decadeFortune.getStartAge() == 0
                            ? decadeFortune.getStartAge()
                            : decadeFortune.getStartAge() - 1,
                        CHAR_AGE),
                    Color.RESET))
            .addCell(
                new Cell(
                        decadeFortune.getStartLunarYear().getYear(), Color.RESET))
            .addCell(
                new Cell(
                    startDecaFortuneSixtyCycle.getHeavenStem(),
                    startDecaFortuneSixtyCycle.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    eightChar
                        .getDay()
                        .getHeavenStem()
                        .getTenStar(startDecaFortuneSixtyCycle.getHeavenStem()),
                    startDecaFortuneSixtyCycle.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    startDecaFortuneSixtyCycle.getEarthBranch(),
                    startDecaFortuneSixtyCycle.getEarthBranch().getElement().getColorVal()))
            .addCell(
                hideHeavenStemStr(
                    startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMain(),
                    eightChar.getDay().getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMiddle(),
                    eightChar.getDay().getHeavenStem()))
            .addCell(
                hideHeavenStemStr(
                    startDecaFortuneSixtyCycle.getEarthBranch().getHideHeavenStemResidual(),
                    eightChar.getDay().getHeavenStem())));
    for (int i = 0; i < FORTURE_SIZE; i++) {
      SixtyCycle decadeFortuneSixtyCycle = decadeFortune.getSixtyCycle();
      table
          .addColumn(new Column(new Header(StringUtils.join(decadeFortune.getStartAge(), CHAR_AGE), Color.RESET))
          .addCell(new Cell(decadeFortune.getStartLunarYear().getYear(), Color.RESET))
          .addCell(
              new Cell(
                  decadeFortune.getSixtyCycle().getHeavenStem(),
                  decadeFortune.getSixtyCycle().getHeavenStem().getElement().getColorVal()))
          .addCell(
              new Cell(
                  eightChar
                      .getDay()
                      .getHeavenStem()
                      .getTenStar(decadeFortuneSixtyCycle.getHeavenStem()),
                  decadeFortune.getSixtyCycle().getHeavenStem().getElement().getColorVal()))
          .addCell(
              new Cell(
                  decadeFortune.getSixtyCycle().getEarthBranch(),
                  decadeFortune.getSixtyCycle().getEarthBranch().getElement().getColorVal()))
          .addCell(
              hideHeavenStemStr(
                  decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMain(),
                  eightChar.getDay().getHeavenStem()))
          .addCell(
              hideHeavenStemStr(
                  decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemMiddle(),
                  eightChar.getDay().getHeavenStem()))
          .addCell(
              hideHeavenStemStr(
                  decadeFortuneSixtyCycle.getEarthBranch().getHideHeavenStemResidual(),
                  eightChar.getDay().getHeavenStem())));
      decadeFortune = decadeFortune.next(1);
    }
    return table;
  }

  public static Table getBaZi(SolarTime solarTime, EightChar eightChar, ChildLimit childLimit) {
    Table table =
        TableBuilder.builder()
            .build()
            .addColumn(new Column(new Header(TermEnum.LEI_MU.show(), Color.BOLD, 10)))
            .addColumn(new Column(new Header(TermEnum.NIAN_ZHU.show(), Color.BOLD)))
            .addColumn(new Column(new Header(TermEnum.YUE_ZHU.show(), Color.BOLD)))
            .addColumn(new Column(new Header(TermEnum.RI_ZHU.show(), Color.BOLD)))
            .addColumn(new Column(new Header(TermEnum.SHI_ZHU.show(), Color.BOLD)))
            .addColumn(new Column(new Header(TermEnum.DA_YUN.show(), Color.BOLD)))
            .addColumn(new Column(new Header(TermEnum.LIU_NIAN.show(), Color.BOLD)))
            .addColumn(new Column(new Header(TermEnum.LIU_YUE.show(), Color.BOLD)));

    table.setName(TermEnum.BA_ZI.show());
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
        new Row().addCell(new Cell(TermEnum.GAN_SHENG.show(), Color.BOLD))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(yearPillar.getHeavenStem()),
                    yearPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(monthPillar.getHeavenStem()),
                    monthPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    StringUtils.join(TermEnum.YUAN.show(), childLimit.getGender().getName()),
                    Color.RESET))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(hourPillar.getHeavenStem()),
                    hourPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(decadeFuturePillar.getHeavenStem()),
                    decadeFuturePillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(yearSixtyCycle.getHeavenStem()),
                    yearSixtyCycle.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem().getTenStar(monthSixtyCycle.getHeavenStem()),
                    monthSixtyCycle.getHeavenStem().getElement().getColorVal())));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.TIAN_GAN.show(), Color.BOLD))
            .addCell(
                new Cell(
                    yearPillar.getHeavenStem(),
                    monthPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    monthPillar.getHeavenStem(),
                    monthPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    dayPillar.getHeavenStem(), dayPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    hourPillar.getHeavenStem(), hourPillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    decadeFuturePillar.getHeavenStem(),
                    decadeFuturePillar.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    yearSixtyCycle.getHeavenStem(),
                    yearSixtyCycle.getHeavenStem().getElement().getColorVal()))
            .addCell(
                new Cell(
                    monthSixtyCycle.getHeavenStem(),
                    monthSixtyCycle.getHeavenStem().getElement().getColorVal())));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.DI_ZHI.show(), Color.BOLD))
            .addCell(
                new Cell(
                    yearPillar.getEarthBranch(),
                    yearPillar.getEarthBranch().getElement().getColorVal()))
            .addCell(
                new Cell(
                    monthPillar.getEarthBranch(),
                    monthPillar.getEarthBranch().getElement().getColorVal()))
            .addCell(
                new Cell(
                    dayPillar.getEarthBranch(), dayPillar.getEarthBranch().getElement().getColorVal()))
            .addCell(
                new Cell(
                    hourPillar.getEarthBranch(),
                    hourPillar.getEarthBranch().getElement().getColorVal()))
            .addCell(
                new Cell(
                    decadeFuturePillar.getEarthBranch(),
                    decadeFuturePillar.getEarthBranch().getElement().getColorVal()))
            .addCell(
                new Cell(
                    yearSixtyCycle.getEarthBranch(),
                    yearSixtyCycle.getEarthBranch().getElement().getColorVal()))
            .addCell(
                new Cell(
                    monthSixtyCycle.getEarthBranch(),
                    monthSixtyCycle.getEarthBranch().getElement().getColorVal())));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.BEN_QI.show(), Color.BOLD))
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
                    dayPillar.getHeavenStem())));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.ZHONG_QI.show(), Color.BOLD))
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
                    dayPillar.getHeavenStem())));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.YU_QI.show(), Color.BOLD))
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
                    dayPillar.getHeavenStem())));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.DI_SHI.show(), Color.BOLD))
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
        new Row().addCell(new Cell(TermEnum.NA_YIN.show(), Color.BOLD))
            .addCell(new Cell(yearPillar.getSound(), Color.RESET))
            .addCell(new Cell(monthPillar.getSound(), Color.RESET))
            .addCell(new Cell(dayPillar.getSound(), Color.RESET))
            .addCell(new Cell(hourPillar.getSound(), Color.RESET))
            .addCell(new Cell(CharConstant.MIDDLE, Color.RESET)));
    table.addRow(
        new Row().addCell(new Cell(TermEnum.KONG_WANG.show(), Color.BOLD))
            .addCell(new Cell(extraEarthBranchStr(yearPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(
                new Cell(extraEarthBranchStr(monthPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(new Cell(extraEarthBranchStr(dayPillar.getExtraEarthBranches()), Color.RESET))
            .addCell(new Cell(extraEarthBranchStr(hourPillar.getExtraEarthBranches()), Color.RESET))
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
            heavenStem.getName(), CharConstant.PERIOD, dayHeavenStem.getTenStar(heavenStem)),
        heavenStem.getElement().getColorVal());
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
