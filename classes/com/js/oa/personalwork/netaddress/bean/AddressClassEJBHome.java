package com.js.oa.personalwork.netaddress.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AddressClassEJBHome extends EJBHome {
  AddressClassEJB create() throws CreateException, RemoteException;
}
