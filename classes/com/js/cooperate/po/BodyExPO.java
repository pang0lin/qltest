package com.js.cooperate.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BodyExPO implements Serializable {
  private Long id;
  
  private Long bodyId;
  
  private String content;
  
  private String empName;
  
  private String empId;
  
  private Date appendTime = null;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Long getBodyId() {
    return this.bodyId;
  }
  
  public void setBodyId(Long bodyId) {
    this.bodyId = bodyId;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public Date getAppendTime() {
    return this.appendTime;
  }
  
  public void setAppendTime(Date appendTime) {
    this.appendTime = appendTime;
  }
}
