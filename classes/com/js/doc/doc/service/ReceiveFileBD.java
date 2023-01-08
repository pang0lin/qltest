package com.js.doc.doc.service;

import com.js.doc.doc.bean.ReceiveFileEJBHome;
import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveAssociatePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ReceiveFileBD {
  private static Logger logger = Logger.getLogger(ReceiveFileBD.class.getName());
  
  public String initNumber(String receiveFileType, String year, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileType, String.class);
      pg.put(year, String.class);
      pg.put(domainId, String.class);
      result = (String)ejbProxy.invoke("initNumber", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to initNumber information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long save(GovReceiveFilePO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(po, GovReceiveFilePO.class);
      result = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return result;
  }
  
  public GovReceiveFilePO load(String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovReceiveFilePO result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileId, String.class);
      result = (GovReceiveFilePO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
    } 
    return result;
  }
  
  public GovReceiveFilePO get(String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovReceiveFilePO result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileId, String.class);
      result = (GovReceiveFilePO)ejbProxy.invoke("get", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long update(GovReceiveFilePO po, String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(po, GovReceiveFilePO.class);
      pg.put(receiveFileId, String.class);
      result = (Long)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long completeReceiveFile(String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileId, String.class);
      result = (Long)ejbProxy.invoke("completeReceiveFile", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to completeReceiveFile information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getDossierInfo(String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileId, String.class);
      result = (Map)ejbProxy.invoke("getDossierInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDossierInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean isPigeonholed(String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Boolean result = Boolean.valueOf(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileId, String.class);
      result = (Boolean)ejbProxy.invoke("isPigeonholed", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to isPigeonholed information :" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public Integer setPigeonholed(String receiveFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = Integer.valueOf("-1");
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveFileId, String.class);
      result = (Integer)ejbProxy.invoke("setPigeonholed", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setPigeonholed information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer delete(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = Integer.valueOf("-1");
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(id, String.class);
      result = (Integer)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer delete2(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = Integer.valueOf("-1");
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(id, String.class);
      result = (Integer)ejbProxy.invoke("delete2", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveReceiveAssociate(ReceiveAssociatePO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", 
          "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(po, ReceiveAssociatePO.class);
      result = (Long)ejbProxy.invoke("saveReceiveAssociate", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveReceiveAssociate information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public int getReceiveAssociateNum(String receiveId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    int result = 0;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveId, String.class);
      result = ((Integer)ejbProxy.invoke("getReceiveAssociateNum", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to getReceiveAssociateNum information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getRecieveAssociateList(String receiveId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileEJB", "ReceiveFileEJBLocal", ReceiveFileEJBHome.class);
      pg.put(receiveId, String.class);
      list = (List)ejbProxy.invoke("getRecieveAssociateList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRecieveAssociateList information :" + e.getMessage());
    } 
    return list;
  }
}
