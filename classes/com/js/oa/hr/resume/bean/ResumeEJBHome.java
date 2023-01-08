package com.js.oa.hr.resume.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ResumeEJBHome extends EJBHome {
  ResumeEJB create() throws CreateException, RemoteException;
}
