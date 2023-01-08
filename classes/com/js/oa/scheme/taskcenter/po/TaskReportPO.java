package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskReportPO implements Serializable {
  private Long reportId;
  
  private Long trDomainId;
  
  private String reportInfo;
  
  private Integer finishRate;
  
  private Date reportTime = null;
  
  private String checkOpinion;
  
  private Date checkTime = null;
  
  private String checkerName;
  
  private Long checkerId;
  
  private Long taskId;
  
  private TaskExecPO taskExec;
  
  private Set taskReportAccessory = null;
  
  private String reportAppend;
  
  public TaskReportPO(String reportInfo, Long trDomainId, Integer finishRate, Date reportTime, String checkOpinion, Date checkTime, String checkerName, Long checkerId, Long taskId, TaskExecPO taskExec, Set taskReportAccessory) {
    this.reportInfo = reportInfo;
    this.finishRate = finishRate;
    this.reportTime = reportTime;
    this.checkOpinion = checkOpinion;
    this.checkTime = checkTime;
    this.checkerName = checkerName;
    this.checkerId = checkerId;
    this.taskExec = taskExec;
    this.taskId = taskId;
    this.checkerId = trDomainId;
    this.taskReportAccessory = taskReportAccessory;
  }
  
  public TaskReportPO() {}
  
  public TaskReportPO(TaskExecPO taskExec, Long trDomainId, Date reportTime) {
    this.taskExec = taskExec;
    this.reportTime = reportTime;
    this.trDomainId = trDomainId;
  }
  
  public Long getReportId() {
    return this.reportId;
  }
  
  public void setReportId(Long reportId) {
    this.reportId = reportId;
  }
  
  public Long getTrDomainId() {
    return this.trDomainId;
  }
  
  public void setTrDomainId(Long reportId) {
    this.trDomainId = reportId;
  }
  
  public String getReportInfo() {
    return this.reportInfo;
  }
  
  public void setReportInfo(String reportInfo) {
    this.reportInfo = reportInfo;
  }
  
  public Integer getFinishRate() {
    return this.finishRate;
  }
  
  public void setFinishRate(Integer finishRate) {
    this.finishRate = finishRate;
  }
  
  public Date getReportTime() {
    return this.reportTime;
  }
  
  public void setReportTime(Date reportTime) {
    this.reportTime = reportTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("reportId", getReportId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskReportPO))
      return false; 
    TaskReportPO castOther = (TaskReportPO)other;
    return (new EqualsBuilder())
      .append(getReportId(), castOther.getReportId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getReportId())
      .toHashCode();
  }
  
  public TaskExecPO getTaskExec() {
    return this.taskExec;
  }
  
  public void setTaskExec(TaskExecPO taskExec) {
    this.taskExec = taskExec;
  }
  
  public Long getCheckerId() {
    return this.checkerId;
  }
  
  public void setCheckerId(Long checkerId) {
    this.checkerId = checkerId;
  }
  
  public String getCheckerName() {
    return this.checkerName;
  }
  
  public void setCheckerName(String checkerName) {
    this.checkerName = checkerName;
  }
  
  public String getCheckOpinion() {
    return this.checkOpinion;
  }
  
  public void setCheckOpinion(String checkOpinion) {
    this.checkOpinion = checkOpinion;
  }
  
  public Date getCheckTime() {
    return this.checkTime;
  }
  
  public void setCheckTime(Date checkTime) {
    this.checkTime = checkTime;
  }
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public String getReportAppend() {
    return this.reportAppend;
  }
  
  public void setReportAppend(String reportAppend) {
    this.reportAppend = reportAppend;
  }
  
  public Set getTaskReportAccessory() {
    return this.taskReportAccessory;
  }
  
  public void setTaskReportAccessory(Set taskReportAccessory) {
    this.taskReportAccessory = taskReportAccessory;
  }
}
