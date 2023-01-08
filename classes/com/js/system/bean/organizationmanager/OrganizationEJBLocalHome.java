package com.js.system.bean.organizationmanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface OrganizationEJBLocalHome extends EJBLocalHome {
  OrganizationEJBLocal create() throws CreateException;
}
