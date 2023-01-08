package com.js.oa.webmail.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WebMailEJBHome extends EJBHome {
  WebMailEJB create() throws CreateException, RemoteException;
}
