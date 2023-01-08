package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface HprecordEJBLocalHome extends EJBLocalHome {
  HprecordEJBLocal create() throws CreateException;
}
