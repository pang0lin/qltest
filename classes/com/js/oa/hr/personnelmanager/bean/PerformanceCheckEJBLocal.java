package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.PerformanceCheckPO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface PerformanceCheckEJBLocal extends EJBLocalObject {
  Boolean save(PerformanceCheckPO paramPerformanceCheckPO) throws HibernateException;
  
  PerformanceCheckPO load(Long paramLong) throws HibernateException;
  
  Boolean modify(PerformanceCheckPO paramPerformanceCheckPO) throws HibernateException;
  
  Boolean delete(Long paramLong) throws HibernateException;
  
  Boolean batchDel(String paramString) throws HibernateException;
  
  Boolean checkExists(Long paramLong1, Long paramLong2, String paramString1, String paramString2) throws Exception;
}
