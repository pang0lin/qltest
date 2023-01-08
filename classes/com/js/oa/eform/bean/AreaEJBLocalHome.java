package com.js.oa.eform.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface AreaEJBLocalHome extends EJBLocalHome {
  AreaEJBLocal create() throws CreateException;
}
