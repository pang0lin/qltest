package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface IntoOutStockEJBLocalHome extends EJBLocalHome {
  IntoOutStockEJBLocal create() throws CreateException;
}
