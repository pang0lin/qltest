package com.js.oa.search.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TransData implements Serializable {
  private SearchTbModel[] arrmodel;
  
  private PageInfor pageInfor;
  
  public SearchTbModel[] getArrmodel() {
    return this.arrmodel;
  }
  
  public void setArrmodel(SearchTbModel[] arrmodel) {
    this.arrmodel = arrmodel;
  }
  
  public PageInfor getPageInfor() {
    return this.pageInfor;
  }
  
  public void setPageInfor(PageInfor pageInfor) {
    this.pageInfor = pageInfor;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", reDate())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TransData))
      return false; 
    TransData castOther = (TransData)other;
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
