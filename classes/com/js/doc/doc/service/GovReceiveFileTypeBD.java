package com.js.doc.doc.service;

import com.js.doc.doc.bean.GovReceiveFileTypeEJBHome;
import com.js.doc.doc.po.GovReceiveFileTypePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;

public class GovReceiveFileTypeBD {
  public String govReceiveFileTypeAdd(GovReceiveFileTypePO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String info = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", 
          "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      pg.put(po, GovReceiveFileTypePO.class);
      info = (String)ejbProxy.invoke("govReceiveFileTypeAdd", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return info;
  }
  
  public String govReceiveFileTypeDelBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String info = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", 
          "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      pg.put(ids, "String");
      info = (String)ejbProxy.invoke("govReceiveFileTypeDelBatch", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return info;
  }
  
  public String govReceiveFileTypeUpdate(GovReceiveFileTypePO paraPO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String update_info = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", 
          "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      pg.put(paraPO, GovReceiveFileTypePO.class);
      update_info = (String)ejbProxy.invoke("govReceiveFileTypeUpdate", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return update_info;
  }
  
  public List govReceiveFileTypeModifylist(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", 
          "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      pg.put(id, "String");
      list = (List)ejbProxy.invoke("govReceiveFileTypeModifylist", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public String govReceiveFileTypeDel(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String info = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", 
          "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      pg.put(id, "String");
      info = (String)ejbProxy.invoke("govReceiveFileTypeDel", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return info;
  }
  
  public List govReceiveFileTypeList() {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", 
          "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      list = (List)ejbProxy.invoke("govReceiveFileTypeList", (Object[][])null);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List govReceiveFileTypeList(String scopeWhere) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      pg.put(scopeWhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("GovReceiveFileTypeEJB", "GovReceiveFileTypeEJBLocal", GovReceiveFileTypeEJBHome.class);
      list = (List)ejbProxy.invoke("govReceiveFileTypeList", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
}
