package com.js.oa.bbs.bean;

import com.js.oa.bbs.po.ForumPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface ForumEJB extends EJBObject {
  Vector list(String paramString1, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14) throws Exception, RemoteException;
  
  String see(Long paramLong) throws Exception, RemoteException;
  
  String add(ForumPO paramForumPO, String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Vector followList(String paramString1, Integer paramInteger, String paramString2) throws Exception, RemoteException;
  
  String getForumTitle(String paramString) throws Exception, RemoteException;
  
  String delForum(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String soulForum(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String setTop(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String move(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List userlist(String paramString) throws Exception, RemoteException;
  
  Vector noteBook(String paramString) throws Exception, RemoteException;
  
  Map getSingle(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer update(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String classIdQuery(String paramString) throws Exception, RemoteException;
  
  List loadContent(List paramList) throws Exception, RemoteException;
  
  String goExamin(String paramString) throws Exception, RemoteException;
  
  String setAuth(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getForumClassPODetails(String paramString) throws Exception, RemoteException;
  
  List getForumPODetails(String paramString) throws Exception, RemoteException;
}
