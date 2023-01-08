package com.js.oa.eform.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface FormPageEJBLocalHome extends EJBLocalHome {
  FormPageEJBLocal create() throws CreateException;
}
