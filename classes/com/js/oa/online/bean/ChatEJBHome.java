package com.js.oa.online.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ChatEJBHome extends EJBHome {
  ChatEJBHome create() throws CreateException, RemoteException;
}
