package com.js.oa.routine.resource.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CsMasterPO implements Serializable {
  private Long id;
  
  private Date csDate = null;
  
  private String csMan;
  
  private Long csStock;
  
  private Long makeMan;
  
  private Date makeDate = null;
  
  private String checkFlag;
  
  private Date checkDate = null;
  
  private String remark;
  
  private String checkManName;
  
  private Long checkMan;
  
  private Set csDetail = null;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Date getCsDate() {
    return this.csDate;
  }
  
  public void setCsDate(Date csDate) {
    this.csDate = csDate;
  }
  
  public String getCsMan() {
    return this.csMan;
  }
  
  public void setCsMan(String csMan) {
    this.csMan = csMan;
  }
  
  public Long getCsStock() {
    return this.csStock;
  }
  
  public void setCsStock(Long csStock) {
    this.csStock = csStock;
  }
  
  public Long getMakeMan() {
    return this.makeMan;
  }
  
  public void setMakeMan(Long makeMan) {
    this.makeMan = makeMan;
  }
  
  public Date getMakeDate() {
    return this.makeDate;
  }
  
  public void setMakeDate(Date makeDate) {
    this.makeDate = makeDate;
  }
  
  public String getCheckFlag() {
    return this.checkFlag;
  }
  
  public void setCheckFlag(String checkFlag) {
    this.checkFlag = checkFlag;
  }
  
  public Long getCheckMan() {
    return this.checkMan;
  }
  
  public void setCheckMan(Long checkMan) {
    this.checkMan = checkMan;
  }
  
  public Date getCheckDate() {
    return this.checkDate;
  }
  
  public void setCheckDate(Date checkDate) {
    this.checkDate = checkDate;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getCheckManName() {
    return this.checkManName;
  }
  
  public void setCheckManName(String checkManName) {
    this.checkManName = checkManName;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof CsMasterPO))
      return false; 
    CsMasterPO castOther = (CsMasterPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Set getCsDetail() {
    return this.csDetail;
  }
  
  public void setCsDetail(Set csDetail) {
    this.csDetail = csDetail;
  }
}
