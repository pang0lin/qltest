package com.js.oa.personalwork.person.service;

import com.js.oa.personalwork.person.action.PersonClassActionForm;
import com.js.oa.personalwork.person.bean.PersonClassEJBBean;
import com.js.oa.personalwork.person.bean.PersonClassEJBHome;
import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class PersonClassBD {
  private static Logger logger = Logger.getLogger(PersonClassBD.class.getName());
  
  public PersonClassActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    PersonClassActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (PersonClassActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(PersonClassActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(form, PersonClassActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String userType, String paraWhere) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(userType, "String");
      pg.put(paraWhere, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids, String userId, String classType) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      pg.put(classType, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public PersonClassPO load(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    PersonClassPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(id, "String");
      po = (PersonClassPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public boolean hasSameClassName(Long userId, String className, String domainId, String classType) {
    boolean retFlg = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(userId, "Long");
      pg.put(className, "String");
      pg.put(domainId, "String");
      pg.put(classType, "String");
      retFlg = ((Boolean)ejbProxy.invoke("hasSameClassName", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return retFlg;
  }
  
  public String add(String className, String classDescribe, String userId, String userType, String id, String name, String action, String classType, String orgId, String domainId) {
    Object object = "";
    ParameterGenerator pg = new ParameterGenerator(10);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(className, String.class);
      pg.put(classDescribe, String.class);
      pg.put(userId, "String");
      pg.put(userType, "String");
      pg.put(id, "String");
      pg.put(name, "String");
      pg.put(action, "String");
      pg.put(classType, "String");
      pg.put(orgId, "String");
      pg.put(domainId, "String");
      object = ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public Integer update(String userId, String userType, String id, String name, String action, String className, String classDescribe) {
    Integer result = Integer.valueOf("0");
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonClassEJB", 
          "PersonClassEJBLocal", PersonClassEJBHome.class);
      pg.put(userId, "String");
      pg.put(userType, "String");
      pg.put(id, "String");
      pg.put(name, "String");
      pg.put(action, "String");
      pg.put(className, "String");
      pg.put(classDescribe, "String");
      result = (Integer)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public PersonClassPO findPersonClassPO(Long userId, String className, byte classType, String orgId) throws HibernateException {
    PersonClassPO personClassPO = null;
    PersonClassEJBBean personClassEJBBean = new PersonClassEJBBean();
    personClassPO = personClassEJBBean.findPersonClassPO(userId, className, classType, orgId);
    return personClassPO;
  }
  
  public long addper(PersonClassPO personClassPO) throws HibernateException {
    PersonClassEJBBean personClassEJBBean = new PersonClassEJBBean();
    long personClassPOid = personClassEJBBean.addper(personClassPO);
    return personClassPOid;
  }
}
