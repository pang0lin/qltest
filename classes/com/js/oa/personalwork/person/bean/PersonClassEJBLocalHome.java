package com.js.oa.personalwork.person.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PersonClassEJBLocalHome extends EJBLocalHome {
  PersonClassEJBLocal create() throws CreateException;
}
