package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovTypeSetPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface GovTypeSetEJBLocal extends EJBLocalObject {
  String add(GovTypeSetPO paramGovTypeSetPO) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  GovTypeSetPO load(String paramString) throws Exception;
  
  String update(String paramString, GovTypeSetPO paramGovTypeSetPO) throws Exception;
  
  List getTypeSet(String paramString) throws Exception;
  
  String getTypeNumber(String paramString) throws Exception;
}
