package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkFlowButtonEJBHome extends EJBHome {
  WorkFlowButtonEJB create() throws CreateException, RemoteException;
}
