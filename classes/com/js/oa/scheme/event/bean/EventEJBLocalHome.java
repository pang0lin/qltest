package com.js.oa.scheme.event.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EventEJBLocalHome extends EJBLocalHome {
  EventEJBLocal create() throws CreateException;
}
