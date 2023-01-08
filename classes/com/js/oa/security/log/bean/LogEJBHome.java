package com.js.oa.security.log.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LogEJBHome extends EJBHome {
  LogEJB create() throws CreateException, RemoteException;
}
