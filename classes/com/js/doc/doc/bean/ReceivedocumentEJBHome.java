package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ReceivedocumentEJBHome extends EJBHome {
  ReceivedocumentEJB create() throws CreateException, RemoteException;
}
