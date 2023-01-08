package com.js.oa.personalwork.person.service;

import com.js.oa.personalwork.person.action.PersonOwnActionForm;
import com.js.oa.personalwork.person.bean.PersonOwnEJBBean;
import com.js.oa.personalwork.person.bean.PersonOwnEJBHome;
import com.js.oa.personalwork.person.po.PersonPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PersonOwnBD {
  private static Logger logger = Logger.getLogger(PersonOwnBD.class.getName());
  
  public PersonOwnActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    PersonOwnActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (PersonOwnActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(PersonOwnActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(form, PersonOwnActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public PersonPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    PersonPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(editId, "String");
      po = (PersonPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public Vector see(String userId, String userType, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    Vector form = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(userId, "String");
      pg.put(userType, "String");
      pg.put(domainId, "String");
      form = (Vector)ejbProxy.invoke("see", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public List city(String country) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List form = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(country, "String");
      form = (List)ejbProxy.invoke("city", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public List county(String country, String city) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List form = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(country, "String");
      pg.put(city, "String");
      form = (List)ejbProxy.invoke("county", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void update(PersonPO paraPO, String classId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(paraPO, PersonPO.class);
      pg.put(classId, String.class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String userId, String userType) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(userId, "String");
      pg.put(userType, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String userId, String userType, String pclassType) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(userId, "String");
      pg.put(userType, "String");
      pg.put(pclassType, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(PersonPO po, String userId, String userName, String orgId, String userType, String tmpLinkManBirth, String classId) {
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(po, PersonPO.class);
      pg.put(userId, "String");
      pg.put(userName, "String");
      pg.put(orgId, "String");
      pg.put(userType, "String");
      pg.put(tmpLinkManBirth, "String");
      pg.put(classId, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public Vector list(String userType, String queryItem, String queryText, String order, String desc, String offset, String userId) {
    ParameterGenerator pg = new ParameterGenerator(7);
    Vector vec = new Vector();
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonOwnEJB", 
          "PersonOwnEJBLocal", PersonOwnEJBHome.class);
      pg.put(userType, "String");
      pg.put(queryItem, "String");
      pg.put(queryText, "String");
      pg.put(order, "String");
      pg.put(desc, "String");
      pg.put(offset, "String");
      pg.put(userId, "String");
      vec = (Vector)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return vec;
  }
  
  public void addInput(PersonPO po) throws Exception {
    PersonOwnEJBBean personOwnEJBBean = new PersonOwnEJBBean();
    personOwnEJBBean.addInput(po);
  }
  
  public List getUnitList(Byte linkManType, int userid) throws Exception {
    List list = null;
    PersonOwnEJBBean poeb = new PersonOwnEJBBean();
    try {
      list = poeb.getUnitList(linkManType, userid);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getPublicUnitList(Byte linkManType) {
    List list = null;
    PersonOwnEJBBean poeb = new PersonOwnEJBBean();
    try {
      list = poeb.getPublicUnitList(linkManType);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
