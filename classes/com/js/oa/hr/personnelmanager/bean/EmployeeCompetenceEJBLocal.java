package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.CompetenceVO;
import javax.ejb.EJBLocalObject;

public interface EmployeeCompetenceEJBLocal extends EJBLocalObject {
  Boolean save(CompetenceVO paramCompetenceVO, Long paramLong) throws Exception;
  
  CompetenceVO load(Long paramLong) throws Exception;
  
  Boolean update(CompetenceVO paramCompetenceVO, Long paramLong1, Long paramLong2) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
}
