package com.js.oa.scheme.worklog.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkLogEJBLocalHome extends EJBLocalHome {
  WorkLogEJBLocal create() throws CreateException;
}
