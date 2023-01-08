package com.js.oa.messageWall.po;

import java.io.Serializable;
import java.util.Date;

public class MessageWallPO implements Serializable {
  private long messagewallId;
  
  private long messagewallEmpid;
  
  private String messagewallEmpname;
  
  private long messagewallOrgid;
  
  private String messagewallOrgname;
  
  private String messagewallContent;
  
  private String messagewallClass;
  
  private String messagewallAccounts;
  
  private Date messagewallTime = null;
  
  private int domainId;
  
  public long getMessagewallId() {
    return this.messagewallId;
  }
  
  public void setMessagewallId(long messagewallId) {
    this.messagewallId = messagewallId;
  }
  
  public long getMessagewallEmpid() {
    return this.messagewallEmpid;
  }
  
  public void setMessagewallEmpid(long messagewallEmpid) {
    this.messagewallEmpid = messagewallEmpid;
  }
  
  public String getMessagewallEmpname() {
    return this.messagewallEmpname;
  }
  
  public void setMessagewallEmpname(String messagewallEmpname) {
    this.messagewallEmpname = messagewallEmpname;
  }
  
  public long getMessagewallOrgid() {
    return this.messagewallOrgid;
  }
  
  public void setMessagewallOrgid(long messagewallOrgid) {
    this.messagewallOrgid = messagewallOrgid;
  }
  
  public String getMessagewallOrgname() {
    return this.messagewallOrgname;
  }
  
  public void setMessagewallOrgname(String messagewallOrgname) {
    this.messagewallOrgname = messagewallOrgname;
  }
  
  public String getMessagewallContent() {
    return this.messagewallContent;
  }
  
  public void setMessagewallContent(String messagewallContent) {
    this.messagewallContent = messagewallContent;
  }
  
  public String getMessagewallClass() {
    return this.messagewallClass;
  }
  
  public void setMessagewallClass(String messagewallClass) {
    this.messagewallClass = messagewallClass;
  }
  
  public String getMessagewallAccounts() {
    return this.messagewallAccounts;
  }
  
  public void setMessagewallAccounts(String messagewallAccounts) {
    this.messagewallAccounts = messagewallAccounts;
  }
  
  public Date getMessagewallTime() {
    return this.messagewallTime;
  }
  
  public void setMessagewallTime(Date messagewallTime) {
    this.messagewallTime = messagewallTime;
  }
  
  public int getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(int domain_id) {
    this.domainId = domain_id;
  }
  
  public MessageWallPO() {}
  
  public MessageWallPO(long messagewallEmpid, String messagewallEmpname, long messagewallOrgid, String messagewallOrgname, String messagewallContent, String messagewallClass, String messagewallAccounts, Date messagewallTime, int domainId) {
    this.messagewallEmpid = messagewallEmpid;
    this.messagewallEmpname = messagewallEmpname;
    this.messagewallOrgid = messagewallOrgid;
    this.messagewallOrgname = messagewallOrgname;
    this.messagewallContent = messagewallContent;
    this.messagewallClass = messagewallClass;
    this.messagewallAccounts = messagewallAccounts;
    this.messagewallTime = messagewallTime;
    this.domainId = domainId;
  }
  
  public MessageWallPO(long messagewallId, long messagewallEmpid, String messagewallEmpname, long messagewallOrgid, String messagewallOrgname, String messagewallContent, String messagewallClass, String messagewallAccounts, Date messagewallTime, int domainId) {
    this.messagewallId = messagewallId;
    this.messagewallEmpid = messagewallEmpid;
    this.messagewallEmpname = messagewallEmpname;
    this.messagewallOrgid = messagewallOrgid;
    this.messagewallOrgname = messagewallOrgname;
    this.messagewallContent = messagewallContent;
    this.messagewallClass = messagewallClass;
    this.messagewallAccounts = messagewallAccounts;
    this.messagewallTime = messagewallTime;
    this.domainId = domainId;
  }
}
