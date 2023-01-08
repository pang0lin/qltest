package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmployeeWorkEJBLocalHome extends EJBLocalHome {
  EmployeeWorkEJBLocal create() throws CreateException;
}
