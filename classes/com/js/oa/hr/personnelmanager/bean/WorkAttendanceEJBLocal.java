package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.WorkAttendancePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface WorkAttendanceEJBLocal extends EJBLocalObject {
  void testMethod();
  
  Boolean save(WorkAttendancePO paramWorkAttendancePO) throws Exception;
  
  List stat(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  Object[] getSingle(String paramString) throws Exception;
  
  Boolean update(WorkAttendancePO paramWorkAttendancePO) throws Exception;
}
