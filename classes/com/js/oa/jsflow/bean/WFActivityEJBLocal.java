package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFActivityPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface WFActivityEJBLocal extends EJBLocalObject {
  void remove(String paramString) throws Exception;
  
  void removeAll(String paramString) throws Exception;
  
  WFActivityPO getActivityInfo(String paramString) throws Exception;
  
  List getFromActivity(String paramString) throws Exception;
  
  List getActivity(String paramString) throws Exception;
  
  List getToActivity(String paramString) throws Exception;
  
  Long setStartActivity(String paramString1, String paramString2) throws Exception;
  
  Long setActivity(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString1, String[] paramArrayOfString5, String paramString2) throws Exception;
  
  Long setActivity(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString1, String[] paramArrayOfString5, String paramString2, String paramString3) throws Exception;
  
  Boolean hasPrintRight(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Long addWithoutCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString) throws Exception;
  
  void updateWithoutCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String paramString1, String paramString2) throws Exception;
  
  void updateWithoutCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString1, String paramString2) throws Exception;
  
  void updateWithCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String paramString1, String paramString2) throws Exception;
  
  void updateWithCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String paramString1, String paramString2) throws Exception;
  
  void addWithCondition(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String paramString) throws Exception;
  
  Long setSingelRelation(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
}
