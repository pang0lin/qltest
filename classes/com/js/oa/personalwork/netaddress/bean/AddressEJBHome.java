package com.js.oa.personalwork.netaddress.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AddressEJBHome extends EJBHome {
  AddressEJB create() throws CreateException, RemoteException;
}
