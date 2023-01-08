package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BoardRoomEquipmentPO implements Serializable {
  private Long equId;
  
  private String equName;
  
  private String equDescribe;
  
  private BoardRoomPO boardRoomPO;
  
  public BoardRoomPO getBoardRoomPO() {
    return this.boardRoomPO;
  }
  
  public void setBoardRoomPO(BoardRoomPO boardRoomPO) {
    this.boardRoomPO = boardRoomPO;
  }
  
  public String getEquDescribe() {
    return this.equDescribe;
  }
  
  public void setEquDescribe(String equDescribe) {
    this.equDescribe = equDescribe;
  }
  
  public Long getEquId() {
    return this.equId;
  }
  
  public void setEquId(Long equId) {
    this.equId = equId;
  }
  
  public String getEquName() {
    return this.equName;
  }
  
  public void setEquName(String equName) {
    this.equName = equName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("equId", getEquId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof BoardRoomEquipmentPO))
      return false; 
    BoardRoomEquipmentPO castOther = (BoardRoomEquipmentPO)other;
    return (new EqualsBuilder())
      .append(getEquId(), castOther.getEquId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEquId())
      .toHashCode();
  }
}
