package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TypeDefineEJBHome extends EJBHome {
  TypeDefineEJB create() throws CreateException, RemoteException;
}
