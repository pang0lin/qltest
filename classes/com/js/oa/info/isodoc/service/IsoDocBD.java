package com.js.oa.info.isodoc.service;

import com.js.oa.info.infomanager.bean.InformationEJBHome;
import com.js.oa.info.isodoc.po.IsoBorrowUserPO;
import com.js.oa.info.isodoc.po.IsoCommentPO;
import com.js.oa.info.isodoc.po.IsoDeallogPO;
import com.js.oa.info.isodoc.po.IsoPaperPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class IsoDocBD {
  private static Logger logger = Logger.getLogger(IsoDocBD.class.getName());
  
  public Long saveIsoPaperPO(IsoPaperPO po) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, IsoPaperPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Long)ejbProxy.invoke("saveIsoPaperPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return result;
  }
  
  public String setPaperPOStatus(String id, String status) {
    String result = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(status, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("setPaperPOStatus", 
          pg.getParameters());
    } catch (Exception e) {
      result = "-1";
    } 
    return result;
  }
  
  public String deletePaperPO(String id) {
    String result = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("deletePaperPO", pg.getParameters());
    } catch (Exception e) {
      result = "-1";
    } 
    return result;
  }
  
  public Long saveBorrowPO(IsoBorrowUserPO po) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, IsoBorrowUserPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Long)ejbProxy.invoke("saveBorrowPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long updateBorrowPO(IsoBorrowUserPO po) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, IsoBorrowUserPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Long)ejbProxy.invoke("updateBorrowPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save updateBorrowPO :" + e.getMessage());
    } 
    return result;
  }
  
  public String setBorrowStatus(String id, String status) {
    String result = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(status, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("setBorrowStatus", 
          pg.getParameters());
    } catch (Exception e) {
      result = "-1";
      logger.error("error to save setBorrowStatus :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleteBorrow(String ids) {
    String result = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("deleteBorrow", pg.getParameters());
    } catch (Exception e) {
      result = "-1";
      logger.error("error to save deleteBorrow :" + e.getMessage());
    } 
    return result;
  }
  
  public IsoBorrowUserPO loadBorrowUserPO(String id) {
    IsoBorrowUserPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      po = (IsoBorrowUserPO)ejbProxy.invoke("loadBorrowUserPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadBorrowUserPO :" + e.getMessage());
    } 
    return po;
  }
  
  public List findIdsFromBorrow(String informationId, String informationOldId, String userId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationId, String.class);
      pg.put(informationOldId, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("findIdsFromBorrow", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to findIdsFromBorrow :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean setInformationStatus(String informationId, String informationStatus, String isoDocStatus) {
    boolean result = true;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationId, String.class);
      pg.put(informationStatus, String.class);
      pg.put(isoDocStatus, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setInformationStatus", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to setInformationStatus :" + e.getMessage());
    } 
    return result;
  }
  
  public void updateBigVersion(String informationId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("updateBigVersion", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateBigVersion :" + e.getMessage());
    } 
  }
  
  public List getInforByVersion(String informationId, String version) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(version, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getInforByVersion", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getInforByVersion :" + e.getMessage());
    } 
    return list;
  }
  
  public String getHisModiNum(String informationId) {
    String result = "1";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("getHisModiNum", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getHisModiNum :" + e.getMessage());
    } 
    return result;
  }
  
  public List getCanVindicate_ISO(String where, String userId, String orgId, String domainId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(where, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getCanVindicate_ISO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanVindicate_ISO :" + e.getMessage());
    } 
    return list;
  }
  
  public String updatePaperPO(String id, String[] arg) {
    String result = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(arg, String[].class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("updatePaperPO", pg.getParameters());
    } catch (Exception e) {
      result = "-1";
      e.printStackTrace();
    } 
    return result;
  }
  
  public IsoPaperPO loadIsoPaperPO(String id) {
    IsoPaperPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      po = (IsoPaperPO)ejbProxy.invoke("loadIsoPaperPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadIsoPaperPO :" + e.getMessage());
    } 
    return po;
  }
  
  public Long saveIsoCommentPO(IsoCommentPO po) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, IsoCommentPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Long)ejbProxy.invoke("saveIsoCommentPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveIsoCommentPO:" + e.getMessage());
    } 
    return result;
  }
  
  public List getIsoCommentList(String informationId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getIsoCommentList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIsoCommentList :" + e.getMessage());
    } 
    return list;
  }
  
  public List getCommentList(String recordId, String tableId, String processId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getCommentList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentList :" + e.getMessage());
    } 
    return list;
  }
  
  public void TransferUserId(String informationId, String userId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "TransferUserId", InformationEJBHome.class);
      ejbProxy.invoke("TransferUserId", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to TransferUserId :" + e.getMessage());
    } 
  }
  
  public Long saveIsoDeallogPO(IsoDeallogPO po) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, IsoDeallogPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Long)ejbProxy.invoke("saveIsoDeallogPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveIsoDeallogPO:" + e.getMessage());
    } 
    return result;
  }
  
  public List getIsoDeallogList(String informationId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getIsoDeallogList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIsoDeallogList :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean setInformationStatus(String informationId, String informationStatus, String isoDocStatus, String orgName, String userName) {
    boolean result = true;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(informationId, String.class);
      pg.put(informationStatus, String.class);
      pg.put(isoDocStatus, String.class);
      pg.put(orgName, String.class);
      pg.put(userName, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setInformationStatus", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to setInformationStatus :" + e.getMessage());
    } 
    return result;
  }
}
