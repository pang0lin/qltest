package com.js.oa.scheme.event.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EventEJBHome extends EJBHome {
  EventEJB create() throws CreateException, RemoteException;
}
