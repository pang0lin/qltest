package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface GovSendFileCheckWithWorkFlowEJBHome extends EJBHome {
  GovSendFileCheckWithWorkFlowEJB create() throws CreateException, RemoteException;
}
