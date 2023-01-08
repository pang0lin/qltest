package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkReportLeaderEJBHome extends EJBHome {
  WorkReportLeaderEJB create() throws CreateException, RemoteException;
}
