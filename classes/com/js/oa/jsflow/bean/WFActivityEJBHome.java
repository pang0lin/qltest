package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WFActivityEJBHome extends EJBHome {
  WFActivityEJB create() throws CreateException, RemoteException;
}
