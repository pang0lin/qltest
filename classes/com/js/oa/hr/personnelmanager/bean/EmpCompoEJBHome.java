package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmpCompoEJBHome extends EJBHome {
  EmpCompoEJB create() throws CreateException, RemoteException;
}
