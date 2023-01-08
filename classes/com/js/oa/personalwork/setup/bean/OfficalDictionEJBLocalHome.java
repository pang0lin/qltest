package com.js.oa.personalwork.setup.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface OfficalDictionEJBLocalHome extends EJBLocalHome {
  OfficalDictionEJBLocal create() throws CreateException;
}
