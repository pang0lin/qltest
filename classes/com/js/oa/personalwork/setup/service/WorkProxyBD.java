package com.js.oa.personalwork.setup.service;

import com.js.oa.personalwork.setup.action.WorkProxyActionForm;
import com.js.oa.personalwork.setup.bean.WorkProxyEJBHome;
import com.js.oa.personalwork.setup.po.WorkProxyPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class WorkProxyBD {
  private static Logger logger = Logger.getLogger(WorkProxyBD.class.getName());
  
  public WorkProxyActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WorkProxyActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", 
          "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (WorkProxyActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(WorkProxyActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", 
          "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(form, WorkProxyActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
  }
  
  public String getAvailableProxy(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(userId, "String");
      userId = (String)ejbProxy.invoke("getAvailableProxy", 
          pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return userId;
  }
  
  public String[] getAvailableUsers(String[] userIdArr) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(userIdArr, "String[]");
      userIdArr = (String[])ejbProxy.invoke("getAvailableUsers", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return userIdArr;
  }
  
  public void delBatch(String ids, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("delBatch", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void delAll(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(userId, "String");
      ejbProxy.invoke("delAll", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void add(WorkProxyPO po, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", 
          "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(po, WorkProxyPO.class);
      pg.put(userId, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void update(WorkProxyPO paraPO, String id, String userId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", 
          "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(paraPO, WorkProxyPO.class);
      pg.put(id, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("update", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public WorkProxyPO load(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WorkProxyPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      pg.put(userId, "String");
      po = (WorkProxyPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public void setUnavailableProxy() {
    ParameterGenerator pg = new ParameterGenerator(0);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkProxyEJB", 
          "WorkProxyEJBLocal", WorkProxyEJBHome.class);
      ejbProxy.invoke("setUnavailableProxy", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
