package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface FormPageEJBHome extends EJBHome {
  FormPageEJB create() throws CreateException, RemoteException;
}
