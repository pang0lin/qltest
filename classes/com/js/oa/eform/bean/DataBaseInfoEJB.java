package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface DataBaseInfoEJB extends EJBObject {
  String[][] getTableInfo(String paramString) throws RemoteException;
  
  String[][] getFieldInfo(String paramString) throws RemoteException;
}
