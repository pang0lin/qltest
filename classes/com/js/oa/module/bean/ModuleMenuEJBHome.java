package com.js.oa.module.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ModuleMenuEJBHome extends EJBHome {
  ModuleMenuEJB create() throws CreateException, RemoteException;
}
