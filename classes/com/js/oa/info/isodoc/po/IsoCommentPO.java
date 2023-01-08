package com.js.oa.info.isodoc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class IsoCommentPO implements Serializable {
  private Long id;
  
  private String dealComment;
  
  private String acName;
  
  private String dealDate;
  
  private Long informationId;
  
  private String dealEmpName;
  
  private String inforversion;
  
  private String infodealType;
  
  public boolean equals(Object other) {
    if (!(other instanceof IsoCommentPO))
      return false; 
    IsoCommentPO castOther = (IsoCommentPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getDealDate() {
    return this.dealDate;
  }
  
  public String getDealComment() {
    return this.dealComment;
  }
  
  public String getAcName() {
    return this.acName;
  }
  
  public Long getInformationId() {
    return this.informationId;
  }
  
  public String getDealEmpName() {
    return this.dealEmpName;
  }
  
  public String getInforversion() {
    return this.inforversion;
  }
  
  public String getInfodealType() {
    return this.infodealType;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setDealDate(String dealDate) {
    this.dealDate = dealDate;
  }
  
  public void setDealComment(String dealComment) {
    this.dealComment = dealComment;
  }
  
  public void setAcName(String acName) {
    this.acName = acName;
  }
  
  public void setInformationId(Long informationId) {
    this.informationId = informationId;
  }
  
  public void setDealEmpName(String dealEmpName) {
    this.dealEmpName = dealEmpName;
  }
  
  public void setInforversion(String inforversion) {
    this.inforversion = inforversion;
  }
  
  public void setInfodealType(String infodealType) {
    this.infodealType = infodealType;
  }
}
