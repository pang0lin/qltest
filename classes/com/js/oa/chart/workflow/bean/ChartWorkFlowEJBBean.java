package com.js.oa.chart.workflow.bean;

import com.js.oa.jsflow.bean.WFProcessEJBBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.page.Page;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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

public class ChartWorkFlowEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map<String, List<Object[]>> listFlowStatus(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) throws Exception {
    Map<String, List<Object[]>> result = new HashMap<String, List<Object[]>>(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    int flag = 0;
    if (databaseType.indexOf("oracle") > -1) {
      flag = 1;
    } else if (databaseType.indexOf("mysql") > -1) {
      flag = 2;
    } 
    Connection conn = null;
    Statement stmt = null;
    begin();
    try {
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      String daiBanSql = "SELECT COUNT(w.wf_work_id) AS count1 FROM JSF_WORK w,org_organization_user o WHERE w.WF_CUREMPLOYEE_ID=o.emp_id  AND (w.WORKSTATUS=0 ) AND(w.WORKLISTCONTROL=1 ) AND(w.WORKTABLE_ID<>-1 ) AND(w.WORKDELETE=0)";
      ManagerService managerBD = new ManagerService();
      String whereTmp = managerBD.getRightWhere(userId, 
          orgId, 
          rightCode, 
          "o.org_id", 
          "o.emp_id");
      if (whereTmp.equals(""))
        daiBanSql = String.valueOf(daiBanSql) + " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        daiBanSql = String.valueOf(daiBanSql) + " and " + whereTmp; 
      int recordCount = 0;
      ResultSet rs = stmt.executeQuery(daiBanSql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      List<Object[]> list2 = new ArrayList(0);
      Object[] obj = new Object[4];
      obj[0] = Integer.valueOf(recordCount);
      String dataType = SystemCommon.getDatabaseType();
      String chaoqiSql = "SELECT COUNT(w.wf_work_id) AS count1 FROM JSF_WORK w,org_organization_user o WHERE w.WF_CUREMPLOYEE_ID=o.emp_id  AND (w.WORKSTATUS=0 ) AND (w.WORKLISTCONTROL=1 ) AND(w.WORKTABLE_ID<>-1 ) AND (w.WORKDELETE=0)";
      if (dataType.indexOf("mysql") >= 0) {
        chaoqiSql = String.valueOf(chaoqiSql) + " AND (w.WORKDEADLINE<>'-1' AND '" + (new Date()).toLocaleString() + "' > w.WORKDEADLINEDATE) ";
      } else {
        chaoqiSql = String.valueOf(chaoqiSql) + " AND (w.WORKDEADLINE<>'-1' AND JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > w.WORKDEADLINEDATE) ";
      } 
      if (whereTmp.equals(""))
        chaoqiSql = String.valueOf(chaoqiSql) + " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        chaoqiSql = String.valueOf(chaoqiSql) + " and " + whereTmp; 
      rs = stmt.executeQuery(chaoqiSql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      obj[1] = Integer.valueOf(recordCount);
      String zaiBanSql = "SELECT COUNT(w.wf_work_id) AS count1 FROM JSF_WORK w,org_organization_user o WHERE w.WF_CUREMPLOYEE_ID=o.emp_id  AND (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE is null) AND(w.WORKTABLE_ID<>-1 ) AND(w.WORKLISTCONTROL=1 ) AND(w.WORKDELETE=0)";
      if (whereTmp.equals(""))
        zaiBanSql = String.valueOf(zaiBanSql) + " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        zaiBanSql = String.valueOf(zaiBanSql) + " and " + whereTmp; 
      rs = stmt.executeQuery(zaiBanSql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      obj[2] = Integer.valueOf(recordCount);
      String banJieSql = "SELECT COUNT(w.wf_work_id) AS count1 FROM JSF_WORK w,org_organization_user o WHERE w.WF_CUREMPLOYEE_ID=o.emp_id  AND (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE IS NOT NULL ) AND(w.WORKLISTCONTROL=1 ) AND(w.WORKTABLE_ID<>-1 ) AND(w.WORKDELETE=0)";
      if (whereTmp.equals(""))
        banJieSql = String.valueOf(banJieSql) + " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        banJieSql = String.valueOf(banJieSql) + " and " + whereTmp; 
      rs = stmt.executeQuery(banJieSql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      obj[3] = Integer.valueOf(recordCount);
      list2.add(obj);
      result.put("list2", list2);
      rs.close();
      stmt.close();
      conn.close();
      this.session.close();
    } catch (SQLException e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      throw e;
    } 
    return result;
  }
  
  public Map<String, Object> getFlowStatusData(String orgId, String userId, String rightCode, Integer pageSize, Integer pageNo, String orgName, String orderBy, String searchWhere, String flowStatusType, String userName, String itemIdSql) throws SQLException {
    Map<String, Object> result = new HashMap<String, Object>();
    Connection conn = null;
    Statement stmt = null;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      String whereTmp = "";
      if ("org".equals(flowStatusType)) {
        whereTmp = getRightWhere(userId, orgId, rightCode, "org_id", "emp.emp_id");
      } else {
        ManagerService managerBD = new ManagerService();
        String rightName = "02*05*01";
        whereTmp = managerBD.getRightWhere(userId, 
            orgId, 
            rightName, 
            "org_id", 
            "emp.emp_id");
        if (userName != null)
          whereTmp = String.valueOf(whereTmp) + " and empname like '%" + userName + "%' "; 
      } 
      if (whereTmp.equals(""))
        whereTmp = " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        whereTmp = " and " + whereTmp; 
      if (orgName != null && !"".equals(orgName))
        whereTmp = " and org.orgname like '%" + orgName + "%' "; 
      StringBuffer sql = new StringBuffer("");
      String countSql = "";
      if ("org".equals(flowStatusType)) {
        sql.append(" SELECT  wk.*,cc1+cc2+cc3+cc4 as sumc  FROM( ");
        sql.append(" SELECT  org.orgnameString,");
        sql.append(" CASE WHEN t1.c1 IS NULL THEN 0 ELSE t1.c1 END AS cc1,");
        sql.append(" CASE WHEN t2.c2 IS NULL THEN 0 ELSE t2.c2 END AS cc2,");
        sql.append(" CASE WHEN t3.c3 IS NULL THEN 0 ELSE t3.c3 END AS cc3,");
        sql.append(" CASE WHEN t4.c4 IS NULL THEN 0 ELSE t4.c4 END AS cc4,");
        sql.append(" org.ORGLEVEL,org.ORG_ID,org.orgidstring ");
        sql.append(" FROM org_organization org ");
        sql.append(" LEFT JOIN  ( ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c1,o.org_id AS o1  ");
        sql.append(" FROM JSF_WORK w,org_organization_user o  ");
        sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id  ");
        sql.append(" AND (w.WORKSTATUS=0 )   AND(w.WORKLISTCONTROL=1 ) ");
        sql.append(" AND(w.WORKDELETE=0)");
        sql.append(" AND(w.WORKTABLE_ID<>-1)");
        sql.append(searchWhere);
        sql.append(" GROUP BY o.org_id  ");
        sql.append(" ) t1 ON org.org_id=t1.o1  ");
        sql.append(" LEFT JOIN   (  ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c2,o.org_id AS o2 ");
        sql.append(" FROM JSF_WORK w,org_organization_user o");
        sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id   AND (w.WORKSTATUS=0 ) ");
        sql.append(" AND(w.WORKLISTCONTROL=1 ) ");
        sql.append(" AND(w.WORKDELETE=0) ");
        sql.append(" AND(w.WORKTABLE_ID<>-1) ");
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          sql.append(" AND (w.WORKDEADLINE<>'-1' AND '" + (new Date()).toLocaleString() + "' > w.WORKDEADLINEDATE) ");
        } else {
          sql.append(" AND (w.WORKDEADLINE<>'-1' AND JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > w.WORKDEADLINEDATE) ");
        } 
        sql.append(searchWhere);
        sql.append(" GROUP BY o.org_id ");
        sql.append(" ) t2 ON  org.org_id=t2.o2");
        sql.append(" LEFT JOIN   (  ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c3,o.org_id AS o3   ");
        sql.append(" FROM JSF_WORK w,org_organization_user o  ");
        sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id ");
        sql.append(" AND (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE IS NULL)  ");
        sql.append(" AND(w.WORKLISTCONTROL=1 )   AND(w.WORKDELETE=0) AND (w.WORKTABLE_ID<>-1)");
        sql.append(searchWhere);
        sql.append(" GROUP BY o.org_id ");
        sql.append(" ) t3 ON  org.org_id=t3.o3 ");
        sql.append(" LEFT JOIN   (  ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c4,o.org_id AS o4 ");
        sql.append(" FROM JSF_WORK w,org_organization_user o");
        sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id   AND (w.WORKSTATUS=101 ) ");
        sql.append(" AND (WORKDONEWITHDATE IS NOT NULL ) ");
        sql.append(" AND(w.WORKLISTCONTROL=1 ) ");
        sql.append(" AND(w.WORKTABLE_ID<>-1) ");
        sql.append(" AND(w.WORKDELETE=0) ");
        sql.append(searchWhere);
        sql.append(" GROUP BY o.org_id ");
        sql.append(" ) t4 ON  org.org_id=t4.o4");
        sql.append(" where 1=1 and org.orgstatus=0 " + whereTmp + ")wk ");
        sql.append(("".equals(itemIdSql) || "-1".equals(itemIdSql)) ? "" : ("where org_id in (" + itemIdSql + ")"));
      } else {
        sql.append(" select  wk.*,cc1+cc2+cc3+cc4 as sumc  from( ");
        sql.append(" select  emp.empname,");
        sql.append(" case when t1.c1 is null then 0 else t1.c1 end as cc1,");
        sql.append(" case when t2.c2 is null then 0 else t2.c2 end as cc2,");
        sql.append(" case when t3.c3 is null then 0 else t3.c3 end as cc3,");
        sql.append(" case when t4.c4 is null then 0 else t4.c4 end as cc4,");
        sql.append(" emp.emp_id,ou.ORG_ID ");
        sql.append(" from org_organization_user ou ");
        sql.append(" left join org_employee emp on emp.emp_id=ou.emp_id");
        sql.append(" left join  ( ");
        sql.append(" SELECT COUNT(w.wf_work_id) as c1,w.WF_CUREMPLOYEE_ID as emp1 ");
        sql.append(" FROM JSF_WORK w  ");
        sql.append(" WHERE (w.WORKSTATUS=0 )   AND(w.WORKLISTCONTROL=1 )  AND(w.WORKDELETE=0)");
        sql.append(" AND(w.WORKTABLE_ID<>-1) ");
        sql.append(searchWhere);
        sql.append(" group by w.WF_CUREMPLOYEE_ID ");
        sql.append(" ) t1 on ou.emp_id=t1.emp1 ");
        sql.append(" left join   (  ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c2,w.WF_CUREMPLOYEE_ID as emp2");
        sql.append(" FROM JSF_WORK w  ");
        sql.append(" WHERE ");
        sql.append(" (w.WORKSTATUS=0 ) AND(w.WORKLISTCONTROL=1 ) AND(w.WORKDELETE=0)");
        sql.append(" AND(w.WORKTABLE_ID<>-1) ");
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          sql.append(" AND (w.WORKDEADLINE<>'-1' AND '" + (new Date()).toLocaleString() + "' > w.WORKDEADLINEDATE) ");
        } else {
          sql.append(" AND (w.WORKDEADLINE<>'-1' AND JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > w.WORKDEADLINEDATE) ");
        } 
        sql.append(searchWhere);
        sql.append(" group by w.WF_CUREMPLOYEE_ID  ");
        sql.append(" ) t2 on ou.emp_id=t2.emp2");
        sql.append(" left join   (  ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c3,w.WF_CUREMPLOYEE_ID as emp3  ");
        sql.append(" FROM JSF_WORK w ");
        sql.append(" WHERE ");
        sql.append(" (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE is null)   AND(w.WORKLISTCONTROL=1 )   AND(w.WORKDELETE=0) ");
        sql.append(" AND (w.WORKTABLE_ID<>-1) ");
        sql.append(searchWhere);
        sql.append(" group by w.WF_CUREMPLOYEE_ID  ");
        sql.append(" ) t3 on  ou.emp_id=t3.emp3 ");
        sql.append(" left join   (  ");
        sql.append(" SELECT COUNT(w.wf_work_id) AS c4,w.WF_CUREMPLOYEE_ID as emp4");
        sql.append(" FROM JSF_WORK w  ");
        sql.append(" WHERE ");
        sql.append(" (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE IS NOT NULL )  AND(w.WORKLISTCONTROL=1 ) AND(w.WORKDELETE=0)");
        sql.append(" AND(w.WORKTABLE_ID<>-1) ");
        sql.append(searchWhere);
        sql.append(" group by w.WF_CUREMPLOYEE_ID  ");
        sql.append(" ) t4 on ou.emp_id=t4.emp4");
        sql.append(" where 1=1 and emp.empstatus=0 and userisdeleted=0 " + whereTmp + ")wk ");
        sql.append(("".equals(itemIdSql) || "-1".equals(itemIdSql)) ? "" : ("where emp_id in (" + itemIdSql + ")"));
      } 
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
        if ("org".equals(flowStatusType)) {
          orderBy = " order by orgidstring,ORG_ID ";
        } else {
          orderBy = " order by ORG_ID,emp_id ";
        } 
      } else if ("org".equals(flowStatusType)) {
        orderBy = " order by " + orderBy + " ,orgidstring,ORG_ID";
      } else {
        orderBy = " order by " + orderBy + " ,ORG_ID,emp_id";
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
        obj = new Object[7];
        for (int k0 = 0; k0 < 7; k0++)
          obj[k0] = rs.getString(k0 + 1); 
        reList.add(obj);
      } 
      result.put("reList", reList);
      reList = new ArrayList();
      String recoutSql = "select sum(w.cc1),sum(w.cc2),sum(w.cc3),sum(w.cc4) from (" + reSql + ") w";
      rs = stmt.executeQuery(recoutSql);
      while (rs.next()) {
        obj = new Object[4];
        for (int k0 = 0; k0 < 4; k0++)
          obj[k0] = rs.getString(k0 + 1); 
        reList.add(obj);
      } 
      rs.close();
      result.put("reCountList", reList);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return result;
  }
  
  public Map<String, Object> getFlowDateSpanData(String orgId, String userId, String range, Integer pageSize, Integer pageNo, String flowStatus, String searchOrg, String oprStartTime, String oprEndTime, String searchTime, String orderBy, String items) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    List<String> tableHeadList = new ArrayList<String>();
    Map<String, Integer> sumMap = new HashMap<String, Integer>();
    Map<String, Object> resultMap = new HashMap<String, Object>();
    int recordCount = 0;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      String outWh = "";
      if (searchOrg != null && !"".equals(searchOrg)) {
        searchOrg = searchOrg.substring(1, searchOrg.length() - 1);
        searchOrg = searchOrg.replace("**", ",");
        outWh = " and org.org_id in (" + searchOrg + ")";
      } else {
        range = range.substring(1, range.length() - 1);
        range = range.replace("**", ",");
        if ("0".equals(range)) {
          outWh = " and 1=1 ";
        } else {
          outWh = " and org.org_id in (" + range + ")";
        } 
      } 
      String whereTmp = "";
      whereTmp = getRightWhere(userId, 
          orgId, 
          "02*05*01", 
          "org_id", 
          "emp.emp_id");
      if (whereTmp.equals(""))
        outWh = String.valueOf(outWh) + " and 1<1"; 
      if (whereTmp != null && !whereTmp.equals(""))
        outWh = String.valueOf(outWh) + " and " + whereTmp; 
      if (searchTime == null || "".equals(searchTime)) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        oprStartTime = String.valueOf(year) + "-" + "01";
        oprEndTime = String.valueOf(year) + "-" + month;
      } 
      if (oprStartTime == null || "".equals(oprStartTime)) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        oprStartTime = String.valueOf(year) + "-" + "01";
      } 
      if (oprEndTime == null || "".equals(oprEndTime)) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        oprEndTime = String.valueOf(year) + "-" + month;
      } 
      if (flowStatus == null || "".equals(flowStatus))
        flowStatus = "0"; 
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date beginDate = sf.parse(String.valueOf(oprStartTime) + "-01 00:00:00");
      Date endDate = sf.parse(String.valueOf(oprEndTime) + "-01 00:00:00");
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      int i = 0;
      StringBuffer sql = new StringBuffer("");
      while (!endDate.before(beginDate)) {
        i++;
        tableHeadList.add(sf.format(beginDate).substring(0, 7));
        String inWh = "";
        if ("oracle".equals(databaseType)) {
          inWh = String.valueOf(inWh) + " and w.WORKSUBMITTIME >=to_date('" + sf.format(beginDate) + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          inWh = String.valueOf(inWh) + " and w.WORKSUBMITTIME >='" + sf.format(beginDate) + "'";
        } 
        if ("oracle".equals(databaseType)) {
          inWh = String.valueOf(inWh) + " and w.WORKSUBMITTIME <=to_date('" + sf.format(getNextMonth(beginDate)) + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          inWh = String.valueOf(inWh) + " and w.WORKSUBMITTIME <='" + sf.format(getNextMonth(beginDate)) + "'";
        } 
        if (i > 1)
          sql.append(" left join "); 
        if ("0".equals(flowStatus)) {
          sql.append(" (SELECT  org.org_id,org.orgname,org.orglevel, case when t1.c is null then 0 else t1.c end as c1,'" + sf.format(beginDate) + "' d");
          sql.append(" FROM org_organization org LEFT JOIN  (  ");
          sql.append(" SELECT COUNT(w.wf_work_id) AS c,o.org_id AS o1  ");
          sql.append(" FROM JSF_WORK w,org_organization_user o  ");
          sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id ");
          sql.append(" AND (w.WORKSTATUS=0 ) ");
          sql.append(" AND(w.WORKLISTCONTROL=1 ) ");
          sql.append(" AND(w.WORKDELETE=0)  ");
          sql.append(" AND(w.WORKTABLE_ID<>-1) ");
          sql.append(inWh);
          sql.append(" GROUP BY o.org_id ");
          sql.append(" ) t1 ON org.org_id=t1.o1 ");
          sql.append(" WHERE 1=1 and org.orgstatus=0 ");
          sql.append(outWh);
          sql.append("  ORDER BY org.orgidstring )f" + i);
        } 
        if ("1".equals(flowStatus)) {
          sql.append(" (SELECT  org.org_id,org.orgname,org.orglevel, case when t1.c is null then 0 else t1.c end as c1,'" + sf.format(beginDate) + "' d");
          sql.append(" FROM org_organization org LEFT JOIN  (  ");
          sql.append(" SELECT COUNT(w.wf_work_id) AS c,o.org_id AS o1  ");
          sql.append(" FROM JSF_WORK w,org_organization_user o  ");
          sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id ");
          sql.append(" AND (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE is null)  ");
          sql.append(" AND(w.WORKLISTCONTROL=1 ) ");
          sql.append(" AND(w.WORKDELETE=0)  ");
          sql.append(" AND(w.WORKTABLE_ID<>-1) ");
          sql.append(inWh);
          sql.append(" GROUP BY o.org_id ");
          sql.append(" ) t1 ON org.org_id=t1.o1 ");
          sql.append(" WHERE 1=1 and org.orgstatus=0 ");
          sql.append(outWh);
          sql.append("  ORDER BY org.orgidstring )f" + i);
        } 
        if ("2".equals(flowStatus)) {
          sql.append(" (SELECT org.org_id,org.orgname,org.orglevel,case when t1.c is null then 0 else t1.c end as c1,'" + sf.format(beginDate) + "' d");
          sql.append(" FROM org_organization org LEFT JOIN  (  ");
          sql.append(" SELECT COUNT(w.wf_work_id) AS c,o.org_id AS o1  ");
          sql.append(" FROM JSF_WORK w,org_organization_user o  ");
          sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id ");
          sql.append(" AND (w.WORKSTATUS=101 ) AND (WORKDONEWITHDATE IS NOT NULL ) ");
          sql.append(" AND(w.WORKLISTCONTROL=1 ) AND(w.WORKDELETE=0)");
          sql.append(" AND(w.WORKTABLE_ID<>-1) ");
          sql.append(inWh);
          sql.append(" GROUP BY o.org_id ");
          sql.append(" ) t1 ON org.org_id=t1.o1 ");
          sql.append(" WHERE 1=1 and org.orgstatus=0 ");
          sql.append(outWh);
          sql.append("  ORDER BY org.orgidstring )f" + i);
        } 
        if ("3".equals(flowStatus)) {
          sql.append(" (SELECT org.org_id,org.orgname,org.orglevel,case when t1.c is null then 0 else t1.c end as c1,'" + sf.format(beginDate) + "' d");
          sql.append(" FROM org_organization org LEFT JOIN  (  ");
          sql.append(" SELECT COUNT(w.wf_work_id) AS c,o.org_id AS o1  ");
          sql.append(" FROM JSF_WORK w,org_organization_user o  ");
          sql.append(" WHERE w.WF_CUREMPLOYEE_ID=o.emp_id ");
          sql.append(" AND (w.WORKSTATUS=0 ) ");
          sql.append(" AND(w.WORKLISTCONTROL=1 ) AND(w.WORKDELETE=0)");
          sql.append(" AND(w.WORKTABLE_ID<>-1) ");
          String dataType = SystemCommon.getDatabaseType();
          if (dataType.indexOf("mysql") >= 0) {
            sql.append(" AND (w.WORKDEADLINE<>'-1' AND '" + (new Date()).toLocaleString() + "' > w.WORKDEADLINEDATE) ");
          } else {
            sql.append(" AND (w.WORKDEADLINE<>'-1' AND JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > w.WORKDEADLINEDATE) ");
          } 
          sql.append(inWh);
          sql.append(" GROUP BY o.org_id ");
          sql.append(" ) t1 ON org.org_id=t1.o1 ");
          sql.append(" WHERE 1=1 and org.orgstatus=0 ");
          sql.append(outWh);
          sql.append("  ORDER BY org.orgidstring )f" + i);
        } 
        beginDate = getNextMonth(beginDate);
        if (i > 1)
          sql.append(" ON f1.org_id=f" + i + ".org_id "); 
      } 
      StringBuffer finalSql = new StringBuffer("select f1.org_id,f1.orgname,f1.orglevel");
      finalSql.append(",f1.c1 f1c1 ");
      for (int j = 2; j <= i; j++)
        finalSql.append(",f" + j + ".c1  " + "f" + j + "c1"); 
      finalSql.append(" from ").append(sql);
      if (items != null && !"-1".equals(items))
        finalSql.append(" where f1.org_id in (" + items + ")"); 
      ResultSet rs = stmt.executeQuery("select count(1) from (" + finalSql.toString() + ") wk");
      if (rs.next())
        recordCount = rs.getInt(1); 
      if (orderBy == null) {
        orderBy = "";
      } else {
        orderBy = " order by " + orderBy;
      } 
      String tempSql = "";
      if ("oracle".equals(databaseType)) {
        tempSql = "select wk.*,rownum as rn from (" + finalSql.toString() + orderBy + ") wk ";
      } else {
        tempSql = "select wk.*,'rn' from (" + finalSql.toString() + orderBy + ") wk ";
      } 
      String querySql = "select * from (" + tempSql + ") wkk ";
      if (flag == 1) {
        querySql = String.valueOf(querySql) + " where rn >=" + ((pageNo.intValue() - 1) * pageSize.intValue() + 1) + 
          "and rn <=" + (pageNo.intValue() * pageSize.intValue());
      } else {
        querySql = String.valueOf(querySql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } 
      rs = stmt.executeQuery(querySql);
      while (rs.next()) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", rs.getObject(1));
        map.put("orgName", rs.getObject(2));
        map.put("monthcount", Integer.valueOf(i));
        map.put("orgLevel", rs.getObject(3));
        map.put("m1", Integer.valueOf(rs.getInt(4)));
        sumMap.put("sum1", Integer.valueOf((sumMap.get("sum1") == null) ? 0 : (((Integer)sumMap.get("sum1")).intValue() + rs.getInt(4))));
        for (int k = 2; k <= i; k++) {
          int temp0 = rs.getInt(k + 3);
          map.put("m" + k, Integer.valueOf(rs.getInt(k + 3)));
          int temp01 = rs.getInt(k + 3);
          int temp1 = (sumMap.get("sum" + k) == null) ? 0 : ((Integer)sumMap.get("sum" + k)).intValue();
          int temp2 = rs.getInt(k + 3);
          int temp3 = temp1 + temp2;
          sumMap.put("sum" + k, Integer.valueOf(temp3));
        } 
        resultList.add(map);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    resultMap.put("resultList", resultList);
    resultMap.put("tableHeadList", tableHeadList);
    resultMap.put("sumMap", sumMap);
    resultMap.put("recordCount", Integer.valueOf(recordCount));
    return resultMap;
  }
  
  public Map<String, Object> getFlowEfficAnalyListData(String orgId, String userId, String range, Integer pageSize, Integer pageNo, String processName) throws SQLException {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      Page page_ol = null;
      String sqlHead = " aaa.wfWorkFlowProcessId,aaa.workFlowProcessName,ttable.tableId";
      String table = " com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable  ";
      String where = " where  aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1' ";
      WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
      String whereTmp = processEjbBean.getProcWhereSql(userId, orgId, "1");
      where = String.valueOf(where) + " and ((" + whereTmp + ") or (aaa.createdEmp = " + userId + ")) ";
      if (processName != null && !"".equals(processName))
        where = String.valueOf(where) + " and aaa.workFlowProcessName like '%" + processName + "%' "; 
      String orderBy = " order by aaa.wfWorkFlowProcessId desc,aaa.workFlowProcessName";
      page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
      page_ol.setPageSize(pageSize.intValue());
      page_ol.setcurrentPage(pageNo.intValue());
      List<?> myList = page_ol.getResultList();
      int recordCount = page_ol.getRecordCount();
      Map<String, Object> tempMap = null;
      if (myList != null && myList.size() > 0)
        for (int i = 0; i < myList.size(); i++) {
          Object[] obj = (Object[])myList.get(i);
          tempMap = getFlowEfficAnalyDataByProcessId(obj[0].toString());
          tempMap.put("porcessId", obj[0].toString());
          tempMap.put("porcessName", obj[1].toString());
          resultList.add(tempMap);
        }  
      resultMap.put("recordCount", Integer.valueOf(recordCount));
      resultMap.put("resultList", resultList);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return resultMap;
  }
  
  public Map<String, Object> getFlowEfficAnalyData(String orgId, String userId, String range, Integer pageSize, Integer pageNo, String processId, String orderBy) throws SQLException {
    Map<String, Object> reMap = new HashMap<String, Object>();
    Connection conn = null;
    Statement stmt = null;
    List<Map<String, Comparable>> resultList = new ArrayList<Map<String, Comparable>>();
    DecimalFormat df = new DecimalFormat("0.00");
    BigDecimal youxiaosum = new BigDecimal("0.00");
    BigDecimal wuxiaosum = new BigDecimal("0.00");
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      if (processId != null && !"".equals(processId)) {
        String activtySql = "";
        if ("oracle".equals(databaseType)) {
          activtySql = "  avg(ceil((j.dealwithtime-j.workViewedDate) * 24 * 60)) AS youxiao, avg(ceil((j.workViewedDate-j.WORKCREATEDATE) * 24 * 60))  AS wuxiao ";
        } else {
          activtySql = "  avg(TIMESTAMPDIFF(MINUTE,j.workViewedDate,j.dealwithtime)) AS youxiao,avg(TIMESTAMPDIFF(MINUTE,j.WORKCREATEDATE,j.workViewedDate))  AS wuxiao ";
        } 
        StringBuffer sb = new StringBuffer("");
        sb.append(" SELECT temp.WF_WORKFLOWPROCESS_ID,temp.WORKFLOWPROCESSNAME,temp.wf_activity_id,temp.activityname, ");
        sb.append(" case when m.youxiao is null then 0 else m.youxiao/60 end as yx, ");
        sb.append(" case when m.wuxiao is null then 0 else m.wuxiao/60 end as wx, ");
        sb.append(" case when  m.youxiao+m.wuxiao is null then 0 else m.youxiao/60+m.wuxiao/60 end as sumxiao");
        sb.append(" FROM ( ");
        sb.append(" SELECT p.WF_WORKFLOWPROCESS_ID,p.WORKFLOWPROCESSNAME,a.wf_activity_id,a.activityname  ");
        sb.append(" FROM   jsf_activity a,JSF_WORKFLOWPROCESS p ");
        sb.append(" WHERE a.activitytype=1 ");
        sb.append(" and a.wf_workflowprocess_id=" + processId);
        sb.append(" and a.wf_workflowprocess_id=p.wf_workflowprocess_id");
        sb.append(" AND a.wf_workflowprocess_id IS NOT NULL");
        sb.append(" )temp ");
        sb.append(" LEFT JOIN( ");
        sb.append(" SELECT  initactivity," + activtySql);
        sb.append(" FROM jsf_work j WHERE j.workdonewithdate IS NOT NULL GROUP BY  initactivity ");
        sb.append(" ) m ON m.initactivity=temp.wf_activity_id");
        int recordCount = 0;
        conn = (new DbOpt()).getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select count(1) from (" + sb + ") w");
        if (rs.next())
          recordCount = rs.getInt(1); 
        reMap.put("recordCount", Integer.valueOf(recordCount));
        if (orderBy == null || "".equals(orderBy)) {
          orderBy = " order by  sumxiao desc,WF_WORKFLOWPROCESS_ID";
        } else {
          orderBy = " order by " + orderBy + ", WF_WORKFLOWPROCESS_ID";
        } 
        String tempSql = "";
        if ("oracle".equals(databaseType)) {
          tempSql = "select wk.*,rownum as rn from (" + sb + orderBy + ") wk ";
        } else {
          tempSql = "select wk.*,'rn' from (" + sb + orderBy + ") wk ";
        } 
        String querySql = "select * from (" + tempSql + ") wkk ";
        if (flag == 1) {
          querySql = String.valueOf(querySql) + " where rn >=" + ((pageNo.intValue() - 1) * pageSize.intValue() + 1) + 
            "and rn <=" + (pageNo.intValue() * pageSize.intValue());
        } else {
          querySql = String.valueOf(querySql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
        } 
        rs = stmt.executeQuery(querySql);
        int i = 0;
        while (rs.next()) {
          String activityId = rs.getObject(3).toString();
          String activityName = rs.getObject(4).toString();
          Map<String, Comparable> tempMap = getNoteEffic(activityId);
          double t1 = Double.valueOf(rs.getObject(5).toString()).doubleValue();
          double t2 = Double.valueOf(rs.getObject(6).toString()).doubleValue();
          BigDecimal b1 = new BigDecimal(t1);
          BigDecimal b2 = new BigDecimal(t2);
          BigDecimal b3 = b1.add(b2);
          tempMap.put("youxiao", b1.setScale(2, 4));
          tempMap.put("wuxiao", b2.setScale(2, 4));
          tempMap.put("sumxiao", b3.setScale(2, 4));
          tempMap.put("activityId", activityId);
          tempMap.put("activityName", activityName);
          youxiaosum = youxiaosum.add(b1);
          wuxiaosum = wuxiaosum.add(b2);
          resultList.add(tempMap);
        } 
        rs.close();
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    reMap.put("resultList", resultList);
    reMap.put("youxiaosum", youxiaosum.setScale(2, 4));
    reMap.put("wuxiaosum", wuxiaosum.setScale(2, 4));
    return reMap;
  }
  
  public Map<String, Object> getFlowEfficAnalyDataByProcessId(String processId) throws SQLException {
    Map<String, Object> reMap = new HashMap<String, Object>();
    Connection conn = null;
    Statement stmt = null;
    List<Map<String, Comparable>> processDotList = new ArrayList<Map<String, Comparable>>();
    double youxiaosum = 0.0D, wuxiaosum = 0.0D;
    DecimalFormat df = new DecimalFormat("0.00");
    try {
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      if (processId != null && !"".equals(processId)) {
        String sql = "SELECT a.wf_activity_id,a.activityname FROM  jsf_activity  a WHERE a.wf_workflowprocess_id=" + processId + 
          "  AND a.activitytype=1 ";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String activityId = rs.getObject(1).toString();
          String activityName = rs.getObject(2).toString();
          Map<String, Comparable> tempMap = getNoteEffic(activityId);
          if (tempMap != null) {
            youxiaosum += Double.valueOf(df.format(Double.valueOf(((Comparable)tempMap.get("youxiao")).toString()))).doubleValue();
            wuxiaosum += Double.valueOf(df.format(Double.valueOf(((Comparable)tempMap.get("wuxiao")).toString()))).doubleValue();
            tempMap.put("activityId", activityId);
            tempMap.put("activityName", activityName);
            processDotList.add(tempMap);
          } 
        } 
        rs.close();
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    reMap.put("processDotList", processDotList);
    reMap.put("youxiaosum", df.format(youxiaosum));
    reMap.put("wuxiaosum", df.format(wuxiaosum));
    return reMap;
  }
  
  public Map<String, Comparable> getNoteEffic(String activityId) throws SQLException {
    Map<String, Comparable> reMap = new HashMap<String, Comparable>();
    Connection conn = null;
    Statement stmt = null;
    List<?> proDotList = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      String sql = "";
      if ("oracle".equals(databaseType)) {
        sql = "SELECT  avg(ceil((j.dealwithtime-j.workViewedDate) * 24 * 60)), avg(ceil((j.workViewedDate-j.WORKCREATEDATE) * 24 * 60))  FROM jsf_work j WHERE j.workdonewithdate IS NOT NULL AND  initactivity=" + 
          
          activityId;
      } else {
        sql = "SELECT  avg((UNIX_TIMESTAMP(j.dealwithtime)-UNIX_TIMESTAMP(j.workViewedDate))),avg((UNIX_TIMESTAMP(j.workViewedDate)-UNIX_TIMESTAMP(j.WORKCREATEDATE))) FROM jsf_work j WHERE j.workdonewithdate IS NOT NULL AND  initactivity=" + 
          
          activityId;
      } 
      ResultSet rs = stmt.executeQuery(sql);
      double youxiao = 0.0D, wuxiao = 0.0D;
      if (rs.next()) {
        youxiao = rs.getInt(1) / 60.0D;
        wuxiao = rs.getInt(2) / 60.0D;
      } 
      rs.close();
      reMap.put("youxiao", Double.valueOf(youxiao));
      reMap.put("wuxiao", Double.valueOf(wuxiao));
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return reMap;
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
  
  public Map getProcessEfficBySql(String orgId, String userId, String range, Integer pageSize, Integer pageNo, String processName, String orderBy) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    Map<String, Object> resultMap = new HashMap<String, Object>();
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      String wh = "";
      WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
      WFProcessEJBBean FProcessEJBBean = new WFProcessEJBBean();
      String whereSqlView = FProcessEJBBean.getProcWhereSql(userId, orgId, "dossier");
      String whereSqlOper = FProcessEJBBean.getProcWhereSql(userId, orgId, "dossieroper");
      String whereSqlAdmin = FProcessEJBBean.getProcWhereSql(userId, orgId, "dossieradmin");
      String whereTmp = "(" + whereSqlView + ") or (" + whereSqlOper + ") or (" + whereSqlAdmin + ") ";
      wh = String.valueOf(wh) + " and (" + whereTmp + ") ";
      if (processName != null && !"".equals(processName))
        wh = String.valueOf(wh) + " and aaa.workFlowProcessName like '%" + processName + "%' "; 
      String activtySql = "";
      if ("oracle".equals(databaseType)) {
        activtySql = "  avg(ceil((j.dealwithtime-j.workViewedDate) * 24 * 60)) AS youxiao, avg(ceil((j.workViewedDate-j.WORKCREATEDATE) * 24 * 60))  AS wuxiao ";
      } else {
        activtySql = "  avg(TIMESTAMPDIFF(MINUTE,j.workViewedDate,j.dealwithtime)) AS youxiao,avg(TIMESTAMPDIFF(MINUTE,j.WORKCREATEDATE,j.workViewedDate))  AS wuxiao ";
      } 
      StringBuffer sb = new StringBuffer("");
      sb.append(" SELECT temp.WF_WORKFLOWPROCESS_ID,temp.WORKFLOWPROCESSNAME,temp.wf_activity_id,temp.activityname,");
      sb.append(" case when m.youxiao is null then 0 else m.youxiao end as yx,");
      sb.append(" case when m.wuxiao is null then 0 else m.wuxiao end as wx");
      sb.append(" FROM (");
      sb.append(" SELECT p.WF_WORKFLOWPROCESS_ID,p.WORKFLOWPROCESSNAME,a.wf_activity_id,a.activityname  FROM ");
      sb.append("  jsf_activity a LEFT JOIN  (");
      sb.append(" SELECT aaa.WF_WORKFLOWPROCESS_ID, aaa.WORKFLOWPROCESSNAME");
      sb.append(" FROM JSF_WORKFLOWPROCESS aaa, TAREA ta, TTABLE tb, JSF_PACKAGE tp WHERE (aaa.ACCESSDATABASEID=ta.PAGE_ID )");
      sb.append(" AND(tb.TABLE_NAME=ta.AREA_TABLE )AND(tp.WF_MODULE_ID=1  AND aaa.WF_PACKAGE_ID=tp.WF_PACKAGE_ID)");
      sb.append(" AND(ta.AREA_NAME='form1' )");
      sb.append(wh);
      sb.append(" ) p");
      sb.append(" ON a.wf_workflowprocess_id=p.wf_workflowprocess_id ");
      sb.append(" WHERE a.activitytype=1 AND a.wf_workflowprocess_id IS NOT NULL");
      sb.append(" AND a.wf_workflowprocess_id IN (");
      sb.append(" SELECT aaa.WF_WORKFLOWPROCESS_ID");
      sb.append(" FROM JSF_WORKFLOWPROCESS aaa, TAREA ta, TTABLE tb, JSF_PACKAGE tp WHERE (aaa.ACCESSDATABASEID=ta.PAGE_ID )");
      sb.append(" AND(tb.TABLE_NAME=ta.AREA_TABLE )AND(tp.WF_MODULE_ID=1  AND aaa.WF_PACKAGE_ID=tp.WF_PACKAGE_ID)");
      sb.append(" AND(ta.AREA_NAME='form1' )");
      sb.append(wh);
      sb.append(" )");
      sb.append(" )temp ");
      sb.append(" LEFT JOIN( ");
      sb.append(" SELECT  initactivity," + activtySql);
      sb.append(" FROM jsf_work j WHERE j.workdonewithdate IS NOT NULL GROUP BY  initactivity");
      sb.append(" ) m ON m.initactivity=temp.wf_activity_id");
      String sql = "select  p.WF_WORKFLOWPROCESS_ID,max(p.WORKFLOWPROCESSNAME) as WORKFLOWPROCESSNAME,sum(yx)/60 as youxiao,sum(wx)/60 as wuxiao,(sum(yx)/60 +sum(wx)/60) as sumxiao from (" + 
        sb + ") p group by p.WF_WORKFLOWPROCESS_ID";
      int recordCount = 0;
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select count(1) from (" + sql + ") wk");
      if (rs.next())
        recordCount = rs.getInt(1); 
      if (orderBy == null || "".equals(orderBy)) {
        orderBy = " order by  sumxiao desc,p.WF_WORKFLOWPROCESS_ID";
      } else {
        orderBy = " order by " + orderBy + ", p.WF_WORKFLOWPROCESS_ID";
      } 
      String tempSql = "";
      if ("oracle".equals(databaseType)) {
        tempSql = "select wk.*,rownum as rn from (" + sql + orderBy + ") wk ";
      } else {
        tempSql = "select wk.*,'rn' from (" + sql + orderBy + ") wk ";
      } 
      String querySql = "select * from (" + tempSql + ") wkk ";
      if (flag == 1) {
        querySql = String.valueOf(querySql) + " where rn >=" + ((pageNo.intValue() - 1) * pageSize.intValue() + 1) + 
          "and rn <=" + (pageNo.intValue() * pageSize.intValue());
      } else {
        querySql = String.valueOf(querySql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } 
      rs = stmt.executeQuery(querySql);
      Map<String, Object> tempMap = null;
      DecimalFormat df = new DecimalFormat("##0.00");
      while (rs.next()) {
        tempMap = new HashMap<String, Object>();
        tempMap.put("porcessId", rs.getObject(1));
        double t1 = Double.valueOf(rs.getObject(3).toString()).doubleValue();
        double t2 = Double.valueOf(rs.getObject(4).toString()).doubleValue();
        BigDecimal b1 = new BigDecimal(t1);
        BigDecimal b2 = new BigDecimal(t2);
        BigDecimal b3 = b1.add(b2);
        String s3 = b3.setScale(2, 4).toString();
        tempMap.put("porcessName", rs.getObject(2));
        tempMap.put("youxiao", b1.setScale(2, 4));
        tempMap.put("wuxiao", b2.setScale(2, 4));
        tempMap.put("sumxiao", b3.setScale(2, 4));
        resultList.add(tempMap);
      } 
      resultMap.put("recordCount", Integer.valueOf(recordCount));
      resultMap.put("resultList", resultList);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return resultMap;
  }
  
  public Map<String, Object> getFlowEfficAnalyUserData(String orgId, String userId, String range, Integer pageSize, Integer pageNo, String activityId, String orderBy) throws SQLException {
    Map<String, Object> reMap = new HashMap<String, Object>();
    Connection conn = null;
    Statement stmt = null;
    List<Map<String, Comparable>> resultList = new ArrayList<Map<String, Comparable>>();
    DecimalFormat df = new DecimalFormat("0.00");
    BigDecimal youxiaosum = new BigDecimal("0.00");
    BigDecimal wuxiaosum = new BigDecimal("0.00");
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int flag = 0;
      if (databaseType.indexOf("oracle") > -1) {
        flag = 1;
      } else if (databaseType.indexOf("mysql") > -1) {
        flag = 2;
      } 
      if (activityId != null && !"".equals(activityId)) {
        String activtySql = "";
        if ("oracle".equals(databaseType)) {
          activtySql = "  avg(ceil((j.dealwithtime-j.workViewedDate) * 24 * 60)) AS youxiao, avg(ceil((j.workViewedDate-j.WORKCREATEDATE) * 24 * 60))  AS wuxiao ";
        } else {
          activtySql = "  avg(TIMESTAMPDIFF(MINUTE,j.workViewedDate,j.dealwithtime)) AS youxiao,avg(TIMESTAMPDIFF(MINUTE,j.WORKCREATEDATE,j.workViewedDate))  AS wuxiao ";
        } 
        StringBuffer sb = new StringBuffer("");
        sb.append(" SELECT e.emp_id,e.empname,  ");
        sb.append(" case when m.youxiao is null then 0 else m.youxiao/60 end as yx, ");
        sb.append(" case when m.wuxiao is null then 0 else m.wuxiao/60 end as wx, ");
        sb.append(" case when  m.youxiao+m.wuxiao is null then 0 else m.youxiao/60+m.wuxiao/60 end as sumxiao");
        sb.append(" FROM ( ");
        sb.append(" SELECT j.WF_CUREMPLOYEE_ID,  ");
        sb.append(activtySql);
        sb.append(" FROM jsf_work j ");
        sb.append(" WHERE j.workdonewithdate  IS NOT NULL  ");
        sb.append(" AND j.initactivity=" + activityId);
        sb.append(" GROUP BY  j.WF_CUREMPLOYEE_ID   ");
        sb.append(" ) m ,org_employee e WHERE m.WF_CUREMPLOYEE_ID= e.emp_id");
        int recordCount = 0;
        conn = (new DbOpt()).getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select count(1) from (" + sb + ") w");
        if (rs.next())
          recordCount = rs.getInt(1); 
        reMap.put("recordCount", Integer.valueOf(recordCount));
        if (orderBy == null || "".equals(orderBy)) {
          orderBy = " order by  sumxiao desc,empname";
        } else {
          orderBy = " order by " + orderBy + ", empname";
        } 
        String tempSql = "";
        if ("oracle".equals(databaseType)) {
          tempSql = "select wk.*,rownum as rn from (" + sb + orderBy + ") wk ";
        } else {
          tempSql = "select wk.*,'rn' from (" + sb + orderBy + ") wk ";
        } 
        String querySql = "select * from (" + tempSql + ") wkk ";
        if (flag == 1) {
          querySql = String.valueOf(querySql) + " where rn >=" + ((pageNo.intValue() - 1) * pageSize.intValue() + 1) + 
            "and rn <=" + (pageNo.intValue() * pageSize.intValue());
        } else {
          querySql = String.valueOf(querySql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
        } 
        rs = stmt.executeQuery(querySql);
        int i = 0;
        while (rs.next()) {
          String empId = rs.getObject(1).toString();
          String empName = rs.getObject(2).toString();
          Map<String, Comparable> tempMap = new HashMap<String, Comparable>();
          double t1 = Double.valueOf(rs.getObject(3).toString()).doubleValue();
          double t2 = Double.valueOf(rs.getObject(4).toString()).doubleValue();
          BigDecimal b1 = new BigDecimal(t1);
          BigDecimal b2 = new BigDecimal(t2);
          BigDecimal b3 = b1.add(b2);
          tempMap.put("youxiao", b1.setScale(2, 4));
          tempMap.put("wuxiao", b2.setScale(2, 4));
          tempMap.put("sumxiao", b3.setScale(2, 4));
          tempMap.put("empId", empId);
          tempMap.put("empName", empName);
          tempMap.put("activityId", activityId);
          youxiaosum = youxiaosum.add(b1);
          wuxiaosum = wuxiaosum.add(b2);
          resultList.add(tempMap);
        } 
        rs.close();
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    reMap.put("resultList", resultList);
    reMap.put("youxiaosum", youxiaosum.setScale(2, 4));
    reMap.put("wuxiaosum", wuxiaosum.setScale(2, 4));
    return reMap;
  }
}
