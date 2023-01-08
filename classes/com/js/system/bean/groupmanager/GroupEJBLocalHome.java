package com.js.system.bean.groupmanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GroupEJBLocalHome extends EJBLocalHome {
  GroupEJBLocal create() throws CreateException;
}
