package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CustomFormEJBHome extends EJBHome {
  CustomFormEJB create() throws CreateException, RemoteException;
}
