package com.js.oa.module.bean;

import com.js.oa.module.po.SystemMenuPO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface ModuleMenuAllEJBLocal extends EJBLocalObject {
  List getOriginalMenuSet(String paramString1, String paramString2) throws HibernateException, Exception;
  
  SystemMenuPO loadMneuSet(String paramString) throws HibernateException, Exception;
  
  boolean updateOriginalMenuSet(SystemMenuPO paramSystemMenuPO) throws HibernateException;
  
  boolean delBatchMenuSet(String paramString1, String paramString2) throws HibernateException;
  
  boolean delAllCustomizeMenuSet(String paramString) throws HibernateException;
  
  List getAllMenuSet(String paramString) throws HibernateException;
  
  Integer checkSubMenuExists(String paramString);
  
  List loadMneuSetByCode(String paramString1, String paramString2) throws HibernateException;
  
  Long saveOriginalMenuSet(SystemMenuPO paramSystemMenuPO) throws HibernateException, Exception;
}
