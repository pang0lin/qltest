package com.js.oa.hr.personnelmanager.action;

import org.apache.struts.action.ActionForm;

public class EmployeeBankCardActionForm extends ActionForm {
  private Long id;
  
  private String bankCardName;
  
  private String bankCardNO;
  
  private Long empID;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public String getBankCardName() {
    return this.bankCardName;
  }
  
  public void setBankCardName(String bankCardName) {
    this.bankCardName = bankCardName;
  }
  
  public String getBankCardNO() {
    return this.bankCardNO;
  }
  
  public void setBankCardNO(String bankCardNO) {
    this.bankCardNO = bankCardNO;
  }
}
