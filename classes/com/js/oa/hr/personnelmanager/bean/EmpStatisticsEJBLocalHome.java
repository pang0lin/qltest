package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EmpStatisticsEJBLocalHome extends EJBLocalHome {
  EmpStatisticsEJBLocal create() throws CreateException;
}
