package com.js.oa.hr.subsidiarywork.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface BirthCardEJBLocalHome extends EJBLocalHome {
  BirthCardEJBLocal create() throws CreateException;
}
