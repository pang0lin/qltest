package com.js.oa.personalwork.setup.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface OptionSetEJBLocalHome extends EJBLocalHome {
  OptionSetEJBLocal create() throws CreateException;
}
