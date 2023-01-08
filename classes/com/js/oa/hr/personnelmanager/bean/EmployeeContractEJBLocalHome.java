package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmployeeContractEJBLocalHome extends EJBLocalHome {
  EmployeeContractEJBLocal create() throws CreateException;
}
