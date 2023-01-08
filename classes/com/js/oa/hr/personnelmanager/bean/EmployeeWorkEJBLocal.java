package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.WorkVO;
import javax.ejb.EJBLocalObject;

public interface EmployeeWorkEJBLocal extends EJBLocalObject {
  Boolean save(WorkVO paramWorkVO, Long paramLong) throws Exception;
  
  WorkVO load(Long paramLong) throws Exception;
  
  Boolean update(WorkVO paramWorkVO, Long paramLong1, Long paramLong2) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
}
