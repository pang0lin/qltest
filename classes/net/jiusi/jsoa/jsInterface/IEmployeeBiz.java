package net.jiusi.jsoa.jsInterface;

import EmployeePojo;

public interface IEmployeeBiz {
  static {
    throw new Error("Unresolved compilation problems: \n\tThe declared package \"net.jiusi.jsoa.jsinterface\" does not match the expected package \"net.jiusi.jsoa.jsInterface\"\n\tThe import net.jiusi.jsoa.jsinterface cannot be resolved\n\tEmployeePojo cannot be resolved to a type\n\tEmployeePojo cannot be resolved to a type\n");
  }
  
  String addEmployee(EmployeePojo paramEmployeePojo);
  
  String modifyEmployee(EmployeePojo paramEmployeePojo);
  
  String disableEmployeeByAccount(String paramString);
  
  String recoverEmployeeByAccount(String paramString);
  
  String deleteEmployeeByAccount(String paramString);
}
