package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NetSurveyItemPO implements Serializable {
  private long id;
  
  private String itemContent;
  
  private NetSurveyPO survey;
  
  private Set itemVotes = null;
  
  private String domainId;
  
  public NetSurveyItemPO(long id, NetSurveyPO survey, Set itemVotes, String itemcontent) {
    this.id = id;
    this.itemContent = itemcontent;
    this.survey = survey;
    this.itemVotes = itemVotes;
  }
  
  public NetSurveyItemPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getItemContent() {
    return this.itemContent;
  }
  
  public void setItemContent(String itemContent) {
    this.itemContent = itemContent;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof NetSurveyItemPO))
      return false; 
    NetSurveyItemPO castOther = (NetSurveyItemPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public NetSurveyPO getSurvey() {
    return this.survey;
  }
  
  public void setSurvey(NetSurveyPO survey) {
    this.survey = survey;
  }
  
  public Set getItemVotes() {
    return this.itemVotes;
  }
  
  public void setItemVotes(Set itemVotes) {
    this.itemVotes = itemVotes;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
