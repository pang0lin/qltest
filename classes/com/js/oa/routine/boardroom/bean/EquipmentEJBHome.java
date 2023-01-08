package com.js.oa.routine.boardroom.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EquipmentEJBHome extends EJBHome {
  EquipmentEJB create() throws CreateException, RemoteException;
}
