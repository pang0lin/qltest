package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkAddressEJBLocalHome extends EJBLocalHome {
  WorkAddressEJBLocal create() throws CreateException;
}
