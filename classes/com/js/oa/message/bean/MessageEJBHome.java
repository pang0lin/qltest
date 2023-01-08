package com.js.oa.message.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MessageEJBHome extends EJBHome {
  MessageEJB create() throws CreateException, RemoteException;
}
