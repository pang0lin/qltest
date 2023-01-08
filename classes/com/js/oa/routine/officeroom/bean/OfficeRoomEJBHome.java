package com.js.oa.routine.officeroom.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface OfficeRoomEJBHome extends EJBHome {
  OfficeRoomEJB create() throws CreateException, RemoteException;
}
