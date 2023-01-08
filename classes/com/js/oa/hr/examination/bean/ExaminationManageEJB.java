package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationAnswerItemPO;
import com.js.oa.hr.examination.po.ExaminationAnswerPO;
import com.js.oa.hr.examination.po.ExaminationManagePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ExaminationManageEJB extends EJBObject {
  Long save(ExaminationManagePO paramExaminationManagePO) throws Exception, RemoteException;
  
  ExaminationManagePO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(ExaminationManagePO paramExaminationManagePO, Long paramLong) throws Exception, RemoteException;
  
  Boolean savePaper(ExaminationAnswerPO paramExaminationAnswerPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  ExaminationAnswerItemPO loadPaper(Long paramLong1, Long paramLong2, String paramString) throws Exception, RemoteException;
  
  Boolean grade(Long paramLong, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Long getScore(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
  
  Boolean deleteBatch(String paramString) throws Exception, RemoteException;
  
  List voteList(String paramString1, String paramString2) throws Exception, RemoteException;
}
