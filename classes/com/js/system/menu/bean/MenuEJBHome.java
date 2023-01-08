package com.js.system.menu.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MenuEJBHome extends EJBHome {
  MenuEJB create() throws CreateException, RemoteException;
}
