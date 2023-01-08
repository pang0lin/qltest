package com.js.oa.eform.bean;

import com.js.oa.eform.po.TEltPO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EltEJBLocal extends EJBLocalObject {
  Boolean save(TEltPO paramTEltPO, String paramString1, String paramString2) throws HibernateException;
  
  Boolean delete(String paramString) throws Exception;
}
