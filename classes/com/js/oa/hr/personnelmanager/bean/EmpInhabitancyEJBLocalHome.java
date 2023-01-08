package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpInhabitancyEJBLocalHome extends EJBLocalHome {
  EmpInhabitancyEJBLocal create() throws CreateException;
}
