package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EmpChangeEJBLocal extends EJBLocalObject {
  Boolean hasSameNameExists(String paramString1, String paramString2) throws HibernateException;
  
  Boolean hasSameNameExists(String paramString1, String paramString2, Long paramLong) throws HibernateException;
  
  boolean addEmpChange(EmployeeChangePO paramEmployeeChangePO) throws Exception;
  
  boolean deleteEmpChange(Long paramLong) throws Exception;
  
  boolean deleteBatchEmpChange(String paramString) throws Exception;
  
  EmployeeChangePO selectEmpChangeView(Long paramLong) throws HibernateException;
  
  boolean updateEmpChange(EmployeeChangePO paramEmployeeChangePO) throws Exception;
  
  String selectEmpName(Long paramLong) throws HibernateException;
  
  String selectOrgName(Long paramLong) throws HibernateException;
  
  List selectEmpDuty(String paramString) throws HibernateException;
  
  List selectEmpType(String paramString) throws HibernateException;
  
  Integer addEmpChangeType(EmployeeChangeTypePO paramEmployeeChangeTypePO) throws Exception;
  
  boolean deleteEmpChangeType(Long paramLong) throws Exception;
  
  boolean deleteBatchEmpChangeType(String paramString) throws Exception;
  
  EmployeeChangeTypePO selectEmpChangeTypeView(Long paramLong) throws HibernateException;
  
  Integer updateEmpChangeType(EmployeeChangeTypePO paramEmployeeChangeTypePO) throws Exception;
}
