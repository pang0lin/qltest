package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface NewDutyEJBLocalHome extends EJBLocalHome {
  NewDutyEJBLocal create() throws CreateException;
}
