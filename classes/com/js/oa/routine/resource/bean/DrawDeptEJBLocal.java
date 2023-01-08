package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.DrawDeptPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface DrawDeptEJBLocal extends EJBLocalObject {
  Boolean save(DrawDeptPO paramDrawDeptPO, String paramString) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  String[] getSingleDept(String paramString) throws Exception;
  
  Boolean update(DrawDeptPO paramDrawDeptPO, String paramString) throws Exception;
  
  String getVindicate(String paramString) throws Exception;
  
  List getDeptInStock(String paramString) throws Exception;
  
  List getUserManaDept(String paramString) throws Exception;
}
