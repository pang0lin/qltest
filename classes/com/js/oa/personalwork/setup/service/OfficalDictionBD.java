package com.js.oa.personalwork.setup.service;

import com.js.oa.personalwork.setup.bean.OfficalDictionEJBHome;
import com.js.oa.personalwork.setup.po.OfficalDictionPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Vector;

public class OfficalDictionBD {
  public String add(OfficalDictionPO po, Long curUserId, String curUserName) {
    ParameterGenerator pg = new ParameterGenerator(3);
    Object object = "";
    try {
      EJBProxy ejbProxy = 
        new EJBProxy("OfficalDictionEJB", 
          "OfficalDictionEJBLocal", 
          OfficalDictionEJBHome.class);
      pg.put(po, OfficalDictionPO.class);
      pg.put(curUserId, "Long");
      pg.put(curUserName, "String");
      object = ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public Vector list(Long curUserId, Integer offset) {
    Vector vec = new Vector();
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = 
        new EJBProxy("OfficalDictionEJB", 
          "OfficalDictionEJBLocal", 
          OfficalDictionEJBHome.class);
      pg.put(curUserId, "Long");
      pg.put(offset, "Integer");
      vec = (Vector)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return vec;
  }
  
  public void delAll(Long curUserId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = 
        new EJBProxy("OfficalDictionEJB", 
          "OfficalDictionEJBLocal", 
          OfficalDictionEJBHome.class);
      pg.put(curUserId, "Long");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = 
        new EJBProxy("OfficalDictionEJB", 
          "OfficalDictionEJBLocal", 
          OfficalDictionEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public OfficalDictionPO load(Long editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    OfficalDictionPO po = new OfficalDictionPO();
    try {
      EJBProxy ejbProxy = 
        new EJBProxy("OfficalDictionEJB", 
          "OfficalDictionEJBLocal", 
          OfficalDictionEJBHome.class);
      pg.put(editId, "Long");
      po = (OfficalDictionPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return po;
  }
  
  public String update(String content, Byte isShare, Long editId, Long curUserId, String curUserName) {
    ParameterGenerator pg = new ParameterGenerator(5);
    Object object = "";
    try {
      EJBProxy ejbProxy = 
        new EJBProxy("OfficalDictionEJB", 
          "OfficalDictionEJBLocal", 
          OfficalDictionEJBHome.class);
      pg.put(content, "String");
      pg.put(isShare, "Byte");
      pg.put(editId, "Long");
      pg.put(curUserId, "Long");
      pg.put(curUserName, "String");
      object = ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
}
