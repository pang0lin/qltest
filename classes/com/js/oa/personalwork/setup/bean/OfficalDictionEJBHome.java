package com.js.oa.personalwork.setup.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface OfficalDictionEJBHome extends EJBHome {
  OfficalDictionEJB create() throws CreateException, RemoteException;
}
