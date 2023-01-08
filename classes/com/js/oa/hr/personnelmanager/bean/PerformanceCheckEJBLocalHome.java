package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PerformanceCheckEJBLocalHome extends EJBLocalHome {
  PerformanceCheckEJBLocal create() throws CreateException;
}
