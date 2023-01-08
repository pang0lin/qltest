package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmployeeJcxxEJBHome extends EJBHome {
  EmployeeJcxxEJB create() throws CreateException, RemoteException;
}
