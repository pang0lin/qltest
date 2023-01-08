package com.js.oa.security.log.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LogEJBLocalHome extends EJBLocalHome {
  LogEJBLocal create() throws CreateException;
}
