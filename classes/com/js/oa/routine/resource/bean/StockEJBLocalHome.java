package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface StockEJBLocalHome extends EJBLocalHome {
  StockEJBLocal create() throws CreateException;
}
