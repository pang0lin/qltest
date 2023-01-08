package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpCompoEJBLocalHome extends EJBLocalHome {
  EmpCompoEJBLocal create() throws CreateException;
}
