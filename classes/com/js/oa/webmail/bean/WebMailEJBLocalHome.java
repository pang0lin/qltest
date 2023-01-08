package com.js.oa.webmail.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WebMailEJBLocalHome extends EJBLocalHome {
  WebMailEJBLocal create() throws CreateException;
}
