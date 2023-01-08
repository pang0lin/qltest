package com.js.oa.module.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ModuleMenuEJBLocalHome extends EJBLocalHome {
  ModuleMenuEJBLocal create() throws CreateException;
}
