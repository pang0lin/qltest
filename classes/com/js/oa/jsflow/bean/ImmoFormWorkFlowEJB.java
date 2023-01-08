package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBObject;

public interface ImmoFormWorkFlowEJB extends EJBObject {
  String[] getCommPO(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Map getComment(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getImmoFormRealName(String paramString) throws RemoteException, Exception;
}
