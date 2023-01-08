package com.js.oa.hr.officemanager.action;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DutyActionForm extends ActionForm implements Serializable {
  private String dutyNO;
  
  private String dutyName;
  
  private String action;
  
  private String dutyLevel;
  
  private String dutyDescribe;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getDutyName() {
    return this.dutyName;
  }
  
  public void setDutyName(String dutyName) {
    this.dutyName = dutyName;
  }
  
  public String getAction() {
    return this.action;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public String getDutyLevel() {
    return this.dutyLevel;
  }
  
  public String getDutyDescribe() {
    return this.dutyDescribe;
  }
  
  public void setDutyLevel(String dutyLevel) {
    this.dutyLevel = dutyLevel;
  }
  
  public void setDutyDescribe(String dutyDescribe) {
    this.dutyDescribe = dutyDescribe;
  }
  
  public String getDutyNO() {
    return this.dutyNO;
  }
  
  public void setDutyNO(String dutyNO) {
    this.dutyNO = dutyNO;
  }
}
