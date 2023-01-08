package com.js.oa.message.bean;

import com.js.oa.message.po.MsLimitPO;
import com.js.oa.message.po.MsModelPO;
import com.js.oa.message.po.MsOutMoPO;
import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface messageSetting extends EJBObject {
  boolean changeAllMembers(Map paramMap, String paramString1, String paramString2) throws Exception, HibernateException, RemoteException;
  
  MsOutMoPO[] changeFroMap(Map paramMap, String paramString) throws RemoteException;
  
  boolean whetherRepeaInMsLimit(String paramString) throws HibernateException, RemoteException;
  
  Boolean addLimitMount(MsLimitPO paramMsLimitPO) throws HibernateException, RemoteException;
  
  boolean changeLimitMount(MsLimitPO paramMsLimitPO) throws HibernateException, RemoteException;
  
  boolean deletLimitMounts(String paramString) throws HibernateException, RemoteException;
  
  boolean addModelSelf(MsModelPO paramMsModelPO) throws HibernateException, RemoteException;
  
  boolean changeModelExist(MsModelPO[] paramArrayOfMsModelPO, String paramString) throws HibernateException, RemoteException;
  
  boolean allNotExist() throws HibernateException, RemoteException;
  
  boolean judgePurviewMessage(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String getModleContents(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
}
