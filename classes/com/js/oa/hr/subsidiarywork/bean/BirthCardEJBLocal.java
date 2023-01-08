package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.BirthdayWishPO;
import javax.ejb.EJBLocalObject;

public interface BirthCardEJBLocal extends EJBLocalObject {
  void delBatch(String paramString) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  void add(BirthdayWishPO paramBirthdayWishPO, String paramString1, String paramString2) throws Exception;
  
  BirthdayWishPO load(String paramString) throws Exception;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws Exception;
}
