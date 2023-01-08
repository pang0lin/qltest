package com.js.doc.doc.service;

import com.js.doc.doc.bean.GovComeFileUnitEJBHome;
import com.js.doc.doc.po.GovComeFileUnitPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;

public class GovComeFileUnitBD {
  public String add(GovComeFileUnitPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovComeFileUnitEJB", 
          "GovComeFileUnitEJBLocal", 
          GovComeFileUnitEJBHome.class);
      pg.put(po, GovComeFileUnitPO.class);
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
          "GovComeFileUnitEJB", 
          "GovComeFileUnitEJBLocal", 
          GovComeFileUnitEJBHome.class);
      pg.put(ids, "String");
      Object object = ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
  
  public GovComeFileUnitPO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovComeFileUnitPO form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovComeFileUnitEJB", 
          "GovComeFileUnitEJBLocal", 
          GovComeFileUnitEJBHome.class);
      pg.put(editId, "String");
      form = (GovComeFileUnitPO)ejbProxy.invoke("load", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public String update(String editId, String comeFileUnitName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String retString = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovComeFileUnitEJB", 
          "GovComeFileUnitEJBLocal", 
          GovComeFileUnitEJBHome.class);
      pg.put(editId, "String");
      pg.put(comeFileUnitName, String.class);
      Object object = ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
    } 
    return retString;
  }
  
  public List getComeFileUnit(String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovComeFileUnitEJB", 
          "GovComeFileUnitEJBLocal", 
          GovComeFileUnitEJBHome.class);
      pg.put(wherePara, "String");
      list = (List)ejbProxy.invoke("getComeFileUnit", pg.getParameters());
    } catch (Exception e) {
      list = new ArrayList();
      e.printStackTrace();
    } finally {}
    return list;
  }
}
