package com.tyme.app.rule;

public class Test {
        public static void main(String[] args) {
            printGuaSymbolsConcatenated();
        }

        private static void printGuaSymbolsConcatenated() {
            // 定义八卦方位的 Unicode 字符
            String qian = "\u5E73"; // 乾
            String kun = "\u571F"; // 坤
            String zhen = "\u970F"; // 震
            String xun = "\u5DF0"; // 巽
            String kan = "\u6C57"; // 坎
            String li = "\u79BB"; // 离
            String gen = "\u80A1"; // 艮
            String dui = "\u5149"; // 兑

            // 拼接八卦方位
            String guaSymbols = qian + " " + kun + " " + zhen + " " + xun + " " + kan + " " + li + " " + gen + " " + dui;

            // 打印八卦方位
            System.out.println(guaSymbols);
        }

}
