package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EmpSocialinsuranceEJBHome extends EJBHome {
  EmpSocialinsuranceEJB create() throws CreateException, RemoteException;
}
