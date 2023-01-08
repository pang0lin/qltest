package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ThemeOptionPO implements Serializable {
  private Long themeOptionId;
  
  private Long questhemeId;
  
  private String title;
  
  private Integer pitchon;
  
  private Float optionScore;
  
  private QuesthemePO questheme;
  
  private String domainId;
  
  public ThemeOptionPO(String title, Integer pitchon, Float optionCode, QuesthemePO questheme) {
    this.title = title;
    this.pitchon = pitchon;
    this.optionScore = this.optionScore;
    this.questheme = questheme;
  }
  
  public Float getOptionScore() {
    return this.optionScore;
  }
  
  public void setOptionScore(Float optionScore) {
    this.optionScore = optionScore;
  }
  
  public Integer getPitchon() {
    return this.pitchon;
  }
  
  public void setPitchon(Integer pitchon) {
    this.pitchon = pitchon;
  }
  
  public QuesthemePO getQuestheme() {
    return this.questheme;
  }
  
  public void setQuestheme(QuesthemePO questheme) {
    this.questheme = questheme;
  }
  
  public Long getQuesthemeId() {
    return this.questhemeId;
  }
  
  public void setQuesthemeId(Long questhemeId) {
    this.questhemeId = questhemeId;
  }
  
  public Long getThemeOptionId() {
    return this.themeOptionId;
  }
  
  public void setThemeOptionId(Long themeOptionId) {
    this.themeOptionId = themeOptionId;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public ThemeOptionPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("themeOptionId", getThemeOptionId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ThemeOptionPO))
      return false; 
    ThemeOptionPO castOther = (ThemeOptionPO)other;
    return (new EqualsBuilder())
      .append(getThemeOptionId(), castOther.getThemeOptionId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getThemeOptionId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
