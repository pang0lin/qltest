package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpSocialinsuranceEJBLocalHome extends EJBLocalHome {
  EmpSocialinsuranceEJBLocal create() throws CreateException;
}
