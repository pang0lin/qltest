package com.js.oa.personalwork.paper.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface NotePaperEJBHome extends EJBHome {
  NotePaperEJB create() throws CreateException, RemoteException;
}
