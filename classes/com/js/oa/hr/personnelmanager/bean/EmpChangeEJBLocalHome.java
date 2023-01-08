package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpChangeEJBLocalHome extends EJBLocalHome {
  EmpChangeEJBLocal create() throws CreateException;
}
