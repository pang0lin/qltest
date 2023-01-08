package com.js.oa.routine.officeroom.service;

import com.js.oa.jsflow.po.WFWorkPO;
import com.js.oa.routine.officeroom.bean.OfficeRoomEJBHome;
import com.js.oa.routine.officeroom.po.OfficeBuildPO;
import com.js.oa.routine.officeroom.po.OfficePO;
import com.js.oa.routine.officeroom.po.OfficeUsePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class OfficeRoomBD {
  private static Logger logger = Logger.getLogger(OfficeRoomBD.class.getName());
  
  public Boolean save(OfficeBuildPO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, OfficeBuildPO.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD save information :" + e.getMessage());
    } 
    return success;
  }
  
  public OfficeBuildPO load(Long id) {
    OfficeBuildPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      po = (OfficeBuildPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD load information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean update(OfficeBuildPO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, OfficeBuildPO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD update information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean delete(Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD delete information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean officeSave(OfficePO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, OfficePO.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      success = (Boolean)ejbProxy.invoke("officeSave", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD save information :" + e.getMessage());
    } 
    return success;
  }
  
  public OfficePO officeLoad(Long id) {
    OfficePO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      po = (OfficePO)ejbProxy.invoke("officeLoad", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD load information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean officeUpdate(OfficePO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, OfficePO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("officeUpdate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeUpdate information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean officeDelete(Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("officeDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeDelete information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public List list(String mode, Long domainID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(mode, String.class);
      pg.put(domainID, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB list information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public List getTitleList(String id, String domainID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(domainID, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("getTitleList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getTitleList information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public List getDirectionList(String id, String domainID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(domainID, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("getDirectionList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getDirectionList information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public String getBuildname(String id) {
    List list = null;
    String buildname = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      buildname = (String)ejbProxy.invoke("getBuildname", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getBuildname information :" + 
          e.getMessage());
    } 
    return buildname;
  }
  
  public String getBuildAdminId(String id) {
    List list = null;
    String buildAdminId = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      buildAdminId = (String)ejbProxy.invoke("getBuildAdminId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getBuildAdminId information :" + 
          e.getMessage());
    } 
    return buildAdminId;
  }
  
  public List getListBuild(String buildId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(buildId, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("getListBuild", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB list information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public List getListBuildNumber(String buildId, String buildNumber, String buildClassWhere) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(buildId, String.class);
      pg.put(buildNumber, String.class);
      pg.put(buildClassWhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("getListBuildNumber", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getListBuildNumber information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public List getOfficeUses(String buildId, String buildNumber, String officeNumber) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(buildId, String.class);
      pg.put(buildNumber, String.class);
      pg.put(officeNumber, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("getOfficeUses", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getOfficeUses information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public Boolean officeUseSave(OfficeUsePO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, OfficeUsePO.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      success = (Boolean)ejbProxy.invoke("officeUseSave", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeUseSave information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean saveWorkflow(WFWorkPO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, WFWorkPO.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      success = (Boolean)ejbProxy.invoke("saveWorkflow", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD saveWorkflow information :" + e.getMessage());
    } 
    return success;
  }
  
  public OfficeUsePO officeUseLoad(Long id) {
    OfficeUsePO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      po = (OfficeUsePO)ejbProxy.invoke("officeUseLoad", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeUseLoad information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean officeUseUpdate(OfficeUsePO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, OfficeUsePO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("officeUseUpdate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeUseUpdate information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean officeUseDelete(Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("officeUseDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeUseDelete information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean officeUpdateIsUse(String id, String flag, OfficeUsePO po) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(id, String.class);
      pg.put(flag, String.class);
      pg.put(po, OfficeUsePO.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("officeUpdateIsUse", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomBD officeUpdateIsUse information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String getOfficeAdminId(String buildId, String buildNumber, String officeNumber) {
    String officeAdminId = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(buildId, String.class);
      pg.put(buildNumber, String.class);
      pg.put(officeNumber, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      officeAdminId = (String)ejbProxy.invoke("getOfficeAdminId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getOfficeAdminId information :" + 
          e.getMessage());
    } 
    return officeAdminId;
  }
  
  public List getOfficeIsTrue(String buildId, String buildNumber, String officeNumber) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(buildId, String.class);
      pg.put(buildNumber, String.class);
      pg.put(officeNumber, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      list = (List)ejbProxy.invoke("getOfficeIsTrue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getOfficeIsTrue information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public String getOfficeTotalStation(String buildId, String buildNumber, String officeNumber, String applayUseId) {
    String OfficeTotalStation = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(buildId, String.class);
      pg.put(buildNumber, String.class);
      pg.put(officeNumber, String.class);
      pg.put(applayUseId, String.class);
      EJBProxy ejbProxy = new EJBProxy("OfficeRoomEJB", 
          "OfficeRoomEJBLocal", OfficeRoomEJBHome.class);
      OfficeTotalStation = (String)ejbProxy.invoke("getOfficeTotalStation", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  OfficeRoomEJB getOfficeTotalStation information :" + 
          e.getMessage());
    } 
    return OfficeTotalStation;
  }
}
