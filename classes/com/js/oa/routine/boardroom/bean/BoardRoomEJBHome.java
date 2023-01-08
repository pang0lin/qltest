package com.js.oa.routine.boardroom.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface BoardRoomEJBHome extends EJBHome {
  BoardRoomEJB create() throws CreateException, RemoteException;
}
