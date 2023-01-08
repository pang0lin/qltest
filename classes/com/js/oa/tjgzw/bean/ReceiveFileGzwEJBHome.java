package com.js.oa.tjgzw.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ReceiveFileGzwEJBHome extends EJBHome {
  ReceiveFileGzwEJB create() throws CreateException, RemoteException;
}
