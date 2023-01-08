package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface IntoOutStockEJBHome extends EJBHome {
  IntoOutStockEJB create() throws CreateException, RemoteException;
}
