package com.js.oa.zky.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ZkyMangerEJBHome extends EJBHome {
  ZkyMangerEJB create() throws CreateException, RemoteException;
}
