package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpRemindEJBLocalHome extends EJBLocalHome {
  EmpRemindEJBLocal create() throws CreateException;
}
