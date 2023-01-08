package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkreportLeaderProductEJBHome extends EJBHome {
  WorkreportLeaderProductEJB create() throws CreateException, RemoteException;
}
