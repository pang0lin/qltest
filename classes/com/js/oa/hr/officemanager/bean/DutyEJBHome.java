package com.js.oa.hr.officemanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DutyEJBHome extends EJBHome {
  DutyEJB create() throws CreateException, RemoteException;
}
