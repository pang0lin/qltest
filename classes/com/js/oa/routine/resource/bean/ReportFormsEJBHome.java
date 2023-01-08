package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ReportFormsEJBHome extends EJBHome {
  ReportFormsEJB create() throws CreateException, RemoteException;
}
