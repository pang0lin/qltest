package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DataBaseInfoEJBHome extends EJBHome {
  DataBaseInfoEJB create() throws CreateException, RemoteException;
}
