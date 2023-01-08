package com.js.oa.scheme.workreport.service;

import com.js.oa.scheme.workreport.action.WorkReportLeaderActionForm;
import com.js.oa.scheme.workreport.bean.WorkReportLeaderEJBHome;
import com.js.oa.scheme.workreport.bean.WorkreportLeaderProductEJBBean;
import com.js.oa.scheme.workreport.bean.WorkreportLeaderProductEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class WorkReportLeaderProductBD {
  private static Logger logger = Logger.getLogger(WorkReportLeaderProductBD.class
      .getName());
  
  public WorkReportLeaderActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WorkReportLeaderActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (WorkReportLeaderActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(WorkReportLeaderActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(form, WorkReportLeaderActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkreportLeaderProductEJB", 
          "WorkreportLeaderProductEJBLocal", WorkreportLeaderProductEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkreportLeaderProductEJB", 
          "WorkreportLeaderProductEJBLocal", WorkreportLeaderProductEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(String userName, String receiveRecordId, String userId, String postilContent, String usersId, String usersName, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkreportLeaderProductEJB", "WorkreportLeaderProductEJBLocal", WorkreportLeaderProductEJBHome.class);
      pg.put(userName, "String");
      pg.put(receiveRecordId, "String");
      pg.put(userId, "String");
      pg.put(postilContent, "String");
      pg.put(usersId, "String");
      pg.put(usersName, "String");
      pg.put(domainId, "Long");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public Vector load(String sendRecordId, String receiveRecordId, String status, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(4);
    Vector vec = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkreportLeaderProductEJB", 
          "WorkreportLeaderProductEJBLocal", WorkreportLeaderProductEJBHome.class);
      pg.put(sendRecordId, "String");
      pg.put(receiveRecordId, "String");
      pg.put(status, "String");
      pg.put(domainId, "Long");
      vec = (Vector)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return vec;
  }
  
  public List getTopnNotReadReports(String readerId, String domainId, int top) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      EJBProxy ejbProxy = new EJBProxy("WorkreportLeaderProductEJB", 
          "WorkreportLeaderProductEJBLocal", WorkreportLeaderProductEJBHome.class);
      pg.put(readerId, "String");
      pg.put(domainId, "String");
      pg.put(new Integer(top), "Integer");
      list = (List)ejbProxy.invoke("getTopnNotReadReport", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public String getReportId(String id) {
    return (new WorkreportLeaderProductEJBBean()).getReportId(id);
  }
  
  public void setReport(String id, String reportName, String previousReport) {
    try {
      (new WorkreportLeaderProductEJBBean()).setReport(id, reportName, previousReport);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
