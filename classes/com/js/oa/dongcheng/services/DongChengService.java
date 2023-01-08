package com.js.oa.dongcheng.services;

import com.js.oa.dongcheng.dao.EmployeeDAO;
import com.js.oa.dongcheng.dao.OrganizationDAO;
import com.js.oa.dongcheng.pojo.EmployeePojo;
import com.js.oa.dongcheng.pojo.OrganizationPojo;

public class DongChengService {
  private OrganizationDAO orgDAO = new OrganizationDAO();
  
  private EmployeeDAO empDAO = new EmployeeDAO();
  
  public String insertOrganization(String orgGUID, String orgName, String orgDescripte, String parentOrgGUID, String orderOrgGUID, String tag) {
    OrganizationPojo org = new OrganizationPojo();
    org.setOrgGUID(orgGUID);
    org.setOrgName(orgName);
    org.setOrgDescripte(orgDescripte);
    org.setOrgParentOrgGUID(parentOrgGUID);
    org.setOrgSimpleName(orgName);
    return this.orgDAO.insertOrganization(org, orderOrgGUID, tag);
  }
  
  public String updateOrganization(String orgGUID, String orgName, String orgDescripte, String parentOrgGUID, String orderOrgGUID, String tag) {
    OrganizationPojo org = new OrganizationPojo();
    org.setOrgGUID(orgGUID);
    org.setOrgName(orgName);
    org.setOrgDescripte(orgDescripte);
    org.setOrgParentOrgGUID(parentOrgGUID);
    org.setOrgSimpleName(orgName);
    return this.orgDAO.updateOrganization(org, orderOrgGUID, tag);
  }
  
  public String insertUser(String userGUID, String userAccounts, String userPassword, String empName, String empSex, String orgGUID, String iPContrl, String ipContrlBeginTime, String ipContrlEndTime, String empLeader, String empNumber) {
    EmployeePojo employee = new EmployeePojo();
    employee.setUserGUID(userGUID);
    employee.setUserAccounts(userAccounts);
    employee.setUserPassword(userPassword);
    employee.setEmpName(empName);
    employee.setEmpSex(empSex);
    employee.setOrgGUID(orgGUID);
    employee.setIpContrl(iPContrl);
    employee.setIpContrlBeginTime(ipContrlBeginTime);
    employee.setIpContrlEndTime(ipContrlEndTime);
    employee.setEmpLeader(empLeader);
    employee.setEmpnumber(empNumber);
    return this.empDAO.insertUser(employee);
  }
  
  public String updateUser(String userGUID, String userAccounts, String userPassword, String empName, String empSex, String orgGUID, String iPContrl, String ipContrlBeginTime, String ipContrlEndTime, String empLeader, String empNumber) {
    EmployeePojo employee = new EmployeePojo();
    employee.setUserGUID(userGUID);
    employee.setUserAccounts(userAccounts);
    employee.setUserPassword(userPassword);
    employee.setEmpName(empName);
    employee.setEmpSex(empSex);
    employee.setOrgGUID(orgGUID);
    employee.setIpContrl(iPContrl);
    employee.setIpContrlBeginTime(ipContrlBeginTime);
    employee.setIpContrlEndTime(ipContrlEndTime);
    employee.setEmpLeader(empLeader);
    employee.setEmpnumber(empNumber);
    return this.empDAO.updateUser(employee);
  }
}
