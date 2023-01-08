package com.js.oa.module.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ModuleMenuAllEJBHome extends EJBHome {
  ModuleMenuAllEJB create() throws CreateException, RemoteException;
}
