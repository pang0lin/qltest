package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationAnswerItemPO;
import com.js.oa.hr.examination.po.ExaminationAnswerPO;
import com.js.oa.hr.examination.po.ExaminationManagePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ExaminationManageEJBLocal extends EJBLocalObject {
  Long save(ExaminationManagePO paramExaminationManagePO) throws Exception;
  
  ExaminationManagePO load(Long paramLong) throws Exception;
  
  Boolean update(ExaminationManagePO paramExaminationManagePO, Long paramLong) throws Exception;
  
  Boolean savePaper(ExaminationAnswerPO paramExaminationAnswerPO, Object[] paramArrayOfObject) throws Exception;
  
  ExaminationAnswerItemPO loadPaper(Long paramLong1, Long paramLong2, String paramString) throws Exception;
  
  Boolean grade(Long paramLong, Object[] paramArrayOfObject) throws Exception;
  
  Long getScore(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
  
  Boolean deleteBatch(String paramString) throws Exception;
  
  List voteList(String paramString1, String paramString2) throws Exception;
}
