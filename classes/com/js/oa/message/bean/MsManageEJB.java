package com.js.oa.message.bean;

import com.js.oa.message.po.MsManagePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface MsManageEJB extends EJBObject {
  List getMsManageList(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getUserNameByMsId(String paramString) throws Exception;
  
  MsManagePO loadMs(String paramString) throws Exception;
  
  List getMsManageInfoByMsId(String paramString) throws Exception;
  
  Boolean updateMsMangeGrant(MsManagePO paramMsManagePO) throws Exception;
  
  List getListByYourSQL(String paramString) throws Exception;
  
  String getListOwner(String paramString) throws Exception;
  
  List getListType(String paramString) throws Exception;
  
  String getListUsers(String paramString1, String paramString2) throws Exception;
  
  String getListTtable(String paramString) throws Exception;
}
