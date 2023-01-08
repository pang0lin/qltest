package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkReportEJBHome extends EJBHome {
  WorkReportEJB create() throws CreateException, RemoteException;
}
