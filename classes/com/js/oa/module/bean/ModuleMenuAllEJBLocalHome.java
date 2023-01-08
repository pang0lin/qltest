package com.js.oa.module.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ModuleMenuAllEJBLocalHome extends EJBLocalHome {
  ModuleMenuAllEJBLocal create() throws CreateException;
}
