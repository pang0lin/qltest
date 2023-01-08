package com.js.oa.hr.subsidiarywork.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface FestalCardEJBLocalHome extends EJBLocalHome {
  FestalCardEJBLocal create() throws CreateException;
}
