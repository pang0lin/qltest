package com.js.wap.action;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.po.ForumPO;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.bbs.service.ForumClassBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.messages.Remind;
import com.js.util.config.SystemCommon;
import com.js.wap.bean.WapBbsBean;
import com.js.wap.service.WapBbsBD;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WapBbsAction extends DispatchAction {
  public ActionForward bbsList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    String classid = getClassId(request);
    if (classid == null || "".equals(classid))
      classid = "''"; 
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    String hql = "select po from com.js.oa.bbs.po.ForumPO po where po.forumClass.id in (" + classid + ") " + 
      "and po.forumTopicId=0 and po.domainId=" + domainId + " order by po.forumTopOrder desc,po.newretime desc";
    Map map = (new WapBbsBD()).getBbsMap(hql, beginIndex, limit);
    request.setAttribute("bbslist", map.get("list"));
    request.setAttribute("size", map.get("size"));
    return mapping.findForward("bbsList_3g");
  }
  
  public ActionForward bbsInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    WapBbsBD wapBbsBD = new WapBbsBD();
    String order = "po.id,po.forumIssueTime";
    if ("1".equals(SystemCommon.getForumOrder()))
      order = "po.forumTopicId,po.id desc,po.forumIssueTime desc"; 
    String beginIndex = request.getParameter("beginIndex").toString();
    String id = request.getParameter("forumId").toString();
    if (request.getParameter("type") != null) {
      String hql = "select po from com.js.oa.bbs.po.ForumPO po where po.id=" + id;
      request.setAttribute("forum", (new WapBbsBD()).getForumPO(hql));
      request.setAttribute("type", "2");
      wapBbsBD.updateReTime(id);
    } else {
      HttpSession session = request.getSession(true);
      String userId = session.getAttribute("userId").toString();
      int begin = Integer.parseInt((request.getParameter("begin") == null) ? "0" : request.getParameter("begin"));
      int limit = WapUtil.LIMIT;
      String hql = "select po from com.js.oa.bbs.po.ForumPO po where po.id=" + id + " or po.forumTopicId=" + id + 
        " and po.examinNum <>1 order by " + order;
      Map map = wapBbsBD.getForumMap(hql, begin, limit);
      List<ForumPO> list = (List<ForumPO>)map.get("list");
      request.setAttribute("list", list);
      request.setAttribute("size", map.get("size"));
      request.setAttribute("type", "bbs");
      request.setAttribute("id", id);
      request.setAttribute("classId", map.get("classId"));
      request.setAttribute("title", (new WapBbsBean()).getTitle(id));
      request.setAttribute("begin", Integer.valueOf(begin));
      ForumBD bd = new ForumBD();
      List<ForumClassPO> ret1 = bd.getForumClassPODetails(map.get("classId").toString());
      ForumClassPO poclassmm = ret1.get(0);
      String isClassOwner = "0";
      String isCheckExam = "0";
      if ("1".equals(poclassmm.getCheckExamin()))
        isCheckExam = "1"; 
      request.setAttribute("isCheckExam", isCheckExam);
      String classOwnerIds = poclassmm.getClassOwnerIds();
      String userid = "*" + userId + "*";
      if (classOwnerIds != null && ("**" + classOwnerIds).indexOf(userid) > 0)
        isClassOwner = "1"; 
      request.setAttribute("isClassOwner", isClassOwner);
    } 
    if (request.getParameter("kit") != null)
      (new WapBbsBD()).forumKit(id); 
    request.setAttribute("beginIndex", beginIndex);
    return mapping.findForward("bbsInfo_3g");
  }
  
  public ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    WapBbsBD wapBbsBD = new WapBbsBD();
    HttpSession session = request.getSession(true);
    String classId = request.getParameter("classId");
    String hql = "select po from com.js.oa.bbs.po.ForumClassPO po where po.id=" + classId;
    String title = URLDecoder.decode(request.getParameter("title"), "utf-8");
    String content = URLDecoder.decode(request.getParameter("content"), "utf-8");
    String topicId = request.getParameter("forumId");
    ForumPO forumPO = new ForumPO();
    forumPO.setForumClass(wapBbsBD.getClassPO(hql));
    forumPO.setForumTitle(title);
    forumPO.setForumContent(content);
    forumPO.setForumTopicId(Long.valueOf(topicId).longValue());
    forumPO.setForumType(Byte.valueOf("0").byteValue());
    forumPO.setForumAttachName("");
    forumPO.setForumAttachSave("");
    forumPO.setForumAuthor(session.getAttribute("userName").toString());
    forumPO.setForumAuthorId(Long.valueOf(session.getAttribute("userId").toString()).longValue());
    forumPO.setForumAuthorIp(request.getRemoteAddr());
    forumPO.setForumAuthorOrg(session.getAttribute("orgName").toString());
    forumPO.setForumIssueTime(new Date());
    forumPO.setForumModifyTime(new Date());
    forumPO.setNewretime(new Date());
    forumPO.setDomainId(Long.valueOf(session.getAttribute("domainId").toString()));
    if (request.getParameter("types") != null) {
      forumPO.setExaminNum(1);
    } else {
      forumPO.setExaminNum(3);
    } 
    String forumid = wapBbsBD.save(forumPO);
    int num = wapBbsBD.updateReTime(topicId);
    message(classId, request, forumPO, forumid);
    return bbsInfo(mapping, form, request, response);
  }
  
  public String getClassId(HttpServletRequest request) {
    String forumClassids = "";
    String moveRight = "0";
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = session.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    ForumClassBD cbd = new ForumClassBD();
    boolean scopeSQL = mbd.hasRight(curUserId.toString(), "06*02*02");
    if (scopeSQL)
      moveRight = "1"; 
    request.setAttribute("moveRight", moveRight);
    String whereScopePara = "";
    String whereScopeManagerString = "";
    if (!"1".equals(moveRight)) {
      whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), 
          curOrgId.toString(), 
          orgIdString, 
          "po.classUserId", 
          "po.classUserOrg", 
          "po.classUserGroup");
      whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
    } else {
      whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), 
          curOrgId.toString(), 
          orgIdString, 
          "po.classUserId", 
          "po.classUserOrg", 
          "po.classUserGroup");
      whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
      whereScopeManagerString = mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "讨论", "维护", "po.createdOrg", "po.createdEmp");
      whereScopeManagerString = String.valueOf(whereScopeManagerString) + cbd.getClassIdString(curUserId.toString(), "po.id");
      if (whereScopeManagerString.trim().length() > 0)
        whereScopePara = String.valueOf(whereScopePara) + " or " + whereScopeManagerString; 
    } 
    List<Object[]> list = cbd.listMenu(whereScopePara, curUserId, domainId);
    request.setAttribute("list", list);
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      if (i < list.size() - 1) {
        forumClassids = String.valueOf(forumClassids) + obj[0].toString() + ",";
      } else {
        forumClassids = String.valueOf(forumClassids) + obj[0].toString();
      } 
    } 
    return forumClassids;
  }
  
  public void message(String classId, HttpServletRequest request, ForumPO forumPO, String forumid) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    ForumClassBD fbd = new ForumClassBD();
    ForumClassPO fpo = fbd.load(classId);
    ForumBD bd = new ForumBD();
    if (!"1".equals(fpo.getCheckExamin())) {
      RemindBD remindBD = new RemindBD();
      List<Remind> lists = remindBD.searchReminduser(forumPO.getForumTopicId(), "Forum");
      String userIds = "";
      if (!lists.isEmpty()) {
        for (int i = 0; i < lists.size(); i++) {
          Remind forumRemind = lists.get(i);
          if (!userId.toString().equals((new Long(forumRemind.getEmp_id())).toString()))
            userIds = String.valueOf(userIds) + forumRemind.getEmp_id() + ","; 
        } 
        String url = "/jsoa/ForumAction.do?action=followList&classId=" + classId + "&mainForumId=" + forumPO.getForumTopicId();
        String titles = "您关注的帖子有新的回复";
        String title_author = "您跟踪的帖子有新的回复";
        Calendar tmp = Calendar.getInstance();
        tmp.set(2050, 12, 12);
        Long authorId = bd.getAuthorIdByForumId((new StringBuilder(String.valueOf(forumPO.getForumTopicId()))).toString());
        if (!"".equals(userIds)) {
          if (!authorId.toString().equals("0") && !authorId.toString().equals(userId.toString())) {
            String authorId_ = authorId + ",";
            RemindUtil.sendMessageToUsers(title_author, url, authorId_, "Forum", new Date(), tmp.getTime());
            userIds = userIds.replaceAll(authorId_, "");
          } 
          if (!userIds.equals("")) {
            userIds = userIds.substring(0, userIds.length() - 1);
            RemindUtil.sendMessageToUsers(titles, url, userIds, "Forum", new Date(), tmp.getTime());
          } 
        } 
      } 
    } 
    if (fpo != null && 
      "1".equals(fpo.getCheckExamin())) {
      String classOwnerIds = fpo.getClassOwnerIds();
      if (!"".equals(classOwnerIds)) {
        String userid = "*" + userId + "*";
        if (("**" + classOwnerIds).indexOf(userid) > 0) {
          request.setAttribute("checkExamin", "false");
          request.setAttribute("isOwner", "true");
        } else {
          classOwnerIds = classOwnerIds.substring(1, classOwnerIds.length() - 1);
          String[] classOwnerIdss = classOwnerIds.split("\\*\\*");
          if (classOwnerIdss.length > 0)
            for (int i = 0; i < classOwnerIdss.length; i++) {
              Calendar tmp = Calendar.getInstance();
              tmp.set(2050, 12, 12);
              String title1 = "您有新的帖子需要审查";
              String url = "/jsoa/ForumAction.do?action=shenchalist&classId=" + fpo.getId();
              RemindUtil.sendMessageToUsers1(title1, url, classOwnerIdss[i], "ShenChaNo", new Date(), tmp.getTime(), forumPO.getForumAuthor(), Long.valueOf(Long.parseLong(forumid)));
            }  
        } 
      } 
    } 
  }
}
