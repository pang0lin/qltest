package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.TrainhistoryVO;
import javax.ejb.EJBLocalObject;

public interface EmployeeTrainhistoryEJBLocal extends EJBLocalObject {
  Boolean save(TrainhistoryVO paramTrainhistoryVO, Long paramLong) throws Exception;
  
  TrainhistoryVO load(Long paramLong) throws Exception;
  
  Boolean update(TrainhistoryVO paramTrainhistoryVO, Long paramLong1, Long paramLong2) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
}
