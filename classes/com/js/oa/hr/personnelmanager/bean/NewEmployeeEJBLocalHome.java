package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface NewEmployeeEJBLocalHome extends EJBLocalHome {
  NewEmployeeEJBLocal create() throws CreateException;
}
