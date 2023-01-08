package com.js.oa.scheme.workreport.service;

import com.js.oa.scheme.workreport.action.WorkReportLeaderActionForm;
import com.js.oa.scheme.workreport.bean.WorkReportLeaderEJBBean;
import com.js.oa.scheme.workreport.bean.WorkReportLeaderEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class WorkReportLeaderBD {
  private static Logger logger = Logger.getLogger(WorkReportLeaderBD.class
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
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(String userName, String receiveRecordId, String userId, String postilContent, String usersId, String usersName, String nextWorkClew, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(userName, "String");
      pg.put(receiveRecordId, "String");
      pg.put(userId, "String");
      pg.put(postilContent, "String");
      pg.put(usersId, "String");
      pg.put(usersName, "String");
      pg.put(nextWorkClew, "String");
      pg.put(domainId, "Long");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(String userName, String receiveRecordId, String userId, String postilContent, String usersId, String usersName, String nextWorkClew, String grade, String postilResult, String postilGrade, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(11);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(userName, "String");
      pg.put(receiveRecordId, "String");
      pg.put(userId, "String");
      pg.put(postilContent, "String");
      pg.put(usersId, "String");
      pg.put(usersName, "String");
      pg.put(nextWorkClew, "String");
      pg.put(grade, "String");
      pg.put(postilResult, "String");
      pg.put(postilGrade, "String");
      pg.put(domainId, "Long");
      ejbProxy.invoke("addMonthReport", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public Vector load(String sendRecordId, String receiveRecordId, String status, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(4);
    Vector vec = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
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
  
  public List getTopnNotReadReports(String readerId, String domainId, int top, int reportType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(readerId, "String");
      pg.put(domainId, "String");
      pg.put(new Integer(top), "Integer");
      pg.put(new Integer(reportType), "Integer");
      list = (List)ejbProxy.invoke("getTopnNotReadReport", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List getTopnNotReadReports(String readerId, String domainId, int top) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(readerId, "String");
      pg.put(domainId, "String");
      pg.put(new Integer(top), "Integer");
      list = (List)ejbProxy.invoke("getTopnNotReadReport", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public boolean back(Long reportId, String leaderName, String reportType) {
    boolean ret = true;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      EJBProxy ejbProxy = new EJBProxy("WorkReportLeaderEJB", 
          "WorkReportLeaderEJBLocal", WorkReportLeaderEJBHome.class);
      pg.put(reportId, Long.class);
      pg.put(leaderName, String.class);
      pg.put(reportType, String.class);
      ejbProxy.invoke("back", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return ret;
  }
  
  public String getByIds(String sendRecordId, String receiveRecordId, String status, Long domanId) throws HibernateException {
    String result = "N";
    WorkReportLeaderEJBBean workLogEJBBean = new WorkReportLeaderEJBBean();
    result = workLogEJBBean.getByIds(sendRecordId, receiveRecordId, status, domanId);
    return result;
  }
}
