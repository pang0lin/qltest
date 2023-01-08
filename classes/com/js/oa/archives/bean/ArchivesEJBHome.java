package com.js.oa.archives.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ArchivesEJBHome extends EJBHome {
  ArchivesEJB create() throws CreateException, RemoteException;
}
