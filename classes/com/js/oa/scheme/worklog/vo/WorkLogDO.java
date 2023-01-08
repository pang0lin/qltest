package com.js.oa.scheme.worklog.vo;

import java.io.Serializable;

public class WorkLogDO implements Serializable {
  private Long logId;
  
  private String empName;
  
  public WorkLogDO() {}
  
  public String getempName() {
    return this.empName;
  }
  
  public void setempName(String empName) {
    this.empName = empName;
  }
  
  public Long getLogId() {
    return this.logId;
  }
  
  public void setLogId(Long logId) {
    this.logId = logId;
  }
  
  public WorkLogDO(Long logId, String empName) {
    this.logId = logId;
    this.empName = empName;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof WorkLogDO))
      return false; 
    WorkLogDO workLogDO = (WorkLogDO)o;
    if ((this.empName != null) ? !this.empName.equals(workLogDO.empName) : (workLogDO.empName != null))
      return false; 
    if (!this.logId.equals(workLogDO.logId))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.logId.hashCode();
    result = 29 * result + ((this.empName != null) ? this.empName.hashCode() : 0);
    return result;
  }
}
