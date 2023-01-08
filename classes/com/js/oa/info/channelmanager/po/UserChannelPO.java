package com.js.oa.info.channelmanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UserChannelPO implements Serializable {
  private String userChannelName;
  
  private Long userChannelId;
  
  private String userChannelOrder;
  
  private Long domainId;
  
  private String channelReader;
  
  private String channelReadOrg;
  
  private String channelReadGroup;
  
  private String channelReadName;
  
  public String getUserChannelName() {
    return this.userChannelName;
  }
  
  public void setUserChannelName(String userChannelName) {
    this.userChannelName = userChannelName;
  }
  
  public Long getUserChannelId() {
    return this.userChannelId;
  }
  
  public void setUserChannelId(Long userChannelId) {
    this.userChannelId = userChannelId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof UserChannelPO))
      return false; 
    UserChannelPO castOther = (UserChannelPO)other;
    return (new EqualsBuilder()).append(getUserChannelId(), castOther.getUserChannelId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getUserChannelId()).toHashCode();
  }
  
  public String getUserChannelOrder() {
    return this.userChannelOrder;
  }
  
  public void setUserChannelOrder(String userChannelOrder) {
    this.userChannelOrder = userChannelOrder;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getChannelReader() {
    return this.channelReader;
  }
  
  public void setChannelReader(String channelReader) {
    this.channelReader = channelReader;
  }
  
  public String getChannelReadOrg() {
    return this.channelReadOrg;
  }
  
  public void setChannelReadOrg(String channelReadOrg) {
    this.channelReadOrg = channelReadOrg;
  }
  
  public String getChannelReadGroup() {
    return this.channelReadGroup;
  }
  
  public void setChannelReadGroup(String channelReadGroup) {
    this.channelReadGroup = channelReadGroup;
  }
  
  public String getChannelReadName() {
    return this.channelReadName;
  }
  
  public void setChannelReadName(String channelReadName) {
    this.channelReadName = channelReadName;
  }
}
