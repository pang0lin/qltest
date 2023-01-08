package com.js.oa.hr.subsidiarywork.service;

import com.js.oa.hr.subsidiarywork.action.BirthCardActionForm;
import com.js.oa.hr.subsidiarywork.bean.BirthCardEJBHome;
import com.js.oa.hr.subsidiarywork.po.BirthdayWishPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class BirthCardBD {
  private static Logger logger = Logger.getLogger(BirthCardBD.class.getName());
  
  public BirthCardActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    BirthCardActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (BirthCardActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(BirthCardActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(form, BirthCardActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(BirthdayWishPO po, String userId1, String orgId1) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(po, BirthdayWishPO.class);
      pg.put(userId1, "String");
      pg.put(orgId1, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void update(String editId, String wishCard, String wishCardName, String wishEmployees, String wishEmployeesName, String wishSign, String wishContent, String path) {
    ParameterGenerator pg = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(editId, String.class);
      pg.put(wishCard, String.class);
      pg.put(wishCardName, String.class);
      pg.put(wishEmployees, String.class);
      pg.put(wishEmployeesName, String.class);
      pg.put(wishSign, String.class);
      pg.put(wishContent, String.class);
      pg.put(path, String.class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public BirthdayWishPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    BirthdayWishPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("BirthCardEJB", 
          "BirthCardEJBLocal", BirthCardEJBHome.class);
      pg.put(editId, "String");
      po = (BirthdayWishPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
}
