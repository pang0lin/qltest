package com.js.oa.info.infomanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface InformationAccessoryEJBLocalHome extends EJBLocalHome {
  InformationAccessoryEJBLocal create() throws CreateException;
}
