package com.js.oa.info.channelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface DepartmentPageEJBLocalHome extends EJBLocalHome {
  DepartmentPageEJBLocal create() throws CreateException;
}
