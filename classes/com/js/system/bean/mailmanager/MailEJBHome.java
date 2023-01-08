package com.js.system.bean.mailmanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MailEJBHome extends EJBHome {
  MailEJB create() throws CreateException, RemoteException;
}
