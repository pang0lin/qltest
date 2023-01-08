package com.js.oa.info.templatemanager.bean;

import com.js.oa.info.templatemanager.po.InformationTemplatePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface TemplateEJB extends EJBObject {
  Boolean add(InformationTemplatePO paramInformationTemplatePO) throws Exception, RemoteException;
  
  Boolean delBatch(String paramString) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  InformationTemplatePO load(String paramString) throws Exception, RemoteException;
  
  Boolean update(InformationTemplatePO paramInformationTemplatePO) throws Exception, RemoteException;
  
  List getTemplate() throws Exception, RemoteException;
  
  String getTemplateContent(String paramString) throws Exception, RemoteException;
  
  List getAvailableTemplateByUser(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String judgeName(String paramString1, String paramString2, String paramString3) throws Exception;
}
