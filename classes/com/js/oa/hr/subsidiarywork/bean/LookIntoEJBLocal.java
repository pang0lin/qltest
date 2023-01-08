package com.js.oa.hr.subsidiarywork.bean;

import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface LookIntoEJBLocal extends EJBLocalObject {
  void delBatch(String paramString) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  void add(Object[] paramArrayOfObject, String paramString1, String paramString2) throws Exception;
  
  Map load(String paramString) throws Exception;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString8, String paramString9) throws Exception;
  
  String voteAdd(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List voteList(String paramString1, String paramString2) throws Exception;
}
