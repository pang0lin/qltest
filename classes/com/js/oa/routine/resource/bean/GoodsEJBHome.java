package com.js.oa.routine.resource.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface GoodsEJBHome extends EJBHome {
  GoodsEJB create() throws CreateException, RemoteException;
}
