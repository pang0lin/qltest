package com.js.oa.relproject.bean;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.relproject.po.RelProActorPO;
import com.js.oa.relproject.po.RelProItemPO;
import com.js.oa.relproject.po.RelProjectPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class RelProjectBean extends HibernateBase {
  public Long save(RelProjectPO po, String[] itemTitle, String[] itemStartTime, String[] itemEndTime, String[] itemReminds, String[] actors, String curUserId, String curUserName, String curOrgId, String flag) {
    Long proId = null;
    String title = "";
    try {
      begin();
      if ("1".equals(flag)) {
        proId = po.getId();
        List<E> itemList = new ArrayList();
        itemList = this.session.createQuery("select po.id from com.js.oa.relproject.po.RelProItemPO po where po.projectId=" + proId).list();
        MessagesBD messagesBD = new MessagesBD();
        if (!itemList.isEmpty() && itemList.size() > 0)
          for (int q = 0; q < itemList.size(); q++)
            messagesBD.delMessage(itemList.get(q).toString(), "ProjectItem", "b");  
        this.session.delete("from com.js.oa.relproject.po.RelProActorPO po where po.project.id=" + proId);
        this.session.delete("from com.js.oa.relproject.po.RelProItemPO po where po.projectId=" + proId);
        RelProjectPO opo = (RelProjectPO)this.session.load(RelProjectPO.class, proId);
        opo.setTitle(po.getTitle().trim());
        opo.setStartTime(po.getStartTime());
        opo.setEndTime(po.getEndTime());
        opo.setProDesc(po.getProDesc());
        opo.setStatus(po.getStatus());
        opo.setRate(po.getRate());
        opo.setClassId(po.getClassId());
        this.session.flush();
        title = String.valueOf(curUserName) + "重新配置了" + po.getTitle() + "项目信息";
      } else {
        proId = (Long)this.session.save(po);
        title = String.valueOf(curUserName) + "新建了" + po.getTitle() + "项目信息";
      } 
      String url = "/jsoa/RelProjectAction.do?flag=load&id=" + proId + "&openType=message";
      StringBuffer channelIssuerName = new StringBuffer();
      StringBuffer channelIssuerId = new StringBuffer();
      StringBuffer channelIssuerOrg = new StringBuffer();
      StringBuffer channelIssuerGroup = new StringBuffer();
      StringBuffer channelReaderName = new StringBuffer();
      StringBuffer channelReaderId = new StringBuffer();
      StringBuffer channelReaderOrg = new StringBuffer();
      StringBuffer channelReaderGroup = new StringBuffer();
      Set actorSet = new HashSet();
      List<Long> empList = new ArrayList<Long>();
      if (actors[0] != null) {
        String[][] actorArr = getActors(actors[0], actors[1]);
        if (actorArr != null && actorArr.length > 0) {
          for (int i = 0; i < actorArr.length; i++) {
            RelProActorPO actor = new RelProActorPO();
            actor.setProject(po);
            actor.setActorId(Long.valueOf(actorArr[i][0]));
            actor.setActorName(actorArr[i][1]);
            actor.setActorRole(Integer.valueOf(10));
            actor.setActorType(Integer.valueOf(10));
            this.session.save(actor);
            if (!empList.contains(Long.valueOf(actorArr[i][0])))
              empList.add(Long.valueOf(actorArr[i][0])); 
          } 
          channelIssuerId.append(actors[0]);
          channelIssuerName.append(actors[1]);
          channelReaderId.append(actors[0]);
          channelReaderName.append(String.valueOf(actors[1]) + ",");
        } 
        actorArr = getActors(actors[2], actors[3]);
        if (actorArr != null && actorArr.length > 0) {
          for (int i = 0; i < actorArr.length; i++) {
            RelProActorPO actor = new RelProActorPO();
            actor.setProject(po);
            actor.setActorId(Long.valueOf(actorArr[i][0]));
            actor.setActorName(actorArr[i][1]);
            actor.setActorRole(Integer.valueOf(20));
            actor.setActorType(Integer.valueOf(10));
            this.session.save(actor);
            if (!empList.contains(Long.valueOf(actorArr[i][0])))
              empList.add(Long.valueOf(actorArr[i][0])); 
          } 
          channelIssuerId.append(actors[2]);
          channelIssuerName.append(actors[3]);
          channelReaderId.append(actors[2]);
          if (actors[3].lastIndexOf(",") != actors[3].length() - 1) {
            channelReaderName.append(String.valueOf(actors[3]) + ",");
          } else {
            channelReaderName.append(actors[3]);
          } 
        } 
        actorArr = getActors(actors[4], actors[5]);
        if (actorArr != null && actorArr.length > 0)
          for (int i = 0; i < actorArr.length; i++) {
            RelProActorPO actor = new RelProActorPO();
            actor.setProject(po);
            actor.setActorId(Long.valueOf(actorArr[i][0]));
            actor.setActorName(actorArr[i][1]);
            actor.setActorRole(Integer.valueOf(30));
            if ("$".equals(actorArr[i][2])) {
              actor.setActorType(Integer.valueOf(10));
              channelIssuerId.append("$").append(actorArr[i][0]).append("$");
              channelReaderId.append("$").append(actorArr[i][0]).append("$");
            } else if ("*".equals(actorArr[i][2])) {
              actor.setActorType(Integer.valueOf(20));
              channelIssuerOrg.append("*").append(actorArr[i][0]).append("*");
              channelReaderOrg.append("*").append(actorArr[i][0]).append("*");
            } else {
              actor.setActorType(Integer.valueOf(30));
              channelIssuerGroup.append("@").append(actorArr[i][0]).append("@");
              channelReaderGroup.append("@").append(actorArr[i][0]).append("@");
            } 
            channelIssuerName.append(actorArr[i][1]).append(",");
            channelReaderName.append(actorArr[i][1]).append(",");
            this.session.save(actor);
            if (!empList.contains(Long.valueOf(actorArr[i][0])))
              empList.add(Long.valueOf(actorArr[i][0])); 
          }  
        actorArr = getActors(actors[6], actors[7]);
        if (actorArr != null && actorArr.length > 0)
          for (int i = 0; i < actorArr.length; i++) {
            RelProActorPO actor = new RelProActorPO();
            actor.setProject(po);
            actor.setActorId(Long.valueOf(actorArr[i][0]));
            actor.setActorName(actorArr[i][1]);
            actor.setActorRole(Integer.valueOf(40));
            if ("$".equals(actorArr[i][2])) {
              actor.setActorType(Integer.valueOf(10));
              channelReaderId.append("$").append(actorArr[i][0]).append("$");
            } else if ("*".equals(actorArr[i][2])) {
              actor.setActorType(Integer.valueOf(20));
              channelReaderOrg.append("*").append(actorArr[i][0]).append("*");
            } else {
              actor.setActorType(Integer.valueOf(30));
              channelReaderGroup.append("@").append(actorArr[i][0]).append("@");
            } 
            channelReaderName.append(actorArr[i][1]).append(",");
            this.session.save(actor);
            if (!empList.contains(Long.valueOf(actorArr[i][0])))
              empList.add(Long.valueOf(actorArr[i][0])); 
          }  
      } 
      if (itemTitle != null && itemTitle.length > 0) {
        Calendar itemStartCalendar = Calendar.getInstance();
        Calendar itemEndCalendar = Calendar.getInstance();
        Calendar mesEndCalendar = Calendar.getInstance();
        Date now = new Date();
        mesEndCalendar.set(2050, 12, 12);
        for (int i = 0; i < itemTitle.length; i++) {
          RelProItemPO itemPO = new RelProItemPO();
          itemPO.setTitle(itemTitle[i]);
          String startTime = itemStartTime[i];
          String endTime = itemEndTime[i];
          String itemStartTitle = String.valueOf(po.getTitle()) + "将要进行[" + itemTitle[i] + "]阶段";
          String itemEndTitle = String.valueOf(po.getTitle()) + "[" + itemTitle[i] + "]阶段将在" + endTime + "结束";
          itemPO.setProjectId(proId);
          startTime = startTime.replaceAll("-", "/");
          endTime = endTime.replaceAll("-", "/");
          itemPO.setStartTime(new Date(startTime));
          itemPO.setEndTime(new Date(endTime));
          itemPO.setItemRemind(Integer.valueOf(itemReminds[i]));
          long itemId = ((Long)this.session.save(itemPO)).longValue();
          itemStartCalendar.setTime(new Date(startTime));
          itemStartCalendar.add(5, -Integer.valueOf(itemReminds[i]).intValue());
          itemStartCalendar.set(11, 0);
          itemStartCalendar.set(12, 1);
          itemEndCalendar.setTime(new Date(endTime));
          itemEndCalendar.add(5, -Integer.valueOf(itemReminds[i]).intValue());
          itemEndCalendar.set(11, 0);
          itemEndCalendar.set(12, 1);
          if (itemEndCalendar.getTime().getTime() >= now.getTime() - 86400000L)
            if (itemStartCalendar.getTime().getTime() < now.getTime() - 86400000L) {
              if (!empList.isEmpty() && empList.size() > 0)
                for (int j = 0; j < empList.size(); j++)
                  RemindUtil.sendMessageToUsers(itemEndTitle, url, ((Long)empList.get(j)).toString(), "ProjectItem", itemEndCalendar.getTime(), mesEndCalendar.getTime(), curUserName, Long.valueOf(itemId));  
            } else if (!empList.isEmpty() && empList.size() > 0) {
              for (int j = 0; j < empList.size(); j++) {
                RemindUtil.sendMessageToUsers(itemStartTitle, url, ((Long)empList.get(j)).toString(), "ProjectItem", itemStartCalendar.getTime(), mesEndCalendar.getTime(), curUserName, Long.valueOf(itemId));
                RemindUtil.sendMessageToUsers(itemEndTitle, url, ((Long)empList.get(j)).toString(), "ProjectItem", itemEndCalendar.getTime(), mesEndCalendar.getTime(), curUserName, Long.valueOf(itemId));
              } 
            }  
        } 
      } 
      Calendar calendar = Calendar.getInstance();
      calendar.set(2050, 12, 12);
      empList.remove(Long.valueOf(curUserId));
      if (!empList.isEmpty() && empList.size() > 0)
        for (int j = 0; j < empList.size(); j++)
          RemindUtil.sendMessageToUsers(title, url, ((Long)empList.get(j)).toString(), "RelProject", new Date(), calendar.getTime(), curUserName, proId);  
      if ("1".equals(flag)) {
        updateChannel(proId, 
            po.getTitle(), 
            curUserId, 
            curUserName, 
            curOrgId, 
            channelIssuerName.toString(), 
            channelIssuerId.toString(), 
            channelIssuerOrg.toString(), 
            channelIssuerGroup.toString(), 
            channelReaderName.toString(), 
            channelReaderId.toString(), 
            channelReaderOrg.toString(), 
            channelReaderGroup.toString(), 
            actors[1], 
            actors[0], 
            flag, 
            Long.valueOf(po.getClassId()));
      } else {
        saveChannel(proId, 
            po.getTitle(), 
            curUserId, 
            curUserName, 
            curOrgId, 
            channelIssuerName.toString(), 
            channelIssuerId.toString(), 
            channelIssuerOrg.toString(), 
            channelIssuerGroup.toString(), 
            channelReaderName.toString(), 
            channelReaderId.toString(), 
            channelReaderOrg.toString(), 
            channelReaderGroup.toString(), 
            actors[1], 
            actors[0], 
            flag, 
            Long.valueOf(po.getClassId()));
      } 
      if ("1".equals(flag)) {
        updateBBSClass(proId, 
            po.getTitle(), 
            curUserId, 
            curUserName, 
            curOrgId, 
            channelReaderName.toString(), 
            channelReaderId.toString(), 
            channelReaderOrg.toString(), 
            channelReaderGroup.toString(), 
            actors[1], 
            actors[0], 
            flag, 
            Long.valueOf(po.getClassId()));
      } else {
        saveBBSClass(proId, 
            po.getTitle(), 
            curUserId, 
            curUserName, 
            curOrgId, 
            channelReaderName.toString(), 
            channelReaderId.toString(), 
            channelReaderOrg.toString(), 
            channelReaderGroup.toString(), 
            actors[1], 
            actors[0], 
            flag, 
            Long.valueOf(po.getClassId()));
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return proId;
  }
  
  public Map getProjectInfo(String proId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      begin();
      RelProjectPO po = (RelProjectPO)this.session.get(RelProjectPO.class, Long.valueOf(proId));
      map.put("projectInfo", po);
      List list = this.session.createQuery("select po.id,po.title,po.startTime,po.endTime,po.itemRemind from com.js.oa.relproject.po.RelProItemPO po where po.projectId=" + proId + " order by po.startTime").list();
      map.put("projectItem", list);
      list = this.session.createQuery("select po.actorRole,po.actorType,po.actorId,po.actorName from com.js.oa.relproject.po.RelProActorPO po where po.project.id=" + proId + " order by po.actorRole").list();
      map.put("projectActor", list);
      list = this.session.createQuery("select po.actorId,po.actorName from com.js.oa.relproject.po.RelProActorPO po where po.actorRole=10 and po.project.id=" + proId + " order by po.id").list();
      map.put("promanager", list);
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          throw ex;
        }  
      throw e;
    } 
    return map;
  }
  
  public ForumClassPO getForumClass(String proId) {
    ForumClassPO forumClassPO = new ForumClassPO();
    try {
      begin();
      List<ForumClassPO> list = this.session.createQuery("select f from com.js.oa.bbs.po.ForumClassPO f where f.relProjectId=" + Long.parseLong(proId)).list();
      if (!list.isEmpty())
        forumClassPO = list.get(0); 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return forumClassPO;
  }
  
  public Map getRelationDetail(String proId, String userId, String orgId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      StringBuffer sqlBuffer = new StringBuffer();
      begin();
      RelProjectPO po = (RelProjectPO)this.session.load(RelProjectPO.class, Long.valueOf(proId));
      map.put("projectInfo", po);
      boolean isPrincipal = false;
      Iterator it = this.session.createQuery("select count(*) from com.js.oa.relproject.po.RelProActorSinglePO act where act.projectId=" + proId + " and act.actorId=" + userId + " and act.actorType=10 and act.actorRole=10").iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && Integer.parseInt(obj.toString()) > 0)
          isPrincipal = true; 
      } 
      Calendar date = Calendar.getInstance();
      String today = String.valueOf(date.get(1)) + "/" + (date.get(2) + 1) + "/" + date.get(5);
      sqlBuffer.append("select po.title from com.js.oa.relproject.po.RelProItemPO po where po.projectId=").append(proId);
      if (databaseType.indexOf("mysql") >= 0) {
        sqlBuffer.append(" and '").append(today).append("'>=po.startTime AND '").append(today).append("'<=po.endTime");
      } else {
        sqlBuffer.append(" AND JSDB.FN_STRTODATE('").append(today).append("','S')>=po.startTime AND JSDB.FN_STRTODATE('").append(today).append("','S')<=po.endTime ");
      } 
      List<Object[]> list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projectItem", list.get(0)); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select distinct aaa.workTitle,aaa.workSubmitPerson, aaa.workSubmitTime,")
        .append("aaa.workProcessId,aaa.workTableId, aaa.workRecordId ")
        .append("from com.js.oa.jsflow.po.WFWorkPO aaa ")
        .append("where aaa.relProjectId=").append(proId);
      if (!isPrincipal) {
        sqlBuffer.append(" and aaa.wfCurEmployeeId=").append(userId);
      } else {
        sqlBuffer.append(" and aaa.workStartFlag=1");
      } 
      sqlBuffer.append(" order by aaa.workSubmitTime desc");
      Query query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectflow", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select bbb.channelId,bbb.channelName,aaa.informationId, aaa.informationTitle, ")
        .append(" aaa.informationIssueTime,aaa.informationIssuer,aaa.informationType,aaa.informationHeadFile")
        .append(" from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb")
        .append(" where bbb.relProjectId=").append(proId)
        .append(" order by aaa.orderCodeTemp desc,aaa.informationModifyTime desc, aaa.informationIssueTime desc, aaa.informationId desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectdoc", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select ev.eventId,ev.eventTitle,ev.eventBeginDate,ev.eventEndDate,")
        .append(" ev.eventEmpId,ev.eventEmpName")
        .append(" from com.js.oa.scheme.event.po.EventPO ev")
        .append(" where ev.relProjectId=")
        .append(proId)
        .append(" order by ev.eventId desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectevent", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select apo.boardroomApplyId,apo.motif,bpo.name,bpo.location,")
        .append("apo.emceeName,mpo.meetingDate,mpo.startTime,mpo.endTime,mpo.id,mpo.sortNum")
        .append(" from com.js.oa.routine.boardroom.po.BoardRoomApplyPO apo join apo.boardroom bpo join apo.meetingTime mpo")
        .append(" where apo.status=0 and apo.relProjectId=").append(proId)
        .append(" order by mpo.meetingDate desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectmeeting", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,poo.id,po.forumTitle,po.forumIssueTime,po.forumAuthor,po.forumAuthorId,poo.createdEmp,")
        .append("poo.createdOrg,po.forumType,po.forumIsSoul,poo.classHasJunior,")
        .append("poo.classOwnerIds,poo.classParentName,po.anonymous,")
        .append("po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint ")
        .append(" from com.js.oa.bbs.po.ForumPO po join po.forumClass poo ")
        .append(" where  po.forumTopicId=0 and poo.relProjectId=").append(proId)
        .append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        map.put("projectbbs", list);
      } 
      list = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.relProjectId =" + proId).list();
      if (!list.isEmpty())
        map.put("forumClassID", list.get(0)); 
      StringBuffer sqlBuffer1 = new StringBuffer("");
      sqlBuffer1.append(" and ( po.empId=").append(userId).append(" or po.reportReaderId like '%$").append(userId).append("$%' )");
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.previousReport,po.reportTime,po.reportEmpName,po.reportReaderId,po.relprojectId");
      sqlBuffer.append(" from com.js.oa.scheme.workreport.po.WorkReportPO po");
      sqlBuffer.append(" where po.relprojectId=").append(proId).append(sqlBuffer1.toString());
      sqlBuffer.append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projectlog", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.taskTitle,po.taskBeginTime,po.taskPrincipal,po.taskPrincipalName,po.isArranged")
        .append(" from com.js.oa.scheme.taskcenter.po.TaskPO po")
        .append(" where po.relProjectId=").append(proId)
        .append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projecttask", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.content,po.sendTime,po.empName")
        .append(" from com.js.oa.relproject.po.ProNotePO po")
        .append(" where po.projectId=").append(proId)
        .append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projectnote", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.actorId,po.actorName")
        .append(" from com.js.oa.relproject.po.RelProActorPO po")
        .append(" where po.actorRole=10 and po.project.id=").append(proId)
        .append(" order by po.id ");
      query = this.session.createQuery(sqlBuffer.toString());
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("promanager", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.channelId")
        .append(" from com.js.oa.info.channelmanager.po.InformationChannelPO po")
        .append(" where   po.relProjectId=").append(proId);
      String channelId = this.session.createQuery(sqlBuffer.toString()).iterate().next().toString();
      map.put("proChannelId", channelId);
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return map;
  }
  
  public boolean deleteProject(String ids) {
    boolean res = false;
    String sql_query = "";
    String channel_ids = "";
    sql_query = "select channel_id from oa_informationchannel where relproject_id in (" + ids + ")";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql_query);
      while (rs.next())
        channel_ids = String.valueOf(channel_ids) + rs.getString(1) + ","; 
      rs.close();
      channel_ids = String.valueOf(channel_ids) + "-1";
      stmt.executeUpdate("delete from oa_information where channel_id in (" + channel_ids + ")");
      stmt.executeUpdate("delete from oa_informationchannel where relproject_id in (" + ids + ")");
      begin();
      this.session.delete("from com.js.oa.relproject.po.ProNotePO po where po.projectId in (" + ids + ")");
      this.session.delete("from com.js.oa.bbs.po.ForumClassPO po where po.relProjectId in (" + ids + ")");
      this.session.delete("from com.js.oa.relproject.po.RelProActorPO po where po.id in (" + ids + ")");
      this.session.delete("from com.js.oa.relproject.po.RelProItemPO po where po.projectId in (" + ids + ")");
      this.session.delete("from com.js.oa.relproject.po.RelProjectPO po where po.id in (" + ids + ")");
      stmt.executeUpdate("update REP_LOG  set relproject_id=null where relproject_id=" + ids);
      stmt.close();
      conn.close();
      this.session.flush();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return res;
  }
  
  public List getActiveProject(String userId, String orgId, String orgIdString) {
    List list = null;
    try {
      begin();
      String sql = "select distinct po.id,po.title from com.js.oa.relproject.po.RelProjectPO po left join po.projectActor act where 1=1 ";
      if ("1".equals(SystemCommon.getGlxmstatus())) {
        sql = String.valueOf(sql) + " ";
      } else {
        sql = String.valueOf(sql) + " and  po.status=10  ";
      } 
      if (!"1".equals(SystemCommon.getGlxmlimit())) {
        String where = getScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
        sql = String.valueOf(sql) + " and " + where;
      } 
      list = this.session.createQuery(sql).list();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getActiveProject(String userId, String orgId, String orgIdString, String keyword) {
    List list = null;
    try {
      begin();
      String sql = "select distinct po.id,po.title from com.js.oa.relproject.po.RelProjectPO po left join po.projectActor act where 1=1";
      if (keyword != null && !"".equals(keyword))
        sql = String.valueOf(sql) + " and po.title like '%" + keyword + "%' "; 
      if ("1".equals(SystemCommon.getGlxmstatus())) {
        sql = String.valueOf(sql) + " ";
      } else {
        sql = String.valueOf(sql) + " and po.status=10 ";
      } 
      if (!"1".equals(SystemCommon.getGlxmlimit())) {
        String where = getScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
        sql = String.valueOf(sql) + " and " + where;
      } 
      list = this.session.createQuery(sql).list();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getProjectList(String para, String from, String where) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery(String.valueOf(para) + from + where).list();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  private String getScopeWhere(String userId, String orgId, String orgIdString, String fieldId, String fieldType) {
    StringBuffer where = new StringBuffer("(");
    ManagerService ms = new ManagerService();
    if (ms.hasRight(userId, "04*10*02")) {
      where.append("(");
    } else {
      where.append("(").append(fieldId).append("=").append(userId);
      where.append(" and ");
    } 
    where.append(fieldType).append("=10)");
    where.append(" or ( ( 1<>1");
    orgIdString = "$" + orgIdString + "$";
    String[] arr = orgIdString.split("\\$\\$");
    int i;
    for (i = 0; i < arr.length; i++) {
      if (arr[i] != null && !arr[i].equals("") && !arr[i].equals(" "))
        where.append(" or ").append(fieldId).append("=").append(arr[i]); 
    } 
    where.append(") and ").append(fieldType).append("=20)");
    try {
      List list = this.session.createQuery("select groups.groupId from com.js.system.vo.usermanager.EmployeeVO user join user.groups groups where user.empId = " + 
          userId).list();
      if (list != null && list.size() > 0) {
        where.append(" or ( ( 1<>1");
        for (i = 0; i < list.size(); i++)
          where.append(" or ").append(fieldId).append("=").append(list.get(i)); 
        where.append(") and ").append(fieldType).append("=30)");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    where.append(")");
    return where.toString();
  }
  
  public String getCurScopeWhere(String userId, String orgId, String orgIdString, String fieldId, String fieldType) {
    String where = "";
    try {
      begin();
      where = getScopeWhere(userId, orgId, orgIdString, fieldId, fieldType);
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return where;
  }
  
  public List getActorByProId(long proId) {
    List list = new ArrayList();
    try {
      begin();
      list = this.session.createQuery("select actor.actorId from com.js.oa.relproject.po.RelProActorPO actor where actor.project.id=" + proId + " and actor.actorRole<>40").list();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public boolean getfuzByProId(long proId, long userId) {
    List list = new ArrayList();
    boolean re = false;
    try {
      begin();
      list = this.session.createQuery("select actor.actorId from com.js.oa.relproject.po.RelProActorPO actor where actor.project.id=" + proId + " and actor.actorRole=10 and actor.actorId =" + userId).list();
      if (!list.isEmpty())
        re = true; 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return re;
  }
  
  private Long saveChannel(Long proId, String title, String curUserId, String curUserName, String curOrgId, String channelIssuerName, String channelIssuerId, String channelIssuerOrg, String channelIssuerGroup, String channelReaderName, String channelReaderId, String channelReaderOrg, String channelReaderGroup, String managerName, String managerId, String flag, Long proclassId) throws Exception {
    Long channelId = null;
    try {
      InformationChannelPO channelPO = new InformationChannelPO();
      channelPO.setRelProjectId(Long.valueOf(proId.longValue()));
      channelPO.setProClassId(Long.valueOf(0L));
      channelPO.setChannelType(2);
      channelPO.setChannelParentId(Long.valueOf(0L));
      channelPO.setChannelLevel(1);
      channelPO.setIsAllowReview("1");
      channelPO.setAfficheChannelStatus("0");
      channelPO.setDomainId(Long.valueOf(0L));
      channelPO.setIncludeChild(0);
      channelPO.setDesktopType(Integer.valueOf(0));
      channelPO.setInfoNum(Integer.valueOf(10));
      channelPO.setUserDefine("1");
      channelPO.setOnDepaDesk(0);
      channelPO.setPositionUpDown(1);
      channelPO.setChannelPosition(0);
      channelPO.setIsRollOnDesktop(0);
      channelPO.setOnDesktop(0);
      channelPO.setCreatedEmp(Long.valueOf(curUserId));
      channelPO.setCreatedEmpName(curUserName);
      channelPO.setCreatedOrg(Long.valueOf(curOrgId));
      channelPO.setChannelShowType(0);
      channelPO.setChannelNeedCheckup(0);
      channelPO.setChannelSort(500000);
      channelPO.setChannelName(title);
      channelPO.setChannelIssuerName(channelIssuerName.toString());
      channelPO.setChannelIssuer(channelIssuerId.toString());
      channelPO.setChannelIssuerOrg(channelIssuerOrg.toString());
      channelPO.setChannelIssuerGroup(channelIssuerGroup.toString());
      channelPO.setChannelManagerName(managerName);
      channelPO.setChannelManager(managerId);
      channelPO.setChannelManagerOrg("");
      channelPO.setChannelManagerGroup("");
      channelPO.setChannelReaderName(getFilerRangeName(channelReaderName.toString()));
      channelPO.setChannelReader(getFilerRangeEmpId(channelReaderId.toString()));
      channelPO.setChannelReaderOrg(getFilerRangeOrgId(channelReaderOrg.toString()));
      channelPO.setChannelReaderGroup(getFilerRangeGroupId(channelReaderGroup.toString()));
      channelId = (Long)this.session.save(channelPO);
      List<Object[]> list = this.session.createQuery("select po.channelId,po.channelIdString,po.channelLevel from com.js.oa.info.channelmanager.po.InformationChannelPO  po  where po.proClassId =" + proclassId).list();
      Object[] obj = list.get(0);
      channelPO.setChannelParentId(Long.valueOf(obj[0].toString()));
      channelPO.setChannelIdString(String.valueOf(obj[1].toString()) + 500000 + "$" + channelId + "$" + "_");
      channelPO.setChannelLevel((short)(Short.parseShort(obj[2].toString()) + 1));
    } catch (Exception ex) {
      throw ex;
    } 
    return channelId;
  }
  
  private Long saveBBSClass(Long proId, String title, String curUserId, String curUserName, String curOrgId, String classUserName, String classUserId, String classUserOrg, String classUserGroup, String managerName, String managerId, String flag, Long proclassId) throws Exception {
    Long classId = null;
    try {
      if (classUserId != null && !"".equals(classUserId) && !"null".equals(classUserId)) {
        List<E> listtemp = getIdStr(classUserId, classUserName);
        classUserId = listtemp.get(0).toString();
        classUserName = listtemp.get(1).toString();
      } 
      ForumClassPO classPO = new ForumClassPO();
      classPO.setRelProjectId(proId);
      classPO.setClassOwnerId(0L);
      classPO.setDomainId(Long.valueOf(0L));
      classPO.setFullDay(1);
      classPO.setBanPrint("0");
      classPO.setCreatedEmp(Long.parseLong(curUserId));
      classPO.setCreatedEmpName(curUserName);
      classPO.setCreatedOrg(Long.parseLong(curOrgId));
      classPO.setClassDate(new Date());
      classPO.setClassName(title);
      managerId = managerId.replaceAll("\\$", "\\*");
      classPO.setClassOwnerIds(managerId);
      classPO.setClassOwnerName(managerName);
      classPO.setClassUserName(classUserName);
      classPO.setClassUserId(classUserId);
      classPO.setClassUserOrg(classUserOrg);
      classPO.setClassUserGroup(classUserGroup);
      classPO.setClassHasJunior((byte)2);
      classPO.setProClassId(Long.valueOf(0L));
      classPO.setClassSortCode(500000);
      classId = (Long)this.session.save(classPO);
      List<Object[]> list = this.session.createQuery("select po.id,po.className,po.classSort,po.classLevel from com.js.oa.bbs.po.ForumClassPO po  where po.proClassId =" + proclassId).list();
      Object[] obj = list.get(0);
      classPO.setClassParent(Long.valueOf(obj[0].toString()).longValue());
      classPO.setClassParentName(obj[1].toString());
      classPO.setClassSort(String.valueOf(obj[2].toString()) + "_" + classPO.getClassSortCode() + "$" + classId + "$");
      classPO.setClassLevel((short)(Short.parseShort(obj[3].toString()) + 1));
      this.session.flush();
    } catch (Exception ex) {
      throw ex;
    } 
    return classId;
  }
  
  private Long updateChannel(Long proId, String title, String curUserId, String curUserName, String curOrgId, String channelIssuerName, String channelIssuerId, String channelIssuerOrg, String channelIssuerGroup, String channelReaderName, String channelReaderId, String channelReaderOrg, String channelReaderGroup, String managerName, String managerId, String flag, Long proclassId) throws Exception {
    Long channelId = null;
    try {
      Iterator<InformationChannelPO> it = this.session.createQuery("select po from com.js.oa.info.channelmanager.po.InformationChannelPO po where  po.relProjectId=" + proId).iterate();
      if (it != null && it.hasNext()) {
        InformationChannelPO channelPO = it.next();
        channelPO.setProClassId(Long.valueOf(0L));
        channelPO.setRelProjectId(proId);
        channelPO.setChannelSort(500000);
        channelPO.setChannelName(title);
        channelPO.setChannelIssuerName(channelIssuerName.toString());
        channelPO.setChannelIssuer(channelIssuerId.toString());
        channelPO.setChannelIssuerOrg(channelIssuerOrg.toString());
        channelPO.setChannelIssuerGroup(channelIssuerGroup.toString());
        channelPO.setChannelManagerName(managerName);
        channelPO.setChannelManager(managerId);
        channelPO.setChannelManagerOrg("");
        channelPO.setChannelManagerGroup("");
        channelPO.setChannelReaderName(getFilerRangeName(channelReaderName.toString()));
        channelPO.setChannelReader(getFilerRangeEmpId(channelReaderId.toString()));
        channelPO.setChannelReaderOrg(getFilerRangeOrgId(channelReaderOrg.toString()));
        channelPO.setChannelReaderGroup(getFilerRangeGroupId(channelReaderGroup.toString()));
        List<Object[]> list = this.session.createQuery("select po.channelId,po.channelIdString,po.channelLevel from com.js.oa.info.channelmanager.po.InformationChannelPO  po  where po.proClassId =" + proclassId).list();
        Object[] obj = list.get(0);
        channelPO.setChannelParentId(Long.valueOf(obj[0].toString()));
        channelPO.setChannelIdString(String.valueOf(obj[1].toString()) + 500000 + "$" + channelPO.getChannelId() + "$" + "_");
        channelPO.setChannelLevel((short)(Short.parseShort(obj[2].toString()) + 1));
        this.session.flush();
      } else {
        saveChannel(proId, 
            title, 
            curUserId, 
            curUserName, 
            curOrgId, 
            channelIssuerName, 
            channelIssuerId, 
            channelIssuerOrg, 
            channelIssuerGroup, 
            channelReaderName, 
            channelReaderId, 
            channelReaderOrg, 
            channelReaderGroup, 
            managerName, 
            managerId, 
            flag, 
            proclassId);
      } 
    } catch (Exception ex) {
      throw ex;
    } 
    return channelId;
  }
  
  private Long updateBBSClass(Long proId, String title, String curUserId, String curUserName, String curOrgId, String classUserName, String classUserId, String classUserOrg, String classUserGroup, String managerName, String managerId, String flag, Long proclassId) throws Exception {
    Long classId = null;
    try {
      Iterator<ForumClassPO> it = this.session.createQuery("select po from com.js.oa.bbs.po.ForumClassPO po where po.relProjectId=" + proId).iterate();
      if (it.hasNext()) {
        ForumClassPO classPO = it.next();
        if (classUserId != null && !"".equals(classUserId) && !"null".equals(classUserId)) {
          List<E> listtemp = getIdStr(classUserId, classUserName);
          classUserId = listtemp.get(0).toString();
          classUserName = listtemp.get(1).toString();
        } 
        classPO.setClassName(title);
        classPO.setProClassId(Long.valueOf(0L));
        classPO.setClassParentName(title);
        managerId = managerId.replaceAll("\\$", "\\*");
        classPO.setClassOwnerIds(managerId);
        classPO.setClassOwnerName(managerName);
        classPO.setClassUserName(classUserName);
        classPO.setClassUserId(classUserId);
        classPO.setClassUserOrg(classUserOrg);
        classPO.setClassUserGroup(classUserGroup);
        classPO.setClassHasJunior((byte)2);
        List<Object[]> list = this.session.createQuery("select po.id,po.className,po.classSort,po.classLevel  from com.js.oa.bbs.po.ForumClassPO po  where po.proClassId =" + proclassId).list();
        Object[] obj = list.get(0);
        classPO.setClassParent(Long.valueOf(obj[0].toString()).longValue());
        classPO.setClassParentName(obj[1].toString());
        classPO.setClassSort(String.valueOf(obj[2].toString()) + "_" + classPO.getClassSortCode() + "$" + classPO.getId() + "$");
        classPO.setClassLevel((short)(Short.parseShort(obj[3].toString()) + 1));
        this.session.flush();
      } else {
        saveBBSClass(proId, 
            title, 
            curUserId, 
            curUserName, 
            curOrgId, 
            classUserName, 
            classUserId, 
            classUserOrg, 
            classUserGroup, 
            managerName, 
            managerId, 
            flag, 
            proclassId);
      } 
    } catch (Exception ex) {
      throw ex;
    } 
    return classId;
  }
  
  public Map getProjectLeaders(String proId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select project_id,actorname from pro_actor where actorrole=10 and project_id in (" + proId + ") order by project_id");
      String preId = "0", temp = "";
      while (rs.next()) {
        proId = rs.getString(1);
        String actorName = rs.getString(2);
        if (!preId.equals(proId)) {
          map.put(preId, temp);
          temp = actorName;
          preId = proId;
          continue;
        } 
        if ("".equals(temp)) {
          temp = actorName;
          continue;
        } 
        temp = String.valueOf(temp) + "," + actorName;
      } 
      if (!temp.equals(""))
        map.put(preId, temp); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return map;
  }
  
  public Map getProjectClass(String proId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select pb.id,pc.name from pro_body pb left join pro_class pc on pb.classid=pc.id and pb.id in (" + proId + ")");
      String classId = "0", className = "";
      while (rs.next()) {
        proId = rs.getString(1);
        className = rs.getString(2);
        if (className == null || "null".equals(className))
          className = ""; 
        map.put(proId, className);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return map;
  }
  
  private String[][] getActors(String userIds, String userNames) {
    String[][] res = (String[][])null;
    if (userIds != null && userIds.length() > 0) {
      String[] names = userNames.split(",");
      res = new String[names.length][3];
      int i = 0;
      while (userIds.length() > 0) {
        char _tag = userIds.charAt(0);
        userIds = userIds.substring(1, userIds.length());
        String tmpId = userIds.substring(0, userIds.indexOf(_tag));
        userIds = userIds.substring(userIds.indexOf(_tag) + 1);
        String tmpName = names[i];
        res[i][0] = tmpId;
        res[i][1] = tmpName;
        res[i][2] = String.valueOf(_tag);
        i++;
      } 
    } 
    return res;
  }
  
  public String validateTitle(String titile, String porId) {
    String re = "Y";
    String sql = "select po from com.js.oa.relproject.po.RelProjectPO po where po.title='" + titile + "'";
    if (porId != null && !"".equals(porId) && !"null".equals(porId))
      sql = String.valueOf(sql) + " and po.id<>" + porId; 
    try {
      begin();
      List list = this.session.createQuery(sql).list();
      if (!list.isEmpty())
        re = "N"; 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return re;
  }
  
  public RelProjectPO getRelProjectPO(String proId) {
    RelProjectPO relProjectPO = null;
    try {
      begin();
      relProjectPO = (RelProjectPO)this.session.get(RelProjectPO.class, Long.valueOf(Long.parseLong(proId)));
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return relProjectPO;
  }
  
  public boolean isPrincipal(String proId, String userId) {
    boolean isPrincipal = false;
    try {
      begin();
      Iterator it = this.session.createQuery("select count(*) from com.js.oa.relproject.po.RelProActorSinglePO act where act.projectId=" + proId + " and act.actorId=" + userId + " and act.actorType=10 and act.actorRole=10").iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && Integer.parseInt(obj.toString()) > 0)
          isPrincipal = true; 
      } 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return isPrincipal;
  }
  
  public List getProjectFuze(String userId, String orgId, String orgIdString) {
    List list = new ArrayList();
    try {
      String where = getCurScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
      where = "where  " + where;
      begin();
      list = this.session.createQuery("select distinct new com.js.oa.crm.po.cmp.Link2Vo(po.id,po.title) from RelProjectPO po join po.projectActor act " + where).list();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public Map getProjectPepole(String proId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      begin();
      List list = this.session.createQuery("from RelProActorPO po where po.actorRole=10 and po.project.id=" + proId + " order by po.id").list();
      List list1 = this.session.createQuery("from RelProActorPO po where po.actorRole=20 and po.project.id=" + proId + " order by po.id").list();
      List list2 = this.session.createQuery("from RelProActorPO po where po.actorRole=30 and po.project.id=" + proId + " order by po.id").list();
      List list3 = this.session.createQuery("from RelProActorPO po where po.actorRole=40 and po.project.id=" + proId + " order by po.id").list();
      map.put("chager_user", list);
      map.put("leade_user", list1);
      map.put("join_user", list2);
      map.put("about_user", list3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
        this.session = null;
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return map;
  }
  
  public EmployeeVO getEmployeeInfoById(String userId) {
    EmployeeVO vo = null;
    try {
      begin();
      vo = (EmployeeVO)this.session.load(EmployeeVO.class, Long.valueOf(userId));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return vo;
  }
  
  public Map getRelationDetail(String proId, String userId, String orgId, String orgIdString) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      StringBuffer sqlBuffer = new StringBuffer();
      begin();
      RelProjectPO po = (RelProjectPO)this.session.load(RelProjectPO.class, Long.valueOf(proId));
      map.put("projectInfo", po);
      Set actorSet = po.getProjectActor();
      Iterator<RelProActorPO> iterator = actorSet.iterator();
      List<RelProActorPO> proLeader = new ArrayList<RelProActorPO>();
      List<RelProActorPO> proJoiner = new ArrayList<RelProActorPO>();
      List<RelProActorPO> proPrincipal = new ArrayList<RelProActorPO>();
      List<RelProActorPO> proAboutMan = new ArrayList<RelProActorPO>();
      while (iterator.hasNext()) {
        RelProActorPO actorPo = iterator.next();
        if (actorPo.getActorRole().equals(Integer.valueOf(10))) {
          proPrincipal.add(actorPo);
          continue;
        } 
        if (actorPo.getActorRole().equals(Integer.valueOf(20))) {
          proLeader.add(actorPo);
          continue;
        } 
        if (actorPo.getActorRole().equals(Integer.valueOf(30))) {
          proJoiner.add(actorPo);
          continue;
        } 
        if (actorPo.getActorRole().equals(Integer.valueOf(40)))
          proAboutMan.add(actorPo); 
      } 
      map.put("proLeader", proLeader);
      map.put("proJoiner", proJoiner);
      map.put("proPrincipal", proPrincipal);
      map.put("proAboutMan", proAboutMan);
      boolean isPrincipal = false;
      Iterator it = this.session.createQuery("select count(*) from com.js.oa.relproject.po.RelProActorSinglePO act where act.projectId=" + proId + " and act.actorId=" + userId + " and act.actorType=10 and act.actorRole=10").iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && Integer.parseInt(obj.toString()) > 0)
          isPrincipal = true; 
      } 
      boolean isLeader = false;
      it = this.session.createQuery("select count(*) from com.js.oa.relproject.po.RelProActorSinglePO act where act.projectId=" + proId + " and act.actorId=" + userId + " and act.actorType=10 and act.actorRole=20").iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && Integer.parseInt(obj.toString()) > 0) {
          isLeader = true;
          isPrincipal = true;
        } 
      } 
      map.put("isPrincipal", Boolean.valueOf(isPrincipal));
      map.put("isLeader", Boolean.valueOf(isLeader));
      sqlBuffer.append("select po.title from com.js.oa.relproject.po.RelProItemPO po where po.projectId=").append(proId);
      if (databaseType.indexOf("mysql") >= 0) {
        sqlBuffer.append(" and now()>=po.startTime AND now()<po.endTime");
      } else {
        Calendar date = Calendar.getInstance();
        String today = String.valueOf(date.get(1)) + "/" + (date.get(2) + 1) + "/" + date.get(5);
        sqlBuffer.append(" AND JSDB.FN_STRTODATE('").append(today).append("','S')>=po.startTime AND JSDB.FN_STRTODATE('").append(today).append("','S')<=po.endTime ");
      } 
      List<Object[]> list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projectItem", list.get(0)); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select distinct aaa.workTitle,aaa.workSubmitPerson, aaa.workSubmitTime,")
        .append("aaa.workProcessId,aaa.workTableId, aaa.workRecordId ")
        .append("from com.js.oa.jsflow.po.WFWorkPO aaa ")
        .append("where aaa.relProjectId=").append(proId);
      if (!isPrincipal && !isLeader) {
        sqlBuffer.append(" and aaa.wfCurEmployeeId=").append(userId);
      } else {
        sqlBuffer.append(" and aaa.workStartFlag=1");
      } 
      sqlBuffer.append(" order by aaa.workSubmitTime desc");
      Query query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectflow", list); 
      InformationBD infoBD = new InformationBD();
      String proChannelIdString = infoBD.getProChannelId(proId);
      String readerWhere = infoBD.getInfoReader(userId, orgId, orgIdString, "aaa");
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select bbb.channelId,bbb.channelName,aaa.informationId, aaa.informationTitle, ")
        .append(" aaa.informationIssueTime,aaa.informationIssuer,aaa.informationType,aaa.informationHeadFile,aaa.titleColor")
        .append(" from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb")
        .append(" where (bbb.relProjectId=").append(proId)
        .append(" or aaa.otherChannel like '%," + proChannelIdString + ",%'")
        .append(") and (")
        .append(" bbb.channelReader like '%$" + userId + "$%' or ")
        .append(readerWhere)
        .append(") order by aaa.orderCodeTemp desc,aaa.informationModifyTime desc, aaa.informationIssueTime desc, aaa.informationId desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectdoc", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select ev.eventId,ev.eventTitle,ev.eventBeginDate,ev.eventEndDate,")
        .append(" ev.eventEmpId,ev.eventEmpName,ev.onTimeMode,ev.onTimeContent ")
        .append(" from com.js.oa.scheme.event.po.EventPO ev")
        .append(" where ev.relProjectId=")
        .append(proId)
        .append(" order by ev.eventId desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectevent", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select apo.boardroomApplyId,apo.motif,bpo.name,bpo.location,")
        .append("apo.emceeName,mpo.meetingDate,mpo.startTime,mpo.endTime,mpo.id,mpo.sortNum")
        .append(" from com.js.oa.routine.boardroom.po.BoardRoomApplyPO apo join apo.boardroom bpo join apo.meetingTime mpo")
        .append(" where apo.status=0 and apo.relProjectId=").append(proId)
        .append(" order by mpo.meetingDate desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("projectmeeting", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,poo.id,po.forumTitle,po.forumIssueTime,po.forumAuthor,po.forumAuthorId,poo.createdEmp,")
        .append("poo.createdOrg,po.forumType,po.forumIsSoul,poo.classHasJunior,")
        .append("poo.classOwnerIds,poo.classParentName,po.anonymous,")
        .append("po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint ")
        .append(" from com.js.oa.bbs.po.ForumPO po join po.forumClass poo ")
        .append(" where  po.forumTopicId=0 and poo.relProjectId=").append(proId)
        .append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = query.list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        map.put("projectbbs", list);
      } 
      list = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.relProjectId =" + proId).list();
      if (!list.isEmpty())
        map.put("forumClassID", list.get(0)); 
      StringBuffer sqlBuffer1 = new StringBuffer("");
      sqlBuffer1.append(" and ( po.empId=").append(userId).append(" or po.reportReaderId like '%$").append(userId).append("$%' )");
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.previousReport,po.reportTime,po.reportEmpName,po.reportReaderId,po.relprojectId,po.reportName");
      sqlBuffer.append(" from com.js.oa.scheme.workreport.po.WorkReportPO po");
      sqlBuffer.append(" where po.relprojectId=").append(proId).append(sqlBuffer1.toString());
      sqlBuffer.append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projectlog", list); 
      sqlBuffer = new StringBuffer();
      WorkLogBD worklogBD = new WorkLogBD();
      List<Object[]> listDown = worklogBD.getDownEmployeeList(userId
          .toString());
      StringBuffer downEmp = new StringBuffer();
      for (int i = 0; i < listDown.size(); i++) {
        Object[] obj = listDown.get(i);
        downEmp.append(obj[0].toString()).append(",");
      } 
      sqlBuffer.append("select po.logId,po.logContent,po.empName,po.logDate");
      sqlBuffer.append(" from com.js.oa.scheme.worklog.po.WorkLogPO po");
      sqlBuffer.append(" where po.relProject=").append(proId).append(" and (po.createdEmp=" + userId);
      if (downEmp != null && downEmp.toString().length() > 2)
        sqlBuffer.append(" or po.createdEmp in (").append(downEmp.toString().substring(0, downEmp.toString().length() - 1)).append(")"); 
      sqlBuffer.append(")");
      sqlBuffer.append(" order by po.logDate desc,po.startPeriod desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("workLog", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.taskTitle,po.taskBeginTime,po.taskPrincipal,po.taskPrincipalName,po.isArranged")
        .append(" from com.js.oa.scheme.taskcenter.po.TaskPO po")
        .append(" where po.relProjectId=").append(proId)
        .append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projecttask", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.content,po.sendTime,po.empName")
        .append(" from com.js.oa.relproject.po.ProNotePO po")
        .append(" where po.projectId=").append(proId)
        .append(" order by po.id desc");
      query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      list = this.session.createQuery(sqlBuffer.toString()).list();
      if (list != null && list.size() > 0)
        map.put("projectnote", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.actorId,po.actorName")
        .append(" from com.js.oa.relproject.po.RelProActorPO po")
        .append(" where po.actorRole=10 and po.project.id=").append(proId)
        .append(" order by po.id ");
      query = this.session.createQuery(sqlBuffer.toString());
      list = query.list();
      if (list != null && list.size() > 0)
        map.put("promanager", list); 
      sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.channelId")
        .append(" from com.js.oa.info.channelmanager.po.InformationChannelPO po")
        .append(" where po.relProjectId=").append(proId);
      String channelId = this.session.createQuery(sqlBuffer.toString()).iterate().next().toString();
      map.put("proChannelId", channelId);
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return map;
  }
  
  private String getFilerRangeName(String rangeName) {
    StringBuffer rangeNameTemp = new StringBuffer("");
    if (rangeName != null && !rangeName.equals("")) {
      String[] readerArray = rangeName.split(",");
      for (int i = 0; i < readerArray.length; i++) {
        if (rangeNameTemp.indexOf(readerArray[i]) == -1)
          rangeNameTemp.append(String.valueOf(readerArray[i]) + ","); 
      } 
    } 
    return rangeNameTemp.toString();
  }
  
  private String getFilerRangeEmpId(String rangeEmpId) {
    StringBuffer rangeEmpTemp = new StringBuffer("");
    if (rangeEmpId != null && !rangeEmpId.equals("") && rangeEmpId.indexOf("$") >= 0) {
      String[] readerArray = rangeEmpId.substring(rangeEmpId.indexOf("$") + 1, rangeEmpId.lastIndexOf("$")).split("\\$\\$");
      for (int i = 0; i < readerArray.length; i++) {
        if (rangeEmpTemp.indexOf(readerArray[i]) == -1)
          rangeEmpTemp.append("$" + readerArray[i] + "$"); 
      } 
    } 
    return rangeEmpTemp.toString();
  }
  
  private String getFilerRangeOrgId(String rangeOrgId) {
    StringBuffer rangeOrgTemp = new StringBuffer("");
    if (rangeOrgId != null && !rangeOrgId.equals("") && rangeOrgId.indexOf("*") >= 0) {
      String[] readerArray = rangeOrgId.substring(rangeOrgId.indexOf("*") + 1, rangeOrgId.lastIndexOf("*")).split("\\*\\*");
      for (int i = 0; i < readerArray.length; i++) {
        if (rangeOrgTemp.indexOf(readerArray[i]) == -1)
          rangeOrgTemp.append("*" + readerArray[i] + "*"); 
      } 
    } 
    return rangeOrgTemp.toString();
  }
  
  private String getFilerRangeGroupId(String rangeGroupId) {
    StringBuffer rangeGroupTemp = new StringBuffer("");
    if (rangeGroupId != null && !rangeGroupId.equals("") && rangeGroupId.indexOf("@") >= 0) {
      String[] readerArray = rangeGroupId.substring(rangeGroupId.indexOf("@") + 1, rangeGroupId.lastIndexOf("@")).split("\\@\\@");
      for (int i = 0; i < readerArray.length; i++) {
        if (rangeGroupTemp.indexOf(readerArray[i]) == -1)
          rangeGroupTemp.append("@" + readerArray[i] + "@"); 
      } 
    } 
    return rangeGroupTemp.toString();
  }
  
  public String getUserRoleInProByUserId(String userId, String proId) {
    String hqlString = "select po from com.js.oa.relproject.po.RelProActorPO po where po.actorId=" + userId + " and po.project=" + proId + " order by po.actorRole ASC";
    String role = "";
    try {
      begin();
      Query query = this.session.createQuery(hqlString);
      List<RelProActorPO> list = query.list();
      if (list.size() > 0) {
        RelProActorPO proActorPO = list.get(0);
        role = proActorPO.getActorRole().toString();
      } 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return role;
  }
  
  public String getRelProjectIdByChannelId(String channelId) {
    String hqlString = "select po from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=" + channelId;
    String relProId = "";
    try {
      begin();
      Query query = this.session.createQuery(hqlString);
      List<InformationChannelPO> list = query.list();
      if (list.size() > 0) {
        InformationChannelPO infoPo = list.get(0);
        relProId = infoPo.getRelProjectId().toString();
      } 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return relProId;
  }
  
  public List getProNotes(String proId) {
    List returnList = new ArrayList();
    try {
      Long pId = Long.valueOf(proId);
      StringBuffer sqlBuffer = new StringBuffer();
      sqlBuffer.append("select po.id,po.content,po.sendTime,po.empName")
        .append(" from com.js.oa.relproject.po.ProNotePO po")
        .append(" where po.projectId=").append(pId.toString())
        .append(" order by po.id desc");
      begin();
      Query query = this.session.createQuery(sqlBuffer.toString());
      query.setFirstResult(0);
      query.setMaxResults(7);
      returnList = query.list();
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return returnList;
  }
  
  public void addItemFromWorkflow(String projectId, String itemTitle, String startTime, String endTime, int remind) {
    try {
      begin();
      RelProItemPO po = new RelProItemPO();
      po.setProjectId(Long.valueOf(projectId));
      po.setTitle(itemTitle);
      po.setStartTime(new Date(startTime));
      po.setEndTime(new Date(endTime));
      po.setItemRemind(Integer.valueOf(remind));
      Long itemId = (Long)this.session.save(po);
      this.session.flush();
      String url = "/jsoa/RelProjectAction.do?flag=load&id=" + projectId + "&openType=message";
      Date now = new Date();
      Calendar itemStartCalendar = Calendar.getInstance();
      Calendar itemEndCalendar = Calendar.getInstance();
      itemStartCalendar.setTime(new Date(startTime));
      itemStartCalendar.add(5, -remind);
      itemStartCalendar.set(11, 0);
      itemStartCalendar.set(12, 1);
      itemEndCalendar.setTime(new Date(endTime));
      itemEndCalendar.add(5, -remind);
      itemEndCalendar.set(11, 0);
      itemEndCalendar.set(12, 1);
      if (itemEndCalendar.getTime().getTime() >= now.getTime() - 86400000L)
        if (itemStartCalendar.getTime().getTime() < now.getTime() - 86400000L) {
          String projectName = this.session.createQuery("select po.title from com.js.oa.relproject.po.RelProjectPO po where po.id=" + projectId).iterate().next().toString();
          String itemEndTitle = String.valueOf(projectName) + "[" + itemTitle + "]阶段将在" + endTime + "结束";
          List<E> empList = this.session.createQuery("select po.actorId from com.js.oa.relproject.po.RelProActorPO po join po.project pro where pro.id=" + projectId).list();
          if (!empList.isEmpty() && empList.size() > 0)
            for (int j = 0; j < empList.size(); j++)
              RemindUtil.sendMessageToUsers(itemEndTitle, url, empList.get(j).toString(), "ProjectItem", itemEndCalendar.getTime(), new Date("2050/1/1"), "管理员", itemId);  
        } else {
          String projectName = this.session.createQuery("select po.title from com.js.oa.relproject.po.RelProjectPO po where po.id=" + projectId).iterate().next().toString();
          String itemStartTitle = String.valueOf(projectName) + "将要进行[" + itemTitle + "]阶段";
          String itemEndTitle = String.valueOf(projectName) + "[" + itemTitle + "]阶段将在" + endTime + "结束";
          List<E> empList = this.session.createQuery("select po.actorId from com.js.oa.relproject.po.RelProActorPO po join po.project pro where pro.id=" + projectId).list();
          if (!empList.isEmpty() && empList.size() > 0)
            for (int j = 0; j < empList.size(); j++) {
              RemindUtil.sendMessageToUsers(itemStartTitle, url, empList.get(j).toString(), "ProjectItem", itemStartCalendar.getTime(), new Date("2050/1/1"), "管理员", itemId);
              RemindUtil.sendMessageToUsers(itemEndTitle, url, empList.get(j).toString(), "ProjectItem", itemEndCalendar.getTime(), new Date("2050/1/1"), "管理员", itemId);
            }  
        }  
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  private List getIdStr(String idStr, String nameStr) {
    String ids = "";
    String idstemp = "", nametemp = "";
    String[] idArry = (String[])null;
    String[] nameArry = (String[])null;
    List<String> list = new ArrayList();
    ids = idStr.replace("$$", ",");
    ids = ids.replace("$", "");
    idArry = ids.split(",");
    nameArry = nameStr.split(",");
    for (int i = 0; i < idArry.length; i++) {
      for (int j = i + 1; j < idArry.length; j++) {
        if (idArry[i].equals(idArry[j])) {
          idArry[j] = "#";
          nameArry[j] = "@";
        } 
      } 
    } 
    for (int k = 0; k < idArry.length; k++) {
      if (!"#".equals(idArry[k]))
        idstemp = String.valueOf(idstemp) + idArry[k] + ","; 
      if (!"@".equals(nameArry[k]))
        nametemp = String.valueOf(nametemp) + nameArry[k] + ","; 
    } 
    idstemp = idstemp.substring(0, idstemp.length() - 1).replace(",", "$$");
    list.add("$" + idstemp + "$");
    list.add(nametemp);
    return list;
  }
}
