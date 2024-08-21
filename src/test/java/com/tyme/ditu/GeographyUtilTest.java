package com.tyme.ditu;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-19
 **/
@Slf4j
public class GeographyUtilTest {

    @Test
    public void testLonAndLat() {
        try {
            // 1、根据地址获取经纬度
            Geography geo = GeographyUtil.getLonAndLat("湖南省长沙市", null);
            Assert.assertEquals("112.938882", geo.getLongitude());
            Assert.assertEquals("28.228304", geo.getLatitude());


            // 2、根据经纬度获取地址
            String formattedAddress = GeographyUtil.getAMapByLngAndLat("120.204798", "30.201000", null);
            Assert.assertEquals("浙江省杭州市滨江区长河街道池奈日式咖喱蛋包饭(龙湖杭州滨江天街店)龙湖郦城公馆", formattedAddress);
        } catch (Exception e) {
            log.error("获取地址错误", e);
        }
    }


    public static String 转真太阳时(double longitude, double latitude, String time) {
        // 将传入的时间字符串解析为ZonedDateTime
        time = formatDate(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));

        // 获取对应的UTC时间
        ZonedDateTime utcTime = localZonedDateTime.withZoneSameInstant(ZoneOffset.UTC);

        // 获取当前当地标准时间（LST）
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");  // 替换为对应时区
        ZonedDateTime localTime = utcTime.withZoneSameInstant(zoneId);

        // 计算时区差异（与标准时区的小时数差异）
        int timeZoneOffset = TimeZone.getTimeZone(zoneId).getRawOffset() / 3600000;

        // 计算经度时间差
        double longitudeTimeDifference = (longitude - (timeZoneOffset * 15)) * 4;  // 分钟数

        // 计算均时差（EOT）
        double eot = 计算均时差(localTime);

        // 计算真太阳时
        LocalTime localSolarTime = localTime.toLocalTime()
                .plusMinutes((long) longitudeTimeDifference)
                .plusMinutes((long) eot);

        // 输出结果
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime solarDateTime = localDateTime.with(LocalTime.of(localSolarTime.getHour(), localSolarTime.getMinute()));

        // 如果计算出的真太阳时比当地标准时间晚,且longitudeTimeDifference<0 减去一天
        if (solarDateTime.isAfter(localDateTime)&&longitudeTimeDifference<0) {
            return solarDateTime.plusDays(-1).format(dateTimeFormatter);
        }
        // 如果计算出的真太阳时比当地标准时间早,且longitudeTimeDifference<0 增加一天
        if (solarDateTime.isBefore(localDateTime)&&longitudeTimeDifference>0) {
            return solarDateTime.plusDays(1).format(dateTimeFormatter);
        }
        return solarDateTime.format(dateTimeFormatter);
    }

    /**
     * 计算均时差（EOT）
     *
     * @param dateTime
     * @return 均时差（分钟）
     */
    private static double 计算均时差(ZonedDateTime dateTime) {
        // 计算EOT所需的近似公式
        int dayOfYear = dateTime.getDayOfYear();
        double b = 2 * Math.PI * (dayOfYear - 81) / 364.0;
        return 9.87 * Math.sin(2 * b) - 7.53 * Math.cos(b) - 1.5 * Math.sin(b);
    }

    public static String formatDate(String dateTime) {
        // 假设输入格式为 "yyyy-M-d H:m" 或 "yyyy-M-d H:m:s"
        // 需要将其格式化为 "yyyy-MM-dd HH:mm:ss"
        try {
            // 解析日期时间
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTimeParsed = LocalDateTime.parse(dateTime, inputFormatter);

            // 格式化为目标格式
            return dateTimeParsed.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // 捕捉并处理解析异常
            System.err.println("时间格式无效: " + e.getMessage());
            return dateTime;
        }
    }

    public static void main(String[] args) {
        // 示例经纬度
        double longitude = 112.966667;
        double latitude = 28.9041999;

        String time = "1986-09-26 21:00";
        System.out.println(longitude);
        System.out.println(time);
        System.out.println(转真太阳时(longitude, latitude, time));
    }
}
