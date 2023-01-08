package com.js.oa.bbs.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ForumEJBHome extends EJBHome {
  ForumEJB create() throws CreateException, RemoteException;
}
