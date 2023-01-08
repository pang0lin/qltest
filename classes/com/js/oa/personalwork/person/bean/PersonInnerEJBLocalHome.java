package com.js.oa.personalwork.person.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PersonInnerEJBLocalHome extends EJBLocalHome {
  PersonInnerEJBLocal create() throws CreateException;
}
