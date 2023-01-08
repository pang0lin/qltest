package com.js.oa.message.bean;

import com.js.oa.message.po.MsManagePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface MsManageEJBLocal extends EJBLocalObject {
  List getMsManageList(String paramString1, String paramString2) throws Exception;
  
  String getUserNameByMsId(String paramString) throws Exception;
  
  MsManagePO loadMs(String paramString) throws Exception;
  
  List getMsManageInfoByMsId(String paramString) throws Exception;
  
  Boolean updateMsMangeGrant(MsManagePO paramMsManagePO) throws Exception;
  
  List getListByYourSQL(String paramString) throws Exception;
}
