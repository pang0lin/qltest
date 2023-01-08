package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PersonalKindEJBHome extends EJBHome {
  PersonalKindEJB create() throws CreateException, RemoteException;
}
