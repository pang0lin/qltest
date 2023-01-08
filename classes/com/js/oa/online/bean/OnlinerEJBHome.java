package com.js.oa.online.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface OnlinerEJBHome extends EJBHome {
  OnlineEJB create() throws CreateException, RemoteException;
}
