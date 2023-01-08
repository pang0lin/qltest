package com.js.oa.webmail.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AffixEJBHome extends EJBHome {
  AffixEJB create() throws CreateException, RemoteException;
}
