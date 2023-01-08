package com.js.system.bean.groupmanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface GroupEJBHome extends EJBHome {
  GroupEJB create() throws CreateException, RemoteException;
}
