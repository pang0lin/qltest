package com.js.wap.bean;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.scheme.event.po.EventPO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.sql.DataSource;
import net.sf.hibernate.Query;

public class WapBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getCoopListByEmpId(String empId, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List coopList = null;
    try {
      begin();
      StringBuffer hql = new StringBuffer(" SELECT aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime,");
      hql.append(" aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId,");
      hql.append(" aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile,");
      hql.append(" aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId,");
      hql.append(" aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,");
      hql.append(" aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId,");
      hql.append(" aaa.processDeadlineDate,aaa.wfCurEmployeeId");
      hql.append(" FROM  com.js.oa.jsflow.po.WFWorkPO aaa  ");
      hql.append(" where aaa.workStatus=0 and aaa.wfCurEmployeeId=" + empId + " and aaa.workListControl=1 and aaa.workDelete=0 ");
      hql.append(" order by aaa.emergence desc, aaa.wfWorkId desc");
      Query query = this.session.createQuery(hql.toString());
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      coopList = query.list();
      Query querySize = this.session.createQuery(hql.toString());
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", coopList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public Map getCoopInfoByEmpId(String empId) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    try {
      begin();
      String hql = " from BodyPO as a where a.id=" + empId;
      String hql1 = " from BodyExPO as b where b.bodyId=" + empId;
      String hql2 = " from AttachPO as c where  c.bodyId=" + empId;
      Query query = this.session.createQuery(hql);
      Query query1 = this.session.createQuery(hql1);
      Query query2 = this.session.createQuery(hql2);
      queryMap.put("coopInfo", (query.uniqueResult() == null) ? null : query.uniqueResult());
      queryMap.put("coopInfoBug", query1.list());
      queryMap.put("coopInfoAffix", query2.list());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public Map getEventListByEmpId(String empId, String domainId, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List eventList = null;
    try {
      begin();
      StringBuffer sql = new StringBuffer("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders ");
      sql.append(" where eventAttenders.empId=" + empId);
      sql.append(" and event.eventDomainId=" + domainId);
      sql.append("  order by event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc ");
      Query query = this.session.createQuery(sql.toString());
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      eventList = query.list();
      Query querySize = this.session.createQuery(sql.toString());
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", eventList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public Map getUnderEventList(String empsStr, String domainId, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List eventList = null;
    try {
      begin();
      StringBuffer sql = new StringBuffer("select distinct event from EventPO event join event.eventAttenders eventAttenders ");
      sql.append(" where eventAttenders.empId in(" + empsStr + ")");
      sql.append(" and event.eventDomainId=" + domainId);
      sql.append("  order by event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc ");
      Query query = this.session.createQuery(sql.toString());
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      eventList = query.list();
      Query querySize = this.session.createQuery(sql.toString());
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", eventList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public EventPO getEventByEventId(Long eventId) throws Exception {
    EventPO event = null;
    try {
      begin();
      String sql = "select event from EventPO event, where event.eventId=" + eventId;
      Query query = this.session.createQuery(sql);
      event = query.list().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return event;
  }
  
  public List getProjectListByRangeParam(String para, String from, String where) throws Exception {
    begin();
    List projectList = null;
    try {
      Query query = this.session.createQuery(String.valueOf(para) + from + where);
      projectList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return projectList;
  }
  
  public Map getMeetListByRangeParam(String para, String from, String where, int beginIndex, int limited) throws Exception {
    begin();
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List meetingList = null;
    try {
      Query query = this.session.createQuery(String.valueOf(para) + from + where);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      meetingList = query.list();
      Query querySize = this.session.createQuery(String.valueOf(para) + from + where);
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", meetingList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public List getMeetInfoById(String para) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery(" from BoardRoomApplyPO po where po.boardroomApplyId=" + para);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Map getReportByDay(String hql, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List reportDayList = null;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      reportDayList = query.list();
      Query querySize = this.session.createQuery(hql);
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", reportDayList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public Map getReportByWeek(String hql, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List reportWeekList = null;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      reportWeekList = query.list();
      Query querySize = this.session.createQuery(hql);
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", reportWeekList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public Map getReportByMonth(String hql, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List reportMonthList = null;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      reportMonthList = query.list();
      Query querySize = this.session.createQuery(hql);
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", reportMonthList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public Map getNewsList(String channelId, String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited) throws Exception {
    List<Object[]> list = new ArrayList();
    int recordCount = 0;
    Calendar date = Calendar.getInstance();
    String today = String.valueOf(date.get(1)) + "/" + (date.get(2) + 1) + "/" + date.get(5);
    StringBuffer buffer = new StringBuffer();
    List<Object[]> groupList = list;
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    begin();
    try {
      InformationChannelPO informationChannelPO = new InformationChannelPO();
      Iterator<InformationChannelPO> it = this.session.createQuery("select po from InformationChannelPO po where po.channelIdString like '%$" + channelId + "$%'").iterate();
      if (it.hasNext()) {
        informationChannelPO = it.next();
      } else {
        return queryMap;
      } 
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer();
      buffer.append("select aaa.channelName, bbb.informationId, bbb.informationTitle, ");
      buffer.append(" bbb.informationKits, bbb.informationIssueTime, bbb.informationHead,bbb.informationType,aaa.channelType,aaa.channelId,aaa.channelShowType,bbb.titleColor,aaa.userDefine,bbb.isConf,bbb.informationHeadFile,bbb.orderCodeTemp,bbb.topTimeEnd,");
      buffer.append(" bbb.informationIssuer ");
      buffer.append(" from com.js.oa.info.infomanager.po.InformationPO bbb join ");
      buffer.append(" bbb.informationChannel aaa ");
      String sql = buffer.toString();
      buffer = new StringBuffer();
      list = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '知识维护' and  bbb.rightName = '维护'  and ccc.empId = " + 
          userId).list();
      boolean allScope = false;
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            buffer.append(" aaa.createdEmp = ").append(userId).append(" or ");
          } else if (scopeType.equals("2")) {
            List orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + orgId + "$%'").list();
            for (int j = 0; j < orgList.size(); j++)
              buffer.append(" aaa.createdOrg = ").append(orgList.get(j)).append(" or "); 
          } else if (scopeType.equals("3")) {
            buffer.append(" aaa.createdOrg = ").append(orgId).append(" or ");
          } else if (scopeType.equals("4")) {
            String scopeScope = (obj[1] == null) ? "" : obj[1].toString();
            if (!scopeScope.equals("")) {
              try {
                scopeScope = getJuniorOrg(scopeScope);
              } catch (Exception exception) {}
              buffer.append(" aaa.createdOrg in (").append(scopeScope).append(") or ");
            } 
          } 
        } else {
          allScope = true;
        } 
      } 
      buffer.append("(");
      int i;
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" aaa.channelReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" aaa.channelReaderGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      buffer.append(" aaa.channelReader like '%$").append(userId).append("$%' ");
      buffer.append(" or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='')))");
      buffer.append(" and ((");
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" bbb.informationReaderOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" bbb.informationReaderGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      buffer.append(" bbb.informationReader like '%$").append(userId).append("$%' ");
      buffer.append(") or ((bbb.informationReader is null or bbb.informationReader='') and (bbb.informationReaderOrg is null or bbb.informationReaderOrg='') and (bbb.informationReaderGroup is null or bbb.informationReaderGroup='')))");
      String where = buffer.toString();
      sql = String.valueOf(sql) + 
        
        " where aaa.domainId=" + domainId + " and (" + where;
      if (allScope)
        sql = String.valueOf(sql) + " or (1=1)"; 
      sql = String.valueOf(sql) + ") and (aaa.channelIdString like '%$" + channelId + "$%'";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + " or bbb.otherChannel like '%," + channelId + 
          ",%') and bbb.informationStatus=0 and (bbb.informationValidType=0 or ( '" + today + "' >=bbb.validBeginTime and '" + today + "'<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ";
      } else {
        sql = String.valueOf(sql) + " or bbb.otherChannel like '%," + channelId + 
          ",%') and bbb.informationStatus=0 and (bbb.informationValidType=0 or (JSDB.FN_STRTODATE('" + 
          today + 
          "','S')>=bbb.validBeginTime and JSDB.FN_STRTODATE('" + 
          today + "','S')<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ";
      } 
      Query query = this.session.createQuery(sql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      list = query.list();
      Query querySize = this.session.createQuery(sql);
      recordCount = querySize.list().size();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return queryMap;
  }
  
  public Map getReportContentByWeek(String userId, String orgId, String orgIdString, String domainId, String reportType, String id) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    try {
      begin();
      Query query = this.session.createQuery(" from WorkReportPO wp where  wp.id=" + id + " and wp.reportType=" + reportType);
      List reportList = query.list();
      Query query1 = this.session.createQuery(" from WorkReportPostilPO wpp where wpp.report=" + id);
      List commnetList = query1.list();
      queryMap.put("REPORTlIST", reportList);
      queryMap.put("COMMENTlIST", commnetList);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return queryMap;
  }
  
  public WorkReportPO getReportById(String userId, Long domainId, String id) throws Exception {
    WorkReportPO wrpo = null;
    try {
      begin();
      Query query = this.session.createQuery("select wp from com.js.oa.scheme.workreport.po.WorkReportPO wp where wp.id=" + id + " and ((wp.reportReaderId like '%$" + userId + "$%') or wp.empId=" + userId + ")");
      List<WorkReportPO> reportList = query.list();
      Iterator<WorkReportPO> it = reportList.iterator();
      if (it.hasNext())
        wrpo = it.next(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return wrpo;
  }
  
  private String getJuniorOrg(String range) throws Exception {
    StringBuffer buffer = new StringBuffer(" where ");
    range = "*" + range + "*";
    String[] rangeArray = range.split("\\*\\*");
    int i = 0;
    for (i = 0; i < rangeArray.length; i++) {
      if (i > 0)
        buffer.append(" or "); 
      buffer.append(" org.orgIdString like '%$");
      buffer.append(rangeArray[i]);
      buffer.append("$%' ");
    } 
    List list = this.session.createQuery(
        "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org " + 
        buffer.toString()).list();
    buffer = new StringBuffer();
    for (i = 0; i < list.size(); i++)
      buffer.append(list.get(i)).append(","); 
    range = buffer.toString();
    i = range.length();
    if (i > 0) {
      range = range.substring(0, i - 1);
    } else {
      range = "-1";
    } 
    return range;
  }
  
  public static int getRowCount() throws SQLException {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    conn = ds.getConnection();
    int rowCount = 0;
    try {
      stmt = conn.createStatement();
      String sql = "select count(ORG_ID) as rowCounts from org_organization where (1=1) and orgstatus=0";
      rs = stmt.executeQuery(sql);
      rs.next();
      rowCount = rs.getInt("rowCounts");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return rowCount;
  }
  
  public static String[][] getOrgs(String res, String table, String where) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String[][] ress = (String[][])null;
    try {
      String sql = "select " + res + " from " + table + " where (1=1)" + where;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (getRowCount() > 0)
        ress = new String[getRowCount()][2]; 
      int i = 0;
      while (rs.next()) {
        ress[i][0] = String.valueOf(rs.getLong(1));
        ress[i][1] = rs.getString(2);
        i++;
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return ress;
  }
  
  public static Map<String, String> getWapList(String res, String table, String where) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    Map<String, String> map = new Hashtable<String, String>();
    try {
      String sql = "select " + res + " from " + table + " where (1=1)" + where;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next())
        map.put(String.valueOf(rs.getLong(1)), rs.getString(2)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return map;
  }
}
