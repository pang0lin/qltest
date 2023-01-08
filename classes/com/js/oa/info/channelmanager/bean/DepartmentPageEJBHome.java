package com.js.oa.info.channelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DepartmentPageEJBHome extends EJBHome {
  DepartmentPageEJB create() throws CreateException, RemoteException;
}
