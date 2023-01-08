package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpCompoPO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EmpCompoEJBLocal extends EJBLocalObject {
  Boolean save(EmpCompoPO paramEmpCompoPO) throws HibernateException;
  
  EmpCompoPO load(Long paramLong) throws HibernateException;
  
  Boolean modify(EmpCompoPO paramEmpCompoPO) throws HibernateException;
  
  Boolean delete(Long paramLong) throws HibernateException;
  
  Boolean batchDel(String paramString) throws HibernateException;
}
