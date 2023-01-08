package com.js.oa.routine.voiture.bean;

import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureAuditingPO;
import com.js.oa.routine.voiture.po.VoitureCancelPO;
import com.js.oa.routine.voiture.po.VoitureFeedbackPO;
import com.js.oa.routine.voiture.po.VoitureMaintainPO;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.oa.routine.voiture.po.VoitureSendPO;
import com.js.oa.routine.voiture.po.VoitureTypePO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface VoitureEJBLocal extends EJBLocalObject {
  Integer saveVoitureType(VoitureTypePO paramVoitureTypePO) throws Exception;
  
  List listVoitureType(String paramString) throws Exception;
  
  boolean delVoitureType(String paramString) throws Exception;
  
  VoitureTypePO loadVoitureType(String paramString) throws Exception;
  
  Integer updateVoitureType(VoitureTypePO paramVoitureTypePO, String paramString) throws Exception;
  
  Integer saveVoiture(VoiturePO paramVoiturePO) throws Exception;
  
  boolean delVoiture(String paramString) throws Exception;
  
  Integer updateVoiture(VoiturePO paramVoiturePO, String paramString) throws Exception;
  
  VoiturePO loadVoiture(String paramString) throws Exception;
  
  Long saveVoitureApply(VoitureApplyPO paramVoitureApplyPO) throws Exception;
  
  boolean delVoitureApply(String paramString) throws Exception;
  
  Integer saveVoitureMaintain(VoitureMaintainPO paramVoitureMaintainPO) throws Exception;
  
  boolean delVoitureMaintain(String paramString) throws Exception;
  
  boolean auditingApply(String paramString1, String paramString2) throws Exception;
  
  boolean auditingApply(String paramString1, VoitureApplyPO paramVoitureApplyPO, String paramString2) throws Exception;
  
  VoitureMaintainPO loadVoitureMaintain(String paramString) throws Exception;
  
  String getVoitureSendNumberMax() throws Exception;
  
  Integer saveVoitureSend(VoitureSendPO paramVoitureSendPO) throws Exception;
  
  VoitureApplyPO loadVoitureApply(String paramString) throws Exception;
  
  Integer updateVoitureApply(VoitureApplyPO paramVoitureApplyPO, String paramString) throws Exception;
  
  List listVoiture(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  VoitureSendPO loadVoitureSend(String paramString) throws Exception;
  
  VoitureSendPO getVoitureSendPO(String paramString) throws Exception;
  
  String[] getVoitureInfo(String paramString1, String paramString2) throws Exception;
  
  VoiturePO getVoiturePO(String paramString) throws Exception;
  
  VoitureApplyPO getVoitureApplyPO(String paramString) throws Exception;
  
  Long saveVoitureCancel(VoitureCancelPO paramVoitureCancelPO) throws Exception;
  
  Long saveVoitureAuditing(VoitureAuditingPO paramVoitureAuditingPO) throws Exception;
  
  boolean isVoitureAuditingPO(String paramString) throws Exception;
  
  VoitureAuditingPO getVoitureAuditingPO(String paramString) throws Exception;
  
  Integer updateVoitureAudting(VoitureAuditingPO paramVoitureAuditingPO, String paramString) throws Exception;
  
  boolean delVoitureCancel(String paramString) throws Exception;
  
  boolean delVoitureCancelByApplyId(String paramString) throws Exception;
  
  boolean isVoitureSendPO(String paramString) throws Exception;
  
  Integer updateVoitureSend(VoitureSendPO paramVoitureSendPO, String paramString) throws Exception;
  
  String getVoitureUseRange(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  VoitureFeedbackPO getVoitureFeedbackPO(String paramString) throws Exception;
  
  String updateVoitureFeedbackPO(VoitureFeedbackPO paramVoitureFeedbackPO) throws Exception;
  
  Long saveVoitureFeedbackPO(VoitureFeedbackPO paramVoitureFeedbackPO) throws Exception;
  
  List getFeedbackStat(String paramString) throws Exception;
  
  List getFeedbackStat2(String paramString, Date paramDate1, Date paramDate2) throws Exception;
}
