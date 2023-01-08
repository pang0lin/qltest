package com.js.oa.routine.voiture.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface VoitureReportEJBHome extends EJBHome {
  VoitureReportEJB create() throws CreateException, RemoteException;
}
