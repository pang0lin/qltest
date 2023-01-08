package com.js.oa.personalwork.setup.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkProxyEJBHome extends EJBHome {
  WorkProxyEJB create() throws CreateException, RemoteException;
}
