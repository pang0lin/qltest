package com.js.oa.personalwork.netaddress.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface AddressEJBLocalHome extends EJBLocalHome {
  AddressEJBLocal create() throws CreateException;
}
