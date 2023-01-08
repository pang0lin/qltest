package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.FestivalSetPO;
import javax.ejb.EJBLocalObject;

public interface FestalCardEJBLocal extends EJBLocalObject {
  void delBatch(String paramString) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  void add(FestivalSetPO paramFestivalSetPO, String paramString1, String paramString2) throws Exception;
  
  FestivalSetPO load(String paramString) throws Exception;
  
  void update(FestivalSetPO paramFestivalSetPO, String paramString1, String paramString2) throws Exception;
}
