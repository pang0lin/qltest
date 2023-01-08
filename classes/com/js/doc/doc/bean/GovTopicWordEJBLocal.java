package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovTopicWordPO;
import javax.ejb.EJBLocalObject;

public interface GovTopicWordEJBLocal extends EJBLocalObject {
  String add(GovTopicWordPO paramGovTopicWordPO) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  GovTopicWordPO load(String paramString) throws Exception;
  
  String update(String paramString, GovTopicWordPO paramGovTopicWordPO) throws Exception;
}
