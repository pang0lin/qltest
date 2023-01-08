package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface GovSendFileCheckWithWorkFlowEJBLocal extends EJBLocalObject {
  Long save(GovSendFileCheckWithWorkFlowPO paramGovSendFileCheckWithWorkFlowPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  Long update(GovSendFileCheckWithWorkFlowPO paramGovSendFileCheckWithWorkFlowPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  ArrayList load(Long paramLong) throws Exception;
  
  void deleteBatch(String paramString) throws Exception;
  
  Map getDossierInfo(String paramString) throws Exception;
  
  Boolean isPigeonholed(String paramString) throws Exception;
  
  Integer setPigeonholed(String paramString) throws Exception;
  
  Long completeSendFileCheck(String paramString) throws Exception;
}
