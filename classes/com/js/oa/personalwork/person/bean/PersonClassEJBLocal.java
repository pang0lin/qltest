package com.js.oa.personalwork.person.bean;

import com.js.oa.personalwork.person.po.PersonClassPO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface PersonClassEJBLocal extends EJBLocalObject {
  Boolean hasSameClassName(Long paramLong, String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  void delBatch(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void delAll(String paramString1, String paramString2) throws Exception;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws Exception;
  
  Integer update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  PersonClassPO load(String paramString) throws Exception;
}
