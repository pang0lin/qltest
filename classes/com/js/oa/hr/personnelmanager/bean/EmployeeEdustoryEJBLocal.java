package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EdustoryVO;
import javax.ejb.EJBLocalObject;

public interface EmployeeEdustoryEJBLocal extends EJBLocalObject {
  Boolean save(EdustoryVO paramEdustoryVO, Long paramLong) throws Exception;
  
  EdustoryVO load(Long paramLong) throws Exception;
  
  Boolean update(EdustoryVO paramEdustoryVO, Long paramLong1, Long paramLong2) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
}
