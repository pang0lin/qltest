package com.js.doc.doc.service;

import com.js.doc.doc.bean.ReceivedocumentEJBHome;
import com.js.doc.doc.po.ReceiveBaseInfoPO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ReceivedocumentBD {
  private static Logger logger = Logger.getLogger(SenddocumentBD.class.getName());
  
  public Object[] getReceivedocumentBaseInfo() {
    ParameterGenerator pg = new ParameterGenerator(1);
    Object[] result = (Object[])null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put("1", String.class);
      result = (Object[])ejbProxy.invoke("getReceivedocumentBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getReceivedocumentBaseInfo:" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveReceiveBaseInfo(ReceiveBaseInfoPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(po, ReceiveBaseInfoPO.class);
      result = (Long)ejbProxy.invoke("saveReceiveBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save saveReceiveBaseInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateReceiveBaseInfo(ReceiveBaseInfoPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(po, ReceiveBaseInfoPO.class);
      result = (String)ejbProxy.invoke("updateReceiveBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update updateReceiveBaseInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveRecSeqInfo(ReceiveFileSeqPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(po, ReceiveFileSeqPO.class);
      result = (Long)ejbProxy.invoke("saveRecSeqInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error  saveRecSeqInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public ReceiveFileSeqPO loadRecSeqPO(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    ReceiveFileSeqPO po = new ReceiveFileSeqPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(editId, String.class);
      po = (ReceiveFileSeqPO)ejbProxy.invoke("loadRecSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error  loadRecSeqPO :" + e.getMessage());
    } 
    return po;
  }
  
  public String updateRecSeqPO(ReceiveFileSeqPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(po, ReceiveFileSeqPO.class);
      result = (String)ejbProxy.invoke("updateRecSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update updateRecSeqPO :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleteRecSeqPO(String Sqlstr) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(Sqlstr, String.class);
      result = (String)ejbProxy.invoke("deleteRecSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error  deleteRecSeqPO :" + e.getMessage());
    } 
    return result;
  }
  
  public List getRecSeqListByProceId(String provcessId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(provcessId, String.class);
      list = (List)ejbProxy.invoke("getRecSeqListByProceId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error  getRecSeqListByProceId :" + e.getMessage());
    } 
    return list;
  }
  
  public List getRecSeqListByProceId(String provcessId, String where) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(provcessId, String.class);
      pg.put(where, String.class);
      list = (List)ejbProxy.invoke("getRecSeqListByProceId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error  getRecSeqListByProceId :" + e.getMessage());
    } 
    return list;
  }
  
  public List getSeqPoListBySeqClass(String seqType) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "ReceivedocumentEJB", 
          "ReceivedocumentEJBLocal", ReceivedocumentEJBHome.class);
      pg.put(seqType, String.class);
      list = (List)ejbProxy.invoke("getSeqPoListBySeqClass", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error  getRecSeqListByProceId :" + e.getMessage());
    } 
    return list;
  }
}
