package com.js.oa.message.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface messageSettingHome extends EJBHome {
  messageSetting create() throws CreateException, RemoteException;
}
