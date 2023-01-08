package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WFPackageEJBLocalHome extends EJBLocalHome {
  WFPackageEJBLocal create() throws CreateException;
}
