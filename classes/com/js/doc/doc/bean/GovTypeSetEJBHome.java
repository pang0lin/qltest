package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface GovTypeSetEJBHome extends EJBHome {
  GovTypeSetEJB create() throws CreateException, RemoteException;
}
