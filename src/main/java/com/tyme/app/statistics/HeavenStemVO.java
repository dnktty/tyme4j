package com.tyme.app.statistics;

import com.tyme.enums.SixtyCyclePosition;
import com.tyme.sixtycycle.HeavenStem;
import lombok.Data;

@Data
public class HeavenStemVO {
  private int index;
  private int position;
  private String name;
  private int elementIndex;
  private String elementName;
  private String positionName;

  public HeavenStemVO(HeavenStem heavenStem) {
    index = heavenStem.getIndex();
    position = heavenStem.getPosition();
    name = heavenStem.getName();
    elementIndex = heavenStem.getElement().getIndex();
    elementName = heavenStem.getElement().getName();
    positionName = SixtyCyclePosition.fromCode(position).getName();
  }
}
