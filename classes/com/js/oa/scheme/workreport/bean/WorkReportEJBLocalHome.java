package com.js.oa.scheme.workreport.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkReportEJBLocalHome extends EJBLocalHome {
  WorkReportEJBLocal create() throws CreateException;
}
