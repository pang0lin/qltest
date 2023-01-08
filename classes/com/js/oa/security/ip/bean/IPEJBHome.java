package com.js.oa.security.ip.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface IPEJBHome extends EJBHome {
  IPEJB create() throws CreateException, RemoteException;
}
