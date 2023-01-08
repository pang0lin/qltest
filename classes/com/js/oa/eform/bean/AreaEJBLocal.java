package com.js.oa.eform.bean;

import com.js.oa.eform.po.TAreaPO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface AreaEJBLocal extends EJBLocalObject {
  Long save(TAreaPO paramTAreaPO, String paramString1, String paramString2) throws HibernateException;
  
  Boolean delete(String paramString) throws Exception;
}
