package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.ProviderPO;
import javax.ejb.EJBLocalObject;

public interface ProviderEJBLocal extends EJBLocalObject {
  Boolean save(ProviderPO paramProviderPO) throws Exception;
  
  ProviderPO getModifyProvider(String paramString) throws Exception;
  
  Boolean update(ProviderPO paramProviderPO, String paramString) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
}
