package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface DrawDeptEJBLocalHome extends EJBLocalHome {
  DrawDeptEJBLocal create() throws CreateException;
}
