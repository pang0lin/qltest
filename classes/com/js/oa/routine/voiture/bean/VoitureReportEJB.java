package com.js.oa.routine.voiture.bean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface VoitureReportEJB extends EJBObject {
  List listReportForms(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
}
