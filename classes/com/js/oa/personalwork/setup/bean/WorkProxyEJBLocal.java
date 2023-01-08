package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.WorkProxyPO;
import javax.ejb.EJBLocalObject;

public interface WorkProxyEJBLocal extends EJBLocalObject {
  void delBatch(String paramString1, String paramString2) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  void add(WorkProxyPO paramWorkProxyPO, String paramString) throws Exception;
  
  WorkProxyPO load(String paramString) throws Exception;
  
  void update(WorkProxyPO paramWorkProxyPO, String paramString1, String paramString2) throws Exception;
  
  String getAvailableProxy(String paramString) throws Exception;
  
  String[] getAvailableUsers(String[] paramArrayOfString) throws Exception;
  
  void setUnavailableProxy() throws Exception;
}
