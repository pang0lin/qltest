package com.js.system.menu.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface MenuEJBLocalHome extends EJBLocalHome {
  MenuEJBLocal create() throws CreateException;
}
