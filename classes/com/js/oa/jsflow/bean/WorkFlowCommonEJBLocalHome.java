package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkFlowCommonEJBLocalHome extends EJBLocalHome {
  WorkFlowCommonEJBLocal create() throws CreateException;
}
