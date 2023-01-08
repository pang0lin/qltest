package com.js.oa.info.channelmanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ChannelPositionPO implements Serializable {
  private int positionId;
  
  private String positionName;
  
  public int getPositionId() {
    return this.positionId;
  }
  
  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }
  
  public String getPositionName() {
    return this.positionName;
  }
  
  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ChannelPositionPO))
      return false; 
    ChannelPositionPO castOther = (ChannelPositionPO)other;
    return (new EqualsBuilder()).append(getPositionId(), castOther.getPositionId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getPositionId()).toHashCode();
  }
}
