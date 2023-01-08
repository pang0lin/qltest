package com.js.oa.tjgzw.bean;

import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveAssociatePO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface ReceiveFileGzwEJBLocal extends EJBLocalObject {
  String initNumber(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Long save(GovReceiveFilePO paramGovReceiveFilePO) throws Exception;
  
  GovReceiveFilePO load(String paramString) throws Exception;
  
  Long update(GovReceiveFilePO paramGovReceiveFilePO, String paramString) throws Exception;
  
  Long completeReceiveFile(String paramString) throws Exception;
  
  Map getDossierInfo(String paramString) throws Exception;
  
  Boolean isPigeonholed(String paramString) throws Exception;
  
  Integer setPigeonholed(String paramString) throws Exception;
  
  Integer delete(String paramString) throws Exception;
  
  Long saveReceiveAssociate(ReceiveAssociatePO paramReceiveAssociatePO) throws Exception;
  
  Integer getReceiveAssociateNum(String paramString) throws Exception;
  
  List getRecieveAssociateList(String paramString) throws Exception;
}
