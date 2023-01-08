package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ReportFormsEJB extends EJBObject {
  List getReportForms(String[] paramArrayOfString, String paramString) throws Exception, RemoteException;
}
