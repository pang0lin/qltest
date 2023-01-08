package com.js.oa.scheme.taskcenter.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TaskEJBHome extends EJBHome {
  TaskEJB create() throws CreateException, RemoteException;
}
