package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmployeeTrainhistoryActionForm extends ActionForm {
  private Long id;
  
  private String trainName;
  
  private String trainUnit;
  
  private String trainMemo;
  
  private String beginDate;
  
  private String endDate;
  
  private Long empID;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public String getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(String endDate) {
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
}
