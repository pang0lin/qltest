package com.js.oa.security.log.bean;

import java.util.Date;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface LogEJBLocal extends EJBLocalObject {
  Boolean log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Date paramDate1, Date paramDate2, String paramString6, String paramString7, String paramString8, String paramString9) throws Exception;
  
  List export(String paramString) throws Exception;
  
  List moduleList(String paramString) throws Exception;
  
  void deleteLog(String paramString) throws Exception;
}
