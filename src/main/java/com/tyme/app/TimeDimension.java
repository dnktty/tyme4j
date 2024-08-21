package com.tyme.app;

import lombok.Data;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-20
 **/
@Data
public class TimeDimension {
    public TimeDimension(String year, String month, String day, String hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }
    private String year;
    private String month;
    private String day;
    private String hour;

    private String daYun;
    private String flowYear;
    private String flowMonth;
}
