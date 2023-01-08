package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmployeeTrainhistoryEJBLocalHome extends EJBLocalHome {
  EmployeeTrainhistoryEJBLocal create() throws CreateException;
}
