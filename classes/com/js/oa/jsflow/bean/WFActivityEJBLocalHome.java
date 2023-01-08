package com.js.oa.jsflow.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WFActivityEJBLocalHome extends EJBLocalHome {
  WFActivityEJBLocal create() throws CreateException;
}
