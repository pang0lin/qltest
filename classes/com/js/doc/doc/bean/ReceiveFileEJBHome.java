package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ReceiveFileEJBHome extends EJBHome {
  ReceiveFileEJB create() throws CreateException, RemoteException;
}
