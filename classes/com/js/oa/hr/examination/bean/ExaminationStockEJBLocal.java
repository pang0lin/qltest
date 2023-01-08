package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationStockPO;
import javax.ejb.EJBLocalObject;

public interface ExaminationStockEJBLocal extends EJBLocalObject {
  Boolean save(ExaminationStockPO paramExaminationStockPO, Object[] paramArrayOfObject) throws Exception;
  
  ExaminationStockPO load(Long paramLong) throws Exception;
  
  Boolean update(ExaminationStockPO paramExaminationStockPO, Object[] paramArrayOfObject, Long paramLong) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
  
  Boolean deleteBatch(String paramString) throws Exception;
  
  Boolean move(String paramString1, String paramString2) throws Exception;
  
  String[] getStockArr(String paramString1, String paramString2, String paramString3, Long paramLong) throws Exception;
}
