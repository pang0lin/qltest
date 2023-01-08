package com.js.oa.scheme.taskcenter.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskReportVO implements Serializable {
  private Long reportId;
  
  private Long trDomainId;
  
  private String reportInfo;
  
  private Integer finishRate;
  
  private Date reportTime = null;
  
  private String reportTimeFormat;
  
  private String checkOpinion;
  
  private Date checkTime = null;
  
  private String checkTimeFormat;
  
  private String checkerName;
  
  private Long checkerId;
  
  private Long empId;
  
  private String empName;
  
  private Long taskId;
  
  private String reportAppend;
  
  public TaskReportVO() {}
  
  public TaskReportVO(Long reportId, Long trDomainId, String reportInfo, Long empId, Integer finishRate, Date reportTime, String checkOpinion, Date checkTime, String checkerName, Long checkerId) {
    this.reportId = reportId;
    this.reportInfo = reportInfo;
    this.finishRate = finishRate;
    this.reportTime = reportTime;
    this.checkOpinion = checkOpinion;
    this.checkTime = checkTime;
    this.checkerName = checkerName;
    this.checkerId = checkerId;
    this.empId = empId;
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
  
  public String getCheckerName() {
    return this.checkerName;
  }
  
  public void setCheckerName(String checkerName) {
    this.checkerName = checkerName;
  }
  
  public Long getCheckerId() {
    return this.checkerId;
  }
  
  public void setCheckerId(Long checkerId) {
    this.checkerId = checkerId;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof TaskReportVO))
      return false; 
    TaskReportVO taskReportVO = (TaskReportVO)o;
    if ((this.checkOpinion != null) ? 
      !this.checkOpinion.equals(taskReportVO.checkOpinion) : (
      taskReportVO.checkOpinion != null))
      return false; 
    if ((this.trDomainId != null) ? 
      !this.trDomainId.equals(taskReportVO.trDomainId) : (
      taskReportVO.trDomainId != null))
      return false; 
    if ((this.checkTime != null) ? !this.checkTime.equals(taskReportVO.checkTime) : (
      taskReportVO.checkTime != null))
      return false; 
    if ((this.checkerId != null) ? !this.checkerId.equals(taskReportVO.checkerId) : (
      taskReportVO.checkerId != null))
      return false; 
    if ((this.checkerName != null) ? !this.checkerName.equals(taskReportVO.checkerName) : (
      taskReportVO.checkerName != null))
      return false; 
    if ((this.finishRate != null) ? !this.finishRate.equals(taskReportVO.finishRate) : (
      taskReportVO.finishRate != null))
      return false; 
    if (!this.reportId.equals(taskReportVO.reportId))
      return false; 
    if ((this.reportInfo != null) ? !this.reportInfo.equals(taskReportVO.reportInfo) : (
      taskReportVO.reportInfo != null))
      return false; 
    if ((this.reportTime != null) ? !this.reportTime.equals(taskReportVO.reportTime) : (
      taskReportVO.reportTime != null))
      return false; 
    if ((this.taskId != null) ? !this.taskId.equals(taskReportVO.taskId) : (
      taskReportVO.taskId != null))
      return false; 
    if ((this.empId != null) ? !this.empId.equals(taskReportVO.empId) : (
      taskReportVO.empId != null))
      return false; 
    if ((this.empName != null) ? !this.empName.equals(taskReportVO.empName) : (
      taskReportVO.empName != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.reportId.hashCode();
    result = 29 * result + ((this.reportInfo != null) ? this.reportInfo.hashCode() : 0);
    result = 29 * result + ((this.finishRate != null) ? this.finishRate.hashCode() : 0);
    result = 29 * result + ((this.reportTime != null) ? this.reportTime.hashCode() : 0);
    result = 29 * result + (
      (this.checkOpinion != null) ? this.checkOpinion.hashCode() : 0);
    result = 29 * result + ((this.checkTime != null) ? this.checkTime.hashCode() : 0);
    result = 29 * result + (
      (this.checkerName != null) ? this.checkerName.hashCode() : 0);
    result = 29 * result + ((this.checkerId != null) ? this.checkerId.hashCode() : 0);
    result = 29 * result + ((this.taskId != null) ? this.taskId.hashCode() : 0);
    result = 29 * result + ((this.empId != null) ? this.taskId.hashCode() : 0);
    result = 29 * result + ((this.empName != null) ? this.empName.hashCode() : 0);
    result = 29 * result + ((this.trDomainId != null) ? this.trDomainId.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "TaskReportVO{reportId=" + 
      this.reportId + 
      ", trDomainId=" + this.trDomainId + 
      ", reportInfo='" + this.reportInfo + "'" + 
      ", finishRate=" + this.finishRate + 
      ", reportTime=" + this.reportTime + 
      ", checkOpinion='" + this.checkOpinion + "'" + 
      ", checkTime=" + this.checkTime + 
      ", checkerName='" + this.checkerName + "'" + 
      ", checkerId=" + this.checkerId + 
      ", taskId = " + this.taskId + 
      ", empId = " + this.empId + 
      ", empName = " + this.empName + 
      "}";
  }
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getCheckTimeFormat() {
    return dateToString(getCheckTime());
  }
  
  public String getReportTimeFormat() {
    return dateToString(getReportTime());
  }
  
  private String dateToString(Date date) {
    if (date != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss", Locale.CHINESE);
      String s = null;
      s = formatter.format(date);
      return s;
    } 
    return null;
  }
  
  public String getReportAppend() {
    return this.reportAppend;
  }
  
  public void setReportAppend(String reportAppend) {
    this.reportAppend = reportAppend;
  }
}
