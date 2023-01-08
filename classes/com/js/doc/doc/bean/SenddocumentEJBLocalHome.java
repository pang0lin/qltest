package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface SenddocumentEJBLocalHome extends EJBLocalHome {
  SenddocumentEJBLocal create() throws CreateException;
}
