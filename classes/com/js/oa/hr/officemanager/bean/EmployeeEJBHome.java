package com.js.oa.hr.officemanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmployeeEJBHome extends EJBHome {
  EmployeeEJB create() throws CreateException, RemoteException;
}
