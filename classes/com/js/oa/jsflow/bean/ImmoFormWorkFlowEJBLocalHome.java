package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ImmoFormWorkFlowEJBLocalHome extends EJBLocalHome {
  ImmoFormWorkFlowEJBLocal create() throws CreateException;
}
