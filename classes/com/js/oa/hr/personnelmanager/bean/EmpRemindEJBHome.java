package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmpRemindEJBHome extends EJBHome {
  EmpRemindEJB create() throws CreateException, RemoteException;
}
