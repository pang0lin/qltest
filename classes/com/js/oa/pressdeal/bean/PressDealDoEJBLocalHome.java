package com.js.oa.pressdeal.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PressDealDoEJBLocalHome extends EJBLocalHome {
  PressDealDoEJBLocal create() throws CreateException;
}
