package com.js.oa.info.templatemanager.service;

import com.js.oa.info.templatemanager.action.TemplateActionForm;
import com.js.oa.info.templatemanager.bean.TemplateEJBHome;
import com.js.oa.info.templatemanager.po.InformationTemplatePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class TemplateBD {
  private static Logger logger = Logger.getLogger(TemplateBD.class.getName());
  
  public TemplateActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    TemplateActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (TemplateActionForm)ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public boolean ejbMethod(TemplateActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      pg.put(form, TemplateActionForm.class);
      pg.put(request, HttpServletRequest.class);
      success = (Boolean)ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return success.booleanValue();
  }
  
  public void delAll(String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      success = (Boolean)ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return success.booleanValue();
  }
  
  public List getTemplate() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      list = (List)ejbProxy.invoke("getTemplate", (Object[][])null);
    } catch (Exception e) {
      logger.error("error to getTemplate information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getTemplateContent(String templateId) {
    String str = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(templateId, String.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      str = (String)ejbProxy.invoke("getTemplateContent", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getTemplateContent information :" + e.getMessage());
    } finally {}
    return str;
  }
  
  public List getAvailableTemplateByUser(String wherePara, String domainId, String module) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(wherePara, String.class);
      pg.put(domainId, String.class);
      pg.put(module, String.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      list = (List)ejbProxy.invoke("getAvailableTemplateByUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAvailableTemplateByUser information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Boolean add(InformationTemplatePO templatePO) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(templatePO, InformationTemplatePO.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      success = (Boolean)ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to add information :" + e.getMessage());
    } 
    return success;
  }
  
  public InformationTemplatePO load(String id) {
    InformationTemplatePO templatePO = new InformationTemplatePO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      templatePO = (InformationTemplatePO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
    } 
    return templatePO;
  }
  
  public Boolean update(InformationTemplatePO templatePO) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(templatePO, InformationTemplatePO.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean delBatch(String ids) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delBatch information :" + e.getMessage());
    } 
    return success;
  }
  
  public String judgeName(String domainId, String templateName, String templateId) {
    String rs = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(domainId, String.class);
      pg.put(templateName, String.class);
      pg.put(templateId, String.class);
      EJBProxy ejbProxy = new EJBProxy("TemplateEJB", "TemplateEJBLocal", TemplateEJBHome.class);
      rs = (String)ejbProxy.invoke("judgeName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to judgeName information :" + e.getMessage());
    } 
    return rs;
  }
}
