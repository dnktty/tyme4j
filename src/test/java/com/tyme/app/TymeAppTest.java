package com.tyme.app;

import com.alibaba.fastjson.JSON;
import com.tyme.constants.CharConstant;
import com.tyme.ditu.Geography;
import com.tyme.ditu.GeographyUtil;
import com.tyme.eightchar.ChildLimit;
import com.tyme.eightchar.DecadeFortune;
import com.tyme.eightchar.Fortune;
import com.tyme.enums.Gender;
import com.tyme.lunar.LunarHour;
import com.tyme.solar.SolarTime;
import com.tyme.solar.TrueSolarTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-06
 **/
@Slf4j
public class TymeAppTest {

    @Test
    public void testBaZi2(){
        Address address = new Address("湖南省", "长沙市", "岳麓区");
        String birthDay = "1986-09-26 21:00:00";
        Birth birth = new Birth(address, ZoneId.of("+8"), birthDay);
        PaiPan paiPan = PaiPanUtil.getPaiPan(birth);
        log.info("排盘信息：{}", JSON.toJSONString(paiPan));
    }
    @Test
    public void testBaZi(){

        String address = "湖南省长沙市";
        String birth = "1986-09-26 21:00:00";
        //获取经纬度
        // 1、根据地址获取经纬度
        Geography geo = GeographyUtil.getLonAndLat(address, null);

        LunarHour lunarHour1 = LunarHour.fromYmdHms(1986, 9, 26, 21, 00, 0);
        // 输出城市的真太阳时
        LocalDateTime localDateTime = LocalDateTime.parse(lunarHour1.getSolarTime().toString(), DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("+0"));
        LocalDateTime trueSolarTime = TrueSolarTime.getTrueSolarTime(geo.getLatitude(), geo.getLongitude(), localZonedDateTime);

//        SolarTime solarTimeAfter = SolarTime.fromYmdHms(trueSolarTime.getYear(), trueSolarTime.getMonthValue(), trueSolarTime.getDayOfMonth(), trueSolarTime.getHour(), trueSolarTime.getMinute(), trueSolarTime.getSecond());
//        LunarHour lunarHour = solarTimeAfter.getLunarHour();
//        log.info("真太阳时农历：{}", lunarHour.format());


        // 输出城市的真太阳时
        localDateTime = LocalDateTime.parse(lunarHour1.format(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        localZonedDateTime = localDateTime.atZone(ZoneId.of("+8"));
        trueSolarTime = TrueSolarTime.getTrueSolarTime(geo.getLongitude(), geo.getLatitude(), localZonedDateTime);
        LunarHour lunarHour= LunarHour.fromYmdHms(trueSolarTime.getYear(), trueSolarTime.getMonthValue(), trueSolarTime.getDayOfMonth(), trueSolarTime.getHour(), trueSolarTime.getMinute(), trueSolarTime.getSecond());
        log.info("真太阳时农历：{}", lunarHour.format());

        //丙寅 戊戌 丙午 戊戌
        log.info(" {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(-2).getName());
        log.info(" {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(-1).getName());
        log.info(" {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(0).getName());
        log.info(" {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(1).getName());
        log.info(" {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(2).getName());



        // 童限
        ChildLimit childLimit = ChildLimit.fromSolarTime(SolarTime.fromYmdHms(1983, 2, 15, 20, 0, 0), Gender.WOMAN);
        // 八字
        Assert.assertEquals("癸亥 甲寅 甲戌 甲戌", childLimit.getEightChar().toString());
        // 童限年数
        Assert.assertEquals(6, childLimit.getYearCount());
        // 童限月数
        Assert.assertEquals(2, childLimit.getMonthCount());
        // 童限日数
        Assert.assertEquals(18, childLimit.getDayCount());
        // 童限结束(即开始起运)的公历时刻
        Assert.assertEquals("1989年5月4日 18:24:00", childLimit.getEndTime().toString());
//        // 童限开始(即出生)的农历年干支
//        Assert.assertEquals("癸亥", childLimit.getStartTime().getLunarHour().getDay().getMonth().getYear().getSixtyCycle().getName());
//        // 童限结束(即开始起运)的农历年干支
//        Assert.assertEquals("己巳", childLimit.getEndTime().getLunarHour().getDay().getMonth().getYear().getSixtyCycle().getName());

        // 第1轮大运
        DecadeFortune decadeFortune = childLimit.getStartDecadeFortune();
        // 开始年龄
        Assert.assertEquals(7, decadeFortune.getStartAge());
        // 结束年龄
        Assert.assertEquals(16, decadeFortune.getEndAge());
        // 开始年
        Assert.assertEquals(1989, decadeFortune.getStartLunarYear().getYear());
        // 结束年
        Assert.assertEquals(1998, decadeFortune.getEndLunarYear().getYear());
        // 干支
        Assert.assertEquals("乙卯", decadeFortune.getName());
        // 下一大运
        Assert.assertEquals("丙辰", decadeFortune.next(1).getName());
        // 上一大运
        Assert.assertEquals("甲寅", decadeFortune.next(-1).getName());
        // 第9轮大运
        Assert.assertEquals("癸亥", decadeFortune.next(8).getName());

        // 小运
        Fortune fortune = childLimit.getStartFortune();
        // 年龄
        Assert.assertEquals(7, fortune.getAge());
        // 农历年
        Assert.assertEquals(1989, fortune.getLunarYear().getYear());
        // 干支
        Assert.assertEquals("辛巳", fortune.getName());

        // 流年
        Assert.assertEquals("己巳", fortune.getLunarYear().getSixtyCycle().getName());
    }
}
