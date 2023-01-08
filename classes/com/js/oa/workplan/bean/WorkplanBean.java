package com.js.oa.workplan.bean;

import com.js.oa.workplan.po.WorkplanPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class WorkplanBean extends HibernateBase {
  public boolean saveWorkplan(List<WorkplanPO> planList) throws Exception {
    boolean result = false;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      try {
        String sql;
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "delete from oa_workplan where leaderId=? and workdate=?";
        } else {
          sql = "delete from oa_workplan where leaderId=? and to_char(workdate, 'YYYY-MM-DD' )=?";
        } 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int j = 0; j < planList.size(); j++) {
          WorkplanPO po = planList.get(j);
          pstmt.setLong(1, Long.valueOf(po.getLeaderId()).longValue());
          pstmt.setString(2, DateHelper.date2String(po.getWorkDate()));
          pstmt.execute();
        } 
        pstmt.close();
        conn.close();
      } catch (Exception err) {
        if (conn != null)
          conn.close(); 
        err.printStackTrace();
      } 
      begin();
      for (int i = 0; i < planList.size(); i++) {
        WorkplanPO po = planList.get(i);
        this.session.save(po);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean updateWorkplan(WorkplanPO po) throws Exception {
    boolean result = false;
    WorkplanPO oldpo = new WorkplanPO();
    try {
      begin();
      oldpo = (WorkplanPO)this.session.load(WorkplanPO.class, Long.valueOf(po.getId()));
      oldpo.setWorkType(po.getWorkType());
      oldpo.setWorkStatus(po.getWorkStatus());
      oldpo.setWorkPlace(po.getWorkPlace());
      oldpo.setDescription(po.getDescription());
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public List getWorkplanListOfWeek(String groupId, String yearMonth) throws Exception {
    List<WorkplanPO> planList = new ArrayList();
    try {
      begin();
      for (int i = 0; i < planList.size(); i++) {
        WorkplanPO po = planList.get(i);
        this.session.save(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return planList;
  }
  
  public WorkplanPO loadWorkplan(String workplanId) throws Exception {
    WorkplanPO po = new WorkplanPO();
    try {
      begin();
      po = (WorkplanPO)this.session.load(WorkplanPO.class, Long.valueOf(workplanId));
      this.session.close();
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return po;
  }
  
  public boolean deleteWorkplan(String workplanId) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.delete("from com.js.oa.workplan.po.WorkplanPO po where po.id in (" + workplanId + ")");
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public String[] getLeaderInfoById(String leaderId) {
    String[] leaderInfo = { "", "", "" };
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT oe.empname,oe.empduty,oo.orgname FROM org_employee oe,org_organization_user ou,org_organization oo WHERE oe.emp_id=ou.emp_id AND ou.org_id=oo.org_id AND oe.emp_id=" + leaderId);
      if (rs.next()) {
        leaderInfo[0] = rs.getString(1);
        leaderInfo[1] = rs.getString(2);
        leaderInfo[2] = rs.getString(3);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return leaderInfo;
  }
  
  public String checkData(String cdate, String leaderId) throws Exception {
    String result = "";
    Connection conn = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String[] dates = cdate.split(",");
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      for (int i = 0; i < dates.length; i++) {
        String sql;
        if (databaseType.indexOf("oracle") >= 0) {
          sql = "SELECT id from oa_workplan WHERE  leaderId=" + leaderId + " and to_char(workdate, 'YYYY-MM-DD')='" + dates[i] + "'";
        } else {
          sql = "SELECT id from oa_workplan WHERE  leaderId=" + leaderId + " and workdate='" + dates[i] + "'";
        } 
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
          result = String.valueOf(result) + dates[i] + ","; 
        rs.close();
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    if (result.length() > 1)
      result = result.substring(0, result.length() - 1); 
    return result;
  }
  
  public List searchWorkplanList(String groupId, String workYearWeek, List flist) throws Exception {
    List<String[]> workplanList = new ArrayList();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String leaderIds = "";
      String sql = "select groupleaderId from oa_workplangroup where id=" + groupId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        leaderIds = rs.getString(1); 
      rs.close();
      String fleaderIds = "";
      Iterator<E> it = flist.iterator();
      while (it.hasNext())
        fleaderIds = String.valueOf(fleaderIds) + "$" + it.next().toString() + "$"; 
      List<String> leaderList = new ArrayList();
      List<String> leaderNameList = new ArrayList();
      List<String> leaderDutyList = new ArrayList();
      List<String> leaderOrgList = new ArrayList();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "select oe.emp_Id,oe.empname,oe.empDuty,oo.orgsimplename from org_employee oe,org_organization_user ou,org_organization oo where oe.emp_id=ou.emp_id and ou.org_id=oo.org_id and '" + 
          fleaderIds + "' like '%$'||oe.emp_Id||'$%' order by oo.orgIdstring,oe.USERORDERCODE ,oe.EMPDUTYLEVEL ,oe.EMPNAME ";
      } else {
        sql = "select oe.emp_Id,oe.empname,oe.empDuty,oo.orgsimplename from org_employee oe,org_organization_user ou,org_organization oo where oe.emp_id=ou.emp_id and ou.org_id=oo.org_id and '" + 
          fleaderIds + "' like CONCAT('%$',oe.emp_id,'$%') order by oo.orgIdstring,oe.USERORDERCODE ,oe.EMPDUTYLEVEL ,oe.EMPNAME";
      } 
      System.out.println("sql:" + sql);
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        leaderList.add(rs.getString(1));
        leaderNameList.add(rs.getString(2));
        String empDuty = rs.getString(3);
        if (empDuty == null || "null".equals(empDuty))
          empDuty = ""; 
        leaderDutyList.add(empDuty);
        String empSimple = rs.getString(4);
        if (empSimple == null || "null".equals(empSimple))
          empSimple = ""; 
        leaderOrgList.add(empSimple);
      } 
      rs.close();
      for (int i = 0; i < leaderList.size(); i++) {
        String[] weekPlan = new String[46];
        weekPlan[0] = leaderList.get(i).toString();
        weekPlan[1] = leaderNameList.get(i).toString();
        weekPlan[2] = leaderDutyList.get(i).toString();
        weekPlan[3] = leaderOrgList.get(i).toString();
        weekPlan[4] = "";
        weekPlan[5] = "";
        weekPlan[6] = "";
        weekPlan[7] = "";
        weekPlan[8] = "";
        weekPlan[9] = "";
        weekPlan[10] = "";
        weekPlan[11] = "";
        weekPlan[12] = "";
        weekPlan[13] = "";
        weekPlan[14] = "";
        weekPlan[15] = "";
        weekPlan[16] = "";
        weekPlan[17] = "";
        weekPlan[18] = "";
        weekPlan[19] = "";
        weekPlan[20] = "";
        weekPlan[21] = "";
        weekPlan[22] = "";
        weekPlan[23] = "";
        weekPlan[24] = "";
        weekPlan[25] = "";
        weekPlan[26] = "";
        weekPlan[27] = "";
        weekPlan[28] = "";
        weekPlan[29] = "";
        weekPlan[30] = "";
        weekPlan[31] = "";
        weekPlan[32] = "";
        weekPlan[33] = "";
        weekPlan[34] = "";
        weekPlan[35] = "";
        weekPlan[36] = "";
        weekPlan[37] = "";
        weekPlan[38] = "";
        weekPlan[39] = "";
        weekPlan[40] = "";
        weekPlan[41] = "";
        weekPlan[42] = "";
        weekPlan[43] = "";
        weekPlan[44] = "";
        weekPlan[45] = "";
        sql = "select id,leaderName,dutyname,orgname,workDate,workType,workstatus,workplace,description from oa_workplan where leaderId=" + leaderList.get(i) + " and workyearmonth=" + workYearWeek + " order by workdate";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String workplanId = rs.getString("id");
          String leaderName = rs.getString("leaderName");
          String dutyName = rs.getString("dutyname");
          String orgName = rs.getString("orgName");
          Date workDate = rs.getDate("workdate");
          String workType = rs.getString("workType");
          String workStatus = rs.getString("workstatus");
          String workPlace = rs.getString("workplace");
          String description = rs.getString("description");
          int weekDay = getWeekDay(workDate);
          int weekDayIndex = 2 * (weekDay + 1);
          if ("上午".equals(workType)) {
            weekPlan[weekDayIndex] = workStatus;
            weekPlan[weekDayIndex + 14] = workPlace;
            weekPlan[weekDayIndex + 28] = description;
            continue;
          } 
          weekPlan[weekDayIndex + 1] = workStatus;
          weekPlan[weekDayIndex + 15] = workPlace;
          weekPlan[weekDayIndex + 29] = description;
        } 
        rs.close();
        workplanList.add(weekPlan);
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return workplanList;
  }
  
  public int getWeekDay(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int day = cal.get(7);
    if (day == 1) {
      day = 7;
    } else {
      day--;
    } 
    return day;
  }
}
