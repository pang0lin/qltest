package com.js.oa.scheme.workreport.bean;

import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface WorkReportTemplateEJBLocal extends EJBLocalObject {
  void delBatch(String paramString) throws Exception;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, Long paramLong) throws Exception;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Long paramLong) throws Exception;
  
  Map load(String paramString, Long paramLong) throws Exception;
  
  Boolean hasWorkReportTemplate(String paramString1, String paramString2, String paramString3, Long paramLong) throws Exception;
}
