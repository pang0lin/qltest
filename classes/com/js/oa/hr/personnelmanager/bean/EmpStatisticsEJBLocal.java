package com.js.oa.hr.personnelmanager.bean;

import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface EmpStatisticsEJBLocal extends EJBLocalObject {
  Map listEmpChange(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception;
  
  Map listEmpStruct(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception;
  
  Map listEmpCizhi(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception;
  
  Map listEmpZhuanzheng(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception;
}
