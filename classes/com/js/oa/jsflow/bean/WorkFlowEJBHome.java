package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkFlowEJBHome extends EJBHome {
  WorkFlowEJB create() throws CreateException, RemoteException;
}
