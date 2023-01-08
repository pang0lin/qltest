package com.js.oa.bbs.bean;

import com.js.oa.bbs.po.ForumPO;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBLocalObject;

public interface ForumEJBLocal extends EJBLocalObject {
  Vector list(String paramString1, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14) throws Exception;
  
  String see(Long paramLong) throws Exception;
  
  String add(ForumPO paramForumPO, String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4) throws Exception;
  
  Vector followList(String paramString1, Integer paramInteger, String paramString2) throws Exception;
  
  String getForumTitle(String paramString) throws Exception;
  
  String delForum(String paramString1, String paramString2) throws Exception;
  
  String soulForum(String paramString1, String paramString2) throws Exception;
  
  String setTop(String paramString1, String paramString2) throws Exception;
  
  String move(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List userlist(String paramString) throws Exception;
  
  Vector noteBook(String paramString) throws Exception;
  
  Map getSingle(String paramString1, String paramString2) throws Exception;
  
  Integer update(String[] paramArrayOfString) throws Exception;
  
  String classIdQuery(String paramString) throws Exception;
  
  List loadContent(List paramList) throws Exception;
  
  String goExamin(String paramString) throws Exception;
  
  String setAuth(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getForumPODetails(String paramString) throws Exception;
  
  List getForumClassPODetails(String paramString) throws Exception;
}
