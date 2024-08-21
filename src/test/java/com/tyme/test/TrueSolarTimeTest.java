package com.tyme.test;

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
 * @date 2024-08-19
 **/
@Slf4j
public class TrueSolarTimeTest {
    @Test
    public void testTrueSolarTime() {
        // 示例城市：长沙
        double longitude = 112.4074; // 经度
        double latitude = 28.9042; // 纬度

        LocalDateTime localDateTime = LocalDateTime.parse("1986-09-26 21:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("+8"));
        LocalDateTime trueSolarTime = TrueSolarTime.getTrueSolarTime(longitude, latitude, localZonedDateTime);
        log.info("当前本地时间: {}", trueSolarTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Assert.assertEquals("20:39", trueSolarTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

}
