package com.js.oa.scheme.workreport.service;

import com.js.oa.scheme.workreport.action.WorkReportTemplateActionForm;
import com.js.oa.scheme.workreport.bean.WorkReportTemplateEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class WorkReportTemplateBD {
  private static Logger logger = Logger.getLogger(WorkReportTemplateBD.class
      .getName());
  
  public WorkReportTemplateActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WorkReportTemplateActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (WorkReportTemplateActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(WorkReportTemplateActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(form, WorkReportTemplateActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void update(String id, String templateName, String action, String templateDescript, String userOrgGroup, String templateUseRange, String templateContent, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(id, "String");
      pg.put(templateName, "String");
      pg.put(action, "String");
      pg.put(templateDescript, "String");
      pg.put(userOrgGroup, "String");
      pg.put(templateUseRange, "String");
      pg.put(templateContent, "String");
      pg.put(domainId, "Long");
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(String userId, String orgId, String templateName, String action, String userName, String templateDescript, String userOrgGroup, String templateUseRange, String templateContent, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(10);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(templateName, "String");
      pg.put(action, "String");
      pg.put(userName, "String");
      pg.put(templateDescript, "String");
      pg.put(userOrgGroup, "String");
      pg.put(templateUseRange, "String");
      pg.put(templateContent, "String");
      pg.put(domainId, "Long");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public boolean hasWorkReportTemplate(String id, String templateName, String action, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(4);
    boolean result = false;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(id, "String");
      pg.put(templateName, "String");
      pg.put(action, "String");
      pg.put(domainId, Long.class);
      result = ((Boolean)ejbProxy.invoke("hasWorkReportTemplate", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Map load(String editId, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "WorkReportTemplateEJB", 
          "WorkReportTemplateEJBLocal", WorkReportTemplateEJBHome.class);
      pg.put(editId, "String");
      pg.put(domainId, "Long");
      result = (Map<Object, Object>)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
}
