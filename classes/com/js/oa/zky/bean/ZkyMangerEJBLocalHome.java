package com.js.oa.zky.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ZkyMangerEJBLocalHome extends EJBLocalHome {
  ZkyMangerEJBLocal create() throws CreateException;
}
