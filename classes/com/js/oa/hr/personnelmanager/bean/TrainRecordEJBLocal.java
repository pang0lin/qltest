package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.TrainRecordPO;
import javax.ejb.EJBLocalObject;

public interface TrainRecordEJBLocal extends EJBLocalObject {
  Boolean save(TrainRecordPO paramTrainRecordPO) throws Exception;
  
  TrainRecordPO load(Long paramLong) throws Exception;
  
  Boolean update(TrainRecordPO paramTrainRecordPO, Long paramLong) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
}
