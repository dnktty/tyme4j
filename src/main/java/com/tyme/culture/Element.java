package com.tyme.culture;

import com.tyme.LoopTyme;

/**
 * 五行
 *
 * @author 6tail
 */
public class Element extends LoopTyme {

  public static final String[] NAMES = {"木", "火", "土", "金", "水"};
  public static final String[] COLORS_RES = {"青", "赤", "黄", "白", "黑"};
  public static final String[] CONSTANTS = {"仁", "礼", "信", "义", "智"};
  public static final String[] SEASONS = {"春", "夏", "四季末", "秋", "东"};
  public static final String[] CHARACTER = {"积极向上", "大胆创新", "忠厚踏实", "主义坚定", "智慧圆滑"};

  public Element(int index) {
    super(NAMES, index);
  }

  public Element(String name) {
    super(NAMES, name);
  }

  public static Element fromIndex(int index) {
    return new Element(index);
  }

  public static Element fromName(String name) {
    return new Element(name);
  }

  public Element next(int n) {
    return fromIndex(nextIndex(n));
  }

  /**
   * 我生者（生）
   *
   * @return 五行
   */
  public Element getReinforce() {
    return next(1);
  }

  /**
   * 我克者（克）
   *
   * @return 五行
   */
  public Element getRestrain() {
    return next(2);
  }

  /**
   * 生我者（泄）
   *
   * @return 五行
   */
  public Element getReinforced() {
    return next(-1);
  }

  /**
   * 克我者（耗）
   *
   * @return 五行
   */
  public Element getRestrained() {
    return next(-2);
  }

  /**
   * 方位
   *
   * @return 方位
   */
  public Direction getDirection() {
    return Direction.fromIndex(new int[]{2, 8, 4, 6, 0}[index]);
  }
  /**
   * 五脏
   *
   * @return
   */
  public Organ getOrgan() {
    return Organ.fromIndex(new int[]{1, 3, 5, 7, 9}[index]);
  }
  /**
   * 颜色代表
   *
   * @return
   */
  public String getColorRes() {
    return COLORS_RES[index];
  }
  /**
   * 高亮颜色
   *
   * @return
   */
  public Color getColorVal() {
    return Color.fromIndex(new int[]{0, 1, 2, 3, 4}[index]);
  }
  /**
   * 五常
   *
   * @return
   */
  public String getConstant() {
    return CONSTANTS[index];
  }
  /**
   * 性格
   *
   * @return
   */
  public String getCharacter() {
    return CHARACTER[index];
  }
  /**
   * 四季
   *
   * @return
   */
  public String getSeason() {
    return SEASONS[index];
  }

}
