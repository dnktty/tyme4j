package com.tyme.app;

import com.alibaba.fastjson.JSON;
import com.tyme.eightchar.ChildLimit;
import com.tyme.eightchar.DecadeFortune;
import com.tyme.eightchar.Fortune;
import com.tyme.enums.Gender;
import com.tyme.lunar.LunarHour;
import com.tyme.solar.SolarTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
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
    public void testBaZi(){
        // 设定城市的时区（例如："America/New_York"）
        ZoneId cityZoneId = ZoneId.of("America/New_York");

        // 获取当前的UTC时间
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneId.of("UTC"));

        // 转换为城市的时间
        ZonedDateTime cityTime = utcTime.withZoneSameInstant(cityZoneId);

        // 输出城市的真太阳时
        System.out.println("City Sun Time: " + cityTime.toLocalTime());

        LunarHour lunarHour = LunarHour.fromYmdHms(1986, 9, 26, 20, 28, 0);
        log.info("八字 {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(-2).getName());
        log.info("八字 {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(-1).getName());
        log.info("八字 {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(0).getName());
        log.info("八字 {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(1).getName());
        log.info("八字 {} {} {} {}", lunarHour.getEightChar().getYear().getName(), lunarHour.getEightChar().getMonth().getName(), lunarHour.getEightChar().getDay().getName(), lunarHour.getEightChar().getHour().next(2).getName());




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
        // 童限开始(即出生)的农历年干支
        Assert.assertEquals("癸亥", childLimit.getStartTime().getLunarHour().getDay().getMonth().getYear().getSixtyCycle().getName());
        // 童限结束(即开始起运)的农历年干支
        Assert.assertEquals("己巳", childLimit.getEndTime().getLunarHour().getDay().getMonth().getYear().getSixtyCycle().getName());

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
