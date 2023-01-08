package com.js.oa.tjgzw.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ReceiveFileGzwEJBLocalHome extends EJBLocalHome {
  ReceiveFileGzwEJBLocal create() throws CreateException;
}
