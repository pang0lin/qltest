package com.js.oa.hr.officemanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PostTitlePO implements Serializable {
  private long id;
  
  private String postTitleSeries;
  
  private String postTitle;
  
  private String domainId;
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PostTitlePO))
      return false; 
    PostTitlePO castOther = (PostTitlePO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getPostTitleSeries() {
    return this.postTitleSeries;
  }
  
  public void setPostTitleSeries(String postTitleSeries) {
    this.postTitleSeries = postTitleSeries;
  }
  
  public String getPostTitle() {
    return this.postTitle;
  }
  
  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
