package com.js.oa.routine.voiture.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface VoitureEJBHome extends EJBHome {
  VoitureEJB create() throws CreateException, RemoteException;
}
