package com.tyme.app;

import com.tyme.eightchar.EightChar;
import com.tyme.lunar.LunarHour;
import com.tyme.solar.SolarTime;
import java.time.ZoneId;
import lombok.Data;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-20
 **/
@Data
public class Birth {
    public Birth(Address address, ZoneId timeZone, String birthDay) {
        this.address = address;
        this.timeZone = timeZone;
        this.birthDay = birthDay;
    }
    private Address address;
    private ZoneId timeZone;
    //农历时间
    private String birthDay;
}
