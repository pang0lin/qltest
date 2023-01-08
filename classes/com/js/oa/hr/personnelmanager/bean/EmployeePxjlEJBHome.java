package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmployeePxjlEJBHome extends EJBHome {
  EmployeePxjlEJB create() throws CreateException, RemoteException;
}
