package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SenddocumentEJBHome extends EJBHome {
  SenddocumentEJB create() throws CreateException, RemoteException;
}
