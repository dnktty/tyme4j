package com.tyme.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tyme.app.paipan.Address;
import com.tyme.app.paipan.Birth;
import com.tyme.app.paipan.PaiPanUtil;
import com.tyme.app.statistics.EarthBranchVO;
import com.tyme.app.statistics.HeavenStemVO;
import com.tyme.eightchar.EightChar;
import com.tyme.enums.Gender;
import com.tyme.enums.SixtyCyclePosition;
import com.tyme.sixtycycle.SixtyCycle;
import com.tyme.solar.SolarTime;
import com.tyme.util.HttpUtil;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-06
 */
@Slf4j
public class PaiPanTest {
  private static final String BASE_URL = "http://113.89.41.243:28080/urule/rest/1";
  private static final String REQUEST_BODY_TEMPLATE =
      "[\n"
          + "    {\n"
          + "        \"name\": \"参数\",\n"
          + "        \"fields\": {\n"
          + "            \"heavenStemMap\": %s,\n"
          + "            \"earthBranthMap\": %s\n"
          + "        },\n"
          + "        \"class\": \"java.util.HashMap\"\n"
          + "    }\n"
          + "]";

  @Test
  public void testBaZi() {
    Address address = new Address("湖南省", "长沙市", "岳麓区");
    String birthDay = "1986-09-26 21:00:00";
    Birth birth = new Birth(address, ZoneId.of("+8"), birthDay, Gender.MAN);
    PaiPanUtil.getPaiPan(birth);
  }

  @Test
  public void testGetInputs() {
    Address address = new Address("湖南省", "长沙市", "岳麓区");
    String birthDay = "1986-09-26 21:00:00";
    Birth birth = new Birth(address, ZoneId.of("+8"), birthDay, Gender.MAN);
    SolarTime solarTime = PaiPanUtil.getTrueSolarTime(birth);
    // 八字
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    EightChar eightChar = PaiPanUtil.getEightChar(solarTime);

    Map<String, HeavenStemVO> heavenStemVOMap = new HashMap<>();
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getYear().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getYear().getHeavenStem()));
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getMonth().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getMonth().getHeavenStem()));
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getDay().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getDay().getHeavenStem()));
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getHour().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getHour().getHeavenStem()));
    log.info(gson.toJson(heavenStemVOMap));
    Map<String, EarthBranchVO> earthBranchVOMap = new HashMap<>();
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getYear().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getYear().getEarthBranch()));
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getMonth().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getMonth().getEarthBranch()));
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getDay().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getDay().getEarthBranch()));
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getHour().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getHour().getEarthBranch()));
    log.info(gson.toJson(earthBranchVOMap));
  }

  private String getRequestBody(EightChar eightChar) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Map<String, HeavenStemVO> heavenStemVOMap = new HashMap<>();
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getYear().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getYear().getHeavenStem()));
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getMonth().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getMonth().getHeavenStem()));
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getDay().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getDay().getHeavenStem()));
    heavenStemVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getHour().getHeavenStem().getPosition()).getName(),
        new HeavenStemVO(eightChar.getHour().getHeavenStem()));
    //    log.info(gson.toJson(heavenStemVOMap));
    Map<String, EarthBranchVO> earthBranchVOMap = new HashMap<>();
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getYear().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getYear().getEarthBranch()));
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getMonth().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getMonth().getEarthBranch()));
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getDay().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getDay().getEarthBranch()));
    earthBranchVOMap.put(
        SixtyCyclePosition.fromCode(eightChar.getHour().getEarthBranch().getPosition()).getName(),
        new EarthBranchVO(eightChar.getHour().getEarthBranch()));
    //    log.info(gson.toJson(earthBranchVOMap));

    String requestBody =
        String.format(
            REQUEST_BODY_TEMPLATE, gson.toJson(heavenStemVOMap), gson.toJson(earthBranchVOMap));
//    log.info(requestBody);
    return requestBody;
  }

  @Test
  public void testToDecision() {
    Address address = new Address("湖南省", "长沙市", "岳麓区");
    String birthDay = "1986-09-26 21:00:00";
    Birth birth = new Birth(address, ZoneId.of("+8"), birthDay, Gender.MAN);
    SolarTime solarTime = PaiPanUtil.getTrueSolarTime(birth);
    // 八字
    EightChar eightChar = PaiPanUtil.getEightChar(solarTime);
    String requestBody = getRequestBody(eightChar);

    String responseBody = HttpUtil.postRequest(BASE_URL, requestBody, "application/json");
    log.info(responseBody);
    Assert.assertTrue(responseBody.contains("{\"金\":2E+2,\"木\":165,\"火\":6E+2,\"土\":1.6E+3}"));
//    Assert.assertTrue(responseBody.contains("{\"金\":2E+2,\"木\":165,\"火\":1257.5,\"土\":3577.5}"));
  }

  @Test
  public void testToDecision2() {
    // 八字
    EightChar eightChar =
        new EightChar(
            SixtyCycle.fromName("丙辰"),
            SixtyCycle.fromName("己亥"),
            SixtyCycle.fromName("甲子"),
            SixtyCycle.fromName("甲戌"));
    String requestBody = getRequestBody(eightChar);

    String responseBody = HttpUtil.postRequest(BASE_URL, requestBody, "application/json");
    log.info(responseBody);
    Assert.assertTrue(
        responseBody.contains("{\"金\":25,\"水\":1495,\"木\":6E+2,\"火\":4E+1,\"土\":3E+2}"));
  }
  @Test
  public void testToDecision3() {
    // 八字
    EightChar eightChar =
        new EightChar(
            SixtyCycle.fromName("癸未"),
            SixtyCycle.fromName("甲寅"),
            SixtyCycle.fromName("乙亥"),
            SixtyCycle.fromName("己卯"));
    String requestBody = getRequestBody(eightChar);

    String responseBody = HttpUtil.postRequest(BASE_URL, requestBody, "application/json");
    log.info(responseBody);
    Assert.assertTrue(
        responseBody.contains("{\"金\":25,\"水\":1495,\"木\":6E+2,\"火\":4E+1,\"土\":3E+2}"));
  }
  @Test
  public void testToDecision4() {
    // 八字
    EightChar eightChar =
        new EightChar(
            SixtyCycle.fromName("辛酉"),
            SixtyCycle.fromName("辛卯"),
            SixtyCycle.fromName("甲申"),
            SixtyCycle.fromName("癸酉"));
    String requestBody = getRequestBody(eightChar);

    String responseBody = HttpUtil.postRequest(BASE_URL, requestBody, "application/json");
    log.info(responseBody);
    Assert.assertTrue(
        responseBody.contains("{\"金\":25,\"水\":1495,\"木\":6E+2,\"火\":4E+1,\"土\":3E+2}"));
  }
  @Test
  public void testToDecision5() {
    // 八字
    EightChar eightChar =
        new EightChar(
            SixtyCycle.fromName("甲申"),
            SixtyCycle.fromName("丙子"),
            SixtyCycle.fromName("庚辰"),
            SixtyCycle.fromName("戊寅"));
    String requestBody = getRequestBody(eightChar);

    String responseBody = HttpUtil.postRequest(BASE_URL, requestBody, "application/json");
    log.info(responseBody);
    Assert.assertTrue(
        responseBody.contains("{\"金\":25,\"水\":1495,\"木\":6E+2,\"火\":4E+1,\"土\":3E+2}"));
  }
}
