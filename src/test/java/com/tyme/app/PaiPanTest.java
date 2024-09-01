package com.tyme.app;

import com.tyme.app.paipan.Address;
import com.tyme.app.paipan.Birth;
import com.tyme.app.paipan.PaiPanUtil;
import com.tyme.app.table.*;
import com.tyme.culture.Color;
import com.tyme.enums.Gender;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-06
 **/
@Slf4j
public class PaiPanTest {

    @Test
    public void testBaZi(){
        Address address = new Address("湖南省", "长沙市", "岳麓区");
        String birthDay = "1986-09-26 21:00:00";
        Birth birth = new Birth(address, ZoneId.of("+8"), birthDay, Gender.MAN);
        PaiPanUtil.getPaiPan(birth);
    }
}
