package com.js.oa.personalwork.paper.service;

import com.js.oa.personalwork.paper.action.NotePaperActionForm;
import com.js.oa.personalwork.paper.bean.NotePaperEJBHome;
import com.js.oa.personalwork.paper.po.NotePaperPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class NotePaperBD {
  private static Logger logger = Logger.getLogger(NotePaperBD.class.getName());
  
  public NotePaperActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    NotePaperActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (NotePaperActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(NotePaperActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(form, NotePaperActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public String getColor(Long curUserId) {
    String retString = "1";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(curUserId, "Long");
      retString = (String)ejbProxy.invoke("getColor", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return retString;
  }
  
  public void delBatch(String ids, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(String notePaperColor, String notePaperContent, String userId, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(notePaperColor, String.class);
      pg.put(notePaperContent, String.class);
      pg.put(userId, "String");
      pg.put(domainId, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void update(String editId, String notPaperColor, String notePaperContent) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", 
          "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(editId, String.class);
      pg.put(notPaperColor, String.class);
      pg.put(notePaperContent, String.class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public NotePaperPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    NotePaperPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NotePaperEJB", "NotePaperEJBLocal", NotePaperEJBHome.class);
      pg.put(editId, String.class);
      po = (NotePaperPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
}
