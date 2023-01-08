package com.js.oa.bbs.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ForumClassEJBLocalHome extends EJBLocalHome {
  ForumClassEJBLocal create() throws CreateException;
}
