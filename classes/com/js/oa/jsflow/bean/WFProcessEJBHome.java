package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WFProcessEJBHome extends EJBHome {
  WFProcessEJB create() throws CreateException, RemoteException;
}
