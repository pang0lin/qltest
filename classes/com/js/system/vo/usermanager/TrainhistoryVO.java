package com.js.system.vo.usermanager;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TrainhistoryVO implements Serializable {
  private Long id;
  
  private String trainName;
  
  private String trainUnit;
  
  private String trainMemo;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private EmployeeVO employeeVO;
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public EmployeeVO getEmployeeVO() {
    return this.employeeVO;
  }
  
  public void setEmployeeVO(EmployeeVO employeeVO) {
    this.employeeVO = employeeVO;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTrainMemo() {
    return this.trainMemo;
  }
  
  public void setTrainMemo(String trainMemo) {
    this.trainMemo = trainMemo;
  }
  
  public String getTrainName() {
    return this.trainName;
  }
  
  public void setTrainName(String trainName) {
    this.trainName = trainName;
  }
  
  public String getTrainUnit() {
    return this.trainUnit;
  }
  
  public void setTrainUnit(String trainUnit) {
    this.trainUnit = trainUnit;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("empId", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TrainhistoryVO))
      return false; 
    TrainhistoryVO castOther = (TrainhistoryVO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
}
