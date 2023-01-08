package com.js.doc.doc.bean;

import com.js.doc.doc.po.ReceiveBaseInfoPO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ReceivedocumentEJBLocal extends EJBLocalObject {
  Object[] getReceivedocumentBaseInfo(String paramString) throws Exception;
  
  Long saveReceiveBaseInfo(ReceiveBaseInfoPO paramReceiveBaseInfoPO) throws Exception;
  
  String updateReceiveBaseInfo(ReceiveBaseInfoPO paramReceiveBaseInfoPO) throws Exception;
  
  Long saveRecSeqInfo(ReceiveFileSeqPO paramReceiveFileSeqPO) throws Exception;
  
  ReceiveFileSeqPO loadRecSeqPO(String paramString) throws Exception;
  
  String updateRecSeqPO(ReceiveFileSeqPO paramReceiveFileSeqPO) throws Exception;
  
  String deleteRecSeqPO(String paramString) throws Exception;
  
  List getRecSeqListByProceId(String paramString) throws Exception;
  
  List getRecSeqListByProceId(String paramString1, String paramString2) throws Exception;
  
  List getSeqPoListBySeqClass(String paramString) throws Exception;
}
