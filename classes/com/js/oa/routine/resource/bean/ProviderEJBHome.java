package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ProviderEJBHome extends EJBHome {
  ProviderEJB create() throws CreateException, RemoteException;
}
