package com.js.oa.hr.subsidiarywork.service;

import com.js.oa.hr.subsidiarywork.action.FestalCardActionForm;
import com.js.oa.hr.subsidiarywork.bean.FestalCardEJBHome;
import com.js.oa.hr.subsidiarywork.po.FestivalSetPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class FestalCardBD {
  private static Logger logger = Logger.getLogger(FestalCardBD.class.getName());
  
  public FestalCardActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    FestalCardActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (FestalCardActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(FestalCardActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(form, FestalCardActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(FestivalSetPO po, String userId1, String orgId1) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(po, FestivalSetPO.class);
      pg.put(userId1, "String");
      pg.put(orgId1, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public FestivalSetPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    FestivalSetPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(editId, "String");
      po = (FestivalSetPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public void update(FestivalSetPO paraPO, String editId, String appointYear) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("FestalCardEJB", 
          "FestalCardEJBLocal", FestalCardEJBHome.class);
      pg.put(paraPO, FestivalSetPO.class);
      pg.put(editId, String.class);
      pg.put(appointYear, String.class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
