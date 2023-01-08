package com.js.oa.weixin.common.bean;

import com.js.oa.bbs.po.ForumPO;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.po.ChatUserPO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import com.js.wap.bean.PersonalMessageBean;
import com.js.wap.util.WapUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.apache.log4j.Logger;

public class WeiXinBean extends HibernateBase {
  Logger log = Logger.getLogger(PersonalMessageBean.class);
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public Map getNewsList(String channelId, String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited, String keyword) throws Exception {
    List<Object[]> list = new ArrayList();
    int size = 0;
    int recordCount = 0;
    Calendar date = Calendar.getInstance();
    String today = String.valueOf(date.get(1)) + "/" + (date.get(2) + 1) + "/" + date.get(5);
    StringBuffer buffer = new StringBuffer();
    List<Object[]> groupList = list;
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    begin();
    try {
      InformationChannelPO informationChannelPO = new InformationChannelPO();
      Iterator<InformationChannelPO> it = this.session.createQuery("select po from InformationChannelPO po where po.channelIdString like '%$" + 
          channelId + "$%'").iterate();
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
      buffer.append(" bbb.informationIssuer,bbb.informationIssuerId ");
      buffer.append(" from com.js.oa.info.infomanager.po.InformationPO bbb join ");
      buffer.append(" bbb.informationChannel aaa ");
      String sql = buffer.toString();
      buffer = new StringBuffer();
      list = this.session.createQuery(
          " select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '知识维护' and  bbb.rightName = '维护'  and ccc.empId = " + 
          userId).list();
      boolean allScope = false;
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            buffer.append(" aaa.createdEmp = ").append(userId).append(" or ");
          } else if (scopeType.equals("2")) {
            List orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                orgId + "$%'").list();
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
          buffer.append(" aaa.channelReaderOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
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
      sql = String.valueOf(sql) + " where aaa.domainId=" + domainId + keyword + " and (" + where;
      if (allScope)
        sql = String.valueOf(sql) + " or (1=1)"; 
      sql = String.valueOf(sql) + ") and (aaa.channelIdString like '%$" + channelId + "$%'";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + " or bbb.otherChannel like '%," + channelId + ",%') and bbb.informationStatus=0 and (bbb.informationValidType=0 or ( '" + 
          today + "' >=bbb.validBeginTime and '" + today + 
          "'<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ";
      } else {
        sql = String.valueOf(sql) + " or bbb.otherChannel like '%," + channelId + ",%') and bbb.informationStatus=0 and (bbb.informationValidType=0 or (JSDB.FN_STRTODATE('" + 
          today + "','S')>=bbb.validBeginTime and JSDB.FN_STRTODATE('" + today + 
          "','S')<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ";
      } 
      Query query = this.session.createQuery(sql);
      size = query.list().size();
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      list = query.list();
      Query querySize = this.session.createQuery(sql);
      recordCount = querySize.list().size();
      queryMap.put("QUERY_LIST", list);
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
    List list = this.session.createQuery("SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org " + 
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
  
  public Map getEventListByEmpId(String empId, String domainId, int beginIndex, int limited, String keyword) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List eventList = null;
    try {
      begin();
      StringBuffer sql = new StringBuffer("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders ");
      sql.append(" where eventAttenders.empId=" + empId);
      sql.append(" and event.eventDomainId=" + domainId);
      if (keyword != null && !"".equals(keyword))
        sql.append(" and event.eventTitle like '%" + keyword + "%'"); 
      sql.append("  order by event.eventId desc,event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc ");
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
  
  public Map getUnderEventList(String empsStr, String domainId, int beginIndex, int limited, String keyword) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List eventList = null;
    try {
      begin();
      StringBuffer sql = new StringBuffer("select distinct event from EventPO event join event.eventAttenders eventAttenders ");
      sql.append(" where eventAttenders.empId in(" + empsStr + ")");
      sql.append(" and event.eventDomainId=" + domainId);
      if (keyword != null && !"".equals(keyword))
        sql.append(" and (event.eventTitle like '%" + keyword + "%' or event.eventEmpName like '%" + keyword + "%')"); 
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
  
  public Map getChannelList(String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited, String keyword) {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List channelList = null;
    List groupList = new ArrayList();
    int size = 0;
    try {
      begin();
      StringBuffer sql = new StringBuffer("select po from com.js.oa.info.infomanager.po.InformationPO po left join po.informationChannel icpo where icpo.channelType<>1");
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      sql.append(" and (");
      int i;
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          sql.append(" icpo.channelReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        sql.append(" icpo.channelReaderGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      sql.append(" icpo.channelReader like '%$").append(userId).append("$%' ");
      sql.append(" or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='')))");
      sql.append(" and ((");
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          sql.append(" po.informationReaderOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        sql.append(" po.informationReaderGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      sql.append(" po.informationReader like '%$").append(userId).append("$%' ");
      sql.append(") or ((po.informationReader is null or po.informationReader='') and (po.informationReaderOrg is null or po.informationReaderOrg='') and (po.informationReaderGroup is null or po.informationReaderGroup='')))");
      sql.append(" and po.domainId=" + domainId);
      if (keyword != null && !"".equals(keyword))
        sql.append(" and po.informationTitle like '%" + keyword + "%'"); 
      if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
        sql.append(" and po.informationIssueTime<=sysdate");
      } else {
        sql.append(" and po.informationIssueTime<=now()");
      } 
      sql.append(" and po.informationStatus=0");
      sql.append(" order by po.informationIssueTime desc");
      Query query = this.session.createQuery(sql.toString());
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      channelList = query.list();
      recordCount = ((Integer)this.session.find(sql.toString().replace("select po from", "select count(*) from")).listIterator().next()).intValue();
      queryMap.put("QUERY_LIST", channelList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
      queryMap.put("size", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return queryMap;
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
  
  public Map getMessageList(String userId, String orgId, String orgIdString, int beginIndex, int limited, String keyword, String from) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    try {
      begin();
      StringBuffer hql = (new StringBuffer())
        .append("select po.chatId, po.chatContent, po.chatTo, po.chatHasattach, po.chatAttachsize, po.senderId, po.senderName, po.chatTime, cu.empId, cu.chatStatus, cu.isRead ")
        .append("from com.js.oa.chat.po.ChatUserPO cu left join cu.chat po ")
        .append("where 1=1 ");
      if (keyword != null && !"".equals(keyword))
        hql.append(" and (po.chatContent like '%" + keyword + "%' or po.chatTo like '%" + keyword + "%') "); 
      if ("0".equals(from)) {
        hql.append(" and po.senderId=" + userId);
      } else if ("1".equals(from)) {
        hql.append(" and cu.empId=" + userId);
      } 
      hql.append(" and cu.chatStatus=" + from + " order by po.chatTime desc");
      String sql = hql.toString();
      String sqlGroupBy = sql;
      this.log.debug("sqlGroupBy = " + sqlGroupBy);
      Query query = this.session.createQuery(sqlGroupBy);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      List messagelList = query.list();
      Query queryCount = this.session.createQuery(sqlGroupBy);
      int recordCount = queryCount.list().size();
      queryMap.put("QUERY_LIST", messagelList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return queryMap;
  }
  
  public Map personalCuibanList(String userId, int beginIndex, int limited, String keyword, String pressStatus) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    try {
      begin();
      StringBuffer hql = (new StringBuffer())
        .append("select press.pressId,press.title,press.sendUsername,press.pressStatus,press.dispatchTime ")
        .append("from com.js.oa.pressdeal.po.OaPersonoaPressPO press ")
        .append("where press.pressId in ( ")
        .append("select distinct opupr.pressId from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr where opupr.userId =" + userId + " and opupr.pressStatus = " + pressStatus + ")");
      if (keyword != null && !"".equals(keyword))
        hql.append(" and (press.title like '%" + keyword + "%' ) "); 
      hql.append(" order by  press.pressId desc ");
      String sql = hql.toString();
      String sqlGroupBy = sql;
      this.log.debug("sqlGroupBy = " + sqlGroupBy);
      Query query = this.session.createQuery(sqlGroupBy);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      List messagelList = query.list();
      Query queryCount = this.session.createQuery(sqlGroupBy);
      int recordCount = queryCount.list().size();
      queryMap.put("QUERY_LIST", messagelList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return queryMap;
  }
  
  public int updateReTime(String forumId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    int num = 0;
    try {
      dataSourceBase.begin();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "update oa_forum set newretime=NOW() where forum_id=" + forumId;
      } else {
        sql = "update oa_forum set newretime=sysdate where forum_id=" + forumId;
      } 
      num = dataSourceBase.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return num;
  }
  
  public Map getForumList(String hql, int beginIndex, int limit) throws Exception {
    Map<Object, Object> bbsMap = new HashMap<Object, Object>();
    int size = 0;
    List<ForumPO> list = null;
    String title = "";
    try {
      begin();
      Query query = this.session.createQuery(hql);
      size = query.list().size();
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      Query querySize = this.session.createQuery(hql);
      bbsMap.put("list", list);
      bbsMap.put("size", Integer.valueOf(size));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return bbsMap;
  }
  
  public List getForumClassList(String wherePara, String curUserId, String domainId) throws Exception {
    List<Object[]> list = new ArrayList();
    wherePara = String.valueOf(wherePara) + getClassIdString(curUserId, "po.id", wherePara);
    begin();
    List<Object[]> _list = new ArrayList();
    String period = "";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    period = sdf.format(new Date());
    boolean isTrue = true;
    try {
      String hql = " select po.id,po.className,po.classLevel,po.classOwnerName,po.classUserName, po.classHasJunior,po.classParent,po.createdEmp,po.createdOrg,po.className, po.checkExamin,po.fullDay,po.startPeriod,po.endPeriod,po.createdEmp,po.classOwnerId,po.classOwnerIds  from com.js.oa.bbs.po.ForumClassPO po where (" + 

        
        wherePara + 
        " or ( ( po.classUserId is null or po.classUserId='') and (po.classUserOrg is null or po.classUserOrg ='' )" + 
        " and  ( po.classUserGroup is null or po.classUserGroup ='') ) ) and po.relProjectId=0 and po.proClassId=0 " + 
        "and po.domainId=" + domainId + "   order by po.classSort ";
      _list = this.session.createQuery(hql).list();
      for (int i = 0; i < _list.size(); i++) {
        Object[] obj = _list.get(i);
        if (!"1".equals(obj[11].toString())) {
          String startPeriods = String.valueOf(obj[12].toString()) + ",";
          String endPeriods = String.valueOf(obj[13].toString()) + ",";
          String[] startPeriodss = startPeriods.split(",");
          String[] endPeriodss = endPeriods.split(",");
          for (int ii = 0; ii < startPeriodss.length; ii++) {
            if (period.compareTo(startPeriodss[ii]) >= 0 && 
              period.compareTo(endPeriodss[ii]) <= 0) {
              isTrue = true;
              break;
            } 
            isTrue = false;
          } 
        } 
        if (isTrue)
          list.add(obj); 
        isTrue = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public String getClassIdString(String curUserId, String poId, String wherePara) throws Exception {
    String tmp = "";
    begin();
    try {
      List tmpList = this.session.createQuery("select po.id  from com.js.oa.bbs.po.ForumClassPO po where  po.classOwnerId=" + curUserId + " or " + wherePara).list();
      tmp = getClassIdString(tmpList, poId);
    } catch (Exception e) {
      e.printStackTrace();
      tmp = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return tmp;
  }
  
  private String getClassIdString(List tmpList, String poId) throws Exception {
    StringBuffer tmp = new StringBuffer(" or " + poId + " in ( ");
    StringBuffer where = new StringBuffer("");
    StringBuffer sql = new StringBuffer("");
    for (int i = 0; i < tmpList.size(); i++)
      sql.append(" po.classSort like '%$").append(tmpList.get(i)).append("$%' or "); 
    sql.append(" 1<>1 ");
    try {
      List tmpList1 = this.session.createQuery(
          "select po.classSort from com.js.oa.bbs.po.ForumClassPO po where " + 
          sql).list();
      for (int j = 0; j < tmpList1.size(); j++)
        where.append(tmpList1.get(j)); 
      String[] str = ("$" + 
        
        StringSplit.splitOrgIdString(where.toString(), "$", "_") + "$")
        .split("\\$\\$");
      where = new StringBuffer("");
      for (int k = 0; k < str.length; k++) {
        if (str[k] != null && !"".equals(str[k]))
          where.append(str[k])
            .append(","); 
      } 
      if (!"".equals(where.toString()) && 
        where.toString().indexOf(",") != -1) {
        tmp.append(where.toString().substring(0, 
              where.toString().lastIndexOf(","))).append(")");
      } else {
        tmp = new StringBuffer("");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      tmp = new StringBuffer("");
      throw e;
    } 
    return tmp.toString();
  }
  
  public List<ForumPO> searchByClassId(String fornmId) throws Exception {
    begin();
    List<ForumPO> list = null;
    try {
      String sqlString = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm where fornm.forumClass.id =" + fornmId + 
          "  and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumType desc,fornm.forumTopOrder desc,fornm.forumIsSoul desc,fornm.newretime desc limit 5";
        list = this.session.createQuery(sqlString).list();
      } else if (databaseType.indexOf("oracle") >= 0) {
        sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm where fornm.forumClass.id =" + fornmId + 
          "  and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumType desc,fornm.forumTopOrder desc,fornm.forumIsSoul desc,fornm.newretime desc";
        Query oracleQuery = this.session.createQuery(sqlString);
        oracleQuery.setMaxResults(5);
        list = oracleQuery.list();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Map searchAttentionByUserid(String userId, String keyword, int beginIndex) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    List<ForumPO> list = new ArrayList<ForumPO>();
    begin();
    int limit = WapUtil.LIMITED;
    if (keyword != null && !"".equals(keyword)) {
      keyword = " fornm.forumTitle like '%" + keyword + "%' and ";
    } else {
      keyword = "";
    } 
    int size = 0;
    String hql = "select fornm from com.js.system.vo.messages.Remind remind,com.js.oa.bbs.po.ForumPO fornm  where " + 
      keyword + " remind.data_id=fornm.id and remind.emp_id =" + userId + 
      " and remind.remind_type='Forum' and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumModifyTime desc";
    try {
      size = this.session.createQuery(hql).list().size();
      list = this.session.createQuery(hql).setFirstResult(beginIndex).setMaxResults(limit).list();
      map.put("list", list);
      map.put("size", Integer.valueOf(size));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return map;
  }
  
  public void replyMessage(String userId, String userName, String toID, String content, String toNames) {
    MessagesBD messagesBD = new MessagesBD();
    String sendID = userId;
    String sendName = userName;
    ChatPO chatPO = new ChatPO();
    String[] strTmp = toID.split(",");
    Calendar tmp = Calendar.getInstance();
    tmp.set(2050, 12, 12);
    String msgTitle = content;
    if (msgTitle.length() > 20) {
      msgTitle = msgTitle.substring(0, 20);
      msgTitle = String.valueOf(msgTitle) + "...";
    } 
    try {
      begin();
      chatPO.setChatTo(toNames);
      chatPO.setChatContent(content);
      chatPO.setSenderId(sendID);
      chatPO.setChatTime(new Date());
      chatPO.setChatHasattach("0");
      chatPO.setSenderName(userName);
      long dataId = ((Long)this.session.save(chatPO)).longValue();
      ChatUserPO chatUserPO = new ChatUserPO();
      chatUserPO.setChatStatus("0");
      chatUserPO.setEmpId(chatPO.getSenderId());
      chatUserPO.setIsRead("1");
      chatUserPO.setChat(chatPO);
      this.session.save(chatUserPO);
      for (int j = 0; j < strTmp.length; j++) {
        chatUserPO = new ChatUserPO();
        chatUserPO.setChatStatus("1");
        chatUserPO.setEmpId(strTmp[j]);
        chatUserPO.setIsRead("0");
        chatUserPO.setChat(chatPO);
        this.session.save(chatUserPO);
        MessagesVO messagesVO = new MessagesVO();
        messagesVO.setMessage_send_UserName(sendName);
        messagesVO.setMessage_type("Chat");
        messagesVO.setMessage_send_UserId(Long.parseLong(sendID));
        messagesVO.setMessage_show(1);
        messagesVO.setMessage_status(1);
        messagesVO.setMessage_time(new Date());
        messagesVO.setMessage_title(msgTitle);
        messagesVO.setMessage_url("/jsoa/chat/showChat.jsp?id=" + dataId);
        messagesVO.setMessage_toUserId(Long.parseLong(strTmp[j]));
        messagesVO.setMessage_date_begin(new Date());
        messagesVO.setData_id(dataId);
        messagesVO.setMessage_date_end(tmp.getTime());
        messagesBD.messageAdd(messagesVO);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public String ImgNamePath(String informationId) {
    String ImageNamePath = "";
    try {
      begin();
      String sql = "select aaa.accessorySaveName from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa join aaa.information bbb where aaa.accessoryIsImage=1 and  bbb.informationId = " + informationId;
      Query query = this.session.createQuery(sql.toString());
      if (query.list() != null && query.list().size() == 1) {
        String accessorySaveName = query.list().get(0);
        String srcFive = "0000";
        if (accessorySaveName != null && accessorySaveName.length() > 6 && accessorySaveName.substring(4, 5).equals("_")) {
          srcFive = accessorySaveName.substring(0, 4);
        } else {
          srcFive = "0000";
        } 
        ImageNamePath = "/jsoa/upload/" + srcFive + "/information/" + accessorySaveName;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return ImageNamePath;
  }
  
  public String InformationTitle(String informationId) {
    String informationTitle = "";
    try {
      begin();
      String sql = "select bbb.informationTitle from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa join aaa.information bbb where aaa.accessoryIsImage=1 and  bbb.informationId = " + informationId;
      Query query = this.session.createQuery(sql.toString());
      if (query.list() != null && query.list().size() == 1)
        informationTitle = query.list().get(0); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return informationTitle;
  }
  
  public int searchYesOrNo(String dataId, String remind_type) throws Exception {
    int guanzhu = 0;
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select remind from com.js.system.vo.messages.Remind remind where remind.data_id =" + dataId + " and remind.remind_type like '" + remind_type + "'").list();
      if (!list.isEmpty())
        guanzhu = list.size(); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return guanzhu;
  }
  
  public String getInfoReaderNum(String informationId) {
    String num = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select INFORMATIONKITS from oa_information where information_id=?");
      pstmt.setString(1, informationId);
      ResultSet rs = pstmt.executeQuery();
      if (rs != null && rs.next())
        num = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } 
    return num;
  }
  
  public Map getRelationData(String hql, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List list = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery(hql.toString());
      recordCount = query.list().size();
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      list = query.list();
      queryMap.put("QUERY_LIST", list);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return queryMap;
  }
  
  public String getInformationHeadFile(String informationId) {
    String InformationHeadFile = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select InformationHeadFile from oa_information where information_id=?");
      pstmt.setString(1, informationId);
      ResultSet rs = pstmt.executeQuery();
      if (rs != null && rs.next())
        InformationHeadFile = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } 
    return InformationHeadFile;
  }
}
