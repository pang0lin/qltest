package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationTypePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ExaminationTypeEJBLocal extends EJBLocalObject {
  Boolean save(ExaminationTypePO paramExaminationTypePO) throws Exception;
  
  ExaminationTypePO load(Long paramLong) throws Exception;
  
  Boolean update(ExaminationTypePO paramExaminationTypePO, Long paramLong) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
  
  Boolean delBatch(String paramString) throws Exception;
  
  List getTypeList(Long paramLong) throws Exception;
}
