package com.js.oa.webmail.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WebMailAccEJBHome extends EJBHome {
  WebMailAccEJB create() throws CreateException, RemoteException;
}
