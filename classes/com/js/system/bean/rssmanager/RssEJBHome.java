package com.js.system.bean.rssmanager;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface RssEJBHome extends EJBHome {
  RssEJB create() throws CreateException, RemoteException;
}
