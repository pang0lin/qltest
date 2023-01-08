package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmployeeCompetenceEJBLocalHome extends EJBLocalHome {
  EmployeeCompetenceEJBLocal create() throws CreateException;
}
