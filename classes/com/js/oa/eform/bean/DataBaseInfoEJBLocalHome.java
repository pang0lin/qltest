package com.js.oa.eform.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface DataBaseInfoEJBLocalHome extends EJBLocalHome {
  DataBaseInfoEJBLocal create() throws CreateException;
}
