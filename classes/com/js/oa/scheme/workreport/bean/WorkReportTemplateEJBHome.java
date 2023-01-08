package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkReportTemplateEJBHome extends EJBHome {
  WorkReportTemplateEJB create() throws CreateException, RemoteException;
}
