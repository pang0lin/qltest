package com.js.system.bean.groupmanager;

import com.js.system.vo.groupmanager.GroupVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface GroupEJBLocal extends EJBLocalObject {
  Integer add(GroupVO paramGroupVO, String[] paramArrayOfString) throws Exception;
  
  String del(String[] paramArrayOfString) throws Exception;
  
  void delAll() throws Exception;
  
  List selectSingle(String paramString) throws Exception;
  
  List select() throws Exception;
  
  List selectGroupUser(String paramString) throws Exception;
  
  List selectGroupUserEmail(String paramString) throws Exception, RemoteException;
  
  Integer update(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11) throws Exception;
  
  List selectPersonUser(String paramString) throws Exception;
  
  List checkGroupByName(String paramString1, String paramString2) throws Exception;
  
  Integer saveAsGroup(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
}
