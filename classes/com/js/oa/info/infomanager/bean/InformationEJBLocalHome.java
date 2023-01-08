package com.js.oa.info.infomanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface InformationEJBLocalHome extends EJBLocalHome {
  InformationEJBLocal create() throws CreateException;
}
