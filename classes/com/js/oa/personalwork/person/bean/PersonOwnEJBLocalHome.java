package com.js.oa.personalwork.person.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PersonOwnEJBLocalHome extends EJBLocalHome {
  PersonOwnEJBLocal create() throws CreateException;
}
