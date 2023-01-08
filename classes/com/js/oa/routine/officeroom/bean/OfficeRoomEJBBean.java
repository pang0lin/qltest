package com.js.oa.routine.officeroom.bean;

import com.js.oa.jsflow.po.WFWorkPO;
import com.js.oa.routine.officeroom.po.OfficeBuildPO;
import com.js.oa.routine.officeroom.po.OfficePO;
import com.js.oa.routine.officeroom.po.OfficeUsePO;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;

public class OfficeRoomEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(OfficeBuildPO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public OfficeBuildPO load(Long id) throws Exception {
    OfficeBuildPO po = new OfficeBuildPO();
    try {
      begin();
      po = (OfficeBuildPO)this.session.load(OfficeBuildPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(OfficeBuildPO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      OfficeBuildPO updatePO = (OfficeBuildPO)this.session.load(OfficeBuildPO.class, id);
      updatePO.setBuildname(po.getBuildname());
      updatePO.setBuildidaccount(po.getBuildidaccount());
      updatePO.setBuildreadname(po.getBuildreadname());
      updatePO.setBz(po.getBz());
      updatePO.setBuildAdminId(po.getBuildAdminId());
      updatePO.setBuildAdminName(po.getBuildAdminName());
      updatePO.setBuildreader(po.getBuildreader());
      updatePO.setBuildreadgroup(po.getBuildreadgroup());
      updatePO.setBuildreadorg(po.getBuildreadorg());
      this.session.update(updatePO);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean delete(Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      OfficeBuildPO po = (OfficeBuildPO)this.session.load(OfficeBuildPO.class, id);
      this.session.delete(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean officeSave(OfficePO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public OfficePO officeLoad(Long id) throws Exception {
    OfficePO po = new OfficePO();
    try {
      begin();
      po = (OfficePO)this.session.load(OfficePO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean officeUpdate(OfficePO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      OfficePO updatePO = (OfficePO)this.session.load(OfficePO.class, id);
      updatePO.setOfficename(po.getOfficename());
      updatePO.setOfficenumber(po.getOfficenumber());
      updatePO.setBuidId(po.getBuidId());
      updatePO.setBuildname(po.getBuildname());
      updatePO.setBuidnumber(po.getBuidnumber());
      updatePO.setOfficearea(po.getOfficearea());
      updatePO.setOfficefaces(po.getOfficefaces());
      updatePO.setOfficereadname(po.getOfficereadname());
      updatePO.setBz(po.getBz());
      updatePO.setOfficeAdminId(po.getOfficeAdminId());
      updatePO.setOfficeAdminName(po.getOfficeAdminName());
      updatePO.setOfficestation(po.getOfficestation());
      updatePO.setOfficereader(po.getOfficereader());
      updatePO.setOfficereadgroup(po.getOfficereadgroup());
      updatePO.setOfficereadorg(po.getOfficereadorg());
      this.session.update(updatePO);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean officeDelete(Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      OfficePO po = (OfficePO)this.session.load(OfficePO.class, id);
      this.session.delete(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List list(String mode, Long domainID) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.buidId,po.buildname,po.buildidaccount from  com.js.oa.routine.officeroom.po.OfficeBuildPO po where 1=1 and po.buidId<>1 ")
        
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getBuildname(String id) throws Exception {
    String buildname = "";
    List<String> list = null;
    try {
      begin();
      list = this.session.createQuery("select po.buildname from  com.js.oa.routine.officeroom.po.OfficeBuildPO po where 1=1 and po.buidId=" + 
          id)
        .list();
      if (list != null && list.size() > 0)
        buildname = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return buildname;
  }
  
  public String getBuildAdminId(String id) throws Exception {
    String buildAdminId = "";
    List<String> list = null;
    try {
      begin();
      list = this.session.createQuery("select po.buildAdminId from  com.js.oa.routine.officeroom.po.OfficeBuildPO po where 1=1 and po.buidId=" + 
          id)
        .list();
      if (list != null && list.size() > 0)
        buildAdminId = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return buildAdminId;
  }
  
  public List getListBuild(String buildId) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.buidId,po.buildname,po.buildidaccount from  com.js.oa.routine.officeroom.po.OfficeBuildPO po where 1=1 and po.buidId=" + 
          buildId)
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getListBuildNumber(String buildId, String buildNumber, String buildClassWhere) throws Exception {
    List list = null;
    String databaseType = 
      SystemCommon.getDatabaseType();
    try {
      begin();
      String sqlString = "select po.officeId,po.officenumber,po.buidId from  com.js.oa.routine.officeroom.po.OfficePO po where 1=1 ";
      if (buildNumber != null && !"".equals(buildNumber))
        sqlString = String.valueOf(sqlString) + "and po.buidnumber='" + buildNumber.trim() + "'"; 
      if (buildId != null && !"".equals(buildId))
        sqlString = String.valueOf(sqlString) + "and  po.buidId=" + buildId.trim() + " "; 
      if (buildClassWhere != null && !"".equals(buildClassWhere))
        if (databaseType.indexOf("mysql") >= 0) {
          sqlString = String.valueOf(sqlString) + "and (" + buildClassWhere + " or ((po.officereader ='' or po.officereader is null) and po.officereadorg is null and po.officereadgroup is null ))";
        } else {
          sqlString = String.valueOf(sqlString) + "and (" + buildClassWhere + " or (po.officereader is null and po.officereadorg is null and po.officereadgroup is null ))";
        }  
      sqlString = String.valueOf(sqlString) + " order by po.officenumber";
      list = this.session.createQuery(sqlString).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getOfficeUses(String buildId, String buildNumber, String officeNumber) throws Exception {
    List list = null;
    try {
      begin();
      String sqlString = "select po.applayId,po.applayNumber,po.applayBuildId from  com.js.oa.routine.officeroom.po.OfficeUsePO po where 1=1 ";
      if (buildNumber != null && !"".equals(buildNumber))
        sqlString = String.valueOf(sqlString) + "and po.applayBuildNumber='" + buildNumber.trim() + "'"; 
      if (buildId != null && !"".equals(buildId))
        sqlString = String.valueOf(sqlString) + "and  po.applayBuildId='" + buildId.trim() + "' "; 
      if (officeNumber != null && !"".equals(officeNumber))
        sqlString = String.valueOf(sqlString) + "and  po.applayNumber='" + officeNumber.trim() + "' "; 
      sqlString = String.valueOf(sqlString) + " order by po.applayId";
      list = this.session.createQuery(sqlString).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean officeUseSave(OfficeUsePO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public OfficeUsePO officeUseLoad(Long id) throws Exception {
    OfficeUsePO po = new OfficeUsePO();
    try {
      begin();
      po = (OfficeUsePO)this.session.load(OfficeUsePO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean officeUseUpdate(OfficeUsePO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      OfficeUsePO updatePO = (OfficeUsePO)this.session.load(OfficeUsePO.class, id);
      updatePO.setApplayBuildId(po.getApplayBuildId());
      updatePO.setApplayBuildNumber(po.getApplayBuildNumber());
      updatePO.setApplayNumber(po.getApplayNumber());
      updatePO.setApplayClass(po.getApplayClass());
      updatePO.setApplayOrg(po.getApplayOrg());
      updatePO.setApplayReason(po.getApplayReason());
      updatePO.setApplaySex(po.getApplaySex());
      updatePO.setApplayTitle(po.getApplayTitle());
      updatePO.setApplayUserId(po.getApplayUserId());
      updatePO.setApplayUsername(po.getApplayUsername());
      updatePO.setVisitBeginTime(po.getVisitBeginTime());
      updatePO.setVisitEndTime(po.getVisitEndTime());
      updatePO.setVisitName(po.getVisitName());
      updatePO.setVisitOrg(po.getVisitOrg());
      updatePO.setVisitWorkunit(po.getVisitWorkunit());
      updatePO.setBz(po.getBz());
      updatePO.setApplayOrgName(po.getApplayOrgName());
      updatePO.setVisitOrgName(po.getVisitOrgName());
      updatePO.setApplayStation(po.getApplayStation());
      this.session.update(updatePO);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean officeUseDelete(Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      OfficeUsePO po = (OfficeUsePO)this.session.load(OfficeUsePO.class, id);
      this.session.delete(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean officeUpdateIsUse(String id, String flag, OfficeUsePO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    String applayBuildId = "";
    String applayNumber = "";
    String applayBuildNumber = "";
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "update OA_OFFICE_USE set APPLAY_ISUSE='" + flag + "' where APPLAY_ID in (" + id + ")";
      stmt.executeUpdate(sql);
      if (id != null && id.contains(",")) {
        String[] idsStr = id.split(",");
        for (int i = 0; i < idsStr.length; i++) {
          String sqlApplay = "select APPLAY_BUILDID,APPLAY_BUILDNUMBER,APPLAY_NUMBER from OA_OFFICE_USE where APPLAY_ID=" + idsStr[i];
          rSet = stmt.executeQuery(sqlApplay);
          while (rSet.next()) {
            applayBuildId = rSet.getString("APPLAY_BUILDID");
            applayBuildNumber = rSet.getString("APPLAY_BUILDNUMBER");
            applayNumber = rSet.getString("APPLAY_NUMBER");
          } 
          String OfficeTotalStation = getOfficeTotalStation(applayBuildId, applayBuildNumber, applayNumber, "");
          String[] use = OfficeTotalStation.split("&");
          int alreadyUse = Integer.parseInt(use[1].toString());
          int maxUse = Integer.parseInt(use[0].toString());
          String useStatus = "0";
          if (alreadyUse == maxUse) {
            useStatus = "1";
          } else if (alreadyUse > 0 && alreadyUse < maxUse) {
            useStatus = "2";
          } else if (alreadyUse == 0) {
            useStatus = "0";
          } 
          String sqlString = "UPDATE  oa_office SET OFFICEISUSE='" + useStatus + "' " + 
            " WHERE BUIDID=" + po.getApplayBuildId() + " and buidnumber='" + po.getApplayBuildNumber() + "' and officenumber='" + po.getApplayNumber() + "'  ";
          stmt.executeUpdate(sqlString);
        } 
      } else {
        String sqlApplay = "select APPLAY_BUILDID,APPLAY_BUILDNUMBER,APPLAY_NUMBER from OA_OFFICE_USE where APPLAY_ID=" + id;
        rSet = stmt.executeQuery(sqlApplay);
        while (rSet.next()) {
          applayBuildId = rSet.getString("APPLAY_BUILDID");
          applayBuildNumber = rSet.getString("APPLAY_BUILDNUMBER");
          applayNumber = rSet.getString("APPLAY_NUMBER");
        } 
        String OfficeTotalStation = getOfficeTotalStation(applayBuildId, applayBuildNumber, applayNumber, "");
        String[] use = OfficeTotalStation.split("&");
        int alreadyUse = Integer.parseInt(use[1].toString());
        int maxUse = Integer.parseInt(use[0].toString());
        String useStatus = "0";
        if (alreadyUse == maxUse) {
          useStatus = "1";
        } else if (alreadyUse > 0 && alreadyUse < maxUse) {
          useStatus = "2";
        } else if (alreadyUse == 0) {
          useStatus = "0";
        } 
        String sqlString = "UPDATE  oa_office SET OFFICEISUSE='" + useStatus + "' " + 
          " WHERE BUIDID=" + applayBuildId + " and buidnumber='" + applayBuildNumber + "' and officenumber='" + applayNumber + "'  ";
        stmt.executeUpdate(sqlString);
      } 
    } catch (Exception e) {
      if (rSet != null)
        rSet.close(); 
      if (conn != null)
        conn.close(); 
    } finally {
      rSet.close();
      stmt.close();
      conn.close();
    } 
    return ret;
  }
  
  public List getBuildList(HttpServletRequest request) throws Exception {
    HttpSession httpSession = request.getSession();
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    ManagerService managerBD = new ManagerService();
    String buildClassWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "po.buildreader", 
        "po.buildreadorg", 
        "po.buildreadgroup");
    List list = null;
    String userString = "$" + userId + "$";
    try {
      begin();
      String sqlString = "select po.buidId,po.buildname,po.buildparentid,po.buildidaccount,po.buildAdminName ";
      sqlString = String.valueOf(sqlString) + "from  com.js.oa.routine.officeroom.po.OfficeBuildPO po  where  ";
      sqlString = String.valueOf(sqlString) + " po.buidId<>1";
      sqlString = String.valueOf(sqlString) + " and " + buildClassWhere;
      sqlString = String.valueOf(sqlString) + " or po.buildAdminId like '%" + userString + "%'";
      sqlString = String.valueOf(sqlString) + " order by po.buidId";
      list = this.session.createQuery(sqlString).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getOfficeUseMessage(String buildId, String buildNumber, String officeNumber) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.applayId,po.applayBuildId,po.applayBuildNumber,po.applayNumber,po.applayClass,po.applayUsername,po.applayReason from com.js.oa.routine.officeroom.po.OfficeUsePO po where po.applayBuildId='" + 
          
          buildId + "' and po.applayBuildNumber='" + buildNumber + "' and po.applayNumber='" + officeNumber + "' ").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean updateRoom(String buildId, String buildNumber, String officeNumber) throws Exception {
    Boolean ret = Boolean.TRUE;
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    ResultSet rstation = null;
    int station = 0;
    int maxstation = 0;
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "select APPLAY_STATION from OA_OFFICE_USE where (applay_isoverdue =0 OR  applay_isoverdue IS NULL)   and APPLAY_BUILDID=" + 
        buildId + " " + 
        " and APPLAY_BUILDNUMBER='" + buildNumber + "' " + 
        " and APPLAY_NUMBER='" + officeNumber + "' ";
      rSet = stmt.executeQuery(sql);
      while (rSet.next())
        station += Integer.parseInt(rSet.getString(1)); 
      String statinsql = "select OFFICESTATION from oa_office where BUIDID='" + buildId + "'  and BUIDNUMBER='" + buildNumber + "'  and OFFICENUMBER='" + officeNumber + "'  ";
      rstation = stmt.executeQuery(statinsql);
      if (rstation.next())
        maxstation = Integer.parseInt(rstation.getString(1)); 
      String sqlupdate = "";
      if (station > 0 && station < maxstation) {
        sqlupdate = "update OA_OFFICE set OFFICEISUSE='2'  where  BUIDID='" + buildId + "'  and BUIDNUMBER='" + buildNumber + "'  and OFFICENUMBER='" + officeNumber + "' ";
      } else if (station >= maxstation) {
        sqlupdate = "update OA_OFFICE set OFFICEISUSE='1'  where  BUIDID='" + buildId + "'  and BUIDNUMBER='" + buildNumber + "'  and OFFICENUMBER='" + officeNumber + "' ";
      } else {
        sqlupdate = "update OA_OFFICE set OFFICEISUSE='0'  where  BUIDID='" + buildId + "'  and BUIDNUMBER='" + buildNumber + "'  and OFFICENUMBER='" + officeNumber + "' ";
      } 
      stmt.executeUpdate(sqlupdate);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rstation != null)
        rstation.close(); 
      if (rSet != null)
        rSet.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return ret.booleanValue();
  }
  
  public String getOfficeAdminId(String buidId, String buidnumber, String officenumber) throws Exception {
    String officeAdminId = "";
    List<String> list = null;
    try {
      begin();
      list = this.session.createQuery("select po.officeAdminId,po.officeAdminId from  com.js.oa.routine.officeroom.po.OfficePO po where 1=1 and po.buidId=" + 
          
          buidId + " and po.buidnumber='" + buidnumber + "' " + 
          "and po.officenumber='" + officenumber + "' ")
        .list();
      if (list != null && list.size() > 0)
        officeAdminId = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return officeAdminId;
  }
  
  public List getOfficeIsTrue(String buidId, String buidnumber, String officenumber) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.officeId,po.buidId, po.buidnumber,po.officenumber from  com.js.oa.routine.officeroom.po.OfficePO po where 1=1 and po.buidId=" + 
          
          buidId + " and po.buidnumber='" + buidnumber + "' " + 
          "and po.officenumber='" + officenumber + "' ")
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getOfficeTotalStation(String buidId, String buidnumber, String officenumber, String applayUserId) throws Exception {
    String officeTotalStation = "0";
    String useTotalStation = "0";
    int useStation = 0;
    List list = null;
    List<Object[]> useList = null;
    try {
      begin();
      list = this.session.createQuery("select po.officestation from  com.js.oa.routine.officeroom.po.OfficePO po where 1=1 and po.buidId=" + 
          
          buidId + " and po.buidnumber='" + buidnumber + "' " + 
          "and po.officenumber='" + officenumber + "'   ")
        .list();
      if (list != null && list.size() > 0)
        officeTotalStation = (new StringBuilder()).append(list.get(0)).append("&").toString(); 
      String applayUserLike = "$" + applayUserId + "$";
      StringBuffer sBuffer = new StringBuffer();
      sBuffer.append(" select po.applayStation,po.applayId from  ");
      sBuffer.append(" com.js.oa.routine.officeroom.po.OfficeUsePO po ");
      sBuffer.append(" where 1=1 and (po.applayIsoverdue is null or po.applayIsoverdue='0' ) and po.applayBuildId=" + buidId + " and po.applayBuildNumber='" + buidnumber + "' ");
      sBuffer.append(" and po.applayNumber='" + officenumber + "' ");
      if (!"".equals(applayUserId))
        sBuffer.append(" and po.applayUserId not like '%" + applayUserLike + "%' "); 
      useList = this.session.createQuery(sBuffer.toString()).list();
      if (useList != null && useList.size() > 0)
        for (int z = 0; z < useList.size(); z++) {
          Object[] objects = useList.get(z);
          useStation += Integer.parseInt((String)objects[0]);
        }  
      useTotalStation = (new StringBuilder(String.valueOf(useStation))).toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    if (officeTotalStation != null && "".equals(officeTotalStation))
      officeTotalStation = "0"; 
    return String.valueOf(officeTotalStation) + useTotalStation;
  }
  
  public Map getBuildBasic(String buildId, String buildNumber) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    String buildName = "";
    int officeCount = 0;
    int officeStation = 0;
    int applayAllUse = 0;
    int applayPathUse = 0;
    int applayNoUse = 0;
    int applayStation = 0;
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlBuildString = "select buildName,build_id from oa_office_building where build_id=" + buildId;
      rSet = stmt.executeQuery(sqlBuildString);
      while (rSet.next())
        buildName = rSet.getString("buildName"); 
      map.put("buildName", buildName);
      String sqlOffice = "select officestation,buidId,buidNumber,officeisuse from oa_office where buidId=" + buildId + " ";
      if (buildNumber != null && !"".equals(buildNumber))
        sqlOffice = String.valueOf(sqlOffice) + " and buidNumber='" + buildNumber + "' "; 
      rSet = stmt.executeQuery(sqlOffice);
      while (rSet.next()) {
        String offices = rSet.getString("officestation");
        String officeisuse = rSet.getString("officeisuse");
        if (offices == null || "".equals(offices))
          offices = "0"; 
        officeStation += Integer.parseInt(offices);
        officeCount++;
        if (officeisuse == null || "0".equals(officeisuse)) {
          applayNoUse++;
          continue;
        } 
        if ("1".equals(officeisuse)) {
          applayAllUse++;
          continue;
        } 
        if ("2".equals(officeisuse))
          applayPathUse++; 
      } 
      map.put("officeStation", Integer.valueOf(officeStation));
      map.put("officeCount", Integer.valueOf(officeCount));
      map.put("applayAllUse", Integer.valueOf(applayAllUse));
      map.put("applayPathUse", Integer.valueOf(applayPathUse));
      map.put("applayNoUse", Integer.valueOf(applayNoUse));
      String sqlOfficeUse = "select applay_station,applay_buildId  from oa_office_use where APPLAY_BUILDID='" + buildId + "'  and APPLAY_ISUSE='1' and (applay_isoverdue is null or applay_isoverdue=0) ";
      if (!"".equals(buildNumber) && buildNumber != null)
        sqlOfficeUse = String.valueOf(sqlOfficeUse) + "and  APPLAY_BUILDNUMBER='" + buildNumber + "' "; 
      rSet = stmt.executeQuery(sqlOfficeUse);
      while (rSet.next()) {
        String stations = rSet.getString(1);
        if (stations == null || "".equals(stations))
          stations = "0"; 
        applayStation += Integer.parseInt(stations);
      } 
      map.put("applayStation", Integer.valueOf(applayStation));
    } catch (Exception e) {
      e.printStackTrace();
      if (rSet != null)
        rSet.close(); 
      if (conn != null)
        conn.close(); 
    } finally {
      rSet.close();
      stmt.close();
      conn.close();
    } 
    return map;
  }
  
  public List getTitleList(String id, String dis) throws Exception {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    List<Object[]> list = new ArrayList();
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select id,titleName,titleLevel from oa_office_title  order by id  ";
      rSet = stmt.executeQuery(sqlString);
      while (rSet.next()) {
        Object[] objects = new Object[3];
        objects[0] = rSet.getString(1);
        objects[1] = rSet.getString(2);
        objects[2] = rSet.getString(3);
        list.add(objects);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      if (rSet != null)
        rSet.close(); 
      if (conn != null)
        conn.close(); 
    } finally {
      rSet.close();
      stmt.close();
      conn.close();
    } 
    return list;
  }
  
  public List getDirectionList(String id, String dis) throws Exception {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    List<Object[]> list = new ArrayList();
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select id,directName,bz from oa_office_direction  order by id  ";
      rSet = stmt.executeQuery(sqlString);
      while (rSet.next()) {
        Object[] objects = new Object[3];
        objects[0] = rSet.getString(1);
        objects[1] = rSet.getString(2);
        objects[2] = rSet.getString(3);
        list.add(objects);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      if (rSet != null)
        rSet.close(); 
      if (conn != null)
        conn.close(); 
    } finally {
      rSet.close();
      stmt.close();
      conn.close();
    } 
    return list;
  }
  
  public Map ImportOffice(HttpServletRequest httpServletRequest) throws FileNotFoundException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String message = "";
    String succeed = "0";
    HttpSession session = httpServletRequest.getSession(true);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowStr = dateFormat.format(new Date());
    String realPath = httpServletRequest.getRealPath("/uploadtemplate/office.xls");
    String buildId = "";
    String buildNumber = "";
    String officeNumber = "";
    FileInputStream file = new FileInputStream(new File(realPath));
    try {
      this.workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      message = String.valueOf(message) + "选择的模版不正确！<br>";
      if (!"1".equals(succeed))
        succeed = "1"; 
      map.put("succeed", succeed);
      map.put("message", message);
      return map;
    } 
    this.sheet = this.workbook.getSheet(0);
    if (this.sheet != null) {
      int rows = this.sheet.getRows();
      int columns = 8;
      try {
        String duiYinWiZhi = this.sheet.getCell(0, 1).getContents().trim();
        String duiYinWiZhiValue = this.sheet.getCell(1, 1).getContents().trim();
        String databaseType = SystemCommon.getDatabaseType();
        if ("对应字段所在列".equals(duiYinWiZhi) && 
          duiYinWiZhiValue.matches("^[0-9]*[1-9][0-9]*$")) {
          if (!"1".equals(succeed)) {
            DataSourceBase dataSourceBase = new DataSourceBase();
            for (int i = 4; i < rows; i++) {
              StringBuffer insertParamSql = new StringBuffer("insert into  oa_office(");
              if ("mysql".equals(databaseType)) {
                insertParamSql.append("officename,officenumber,buidId,buildname,buidnumber,officefaces,officearea,officestation ");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(");
              } else {
                insertParamSql.append("office_id,officename,officenumber,buidId,buildname,buidnumber,officefaces,officearea,officestation ");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(hibernate_sequence.nextval,");
              } 
              for (int k = 0; k < columns; k++) {
                if (i != Long.valueOf(duiYinWiZhiValue).longValue() - 1L) {
                  String textValue = this.sheet.getCell(k, i).getContents().trim();
                  if (k == 8 || k == 7) {
                    insertParamSql.append("").append(textValue).append(",");
                  } else {
                    insertParamSql.append("'").append(textValue).append("',");
                  } 
                  if (k == 2)
                    buildId = textValue.toString(); 
                  if (k == 4)
                    buildNumber = textValue.toString(); 
                  if (k == 1)
                    officeNumber = textValue.toString(); 
                } 
              } 
              String insertParamSqlString = insertParamSql.toString();
              if (insertParamSqlString.endsWith(","))
                insertParamSqlString = String.valueOf(insertParamSqlString.substring(0, insertParamSqlString.length() - 1)) + ")"; 
              System.out.println("sql:" + insertParamSqlString.toString());
              List list = getOfficeIsTrue(buildId, buildNumber, officeNumber);
              if (list == null || list.size() <= 0)
                updateByDutySql(insertParamSqlString.toString()); 
            } 
          } 
        } else {
          if (!"1".equals(succeed))
            succeed = "1"; 
          message = String.valueOf(message) + "选择的模版不正确！<br>";
        } 
      } catch (Exception ex) {
        succeed = "1";
        message = String.valueOf(message) + "导入数据对应不正确！<br>";
        ex.printStackTrace();
      } 
    } 
    map.put("succeed", succeed);
    map.put("message", message);
    return map;
  }
  
  public boolean updateByDutySql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean getBuildApplayIsTrue(String buildId) throws Exception {
    boolean flag = false;
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.applayId,po.applayNumber, po.applayBuildId,po.applayBuildNumber from  com.js.oa.routine.officeroom.po.OfficeUsePO po where 1=1 and po.applayBuildId=" + 
          
          buildId + " and po.applayIsUse<>1 ")
        .list();
      if (list != null && list.size() > 0) {
        flag = false;
      } else {
        flag = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return flag;
  }
  
  public Boolean saveWorkflow(WFWorkPO po) throws Exception {
    boolean flag = false;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return Boolean.valueOf(flag);
  }
  
  public String getBuildCount(String buildId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    String count = "0";
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "select BUILDIDACCOUNT from oa_office_building where BUILD_ID=" + buildId + " ";
      rSet = stmt.executeQuery(sql);
      if (rSet.next())
        count = rSet.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      rSet.close();
      stmt.close();
      conn.close();
    } 
    return count;
  }
}
