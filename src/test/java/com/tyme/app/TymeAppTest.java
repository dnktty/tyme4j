package com.tyme.app;

import com.alibaba.fastjson.JSON;
import com.tyme.lunar.LunarHour;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
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
    }
}
