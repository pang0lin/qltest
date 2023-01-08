package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EltEJBHome extends EJBHome {
  EltEJB create() throws CreateException, RemoteException;
}
