package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AreaEJBHome extends EJBHome {
  AreaEJB create() throws CreateException, RemoteException;
}
