package com.js.oa.hr.examination.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ExaminationSelfTestEJBHome extends EJBHome {
  ExaminationSelfTestEJB create() throws CreateException, RemoteException;
}
