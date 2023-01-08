package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PersonalKindEJBLocalHome extends EJBLocalHome {
  PersonalKindEJBLocal create() throws CreateException;
}
