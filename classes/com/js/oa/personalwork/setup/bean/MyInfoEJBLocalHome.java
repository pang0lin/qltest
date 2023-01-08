package com.js.oa.personalwork.setup.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface MyInfoEJBLocalHome extends EJBLocalHome {
  MyInfoEJBLocal create() throws CreateException;
}
