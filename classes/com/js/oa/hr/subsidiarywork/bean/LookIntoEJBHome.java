package com.js.oa.hr.subsidiarywork.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LookIntoEJBHome extends EJBHome {
  LookIntoEJB create() throws CreateException, RemoteException;
}
