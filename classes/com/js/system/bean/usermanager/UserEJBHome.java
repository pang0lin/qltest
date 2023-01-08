package com.js.system.bean.usermanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface UserEJBHome extends EJBHome {
  UserEJB create() throws CreateException, RemoteException;
}
