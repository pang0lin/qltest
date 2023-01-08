package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ReceiveFileEJBLocalHome extends EJBLocalHome {
  ReceiveFileEJBLocal create() throws CreateException;
}
