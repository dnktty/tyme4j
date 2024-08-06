package com.tyme.solar;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @describe:
 * @author: ken
 * @date 2024-08-06
 **/
public class TrueSolarTime {

    // 地球自转一周的时间（分钟）
    private static final double MINUTES_PER_DAY = 1440.0;
    // 地球每小时自转的角度（度）
    private static final double DEGREES_PER_HOUR = 15.0;
    // 地球自转一周的角度（度）
    private static final double DEGREES_PER_DAY = 360.0;

    public static void main(String[] args) {
        // 示例城市：北京
        double latitude = 39.9042; // 纬度
        double longitude = 116.4074; // 经度

        LocalDateTime localDateTime = LocalDateTime.now();
        double trueSolarTime = calculateTrueSolarTime(localDateTime, latitude, longitude);

        System.out.println("当前本地时间: " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("真太阳时: " + formatTime(trueSolarTime));
    }

    public static double calculateTrueSolarTime(LocalDateTime localDateTime, double latitude, double longitude) {
        // 获取当前时间的UTC时间
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault());
        long epochSeconds = zonedDateTime.toEpochSecond();
        LocalDateTime utcDateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC);

        // 计算时差（以分钟为单位）
        double timeDifference = longitude / DEGREES_PER_HOUR * 60;

        // 计算太阳时角（以度为单位）
        double dayOfYear = utcDateTime.getDayOfYear();
        double declination = 23.45 * Math.sin(Math.toRadians(360.0 * (284 + dayOfYear) / 365.0));
        double hourAngle = 15 * (utcDateTime.getHour() + utcDateTime.getMinute() / 60.0 - 12);

        // 计算真太阳时（以分钟为单位）
        double trueSolarTime =
                (hourAngle + longitude + timeDifference + 4 * Math.sin(2 * Math.PI * dayOfYear / 365.0)) % 360;
        trueSolarTime = trueSolarTime / DEGREES_PER_HOUR * 60;

        return trueSolarTime;
    }

    public static String formatTime(double minutes) {
        int hours = (int) minutes / 60;
        int mins = (int) minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

}
