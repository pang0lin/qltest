package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovReceiveFileTypePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface GovReceiveFileTypeEJBLocal extends EJBLocalObject {
  String govReceiveFileTypeAdd(GovReceiveFileTypePO paramGovReceiveFileTypePO) throws Exception;
  
  String govReceiveFileTypeDelBatch(String paramString) throws Exception;
  
  String govReceiveFileTypeUpdate(GovReceiveFileTypePO paramGovReceiveFileTypePO) throws Exception;
  
  List govReceiveFileTypeModifylist(String paramString) throws Exception;
  
  String govReceiveFileTypeDel(String paramString) throws Exception;
  
  List govReceiveFileTypeList() throws Exception;
  
  List govReceiveFileTypeList(String paramString) throws Exception;
}
