package com.js.system.bean.logomanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LogoEJBHome extends EJBHome {
  LogoEJB create() throws CreateException, RemoteException;
}
