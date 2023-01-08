package com.js.oa.personalwork.person.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CountryPO implements Serializable {
  private long id;
  
  private String countyName;
  
  public CountryPO(String countyname) {
    this.countyName = countyname;
  }
  
  public CountryPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getCountyName() {
    return this.countyName;
  }
  
  public void setCountyName(String countyName) {
    this.countyName = countyName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof CountryPO))
      return false; 
    CountryPO castOther = (CountryPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
}
