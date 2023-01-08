package com.js.oa.routine.resource.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ReportFormsEJBLocalHome extends EJBLocalHome {
  ReportFormsEJBLocal create() throws CreateException;
}
