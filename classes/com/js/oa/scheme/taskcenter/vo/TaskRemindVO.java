package com.js.oa.scheme.taskcenter.vo;

import java.io.Serializable;

public class TaskRemindVO implements Serializable {
  private Long remindId;
  
  private Long remindDomainId;
  
  private Long empId;
  
  private Long taskId;
  
  public TaskRemindVO() {}
  
  public TaskRemindVO(Long remindId, Long remindDomainId, Long empId, Long taskId) {
    this.remindId = remindId;
    this.empId = empId;
    this.taskId = taskId;
    this.remindDomainId = remindDomainId;
  }
  
  public Long getRemindId() {
    return this.remindId;
  }
  
  public void setRemindId(Long remindId) {
    this.remindId = remindId;
  }
  
  public Long getRemindDomainId() {
    return this.remindDomainId;
  }
  
  public void setRemindDomainId(Long remindDomainId) {
    this.remindDomainId = remindDomainId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof TaskRemindVO))
      return false; 
    TaskRemindVO taskRemindVO = (TaskRemindVO)o;
    if ((this.empId != null) ? !this.empId.equals(taskRemindVO.empId) : (taskRemindVO.empId != null))
      return false; 
    if (!this.remindId.equals(taskRemindVO.remindId))
      return false; 
    if ((this.taskId != null) ? !this.taskId.equals(taskRemindVO.taskId) : (taskRemindVO.taskId != null))
      return false; 
    if ((this.remindDomainId != null) ? !this.remindDomainId.equals(taskRemindVO.remindDomainId) : (taskRemindVO.remindDomainId != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.remindId.hashCode();
    result = 29 * result + ((this.empId != null) ? this.empId.hashCode() : 0);
    result = 29 * result + ((this.taskId != null) ? this.taskId.hashCode() : 0);
    result = 29 * result + ((this.remindDomainId != null) ? this.remindDomainId.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    StringBuffer _ret = new StringBuffer();
    _ret.append("TaskRemindVO{");
    _ret.append("/n remindDomainId = " + this.remindDomainId);
    _ret.append("/n remindId = " + this.remindId);
    _ret.append(",/n empId = " + this.empId);
    _ret.append(",/n taskId = " + this.taskId);
    _ret.append("/n}");
    return _ret.toString();
  }
}
