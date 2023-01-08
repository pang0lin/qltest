package com.js.oa.netdisk.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface NetdiskEJBHome extends EJBHome {
  NetdiskEJB create() throws CreateException, RemoteException;
}
