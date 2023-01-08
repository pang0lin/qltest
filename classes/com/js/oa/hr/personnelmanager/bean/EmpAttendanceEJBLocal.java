package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpAttendancePO;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EmpAttendanceEJBLocal extends EJBLocalObject {
  Boolean save(EmpAttendancePO paramEmpAttendancePO) throws HibernateException;
  
  EmpAttendancePO load(Long paramLong) throws HibernateException;
  
  Boolean modify(EmpAttendancePO paramEmpAttendancePO) throws HibernateException;
  
  Boolean delete(Long paramLong) throws HibernateException;
  
  Boolean batchDel(String paramString) throws HibernateException;
  
  Boolean checkExists(Long paramLong1, Long paramLong2, String paramString) throws Exception;
}
