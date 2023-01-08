package com.js.system.bean.rightmanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface RightEJBLocalHome extends EJBLocalHome {
  RightEJBLocal create() throws CreateException;
}
