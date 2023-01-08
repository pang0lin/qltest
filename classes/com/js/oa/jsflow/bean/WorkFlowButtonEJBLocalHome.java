package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkFlowButtonEJBLocalHome extends EJBLocalHome {
  WorkFlowButtonEJBLocal create() throws CreateException;
}
