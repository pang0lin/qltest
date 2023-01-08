package com.js.oa.workplan.po;

import java.io.Serializable;
import java.util.Date;

public class WorkplanProxyPO implements Serializable {
  private long id;
  
  private long leaderId;
  
  private String leaderName;
  
  private long proxyId;
  
  private String proxyName;
  
  private int proxystatus;
  
  private Date proxybegin = null;
  
  private Date proxyend = null;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getLeaderId() {
    return this.leaderId;
  }
  
  public void setLeaderId(long leaderId) {
    this.leaderId = leaderId;
  }
  
  public String getLeaderName() {
    return this.leaderName;
  }
  
  public void setLeaderName(String leaderName) {
    this.leaderName = leaderName;
  }
  
  public long getProxyId() {
    return this.proxyId;
  }
  
  public void setProxyId(long proxyId) {
    this.proxyId = proxyId;
  }
  
  public String getProxyName() {
    return this.proxyName;
  }
  
  public void setProxyName(String proxyName) {
    this.proxyName = proxyName;
  }
  
  public int getProxystatus() {
    return this.proxystatus;
  }
  
  public void setProxystatus(int proxystatus) {
    this.proxystatus = proxystatus;
  }
  
  public Date getProxybegin() {
    return this.proxybegin;
  }
  
  public void setProxybegin(Date proxybegin) {
    this.proxybegin = proxybegin;
  }
  
  public Date getProxyend() {
    return this.proxyend;
  }
  
  public void setProxyend(Date proxyend) {
    this.proxyend = proxyend;
  }
}
