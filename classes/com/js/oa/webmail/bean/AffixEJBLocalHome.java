package com.js.oa.webmail.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface AffixEJBLocalHome extends EJBLocalHome {
  AffixEJBLocal create() throws CreateException;
}
