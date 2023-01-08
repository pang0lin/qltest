package com.js.system.bean.securitymanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SecurityManagerEJBHome extends EJBHome {
  SecurityManagerEJB create() throws CreateException, RemoteException;
}
