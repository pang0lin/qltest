package com.js.oa.personalwork.netaddress.service;

import com.js.oa.personalwork.netaddress.action.AddressClassActionForm;
import com.js.oa.personalwork.netaddress.bean.AddressClassEJBHome;
import com.js.oa.personalwork.netaddress.po.AddressClassPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class AddressClassBD {
  private static Logger logger = Logger.getLogger(AddressClassBD.class
      .getName());
  
  public AddressClassActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    AddressClassActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (AddressClassActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(AddressClassActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(form, AddressClassActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(userId, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public Boolean add(String className, Integer classIsShare, String action, String editId, String userId, String domainId) {
    Boolean flag = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(className, "String");
      pg.put(classIsShare, Integer.class);
      pg.put(action, "String");
      pg.put(editId, "String");
      pg.put(userId, "String");
      pg.put(domainId, "String");
      flag = (Boolean)ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return flag;
  }
  
  public Boolean update(String className, Integer classIsShare, String action, String editId, String userId) {
    Boolean flag = null;
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(className, "String");
      pg.put(classIsShare, Integer.class);
      pg.put(action, "String");
      pg.put(editId, "String");
      pg.put(userId, "String");
      flag = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return flag;
  }
  
  public AddressClassPO load(String id) {
    AddressClassPO addressClassPO = null;
    String className = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressClassEJB", 
          "AddressClassEJBLocal", AddressClassEJBHome.class);
      pg.put(id, "String");
      addressClassPO = (AddressClassPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return addressClassPO;
  }
}
