package com.js.oa.weixin.ltgg;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.po.ForumPO;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.bbs.service.ForumClassBD;
import com.js.oa.search.client.SearchService;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.messages.Remind;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinLtggAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String method = request.getParameter("action");
    String operate = request.getParameter("operate");
    request.setAttribute("operate", operate);
    if ("bbsList".equals(method)) {
      bbsMenuList(session, request);
      request.setAttribute("action", "bbsList");
      return mapping.findForward("bbsList");
    } 
    if ("bbsInfo".equals(method)) {
      bbsInfo(request);
      return mapping.findForward("bbsInfo");
    } 
    if ("reply".equals(method)) {
      reply(request);
      return mapping.findForward("bbsInfo");
    } 
    if ("write".equals(method)) {
      bbsMenuList(session, request);
      return mapping.findForward("write");
    } 
    if ("seeList".equals(method)) {
      seeList(session, request);
      request.setAttribute("action", "seeList");
      return mapping.findForward("bkList");
    } 
    if ("bbsListByClassId".equals(method)) {
      bbsList(request, session);
      request.setAttribute("action", "bbsListByClassId");
      return mapping.findForward("bkList");
    } 
    if ("save".equals(method)) {
      save(session, request);
      bbsMenuList(session, request);
      return mapping.findForward("bbsList");
    } 
    if ("checkClass".equals(method)) {
      String classId = request.getParameter("classId");
      DbOpt db = new DbOpt();
      String[] list = db.executeQueryToStrArr1("select EstopAnonymity from oa_forumclass where class_id=" + classId);
      db.close();
      String estopAnonymity = "0";
      if (list != null && list.length > 0)
        estopAnonymity = (list[0] == null || "null".equalsIgnoreCase(list[0]) || "".equals(list[0])) ? "0" : list[0]; 
      response.getWriter().print(estopAnonymity);
      return null;
    } 
    return null;
  }
  
  private void seeList(HttpSession session, HttpServletRequest request) throws Exception {
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      keyword = URLDecoder.decode(keyword, "utf-8"); 
    Map map = (new WeiXinBD()).searchAttentionByUserid(session.getAttribute("userId").toString(), keyword, beginIndex);
    request.setAttribute("bbslist", map.get("list"));
    request.setAttribute("size", map.get("size"));
    request.setAttribute("keyword", keyword);
  }
  
  private void save(HttpSession session, HttpServletRequest request) throws Exception {
    ForumBD bd = new ForumBD();
    ForumPO po = new ForumPO();
    String userId = session.getAttribute("userId").toString();
    String ftitle = request.getParameter("title");
    if (ftitle != null && !"".equals(ftitle))
      ftitle = URLDecoder.decode(ftitle, "utf-8"); 
    String content = request.getParameter("content");
    if (content != null && !"".equals(content))
      content = URLDecoder.decode(content, "utf-8"); 
    String textType = request.getParameter("textType");
    String classId = (request.getParameter("classId") == null) ? "-1" : request.getParameter("classId");
    String niming = (request.getParameter("niming") == null) ? "0" : request.getParameter("niming");
    String genzong = (request.getParameter("genzong") == null) ? "0" : request.getParameter("genzong");
    String forumIsSoul = "0";
    String forumSign = "";
    if ("1".equals(textType))
      po.setForumContent(CharacterTool.escapeHTMLTags2(po.getForumContent())); 
    String contexeyinyong = request.getParameter("contexeyinyong");
    if (contexeyinyong != null && !"".equals(contexeyinyong) && !"null".equals(contexeyinyong)) {
      po.setForumContent(String.valueOf(contexeyinyong) + CharacterTool.escapeHTMLTags2(po.getForumContent()));
    } else {
      po.setForumContent(CharacterTool.escapeHTMLTags2(po.getForumContent()));
    } 
    ForumClassPO classPO = new ForumClassPO();
    classPO.setId(Long.parseLong(classId));
    po.setForumClass(classPO);
    po.setForumTopicId(0L);
    po.setForumTitle(ftitle);
    po.setForumIsSoul(Byte.parseByte(forumIsSoul));
    po.setForumType(Byte.parseByte("0"));
    po.setForumContent(content);
    po.setForumAttachName("");
    po.setForumAttachSave("");
    po.setForumAuthor(session.getAttribute("userName").toString());
    po.setForumAuthorId(Long.parseLong(userId));
    po.setForumAuthorIp(request.getRemoteAddr());
    po.setForumAuthorOrg(session.getAttribute("orgName").toString());
    po.setForumSign(forumSign);
    po.setForumIssueTime(new Date());
    po.setForumModifyTime(new Date());
    po.setAnonymous(Byte.parseByte(niming));
    po.setDomainId(new Long(0L));
    po.setForumNotPrint(0);
    po.setNewretime(new Date());
    String mainForumId = request.getParameter("mainForumId");
    if (mainForumId == null || "".equals(mainForumId) || "null".equals(mainForumId))
      mainForumId = "none"; 
    String message = bd.add(po, classId, mainForumId, Long.valueOf(Long.parseLong(userId)), "0", forumSign);
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && message != null && ifActiveUpdateDelete != null && !"".equals(message) && 
      !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.addIndex(message, "oa_forum");
    } 
    Long forumId = Long.valueOf(po.getForumTopicId());
    if (forumId.longValue() != 0L)
      (new ForumBD()).updateModifytime(forumId); 
    ForumClassBD fbd = new ForumClassBD();
    ForumClassPO fpo = fbd.load(classId);
    if (0L == po.getForumTopicId()) {
      if ("1".equals(genzong))
        RemindUtil.addRemind(Long.parseLong(userId), Long.parseLong(message), "Forum"); 
    } else if (!"1".equals(fpo.getCheckExamin())) {
      RemindBD remindBD = new RemindBD();
      List<Remind> list = remindBD.searchReminduser(po.getForumTopicId(), "Forum");
      String userIds = "";
      if (!list.isEmpty()) {
        for (int i = 0; i < list.size(); i++) {
          Remind forumRemind = list.get(i);
          if (!userId.toString().equals((new Long(forumRemind.getEmp_id())).toString()))
            userIds = String.valueOf(userIds) + forumRemind.getEmp_id() + ","; 
        } 
        String url = "/jsoa/ForumAction.do?action=followList&classId=" + classId + "&mainForumId=" + po.getForumTopicId();
        String title = "您关注的帖子有新的回复";
        String title_author = "您跟踪的帖子有新的回复";
        Calendar tmp = Calendar.getInstance();
        tmp.set(2050, 12, 12);
        Long authorId = bd.getAuthorIdByForumId((new StringBuilder(String.valueOf(po.getForumTopicId()))).toString());
        if (!"".equals(userIds)) {
          if (!authorId.toString().equals("0") && !authorId.toString().equals(userId)) {
            String authorId_ = authorId + ",";
            RemindUtil.sendMessageToUsers(title_author, url, authorId_, "Forum", new Date(), tmp.getTime());
            userIds = userIds.replaceAll(authorId_, "");
          } 
          if (!userIds.equals("")) {
            userIds = userIds.substring(0, userIds.length() - 1);
            RemindUtil.sendMessageToUsers(title, url, userIds, "Forum", new Date(), tmp.getTime());
          } 
        } 
      } 
    } 
    request.setAttribute("message", message);
    request.setAttribute("endAddSign", "1");
    if (fpo != null && 
      "1".equals(fpo.getCheckExamin())) {
      String classOwnerIds = fpo.getClassOwnerIds();
      if (!"".equals(classOwnerIds)) {
        String userid = "*" + userId + "*";
        if (("**" + classOwnerIds).indexOf(userid) > 0) {
          request.setAttribute("checkExamin", "false");
          request.setAttribute("isOwner", "true");
        } else {
          request.setAttribute("checkExamin", "true");
          request.setAttribute("isOwner", "false");
          classOwnerIds = classOwnerIds.substring(1, classOwnerIds.length() - 1);
          String[] classOwnerIdss = classOwnerIds.split("\\*\\*");
          if (classOwnerIdss.length > 0)
            for (int i = 0; i < classOwnerIdss.length; i++) {
              Calendar tmp = Calendar.getInstance();
              tmp.set(2050, 12, 12);
              String title = "您有新的帖子需要审查";
              String url = "/jsoa/ForumAction.do?action=shenchalist&classId=" + fpo.getId();
              RemindUtil.sendMessageToUsers1(title, url, classOwnerIdss[i], "ShenChaNo", new Date(), tmp.getTime(), po.getForumAuthor(), Long.valueOf(Long.parseLong(message)));
            }  
        } 
      } 
    } 
  }
  
  private void bbsMenuList(HttpSession session, HttpServletRequest request) throws Exception {
    ManagerService mbd = new ManagerService();
    String userId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String domainId = "0";
    String moveRight = "0";
    ForumClassBD cbd = new ForumClassBD();
    boolean scopeSQL = mbd.hasRight(userId, "06*02*02");
    if (scopeSQL)
      moveRight = "1"; 
    request.setAttribute("moveRight", moveRight);
    String whereScopePara = "";
    String whereScopeManagerString = "";
    if (!"1".equals(moveRight)) {
      whereScopePara = mbd.getScopeFinalWhere(userId, curOrgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
      whereScopePara = " 1<>1 " + cbd.getClassIdString(userId, "po.id", "po.classOwnerIds like '%*" + userId + "*%' or " + whereScopePara);
    } else {
      whereScopePara = mbd.getScopeFinalWhere(userId, curOrgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
      whereScopePara = " 1<>1 " + cbd.getClassIdString(userId, "po.id", "po.classOwnerIds like '%*" + userId + "*%' or " + whereScopePara);
      whereScopeManagerString = mbd.getRightFinalWhere(userId, curOrgId, orgIdString, "讨论", "维护", "po.createdOrg", "po.createdEmp");
      whereScopeManagerString = String.valueOf(whereScopeManagerString) + cbd.getClassIdString(userId, "po.id");
      if (whereScopeManagerString.trim().length() > 0)
        whereScopePara = String.valueOf(whereScopePara) + " or " + whereScopeManagerString; 
    } 
    List list = (new WeiXinBD()).getForumClassList(whereScopePara, userId, domainId);
    request.setAttribute("list", list);
  }
  
  private void bbsList(HttpServletRequest request, HttpSession session) throws Exception {
    String domainId = (String)session.getAttribute("domainId");
    String classId = request.getParameter("classId");
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      keyword = URLDecoder.decode(keyword, "utf-8"); 
    if (classId == null || "".equals(classId))
      classId = "''"; 
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    String hql = "select po from com.js.oa.bbs.po.ForumPO po where po.forumClass.id=" + classId + " ";
    if (keyword != null && !"".equals(keyword))
      hql = String.valueOf(hql) + " and po.forumTitle like '%" + keyword + "%' "; 
    hql = String.valueOf(hql) + " and po.forumTopicId=0 and po.domainId=" + domainId + " order by po.forumTopOrder desc,po.newretime desc";
    Map map = (new WapBbsBD()).getBbsMap(hql, beginIndex, limit);
    request.setAttribute("bbslist", map.get("list"));
    request.setAttribute("size", map.get("size"));
    request.setAttribute("classId", classId);
    request.setAttribute("keyword", keyword);
  }
  
  private void reply(HttpServletRequest request) throws Exception {
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
    bbsInfo(request);
    request.setAttribute("flag", "1");
  }
  
  private void bbsInfo(HttpServletRequest request) throws Exception {
    WeiXinBD weixinBD = new WeiXinBD();
    String order = "po.id,po.forumIssueTime";
    if ("1".equals(SystemCommon.getForumOrder()))
      order = "po.forumTopicId,po.id desc,po.forumIssueTime desc"; 
    String beginIndex = (new StringBuilder(String.valueOf(request.getParameter("beginIndex")))).toString();
    String id = (new StringBuilder(String.valueOf(request.getParameter("forumId")))).toString();
    if (request.getParameter("type") != null) {
      String hql = "select po from com.js.oa.bbs.po.ForumPO po where po.id=" + id;
      request.setAttribute("forum", (new WapBbsBD()).getForumPO(hql));
      request.setAttribute("type", "2");
      weixinBD.updateReTime(id);
    } else {
      HttpSession session = request.getSession(true);
      String userId = session.getAttribute("userId").toString();
      int begin = Integer.parseInt((request.getParameter("begin") == null) ? "0" : request.getParameter("begin"));
      int limit = WapUtil.LIMITED;
      String sql = "select po from com.js.oa.bbs.po.ForumPO po where po.id=" + id;
      String hql = "select po from com.js.oa.bbs.po.ForumPO po where po.forumTopicId=" + id + " and po.examinNum <>1 order by " + order;
      Map mapO = weixinBD.getForumMap(sql, 0, limit);
      Map map = weixinBD.getForumMap(hql, begin, limit);
      List<ForumPO> list = (List<ForumPO>)map.get("list");
      List<ForumPO> list1 = (List<ForumPO>)mapO.get("list");
      ForumPO forumPO = list1.get(0);
      request.setAttribute("list", list);
      request.setAttribute("listSize", Integer.valueOf(list.size()));
      request.setAttribute("size", map.get("size"));
      request.setAttribute("type", "bbs");
      request.setAttribute("id", id);
      request.setAttribute("classId", Long.valueOf(forumPO.getForumClass().getId()));
      request.setAttribute("title", forumPO.getForumTitle());
      request.setAttribute("begin", Integer.valueOf(begin));
      request.setAttribute("forumPO", forumPO);
      ForumBD bd = new ForumBD();
      List<ForumClassPO> ret1 = bd.getForumClassPODetails((new StringBuilder(String.valueOf(forumPO.getForumClass().getId()))).toString());
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
  }
  
  private String getClassId(HttpServletRequest request) {
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
      whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "po.classUserId", 
          "po.classUserOrg", "po.classUserGroup");
      whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
    } else {
      whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "po.classUserId", 
          "po.classUserOrg", "po.classUserGroup");
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
