package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GoodsEJBLocalHome extends EJBLocalHome {
  GoodsEJBLocal create() throws CreateException;
}
