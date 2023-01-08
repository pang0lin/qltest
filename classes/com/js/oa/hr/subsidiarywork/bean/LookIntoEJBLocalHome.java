package com.js.oa.hr.subsidiarywork.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LookIntoEJBLocalHome extends EJBLocalHome {
  LookIntoEJBLocal create() throws CreateException;
}
