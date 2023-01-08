package com.js.doc.doc.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface GovTopicWordEJBHome extends EJBHome {
  GovTopicWordEJB create() throws CreateException, RemoteException;
}
