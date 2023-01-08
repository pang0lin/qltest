package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkAttendanceEJBLocalHome extends EJBLocalHome {
  WorkAttendanceEJBLocal create() throws CreateException;
}
