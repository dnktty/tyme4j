package com.tyme.solar;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

/**
 * @describe: https://github.com/chunjie008/java-solar-time/blob/main/%E7%BB%8F%E7%BA%AC%E5%BA%A6%E8%BD%AC%E7%9C%9F%E5%A4%AA%E9%98%B3%E6%97%B6.java
 * @author: ken
 * @date 2024-08-06
 **/
@Slf4j
public class TrueSolarTime {

    /**
     *
     * @param longitude
     * @param latitude
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime getTrueSolarTime(double longitude, double latitude, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("+8"));
        return getTrueSolarTime(longitude, latitude, localZonedDateTime);
    }

    /**
     * 建议传入公历时间
     * @param longitude
     * @param latitude
     * @param zonedDateTime
     * @return
     */
    public static LocalDateTime getTrueSolarTime(double longitude, double latitude, ZonedDateTime zonedDateTime) {
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        // 获取当前当地标准时间（LST）
        ZoneId zoneId = zonedDateTime.getZone();
        // 计算时区差异（与标准时区的小时数差异）
        int timeZoneOffset = TimeZone.getTimeZone(zoneId).getRawOffset() / 3600000;

        // 计算经度时间差
        double longitudeTimeDifference = (longitude - (timeZoneOffset * 15)) * 4;  // 分钟数

        // 计算均时差（EOT）
        double eot = getEot(zonedDateTime);

        // 计算真太阳时
        LocalTime localSolarTime = zonedDateTime.toLocalTime()
                .plusMinutes((long) longitudeTimeDifference)
                .plusMinutes((long) eot);

        // 输出结果
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime solarDateTime = localDateTime.with(LocalTime.of(localSolarTime.getHour(), localSolarTime.getMinute()));

        // 如果计算出的真太阳时比当地标准时间晚,且longitudeTimeDifference<0 减去一天
        if (solarDateTime.isAfter(localDateTime)&&longitudeTimeDifference<0) {
            return solarDateTime.plusDays(-1);
        }
        // 如果计算出的真太阳时比当地标准时间早,且longitudeTimeDifference<0 增加一天
        if (solarDateTime.isBefore(localDateTime)&&longitudeTimeDifference>0) {
            return solarDateTime.plusDays(1);
        }
        return solarDateTime;
    }

    /**
     * 计算均时差（EOT）
     *
     * @param dateTime
     * @return 均时差（分钟）
     */
    private static double getEot(ZonedDateTime dateTime) {
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
        double longitude = 130.966667;
        double latitude = 39.9041999;


        String time = "2024-07-01 23:55";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("+8"));


        System.out.println(longitude);
        System.out.println(time);
        System.out.println(getTrueSolarTime(longitude, latitude, localZonedDateTime));
    }

}
