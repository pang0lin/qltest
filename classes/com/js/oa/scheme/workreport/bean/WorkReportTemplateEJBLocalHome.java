package com.js.oa.scheme.workreport.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkReportTemplateEJBLocalHome extends EJBLocalHome {
  WorkReportTemplateEJBLocal create() throws CreateException;
}
