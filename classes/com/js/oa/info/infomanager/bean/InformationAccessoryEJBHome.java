package com.js.oa.info.infomanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface InformationAccessoryEJBHome extends EJBHome {
  InformationAccessoryEJB create() throws CreateException, RemoteException;
}
