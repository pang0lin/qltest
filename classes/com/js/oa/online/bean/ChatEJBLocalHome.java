package com.js.oa.online.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ChatEJBLocalHome extends EJBLocalHome {
  ChatEJBLocalHome create() throws CreateException;
}
