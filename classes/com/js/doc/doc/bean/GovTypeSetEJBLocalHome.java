package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GovTypeSetEJBLocalHome extends EJBLocalHome {
  GovTypeSetEJBLocal create() throws CreateException;
}
