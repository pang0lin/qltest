package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ProviderEJBLocalHome extends EJBLocalHome {
  ProviderEJBLocal create() throws CreateException;
}
