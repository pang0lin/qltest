package com.js.oa.online.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface OnlinerEJBLocalHome extends EJBLocalHome {
  OnlinerEJBLocal create() throws CreateException;
}
