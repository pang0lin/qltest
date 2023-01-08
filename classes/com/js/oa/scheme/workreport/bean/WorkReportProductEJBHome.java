package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkReportProductEJBHome extends EJBHome {
  WorkReportProductEJB create() throws CreateException, RemoteException;
}
