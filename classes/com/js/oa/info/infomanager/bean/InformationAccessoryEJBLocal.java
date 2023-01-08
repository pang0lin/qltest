package com.js.oa.info.infomanager.bean;

import com.js.oa.info.infomanager.po.InformationAccessoryPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface InformationAccessoryEJBLocal extends EJBLocalObject {
  List getAccessory(String paramString) throws Exception;
  
  void updateAccessory(String paramString, InformationAccessoryPO paramInformationAccessoryPO) throws Exception;
  
  String getAccessoryFile(String paramString) throws Exception;
  
  List getHistAccessory(String paramString) throws Exception;
  
  String getOneInfoPic(String paramString) throws Exception;
  
  void updateAccessory(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4) throws Exception;
}
