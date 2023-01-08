package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskexecAccessoryPO implements Serializable {
  private Long accessoryid;
  
  private String accessoryname;
  
  private String accessorysavename;
  
  private Long domain_id;
  
  private TaskPeriodicityPO task;
  
  public TaskexecAccessoryPO(Long accessoryid, String accessoryname, String accessorysavename, TaskPeriodicityPO task, Long domain_id) {
    this.accessoryid = accessoryid;
    this.accessoryname = accessoryname;
    this.accessorysavename = accessorysavename;
    this.task = task;
    this.domain_id = domain_id;
  }
  
  public TaskexecAccessoryPO() {}
  
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
  
  public TaskPeriodicityPO getTask() {
    return this.task;
  }
  
  public void setTask(TaskPeriodicityPO task) {
    this.task = task;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("accessoryid", getAccessoryid())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskAccessoryPO))
      return false; 
    TaskAccessoryPO castOther = (TaskAccessoryPO)other;
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
