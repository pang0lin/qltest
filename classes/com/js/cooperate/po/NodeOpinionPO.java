package com.js.cooperate.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NodeOpinionPO implements Serializable {
  private Long id;
  
  private Long bodyId;
  
  private Long preId;
  
  private Long empId;
  
  private String content;
  
  private Integer isHidden;
  
  private String empName;
  
  private Date sendTime = null;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getBodyId() {
    return this.bodyId;
  }
  
  public void setBodyId(Long bodyId) {
    this.bodyId = bodyId;
  }
  
  public Long getPreId() {
    return this.preId;
  }
  
  public void setPreId(Long preId) {
    this.preId = preId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Integer getIsHidden() {
    return this.isHidden;
  }
  
  public void setIsHidden(Integer isHidden) {
    this.isHidden = isHidden;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
}
