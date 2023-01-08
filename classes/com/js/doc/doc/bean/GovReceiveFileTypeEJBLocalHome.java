package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GovReceiveFileTypeEJBLocalHome extends EJBLocalHome {
  GovReceiveFileTypeEJBLocal create() throws CreateException;
}
