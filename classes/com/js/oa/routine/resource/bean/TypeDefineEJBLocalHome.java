package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface TypeDefineEJBLocalHome extends EJBLocalHome {
  TypeDefineEJBLocal create() throws CreateException;
}
