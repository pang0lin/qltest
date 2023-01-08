package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmployeeEdustoryEJBLocalHome extends EJBLocalHome {
  EmployeeEdustoryEJBLocal create() throws CreateException;
}
