package com.js.oa.message.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MsAccountBookEJBHome extends EJBHome {
  MsAccountBookEJB create() throws CreateException, RemoteException;
}
