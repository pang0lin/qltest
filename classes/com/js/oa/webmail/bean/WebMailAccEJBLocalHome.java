package com.js.oa.webmail.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WebMailAccEJBLocalHome extends EJBLocalHome {
  WebMailAccEJBLocal create() throws CreateException;
}
