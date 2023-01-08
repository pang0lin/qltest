package com.js.oa.hr.subsidiarywork.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface BirthCardEJBHome extends EJBHome {
  BirthCardEJB create() throws CreateException, RemoteException;
}
