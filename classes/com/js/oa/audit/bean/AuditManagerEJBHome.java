package com.js.oa.audit.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AuditManagerEJBHome extends EJBHome {
  AuditManagerEJB create() throws CreateException, RemoteException;
}
