package com.js.oa.scheme.worklog.vo;

import java.io.Serializable;

public class SimpleInfomationVO implements Serializable {
  private String projectName;
  
  private String projectStep;
  
  private Long countTime;
  
  public SimpleInfomationVO() {}
  
  public SimpleInfomationVO(String projectName, String projectStep, Long countTime) {
    this.projectName = projectName;
    this.projectStep = projectStep;
    this.countTime = countTime;
  }
  
  public String getProjectStep() {
    return this.projectStep;
  }
  
  public void setProjectStep(String projectStep) {
    this.projectStep = projectStep;
  }
  
  public Long getCountTime() {
    return this.countTime;
  }
  
  public void setCountTime(Long countTime) {
    this.countTime = countTime;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof SimpleInfomationVO))
      return false; 
    SimpleInfomationVO simpleInfomationVO = (SimpleInfomationVO)o;
    if ((this.countTime != null) ? !this.countTime.equals(simpleInfomationVO.countTime) : (simpleInfomationVO.countTime != null))
      return false; 
    if ((this.projectStep != null) ? !this.projectStep.equals(simpleInfomationVO.projectStep) : (simpleInfomationVO.projectStep != null))
      return false; 
    if ((this.projectName != null) ? !this.projectName.equals(simpleInfomationVO.projectName) : (simpleInfomationVO.projectName != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = (this.projectStep != null) ? this.projectStep.hashCode() : 0;
    result = 29 * result + ((this.projectName != null) ? this.projectName.hashCode() : 0);
    result = 29 * result + ((this.countTime != null) ? this.countTime.hashCode() : 0);
    return result;
  }
  
  public String getProjectName() {
    return this.projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
}
