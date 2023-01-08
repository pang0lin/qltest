package com.js.oa.info.channelmanager.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ChannelVO implements Serializable {
  private Long id;
  
  private String name;
  
  private String showType;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ChannelVO))
      return false; 
    ChannelVO castOther = (ChannelVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getShowType() {
    return this.showType;
  }
  
  public void setShowType(String showType) {
    this.showType = showType;
  }
}
