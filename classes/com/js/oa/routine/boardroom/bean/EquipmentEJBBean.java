package com.js.oa.routine.boardroom.bean;

import com.js.oa.routine.boardroom.po.EquipmentApplyPO;
import com.js.oa.routine.boardroom.po.EquipmentPO;
import com.js.oa.routine.boardroom.po.EquipmentTypePO;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class EquipmentEJBBean extends HibernateBase implements SessionBean {
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
  
  public boolean addEquipmentType(EquipmentTypePO equipmentTypePO) throws HibernateException {
    boolean result = false;
    begin();
    try {
      this.session.save(equipmentTypePO);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("addEquipmentType EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteEquipmentType(Long equipmentSortId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "select equipmentid from oa_equipment where equipmentsortid=" + 
          equipmentSortId);
      if (rs.next()) {
        result = false;
      } else {
        stmt.executeUpdate(
            "delete from oa_equipmenttype where equipmentsortid=" + 
            equipmentSortId);
        result = true;
      } 
      this.session.flush();
      stmt.close();
      conn.close();
    } catch (HibernateException e) {
      System.out.println("deleteEquipmentType EJB Exception:" + 
          e.toString());
      throw e;
    } catch (SQLException sQLException) {
    
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteBatchEquipmentType(String equipmentSortIds) throws HibernateException {
    boolean result = true;
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] idsArr = equipmentSortIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        ResultSet rs = stmt.executeQuery(
            "select equipmentid from oa_equipment where equipmentsortid=" + 
            idsArr[i]);
        if (rs.next()) {
          result = false;
        } else {
          stmt.executeUpdate(
              "delete from oa_equipmenttype where equipmentsortid=" + 
              idsArr[i]);
        } 
      } 
      this.session.flush();
      stmt.close();
      conn.close();
    } catch (HibernateException e) {
      System.out.println("deleteBatchEquipmentType EJB Exception:" + 
          e.toString());
      throw e;
    } catch (SQLException sQLException) {
    
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public EquipmentTypePO selectEquipmentType(Long equipmentSortId) throws HibernateException {
    EquipmentTypePO equipmentTypePO = null;
    begin();
    try {
      equipmentTypePO = (EquipmentTypePO)this.session.load(EquipmentTypePO.class, 
          equipmentSortId);
    } catch (HibernateException e) {
      System.out.println("selectEquipmentType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return equipmentTypePO;
  }
  
  public boolean modiEquipmentType(EquipmentTypePO equipmentTypePO) throws HibernateException {
    boolean result = false;
    begin();
    try {
      EquipmentTypePO equipmentType = (EquipmentTypePO)this.session.load(EquipmentTypePO.class, 
          equipmentTypePO.getEquipmentSortId());
      equipmentType.setEquipmentSortId(equipmentTypePO.getEquipmentSortId());
      equipmentType.setName(equipmentTypePO.getName());
      equipmentType.setCreatedEmp(equipmentTypePO.getCreatedEmp());
      equipmentType.setCreatedOrg(equipmentTypePO.getCreatedOrg());
      this.session.update(equipmentType);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List selectEquipmentType(String corpId) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query equipmentTypeQuery = null;
      if (SystemCommon.getMultiDepart() == 1) {
        equipmentTypeQuery = this.session.createQuery("select equipmentTypePO.equipmentSortId,equipmentTypePO.name from com.js.oa.routine.boardroom.po.EquipmentTypePO equipmentTypePO where equipmentTypePO.corpId=" + corpId);
      } else {
        equipmentTypeQuery = this.session.createQuery("select equipmentTypePO.equipmentSortId,equipmentTypePO.name from com.js.oa.routine.boardroom.po.EquipmentTypePO equipmentTypePO");
      } 
      list = equipmentTypeQuery.list();
    } catch (HibernateException e) {
      System.out.println("selectEquipmentType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public boolean addEquipment(EquipmentPO equipmentPO, Long equipmentSortId) throws HibernateException {
    boolean result = false;
    EquipmentTypePO equipmentType = null;
    begin();
    try {
      String sql = 
        "select po.equipmentId from com.js.oa.routine.boardroom.po.EquipmentPO po  where po.code='" + 
        
        equipmentPO.getCode() + 
        "' and po.domainId=" + 
        equipmentPO.getDomainId();
      if (SystemCommon.getMultiDepart() == 1)
        sql = String.valueOf(sql) + " and po.corpId=" + equipmentPO.getCorpId(); 
      Query query = this.session.createQuery(sql);
      if (query.iterate().hasNext()) {
        result = false;
      } else {
        equipmentType = (EquipmentTypePO)this.session.load(EquipmentTypePO.class, 
            equipmentSortId);
        equipmentPO.setEquipmentType(equipmentType);
        this.session.save(equipmentPO);
        this.session.flush();
        result = true;
      } 
    } catch (HibernateException e) {
      System.out.println("addEquipment EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteEquipment(Long equipmentId) throws Exception {
    boolean result = false;
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(
          "delete from oa_equipmentapply where equipmentid=" + 
          equipmentId);
      stmt.executeUpdate("delete from oa_equipment where equipmentid=" + 
          equipmentId);
      result = true;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("deleteEquipment EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteBatchEquipment(String equipmentIds) throws HibernateException {
    boolean result = false;
    begin();
    try {
      String[] idsArr = equipmentIds.split(",");
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      for (int i = 0; i < idsArr.length; i++) {
        stmt.executeUpdate(
            "delete from oa_equipmentapply where equipmentid=" + 
            idsArr[i]);
        stmt.executeUpdate(
            "delete from oa_equipment where equipmentid=" + 
            idsArr[i]);
      } 
      this.session.flush();
      result = true;
      stmt.close();
      conn.close();
    } catch (HibernateException e) {
      System.out.println("deleteBatchEquipment EJB Exception:" + 
          e.toString());
      throw e;
    } catch (SQLException sQLException) {
    
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public EquipmentPO selectEquipment(Long equipmentId) throws HibernateException {
    EquipmentPO equipmentPO = null;
    begin();
    try {
      equipmentPO = (EquipmentPO)this.session.load(EquipmentPO.class, 
          equipmentId);
    } catch (HibernateException e) {
      System.out.println("selectEquipment EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return equipmentPO;
  }
  
  public boolean modiEquipment(EquipmentPO equipmentPO, Long equipmentSortId) throws HibernateException {
    boolean result = false;
    EquipmentTypePO equipmentType = null;
    begin();
    try {
      EquipmentPO equipment = (EquipmentPO)this.session.load(EquipmentPO.class, 
          equipmentPO.getEquipmentId());
      String sql = 
        "select po.equipmentId from com.js.oa.routine.boardroom.po.EquipmentPO po  where po.code='" + 
        
        equipmentPO.getCode() + 
        "' and po.domainId=" + 
        equipmentPO.getDomainId() + " and po.equipmentId<>" + equipmentPO.getEquipmentId();
      if (SystemCommon.getMultiDepart() == 1)
        sql = String.valueOf(sql) + " and po.corpId=" + equipment.getCorpId(); 
      Query query = this.session.createQuery(sql);
      if (query.iterate().hasNext()) {
        result = false;
      } else {
        equipment.setEquipmentId(equipmentPO.getEquipmentId());
        equipmentType = (EquipmentTypePO)this.session.load(EquipmentTypePO.class, 
            equipmentSortId);
        equipment.setEquipmentType(equipmentType);
        equipment.setName(equipmentPO.getName());
        equipment.setCode(equipmentPO.getCode());
        equipment.setCost(equipmentPO.getCost());
        equipment.setManageDept(equipmentPO.getManageDept());
        equipment.setManageDeptName(equipmentPO.getManageDeptName());
        equipment.setRemark(equipmentPO.getRemark());
        equipment.setStatus(equipmentPO.getStatus());
        equipment.setStandard(equipmentPO.getStandard());
        equipment.setStoreManID(equipmentPO.getStoreManID());
        equipment.setStoreManName(equipmentPO.getStoreManName());
        this.session.update(equipment);
        this.session.flush();
        result = true;
      } 
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long addEquipmentApply(EquipmentApplyPO equipmentApplyPO, Long equipmentId) throws HibernateException {
    Long id = null;
    begin();
    try {
      EquipmentPO equipment = (EquipmentPO)this.session.load(EquipmentPO.class, 
          equipmentId);
      equipment.setStatus(new Integer(1));
      equipmentApplyPO.setEquipment(equipment);
      id = (Long)this.session.save(equipmentApplyPO);
      this.session.flush();
    } catch (HibernateException e) {
      id = new Long("-1");
      System.out.println("addEquipmentApply EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public EquipmentApplyPO selectEquipmentApply(Long equipmentApplyId) throws HibernateException {
    EquipmentApplyPO equipmentApplyPO = null;
    begin();
    try {
      equipmentApplyPO = (EquipmentApplyPO)this.session.load(EquipmentApplyPO.class, 
          equipmentApplyId);
    } catch (HibernateException e) {
      System.out.println("selectEquipmentApply EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return equipmentApplyPO;
  }
  
  public boolean modiEquipmentApply(EquipmentApplyPO equipmentApplyPO) throws HibernateException {
    boolean result = false;
    begin();
    try {
      EquipmentApplyPO equipmentApply = (EquipmentApplyPO)this.session.load(EquipmentApplyPO.class, 
          equipmentApplyPO.getEquipmentApplyId());
      equipmentApply.setStartDate(equipmentApplyPO.getStartDate());
      equipmentApply.setStartTime(equipmentApplyPO.getStartTime());
      equipmentApply.setEndDate(equipmentApplyPO.getEndDate());
      equipmentApply.setEndTime(equipmentApplyPO.getEndTime());
      equipmentApply.setPurpose(equipmentApplyPO.getPurpose());
      equipmentApply.setStatus(equipmentApplyPO.getStatus());
      EquipmentPO equipmentPO = equipmentApply.getEquipment();
      equipmentPO.setStatus(equipmentApplyPO.getEquipment().getStatus());
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteEquipmentApply(Long equipmentApplyId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      EquipmentApplyPO equipmentApplyPO = (EquipmentApplyPO)this.session.load(
          EquipmentApplyPO.class, equipmentApplyId);
      EquipmentPO equipmentPO = equipmentApplyPO.getEquipment();
      equipmentPO.setStatus(new Integer(0));
      this.session.delete(equipmentApplyPO);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("deleteEquipmentApply EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean restituteEquipmentApply(Long equipmentApplyId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      EquipmentApplyPO equipmentApplyPO = (EquipmentApplyPO)this.session.load(
          EquipmentApplyPO.class, equipmentApplyId);
      EquipmentPO equipmentPO = equipmentApplyPO.getEquipment();
      equipmentPO.setStatus(new Integer(0));
      equipmentApplyPO.setStatus(new Integer(4));
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("restituteEquipmentApply EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Boolean isRepeatName(String from, String where) throws HibernateException {
    Boolean result = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery("from " + from + " where " + 
          where);
      List list = query.list();
      if (list.size() >= 1)
        result = Boolean.TRUE; 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String[] getEquipmentInfo(String voitureId, String searchDate) throws Exception {
    List list = new ArrayList();
    String[] voiturePlace = new String[288];
    for (int i = 0; i < voiturePlace.length; i++)
      voiturePlace[i] = "false"; 
    Iterator<Object[]> iter = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select po.startDate,po.endDate,po.startTime,po.endTime from com.js.oa.routine.boardroom.po.EquipmentApplyPO po where po.equipment.equipmentId =" + 
          voitureId + " and ('" + 
          searchDate + 
          "' between po.startDate and po.endDate) " + 
          " and po.status=2";
      } else {
        tmpSql = "select po.startDate,po.endDate,po.startTime,po.endTime from com.js.oa.routine.boardroom.po.EquipmentApplyPO po where po.equipment.equipmentId =" + 
          voitureId + " and (JSDB.FN_STRTODATE('" + 
          searchDate + 
          "','S') between po.startDate and po.endDate) " + 
          " and po.status=2";
      } 
      Query query = this.session.createQuery(tmpSql);
      list = query.list();
      iter = list.iterator();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (formatter.parse(searchDate).after(formatter.parse(obj[0]
              .toString())))
          if (formatter.parse(searchDate).before(formatter.parse(obj[
                  1]
                .toString()))) {
            System.out.println("全部占用");
            for (int k = 0; k < voiturePlace.length; k++)
              voiturePlace[k] = "true"; 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                0].toString())))
          if (!formatter.parse(searchDate).equals(
              formatter.parse(obj[1].toString()))) {
            System.out.println("占用起始日期");
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 1; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                minute = "30";
                if (!formatter1.parse(myTime).before(formatter1
                    .parse(obj[2].toString())))
                  if (!formatter1.parse(myTime).after(
                      formatter1
                      .parse(castBoardRoomTime(obj[3].toString()))))
                    voiturePlace[2 * k + m] = "true";  
              } 
            } 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                1].toString())))
          if (!formatter.parse(searchDate).equals(
              formatter.parse(obj[
                  0].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 1; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                minute = "30";
                if (!formatter1.parse(myTime).after(formatter1
                    .parse(castBoardRoomTime(obj[3].toString()))))
                  voiturePlace[2 * k + m] = "true"; 
              } 
            } 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                1].toString())))
          if (formatter.parse(searchDate).equals(formatter.parse(
                obj[
                  0].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 1; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                minute = "30";
                if (!formatter1.parse(myTime).before(formatter1
                    .parse(castBoardRoomTime(obj[2].toString()))))
                  if (!formatter1.parse(myTime).after(formatter1
                      .parse(castBoardRoomTime(obj[3].toString()))))
                    voiturePlace[2 * k + m] = "true";  
              } 
            } 
            continue;
          }  
        for (int j = 0; j < voiturePlace.length; j++)
          voiturePlace[j] = "false"; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return voiturePlace;
  }
  
  private String castBoardRoomTime(String time) {
    if (time != null && time.length() > 0) {
      String retStr = "";
      String cTime = (new StringBuilder(String.valueOf(Float.valueOf(time).floatValue() / 3600.0F))).toString();
      if (cTime.indexOf(".") > 0) {
        int pos = 0;
        String mid = "";
        StringTokenizer st = new StringTokenizer(cTime, ".");
        while (st.hasMoreTokens()) {
          mid = st.nextToken();
          if (pos < 1 && mid.length() < 2) {
            retStr = "0" + mid;
          } else if (pos < 1 && mid.length() > 1) {
            retStr = mid;
          } 
          if (pos > 0 && "0".equals(mid)) {
            retStr = String.valueOf(retStr) + ":00";
          } else if (pos > 0 && !"0".equals(mid)) {
            retStr = String.valueOf(retStr) + ":30";
          } 
          pos++;
        } 
      } 
      return retStr;
    } 
    return "";
  }
  
  public String maintenanceEquipment(String wherePara) throws HibernateException {
    String maintenanceBoardRoomIds = "";
    begin();
    try {
      Query query = this.session.createQuery("select equipmentPO.equipmentId from com.js.oa.routine.boardroom.po.EquipmentPO equipmentPO where " + 
          wherePara);
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++)
        maintenanceBoardRoomIds = String.valueOf(maintenanceBoardRoomIds) + list.get(i) + 
          ","; 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return maintenanceBoardRoomIds;
  }
  
  public Map ImportEquipment(HttpServletRequest httpServletRequest) throws FileNotFoundException {
    Map<String, String> map = new HashMap<String, String>();
    String message = "";
    String succeed = "0";
    HttpSession session = httpServletRequest.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    String corpId = (session.getAttribute("corpId") == null) ? "0" : session.getAttribute("corpId").toString();
    if (SystemCommon.getMultiDepart() != 1)
      corpId = "0"; 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowStr = dateFormat.format(new Date());
    String realPath = httpServletRequest.getRealPath("/uploadtemplate/equipment.xls");
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
      int columns = 7;
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
        String duiYinWiZhi = this.sheet.getCell(0, 1).getContents().trim();
        String duiYinWiZhiValue = this.sheet.getCell(1, 1).getContents().trim();
        String databaseType = SystemCommon.getDatabaseType();
      } catch (Exception ex) {
        succeed = "1";
        message = String.valueOf(message) + "导入数据对应不正确！<br>";
        ex.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close(); 
          if (stmt != null)
            stmt.close(); 
          if (conn != null)
            conn.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
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
  
  public boolean selectByCode(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rSet = stmt.executeQuery(sql);
      String dataCodeString = "";
      while (rSet.next())
        dataCodeString = rSet.getString(1); 
      if ("".equals(dataCodeString) || dataCodeString == null) {
        result = true;
      } else {
        result = false;
      } 
      rSet.close();
      stmt.close();
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
  
  public String selectByNAME(String sql) throws Exception {
    String result = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rSet = stmt.executeQuery(sql);
      while (rSet.next()) {
        if (sql.contains("max(aaa.num)")) {
          result = (new StringBuilder(String.valueOf(rSet.getInt(1)))).toString();
          continue;
        } 
        result = rSet.getString(1);
      } 
      rSet.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public List export(String sqlAll) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(sqlAll).list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
}
