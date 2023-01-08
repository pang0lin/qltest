package com.js.oa.personalwork.person.bean;

import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class PersonRelationEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getRelationList(String userId, String empId, String orgId, String orgIdString, String domainId, List underEmpList, String whereDoc) throws Exception {
    Map<Object, Object> mapList = new HashMap<Object, Object>();
    try {
      begin();
      List groupList = this.session.createQuery("select g.groupId from GroupVO g where g.groupUserString like '%$" + userId + "$%' ").list();
      List cooList = getRelationCooList(userId, empId, orgId, orgIdString, groupList);
      List docList = getRelationDocList(domainId, empId, whereDoc);
      List reportList = getRelationReportList(userId, empId, orgId, domainId, underEmpList);
      List taskList = getRelationTaskList(userId, empId, domainId);
      List msgList = getRelationMsgList(userId, empId);
      List empInfoList = getRelationEmpInfoById(empId);
      mapList.put("cooList", cooList);
      mapList.put("docList", docList);
      mapList.put("reportList", reportList);
      mapList.put("taskList", taskList);
      mapList.put("msgList", msgList);
      mapList.put("empInfoList", empInfoList);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return mapList;
  }
  
  public List getRelationCooList(String userId, String empId, String orgId, String orgIdString, List groupList) {
    List cooList = null;
    try {
      String hql = "select distinct bpo.id,bpo.title,bpo.postTime,bpo.posterId,bpo.posterName,bpo.status,mpo.id,mpo.nodeId,bpo.level,bpo.hasTerm,bpo.term";
      hql = String.valueOf(hql) + " from BodyPO bpo,NodeMemberPO mpo ";
      hql = String.valueOf(hql) + " where bpo.id=mpo.bodyId ";
      hql = String.valueOf(hql) + " and mpo.status=10 ";
      hql = String.valueOf(hql) + " and (bpo.posterId=" + empId + " and bpo.sendToId like '%$" + userId + "$%')";
      hql = String.valueOf(hql) + " and mpo.empId=" + userId + " order by bpo.postTime desc";
      Query query = this.session.createQuery(hql);
      query.setFirstResult(0);
      query.setMaxResults(7);
      cooList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      return cooList = null;
    } 
    return cooList;
  }
  
  public List getRelationTaskList(String userId, String empId, String domainId) {
    List taskList = null;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      StringBuffer hql = new StringBuffer("from TaskPO po where po.createdEmp=" + userId + " and po.taskDomainId=" + domainId);
      if (databaseType.indexOf("mysql") >= 0) {
        hql.append(" and (concat('$',replace(po.taskJoineOrg,',','$$'),'$') like '%$" + empId + "$%' ");
      } else {
        hql.append(" and (JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$',JSDB.FN_replace(po.taskJoineOrg,',','$$')),'$') like '%$" + empId + "$%' ");
      } 
      hql.append(" or po.taskJoinedEmp like '%$" + empId + "$%' or po.taskPrincipal=" + empId + " or po.taskChecker=" + empId + " )");
      hql.append(" order by po.taskCreatedTime desc ");
      Query query = this.session.createQuery(hql.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      taskList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return taskList;
  }
  
  public List getRelationReportList(String userId, String empId, String orgId, String domainId, List underEmpList) {
    List reportList = null;
    try {
      StringBuffer sb = new StringBuffer(" select distinct workLog,poo.orgNameString from WorkLogPO workLog,OrganizationVO poo");
      sb.append(" where workLog.logDomainId=" + domainId + " ");
      sb.append(" and workLog.createdEmp=" + empId + " ");
      sb.append(" and workLog.createdOrg = poo.orgId ");
      sb.append(" order by workLog.logDate desc,workLog.empName,workLog.startPeriod ");
      Query query = this.session.createQuery(sb.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      reportList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return reportList;
  }
  
  public List getRelationDocList(String domainId, String empId, String whereDoc) {
    List taskList = null;
    try {
      String nowStr = "now()";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0)
        nowStr = "sysdate"; 
      StringBuffer hql = new StringBuffer("from NetDiskPO netdisk");
      if (whereDoc.equals(""))
        whereDoc = " 1=1 "; 
      hql.append(" where netdisk.domainId=" + domainId + " and netdisk.fileOwnId=" + empId + "  and netdisk.fileExtName='DIR' and (" + whereDoc + ")");
      hql.append(" and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (").append(nowStr).append(" between netdisk.readTimeFrom and netdisk.readTimeTo))");
      hql.append(" or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (").append(nowStr).append(" between netdisk.writeTimeFrom and netdisk.writeTimeTo)))");
      hql.append(" order by netdisk.fileSize,netdisk.fileCreatedTime desc");
      Query query = this.session.createQuery(hql.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      taskList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return taskList;
  }
  
  public List getRelationMsgList(String userId, String empId) {
    List msgList = null;
    try {
      StringBuffer hql = new StringBuffer("select chat.chatId,chat.chatContent,chat.chatTime,cu.id,cu.chatStatus,cu.isRead,chat.senderId,chat.senderName from com.js.oa.chat.po.ChatUserPO cu left join cu.chat chat");
      hql.append(" where cu.empId=")
        .append(userId)
        .append(" and chat.senderId=")
        .append(empId);
      Query query = this.session.createQuery(hql.toString());
      query.setFirstResult(0);
      query.setMaxResults(9);
      msgList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return msgList;
  }
  
  public List getRelationEmpInfoById(String empId) {
    List list = null;
    try {
      StringBuffer hql = new StringBuffer("select e.empName,o.orgName,e.empDuty,e.empPosition,e.empMobilePhone,e.empBusinessPhone,e.empEmail ");
      hql.append(" from EmployeeVO e,OrganizationVO o ");
      hql.append(" where e.empId=" + empId + " and o.orgId=(select u.orgId from EmployeeOrgVO u where u.empId=" + empId + " )");
      Query query = this.session.createQuery(hql.toString());
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
