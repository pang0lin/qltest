package com.js.system.manager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ManagerEJBHome extends EJBHome {
  ManagerEJB create() throws CreateException, RemoteException;
}
