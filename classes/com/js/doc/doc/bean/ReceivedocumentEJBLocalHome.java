package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ReceivedocumentEJBLocalHome extends EJBLocalHome {
  ReceivedocumentEJBLocal create() throws CreateException;
}
