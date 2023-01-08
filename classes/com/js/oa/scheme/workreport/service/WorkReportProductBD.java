package com.js.oa.scheme.workreport.service;

import com.js.oa.scheme.workreport.action.WorkReportActionForm;
import com.js.oa.scheme.workreport.bean.WorkReportProductEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class WorkReportProductBD {
  private static Logger logger = Logger.getLogger(WorkReportProductBD.class.getName());
  
  public WorkReportActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WorkReportActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (WorkReportActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(WorkReportActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(form, WorkReportActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void see(HttpServletRequest request, String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("see", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public String template(String tempId, Long domainId) {
    return (new WorkReportBD()).template(tempId, domainId);
  }
  
  public Vector list(String wherePara, String currentPage) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Vector retString = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(wherePara, "String");
      pg.put(currentPage, "String");
      retString = (Vector)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return retString;
  }
  
  public List see(String wherePara, Long domainId) {
    return (new WorkReportBD()).see(wherePara, domainId);
  }
  
  public Map load(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(id, "String");
      result = (Map<Object, Object>)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Object object = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(ids, "String");
      object = ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public void add(String userId, String orgId, String userName, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para) {
    ParameterGenerator pg = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(userName, "String");
      pg.put(nextReport, "String");
      pg.put(previousReport, "String");
      pg.put(accessoryName, String[].class);
      pg.put(accessorySaveName, String[].class);
      pg.put(para, String[].class);
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void update(String editId, String userId, String orgId, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para, String reportName) {
    ParameterGenerator pg = new ParameterGenerator(9);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(editId, "String");
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(nextReport, "String");
      pg.put(previousReport, "String");
      pg.put(accessoryName, String[].class);
      pg.put(accessorySaveName, String[].class);
      pg.put(para, String[].class);
      pg.put(reportName, "String");
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List initList(List list) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("WorkReportProductEJB", 
          "WorkReportProductEJBLocal", WorkReportProductEJBHome.class);
      pg.put(list, List.class);
      alist = (List)ejbProxy.invoke("initList", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return alist;
  }
}
