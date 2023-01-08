package com.js.oa.routine.resource.bean;

import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ReportFormsEJBLocal extends EJBLocalObject {
  List getReportForms(String[] paramArrayOfString, String paramString) throws Exception;
}
