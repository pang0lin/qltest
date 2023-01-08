package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EmpSocialinsuranceEJBLocal extends EJBLocalObject {
  Boolean save(EmpSocialinsurancePO paramEmpSocialinsurancePO) throws HibernateException;
  
  EmpSocialinsurancePO load(Long paramLong) throws HibernateException;
  
  Boolean modify(EmpSocialinsurancePO paramEmpSocialinsurancePO) throws HibernateException;
  
  Boolean delete(Long paramLong) throws HibernateException;
  
  Boolean batchDel(String paramString) throws HibernateException;
}
