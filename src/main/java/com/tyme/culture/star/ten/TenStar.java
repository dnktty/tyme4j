package com.tyme.culture.star.ten;

import com.tyme.LoopTyme;

/**
 * 十神
 *
 * @author 6tail
 */
public class TenStar extends LoopTyme {

  public static final String[] NAMES = {"比", "劫", "食", "伤", "才", "财", "杀", "官", "枭", "印"};
  public static final String[] FULL_NAMES = {"比肩", "劫财", "食神", "伤官", "偏财", "正财", "七杀", "正官", "偏印/枭神", "正印"};
  public static final String[] PRESENTS = {
    "代表同辈朋友或兄弟姐妹", "代表竞争和冲突", "代表才华和艺术天赋", "代表才华和创造力，但也可能带来是非", "众人之才，代表父亲或者自己的才能，即找钱的本事。代表意外之财或浮动资产，如股票、奖金等", "代表个人的稳定收入和物质财富，代表妻子的嫁妆和个人固定资产，这种是可以统计的", "代表权力和暴力，有时也代表小人或挑战", "代表名誉、地位和权力", "代表智慧和隐秘的力量", "代表学问、智慧和慈悲心"
  };

  public TenStar(int index) {
    super(NAMES, index);
  }

  public TenStar(String name) {
    super(NAMES, name);
  }

  public static TenStar fromIndex(int index) {
    return new TenStar(index);
  }

  public static TenStar fromName(String name) {
    return new TenStar(name);
  }

  public TenStar next(int n) {
    return fromIndex(nextIndex(n));
  }

}
