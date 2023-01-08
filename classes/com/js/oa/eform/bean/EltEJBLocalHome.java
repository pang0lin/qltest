package com.js.oa.eform.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EltEJBLocalHome extends EJBLocalHome {
  EltEJBLocal create() throws CreateException;
}
