package com.js.oa.scheme.workreport.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkReportLeaderEJBLocalHome extends EJBLocalHome {
  WorkReportLeaderEJBLocal create() throws CreateException;
}
