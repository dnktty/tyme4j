package com.tyme.app.statistics;

import com.tyme.enums.SixtyCyclePosition;
import com.tyme.sixtycycle.EarthBranch;
import lombok.Data;

@Data
public class EarthBranchVO {
  private int index;
  private int position;
  private String name;
  private int elementIndex;
  private String elementName;
  private HeavenStemVO hideMain;
  private HeavenStemVO hideMiddle;
  private HeavenStemVO hideResidual;
  private String positionName;

  public EarthBranchVO(EarthBranch earthBranch) {
    index = earthBranch.getIndex();
    position = earthBranch.getPosition();
    name = earthBranch.getName();
    elementIndex = earthBranch.getElement().getIndex();
    elementName = earthBranch.getElement().getName();
    positionName = SixtyCyclePosition.fromCode(position).getName();
    hideMain =
        null == earthBranch.getHideHeavenStemMain()
            ? null
            : new HeavenStemVO(earthBranch.getHideHeavenStemMain());
    hideMiddle =
        null == earthBranch.getHideHeavenStemMiddle()
            ? null
            : new HeavenStemVO(earthBranch.getHideHeavenStemMiddle());
    hideResidual =
        null == earthBranch.getHideHeavenStemResidual()
            ? null
            : new HeavenStemVO(earthBranch.getHideHeavenStemResidual());
  }
}
