package com.tyme.app.paipan;

import lombok.Data;

/**
 * @describe: 地址
 * @author: kenschen
 * @date 2024-08-20
 **/
@Data
public class Address {
    public Address(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }
    private String province;
    private String city;
    private String district;
}
