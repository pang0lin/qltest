package com.js.oa.bbs.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ForumEJBLocalHome extends EJBLocalHome {
  ForumEJBLocal create() throws CreateException;
}
