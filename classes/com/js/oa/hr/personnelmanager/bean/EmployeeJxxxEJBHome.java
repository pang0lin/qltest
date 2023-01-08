package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmployeeJxxxEJBHome extends EJBHome {
  EmployeeJxxxEJB create() throws CreateException, RemoteException;
}
