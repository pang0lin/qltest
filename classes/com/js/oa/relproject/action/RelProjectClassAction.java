package com.js.oa.relproject.action;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.bbs.service.ForumClassBD;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.scheme.event.service.EventBD;
import com.js.oa.scheme.workreport.service.WorkReportProductBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.Accessory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RelProjectClassAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    Long curUserId = new Long(request.getSession(true).getAttribute("userId").toString());
    String promanagerIds = request.getParameter("promanagerIds");
    request.setAttribute("promanagerIds", promanagerIds);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String operate = request.getParameter("flag");
    ForumBD bd = new ForumBD();
    ManagerService mbd = new ManagerService();
    Long curOrgId = new Long(request.getSession(true).getAttribute("orgId").toString());
    String userIdStr = request.getSession(true).getAttribute("userId").toString();
    String tag = "";
    String proId = request.getParameter("id");
    request.setAttribute("projectId_", proId);
    String type = request.getParameter("type");
    RelProjectBean bean = new RelProjectBean();
    List list = bean.getActorByProId(Long.parseLong(proId));
    String userRoll = "0";
    if (list.contains(Long.valueOf(Long.parseLong(userId))))
      userRoll = "1"; 
    request.setAttribute("userRoll", userRoll);
    if ("Forum".equals(operate)) {
      String para = "";
      String from = "";
      String where = "";
      if ("delForum".equals(type)) {
        String forumId = request.getParameter("forumId");
        String classId = request.getParameter("classId");
        String retString = bd.delForum(forumId, classId);
        String path = request.getRealPath("/");
        path = String.valueOf(path) + "/upload/forum";
        Accessory.delAccessory(path, retString);
        type = "list";
      } 
      if ("soulForum".equals(type)) {
        String forumId = request.getParameter("forumId");
        String status = request.getParameter("status");
        String message = bd.soulForum(forumId, status);
        request.setAttribute("message", message);
        type = "list";
      } 
      if ("setAuth".equals(type)) {
        String type1 = request.getParameter("type1");
        String isnot = request.getParameter("isnot");
        String forumId = request.getParameter("forumId");
        String message = bd.setAuth(type1, isnot, forumId);
        request.setAttribute("message", message);
        type = "list";
      } 
      if ("setTop".equals(type)) {
        String setTopType = (request.getParameter("setTopType") == null) ? "1" : request.getParameter("setTopType");
        String forumId = request.getParameter("forumId");
        String classId = request.getParameter("classId");
        String message = bd.setTop(setTopType, forumId, classId);
        request.setAttribute("message", message);
        type = "list";
      } 
      if ("list".equals(type)) {
        tag = "forum_list";
        Map map = bean.getProjectInfo(proId);
        request.setAttribute("map", map);
        request.setAttribute("proId", proId);
        int offset = 0;
        if (request.getParameter("pager.offset") != null)
          offset = Integer.parseInt(request
              .getParameter("pager.offset")); 
        ForumClassPO poclassmm = bean.getForumClass(proId);
        request.setAttribute("poclassmm", poclassmm);
        String classId = (new Long(poclassmm.getId())).toString();
        request.setAttribute("classId", classId);
        ForumClassBD cbd = new ForumClassBD();
        String whereScopePara = "";
        whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), 
            curOrgId.toString(), orgIdString, "po.classUserId", 
            "po.classUserOrg", "po.classUserGroup");
        whereScopePara = " 1<>1 " + 
          cbd.getClassIdString(curUserId.toString(), 
            "po.forumClass.id", whereScopePara);
        request.setAttribute("examinOrNot", "yes");
        Vector<String> vec = bd.list(classId, null, null, new Integer(offset), 
            null, null, null, null, null, whereScopePara, 
            
            null, null, null, null, domainId);
        Vector<String> pageVec = getPage(curUserId.toString(), classId, null, 
            null, new Integer(offset), null, null, null, null, 
            null, whereScopePara, 
            
            null, null, null, null, null, domainId, "y", userIdStr);
        String recordCount = pageVec.get(0);
        String pageSize = vec.get(1);
        request.setAttribute("classOwnerName", vec.get(3));
        request.setAttribute("classDate", vec.get(4));
        request.setAttribute("classHasJunior", vec.get(5));
        request.setAttribute("classRange", vec.get(8));
        String addBulletinRight = "0";
        String ownerIds = vec.get(6);
        if (ownerIds.indexOf("*" + curUserId + "*") != -1)
          addBulletinRight = "1"; 
        request.setAttribute("addBulletinRight", addBulletinRight);
        request.setAttribute("navigationVec", vec.get(7));
        request.setAttribute("recordCount", recordCount);
        request.setAttribute("maxPageItems", pageSize);
        request
          .setAttribute(
            "pageParameters", 
            "action,sortFirst,kitDesc,replyDesc,btimeDesc,moveRight,classId,queryText,queryItem,queryClass,queryMan,startDate,endDate,queryForumType,queryTitle,examinYesOrNot");
        cbd = new ForumClassBD();
        String whereScopePara1 = "";
        whereScopePara1 = mbd.getScopeFinalWhere(curUserId.toString(), 
            curOrgId.toString(), orgIdString, "po.classUserId", 
            "po.classUserOrg", "po.classUserGroup");
        whereScopePara1 = " po.classOwnerId = " + curUserId + " or " + 
          whereScopePara1;
        request.setAttribute("classList", cbd.listMenu(whereScopePara1, 
              curUserId.toString(), domainId));
        request.setAttribute("banPrint", poclassmm.getBanPrint());
        String isClassOwner = "0";
        String classOwnerIds = poclassmm.getClassOwnerIds();
        if (classOwnerIds == null)
          classOwnerIds = ""; 
        String userid = "*" + curUserId + "*";
        if (classOwnerIds != null && ("**" + classOwnerIds).indexOf(userid) > 0)
          isClassOwner = "1"; 
        String[] classOwnerIdss = classOwnerIds.split("\\*\\*");
        classOwnerIds = "";
        for (int i = 0; i < classOwnerIdss.length; i++) {
          String tmp = classOwnerIdss[i].replaceAll("\\*", "");
          classOwnerIds = String.valueOf(classOwnerIds) + tmp + ",";
        } 
        classOwnerIds = classOwnerIds.substring(0, classOwnerIds
            .length() - 1);
        ForumClassBD Cbd = new ForumClassBD();
        String empSimpleNames = Cbd.selectSimpleName(classOwnerIds, 
            domainId);
        request.setAttribute("empSimpleNames", empSimpleNames);
        request.setAttribute("isClassOwner", isClassOwner);
        String ClassEmail = poclassmm.getClassEmail();
        request.setAttribute("classEmail", ClassEmail);
        para = "select po.id,po.forumTitle,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.id,po.forumClass.classHasJunior,po.forumClass.classOwnerIds,po.forumClass.classParentName,po.anonymous,po.forumClass.classOwnerIds,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint,po.forumModifyTime,po.forumTopOrder";
        from = "from com.js.oa.bbs.po.ForumPO po join po.forumClass poo";
        where = " where po.forumTopicId=0 and poo.relProjectId=" + 
          proId + "  and (po.examinNum=2 or po.examinNum=3 or po.examinNum is null) order by po.forumTopOrder desc,po.forumIsSoul desc, po.forumType desc , po.forumModifyTime desc , po.id desc ";
        list(request, para, from, where);
      } 
    } 
    if ("Event".equals(operate)) {
      String para = "";
      String from = "";
      String where = "";
      if ("delEvent".equals(type)) {
        EventBD eventBD = new EventBD();
        Long eventId = Long.valueOf(request.getParameter("eventId"));
        boolean result = eventBD.deleteEvent(Long.valueOf(Long.parseLong(userId)), eventId);
        type = "list";
      } 
      if ("list".equals(type)) {
        tag = "event_list";
        Map map = bean.getProjectInfo(proId);
        request.setAttribute("map", map);
        request.setAttribute("proId", proId);
        para = "select ev.eventId,ev.eventTitle,ev.eventBeginDate,ev.eventBeginTime,ev.eventEndDate,ev.eventEndTime,ev.eventEmpId,ev.eventEmpName,ev.onTimeMode,ev.echoBeginTime,ev.echoEndTime,ev.eventDate";
        from = "from com.js.oa.scheme.event.po.EventPO ev";
        where = " where ev.relProjectId=" + proId + " order by ev.eventId desc";
        list(request, para, from, where);
      } 
    } 
    if ("log".equals(operate)) {
      String para = "";
      String from = "";
      String where = "";
      if ("list".equals(type)) {
        tag = "log_list";
        Map map = bean.getProjectInfo(proId);
        request.setAttribute("map", map);
        request.setAttribute("proId", proId);
        boolean fz = false;
        fz = bean.getfuzByProId(Long.valueOf(proId).longValue(), Long.valueOf(userId).longValue());
        StringBuffer sqlBuffer1 = new StringBuffer("");
        if (!fz)
          sqlBuffer1.append(" and ( po.empId=").append(userId).append(" or po.reportReaderId like '%$").append(userId).append("$%' )"); 
        para = "po.id,po.reportType,po.reportTime,po.reportReader,po.reportReader,po.templateId,po.hadRead,po.sendType,po.empId,po.reportName";
        from = "com.js.oa.scheme.workreport.po.WorkReportPO po";
        where = " where (po.reportReaderId like '%$" + userIdStr + "$%' or po.empId=" + userIdStr + ") and po.relprojectId=" + proId + sqlBuffer1.toString();
        if (request.getParameter("ribao") != null && !"".equals(request.getParameter("ribao")))
          where = String.valueOf(where) + " and po.reportType=0"; 
        where = String.valueOf(where) + " order by po.id desc";
        list(request, para, from, where);
      } 
      if ("del".equals(type)) {
        WorkReportProductBD bd1 = new WorkReportProductBD();
        String retString = bd1.delBatch(request.getParameter("ids"));
        String path = request.getRealPath("/");
        path = String.valueOf(path) + "/upload/workreport";
        Accessory.delAccessory(path, retString);
        String url = "/jsoa/RelProjectClassAction.do?flag=log&id=" + proId + "&type=list";
        request.setAttribute("url", url);
        return mapping.findForward("bbs_forward");
      } 
    } 
    if ("Meeting".equals(operate)) {
      String para = "";
      String from = "";
      String where = "";
      if ("list".equals(type)) {
        tag = "meeting_list";
        Map map = bean.getProjectInfo(proId);
        request.setAttribute("map", map);
        request.setAttribute("proId", proId);
        para = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum";
        from = " from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo";
        where = " where boardRoomApplyPO.status=0 and boardRoomApplyPO.relProjectId=" + proId + " order by poo.meetingDate desc";
        list(request, para, from, where);
      } 
    } 
    if ("task".equals(operate)) {
      String para = "";
      String from = "";
      String where = "";
      if ("list".equals(type)) {
        tag = "task_list";
        Map map = bean.getProjectInfo(proId);
        request.setAttribute("map", map);
        request.setAttribute("proId", proId);
        para = "select po.id,po.taskTitle,po.taskBeginTime,po.taskPrincipal,po.taskPrincipalName,po.isArranged,po.taskFinishRate";
        from = " from com.js.oa.scheme.taskcenter.po.TaskPO po";
        where = " where po.relProjectId=" + proId + " order by po.id desc";
        list(request, para, from, where);
      } 
    } 
    if ("Note".equals(operate)) {
      String para = "";
      String from = "";
      String where = "";
      if ("list".equals(type)) {
        Map map = bean.getProjectInfo(proId);
        request.setAttribute("map", map);
        request.setAttribute("proId", proId);
        para = "select po.id,po.content,po.sendTime,po.empName,po.empId";
        from = "from com.js.oa.relproject.po.ProNotePO po";
        where = " where po.projectId=" + proId + " order by po.id desc";
        list(request, para, from, where);
        tag = "note_list";
      } 
    } 
    return mapping.findForward(tag);
  }
  
  public void list(HttpServletRequest request, String viewSql, String fromSql, String whereSql) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    String pagP = "";
    if (request.getParameter("ribao") != null)
      pagP = ",reportType,ribao"; 
    request.setAttribute("pageParameters", "flag,id,type" + pagP);
  }
  
  private Vector getPage(String curUserId, String classId, String startDate, String endDate, Integer offset, String queryText, String queryItem, String queryClass, String queryMan, String queryForumType, String wherePara, String replyDesc, String kitDesc, String btimeDesc, String sortFirst, String queryTitle, String domainId, String examinYesOrNot, String userIdStr) {
    int pageSize = 15;
    int currentPage = offset.intValue() / pageSize + 1;
    String orderby = "";
    String orderbyreply = "";
    String orderbykit = "";
    String orderbybtime = "";
    ForumBD forumBD = new ForumBD();
    if (!"none".equals(replyDesc) && replyDesc != null)
      if ("1".equals(replyDesc)) {
        orderbyreply = " po.forumRevertNum ,  ";
      } else {
        orderbyreply = " po.forumRevertNum desc ,  ";
      }  
    if (!"none".equals(kitDesc) && kitDesc != null)
      if ("1".equals(kitDesc)) {
        orderbykit = " po.forumKits ,  ";
      } else {
        orderbykit = " po.forumKits desc ,  ";
      }  
    if (!"none".equals(btimeDesc) && btimeDesc != null)
      if ("1".equals(btimeDesc)) {
        orderbybtime = " po.forumIssueTime ,  ";
      } else {
        orderbybtime = " po.forumIssueTime desc ,  ";
      }  
    if (!"none".equals(sortFirst) && sortFirst != null)
      if ("reply".equals(sortFirst)) {
        orderby = orderbyreply;
      } else if ("kit".equals(sortFirst)) {
        orderby = orderbykit;
      } else {
        orderby = orderbybtime;
      }  
    orderby = String.valueOf(orderby) + "po.forumTopOrder desc,po.forumIsSoul desc, ";
    StringBuffer where = new StringBuffer(" where po.forumTopicId=0");
    if ("1".equals(queryForumType)) {
      where.append(" and po.forumType=0").append(" and po.forumIsSoul=1");
    } else if (!"-1".equals(queryForumType) && queryForumType != null) {
      where.append(" and po.forumType=").append(queryForumType).append(
          " and po.forumIsSoul=0");
    } 
    if (examinYesOrNot != null && examinYesOrNot.equals("n")) {
      where
        .append(" and ( (po.examinNum=1")
        .append(
          " and po.forumClass.classOwnerIds like '%*" + 
          userIdStr + "*%' ) ")
        .append(
          " or ( po.examinNum<>1  and po.forumClass.classOwnerIds like '%*" + 
          
          userIdStr + 
          "*%'" + 
          " and ( select count(*) from com.js.oa.bbs.po.ForumPO xpo where xpo.examinNum=1 and xpo.forumTopicId=po.id )>0)) ");
    } else {
      where.append(" and (po.examinNum=2").append(
          " or po.examinNum=3 or po.examinNum is null)");
    } 
    if (queryClass != null && !"none".equals(queryClass)) {
      where.append(" and (").append(forumBD.classIdQuery(queryClass))
        .append(")");
    } else if (!"none".equals(queryClass)) {
      where.append(" and po.forumClass.id = ").append(classId);
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where.append(" and ( po.forumIssueTime between '").append(
            startDate).append(" 00:00:00").append("' and '")
          .append(endDate).append(" 23:59:59").append("'")
          .append(" or  po.forumIssueTime between '").append(
            endDate).append(" 00:00:00").append("' and '")
          .append(startDate).append(" 23:59:59").append("' )");
      } else {
        where
          .append(
            " and ( po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(startDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(endDate)
          .append(" 23:59:59")
          .append("','L')")
          .append(
            " or  po.forumIssueTime between JSDB.FN_STRTODATE('")
          .append(endDate).append(" 00:00:00").append(
            "','L') and JSDB.FN_STRTODATE('").append(
            startDate).append(" 23:59:59").append(
            "','L') )");
      } 
    } 
    if (queryText != null && !"".equals(queryText))
      where.append(" and (").append(" dbms_lob.instr(po.forumContent,'")
        .append(queryText).append("',1,1)>0 ").append(
          " or po.forumTitle like'%").append(queryText)
        .append("%') "); 
    if (queryTitle != null && !"".equals(queryTitle))
      where.append(" and (po.forumTitle like'%").append(queryTitle)
        .append("%') "); 
    if (queryMan != null && !"".equals(queryMan) && !"匿名".equals(queryMan))
      where.append(" and po.forumAuthor like '%" + queryMan + 
          "%' and po.anonymous <>1 "); 
    if (queryMan != null && !"".equals(queryMan) && "匿名".equals(queryMan))
      where.append(" and (po.forumAuthor like '%" + queryMan + 
          "%' or po.anonymous=1) "); 
    where.append(" and (").append(wherePara).append(")");
    String period = "";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    period = sdf.format(new Date());
    String _period = " and ((poo.createdEmp=" + 
      curUserId + 
      " or (poo.classOwnerId=" + 
      curUserId + 
      " or poo.classOwnerIds like '%*" + 
      curUserId + 
      "*%'))" + 
      " or (poo.fullDay=1 or (poo.fullDay!=1 and poo.startPeriod <= '" + 
      period + "' and '" + period + "' <= poo.endPeriod))) ";
    where.append(_period);
    Page page = new Page(
        " po.id,po.forumTitle,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.id,po.forumClass.classHasJunior,po.forumClass.classOwnerIds,po.forumClass.classParentName,po.anonymous,po.forumClass.classOwnerIds,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint,po.forumModifyTime,po.forumTopOrder", 



        
        " com.js.oa.bbs.po.ForumPO po join po.forumClass poo ", 
        where + 
        " and po.domainId=" + 
        domainId + 
        " order by " + 
        orderby + 
        "  po.forumType desc , po.forumModifyTime desc , po.id desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    Vector<String> vector = new Vector();
    vector.add((new StringBuilder(String.valueOf(page.getRecordCount()))).toString());
    vector.add(list);
    return vector;
  }
}
