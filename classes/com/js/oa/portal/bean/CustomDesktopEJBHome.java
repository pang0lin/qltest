package com.js.oa.portal.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CustomDesktopEJBHome extends EJBHome {
  CustomDesktopEJB create() throws CreateException, RemoteException;
}
