package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BdroomAppAccessoryPO implements Serializable {
  private Long bdroomAppAccessoryId;
  
  private String accessoryname;
  
  private String accessorysavename;
  
  private BoardRoomApplyPO boardRoomApply;
  
  private String domainId;
  
  public BdroomAppAccessoryPO(Long bdroomAppAccessoryId, String accessoryname, String accessorysavename, BoardRoomApplyPO boardRoomApply) {
    this.bdroomAppAccessoryId = bdroomAppAccessoryId;
    this.accessoryname = accessoryname;
    this.accessorysavename = accessorysavename;
    this.boardRoomApply = boardRoomApply;
  }
  
  public BdroomAppAccessoryPO() {}
  
  public Long getBdroomAppAccessoryId() {
    return this.bdroomAppAccessoryId;
  }
  
  public void setBdroomAppAccessoryId(Long bdroomAppAccessoryId) {
    this.bdroomAppAccessoryId = bdroomAppAccessoryId;
  }
  
  public String getAccessoryname() {
    return this.accessoryname;
  }
  
  public void setAccessoryname(String accessoryname) {
    this.accessoryname = accessoryname;
  }
  
  public String getAccessorysavename() {
    return this.accessorysavename;
  }
  
  public void setAccessorysavename(String accessorysavename) {
    this.accessorysavename = accessorysavename;
  }
  
  public BoardRoomApplyPO getBoardRoomApply() {
    return this.boardRoomApply;
  }
  
  public void setBoardRoomApply(BoardRoomApplyPO boardRoomApply) {
    this.boardRoomApply = boardRoomApply;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("bdroomAppAccessoryId", getBdroomAppAccessoryId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof BdroomAppAccessoryPO))
      return false; 
    BdroomAppAccessoryPO castOther = (BdroomAppAccessoryPO)other;
    return (new EqualsBuilder())
      .append(getAccessorysavename(), castOther.getAccessorysavename())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getBdroomAppAccessoryId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
