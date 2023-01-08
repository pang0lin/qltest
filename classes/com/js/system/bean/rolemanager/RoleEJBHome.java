package com.js.system.bean.rolemanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface RoleEJBHome extends EJBHome {
  RoleEJB create() throws CreateException, RemoteException;
}
