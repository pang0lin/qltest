package com.js.system.vo.usermanager;

import java.io.Serializable;

public class BankCardVO implements Serializable {
  private Long id;
  
  private String bankCardName;
  
  private String bankCardNO;
  
  private EmployeeVO employeeVO;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public EmployeeVO getEmployeeVO() {
    return this.employeeVO;
  }
  
  public void setEmployeeVO(EmployeeVO employeeVO) {
    this.employeeVO = employeeVO;
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
