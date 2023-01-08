package com.js.doc.doc.service;

import com.js.doc.doc.bean.GovTypeSetEJBHome;
import com.js.doc.doc.po.GovTypeSetPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;

public class GovTypeSetBD {
  public String add(GovTypeSetPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTypeSetEJB", 
          "GovTypeSetEJBLocal", 
          GovTypeSetEJBHome.class);
      pg.put(po, GovTypeSetPO.class);
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
          "GovTypeSetEJB", 
          "GovTypeSetEJBLocal", 
          GovTypeSetEJBHome.class);
      pg.put(ids, "String");
      Object object = ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
  
  public GovTypeSetPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovTypeSetPO form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTypeSetEJB", 
          "GovTypeSetEJBLocal", 
          GovTypeSetEJBHome.class);
      pg.put(editId, "String");
      form = (GovTypeSetPO)ejbProxy.invoke("load", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return form;
  }
  
  public String update(String editId, GovTypeSetPO form) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTypeSetEJB", 
          "GovTypeSetEJBLocal", 
          GovTypeSetEJBHome.class);
      pg.put(editId, "String");
      pg.put(form, GovTypeSetPO.class);
      Object object = ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
  
  public List getTypeSet(String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTypeSetEJB", 
          "GovTypeSetEJBLocal", 
          GovTypeSetEJBHome.class);
      pg.put(wherePara, "String");
      list = (List)ejbProxy.invoke("getTypeSet", pg.getParameters());
    } catch (Exception e) {
      list = new ArrayList();
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public String getTypeNumber(String typeId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String retString = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovTypeSetEJB", 
          "GovTypeSetEJBLocal", 
          GovTypeSetEJBHome.class);
      pg.put(typeId, "String");
      Object object = ejbProxy.invoke("getTypeNumber", pg.getParameters());
    } catch (Exception e) {
      retString = "-1";
      e.printStackTrace();
    } finally {}
    return retString;
  }
}
