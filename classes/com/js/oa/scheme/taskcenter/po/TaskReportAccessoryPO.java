package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskReportAccessoryPO implements Serializable {
  private Long accessoryid;
  
  private String accessoryname;
  
  private String accessorysavename;
  
  private Long domain_id;
  
  private TaskReportPO taskReport;
  
  public TaskReportAccessoryPO(Long accessoryid, String accessoryname, String accessorysavename, TaskReportPO taskReport, Long domain_id) {
    this.accessoryid = accessoryid;
    this.accessoryname = accessoryname;
    this.accessorysavename = accessorysavename;
    this.taskReport = taskReport;
    this.domain_id = domain_id;
  }
  
  public TaskReportAccessoryPO() {}
  
  public Long getAccessoryid() {
    return this.accessoryid;
  }
  
  public void setAccessoryid(Long accessoryid) {
    this.accessoryid = accessoryid;
  }
  
  public String getAccessoryname() {
    return this.accessoryname;
  }
  
  public void setAccessoryname(String accessoryname) {
    this.accessoryname = accessoryname;
  }
  
  public String getAccessorysavename() {
    return this.accessorysavename;
  }
  
  public void setAccessorysavename(String accessorysavename) {
    this.accessorysavename = accessorysavename;
  }
  
  public TaskReportPO getTaskReport() {
    return this.taskReport;
  }
  
  public void setTaskReport(TaskReportPO taskReport) {
    this.taskReport = taskReport;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("accessoryid", getAccessoryid())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskReportAccessoryPO))
      return false; 
    TaskReportAccessoryPO castOther = (TaskReportAccessoryPO)other;
    return (new EqualsBuilder())
      .append(getAccessorysavename(), castOther.getAccessorysavename())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAccessoryid())
      .toHashCode();
  }
  
  public Long getDomain_id() {
    return this.domain_id;
  }
  
  public void setDomain_id(Long domain_id) {
    this.domain_id = domain_id;
  }
}
