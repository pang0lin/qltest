package com.js.oa.logon.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LogonEJBLocalHome extends EJBLocalHome {
  LogonEJBLocal create() throws CreateException;
}
