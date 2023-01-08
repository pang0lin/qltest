package com.js.oa.info.templatemanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TemplateEJBHome extends EJBHome {
  TemplateEJB create() throws CreateException, RemoteException;
}
