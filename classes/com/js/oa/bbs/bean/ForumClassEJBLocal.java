package com.js.oa.bbs.bean;

import com.js.oa.bbs.po.ForumClassPO;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJBLocalObject;

public interface ForumClassEJBLocal extends EJBLocalObject {
  List listMenu(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getClassIdString(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getClassIdString(String paramString1, String paramString2) throws Exception;
  
  Vector list(Integer paramInteger, String paramString1, String paramString2) throws Exception;
  
  List see(String paramString1, String paramString2) throws Exception;
  
  String del(String paramString) throws Exception;
  
  ForumClassPO load(String paramString) throws Exception;
  
  String update(String paramString1, String paramString2, String paramString3, Long paramLong, String paramString4, String paramString5, Short paramShort, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, Integer paramInteger) throws Exception;
  
  String update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Long paramLong, String paramString7, String paramString8, Short paramShort, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, Integer paramInteger, String paramString18) throws Exception;
  
  String add(ForumClassPO paramForumClassPO, String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Boolean isClassOwner(Long paramLong1, Long paramLong2) throws Exception;
  
  String selectSimpleName(String paramString1, String paramString2) throws Exception;
}
