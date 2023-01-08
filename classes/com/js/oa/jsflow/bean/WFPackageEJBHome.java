package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WFPackageEJBHome extends EJBHome {
  WFPackageEJB create() throws CreateException, RemoteException;
}
