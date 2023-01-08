package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.hibernate.Query;

public class NewDutyEJBBean extends HibernateBase implements SessionBean {
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
  
  public Integer add(DutyPO dutyPO) throws Exception {
    int result = 2;
    begin();
    try {
      String dutyName = dutyPO.getDutyName();
      result = Integer.parseInt(this.session
          .createQuery(
            "select count(*) from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + 
            dutyName + "' and po.domainId=" + dutyPO.getDomainId()).iterate().next()
          .toString());
      int result2 = Integer.parseInt(this.session
          .createQuery(
            "select count(*) from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyNO='" + 
            dutyPO.getDutyNO() + "' and po.domainId=" + dutyPO.getDomainId()).iterate()
          .next().toString());
      if (result > 0) {
        result = 1;
        return new Integer(1);
      } 
      if (result2 > 0) {
        result = 3;
        return new Integer(3);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public Boolean del(String[] id) throws Exception {
    Boolean result = Boolean.FALSE;
    String idbuf = "";
    for (int i = 0; i < id.length; i++)
      idbuf = String.valueOf(idbuf) + id[i] + ","; 
    begin();
    try {
      this.session.delete("from com.js.oa.hr.officemanager.po.DutyPO po where  po.id in (" + 
          idbuf.substring(0, idbuf.length() - 1) + ")");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getList(String domainId) throws Exception {
    return getList(domainId, "");
  }
  
  public List getList(String domainId, String corpId) throws Exception {
    ArrayList alist = new ArrayList();
    begin();
    try {
      String hql = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hql = "(aaa.corpId = 0 or aaa.corpId=" + corpId + ") and "; 
      Query query = this.session
        .createQuery("select aaa.id, aaa.dutyName,aaa.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO aaa where " + 
          hql + " aaa.domainId=" + domainId);
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public DutyPO getSingle(String dutyId) throws Exception {
    DutyPO dutyPO = null;
    begin();
    try {
      dutyPO = (DutyPO)this.session.load(DutyPO.class, new Long(dutyId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return dutyPO;
  }
  
  public Integer update(DutyPO dutyPO) throws Exception {
    int result = 2;
    begin();
    try {
      DutyPO modifyPO = (DutyPO)this.session.load(DutyPO.class, new Long(dutyPO.getId()));
      Iterator iter = this.session.createQuery(
          "select a.id from com.js.oa.hr.officemanager.po.DutyPO a where a.dutyName='" + 
          dutyPO.getDutyName() + "' and a.domainId=" + modifyPO.getDomainId() + " and a.id<>" + 
          dutyPO.getId()).iterate();
      Iterator iter2 = this.session.createQuery(
          "select a.id from com.js.oa.hr.officemanager.po.DutyPO a where a.dutyNO='" + 
          dutyPO.getDutyNO() + "' and a.domainId=" + modifyPO.getDomainId() + " and a.id<>" + 
          dutyPO.getId()).iterate();
      if (iter.hasNext()) {
        result = 1;
      } else if (iter2.hasNext()) {
        result = 3;
      } else {
        String oldDutyName = modifyPO.getDutyName();
        modifyPO.setDutyName(dutyPO.getDutyName());
        modifyPO.setDutyLevel(dutyPO.getDutyLevel());
        modifyPO.setDutyDescribe(dutyPO.getDutyDescribe());
        modifyPO.setDutyNO(dutyPO.getDutyNO());
        this.session.flush();
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("update org_employee set EMPDUTY = '" + dutyPO.getDutyName() + "', EMPDUTYLEVEL =" + 
            dutyPO.getDutyLevel() + " where EMPDUTY = '" + oldDutyName + "'");
        stmt.close();
        conn.close();
        result = 0;
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return new Integer(result);
  }
  
  public PostTitlePO getSinglePost(String postTitleId) throws Exception {
    PostTitlePO postPO = null;
    begin();
    try {
      postPO = (PostTitlePO)this.session.load(PostTitlePO.class, new Long(postTitleId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return postPO;
  }
  
  public Integer updatePost(PostTitlePO postTitlePO) throws Exception {
    int result = 2;
    begin();
    try {
      PostTitlePO modifyPO = (PostTitlePO)this.session.load(PostTitlePO.class, new Long(postTitlePO.getId()));
      Iterator iter = this.session.createQuery(
          "select a.id from com.js.oa.hr.officemanager.po.PostTitlePO a where a.postTitle='" + 
          postTitlePO.getPostTitle() + "' and a.id<>" + postTitlePO.getId() + 
          " and a.postTitleSeries='" + postTitlePO.getPostTitleSeries() + "' and a.domainId=" + 
          postTitlePO.getDomainId()).iterate();
      if (iter.hasNext()) {
        result = 1;
      } else {
        modifyPO.setPostTitle(postTitlePO.getPostTitle());
        modifyPO.setPostTitleSeries(postTitlePO.getPostTitleSeries());
        this.session.flush();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return new Integer(result);
  }
  
  public String saveStation(StationPO stationPO) throws Exception {
    String result = "success";
    begin();
    try {
      String sqlForName = "";
      if (SystemCommon.getMultiDepart() == 1) {
        sqlForName = "select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.name='" + 
          stationPO.getName() + "' and aaa.domainId=" + stationPO.getDomainId() + " and (aaa.corpId=" + 
          stationPO.getCorpId() + " or aaa.corpId=0)";
      } else {
        sqlForName = "select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.name='" + 
          stationPO.getName() + "' and aaa.domainId=" + stationPO.getDomainId();
      } 
      String sqlForNo = "";
      sqlForNo = "select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.no='" + 
        stationPO.getNo() + "' and aaa.domainId=" + stationPO.getDomainId();
      Query query1 = this.session.createQuery(sqlForName);
      Query query2 = this.session.createQuery(sqlForNo);
      if (query1.iterate().hasNext()) {
        result = "name";
      } else if (query2.iterate().hasNext()) {
        result = "no";
      } else {
        this.session.save(stationPO);
        this.session.flush();
      } 
    } catch (Exception e) {
      result = "failure";
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean deleteStation(String ids) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      this.session.delete("from com.js.oa.hr.personnelmanager.po.StationPO a where a.id in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public String[] getSingleStation(String stationId) throws Exception {
    String[] station = { "", "", "" };
    begin();
    try {
      List<Object[]> list = this.session.createQuery(
          "select a.name,a.describe,a.no from com.js.oa.hr.personnelmanager.po.StationPO a where a.id=" + 
          stationId).list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        station[0] = obj[0].toString();
        station[1] = (obj[1] == null) ? "" : obj[1].toString();
        station[2] = (obj[2] == null) ? "" : obj[2].toString();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return station;
  }
  
  public String[] getSingleStationByName(String stationName) throws Exception {
    String[] station = { "", "", "" };
    begin();
    try {
      List<Object[]> list = this.session.createQuery(
          "select a.id,a.name,a.no from com.js.oa.hr.personnelmanager.po.StationPO a where a.name='" + 
          stationName + "'").list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        station[0] = obj[0].toString();
        station[1] = (obj[1] == null) ? "" : obj[1].toString();
        station[2] = (obj[2] == null) ? "" : obj[2].toString();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return station;
  }
  
  public String updateStation(StationPO stationPO) throws Exception {
    String result = "success";
    begin();
    try {
      StationPO modifyPO = (StationPO)this.session.load(StationPO.class, stationPO.getId());
      String sqlForName = "";
      if (SystemCommon.getMultiDepart() == 1) {
        sqlForName = "select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.name='" + 
          stationPO.getName() + "' and aaa.domainId=" + modifyPO.getDomainId() + " and (aaa.corpId=" + 
          modifyPO.getCorpId() + " or aaa.corpId=0) and aaa.id<>" + stationPO.getId();
      } else {
        sqlForName = "select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.name='" + 
          stationPO.getName() + "' and " + "aaa.domainId=" + modifyPO.getDomainId() + " and aaa.id<>" + 
          stationPO.getId();
      } 
      String sqlForNo = "select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.no='" + 
        stationPO.getNo() + "' and " + "aaa.domainId=" + modifyPO.getDomainId() + " and  aaa.id<>" + 
        stationPO.getId();
      Query query1 = this.session.createQuery(sqlForName);
      Query query2 = this.session.createQuery(sqlForNo);
      if (query1.iterate().hasNext()) {
        result = "name";
      } else if (query2.iterate().hasNext()) {
        result = "no";
      } else {
        modifyPO.setName(stationPO.getName());
        modifyPO.setDescribe(stationPO.getDescribe());
        modifyPO.setNo(stationPO.getNo());
        this.session.flush();
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("update org_employee set EMPPOSITION = '" + stationPO.getName() + "'" + 
            " where EMPPOSITIONID = '" + stationPO.getId() + "'");
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      result = "failure";
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getStationList(String domainId) throws Exception {
    return getStationList(domainId, "");
  }
  
  public List getStationList(String domainId, String corpId) throws Exception {
    ArrayList alist = new ArrayList();
    begin();
    try {
      String hql = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hql = "(a.corpId=0 or a.corpId=" + corpId + ") and"; 
      alist = (ArrayList)this.session.createQuery(
          "select a.id,a.name,a.no from com.js.oa.hr.personnelmanager.po.StationPO a where " + hql + 
          " a.domainId=" + domainId).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public Long getDutyID(String dutyName, Long domainId) throws Exception {
    Long id = new Long(0L);
    try {
      begin();
      List<E> list = this.session.createQuery(
          "select po.id from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + dutyName + 
          "' and po.domainId=" + domainId).list();
      if (list != null && list.size() > 0)
        id = new Long(list.get(0).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return id;
  }
  
  public OrganizationVO getOrgPO(Long id) throws Exception {
    OrganizationVO po = null;
    try {
      begin();
      po = (OrganizationVO)this.session.load(OrganizationVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public int updateDutyBeforDel(String[] ids) {
    String id = "";
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = ids).length, b = 0; b < i; ) {
      String s = arrayOfString[b];
      id = String.valueOf(id) + "," + s;
      b++;
    } 
    id = id.substring(1);
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    int count = 0;
    String sql = "update org_employee em join oa_duty du on em.empduty=du.dutyname set em.EMPDUTY='' where du.duty_id in(" + 
      id + ")";
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      count = stmt.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return count;
  }
  
  public Map ImportDuty(HttpServletRequest httpServletRequest) throws FileNotFoundException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String message = "";
    String succeed = "0";
    String messageString = "";
    boolean flag = false;
    List<String> errorDutyList = new ArrayList<String>();
    String textValue = null;
    HttpSession session = httpServletRequest.getSession(true);
    String savetype = httpServletRequest.getParameter("savetype");
    String saveTypeName = "";
    if ("1".equals(savetype)) {
      saveTypeName = "忽略";
    } else {
      saveTypeName = "覆盖";
    } 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowStr = dateFormat.format(new Date());
    String realPath = httpServletRequest.getRealPath("/uploadtemplate/station.xls");
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
      int columns = 2;
      try {
        String duiYinWiZhi = this.sheet.getCell(0, 1).getContents().trim();
        String databaseType = SystemCommon.getDatabaseType();
        if ("对应字段所在列".equals(duiYinWiZhi)) {
          if (!"1".equals(succeed)) {
            DataSourceBase dataSourceBase = new DataSourceBase();
            for (int i = 4; i < rows; i++) {
              StringBuffer insertParamSql = new StringBuffer("insert into  ST_STATION(");
              if ("mysql".equals(databaseType)) {
                insertParamSql.append("no,station_name,domain_id,description,corpId ");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(");
              } else {
                insertParamSql.append("id,no,station_name,domain_id,description,corpId ");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(hibernate_sequence.nextval,");
              } 
              for (int k = 0; k < columns; k++) {
                textValue = this.sheet.getCell(k, i).getContents().trim();
                insertParamSql.append("'").append(textValue).append("',");
              } 
              insertParamSql.append("'").append("0").append("',");
              insertParamSql.append("''");
              if (session.getAttribute("sysManager").toString().indexOf("1") >= 0 && 
                httpServletRequest.getParameter("fromSystem") != null && 
                "1".equals(httpServletRequest.getParameter("fromSystem"))) {
                insertParamSql.append(",0)");
              } else {
                Long corpId = Long.valueOf(session.getAttribute("corpId").toString());
                insertParamSql.append("," + corpId + ")");
              } 
              if (!"".equals(textValue) && textValue != null)
                flag = getStationName(textValue); 
              if (savetype != null && "2".equals(savetype)) {
                String sql = "delete from ST_STATION  where station_name='" + textValue + "' ";
                deleteByDutyName(sql);
                updateByDutySql(insertParamSql.toString());
              } else {
                updateByDutySql(insertParamSql.toString());
              } 
              if (flag) {
                succeed = "0";
                map.put("succeed", succeed);
                errorDutyList.add(textValue);
              } 
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
    if (errorDutyList != null && !errorDutyList.isEmpty())
      for (int m = errorDutyList.size() - 1; m > 0; m--) {
        for (int n = 0; n < errorDutyList.size(); n++) {
          if (((String)errorDutyList.get(m)).equals(errorDutyList.get(n)) && m != n)
            errorDutyList.remove(m); 
        } 
      }  
    if (errorDutyList != null && !errorDutyList.isEmpty())
      for (String errorDuty : errorDutyList) {
        messageString = String.valueOf(messageString) + "【" + errorDuty + "】";
        flag = true;
      }  
    if (flag) {
      messageString = "提示：导入的岗位" + messageString + "已经存在！已进行" + saveTypeName;
      map.put("succeed", succeed);
      map.put("messageString", messageString);
      map.put("message", message);
      return map;
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
  
  public boolean deleteByDutyName(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
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
  
  public boolean getStationName(String name) throws Exception {
    boolean flag = false;
    try {
      begin();
      List list = this.session
        .createQuery(
          "select a.id,a.name from com.js.oa.hr.personnelmanager.po.StationPO a where a.name='" + 
          name + "' ").list();
      if (list != null && list.size() > 0)
        flag = true; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return flag;
  }
}
