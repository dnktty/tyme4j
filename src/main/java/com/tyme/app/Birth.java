package com.tyme.app;

import com.tyme.enums.Gender;
import lombok.Data;

import java.time.ZoneId;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-20
 **/
@Data
public class Birth {
    public Birth(Address address, ZoneId timeZone, String birthDay, Gender gender) {
        this.address = address;
        this.timeZone = timeZone;
        this.birthDay = birthDay;
        this.gender = gender;
    }
    private Address address;
    private ZoneId timeZone;
    //农历时间
    private String birthDay;
    private Gender gender;
}
