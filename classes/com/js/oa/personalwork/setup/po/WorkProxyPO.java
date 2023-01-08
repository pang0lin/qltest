package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkProxyPO implements Serializable {
  private long id;
  
  private long empId;
  
  private long proxyEmpId;
  
  private String proxyEmpName;
  
  private Date beginTime = null;
  
  private Date endTime = null;
  
  private byte proxyState;
  
  private String proxyAllProcess;
  
  private String proxyProcess;
  
  private String domainId;
  
  private String proxyOrgName;
  
  private String proxyOrgId;
  
  private String empName;
  
  private String createEmpId;
  
  private String createEmpName;
  
  private Date createTime = null;
  
  public WorkProxyPO(long empId, long proxyempid, String proxyempname, Date begintime, Date endtime, byte proxystate) {
    this.empId = empId;
    this.proxyEmpId = proxyempid;
    this.proxyEmpName = proxyempname;
    this.beginTime = begintime;
    this.endTime = endtime;
    this.proxyState = proxystate;
  }
  
  public WorkProxyPO() {}
  
  public WorkProxyPO(long empId, long proxyempid, Date begintime, Date endtime) {
    this.empId = empId;
    this.proxyEmpId = proxyempid;
    this.beginTime = begintime;
    this.endTime = endtime;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public long getProxyEmpId() {
    return this.proxyEmpId;
  }
  
  public void setProxyEmpId(long proxyEmpId) {
    this.proxyEmpId = proxyEmpId;
  }
  
  public String getProxyEmpName() {
    return this.proxyEmpName;
  }
  
  public void setProxyEmpName(String proxyEmpName) {
    this.proxyEmpName = proxyEmpName;
  }
  
  public Date getBeginTime() {
    return this.beginTime;
  }
  
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }
  
  public Date getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  
  public byte getProxyState() {
    return this.proxyState;
  }
  
  public void setProxyState(byte proxyState) {
    this.proxyState = proxyState;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkProxyPO))
      return false; 
    WorkProxyPO castOther = (WorkProxyPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getProxyOrgName() {
    return this.proxyOrgName;
  }
  
  public void setProxyOrgName(String proxyOrgName) {
    this.proxyOrgName = proxyOrgName;
  }
  
  public String getProxyOrgId() {
    return this.proxyOrgId;
  }
  
  public void setProxyOrgId(String proxyOrgId) {
    this.proxyOrgId = proxyOrgId;
  }
  
  public String getProxyProcess() {
    return this.proxyProcess;
  }
  
  public void setProxyProcess(String proxyProcess) {
    this.proxyProcess = proxyProcess;
  }
  
  public String getProxyAllProcess() {
    return this.proxyAllProcess;
  }
  
  public void setProxyAllProcess(String proxyAllProcess) {
    this.proxyAllProcess = proxyAllProcess;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getCreateEmpId() {
    return this.createEmpId;
  }
  
  public void setCreateEmpId(String createEmpId) {
    this.createEmpId = createEmpId;
  }
  
  public String getCreateEmpName() {
    return this.createEmpName;
  }
  
  public void setCreateEmpName(String createEmpName) {
    this.createEmpName = createEmpName;
  }
  
  public Date getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
