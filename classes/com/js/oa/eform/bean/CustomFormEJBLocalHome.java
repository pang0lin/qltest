package com.js.oa.eform.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface CustomFormEJBLocalHome extends EJBLocalHome {
  CustomFormEJBLocal create() throws CreateException;
}
