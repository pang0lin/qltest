package com.js.system.bean.logomanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LogoEJBLocalHome extends EJBLocalHome {
  LogoEJBLocal create() throws CreateException;
}
