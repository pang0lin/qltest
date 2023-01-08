package com.js.system.vo.organizationmanager;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DomainVO implements Serializable {
  private Long id;
  
  private String domainName;
  
  private String domainAccount;
  
  private Integer inUse;
  
  private String module;
  
  private Integer userNum;
  
  private String serverOption;
  
  private Integer workLog;
  
  private String netDiskSize;
  
  private String mailBoxSize;
  
  private String domainType;
  
  private String domainInit;
  
  private String domainDataModule;
  
  private String domainAttachLimit;
  
  private String domainAttachLimitSize;
  
  private String domain_filterlimitcontent;
  
  private String domain_filterlimit;
  
  private Date domainRegDate = null;
  
  private Date domainEndDate = null;
  
  private String domainMemo;
  
  private Integer noLog;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getDomainName() {
    return this.domainName;
  }
  
  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }
  
  public String getDomainAccount() {
    return this.domainAccount;
  }
  
  public void setDomainAccount(String domainAccount) {
    this.domainAccount = domainAccount;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("Id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof DomainVO))
      return false; 
    DomainVO castOther = (DomainVO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Integer getInUse() {
    return this.inUse;
  }
  
  public void setInUse(Integer inUse) {
    this.inUse = inUse;
  }
  
  public String getModule() {
    return this.module;
  }
  
  public void setModule(String module) {
    this.module = module;
  }
  
  public Integer getUserNum() {
    return this.userNum;
  }
  
  public void setUserNum(Integer userNum) {
    this.userNum = userNum;
  }
  
  public String getServerOption() {
    return this.serverOption;
  }
  
  public void setServerOption(String serverOption) {
    this.serverOption = serverOption;
  }
  
  public Integer getWorkLog() {
    return this.workLog;
  }
  
  public void setWorkLog(Integer workLog) {
    this.workLog = workLog;
  }
  
  public String getNetDiskSize() {
    return this.netDiskSize;
  }
  
  public void setNetDiskSize(String netDiskSize) {
    this.netDiskSize = netDiskSize;
  }
  
  public String getMailBoxSize() {
    return this.mailBoxSize;
  }
  
  public void setMailBoxSize(String mailBoxSize) {
    this.mailBoxSize = mailBoxSize;
  }
  
  public String getDomainType() {
    return this.domainType;
  }
  
  public void setDomainType(String domainType) {
    this.domainType = domainType;
  }
  
  public String getDomainInit() {
    return this.domainInit;
  }
  
  public void setDomainInit(String domainInit) {
    this.domainInit = domainInit;
  }
  
  public String getDomainDataModule() {
    return this.domainDataModule;
  }
  
  public void setDomainDataModule(String domainDataModule) {
    this.domainDataModule = domainDataModule;
  }
  
  public String getDomainAttachLimit() {
    return this.domainAttachLimit;
  }
  
  public void setDomainAttachLimit(String domainAttachLimit) {
    this.domainAttachLimit = domainAttachLimit;
  }
  
  public String getDomainAttachLimitSize() {
    return this.domainAttachLimitSize;
  }
  
  public void setDomainAttachLimitSize(String domainAttachLimitSize) {
    this.domainAttachLimitSize = domainAttachLimitSize;
  }
  
  public Date getDomainRegDate() {
    return this.domainRegDate;
  }
  
  public void setDomainRegDate(Date domainRegDate) {
    this.domainRegDate = domainRegDate;
  }
  
  public Date getDomainEndDate() {
    return this.domainEndDate;
  }
  
  public void setDomainEndDate(Date domainEndDate) {
    this.domainEndDate = domainEndDate;
  }
  
  public String getDomainMemo() {
    return this.domainMemo;
  }
  
  public void setDomainMemo(String domainMemo) {
    this.domainMemo = domainMemo;
  }
  
  public Integer getNoLog() {
    return this.noLog;
  }
  
  public void setNoLog(Integer noLog) {
    this.noLog = noLog;
  }
  
  public String getDomain_filterlimitcontent() {
    return this.domain_filterlimitcontent;
  }
  
  public void setDomain_filterlimitcontent(String domain_filterlimitcontent) {
    this.domain_filterlimitcontent = domain_filterlimitcontent;
  }
  
  public String getDomain_filterlimit() {
    return this.domain_filterlimit;
  }
  
  public void setDomain_filterlimit(String domain_filterlimit) {
    this.domain_filterlimit = domain_filterlimit;
  }
}
