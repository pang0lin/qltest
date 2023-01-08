package com.js.oa.routine.voiture.bean;

import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureAuditingPO;
import com.js.oa.routine.voiture.po.VoitureCancelPO;
import com.js.oa.routine.voiture.po.VoitureFeedbackPO;
import com.js.oa.routine.voiture.po.VoitureMaintainPO;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.oa.routine.voiture.po.VoitureSendPO;
import com.js.oa.routine.voiture.po.VoitureTypePO;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBObject;

public interface VoitureEJB extends EJBObject {
  Integer saveVoitureType(VoitureTypePO paramVoitureTypePO) throws Exception, RemoteException;
  
  List listVoitureType(String paramString) throws Exception, RemoteException;
  
  boolean delVoitureType(String paramString) throws Exception, RemoteException;
  
  VoitureTypePO loadVoitureType(String paramString) throws Exception, RemoteException;
  
  Integer updateVoitureType(VoitureTypePO paramVoitureTypePO, String paramString) throws Exception, RemoteException;
  
  Integer saveVoiture(VoiturePO paramVoiturePO) throws Exception, RemoteException;
  
  boolean delVoiture(String paramString) throws Exception, RemoteException;
  
  Integer updateVoiture(VoiturePO paramVoiturePO, String paramString) throws Exception, RemoteException;
  
  VoiturePO loadVoiture(String paramString) throws Exception, RemoteException;
  
  Long saveVoitureApply(VoitureApplyPO paramVoitureApplyPO) throws Exception, RemoteException;
  
  boolean delVoitureApply(String paramString) throws Exception, RemoteException;
  
  Integer saveVoitureMaintain(VoitureMaintainPO paramVoitureMaintainPO) throws Exception, RemoteException;
  
  boolean delVoitureMaintain(String paramString) throws Exception, RemoteException;
  
  boolean auditingApply(String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean auditingApply(String paramString1, VoitureApplyPO paramVoitureApplyPO, String paramString2) throws Exception, RemoteException;
  
  VoitureMaintainPO loadVoitureMaintain(String paramString) throws Exception, RemoteException;
  
  String getVoitureSendNumberMax() throws Exception, RemoteException;
  
  Integer saveVoitureSend(VoitureSendPO paramVoitureSendPO) throws Exception, RemoteException;
  
  VoitureApplyPO loadVoitureApply(String paramString) throws Exception, RemoteException;
  
  Integer updateVoitureApply(VoitureApplyPO paramVoitureApplyPO, String paramString) throws Exception, RemoteException;
  
  List listVoiture(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  VoitureSendPO loadVoitureSend(String paramString) throws Exception, RemoteException;
  
  VoitureSendPO getVoitureSendPO(String paramString) throws Exception, RemoteException;
  
  String[] getVoitureInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  VoiturePO getVoiturePO(String paramString) throws Exception, RemoteException;
  
  VoitureApplyPO getVoitureApplyPO(String paramString) throws Exception, RemoteException;
  
  Long saveVoitureCancel(VoitureCancelPO paramVoitureCancelPO) throws Exception, RemoteException;
  
  Long saveVoitureAuditing(VoitureAuditingPO paramVoitureAuditingPO) throws Exception, RemoteException;
  
  boolean isVoitureAuditingPO(String paramString) throws Exception, RemoteException;
  
  VoitureAuditingPO getVoitureAuditingPO(String paramString) throws Exception, RemoteException;
  
  Integer updateVoitureAudting(VoitureAuditingPO paramVoitureAuditingPO, String paramString) throws Exception, RemoteException;
  
  boolean delVoitureCancel(String paramString) throws Exception, RemoteException;
  
  boolean delVoitureCancelByApplyId(String paramString) throws Exception, RemoteException;
  
  boolean isVoitureSendPO(String paramString) throws Exception, RemoteException;
  
  Integer updateVoitureSend(VoitureSendPO paramVoitureSendPO, String paramString) throws Exception, RemoteException;
  
  String getVoitureUseRange(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  VoitureFeedbackPO getVoitureFeedbackPO(String paramString) throws Exception, RemoteException;
  
  String updateVoitureFeedbackPO(VoitureFeedbackPO paramVoitureFeedbackPO) throws Exception, RemoteException;
  
  Long saveVoitureFeedbackPO(VoitureFeedbackPO paramVoitureFeedbackPO) throws Exception, RemoteException;
  
  List getFeedbackStat(String paramString) throws Exception, RemoteException;
  
  List getFeedbackStat2(String paramString, Date paramDate1, Date paramDate2) throws Exception, RemoteException;
}
