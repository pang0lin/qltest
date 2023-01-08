package com.js.system.vo.organizationmanager;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SyncRTXVO {
  private Long id;
  
  private Long orgId;
  
  private String userAccount;
  
  private String userPwd;
  
  private Byte dataType;
  
  private Byte dataOpr;
  
  public SyncRTXVO(Long orgId, String userAccount, String userPwd, Byte dataType, Byte dataOpr) {
    this.orgId = orgId;
    this.userAccount = userAccount;
    this.userPwd = userPwd;
    this.dataType = dataType;
    this.dataOpr = dataOpr;
  }
  
  public SyncRTXVO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getUserAccount() {
    return this.userAccount;
  }
  
  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }
  
  public String getUserPwd() {
    return this.userPwd;
  }
  
  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }
  
  public Byte getDataType() {
    return this.dataType;
  }
  
  public void setDataType(Byte dataType) {
    this.dataType = dataType;
  }
  
  public Byte getDataOpr() {
    return this.dataOpr;
  }
  
  public void setDataOpr(Byte dataOpr) {
    this.dataOpr = dataOpr;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SyncRTXVO))
      return false; 
    SyncRTXVO castOther = (SyncRTXVO)other;
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
