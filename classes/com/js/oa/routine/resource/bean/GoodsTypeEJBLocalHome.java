package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GoodsTypeEJBLocalHome extends EJBLocalHome {
  GoodsTypeEJBLocal create() throws CreateException;
}
