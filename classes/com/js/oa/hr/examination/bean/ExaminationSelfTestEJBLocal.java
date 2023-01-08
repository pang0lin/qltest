package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationSelfTestPO;
import javax.ejb.EJBLocalObject;

public interface ExaminationSelfTestEJBLocal extends EJBLocalObject {
  Long save(ExaminationSelfTestPO paramExaminationSelfTestPO, Object[] paramArrayOfObject) throws Exception;
  
  String[] result(Long paramLong) throws Exception;
  
  ExaminationSelfTestPO load(Long paramLong) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
  
  Boolean deleteBatch(String paramString) throws Exception;
}
