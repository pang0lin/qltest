package com.js.oa.routine.voiture.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface VoitureSecondEJBHome extends EJBHome {
  VoitureSecondEJB create() throws CreateException, RemoteException;
}
