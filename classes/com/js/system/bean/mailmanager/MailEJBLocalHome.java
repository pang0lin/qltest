package com.js.system.bean.mailmanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface MailEJBLocalHome extends EJBLocalHome {
  MailEJBLocal create() throws CreateException;
}
