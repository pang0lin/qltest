package com.js.oa.message.bean;

import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface MsAccountBookEJBLocal extends EJBLocalObject {
  String getMeAccountInfo(String paramString) throws Exception, HibernateException;
}
