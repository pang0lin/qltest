package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFActivityPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface WFActivityEJB extends EJBObject {
  void remove(String paramString) throws Exception, RemoteException;
  
  void removeAll(String paramString) throws Exception, RemoteException;
  
  WFActivityPO getActivityInfo(String paramString) throws Exception, RemoteException;
  
  List getFromActivity(String paramString) throws Exception, RemoteException;
  
  List getActivity(String paramString) throws Exception, RemoteException;
  
  List getToActivity(String paramString) throws Exception, RemoteException;
  
  Long setStartActivity(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Long setActivity(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString1, String[] paramArrayOfString5, String paramString2) throws Exception, RemoteException;
  
  Long setActivity(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString1, String[] paramArrayOfString5, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean hasPrintRight(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Long addWithoutCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString) throws Exception, RemoteException;
  
  void updateWithoutCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString1, String paramString2) throws Exception, RemoteException;
  
  void updateWithoutCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String paramString1, String paramString2) throws Exception, RemoteException;
  
  void updateWithCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String paramString1, String paramString2) throws Exception, RemoteException;
  
  void updateWithCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String paramString1, String paramString2) throws Exception, RemoteException;
  
  void addWithCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String paramString) throws Exception, RemoteException;
  
  Long setSingelRelation(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
}
