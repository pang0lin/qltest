package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkFlowEJBLocalHome extends EJBLocalHome {
  WorkFlowEJBLocal create() throws CreateException;
}
