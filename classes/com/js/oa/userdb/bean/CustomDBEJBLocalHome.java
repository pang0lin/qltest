package com.js.oa.userdb.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface CustomDBEJBLocalHome extends EJBLocalHome {
  CustomDBEJBLocal create() throws CreateException;
}
