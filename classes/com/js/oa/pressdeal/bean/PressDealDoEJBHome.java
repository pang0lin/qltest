package com.js.oa.pressdeal.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PressDealDoEJBHome extends EJBHome {
  PressDealDoEJB create() throws CreateException, RemoteException;
}
