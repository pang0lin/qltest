package com.js.oa.audit.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface OrganizationEJBLocalHome extends EJBLocalHome {
  OrganizationEJBLocal create() throws CreateException;
}
