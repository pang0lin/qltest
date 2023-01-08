package com.js.oa.personalwork.person.service;

import com.js.oa.personalwork.person.action.PersonInnerActionForm;
import com.js.oa.personalwork.person.bean.PersonInnerEJBHome;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PersonInnerBD {
  private static Logger logger = Logger.getLogger(PersonInnerBD.class.getName());
  
  public PersonInnerActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    PersonInnerActionForm form = new PersonInnerActionForm();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (PersonInnerActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(PersonInnerActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(form, PersonInnerActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public Vector list(String browseRange, String orgId, String userId, String queryItem, String queryText, String order, String desc, String offset) {
    ParameterGenerator pg = new ParameterGenerator(8);
    Vector vec = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(browseRange, "String");
      pg.put(orgId, "String");
      pg.put(userId, "String");
      pg.put(queryItem, "String");
      pg.put(queryText, "String");
      pg.put(order, "String");
      pg.put(desc, "String");
      pg.put(offset, "String");
      vec = (Vector)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return vec;
  }
  
  public Map load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(editId, "String");
      result = (Map)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public void update(EmployeeVO paraVO, String userAccount) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(paraVO, EmployeeVO.class);
      pg.put(userAccount, "String");
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List city(String country) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List vec = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(country, "String");
      vec = (List)ejbProxy.invoke("city", 
          pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return vec;
  }
  
  public List county(String country, String city) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List vec = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(country, "String");
      pg.put(city, "String");
      vec = (List)ejbProxy.invoke("county", 
          pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return vec;
  }
  
  public List see() {
    List vec = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      vec = (List)ejbProxy.invoke("see", (Object[][])null);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return vec;
  }
  
  public List setValidOrgs(Long domainId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonInnerEJB", 
          "PersonInnerEJBLocal", PersonInnerEJBHome.class);
      pg.put(domainId, Long.class);
      list = (List)ejbProxy.invoke("setValidOrgs", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
}
