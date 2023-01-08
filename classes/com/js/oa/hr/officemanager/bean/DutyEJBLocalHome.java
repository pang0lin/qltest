package com.js.oa.hr.officemanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface DutyEJBLocalHome extends EJBLocalHome {
  DutyEJBLocal create() throws CreateException;
}
