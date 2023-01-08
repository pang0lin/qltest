package com.js.system.bean.rolemanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface RoleEJBLocalHome extends EJBLocalHome {
  RoleEJBLocal create() throws CreateException;
}
