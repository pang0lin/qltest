package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmployeeTrainhistoryEJBHome extends EJBHome {
  EmployeeTrainhistoryEJB create() throws CreateException, RemoteException;
}
