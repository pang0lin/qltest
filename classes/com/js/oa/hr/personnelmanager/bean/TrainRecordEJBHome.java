package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TrainRecordEJBHome extends EJBHome {
  TrainRecordEJB create() throws CreateException, RemoteException;
}
