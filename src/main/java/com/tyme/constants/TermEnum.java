package com.tyme.constants;

public enum TermEnum {
    TIAN_GAN("天干", "", ""),
    SHENG_WANG("生旺", "", ""),
    SHEN_XIAO("生肖", "", ""),
    SHI_CHEN("时辰", "", ""),
    REPRESENT("表象", "", ""),
    DI_ZHI("地支", "", ""),
    YIN_YANG("阴阳", "", ""),
    WU_XING("五行", "", ""),
    MONTH("月份", "", ""),
    SHENG_XIAO("生肖", "", ""),
    DAI_BIAO("代表", "", ""),
    SHEN_TI("身体", "", ""),
    YAN_SE("颜色", "", ""),
    JI_JIE("季节", "", ""),
    WU_CHANG("五常", "", ""),
    XING_GE("性格", "", ""),
    FANG_WEI("方位", "", ""),
    BA_GUA("八卦", "", ""),
    WU_HE("五合", "", ""),
    HE_HUA("合化", "", ""),
    BEN_QI("藏干-本气", "", ""),
    ZHONG_QI("藏干-中气", "", ""),
    YU_QI("藏干-余气", "", ""),
    GAN_SHENG("干神", "", ""),
    BA_ZI("八字", "", ""),
    DA_YUN("大运", "", ""),
    LIU_NIAN("流年", "", ""),
    LIU_YUE("流月", "", ""),
    NIAN_ZHU("年柱", "", ""),
    RI_ZHU("日柱", "", ""),
    YUE_ZHU("月柱", "", ""),
    SHI_ZHU("时柱", "", ""),
    LEI_MU("类目", "", ""),
    YUAN("元", "", ""),
    DI_SHI("地势", "", ""),
    SUI("岁", "", ""),
    NIAN("年", "", ""),
    NA_YIN("纳音", "", ""),
    JIE_QI_RI("节气日", "", ""),
    SHI_SHEN("十神", "", ""),
    ADVANTAGE("优点", "", ""),
    DISADVANTAGE("缺点", "", ""),
    OTHERS("其他", "", ""),
    ALIAS("别名", "", ""),
    GENDER("性别", "", ""),
    RELATION("关系", "", ""),
    SIX_RELATION("六亲", "", ""),
    KONG_WANG("空亡", "", ""),
    SHI_CHENG("时辰", "", "");
    
    private String name;
    private String en;
    private String alias;
    
    TermEnum(String name, String en, String alias) {
        this.name = name;
        this.en = en;
        this.alias = alias;
    }
    
    public String getName() {
        return name;
    }
    public String show(){
        return name;
    }
    public String getEn() {
        return en;
    }
    
    public String getAlias() {
        return alias;
    }
    
}
