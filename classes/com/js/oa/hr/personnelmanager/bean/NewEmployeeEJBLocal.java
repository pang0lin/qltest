package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface NewEmployeeEJBLocal extends EJBLocalObject {
  List selectSingle(Long paramLong) throws Exception;
  
  Integer add(EmployeeVO paramEmployeeVO, String paramString) throws Exception;
  
  List postTitle(String paramString) throws Exception;
  
  Integer update(EmployeeVO paramEmployeeVO, String paramString, Long paramLong) throws Exception;
  
  List getMaturityAlertSettings(String paramString1, String paramString2) throws Exception;
  
  Boolean saveMaturityAlertSettings(String paramString1, String[][] paramArrayOfString, String paramString2) throws Exception;
  
  String getMaturityAlertSettingsValue(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer getLogCountByUserId(String paramString) throws Exception;
  
  Integer getLogCountByOrgId(String paramString) throws Exception;
}
