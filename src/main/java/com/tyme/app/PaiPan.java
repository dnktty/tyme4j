package com.tyme.app;

import com.tyme.eightchar.EightChar;
import java.util.List;
import lombok.Data;

/**
 * @describe: 排盘
 * @author: kenschen
 * @date 2024-08-20
 **/
@Data
public class PaiPan {
    private Birth birth;
    //天干
    private TimeDimension heavenStem;
    //地支
    private TimeDimension earthBranch;
    //纳音
    private TimeDimension sound;
    //十神
    private TimeDimension tenStar;
    //长生十二神
    private TimeDimension terrain;
    //藏干
    private List<TimeDimension> hideHeavenStem;
    //藏干十神
    private List<TimeDimension> hideTenStar;
}
