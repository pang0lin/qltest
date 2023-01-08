package com.js.oa.personalwork.person.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBLocalObject;

public interface PersonInnerEJBLocal extends EJBLocalObject {
  Vector list(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws Exception;
  
  Map load(String paramString) throws Exception;
  
  void update(EmployeeVO paramEmployeeVO, String paramString) throws Exception;
  
  List see() throws Exception;
  
  List city(String paramString) throws Exception;
  
  List county(String paramString1, String paramString2) throws Exception;
  
  List setValidOrgs(Long paramLong) throws Exception;
}
