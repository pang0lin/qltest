package com.js.oa.scheme.workreport.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkReportProductEJBLocalHome extends EJBLocalHome {
  WorkReportProductEJBLocal create() throws CreateException;
}
