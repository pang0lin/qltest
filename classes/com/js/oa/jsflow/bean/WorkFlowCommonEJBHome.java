package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkFlowCommonEJBHome extends EJBHome {
  WorkFlowCommonEJB create() throws CreateException, RemoteException;
}
