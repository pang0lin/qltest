package com.js.oa.audit.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface OrganizationEJBHome extends EJBHome {
  OrganizationEJB create() throws CreateException, RemoteException;
}
