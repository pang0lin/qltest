package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PerformanceCheckEJBHome extends EJBHome {
  PerformanceCheckEJB create() throws CreateException, RemoteException;
}
