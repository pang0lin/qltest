package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface SendFileEJBLocalHome extends EJBLocalHome {
  SendFileEJBLocal create() throws CreateException;
}
