package com.js.oa.logon.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LogonEJBHome extends EJBHome {
  LogonEJB create() throws CreateException, RemoteException;
}
