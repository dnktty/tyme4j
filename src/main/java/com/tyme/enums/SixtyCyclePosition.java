package com.tyme.enums;

/**
 * 甲子应用场景类型
 *
 * @author 6tail
 */
public enum SixtyCyclePosition {
  YEAR_EARTH(11, "年支"),
  MONTH_EARTH(21, "月支"),
  DAY_EARTH(31, "日支"),
  HOUR_EARTH(41, "时支"),
  YEAR_EARTH_MAIN(111, "年藏干主气"),
  MONTH_EARTH_MAIN(211, "月藏干主气"),
  DAY_EARTH_MAIN(311, "日藏干主气"),
  HOUR_EARTH_MAIN(411, "时藏干主气"),
  YEAR_EARTH_MIDDLE(112, "年藏干中气"),
  MONTH_EARTH_MIDDLE(212, "月藏干中气"),
  DAY_EARTH_MIDDLE(312, "日藏干中气"),
  HOUR_EARTH_MIDDLE(412, "时藏干中气"),
  YEAR_EARTH_RESIDUAL(113, "年藏干余气"),
  MONTH_EARTH_RESIDUAL(213, "月藏干余气"),
  DAY_EARTH_RESIDUAL(313, "日藏干余气"),
  HOUR_EARTH_RESIDUAL(413, "时藏干余气"),
  YEAR_HEAVEN(10, "年干"),
  MONTH_HEAVEN(20, "月干"),
  DAY_HEAVEN(30, "日干"),
  HOUR_HEAVEN(40, "时干"),
  YEAR(1, "年"),
  MONTH(2, "月"),
  DAY(3, "日"),
  HOUR(4, "时");

  /**
   * 代码
   */
  private final int code;

  /**
   * 名称
   */
  private final String name;

  SixtyCyclePosition(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public static SixtyCyclePosition fromCode(Integer code) {
    if (null == code) {
      return null;
    }
    for (SixtyCyclePosition item : values()) {
      if (item.getCode() == code) {
        return item;
      }
    }
    return null;
  }

  public static SixtyCyclePosition fromName(String name) {
    if (null == name) {
      return null;
    }
    for (SixtyCyclePosition item : values()) {
      if (item.getName().equals(name)) {
        return item;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }

}
