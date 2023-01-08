package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovSendFileCheckPO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface GovSendFileCheckEJBLocal extends EJBLocalObject {
  String add(GovSendFileCheckPO paramGovSendFileCheckPO) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  List load(String paramString) throws Exception;
  
  String update(GovSendFileCheckPO paramGovSendFileCheckPO, String paramString) throws Exception;
  
  String selfPostil(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String add(GovSendFileCheckPO paramGovSendFileCheckPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  Map getDossierInfo(String paramString) throws Exception;
  
  Boolean isPigeonholed(String paramString) throws Exception;
  
  Integer setPigeonholed(String paramString) throws Exception;
}
