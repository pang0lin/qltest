package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WFProcessEJBLocalHome extends EJBLocalHome {
  WFProcessEJBLocal create() throws CreateException;
}
