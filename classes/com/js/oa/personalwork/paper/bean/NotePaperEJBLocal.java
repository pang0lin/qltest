package com.js.oa.personalwork.paper.bean;

import com.js.oa.personalwork.paper.po.NotePaperPO;
import javax.ejb.EJBLocalObject;

public interface NotePaperEJBLocal extends EJBLocalObject {
  String getColor(Long paramLong) throws Exception;
  
  void delBatch(String paramString1, String paramString2) throws Exception;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  void update(String paramString1, String paramString2, String paramString3) throws Exception;
  
  NotePaperPO load(String paramString) throws Exception;
}
