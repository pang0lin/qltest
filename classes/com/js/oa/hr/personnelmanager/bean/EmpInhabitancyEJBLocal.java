package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpInhabitancyPO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EmpInhabitancyEJBLocal extends EJBLocalObject {
  Boolean save(EmpInhabitancyPO paramEmpInhabitancyPO) throws HibernateException;
  
  EmpInhabitancyPO load(Long paramLong) throws HibernateException;
  
  Boolean modify(EmpInhabitancyPO paramEmpInhabitancyPO) throws HibernateException;
  
  Boolean delete(Long paramLong) throws HibernateException;
  
  Boolean batchDel(String paramString) throws HibernateException;
  
  List getEmployeeFROMJZZ2(Integer paramInteger) throws Exception;
  
  Boolean saveTOSENDJZZ(Long paramLong, Integer paramInteger) throws Exception;
}
