package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface WorkAttendanceEJBHome extends EJBHome {
  WorkAttendanceEJB create() throws CreateException, RemoteException;
}
