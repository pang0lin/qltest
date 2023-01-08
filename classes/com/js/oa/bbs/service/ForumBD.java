package com.js.oa.bbs.service;

import com.ibm.icu.util.Calendar;
import com.js.oa.bbs.bean.ForumEJBBean;
import com.js.oa.bbs.bean.ForumEJBHome;
import com.js.oa.bbs.po.ForumPO;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

public class ForumBD {
  private static Logger logger = Logger.getLogger(ForumBD.class.getName());
  
  private EJBProxy ejbProxy = null;
  
  private ParameterGenerator pg = null;
  
  private ManagerEJBBean meb = new ManagerEJBBean();
  
  private ForumEJBBean feb = new ForumEJBBean();
  
  public ForumBD() {
    this.pg = null;
    try {
      this.ejbProxy = new EJBProxy("ForumEJB", "ForumEJBLocal", ForumEJBHome.class);
    } catch (Exception exception) {}
  }
  
  public Vector list(String classId, String startDate, String endDate, Integer offset, String queryText, String queryItem, String queryClass, String queryMan, String queryForumType, String wherePara, String replyDesc, String kitDesc, String btimeDesc, String sortFirst, String domainId) {
    Vector vec = null;
    this.pg = new ParameterGenerator(15);
    try {
      this.pg.put(classId, "String");
      this.pg.put(startDate, "String");
      this.pg.put(endDate, "String");
      this.pg.put(offset, "Integer");
      this.pg.put(queryText, "String");
      this.pg.put(queryItem, "String");
      this.pg.put(queryClass, "String");
      this.pg.put(queryMan, "String");
      this.pg.put(queryForumType, "String");
      this.pg.put(wherePara, "String");
      this.pg.put(replyDesc, "String");
      this.pg.put(kitDesc, "String");
      this.pg.put(btimeDesc, "String");
      this.pg.put(sortFirst, "String");
      this.pg.put(domainId, "String");
      vec = (Vector)this.ejbProxy.invoke("list", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return vec;
  }
  
  public String see(Long curUserId) {
    Object object = "";
    this.pg = new ParameterGenerator(1);
    try {
      this.pg.put(curUserId, "Long");
      object = this.ejbProxy.invoke("see", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String add(ForumPO po, String classId, String forumId, Long curUserId, String addSign, String forumSign) {
    Object object = "";
    this.pg = new ParameterGenerator(6);
    try {
      this.pg.put(po, ForumPO.class);
      this.pg.put(classId, "String");
      this.pg.put(forumId, "String");
      this.pg.put(curUserId, "Long");
      this.pg.put(addSign, "String");
      this.pg.put(forumSign, "String");
      object = this.ejbProxy.invoke("add", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public Vector followList(String forumId, Integer offset, String sign) {
    Vector vec = null;
    this.pg = new ParameterGenerator(3);
    try {
      this.pg.put(forumId, "String");
      this.pg.put(offset, "Integer");
      this.pg.put(sign, "String");
      vec = (Vector)this.ejbProxy.invoke("followList", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return vec;
  }
  
  public String getForumTitle(String forumId) {
    Object object = "";
    this.pg = new ParameterGenerator(1);
    try {
      this.pg.put(forumId, "String");
      object = this.ejbProxy.invoke("getForumTitle", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String delForum(String forumId, String classId) {
    Object object = "";
    this.pg = new ParameterGenerator(2);
    try {
      this.pg.put(forumId, "String");
      this.pg.put(classId, "String");
      object = this.ejbProxy.invoke("delForum", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String soulForum(String forumId, String status) {
    Object object = "";
    this.pg = new ParameterGenerator(2);
    try {
      this.pg.put(forumId, "String");
      this.pg.put(status, "String");
      object = this.ejbProxy.invoke("soulForum", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String setTop(String setTopType, String forumId, String classId) {
    Object object = "";
    this.pg = new ParameterGenerator(3);
    try {
      this.pg.put(setTopType, "String");
      this.pg.put(forumId, "String");
      this.pg.put(classId, "String");
      object = this.ejbProxy.invoke("setTop", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String setAuth(String type, String isnot, String forumId) {
    Object object = "";
    this.pg = new ParameterGenerator(3);
    try {
      this.pg.put(type, "String");
      this.pg.put(isnot, "String");
      this.pg.put(forumId, "String");
      object = this.ejbProxy.invoke("setAuth", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public List getForumClassPODetails(String classId) {
    List retString = null;
    this.pg = new ParameterGenerator(1);
    try {
      this.pg.put(classId, "String");
      retString = (List)this.ejbProxy.invoke("getForumClassPODetails", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return retString;
  }
  
  public List getForumPODetails(String forumId) {
    List retString = null;
    this.pg = new ParameterGenerator(1);
    try {
      this.pg.put(forumId, "String");
      retString = (List)this.ejbProxy.invoke("getForumPODetails", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return retString;
  }
  
  public String goExamin(String forumId) {
    Object object = "";
    this.pg = new ParameterGenerator(1);
    try {
      this.pg.put(forumId, "String");
      object = this.ejbProxy.invoke("goExamin", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String move(String ids, String classId, String oriClassId) {
    Object object = "";
    this.pg = new ParameterGenerator(3);
    try {
      this.pg.put(ids, "String");
      this.pg.put(classId, "String");
      this.pg.put(oriClassId, "String");
      object = this.ejbProxy.invoke("move", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public List userList(String empId) {
    this.pg = new ParameterGenerator(1);
    List list = new ArrayList();
    try {
      this.pg.put(empId, "String");
      list = (List)this.ejbProxy.invoke("userlist", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public Vector noteBook(String forumId) {
    this.pg = new ParameterGenerator(1);
    Vector vec = new Vector();
    try {
      this.pg.put(forumId, "String");
      vec = (Vector)this.ejbProxy.invoke("noteBook", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return vec;
  }
  
  public Map getSingle(String forumId, String userId) {
    Map forum = null;
    try {
      this.pg = new ParameterGenerator(2);
      this.pg.put(forumId, "String");
      this.pg.put(userId, "String");
      forum = (Map)this.ejbProxy.invoke("getSingle", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return forum;
  }
  
  public Integer update(String[] forumPara) {
    Integer result = new Integer(0);
    try {
      this.pg = new ParameterGenerator(1);
      this.pg.put(forumPara, String[].class);
      result = (Integer)this.ejbProxy.invoke("update", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Long getAuthorIdByForumId(String id) {
    Long result = new Long(0L);
    try {
      this.pg = new ParameterGenerator(1);
      this.pg.put(id, String.class);
      result = (Long)this.ejbProxy.invoke("getAuthorIdByForumId", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String classIdQuery(String classId) {
    String s = "";
    try {
      this.pg = new ParameterGenerator(1);
      this.pg.put(classId, String.class);
      s = (String)this.ejbProxy.invoke("classIdQuery", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return s;
  }
  
  public List loadContent(List list) {
    List alist = null;
    try {
      this.pg = new ParameterGenerator(1);
      this.pg.put(list, List.class);
      alist = (List)this.ejbProxy.invoke("loadContent", this.pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return alist;
  }
  
  public List searchByClassId(long fornmId) throws Exception {
    List list = null;
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.searchByClassId(fornmId);
    return list;
  }
  
  public ForumPO searchById(long fornmId) throws Exception {
    ForumPO forumPO = null;
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    forumPO = forumEJBBean.searchById(fornmId);
    return forumPO;
  }
  
  public String getAttentionMoreSql(Long userId, String forumClassIds, String orderString, String queryItem, String queryMan, String startDate, String endDate, String queryTitle, String queryClass, String queryForumType) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" where remind.data_id=po.id and remind.emp_id =");
    sb.append(userId);
    if (forumClassIds.length() > 0) {
      forumClassIds = forumClassIds.substring(0, forumClassIds.length() - 1);
      sb.append(" and po.forumClass.id in( ");
      sb.append(forumClassIds);
      sb.append(") ");
    } 
    sb.append(" and remind.remind_type like 'Forum' and po.forumTopicId=0 ");
    if (queryClass != null && !"none".equals(queryClass)) {
      sb.append(" and po.forumClass.id=");
      sb.append(queryClass);
    } 
    if ("0".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=0 "); 
    if ("1".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=1 "); 
    if ("2".equals(queryForumType))
      sb.append(" and po.forumType=2 and po.forumIsSoul=0 "); 
    if (queryMan != null && !"".equals(queryMan)) {
      sb.append(" and po.forumAuthor like '%");
      sb.append(queryMan);
      sb.append("%' ");
    } 
    if (queryTitle != null && !"".equals(queryTitle)) {
      sb.append(" and po.forumTitle like'%");
      sb.append(queryTitle);
      sb.append("%' ");
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.forumIssueTime between '")
          .append(startDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(endDate)
          .append(" 23:59:59")
          .append("'")
          .append(" or  po.forumIssueTime between '")
          .append(endDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(startDate)
          .append(" 23:59:59")
          .append("' )");
      } else {
        sb.append(" and ( po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(startDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(endDate).append(" 23:59:59")
          .append("','L')")
          .append(" or  po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(endDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(startDate)
          .append(" 23:59:59")
          .append("','L') )");
      } 
    } 
    sb.append(" and po.examinNum <>1 order by ");
    sb.append(orderString);
    sb.append(" po.forumModifyTime desc");
    return sb.toString();
  }
  
  public List searchAttentionByUserid(long userId, String isPortal) throws Exception {
    List list = new ArrayList();
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.searchAttentionByUserid(userId, isPortal);
    return list;
  }
  
  public String getSoulMoreSql(String forumClassIds, String orderString, String queryItem, String queryMan, String startDate, String endDate, String queryTitle, String queryClass) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" where po.forumTopicId=0  and po.forumIsSoul='1' ");
    if (!"".equals(forumClassIds) && forumClassIds != null) {
      forumClassIds = forumClassIds.substring(0, forumClassIds.length() - 1);
      sb.append(" and po.forumClass.id in( ");
      sb.append(forumClassIds);
      sb.append(" )");
    } 
    if (queryClass != null && !"none".equals(queryClass)) {
      sb.append(" and po.forumClass.id=");
      sb.append(queryClass);
    } 
    if (queryMan != null && !"".equals(queryMan)) {
      sb.append(" and po.forumAuthor like '%");
      sb.append(queryMan);
      sb.append("%' ");
    } 
    if (queryTitle != null && !"".equals(queryTitle)) {
      sb.append(" and po.forumTitle like'%");
      sb.append(queryTitle);
      sb.append("%' ");
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.forumIssueTime between '")
          .append(startDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(endDate)
          .append(" 23:59:59")
          .append("'")
          .append(" or  po.forumIssueTime between '")
          .append(endDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(startDate)
          .append(" 23:59:59")
          .append("' )");
      } else {
        sb.append(" and ( po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(startDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(endDate).append(" 23:59:59")
          .append("','L')")
          .append(" or  po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(endDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(startDate)
          .append(" 23:59:59")
          .append("','L') )");
      } 
    } 
    sb.append(" and po.examinNum <>1 order by ");
    sb.append(orderString);
    sb.append(" po.forumModifyTime desc ");
    return sb.toString();
  }
  
  public List searchSoulByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.searchSoulByUserid(isPortal, forumClass);
    return list;
  }
  
  public String GetTopMoreSql(String forumClassIds, String orderString, String queryItem, String queryMan, String startDate, String endDate, String queryTitle, String queryClass, String queryForumType) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" where po.forumTopicId=0 and po.forumTopOrder<>'0' ");
    if (!"".equals(forumClassIds) && forumClassIds != null) {
      forumClassIds = forumClassIds.substring(0, forumClassIds.length() - 1);
      sb.append(" and po.forumClass.id in( ");
      sb.append(forumClassIds);
      sb.append(" )");
    } 
    if (queryClass != null && !"none".equals(queryClass)) {
      sb.append(" and po.forumClass.id=");
      sb.append(queryClass);
    } 
    if ("0".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=0 "); 
    if ("1".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=1 "); 
    if ("2".equals(queryForumType))
      sb.append(" and po.forumType=2 and po.forumIsSoul=0 "); 
    if (queryMan != null && !"".equals(queryMan)) {
      sb.append(" and po.forumAuthor like '%");
      sb.append(queryMan);
      sb.append("%' ");
    } 
    if (queryTitle != null && !"".equals(queryTitle)) {
      sb.append(" and po.forumTitle like'%");
      sb.append(queryTitle);
      sb.append("%' ");
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.forumIssueTime between '")
          .append(startDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(endDate)
          .append(" 23:59:59")
          .append("'")
          .append(" or  po.forumIssueTime between '")
          .append(endDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(startDate)
          .append(" 23:59:59")
          .append("' )");
      } else {
        sb.append(" and ( po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(startDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(endDate).append(" 23:59:59")
          .append("','L')")
          .append(" or  po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(endDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(startDate)
          .append(" 23:59:59")
          .append("','L') )");
      } 
    } 
    sb.append(" and po.examinNum <>1 order by ");
    sb.append(orderString);
    sb.append(" po.forumTopOrder desc ");
    return sb.toString();
  }
  
  public List searchTopByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.searchTopByUserid(isPortal, forumClass);
    return list;
  }
  
  public String getHotMoreSql(String forumClassIds, String orderString, String queryItem, String queryMan, String startDate, String endDate, String queryTitle, String queryClass, String queryForumType) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" where po.forumTopicId=0 and po.examinNum <>1  ");
    if (!"".equals(forumClassIds) && forumClassIds != null) {
      forumClassIds = forumClassIds.substring(0, forumClassIds.length() - 1);
      sb.append(" and po.forumClass.id in( ");
      sb.append(forumClassIds);
      sb.append(" )");
    } 
    if (queryClass != null && !"none".equals(queryClass)) {
      sb.append(" and po.forumClass.id=");
      sb.append(queryClass);
    } 
    if ("0".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=0 "); 
    if ("1".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=1 "); 
    if ("2".equals(queryForumType))
      sb.append(" and po.forumType=2 and po.forumIsSoul=0 "); 
    if (queryMan != null && !"".equals(queryMan)) {
      sb.append(" and po.forumAuthor like '%");
      sb.append(queryMan);
      sb.append("%' ");
    } 
    if (queryTitle != null && !"".equals(queryTitle)) {
      sb.append(" and po.forumTitle like'%");
      sb.append(queryTitle);
      sb.append("%' ");
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.forumIssueTime between '")
          .append(startDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(endDate)
          .append(" 23:59:59")
          .append("'")
          .append(" or  po.forumIssueTime between '")
          .append(endDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(startDate)
          .append(" 23:59:59")
          .append("' )");
      } else {
        sb.append(" and ( po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(startDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(endDate).append(" 23:59:59")
          .append("','L')")
          .append(" or  po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(endDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(startDate)
          .append(" 23:59:59")
          .append("','L') )");
      } 
    } 
    sb.append(" order by ");
    sb.append(orderString);
    sb.append(" po.forumKits desc ");
    return sb.toString();
  }
  
  public List searchHotByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.searchHotByUserid(isPortal, forumClass);
    return list;
  }
  
  public String getNewUpdateMoreSql(String forumClassIds, String orderString, String queryItem, String queryMan, String startDate, String endDate, String queryTitle, String queryClass, String queryForumType) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" where po.forumTopicId=0 and po.examinNum <>1 ");
    if (!"".equals(forumClassIds) && forumClassIds != null) {
      forumClassIds = forumClassIds.substring(0, forumClassIds.length() - 1);
      sb.append(" and po.forumClass.id in( ");
      sb.append(forumClassIds);
      sb.append(" )");
    } 
    if (queryClass != null && !"none".equals(queryClass)) {
      sb.append(" and po.forumClass.id=");
      sb.append(queryClass);
    } 
    if ("0".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=0 "); 
    if ("1".equals(queryForumType))
      sb.append(" and po.forumType=0 and po.forumIsSoul=1 "); 
    if ("2".equals(queryForumType))
      sb.append(" and po.forumType=2 and po.forumIsSoul=0 "); 
    if (queryMan != null && !"".equals(queryMan)) {
      sb.append(" and po.forumAuthor like '%");
      sb.append(queryMan);
      sb.append("%' ");
    } 
    if (queryTitle != null && !"".equals(queryTitle)) {
      sb.append(" and po.forumTitle like'%");
      sb.append(queryTitle);
      sb.append("%' ");
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.forumIssueTime between '")
          .append(startDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(endDate)
          .append(" 23:59:59")
          .append("'")
          .append(" or  po.forumIssueTime between '")
          .append(endDate)
          .append(" 00:00:00")
          .append("' and '")
          .append(startDate)
          .append(" 23:59:59")
          .append("' )");
      } else {
        sb.append(" and ( po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(startDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(endDate).append(" 23:59:59")
          .append("','L')")
          .append(" or  po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(endDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(startDate)
          .append(" 23:59:59")
          .append("','L') )");
      } 
    } 
    sb.append(" order by ");
    sb.append(orderString);
    sb.append(" po.forumModifyTime desc ");
    return sb.toString();
  }
  
  public List searchNewUpdateByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.searchNewUpdateByUserid(isPortal, forumClass);
    return list;
  }
  
  public List getForumClass(String forumClass, String domainId) throws Exception {
    List list = new ArrayList();
    ForumEJBBean forumEJBBean = new ForumEJBBean();
    list = forumEJBBean.getForumClass(forumClass, domainId);
    return list;
  }
  
  public boolean isHasRightToMove(String currentUserId, String currentUserOrgIds, String createUserID, String createUserOrgIds) throws Exception {
    boolean returnValue = Boolean.FALSE.booleanValue();
    String rightCodeString = "06*02*02";
    Boolean isHasRight = this.meb.hasRight(currentUserId, rightCodeString);
    if (createUserOrgIds == null)
      createUserOrgIds = ""; 
    if (isHasRight.booleanValue()) {
      String[] currentOrgStrings = currentUserOrgIds.split(",");
      String[] createOrgStrings = createUserOrgIds.split(",");
      List<Object[]> scopeList = this.meb.getRightScope(currentUserId, rightCodeString);
      if (scopeList.size() > 0) {
        Object[] obj = scopeList.get(0);
        String scopeString = obj[0].toString();
        if ("0".equals(scopeString)) {
          returnValue = Boolean.TRUE.booleanValue();
          return returnValue;
        } 
        if ("1".equals(scopeString)) {
          if (currentUserId.equals(createUserID)) {
            returnValue = Boolean.TRUE.booleanValue();
            return returnValue;
          } 
        } else if ("2".equals(scopeString)) {
          String relationType = getOrgRelationType(currentOrgStrings, createOrgStrings);
          if ("selfOrg".equals(relationType) || "subOrg".equals(relationType)) {
            returnValue = Boolean.TRUE.booleanValue();
            return returnValue;
          } 
        } else if ("3".equals(scopeString)) {
          String relationType = getOrgRelationType(currentOrgStrings, createOrgStrings);
          if ("selfOrg".equals(relationType)) {
            returnValue = Boolean.TRUE.booleanValue();
            return returnValue;
          } 
        } else {
          String customScopeString = this.feb.getCustomScope(currentUserId, "06*02*02", "0");
          if (customScopeString.length() > 0) {
            String orgsString = "";
            String usersString = "";
            if (customScopeString.indexOf("-") != 0)
              orgsString = customScopeString.split("-")[0]; 
            if (customScopeString.indexOf("-") != customScopeString.length() - 1)
              if ((customScopeString.split("-")).length > 1) {
                usersString = customScopeString.split("-")[1];
              } else {
                usersString = customScopeString.split("-")[0];
              }  
            if (usersString != null && !"".equals(usersString)) {
              usersString = usersString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
              String[] usersArray = usersString.split(",");
              for (int i = 0; i < usersArray.length; i++) {
                if (createUserID.equals(usersArray[i])) {
                  returnValue = Boolean.TRUE.booleanValue();
                  return returnValue;
                } 
              } 
            } 
            if (orgsString != null && !"".equals(orgsString)) {
              orgsString = orgsString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
              String[] orgsArray = orgsString.split(",");
              String scopeOrgStringTemp = "";
              String createOrgStringTemp = "";
              for (int i = 0; i < orgsArray.length; i++) {
                scopeOrgStringTemp = orgsArray[i];
                for (int j = 0; j < createOrgStrings.length; j++) {
                  createOrgStringTemp = createOrgStrings[j];
                  if (scopeOrgStringTemp.equals(createOrgStringTemp)) {
                    returnValue = Boolean.TRUE.booleanValue();
                    return returnValue;
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    return returnValue;
  }
  
  public String getOrgRelationType(String[] currentOrgStrings, String[] createOrgStrings) throws Exception {
    String relationType = "none";
    List<String> relationList = new ArrayList();
    String createOrgTemp = "";
    String currentOrgTemp = "";
    int i;
    for (i = 0; i < createOrgStrings.length; i++) {
      createOrgTemp = createOrgStrings[i];
      for (int j = 0; j < currentOrgStrings.length; j++) {
        currentOrgTemp = currentOrgStrings[j];
        if (createOrgTemp.equals(currentOrgTemp)) {
          relationType = "selfOrg";
          return relationType;
        } 
        if (isSubOrg(createOrgTemp, currentOrgTemp))
          relationList.add("subOrg"); 
      } 
    } 
    for (i = 0; i < relationList.size(); i++) {
      if (relationList.contains("subOrg")) {
        relationType = "subOrg";
        break;
      } 
    } 
    return relationType;
  }
  
  public boolean isSubOrg(String subOrgId, String parentOrgId) throws Exception {
    boolean returnValue = Boolean.FALSE.booleanValue();
    returnValue = this.feb.isSubOrg(subOrgId, parentOrgId);
    return returnValue;
  }
  
  public ArrayList getHasMoveRightForumList(String currentUserId, String currentUserOrgIds, String[] forumList) throws Exception {
    ArrayList<String> list = new ArrayList();
    String createrId = "";
    String createrOrgs = "";
    String[] tempArray = new String[2];
    for (int i = 0; i < forumList.length; i++) {
      tempArray = this.feb.getCreateIdsOrgIdsByForumId(forumList[i], "0");
      createrId = tempArray[0];
      createrOrgs = tempArray[1];
      if (isHasRightToMove(currentUserId, currentUserOrgIds, createrId, createrOrgs))
        list.add(forumList[i]); 
    } 
    return list;
  }
  
  public boolean isHasMoveRight(String currentUserId, String currentUserOrgIds, String forumClassId, String domainId) throws Exception {
    String createrId = "";
    String createrOrgs = "";
    String[] tempArray = new String[2];
    tempArray = this.feb.getCreateIdsOrgIdsByForumId(forumClassId, domainId);
    createrId = tempArray[0];
    createrOrgs = tempArray[1];
    if (isHasRightToMove(currentUserId, currentUserOrgIds, createrId, createrOrgs))
      return true; 
    return false;
  }
  
  public void updateModifytime(Long id) {
    (new ForumEJBBean()).updateModifytime(id);
  }
  
  public String checkAdd(ForumPO po) {
    String res = "";
    Connection conn = null;
    try {
      String now = "";
      Calendar cal = Calendar.getInstance();
      cal.add(10, -1);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      now = format.format(cal.getTime());
      if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
        now = "to_date('" + now + ",'YYYY-MM-DD HH24:MI:SS')";
      } else {
        now = "'" + now + "'";
      } 
      String sql = "select count(*) from oa_forum where FORUMMODIFYTIME>" + now + " and forumauthorid=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(po.getForumAuthorId()));
      ResultSet rs = pstmt.executeQuery();
      if (rs.next() && 
        rs.getInt(1) > 20)
        res = "发帖频率过高，每小时发帖数请不要超过20篇！"; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return res;
  }
}
