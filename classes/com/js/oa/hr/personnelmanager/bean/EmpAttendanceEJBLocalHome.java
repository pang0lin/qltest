package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpAttendanceEJBLocalHome extends EJBLocalHome {
  EmpAttendanceEJBLocal create() throws CreateException;
}
