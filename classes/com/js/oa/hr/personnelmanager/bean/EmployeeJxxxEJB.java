package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeJxxxEJB extends EJBObject {
  String[][] list(String paramString) throws Exception, RemoteException;
}
