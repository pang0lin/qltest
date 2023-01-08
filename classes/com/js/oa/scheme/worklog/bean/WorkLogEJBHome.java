package com.js.oa.scheme.worklog.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkLogEJBHome extends EJBHome {
  WorkLogEJB create() throws CreateException, RemoteException;
}
