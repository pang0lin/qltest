package com.js.oa.security.ip.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface IPEJBLocalHome extends EJBLocalHome {
  IPEJBLocal create() throws CreateException;
}
