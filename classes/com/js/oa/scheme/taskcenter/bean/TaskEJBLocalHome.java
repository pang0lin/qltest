package com.js.oa.scheme.taskcenter.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface TaskEJBLocalHome extends EJBLocalHome {
  TaskEJBLocal create() throws CreateException;
}
