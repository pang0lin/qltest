package com.js.system.bean.usermanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface UserEJBLocalHome extends EJBLocalHome {
  UserEJBLocal create() throws CreateException;
}
