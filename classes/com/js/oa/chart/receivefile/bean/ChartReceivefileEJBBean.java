package com.js.oa.chart.receivefile.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ChartReceivefileEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map<String, Object> getFlowStatusData(String orgId, String userId, String rightCode, Integer pageSize, Integer pageNo, String orgName, String orderBy, String searchWhere, String flowStatusType, String userName, String itemIdSql, String statProcessId) throws SQLException {
    Map<String, Object> result = new HashMap<String, Object>();
    Connection conn = null;
    Statement stmt = null;
    String processIds = statProcessId;
    if (processIds == null || "".equals(processIds))
      processIds = getStatProcessIds(); 
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      String whereTmp = "1=1";
      if (whereTmp.equals(""))
        whereTmp = " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        whereTmp = " and " + whereTmp; 
      if (orgName != null && !"".equals(orgName))
        whereTmp = " and org.orgname like '%" + orgName + "%' "; 
      StringBuffer sql = new StringBuffer("");
      String countSql = "";
      sql.append(" SELECT  wk.*,cc1+cc2+cc3+cc4 as sumc  FROM( ");
      sql.append(" SELECT  org.orgnameString,");
      sql.append(" CASE WHEN t1.c1 IS NULL THEN 0 ELSE t1.c1 END AS cc1,");
      sql.append(" CASE WHEN t2.c2 IS NULL THEN 0 ELSE t2.c2 END AS cc2,");
      sql.append(" CASE WHEN t3.c3 IS NULL THEN 0 ELSE t3.c3 END AS cc3,");
      sql.append(" CASE WHEN t4.c4 IS NULL THEN 0 ELSE t4.c4 END AS cc4,");
      sql.append(" CASE WHEN t5.c5 IS NULL THEN 0 ELSE t5.c5 END AS cc5,");
      sql.append(" org.ORGLEVEL,org.ORG_ID,org.orgidstring ");
      sql.append(" FROM org_organization org ");
      sql.append(" LEFT JOIN  ( ");
      sql.append(" SELECT COUNT(w.wf_work_id) AS c1,o.org_id AS o1  ");
      sql.append(" FROM JSF_WORK w left join doc_receivefile_stat ds on w.workrecord_id=ds.record_id,org_organization_user o  ");
      sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id  ");
      sql.append(" AND (w.WORKSTATUS=0 )   AND(w.WORKLISTCONTROL=1 ) ");
      sql.append(" AND(w.WORKDELETE=0)");
      sql.append(" AND(w.workprocess_id in(" + processIds + "))");
      sql.append(searchWhere);
      sql.append(" GROUP BY o.org_id  ");
      sql.append(" ) t1 ON org.org_id=t1.o1  ");
      sql.append(" LEFT JOIN   (  ");
      sql.append(" SELECT COUNT(w.wf_work_id) AS c2,o.org_id AS o2 ");
      sql.append(" FROM JSF_WORK w left join doc_receivefile_stat ds on w.workrecord_id=ds.record_id ,org_organization_user o ");
      sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id   AND (w.WORKSTATUS=0 ) ");
      sql.append(" AND(w.WORKLISTCONTROL=1 ) ");
      sql.append(" AND(w.WORKDELETE=0) ");
      sql.append(" AND(w.workprocess_id in(" + processIds + "))");
      String dataType = SystemCommon.getDatabaseType();
      if (dataType.indexOf("mysql") >= 0) {
        sql.append(" AND ('" + (new Date()).toLocaleString() + "' > ds.expect_donetime) ");
      } else {
        sql.append(" AND (JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > ds.expect_donetime) ");
      } 
      sql.append(searchWhere);
      sql.append(" GROUP BY o.org_id ");
      sql.append(" ) t2 ON  org.org_id=t2.o2");
      sql.append(" LEFT JOIN   (  ");
      sql.append(" SELECT COUNT(w.wf_work_id) AS c3,o.org_id AS o3   ");
      sql.append(" FROM JSF_WORK w left join doc_receivefile_stat ds on w.workrecord_id=ds.record_id ,org_organization_user o  ");
      sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id ");
      sql.append(" AND (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE IS NULL)  ");
      sql.append(" AND(w.WORKLISTCONTROL=1 )   AND(w.WORKDELETE=0)");
      sql.append(" AND(w.workprocess_id in(" + processIds + "))");
      sql.append(searchWhere);
      sql.append(" GROUP BY o.org_id ");
      sql.append(" ) t3 ON  org.org_id=t3.o3 ");
      sql.append(" LEFT JOIN   (  ");
      sql.append(" SELECT COUNT(w.wf_work_id) AS c4,o.org_id AS o4 ");
      sql.append(" FROM JSF_WORK w left join doc_receivefile_stat ds on w.workrecord_id=ds.record_id ,org_organization_user o");
      sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id   AND (w.WORKSTATUS=101 ) ");
      sql.append(" AND (WORKDONEWITHDATE IS NOT NULL ) ");
      sql.append(" AND(w.WORKLISTCONTROL=1 ) ");
      sql.append(" AND(w.workprocess_id in(" + processIds + "))");
      sql.append(" AND(w.WORKDELETE=0) ");
      sql.append(searchWhere);
      sql.append(" GROUP BY o.org_id ");
      sql.append(" ) t4 ON  org.org_id=t4.o4");
      sql.append(" LEFT JOIN   (  ");
      sql.append(" SELECT COUNT(ds.stat_id) AS c5,o.org_id AS o5 ");
      sql.append(" FROM doc_receivefile_stat ds,org_organization_user o");
      sql.append(" WHERE ds.EMP_ID=o.emp_id   AND (ds.file_STATUS=100 ) ");
      sql.append(" AND (ds.isoverdue=1) ");
      sql.append(" AND(ds.process_id in(" + processIds + "))");
      sql.append(searchWhere);
      sql.append(" GROUP BY o.org_id ");
      sql.append(" ) t5 ON  org.org_id=t5.o5");
      sql.append(" where 1=1 and org.orgstatus=0 " + whereTmp + ") wk ");
      sql.append(("".equals(itemIdSql) || "-1".equals(itemIdSql)) ? "" : ("where org_id in (" + itemIdSql + ")"));
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      int recordCount = 0;
      countSql = "select count(1) from (" + sql + ")kk";
      ResultSet rs = stmt.executeQuery(countSql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      result.put("recordCount", new Integer(recordCount));
      List<Object[]> reList = new ArrayList();
      Object[] obj = (Object[])null;
      if (orderBy == null) {
        orderBy = " order by orgidstring,ORG_ID ";
      } else {
        orderBy = " order by " + orderBy + " ,orgidstring,ORG_ID";
      } 
      String tempSql = "";
      if ("oracle".equals(databaseType)) {
        tempSql = "select wkk.*,rownum as rn  from (" + sql + orderBy + ") wkk ";
      } else {
        tempSql = "select wkk.*,'rn' from (" + sql + orderBy + ") wkk ";
      } 
      String reSql = "select * from (" + tempSql + ")";
      if (flag == 1) {
        reSql = String.valueOf(reSql) + " where rn >=" + ((pageNo.intValue() - 1) * pageSize.intValue() + 1) + 
          "and rn <=" + (pageNo.intValue() * pageSize.intValue());
      } else {
        reSql = String.valueOf(reSql) + "ttt limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } 
      rs = stmt.executeQuery(reSql);
      while (rs.next()) {
        obj = new Object[8];
        for (int k0 = 0; k0 < 8; k0++)
          obj[k0] = rs.getString(k0 + 1); 
        reList.add(obj);
      } 
      result.put("reList", reList);
      reList = new ArrayList();
      String recoutSql = "select sum(w.cc1),sum(w.cc2),sum(w.cc3),sum(w.cc4),sum(w.cc5) from (" + reSql + ") w";
      rs = stmt.executeQuery(recoutSql);
      while (rs.next()) {
        obj = new Object[5];
        for (int k0 = 0; k0 < 5; k0++)
          obj[k0] = rs.getString(k0 + 1); 
        reList.add(obj);
      } 
      result.put("reCountList", reList);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return result;
  }
  
  public String getRightWhere(String userId, String orgId, String rightCode, String fieldOrg, String fieldEmp) throws Exception {
    String where = "";
    ManagerEJBBean managerEJBBean = new ManagerEJBBean();
    List<?> list = managerEJBBean.getRightScope(userId, rightCode);
    if (list != null && list.size() > 0) {
      Object[] obj = (Object[])list.get(0);
      String scopeType = obj[0].toString();
      if ("0".equals(scopeType)) {
        where = " 1=1 ";
      } else if ("1".equals(scopeType)) {
        where = "".equals(fieldEmp) ? "1>1" : (String.valueOf(fieldOrg) + "=" + orgId);
      } else if ("2".equals(scopeType)) {
        String orgRange = managerEJBBean.getAllJuniorOrgIdByRange("*" + orgId + "*");
        if (orgRange.indexOf("a") > 0) {
          String[] tmp = orgRange.split("a");
          for (int k = 0; k < tmp.length; k++)
            where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
          if (where.endsWith("or "))
            where = where.substring(0, where.length() - 3); 
        } else {
          where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
        } 
      } else if ("3".equals(scopeType)) {
        where = String.valueOf(fieldOrg) + "=" + orgId;
      } else {
        if (obj[1] != null && !"".equals(obj[1].toString())) {
          String orgRange = managerEJBBean.getAllJuniorOrgIdByRange((String)obj[1]);
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
          } 
        } 
        String dbType = SystemCommon.getDatabaseType();
        if (obj[3] != null && !"".equals(obj[3].toString())) {
          if (!"".equals(where))
            where = String.valueOf(where) + " or "; 
          if ("mysql".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like concat('%$'," + fieldEmp + ",'$%')";
          } else if ("oracle".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'||" + fieldEmp + "||'$%'";
          } else if ("mssqlserver".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
          } else if ("db2".equals(fieldEmp)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
          } 
        } 
      } 
    } else {
      where = "1>2";
    } 
    return "(" + where + ")";
  }
  
  public Date getNextMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(2, 1);
    return c.getTime();
  }
  
  public String getStatProcessIds() {
    String ids = "0";
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("CqstStatReceiveFileFlow");
      ids = node.getAttributeValue("value");
    } catch (Exception err) {
      err.printStackTrace();
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return ids;
  }
  
  public List<String[]> getReceiveProcess() {
    List<String[]> processInfo = (List)new ArrayList<String>();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String flowIds = getStatProcessIds();
      ResultSet rs = stmt.executeQuery("select wf_workflowprocess_id,workflowprocessname from JSF_WORKFLOWPROCESS t where t.wf_workflowprocess_id in(" + flowIds + ")");
      while (rs.next()) {
        String[] tmp = { rs.getString(1), rs.getString(2) };
        processInfo.add(tmp);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return processInfo;
  }
  
  public boolean initReceivefileStat() {
    boolean res = false;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String flowIds = getStatProcessIds();
      List<String[]> recordIds = (List)new ArrayList<String>();
      String sql = "select distinct dr.receivefile_id,dr.createdtime,receivefile_receivedate,w.workstatus,w.workprocess_id from jsf_work w right join doc_receivefile dr on w.workrecord_id=dr.receivefile_id where w.workprocess_id in(" + flowIds + ") and w.workstatus=1 or w.workstatus=100";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String fileId = rs.getString(1);
        String createdTime = rs.getString(2);
        String receiveDate = rs.getString(3);
        if (createdTime.indexOf(" ") > 0)
          createdTime = String.valueOf(createdTime.substring(0, createdTime.indexOf(" "))) + " 23:59:59"; 
        if (receiveDate.indexOf(" ") > 0)
          receiveDate = String.valueOf(receiveDate.substring(0, receiveDate.indexOf(" "))) + " 00:00:00"; 
        recordIds.add(new String[] { fileId, createdTime, rs.getString(4), rs.getString(5), receiveDate });
      } 
      rs.close();
      int isoverdue = 0;
      String empId = "", dealwithTime = "";
      sql = "delete from doc_receivefile_stat";
      stmt.executeUpdate(sql);
      for (String[] recordInfo : recordIds) {
        sql = "select wf_curemployee_id,dealwithtime from jsf_work where workprocess_id in(" + flowIds + ") and workrecord_id=" + recordInfo[0] + " order by dealwithtime";
        rs = stmt.executeQuery(sql);
        isoverdue = 0;
        if ("100".equals(recordInfo[2])) {
          while (rs.next()) {
            empId = rs.getString(1);
            dealwithTime = rs.getString(2);
            if (dealwithTime != null && isOverdue(dealwithTime, recordInfo[1])) {
              isoverdue = 1;
              if (dealwithTime.indexOf(".") > 0)
                dealwithTime = dealwithTime.substring(0, dealwithTime.indexOf(".")); 
              break;
            } 
          } 
        } else {
          while (rs.next()) {
            empId = rs.getString(1);
            dealwithTime = rs.getString(2);
            if (dealwithTime != null && isOverdue(dealwithTime, recordInfo[1])) {
              isoverdue = 1;
              if (dealwithTime.indexOf(".") > 0)
                dealwithTime = dealwithTime.substring(0, dealwithTime.indexOf(".")); 
              break;
            } 
          } 
        } 
        rs.close();
        if (isoverdue == 1) {
          sql = "insert into doc_receivefile_stat(stat_id,record_id,process_id,file_status,expect_donetime,donetime,isoverdue,receive_date,emp_id)";
          sql = String.valueOf(sql) + "values(hibernate_sequence.nextval," + recordInfo[0] + "," + recordInfo[3] + "," + recordInfo[2] + ",to_date('" + recordInfo[1] + "','YYYY-MM-DD HH24:MI:SS'),to_date('" + dealwithTime + "','YYYY-MM-DD HH24:MI:SS')," + isoverdue + ",to_date('" + recordInfo[4] + "','YYYY-MM-DD HH24:MI:SS')," + empId + ")";
        } else {
          sql = "insert into doc_receivefile_stat(stat_id,record_id,process_id,file_status,expect_donetime,isoverdue,receive_date)";
          sql = String.valueOf(sql) + "values(hibernate_sequence.nextval," + recordInfo[0] + "," + recordInfo[3] + "," + recordInfo[2] + ",to_date('" + recordInfo[1] + "','YYYY-MM-DD HH24:MI:SS')," + isoverdue + ",to_date('" + recordInfo[4] + "','YYYY-MM-DD HH24:MI:SS'))";
        } 
        stmt.executeUpdate(sql);
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res;
  }
  
  private boolean isOverdue(String dealwithTime, String endTime) {
    boolean result = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date begin = format.parse(dealwithTime);
      Date end = format.parse(endTime);
      if (begin.after(end))
        result = true; 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
}
