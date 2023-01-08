package com.js.system.bean.rssmanager;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface RssEJBLocalHome extends EJBLocalHome {
  RssEJBLocal create() throws CreateException;
}
