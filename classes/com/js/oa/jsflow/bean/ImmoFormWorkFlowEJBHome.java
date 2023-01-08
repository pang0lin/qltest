package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ImmoFormWorkFlowEJBHome extends EJBHome {
  ImmoFormWorkFlowEJB create() throws CreateException, RemoteException;
}
