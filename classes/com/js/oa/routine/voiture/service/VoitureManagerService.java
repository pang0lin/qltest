package com.js.oa.routine.voiture.service;

import com.js.oa.routine.voiture.bean.VoitureEJBBean;
import com.js.oa.routine.voiture.bean.VoitureEJBHome;
import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureAuditingPO;
import com.js.oa.routine.voiture.po.VoitureCancelPO;
import com.js.oa.routine.voiture.po.VoitureFeedbackPO;
import com.js.oa.routine.voiture.po.VoitureMaintainPO;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.oa.routine.voiture.po.VoitureSendPO;
import com.js.oa.routine.voiture.po.VoitureTypePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class VoitureManagerService {
  private static Logger logger = Logger.getLogger(VoitureManagerService.class
      .getName());
  
  public Integer saveVoitureType(VoitureTypePO voitureTypePO) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureTypePO, VoitureTypePO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("saveVoitureType", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureType information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public List listVoitureType(String domainId) {
    Integer result = new Integer(1);
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      list = (List)ejbProxy.invoke("listVoitureType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return list;
  }
  
  public VoitureTypePO loadVoitureType(String id) {
    VoitureTypePO vtpo = new VoitureTypePO();
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vtpo = (VoitureTypePO)ejbProxy.invoke("loadVoitureType", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vtpo;
  }
  
  public boolean delVoitureType(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("delVoitureType", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to delVoitureType information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public Integer updateVoitureType(VoitureTypePO voitueTypePO, String typeId) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitueTypePO, VoitureTypePO.class);
      pg.put(typeId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateVoitureType", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delVoitureType information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public Integer updateVoitureApply(VoitureApplyPO voitueApplyPO, String applyId) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitueApplyPO, VoitureApplyPO.class);
      pg.put(applyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateVoitureApply", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateVoitureApply information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public Integer saveVoiture(VoiturePO voiturePO) {
    Integer result = new Integer(2);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voiturePO, VoiturePO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("saveVoiture", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoiture information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public VoiturePO loadVoiture(String id) {
    VoiturePO vpo = new VoiturePO();
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vpo = (VoiturePO)ejbProxy.invoke("loadVoiture", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadVoiture information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vpo;
  }
  
  public boolean delVoiture(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("delVoiture", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to delVoiture information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public boolean checkVoiture(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("checkVoiture", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to checkVoiture information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public Integer updateVoiture(VoiturePO voituePO, String id) {
    Integer result = new Integer(2);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voituePO, VoiturePO.class);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateVoiture", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delVoiture information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public Integer saveMessage(String userId, String userName, String messageTitle, String messageType, String dataId, String messageUrl) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(messageTitle, String.class);
      pg.put(messageType, String.class);
      pg.put(dataId, String.class);
      pg.put(messageUrl, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      ejbProxy.invoke("saveMessage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveMessage information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public Long saveVoitureApply(VoitureApplyPO voitureApplyPO) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureApplyPO, VoitureApplyPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Long)ejbProxy.invoke("saveVoitureApply", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureApply information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public boolean delVoitureApply(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("delVoitureApply", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to delVoitureApply information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public boolean delVoitureMaintain(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("delVoitureMaintain", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to delVoitureMaintain information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public Integer saveVoitureMaintain(VoitureMaintainPO voitureMaintainPO) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureMaintainPO, VoitureMaintainPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("saveVoitureMaintain", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureMaintain information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public boolean auditingApply(String id, String status) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(status, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("auditingApply", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to auditingApply information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public boolean auditingApply(String id, VoitureApplyPO voitureApplyPO, String status) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(id, String.class);
      pg.put(voitureApplyPO, VoitureApplyPO.class);
      pg.put(status, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("auditingApply", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to auditingApply information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public VoitureMaintainPO loadVoitureMaintain(String id) {
    VoitureMaintainPO vmpo = new VoitureMaintainPO();
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vmpo = (VoitureMaintainPO)ejbProxy.invoke("VoitureMaintainPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vmpo;
  }
  
  public String getVoitureSendNumberMax() {
    VoitureMaintainPO vmpo = new VoitureMaintainPO();
    String voitureSendNumber = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(0);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      voitureSendNumber = (String)ejbProxy.invoke(
          "getVoitureSendNumberMax", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return voitureSendNumber;
  }
  
  public Integer saveVoitureSend(VoitureSendPO voitureSendPO) {
    Integer result = new Integer(2);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureSendPO, VoitureSendPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("saveVoitureSend", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureApply information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public VoitureApplyPO loadVoitureApply(String id) {
    VoitureApplyPO vapo = new VoitureApplyPO();
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vapo = (VoitureApplyPO)ejbProxy.invoke("loadVoitureApply", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadVoiture information :" + e.getMessage());
      e.printStackTrace();
    } 
    return vapo;
  }
  
  public List listVoiture(String id, String domainId, String curUserName, String orgName, String userId, String orgIdString) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(id, String.class);
      pg.put(domainId, String.class);
      pg.put(curUserName, String.class);
      pg.put(orgName, String.class);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      list = (List)ejbProxy.invoke("listVoiture", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return list;
  }
  
  public VoitureSendPO loadVoitureSend(String id) {
    VoitureSendPO vspo = new VoitureSendPO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vspo = (VoitureSendPO)ejbProxy.invoke("loadVoitureSend", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vspo;
  }
  
  public VoitureSendPO getVoitureSendPO(String applyId) {
    VoitureSendPO vspo = new VoitureSendPO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(applyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vspo = (VoitureSendPO)ejbProxy.invoke("getVoitureSendPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vspo;
  }
  
  public String[] getVoitureInfo(String voitureId, String searchDate) {
    String[] voiturePlace = new String[288];
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitureId, String.class);
      pg.put(searchDate, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      voiturePlace = (String[])ejbProxy.invoke("getVoitureInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return voiturePlace;
  }
  
  public VoiturePO getVoiturePO(String id) {
    VoiturePO vpo = new VoiturePO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vpo = (VoiturePO)ejbProxy.invoke("getVoiturePO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vpo;
  }
  
  public VoitureApplyPO getVoitureApplyPO(String id) {
    VoitureApplyPO vapo = new VoitureApplyPO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vapo = (VoitureApplyPO)ejbProxy.invoke("getVoitureApplyPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vapo;
  }
  
  public Long saveVoitureCancel(VoitureCancelPO voitureCancelPO) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureCancelPO, VoitureCancelPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Long)ejbProxy.invoke("saveVoitureCancel", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureCancel information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public Long saveVoitureAuditing(VoitureAuditingPO voitureAuditingPO) {
    Long result = new Long(-1L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureAuditingPO, VoitureAuditingPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Long)ejbProxy.invoke("saveVoitureAuditing", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureAuditing information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public boolean isVoitureAuditingPO(String applyId) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(applyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("isVoitureAuditingPO", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public VoitureAuditingPO getVoitureAuditingPO(String applyId) {
    VoitureAuditingPO vapo = new VoitureAuditingPO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(applyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vapo = (VoitureAuditingPO)ejbProxy.invoke("getVoitureAuditingPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return vapo;
  }
  
  public Integer updateVoitureAudting(VoitureAuditingPO voitureAuditingPO, String auditingId) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitureAuditingPO, VoitureAuditingPO.class);
      pg.put(auditingId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateVoitureAudting", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateVoitureAudting information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public boolean delVoitureCancel(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("delVoitureCancel", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to delVoitureCancel information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public boolean delVoitureCancelByApplyId(String id) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("delVoitureCancelByApplyId", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to delVoitureCancelByApplyId information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public boolean isVoitureSendPO(String applyId) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(applyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("isVoitureAuditingPO", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return bl;
  }
  
  public Integer updateVoitureSend(VoitureSendPO voitueSendPO, String sendId) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitueSendPO, VoitureSendPO.class);
      pg.put(sendId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateVoitureSend", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateVoitureSend information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return result;
  }
  
  public String getVoitureUseRange(String userName, String orgName, String userId, String orgIdString) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      ret = (String)ejbProxy.invoke("getVoitureUseRange", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getVoitureUseRange information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public List getVoitureApplyUnderDate(String date, String voitureIds) {
    List ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(date, String.class);
      pg.put(voitureIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      ret = (List)ejbProxy.invoke("getVoitureApplyUnderDate", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getVoitureApplyUnderDate information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public List getVaIdHasStatusInCurrent(String date, String domainId) {
    List ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(date, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      ret = (List)ejbProxy.invoke("getVaIdHasStatusInCurrent", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getVaIdHasStatusInCurrent information :" + 
          e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public VoitureFeedbackPO getVoitureFeedbackPO(String applyId) {
    VoitureFeedbackPO vapo = new VoitureFeedbackPO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(applyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      vapo = (VoitureFeedbackPO)ejbProxy.invoke("getVoitureFeedbackPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load getVoitureFeedbackPO :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return vapo;
  }
  
  public String updateVoitureFeedbackPO(VoitureFeedbackPO po) {
    String ur = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, VoitureFeedbackPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      ur = (String)ejbProxy.invoke("updateVoitureFeedbackPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load updateVoitureFeedbackPO :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return ur;
  }
  
  public Long saveVoitureFeedbackPO(VoitureFeedbackPO po) {
    Long sr = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, VoitureFeedbackPO.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      sr = (Long)ejbProxy.invoke("saveVoitureFeedbackPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load saveVoitureFeedbackPO :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return sr;
  }
  
  public List getFeedbackStat(String sqlStr) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(sqlStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      list = (List)ejbProxy.invoke("getFeedbackStat", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load getFeedbackStat :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return list;
  }
  
  public List getFeedbackStat2(String sqlStr, Date bd, Date ed) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(sqlStr, String.class);
      pg.put(bd, Date.class);
      pg.put(ed, Date.class);
      EJBProxy ejbProxy = new EJBProxy("VoitureEJB", 
          "VoitureEJBLocal", VoitureEJBHome.class);
      list = (List)ejbProxy.invoke("getFeedbackStat2", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load getFeedbackStat2 :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return list;
  }
  
  public String isImpropriateTime(VoitureApplyPO voitureApplyPO, Long voitureId) throws HibernateException {
    String isImpropriateTime = "0";
    VoitureEJBBean voitureEJBBean = new VoitureEJBBean();
    isImpropriateTime = voitureEJBBean.isImpropriateTime(voitureApplyPO, voitureId);
    return isImpropriateTime;
  }
}
