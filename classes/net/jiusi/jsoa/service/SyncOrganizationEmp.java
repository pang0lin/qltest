package net.jiusi.jsoa.service;

import net.jiusi.jsoa.service.impl.EmployeeBizImpl;
import net.jiusi.jsoa.service.impl.OrgBziImpl;
import net.jiusi.jsoa.service.pojo.EmployeePojo;
import net.jiusi.jsoa.service.pojo.OrganizationPojo;

public class SyncOrganizationEmp {
  IEmployeeBiz ieb = new EmployeeBizImpl();
  
  IOrgBiz iob = new OrgBziImpl();
  
  public String addOrganization(String orgSerial, String orgName, String orgDescripte, String parentOrgSerial, String orderOrgSerial, String tag) {
    OrganizationPojo org = new OrganizationPojo();
    org.setOrgSerial(orgSerial);
    org.setOrgName(orgName);
    org.setOrgDescripte(orgDescripte);
    org.setOrgParentOrgSerial(parentOrgSerial);
    org.setOrgSimpleName(orgName);
    return this.iob.addOrganization(org, orderOrgSerial, tag);
  }
  
  public String modifyOrg(String orgSerial, String orgName, String orgDescripte, String parentOrgSerial, String orderOrgSerial, String tag) {
    OrganizationPojo org = new OrganizationPojo();
    org.setOrgSerial(orgSerial);
    org.setOrgName(orgName);
    org.setOrgDescripte(orgDescripte);
    org.setOrgParentOrgSerial(parentOrgSerial);
    org.setOrgSimpleName(orgName);
    return this.iob.modifyOrg(org, orderOrgSerial, tag);
  }
  
  public String deleteOrgByOrgSerial(String orgSerial) {
    return this.iob.deleteOrgByOrgSerial(orgSerial);
  }
  
  public String disableOrgByOrgSerial(String orgSerial) {
    return this.iob.disableOrgByOrgSerial(orgSerial);
  }
  
  public String recoverOrgByOrgSerial(String orgSerial) {
    return this.iob.recoverOrgByOrgSerial(orgSerial);
  }
  
  public String addEmployee(String userAccounts, String userPassword, String empName, String empSex, String orgSerial, String iPContrl, String ipContrlBeginTime, String ipContrlEndTime, String empLeader) {
    EmployeePojo employee = new EmployeePojo();
    employee.setUserAccounts(userAccounts);
    employee.setUserPassword(userPassword);
    employee.setEmpName(empName);
    employee.setEmpSex(empSex);
    employee.setOrgSerial(orgSerial);
    employee.setIpContrl(iPContrl);
    employee.setIpContrlBeginTime(ipContrlBeginTime);
    employee.setIpContrlEndTime(ipContrlEndTime);
    employee.setEmpLeader(empLeader);
    return this.ieb.addEmployee(employee);
  }
  
  public String addEmployeeInfo(String userAccounts, String userPassword, String empName, String empSex, String orgSerial, String iPContrl, String ipContrlBeginTime, String ipContrlEndTime, String empLeader, String empNumber) {
    EmployeePojo employee = new EmployeePojo();
    employee.setUserAccounts(userAccounts);
    employee.setUserPassword(userPassword);
    employee.setEmpName(empName);
    employee.setEmpSex(empSex);
    employee.setOrgSerial(orgSerial);
    employee.setIpContrl(iPContrl);
    employee.setIpContrlBeginTime(ipContrlBeginTime);
    employee.setIpContrlEndTime(ipContrlEndTime);
    employee.setEmpLeader(empLeader);
    employee.setEmpnumber(empNumber);
    return this.ieb.addEmployee(employee);
  }
  
  public String modifyEmployee(String userAccounts, String userPassword, String empName, String empSex, String orgSerial, String iPContrl, String ipContrlBeginTime, String ipContrlEndTime, String empLeader) {
    EmployeePojo employee = new EmployeePojo();
    employee.setUserAccounts(userAccounts);
    employee.setUserPassword(userPassword);
    employee.setEmpName(empName);
    employee.setEmpSex(empSex);
    employee.setOrgSerial(orgSerial);
    employee.setIpContrl(iPContrl);
    employee.setIpContrlBeginTime(ipContrlBeginTime);
    employee.setIpContrlEndTime(ipContrlEndTime);
    employee.setEmpLeader(empLeader);
    return this.ieb.modifyEmployee(employee);
  }
  
  public String modifyEmployeeInfo(String userAccounts, String userPassword, String empName, String empSex, String orgSerial, String iPContrl, String ipContrlBeginTime, String ipContrlEndTime, String empLeader, String empNumber) {
    EmployeePojo employee = new EmployeePojo();
    employee.setUserAccounts(userAccounts);
    employee.setUserPassword(userPassword);
    employee.setEmpName(empName);
    employee.setEmpSex(empSex);
    employee.setOrgSerial(orgSerial);
    employee.setIpContrl(iPContrl);
    employee.setIpContrlBeginTime(ipContrlBeginTime);
    employee.setIpContrlEndTime(ipContrlEndTime);
    employee.setEmpLeader(empLeader);
    employee.setEmpnumber(empNumber);
    return this.ieb.modifyEmployee(employee);
  }
  
  public String disableEmployeeByAccount(String account) {
    return this.ieb.disableEmployeeByAccount(account);
  }
  
  public String recoverEmployeeByAccount(String account) {
    return this.ieb.recoverEmployeeByAccount(account);
  }
  
  public String deleteEmployeeByAccount(String account) {
    return this.ieb.deleteEmployeeByAccount(account);
  }
  
  public String queryUserInfo(String userAccount) {
    return this.ieb.queryUserInfo(userAccount);
  }
}
