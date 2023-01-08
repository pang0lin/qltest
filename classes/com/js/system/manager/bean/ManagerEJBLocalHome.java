package com.js.system.manager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ManagerEJBLocalHome extends EJBLocalHome {
  ManagerEJBLocal create() throws CreateException;
}
