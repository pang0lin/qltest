package com.js.oa.hr.officemanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmployeeEJBLocalHome extends EJBLocalHome {
  EmployeeEJBLocal create() throws CreateException;
}
