package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DrawDeptEJBHome extends EJBHome {
  DrawDeptEJB create() throws CreateException, RemoteException;
}
