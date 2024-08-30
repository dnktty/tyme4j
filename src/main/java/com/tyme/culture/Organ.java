package com.tyme.culture;

import com.tyme.LoopTyme;

public class Organ extends LoopTyme {

    public static final String[] NAMES = {"胆", "肝", "小肠", "心", "胃", "脾", "大肠", "肺", "膀胱", "肾"};

    public Organ(int index) {
        super(NAMES, index);
    }

    public Organ(String name) {
        super(NAMES, name);
    }

    public static Organ fromIndex(int index) {
        return new Organ(index);
    }

    public static Organ fromName(String name) {
        return new Organ(name);
    }

    public Organ next(int n) {
        return fromIndex(nextIndex(n));
    }



    /**
     * 五行
     *
     * @return 五行
     */
    public Element getElement() {
        return Element.fromIndex(new int[]{4, 2, 0, 0, 2, 3, 3, 2, 1}[index]);
    }
}
