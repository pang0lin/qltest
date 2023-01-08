package com.js.oa.message.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface MessageEJBLocalHome extends EJBLocalHome {
  MessageEJBLocal create() throws CreateException;
}
