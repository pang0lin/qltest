package com.js.oa.routine.resource.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PtMasterPO implements Serializable {
  private Long id;
  
  private Date ptDate = null;
  
  private String ptSupp;
  
  private String ptMan;
  
  private Date makeDate = null;
  
  private String checkFlag;
  
  private Date checkDate = null;
  
  private String remark;
  
  private Set ptDetail = null;
  
  private Long ptStock;
  
  private Float ptMoney;
  
  private Long makeMan;
  
  private Long checkMan;
  
  private String checkManName;
  
  private int domainid;
  
  private String serial;
  
  private Integer num;
  
  private String ptOrg;
  
  private String ptOrgName;
  
  private String ptMode;
  
  private String ptStoreMan;
  
  private String ptBuyer;
  
  private String ptTypeDefine;
  
  private String ptHandleName;
  
  private String invoiceNO;
  
  private String ptHaveWorkFlow;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Date getPtDate() {
    return this.ptDate;
  }
  
  public void setPtDate(Date ptDate) {
    this.ptDate = ptDate;
  }
  
  public String getPtSupp() {
    return this.ptSupp;
  }
  
  public void setPtSupp(String ptSupp) {
    this.ptSupp = ptSupp;
  }
  
  public String getPtMan() {
    return this.ptMan;
  }
  
  public void setPtMan(String ptMan) {
    this.ptMan = ptMan;
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
  
  public Set getPtDetail() {
    return this.ptDetail;
  }
  
  public void setPtDetail(Set ptDetail) {
    this.ptDetail = ptDetail;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PtDetailPO))
      return false; 
    PtDetailPO castOther = (PtDetailPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getPtStock() {
    return this.ptStock;
  }
  
  public void setPtStock(Long ptStock) {
    this.ptStock = ptStock;
  }
  
  public Float getPtMoney() {
    return this.ptMoney;
  }
  
  public void setPtMoney(Float ptMoney) {
    this.ptMoney = ptMoney;
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
  
  public String getPtMode() {
    return this.ptMode;
  }
  
  public String getPtBuyer() {
    return this.ptBuyer;
  }
  
  public void setPtBuyer(String ptBuyer) {
    this.ptBuyer = ptBuyer;
  }
  
  public void setPtMode(String ptMode) {
    this.ptMode = ptMode;
  }
  
  public String getPtOrg() {
    return this.ptOrg;
  }
  
  public void setPtOrg(String ptOrg) {
    this.ptOrg = ptOrg;
  }
  
  public String getPtOrgName() {
    return this.ptOrgName;
  }
  
  public void setPtOrgName(String ptOrgName) {
    this.ptOrgName = ptOrgName;
  }
  
  public String getPtStoreMan() {
    return this.ptStoreMan;
  }
  
  public void setPtStoreMan(String ptStoreMan) {
    this.ptStoreMan = ptStoreMan;
  }
  
  public String getPtTypeDefine() {
    return this.ptTypeDefine;
  }
  
  public void setPtTypeDefine(String ptTypeDefine) {
    this.ptTypeDefine = ptTypeDefine;
  }
  
  public String getInvoiceNO() {
    return this.invoiceNO;
  }
  
  public void setInvoiceNO(String invoiceNO) {
    this.invoiceNO = invoiceNO;
  }
  
  public String getPtHandleName() {
    return this.ptHandleName;
  }
  
  public void setPtHandleName(String ptHandleName) {
    this.ptHandleName = ptHandleName;
  }
  
  public String getPtHaveWorkFlow() {
    return this.ptHaveWorkFlow;
  }
  
  public void setPtHaveWorkFlow(String ptHaveWorkFlow) {
    this.ptHaveWorkFlow = ptHaveWorkFlow;
  }
}
