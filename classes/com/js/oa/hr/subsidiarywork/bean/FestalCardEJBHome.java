package com.js.oa.hr.subsidiarywork.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface FestalCardEJBHome extends EJBHome {
  FestalCardEJB create() throws CreateException, RemoteException;
}
