package com.js.oa.personalwork.netaddress.bean;

import javax.ejb.EJBLocalObject;

public interface AddressClassEJBLocal extends EJBLocalObject {
  void delBatch(String paramString1, String paramString2) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  Boolean add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  Boolean update(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String load(String paramString) throws Exception;
}
