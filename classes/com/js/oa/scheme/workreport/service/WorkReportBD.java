package com.js.oa.scheme.workreport.service;

import com.js.oa.scheme.workreport.action.WorkReportActionForm;
import com.js.oa.scheme.workreport.bean.WorkReportEJBBean;
import com.js.oa.scheme.workreport.bean.WorkReportEJBHome;
import com.js.oa.scheme.workreport.po.WorkReportTransmitPO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class WorkReportBD {
  private static Logger logger = Logger.getLogger(WorkReportBD.class.getName());
  
  public WorkReportActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WorkReportActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("see", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public String template(String tempId, Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Object object = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(tempId, "String");
      pg.put(domainId, "Long");
      object = ejbProxy.invoke("template", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public Vector list(String wherePara, String currentPage) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Vector retString = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
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
    ParameterGenerator pg = new ParameterGenerator(2);
    List retString = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(wherePara, String.class);
      pg.put(domainId, "Long");
      retString = (List)ejbProxy.invoke("see", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return retString;
  }
  
  public Map load(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(ids, "String");
      object = ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public Boolean add(String userId, String orgId, String userName, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para, String reportName) {
    Boolean ret = new Boolean(true);
    ParameterGenerator pg = new ParameterGenerator(9);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(userName, "String");
      pg.put(nextReport, "String");
      pg.put(previousReport, "String");
      pg.put(accessoryName, String[].class);
      pg.put(accessorySaveName, String[].class);
      pg.put(para, String[].class);
      pg.put(reportName, "String");
      ret = (Boolean)ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return ret;
  }
  
  public void update(String editId, String userId, String orgId, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para) {
    ParameterGenerator pg = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(editId, "String");
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(nextReport, "String");
      pg.put(previousReport, "String");
      pg.put(accessoryName, String[].class);
      pg.put(accessorySaveName, String[].class);
      pg.put(para, String[].class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List initList(List list) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(list, List.class);
      alist = (List)ejbProxy.invoke("initList", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return alist;
  }
  
  public EmployeeVO getEmployeeByID(Long userID) {
    EmployeeVO vo = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(userID, Long.class);
      vo = (EmployeeVO)ejbProxy.invoke("getEmployeeByID", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return vo;
  }
  
  public String getWorkReportContentByCourse(String curUserID, String reportCourse) {
    String content = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(curUserID, String.class);
      pg.put(reportCourse, String.class);
      content = (String)ejbProxy.invoke("getWorkReportContentByCourse", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return content;
  }
  
  public List getReportData(String year, String orgID, String reportDomainId, String curUserId, List rightList) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(year, String.class);
      pg.put(orgID, String.class);
      pg.put(reportDomainId, String.class);
      pg.put(curUserId, String.class);
      pg.put(rightList, List.class);
      list = (List)ejbProxy.invoke("getReportData", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public int getUnderLingCount(String userID) {
    Integer underLingCount = new Integer(0);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(userID, String.class);
      underLingCount = (Integer)ejbProxy.invoke("getUnderLingCount", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return underLingCount.intValue();
  }
  
  public void deliverTO(String recieveID, String reportReadID, WorkReportTransmitPO wrtpo) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(recieveID, String.class);
      pg.put(reportReadID, String.class);
      pg.put(wrtpo, WorkReportTransmitPO.class);
      ejbProxy.invoke("deliverTO", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List getSonsByName(String orgName) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("WorkReportEJB", 
          "WorkReportEJBLocal", WorkReportEJBHome.class);
      pg.put(orgName, String.class);
      list = (List)ejbProxy.invoke("getSonsByName", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getWorkReportPO(String userId) {
    List list = null;
    try {
      list = (new WorkReportEJBBean()).getWorkReportPO(userId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
