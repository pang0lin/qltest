package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SendFileEJBHome extends EJBHome {
  SendFileEJB create() throws CreateException, RemoteException;
}
