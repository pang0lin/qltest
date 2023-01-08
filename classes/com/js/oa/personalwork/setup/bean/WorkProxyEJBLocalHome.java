package com.js.oa.personalwork.setup.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkProxyEJBLocalHome extends EJBLocalHome {
  WorkProxyEJBLocal create() throws CreateException;
}
