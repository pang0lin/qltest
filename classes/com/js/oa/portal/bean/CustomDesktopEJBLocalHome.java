package com.js.oa.portal.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface CustomDesktopEJBLocalHome extends EJBLocalHome {
  CustomDesktopEJBLocal create() throws CreateException;
}
