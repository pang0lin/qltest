package com.js.doc.doc.service;

import com.js.doc.doc.bean.GovTopicWordEJBHome;
import com.js.doc.doc.po.GovTopicWordPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;

public class GovTopicWordBD {
  public String add(GovTopicWordPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTopicWordEJB", 
          "GovTopicWordEJBLocal", 
          GovTopicWordEJBHome.class);
      pg.put(po, GovTopicWordPO.class);
      Object object = ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
  
  public String delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTopicWordEJB", 
          "GovTopicWordEJBLocal", 
          GovTopicWordEJBHome.class);
      pg.put(ids, "String");
      Object object = ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
  
  public GovTopicWordPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovTopicWordPO form = new GovTopicWordPO();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTopicWordEJB", 
          "GovTopicWordEJBLocal", 
          GovTopicWordEJBHome.class);
      pg.put(editId, "String");
      form = (GovTopicWordPO)ejbProxy.invoke("load", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public String update(String editId, GovTopicWordPO form) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTopicWordEJB", 
          "GovTopicWordEJBLocal", 
          GovTopicWordEJBHome.class);
      pg.put(editId, "String");
      pg.put(form, GovTopicWordPO.class);
      Object object = ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
}
