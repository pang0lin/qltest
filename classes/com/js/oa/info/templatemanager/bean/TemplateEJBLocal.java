package com.js.oa.info.templatemanager.bean;

import com.js.oa.info.templatemanager.po.InformationTemplatePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface TemplateEJBLocal extends EJBLocalObject {
  Boolean add(InformationTemplatePO paramInformationTemplatePO) throws Exception;
  
  Boolean delBatch(String paramString) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  InformationTemplatePO load(String paramString) throws Exception;
  
  Boolean update(InformationTemplatePO paramInformationTemplatePO) throws Exception;
  
  List getTemplate() throws Exception;
  
  String getTemplateContent(String paramString) throws Exception;
  
  List getAvailableTemplateByUser(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String judgeName(String paramString1, String paramString2, String paramString3) throws Exception;
}
