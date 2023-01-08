package com.js.oa.scheme.workreport.bean;

import com.js.oa.scheme.workreport.po.WorkReportTransmitPO;
import com.js.system.vo.usermanager.EmployeeVO;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBLocalObject;

public interface WorkReportEJBLocal extends EJBLocalObject {
  Vector list(String paramString1, String paramString2) throws Exception;
  
  Map load(String paramString) throws Exception;
  
  Boolean add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  List see(String paramString, Long paramLong) throws Exception;
  
  String template(String paramString, Long paramLong) throws Exception;
  
  EmployeeVO getEmployeeByID(Long paramLong) throws Exception;
  
  String getWorkReportContentByCourse(String paramString1, String paramString2) throws Exception;
  
  List getReportData(String paramString1, String paramString2, String paramString3, String paramString4, List paramList) throws Exception;
  
  Integer getUnderLingCount(String paramString) throws Exception;
  
  void deliverTO(String paramString1, String paramString2, WorkReportTransmitPO paramWorkReportTransmitPO) throws Exception;
  
  List initList(List paramList) throws Exception;
  
  List getSonsByName(String paramString) throws Exception;
}
