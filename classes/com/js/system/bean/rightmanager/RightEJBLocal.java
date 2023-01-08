package com.js.system.bean.rightmanager;

import java.util.List;
import javax.ejb.EJBLocalObject;

public interface RightEJBLocal extends EJBLocalObject {
  List getRightType() throws Exception;
  
  List getRightIdAndName(String paramString) throws Exception;
  
  List getIdTypeName(String paramString1, String paramString2) throws Exception;
  
  List getRightInfo(String paramString) throws Exception;
  
  List getRoleId(String paramString) throws Exception;
  
  void updateRole(String paramString, String[] paramArrayOfString) throws Exception;
  
  List getUserRightScope(String paramString) throws Exception;
}
