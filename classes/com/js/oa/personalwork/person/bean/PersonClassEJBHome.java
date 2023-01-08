package com.js.oa.personalwork.person.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PersonClassEJBHome extends EJBHome {
  PersonClassEJB create() throws CreateException, RemoteException;
}
