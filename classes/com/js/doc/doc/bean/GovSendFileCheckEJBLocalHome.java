package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GovSendFileCheckEJBLocalHome extends EJBLocalHome {
  GovSendFileCheckEJBLocal create() throws CreateException;
}
