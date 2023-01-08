package com.js.oa.scheme.workreport.bean;

import java.util.List;
import java.util.Vector;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface WorkreportLeaderProductEJBLocal extends EJBLocalObject {
  Vector load(String paramString1, String paramString2, String paramString3, Long paramLong) throws Exception;
  
  List loadContent(List paramList) throws Exception;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Long paramLong) throws Exception;
  
  void delBatch(String paramString) throws Exception;
  
  List getTopnNotReadReport(String paramString1, String paramString2, Integer paramInteger) throws HibernateException;
}
