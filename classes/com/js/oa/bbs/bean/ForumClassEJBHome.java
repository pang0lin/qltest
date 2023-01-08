package com.js.oa.bbs.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ForumClassEJBHome extends EJBHome {
  ForumClassEJB create() throws CreateException, RemoteException;
}
