package com.js.oa.routine.resource.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SsMasterPO implements Serializable {
  private Long id;
  
  private Date ssDate = null;
  
  private String ssMan;
  
  private Date makeDate = null;
  
  private String checkFlag;
  
  private Date checkDate = null;
  
  private String remark;
  
  private Set ssDetail = null;
  
  private Long ssStock;
  
  private Float ssMoney;
  
  private Long makeMan;
  
  private Long checkMan;
  
  private String checkManName;
  
  private Long ssDept;
  
  private Long ssHaveWorkFlow;
  
  private int domainid;
  
  private String serial;
  
  private Integer num;
  
  private String ssOrg;
  
  private String ssOrgName;
  
  private String ssMode;
  
  private String ssStoreMan;
  
  private String ssPicker;
  
  private String ssTypeDefine;
  
  private String ssOutFlag;
  
  private String ssUseManID;
  
  private String ssUseMan;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Date getSsDate() {
    return this.ssDate;
  }
  
  public void setSsDate(Date ssDate) {
    this.ssDate = ssDate;
  }
  
  public Long getSsDept() {
    return this.ssDept;
  }
  
  public void setSsDept(Long ssDept) {
    this.ssDept = ssDept;
  }
  
  public String getSsMan() {
    return this.ssMan;
  }
  
  public void setSsMan(String ssMan) {
    this.ssMan = ssMan;
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
  
  public Date getCheckDate() {
    return this.checkDate;
  }
  
  public void setCheckDate(Date checkDate) {
    this.checkDate = checkDate;
  }
  
  public Long getCheckMan() {
    return this.checkMan;
  }
  
  public void setCheckMan(Long checkMan) {
    this.checkMan = checkMan;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public Set getSsDetail() {
    return this.ssDetail;
  }
  
  public void setSsDetail(Set ssDetail) {
    this.ssDetail = ssDetail;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SsMasterPO))
      return false; 
    SsMasterPO castOther = (SsMasterPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getSsStock() {
    return this.ssStock;
  }
  
  public void setSsStock(Long ssStock) {
    this.ssStock = ssStock;
  }
  
  public Float getSsMoney() {
    return this.ssMoney;
  }
  
  public void setSsMoney(Float ssMoney) {
    this.ssMoney = ssMoney;
  }
  
  public Long getMakeMan() {
    return this.makeMan;
  }
  
  public void setMakeMan(Long makeMan) {
    this.makeMan = makeMan;
  }
  
  public String getCheckManName() {
    return this.checkManName;
  }
  
  public void setCheckManName(String checkManName) {
    this.checkManName = checkManName;
  }
  
  public Long getSsHaveWorkFlow() {
    return this.ssHaveWorkFlow;
  }
  
  public void setSsHaveWorkFlow(Long ssHaveWorkFlow) {
    this.ssHaveWorkFlow = ssHaveWorkFlow;
  }
  
  public int getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(int domainid) {
    this.domainid = domainid;
  }
  
  public String getSerial() {
    return this.serial;
  }
  
  public void setSerial(String serial) {
    this.serial = serial;
  }
  
  public Integer getNum() {
    return this.num;
  }
  
  public void setNum(Integer num) {
    this.num = num;
  }
  
  public String getSsMode() {
    return this.ssMode;
  }
  
  public void setSsMode(String ssMode) {
    this.ssMode = ssMode;
  }
  
  public String getSsOrg() {
    return this.ssOrg;
  }
  
  public void setSsOrg(String ssOrg) {
    this.ssOrg = ssOrg;
  }
  
  public String getSsOrgName() {
    return this.ssOrgName;
  }
  
  public void setSsOrgName(String ssOrgName) {
    this.ssOrgName = ssOrgName;
  }
  
  public String getSsPicker() {
    return this.ssPicker;
  }
  
  public void setSsPicker(String ssPicker) {
    this.ssPicker = ssPicker;
  }
  
  public String getSsStoreMan() {
    return this.ssStoreMan;
  }
  
  public void setSsStoreMan(String ssStoreMan) {
    this.ssStoreMan = ssStoreMan;
  }
  
  public String getSsTypeDefine() {
    return this.ssTypeDefine;
  }
  
  public void setSsTypeDefine(String ssTypeDefine) {
    this.ssTypeDefine = ssTypeDefine;
  }
  
  public String getSsOutFlag() {
    return this.ssOutFlag;
  }
  
  public void setSsOutFlag(String ssOutFlag) {
    this.ssOutFlag = ssOutFlag;
  }
  
  public String getSsUseManID() {
    return this.ssUseManID;
  }
  
  public void setSsUseManID(String ssUseManID) {
    this.ssUseManID = ssUseManID;
  }
  
  public String getSsUseMan() {
    return this.ssUseMan;
  }
  
  public void setSsUseMan(String ssUseMan) {
    this.ssUseMan = ssUseMan;
  }
}
