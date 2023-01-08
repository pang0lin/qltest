package com.js.oa.personalwork.netaddress.service;

import com.js.oa.personalwork.netaddress.action.AddressActionForm;
import com.js.oa.personalwork.netaddress.bean.AddressEJBBean;
import com.js.oa.personalwork.netaddress.bean.AddressEJBHome;
import com.js.oa.personalwork.netaddress.po.AddressPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class AddressBD {
  private static Logger logger = Logger.getLogger(AddressBD.class.getName());
  
  public AddressActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    AddressActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (AddressActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(AddressActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(form, AddressActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod1(HttpServletRequest request, String methodName, String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(AddressPO po, String userId, String userName, String myAddressClass, Byte isShare, String ids) {
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(po, AddressPO.class);
      pg.put(userId, "String");
      pg.put(userName, "String");
      pg.put(myAddressClass, "String");
      pg.put(isShare, "Byte");
      pg.put(ids, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(userId, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delShare(String ids, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(ids, "String");
      pg.put(userId, "String");
      ejbProxy.invoke("delShare", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public String showDesktop(String userId, String isShow, String netAddressId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    Object object = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(userId, "String");
      pg.put(isShow, "String");
      pg.put(netAddressId, "String");
      object = ejbProxy.invoke("showDesktop", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public AddressPO load(String eidtId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    AddressPO form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(eidtId, "String");
      form = (AddressPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void update(Byte isShare, String synopsis, String saveImg, Integer operate, Integer sso, String formaction, String formusername, String formuserpassword, Integer ssologin, String username, String password, String netAddressName, String netAddressUrl, String editId, String ids, String shareToName, String myAddress, String formelseparam, String oldPath, String userName, String userId, String domainId, String fieldValues, String isImmoField, int ordernumber) {
    ParameterGenerator pg = new ParameterGenerator(25);
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(isShare, Byte.class);
      pg.put(synopsis, String.class);
      pg.put(saveImg, String.class);
      pg.put(operate, Integer.class);
      pg.put(sso, Integer.class);
      pg.put(formaction, String.class);
      pg.put(formusername, String.class);
      pg.put(formuserpassword, String.class);
      pg.put(ssologin, Integer.class);
      pg.put(username, String.class);
      pg.put(password, String.class);
      pg.put(netAddressName, String.class);
      pg.put(netAddressUrl, String.class);
      pg.put(editId, String.class);
      pg.put(ids, String.class);
      pg.put(shareToName, String.class);
      pg.put(myAddress, "String");
      pg.put(formelseparam, String.class);
      pg.put(oldPath, String.class);
      pg.put(userName, String.class);
      pg.put(userId, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldValues, String.class);
      pg.put(isImmoField, String.class);
      pg.put(Integer.valueOf(ordernumber), int.class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List see(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("AddressEJB", 
          "AddressEJBLocal", AddressEJBHome.class);
      pg.put(userId, "String");
      list = (List)ejbProxy.invoke("see", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List getBox(String curUserId, String curOrgId, String orgIdString) {
    AddressEJBBean addressEJBBean = new AddressEJBBean();
    return addressEJBBean.getBox(curUserId, curOrgId, orgIdString);
  }
  
  public String setAccount(String id, String userId, String userName, String userPass, String elseparamvalue) {
    AddressEJBBean bean = new AddressEJBBean();
    try {
      bean.setAccount(id, userId, userName, userPass, elseparamvalue);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public String getFormelseParam(String id) {
    AddressEJBBean bean = new AddressEJBBean();
    String FormelseParam = null;
    try {
      FormelseParam = bean.getFormelseParam(id);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return FormelseParam;
  }
  
  public String getFormelseAllParam(String id) {
    AddressEJBBean bean = new AddressEJBBean();
    String FormelseParam = null;
    try {
      FormelseParam = bean.getFormelseAllParam(id);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return FormelseParam;
  }
  
  public String getElseParam(String id, String userId) {
    AddressEJBBean bean = new AddressEJBBean();
    String elseParam = null;
    try {
      elseParam = bean.getElseParam(id, userId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return elseParam;
  }
  
  public String[] getAccount(String id, String userId) {
    AddressEJBBean bean = new AddressEJBBean();
    String[] res = (String[])null;
    try {
      res = bean.getAccount(id, userId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
}
