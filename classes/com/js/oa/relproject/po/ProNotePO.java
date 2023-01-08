package com.js.oa.relproject.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ProNotePO implements Serializable {
  private Long id;
  
  private Long projectId;
  
  private Long empId;
  
  private String empName;
  
  private String title;
  
  private String content;
  
  private Date sendTime = null;
  
  public ProNotePO(Long id) {
    this.id = id;
  }
  
  public ProNotePO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getProjectId() {
    return this.projectId;
  }
  
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
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
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
}
