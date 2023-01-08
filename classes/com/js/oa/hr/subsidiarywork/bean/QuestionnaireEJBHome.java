package com.js.oa.hr.subsidiarywork.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface QuestionnaireEJBHome extends EJBHome {
  QuestionnaireEJB create() throws CreateException, RemoteException;
}
