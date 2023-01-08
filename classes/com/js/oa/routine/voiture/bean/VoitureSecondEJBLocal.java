package com.js.oa.routine.voiture.bean;

import com.js.oa.routine.voiture.po.VoitureFeePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface VoitureSecondEJBLocal extends EJBLocalObject {
  Integer voitureFeeAdd(VoitureFeePO paramVoitureFeePO, Long paramLong) throws Exception;
  
  List listVoitureFeeDetail(Long paramLong) throws Exception;
  
  void delVoitureFeeBatch(String paramString) throws Exception;
  
  void delVoitureFee(Long paramLong) throws Exception;
  
  List listModifyVoitureFee(Long paramLong) throws Exception;
  
  Integer updateVoitureFee(VoitureFeePO paramVoitureFeePO, Long paramLong) throws Exception;
  
  List listReportForms(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List listVoitureInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List listVoitureSend(String paramString1, String paramString2, String paramString3) throws Exception;
}
