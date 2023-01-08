package com.js.oa.eform.bean;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface CustomFormEJB extends EJBObject {
  String getCode(String paramString) throws Exception, RemoteException;
  
  String getEditHTML(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String[] getField(String paramString) throws Exception, RemoteException;
  
  String[][] getPageField(String paramString) throws Exception, RemoteException;
  
  String[][] getFieldInfo(String paramString) throws Exception, RemoteException;
  
  String getHTML(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getTable(String paramString) throws Exception, RemoteException;
  
  String getValue(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String[][] setValue(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getQueryFormByTblId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getTableIDAndName(String paramString) throws Exception, RemoteException;
  
  String[][] loadDataBySQL(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getComputeFieldHTML(String paramString) throws Exception, RemoteException;
  
  String getAutoCode(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getCommentField(String paramString) throws Exception, RemoteException;
  
  String getFieldShowValue(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getRemindValue(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String pasteForm(String paramString1, String paramString2) throws Exception;
  
  String[][] getQueryMainFormByTblId(String paramString1, String paramString2) throws RemoteException;
  
  String getFieldShowValue(String paramString1, String paramString2, String paramString3) throws RemoteException;
  
  String getPageName(String[] paramArrayOfString) throws Exception;
  
  String getCurStep(String paramString) throws Exception;
  
  String[] getJSCode(String paramString) throws Exception;
}
