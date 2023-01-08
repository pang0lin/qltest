package com.js.system.bean.securitymanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface SecurityManagerEJBLocalHome extends EJBLocalHome {
  SecurityManagerEJBLocal create() throws CreateException;
}
