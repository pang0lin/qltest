package com.js.oa.hr.examination.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ExaminationTypeEJBHome extends EJBHome {
  ExaminationTypeEJB create() throws CreateException, RemoteException;
}
