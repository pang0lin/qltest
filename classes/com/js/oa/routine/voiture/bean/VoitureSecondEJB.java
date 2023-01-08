package com.js.oa.routine.voiture.bean;

import com.js.oa.routine.voiture.po.VoitureFeePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface VoitureSecondEJB extends EJBObject {
  Integer voitureFeeAdd(VoitureFeePO paramVoitureFeePO, Long paramLong) throws Exception, RemoteException;
  
  List listVoitureFeeDetail(Long paramLong) throws Exception, RemoteException;
  
  void delVoitureFeeBatch(String paramString) throws Exception, RemoteException;
  
  void delVoitureFee(Long paramLong) throws Exception, RemoteException;
  
  List listModifyVoitureFee(Long paramLong) throws Exception, RemoteException;
  
  Integer updateVoitureFee(VoitureFeePO paramVoitureFeePO, Long paramLong) throws Exception, RemoteException;
  
  List listReportForms(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List listVoitureInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List listVoitureSend(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
}
