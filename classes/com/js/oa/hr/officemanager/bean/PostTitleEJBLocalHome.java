package com.js.oa.hr.officemanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PostTitleEJBLocalHome extends EJBLocalHome {
  PostTitleEJBLocal create() throws CreateException;
}
