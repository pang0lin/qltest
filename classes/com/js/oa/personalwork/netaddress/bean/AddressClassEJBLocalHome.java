package com.js.oa.personalwork.netaddress.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface AddressClassEJBLocalHome extends EJBLocalHome {
  AddressClassEJBLocal create() throws CreateException;
}
