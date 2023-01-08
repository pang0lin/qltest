package com.js.oa.routine.boardroom.service;

import com.js.oa.routine.boardroom.bean.EquipmentEJBHome;
import com.js.oa.routine.boardroom.po.EquipmentApplyPO;
import com.js.oa.routine.boardroom.po.EquipmentPO;
import com.js.oa.routine.boardroom.po.EquipmentTypePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class EquipmentBD {
  private static Logger logger = Logger.getLogger(EquipmentBD.class.getName());
  
  public boolean addEquipmentType(EquipmentTypePO equipmentTypePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentTypePO, EquipmentTypePO.class);
      ejbProxy.invoke("addEquipmentType", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addEquipmentType BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteEquipmentType(Long equipmentSortId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentSortId, Long.class);
      result = ((Boolean)ejbProxy.invoke("deleteEquipmentType", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("deleteEquipmentType BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchEquipmentType(String equipmentSortIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentSortIds, String.class);
      result = ((Boolean)ejbProxy.invoke("deleteBatchEquipmentType", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("deleteBatchEquipmentType BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public EquipmentTypePO selectEquipmentType(Long equipmentSortId) {
    EquipmentTypePO equipmentTypePO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentSortId, Long.class);
      equipmentTypePO = (EquipmentTypePO)ejbProxy.invoke(
          "selectEquipmentType", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEquipmentType BD Exception:" + 
          ex.getMessage());
    } finally {}
    return equipmentTypePO;
  }
  
  public boolean modiEquipmentType(EquipmentTypePO equipmentTypePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentTypePO, EquipmentTypePO.class);
      ejbProxy.invoke("modiEquipmentType", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("modiEquipmentType BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectEquipmentType(String domainId) {
    List equipmentTypeList = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(domainId, String.class);
      equipmentTypeList = (List)ejbProxy.invoke("selectEquipmentType", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEquipmentType BD Exception:" + 
          ex.getMessage());
    } finally {}
    return equipmentTypeList;
  }
  
  public boolean addEquipment(EquipmentPO equipmentPO, Long equipmentSortId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentPO, EquipmentPO.class);
      pg.put(equipmentSortId, "Long");
      result = ((Boolean)ejbProxy.invoke("addEquipment", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("addEquipment BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteEquipment(Long equipmentId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentId, Long.class);
      ejbProxy.invoke("deleteEquipment", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteEquipment BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchEquipment(String equipmentIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentIds, String.class);
      ejbProxy.invoke("deleteBatchEquipment", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchEquipment BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public EquipmentPO selectEquipment(Long equipmentId) {
    EquipmentPO equipmentPO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentId, Long.class);
      equipmentPO = (EquipmentPO)ejbProxy.invoke("selectEquipment", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEquipment BD Exception:" + 
          ex.getMessage());
    } finally {}
    return equipmentPO;
  }
  
  public boolean modiEquipment(EquipmentPO equipmentPO, Long equipmentSortId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentPO, EquipmentPO.class);
      pg.put(equipmentSortId, Long.class);
      result = ((Boolean)ejbProxy.invoke("modiEquipment", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modiEquipment BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public Long addEquipmentApply(EquipmentApplyPO equipmentApplyPO, Long equipmentId) {
    Long result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentApplyPO, EquipmentApplyPO.class);
      pg.put(equipmentId, "Long");
      result = (Long)ejbProxy.invoke("addEquipmentApply", 
          pg.getParameters());
    } catch (Exception ex) {
      result = new Long("-1");
      System.out.println("addEquipmentApply BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public EquipmentApplyPO selectEquipmentApply(Long equipmentApplyId) {
    EquipmentApplyPO equipmentApplyPO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentApplyId, Long.class);
      equipmentApplyPO = (EquipmentApplyPO)ejbProxy.invoke(
          "selectEquipmentApply", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEquipmentApply BD Exception:" + 
          ex.getMessage());
    } finally {}
    return equipmentApplyPO;
  }
  
  public boolean modiEquipmentApply(EquipmentApplyPO equipmentApplyPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentApplyPO, EquipmentApplyPO.class);
      ejbProxy.invoke("modiEquipmentApply", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("modiEquipmentApply BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteEquipmentApply(Long equipmentApplyId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentApplyId, Long.class);
      ejbProxy.invoke("deleteEquipmentApply", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteEquipmentApply BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean restituteEquipmentApply(Long equipmentApplyId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(equipmentApplyId, Long.class);
      ejbProxy.invoke("restituteEquipmentApply", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("restituteEquipmentApply BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean isRepeatName(String from, String where) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(from, String.class);
      pg.put(where, String.class);
      Boolean b = (Boolean)ejbProxy.invoke("isRepeatName", 
          pg.getParameters());
      result = b.booleanValue();
    } catch (Exception ex) {
      System.out.println("isRepeatNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String[] getEquipmentInfo(String voitureId, String searchDate) {
    String[] boardRoomPlace = new String[48];
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitureId, String.class);
      pg.put(searchDate, String.class);
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      boardRoomPlace = (String[])ejbProxy.invoke("getEquipmentInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return boardRoomPlace;
  }
  
  public String maintenanceEquipment(String wherePara) {
    String maintenanceBoardRoomIds = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EquipmentEJB", 
          "EquipmentEJBLocal", EquipmentEJBHome.class);
      pg.put(wherePara, String.class);
      maintenanceBoardRoomIds = (String)ejbProxy.invoke(
          "maintenanceEquipment", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("maintenanceEquipment BD Exception:" + 
          ex.getMessage());
    } finally {}
    return maintenanceBoardRoomIds;
  }
}
