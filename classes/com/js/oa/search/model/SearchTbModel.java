package com.js.oa.search.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SearchTbModel implements Serializable {
  public String id;
  
  public String title;
  
  public String content;
  
  public String url;
  
  public String userId;
  
  public String userName;
  
  public String date;
  
  public String others;
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getDate() {
    return this.date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
  
  public String getOthers() {
    return this.others;
  }
  
  public void setOthers(String others) {
    this.others = others;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", reDate())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SearchTbModel))
      return false; 
    SearchTbModel castOther = (SearchTbModel)other;
    return (new EqualsBuilder())
      .append(reDate(), castOther.reDate())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(reDate())
      .toHashCode();
  }
  
  public Date reDate() {
    return new Date();
  }
}
