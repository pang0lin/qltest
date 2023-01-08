package com.js.doc.doc.service;

import com.js.doc.doc.bean.GovCustomEJBHome;
import com.js.doc.doc.po.GovCustomCheckedFieldPO;
import com.js.doc.doc.po.GovCustomPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class CovCustomBD {
  public Long SaveGovCustomPO(GovCustomPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = new Long(-1L);
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(po, GovCustomPO.class);
      result = (Long)ejbProxy.invoke("SaveGovCustomPO", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to SaveGovCustomPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateGovCustomPO(GovCustomPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(po, GovCustomPO.class);
      result = (String)ejbProxy.invoke("updateGovCustomPO", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateGovCustomPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public GovCustomPO loadGovCustomPO(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovCustomPO po = new GovCustomPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(id, String.class);
      po = (GovCustomPO)ejbProxy.invoke("loadGovCustomPO", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to loadGovCustomPO  :" + e.getMessage());
    } 
    return po;
  }
  
  public String deleteGovCustomPO(String id, String formId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(id, String.class);
      pg.put(formId, String.class);
      result = (String)ejbProxy.invoke("deleteGovCustomPO", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to deleteGovCustomPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public List loadCheckFieldList(String formId, String gffType) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(formId, String.class);
      pg.put(gffType, String.class);
      list = (List)ejbProxy.invoke("loadCheckFieldList", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to loadCheckFieldList  :" + e.getMessage());
    } 
    return list;
  }
  
  public String saveCheckFieldBatch(List poList, String gffType) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(poList, List.class);
      pg.put(gffType, String.class);
      result = (String)ejbProxy.invoke("saveCheckFieldBatch", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to saveCheckFieldBatch  :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateGovCustomPO(GovCustomPO fPO, String gffType) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(fPO, GovCustomPO.class);
      pg.put(gffType, String.class);
      result = (String)ejbProxy.invoke("updateGovCustomPO", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateGovCustomPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveCheckField(GovCustomCheckedFieldPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = Long.valueOf("-1");
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(po, GovCustomCheckedFieldPO.class);
      result = (Long)ejbProxy.invoke("saveCheckField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to saveCheckField  :" + e.getMessage());
    } 
    return result;
  }
  
  public List loadFieldList(String type) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(type, String.class);
      list = (List)ejbProxy.invoke("loadFieldList", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to loadFieldList  :" + e.getMessage());
    } 
    return list;
  }
  
  public void addWorkFlowControl(List fieldControlList, String formId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(fieldControlList, List.class);
      pg.put(formId, String.class);
      list = (List)ejbProxy.invoke("addWorkFlowControl", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to addWorkFlowControl  :" + e.getMessage());
    } 
  }
  
  public void updateWorkFlowControl(List fieldControlList, String formId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(fieldControlList, List.class);
      pg.put(formId, String.class);
      list = (List)ejbProxy.invoke("updateWorkFlowControl", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateWorkFlowControl  :" + e.getMessage());
    } 
  }
  
  public List loadCheckFieldListByDisplayType(String formId, String displayType, String gffType) {
    ParameterGenerator pg = new ParameterGenerator(3);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(formId, String.class);
      pg.put(displayType, String.class);
      pg.put(gffType, String.class);
      list = (List)ejbProxy.invoke("loadCheckFieldListByDisplayType", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to loadCheckFieldListByDisplayType  :" + e.getMessage());
    } 
    return list;
  }
  
  public String saveBookMarks(List fieldControlList, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "0";
    try {
      EJBProxy ejbProxy = new EJBProxy("GovCustomEJB", "GovCustomEJBLocal", GovCustomEJBHome.class);
      pg.put(fieldControlList, List.class);
      pg.put(domainId, String.class);
      result = (String)ejbProxy.invoke("saveBookMarks", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to saveBookMarks  :" + e.getMessage());
    } 
    return result;
  }
  
  private static Logger logger = Logger.getLogger(ReceiveFileBD.class.getName());
}
