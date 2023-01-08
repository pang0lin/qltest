package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.TrainClassPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface TrainClassEJBLocal extends EJBLocalObject {
  Boolean save(TrainClassPO paramTrainClassPO) throws Exception;
  
  TrainClassPO load(Long paramLong) throws Exception;
  
  Boolean update(TrainClassPO paramTrainClassPO, Long paramLong) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  List list(Long paramLong) throws Exception;
}
