package net.jiusi.jsoa.service;

import net.jiusi.jsoa.service.pojo.EmployeePojo;

public interface IEmployeeBiz {
  String addEmployee(EmployeePojo paramEmployeePojo);
  
  String modifyEmployee(EmployeePojo paramEmployeePojo);
  
  String disableEmployeeByAccount(String paramString);
  
  String recoverEmployeeByAccount(String paramString);
  
  String deleteEmployeeByAccount(String paramString);
  
  String queryUserInfo(String paramString);
}
