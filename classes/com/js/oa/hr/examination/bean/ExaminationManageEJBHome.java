package com.js.oa.hr.examination.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ExaminationManageEJBHome extends EJBHome {
  ExaminationManageEJB create() throws CreateException, RemoteException;
}
