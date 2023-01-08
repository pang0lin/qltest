package net.jiusi.jsoa.service.impl;

import net.jiusi.jsoa.service.IEmployeeBiz;
import net.jiusi.jsoa.service.dao.EmployeeDao;
import net.jiusi.jsoa.service.pojo.EmployeePojo;

public class EmployeeBizImpl implements IEmployeeBiz {
  private EmployeeDao employeeDao = new EmployeeDao();
  
  public String disableEmployeeByAccount(String account) {
    int tag = this.employeeDao.modifyEmployeeStatus(account, "0", "0");
    return String.valueOf(tag);
  }
  
  public String recoverEmployeeByAccount(String account) {
    int tag = this.employeeDao.modifyEmployeeStatus(account, "1", "0");
    return String.valueOf(tag);
  }
  
  public String deleteEmployeeByAccount(String account) {
    int tag = this.employeeDao.modifyEmployeeStatus(account, "0", "1");
    return String.valueOf(tag);
  }
  
  public String modifyEmployee(EmployeePojo employee) {
    return this.employeeDao.modifyEmployeeInfo(employee);
  }
  
  public String addEmployee(EmployeePojo employee) {
    return this.employeeDao.addEmployee(employee);
  }
  
  public String queryUserInfo(String userAccount) {
    return this.employeeDao.queryUserInfo(userAccount);
  }
}
