package com.js.oa.message.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface MsAccountBookEJBLocalHome extends EJBLocalHome {
  MsAccountBookEJBLocal create() throws CreateException;
}
