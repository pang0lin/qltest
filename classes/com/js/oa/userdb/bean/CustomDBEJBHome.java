package com.js.oa.userdb.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CustomDBEJBHome extends EJBHome {
  CustomDBEJB create() throws CreateException, RemoteException;
}
