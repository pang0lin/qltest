package com.js.oa.message.bean;

import com.js.oa.message.po.MsLimitPO;
import com.js.oa.message.po.MsModelPO;
import com.js.oa.message.po.MsOutMoPO;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface messageSettingLocal extends EJBLocalObject {
  boolean changeAllMembers(Map paramMap, String paramString1, String paramString2) throws Exception, HibernateException;
  
  MsOutMoPO[] changeFroMap(Map paramMap, String paramString);
  
  boolean whetherRepeaInMsLimit(String paramString) throws HibernateException;
  
  Boolean addLimitMount(MsLimitPO paramMsLimitPO) throws HibernateException;
  
  boolean changeLimitMount(MsLimitPO paramMsLimitPO) throws HibernateException;
  
  boolean deletLimitMounts(String paramString) throws HibernateException;
  
  boolean addModelSelf(MsModelPO paramMsModelPO) throws HibernateException;
  
  boolean changeModelExist(MsModelPO[] paramArrayOfMsModelPO, String paramString) throws HibernateException;
  
  boolean allNotExist() throws HibernateException;
  
  boolean judgePurviewMessage(String paramString1, String paramString2) throws HibernateException;
  
  String getModleContents(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException;
}
