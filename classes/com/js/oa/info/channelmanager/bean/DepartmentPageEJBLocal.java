package com.js.oa.info.channelmanager.bean;

import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface DepartmentPageEJBLocal extends EJBLocalObject {
  List getTopChannel(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getLeftChTree(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void ModiDepaStyle(String paramString1, String paramString2) throws Exception;
  
  List departmentDeskop(String paramString1, String paramString2) throws Exception;
  
  void setDepartFlag(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getNewAnno(String paramString1, String paramString2) throws Exception;
  
  List getPhotoInfo(String paramString1, String paramString2) throws Exception;
  
  void updateBanner(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getMostNewInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getOrgBanner(String paramString) throws Exception;
  
  Map departHomepage(String paramString1, String paramString2) throws Exception;
  
  List getCanVindicate(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Boolean deptVindicateInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getAllChannel(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getCanVindicate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
}
