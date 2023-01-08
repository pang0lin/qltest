package com.js.oa.bbs.action;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblUserBD;
import com.active.e_uc.user.service.TblUserStatusBD;
import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.po.ForumPO;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.bbs.service.ForumClassBD;
import com.js.oa.search.client.SearchService;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.messages.RemindBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.messages.Remind;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.Accessory;
import com.js.util.util.CharacterTool;
import com.js.util.util.ReadActiveXml;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ForumAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    Long curUserId = new Long(request.getSession(true).getAttribute("userId").toString());
    String userIdStr = request.getSession(true).getAttribute("userId").toString();
    Long curOrgId = new Long(request.getSession(true).getAttribute("orgId").toString());
    String curUserName = request.getSession(true).getAttribute("userName").toString();
    String curOrgName = request.getSession(true).getAttribute("orgName").toString();
    ForumActionForm forumActionForm = (ForumActionForm)actionForm;
    String action = request.getParameter("action");
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    ForumBD bd = new ForumBD();
    ManagerService mbd = new ManagerService();
    String relProject = request.getParameter("relProject");
    if ("delForum".equals(action)) {
      String forumId = request.getParameter("forumId");
      String classId = request.getParameter("classId");
      if ("none".equals(classId))
        classId = request.getParameter("delClassId"); 
      String mainForumId = request.getParameter("mainForumId");
      String delType = request.getParameter("delType");
      String title = "您有帖子已被删除";
      String url1 = "/jsoa/ForumClassAction.do?action=listAll";
      Calendar tmp = Calendar.getInstance();
      tmp.set(2050, 12, 12);
      Long userIds = bd.getAuthorIdByForumId(forumId);
      if (!curUserId.toString().equals(userIds.toString()) && !userIds.toString().equals("0"))
        RemindUtil.sendMessageToUsers1(title, url1, userIds.toString(), "Forum", new Date(), tmp.getTime(), "系统提醒", Long.valueOf(Long.parseLong("0"))); 
      String retString = bd.delForum(forumId, classId);
      String path = request.getRealPath("/");
      path = String.valueOf(path) + "/upload/forum";
      Accessory.delAccessory(path, retString);
      if (mainForumId == null) {
        if ("shen".equals(delType)) {
          action = "shenchalist";
        } else {
          String moreType = request.getParameter("moreType");
          if (moreType != null && !moreType.equals("")) {
            action = "more";
          } else {
            action = "list";
          } 
        } 
      } else {
        ForumClassBD fbd = new ForumClassBD();
        ForumClassPO fpo = fbd.load(classId);
        String canMove = request.getParameter("delRight");
        String url = "/jsoa/ForumAction.do?action=followList&classId=" + classId + "&mainForumId=" + mainForumId + "&delRight=" + canMove + "&sign=0&pager.offset=" + 
          request.getParameter("pager.offset") + "&examinYesOrNot=" + request.getParameter("examinYesOrNot");
        request.setAttribute("url", url);
        return actionMapping.findForward("bbs_forward");
      } 
    } 
    if ("examin".equals(action)) {
      String forumId = request.getParameter("forumId");
      String message = bd.goExamin(forumId);
      MessagesBD messagesBD = new MessagesBD();
      messagesBD.changeMessageStatusForBbs(forumId, "ShenChaNo", "");
      ForumPO forumPO = bd.searchById(Long.parseLong(forumId));
      Long mainForumId = null;
      if (forumPO.getForumTopicId() == 0L) {
        mainForumId = Long.valueOf(forumPO.getId());
      } else {
        mainForumId = Long.valueOf(forumPO.getForumTopicId());
      } 
      String canMove = "0";
      String curOwnerIds = (new StringBuilder(String.valueOf(forumPO.getForumClass().getClassOwnerIds()))).toString();
      if (curOwnerIds.indexOf("*" + curUserId + "*") != -1)
        canMove = "1"; 
      Calendar tmp = Calendar.getInstance();
      tmp.set(2050, 12, 12);
      String url = "/jsoa/ForumAction.do?action=followList&classId=" + forumPO.getForumClass().getId() + "&mainForumId=" + mainForumId + "&delRight=" + canMove;
      String title = "";
      RemindBD remindBD = new RemindBD();
      List<Remind> list = remindBD.searchReminduser(forumPO.getForumTopicId(), "Forum");
      String userIds = "";
      if (!list.isEmpty()) {
        for (int i = 0; i < list.size(); i++) {
          Remind forumRemind = list.get(i);
          if (!curUserId.toString().equals((new Long(forumRemind.getEmp_id())).toString()))
            userIds = String.valueOf(userIds) + forumRemind.getEmp_id() + ","; 
        } 
        title = "您关注的帖子有新的回复";
        if (!"".equals(userIds)) {
          userIds = userIds.substring(0, userIds.length() - 1);
          RemindUtil.sendMessageToUsers(title, url, userIds, "Forum", new Date(), tmp.getTime());
        } 
      } 
      title = "您发的帖子通过了审查";
      url = "/jsoa/ForumAction.do?action=followList&classId=" + forumPO.getForumClass().getId() + "&mainForumId=" + mainForumId + "&delRight=0";
      RemindUtil.sendMessageToUsers(title, url, (new Long(forumPO.getForumAuthorId())).toString(), "ShenChaYes", new Date(), tmp.getTime(), curUserName, Long.valueOf(forumPO.getId()));
      request.setAttribute("message", message);
      action = "followList";
    } 
    if ("goExamin".equals(action)) {
      String forumId = request.getParameter("forumId");
      String message = bd.goExamin(forumId);
      MessagesBD messagesBD = new MessagesBD();
      messagesBD.changeMessageStatusForBbs(forumId, "ShenChaNo", "");
      ForumPO forumPO = bd.searchById(Long.parseLong(forumId));
      Long mainForumId = null;
      if (forumPO.getForumTopicId() == 0L) {
        mainForumId = Long.valueOf(forumPO.getId());
      } else {
        mainForumId = Long.valueOf(forumPO.getForumTopicId());
      } 
      Calendar tmp = Calendar.getInstance();
      tmp.set(2050, 12, 12);
      String title = "您发的帖子通过了审查";
      String url = "/jsoa/ForumAction.do?action=followList&classId=" + forumPO.getForumClass().getId() + "&mainForumId=" + mainForumId + "&delRight=0";
      RemindUtil.sendMessageToUsers(title, url, (new Long(forumPO.getForumAuthorId())).toString(), "ShenChaYes", new Date(), tmp.getTime(), curUserName, Long.valueOf(forumPO.getId()));
      request.setAttribute("message", message);
      action = "shenchalist";
    } 
    if ("soulForum".equals(action)) {
      String forumId = request.getParameter("forumId");
      String status = request.getParameter("status");
      String message = bd.soulForum(forumId, status);
      request.setAttribute("message", message);
      String moreType = request.getParameter("moreType");
      if (moreType != null && !moreType.equals("")) {
        action = "more";
      } else {
        action = "list";
      } 
    } 
    if ("setTop".equals(action)) {
      String setTopType = (request.getParameter("setTopType") == null) ? "1" : request.getParameter("setTopType");
      String forumId = request.getParameter("forumId");
      String classId = request.getParameter("classId");
      String message = bd.setTop(setTopType, forumId, classId);
      request.setAttribute("message", message);
      String moreType = request.getParameter("moreType");
      if (moreType != null && !moreType.equals("")) {
        action = "more";
      } else {
        action = "list";
      } 
    } 
    if ("setAuth".equals(action)) {
      String type = request.getParameter("type");
      String isnot = request.getParameter("isnot");
      String forumId = request.getParameter("forumId");
      String message = bd.setAuth(type, isnot, forumId);
      request.setAttribute("message", message);
      String moreType = request.getParameter("moreType");
      if (moreType != null && !moreType.equals("")) {
        action = "more";
      } else {
        action = "list";
      } 
    } 
    if ("shenchalist".equals(action)) {
      String classId = request.getParameter("classId");
      shenchalist(request, classId);
      if (classId != null && 
        !"".equals(classId) && !"null".equals(classId) && !"none".equals(classId)) {
        List<ForumClassPO> ret1 = bd.getForumClassPODetails(classId);
        ForumClassPO poclassmm = ret1.get(0);
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
        classOwnerIds = classOwnerIds.substring(0, classOwnerIds.length() - 1);
        ForumClassBD Cbd = new ForumClassBD();
        String empSimpleNames = Cbd.selectSimpleName(classOwnerIds, domainId);
        request.setAttribute("empSimpleNames", empSimpleNames);
        request.setAttribute("isClassOwner", isClassOwner);
        String ClassEmail = poclassmm.getClassEmail();
        request.setAttribute("classEmail", ClassEmail);
        request.setAttribute("classId", classId);
        request.setAttribute("poclassmm", poclassmm);
      } 
      return actionMapping.findForward("shenchalist");
    } 
    if ("list".equals(action)) {
      String forumClassId = request.getParameter("classId");
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      String queryText = forumActionForm.getQueryText();
      String queryItem = forumActionForm.getQueryItem();
      String queryMan = forumActionForm.getQueryMan();
      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      String classId = request.getParameter("classId");
      String queryTitle = forumActionForm.getQueryTitle();
      String queryClass = forumActionForm.getQueryClass();
      String queryForumType = forumActionForm.getQueryForumType();
      if (queryTitle == null || queryTitle.equals(""))
        queryTitle = request.getParameter("queryTitle"); 
      if (queryClass == null || queryClass.equals(""))
        queryClass = request.getParameter("queryClass"); 
      if (queryForumType == null || queryForumType.equals(""))
        queryForumType = request.getParameter("queryForumType"); 
      if (queryClass == null) {
        forumActionForm.setQueryClass(classId);
      } else {
        forumActionForm.setQueryClass(queryClass);
      } 
      ForumClassBD cbd = new ForumClassBD();
      String moveRight = "0";
      boolean scopeSQL = mbd.hasRight(curUserId.toString(), "06*02*02");
      if (scopeSQL && 
        !"none".equals(forumClassId))
        if (bd.isHasMoveRight(curUserId.toString(), curOrgId.toString(), forumClassId, domainId))
          moveRight = "1";  
      request.setAttribute("moveRight", moveRight);
      String whereScopePara = "";
      String whereScopeManagerString = "";
      if (!"1".equals(moveRight)) {
        whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
        whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.forumClass.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
        whereScopePara = String.valueOf(whereScopePara) + "or ( ( po.forumClass.classUserId is null or po.forumClass.classUserId='') and (po.forumClass.classUserOrg is null or po.forumClass.classUserOrg ='' ) and  ( po.forumClass.classUserGroup is null or po.forumClass.classUserGroup ='') )";
      } else {
        whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), 
            curOrgId.toString(), 
            orgIdString, 
            "po.classUserId", 
            "po.classUserOrg", 
            "po.classUserGroup");
        whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.forumClass.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
        whereScopeManagerString = mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "讨论", "维护", "po.forumClass.createdOrg", "po.forumClass.createdEmp");
        whereScopeManagerString = String.valueOf(whereScopeManagerString) + cbd.getClassIdString(curUserId.toString(), "po.forumClass.id");
        if (whereScopeManagerString.trim().length() > 0)
          whereScopePara = String.valueOf(whereScopePara) + " or " + whereScopeManagerString; 
      } 
      String replyDesc = request.getParameter("replyDesc");
      String kitDesc = request.getParameter("kitDesc");
      String btimeDesc = request.getParameter("btimeDesc");
      String sortFirst = request.getParameter("sortFirst");
      String examinYesOrNot = "y";
      Vector<String> vec = bd.list(classId, startDate, endDate, new Integer(offset), queryText, queryItem, queryClass, queryMan, queryForumType, whereScopePara, 
          
          replyDesc, kitDesc, btimeDesc, sortFirst, domainId);
      Vector<String> pageVec = getPage(curUserId.toString(), classId, startDate, endDate, new Integer(offset), queryText, queryItem, queryClass, queryMan, queryForumType, whereScopePara, 
          
          replyDesc, kitDesc, btimeDesc, sortFirst, queryTitle, domainId, examinYesOrNot, userIdStr);
      String recordCount = pageVec.get(0);
      String pageSize = vec.get(1);
      request.setAttribute("myList", pageVec.get(1));
      request.setAttribute("classOwnerName", vec.get(3));
      request.setAttribute("classDate", vec.get(4));
      request.setAttribute("classHasJunior", vec.get(5));
      request.setAttribute("classRange", vec.get(8));
      request.setAttribute("isTrue", vec.get(9));
      String addBulletinRight = "0";
      String ownerIds = vec.get(6);
      if (ownerIds.indexOf("*" + curUserId + "*") != -1)
        addBulletinRight = "1"; 
      request.setAttribute("addBulletinRight", addBulletinRight);
      request.setAttribute("navigationVec", vec.get(7));
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", pageSize);
      request.setAttribute("pageParameters", 
          "action,sortFirst,kitDesc,replyDesc,btimeDesc,moveRight,classId,queryText,queryItem,queryClass,queryMan,startDate,endDate,queryForumType,queryTitle,examinYesOrNot");
      cbd = new ForumClassBD();
      String whereScopePara1 = "";
      String whereScopeManagerString1 = "";
      if (!"1".equals(moveRight)) {
        whereScopePara1 = mbd.getScopeFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
        whereScopePara1 = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara1);
      } else {
        whereScopePara1 = mbd.getScopeFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
        whereScopePara1 = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara1);
        whereScopeManagerString1 = mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "讨论", "维护", "po.createdOrg", "po.createdEmp");
        whereScopeManagerString1 = String.valueOf(whereScopeManagerString1) + cbd.getClassIdString(curUserId.toString(), "po.id");
        if (whereScopeManagerString1.trim().length() > 0)
          whereScopePara1 = String.valueOf(whereScopePara1) + " or " + whereScopeManagerString1; 
      } 
      request.setAttribute("classList", cbd.listMenu(whereScopePara1, curUserId.toString(), domainId));
      if (classId != null && 
        !"".equals(classId) && !"null".equals(classId) && !"none".equals(classId)) {
        List<ForumClassPO> ret1 = bd.getForumClassPODetails(classId);
        ForumClassPO poclassmm = ret1.get(0);
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
        classOwnerIds = classOwnerIds.substring(0, classOwnerIds.length() - 1);
        ForumClassBD Cbd = new ForumClassBD();
        String empSimpleNames = Cbd.selectSimpleName(classOwnerIds, domainId);
        request.setAttribute("empSimpleNames", empSimpleNames);
        request.setAttribute("isClassOwner", isClassOwner);
        String ClassEmail = poclassmm.getClassEmail();
        request.setAttribute("classEmail", ClassEmail);
        request.setAttribute("poclassmm", poclassmm);
      } 
    } 
    if ("more".equals(action)) {
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int pageSize = 15;
      int currentPage = offset / pageSize + 1;
      String queryItem = forumActionForm.getQueryItem();
      String queryMan = forumActionForm.getQueryMan();
      String queryTitle = forumActionForm.getQueryTitle();
      String queryClass = forumActionForm.getQueryClass();
      String queryForumType = forumActionForm.getQueryForumType();
      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      if (queryTitle == null || "".equals(queryTitle))
        queryTitle = request.getParameter("queryTitle"); 
      if (queryClass == null || "".equals(queryClass))
        queryClass = request.getParameter("queryClass"); 
      if (queryForumType == null || "".equals(queryForumType))
        queryForumType = request.getParameter("queryForumType"); 
      String replyDesc = request.getParameter("replyDesc");
      String kitDesc = request.getParameter("kitDesc");
      String btimeDesc = request.getParameter("btimeDesc");
      String sortFirst = request.getParameter("sortFirst");
      String orderString = "";
      String orderByReplay = "";
      String orderByKit = "";
      String orderBybTime = "";
      if (!"none".equals(replyDesc) && replyDesc != null)
        if ("1".equals(replyDesc)) {
          orderByReplay = " po.forumRevertNum , ";
        } else {
          orderByReplay = " po.forumRevertNum desc , ";
        }  
      if (!"none".equals(kitDesc) && kitDesc != null)
        if ("1".equals(kitDesc)) {
          orderByKit = " po.forumKits , ";
        } else {
          orderByKit = " po.forumKits desc , ";
        }  
      if (!"none".equals(btimeDesc) && btimeDesc != null)
        if ("1".equals(btimeDesc)) {
          orderBybTime = " po.forumIssueTime , ";
        } else {
          orderBybTime = " po.forumIssueTime desc , ";
        }  
      if (!"none".equals(sortFirst) && sortFirst != null)
        if ("reply".equals(sortFirst)) {
          orderString = orderByReplay;
        } else if ("kit".equals(sortFirst)) {
          orderString = orderByKit;
        } else {
          orderString = orderBybTime;
        }  
      String forwardName = "";
      orderString = String.valueOf(orderString) + "po.forumType desc,po.forumTopOrder desc,po.forumIsSoul desc, ";
      String moreType = request.getParameter("moreType");
      request.setAttribute("moreType", moreType);
      String whereString = "";
      String forumClassIds = request.getParameter("forumClassIds");
      request.setAttribute("forumClassIds", forumClassIds);
      List classList = bd.getForumClass(forumClassIds, "0");
      request.setAttribute("classList", classList);
      ArrayList canMoveForumList = bd.getHasMoveRightForumList(curUserId.toString(), curOrgId.toString(), forumClassIds.split(","));
      request.setAttribute("canMoveForumList", canMoveForumList);
      Page page = null;
      if (moreType.equals("attention")) {
        whereString = bd.getAttentionMoreSql(curUserId, forumClassIds, orderString, queryItem, queryMan, startDate, endDate, queryTitle, queryClass, queryForumType);
        forwardName = "getMoreAttention";
        page = new Page("po.id,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.id,po.forumClass.classUserId,po.forumClass.classUserOrg,po.forumClass.classUserGroup,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.classOwnerIds,po.anonymous,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,po.forumModifyTime,po.forumTopOrder,po.forumClass.banPrint,po.forumClass.className,po.forumTitle,po.forumClass.fullDay,po.forumClass.startPeriod,po.forumClass.endPeriod ", 






            
            " com.js.system.vo.messages.Remind remind,com.js.oa.bbs.po.ForumPO po ", 
            whereString);
      } else if (moreType.equals("soul")) {
        whereString = bd.getSoulMoreSql(forumClassIds, orderString, queryItem, queryMan, startDate, endDate, queryTitle, queryClass);
        forwardName = "getMoreSoul";
        page = getMorePage(whereString);
      } else if (moreType.equals("top")) {
        whereString = bd.GetTopMoreSql(forumClassIds, orderString, queryItem, queryMan, startDate, endDate, queryTitle, queryClass, queryForumType);
        forwardName = "getMoreTop";
        page = getMorePage(whereString);
      } else if (moreType.equals("hot")) {
        whereString = bd.getHotMoreSql(forumClassIds, orderString, queryItem, queryMan, startDate, endDate, queryTitle, queryClass, queryForumType);
        forwardName = "getMoreHot";
        page = getMorePage(whereString);
      } else if (moreType.equals("new")) {
        whereString = bd.getNewUpdateMoreSql(forumClassIds, orderString, queryItem, queryMan, startDate, endDate, queryTitle, queryClass, queryForumType);
        forwardName = "getMoreNew";
        page = getMorePage(whereString);
      } 
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List dataList = page.getResultList();
      request.setAttribute("myList", dataList);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
      request.setAttribute("pageParameters", 
          "action,sortFirst,kitDesc,replyDesc,btimeDesc,queryItem,queryMan,queryTitle,queryClass,queryForumType,startDate,endDate,moreType,forumClassIds");
      return actionMapping.findForward(forwardName);
    } 
    if ("add".equals(action)) {
      if ("1".equals(request.getParameter("textType")))
        forumActionForm.setForumContent1(CharacterTool.escapeHTMLTags2(forumActionForm.getForumContent1())); 
      String contexeyinyong = request.getParameter("contexeyinyong");
      ForumPO po = new ForumPO();
      if (contexeyinyong != null && !"".equals(contexeyinyong) && !"null".equals(contexeyinyong)) {
        po.setForumContent(String.valueOf(contexeyinyong) + CharacterTool.escapeHTMLTags2(forumActionForm.getForumContent1()));
      } else {
        po.setForumContent(CharacterTool.escapeHTMLTags2(forumActionForm.getForumContent1()));
      } 
      try {
        BeanUtils.copyProperties(po, forumActionForm);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      String attachName = "";
      String[] attachNameArr = forumActionForm.getAccessoryName();
      String attachSaveName = "";
      String[] attachSaveNameArr = forumActionForm.getAccessorySaveName();
      int k = 0;
      for (int i = 0; attachSaveNameArr != null && i < attachSaveNameArr.length; i++) {
        if (attachSaveNameArr[i] != null && !"".equals(attachSaveNameArr[i])) {
          k++;
          if (k != 1) {
            attachName = String.valueOf(attachName) + "|";
            attachSaveName = String.valueOf(attachSaveName) + "|";
          } 
          attachName = String.valueOf(attachName) + attachNameArr[i];
          attachSaveName = String.valueOf(attachSaveName) + attachSaveNameArr[i];
        } 
      } 
      po.setForumAttachName(attachName);
      po.setForumAttachSave(attachSaveName);
      po.setForumAuthorId(curUserId.longValue());
      po.setForumAuthor(curUserName);
      po.setForumAuthorIp(request.getRemoteAddr());
      po.setForumAuthorOrg(curOrgName);
      po.setForumIssueTime(new Date());
      po.setForumModifyTime(new Date());
      po.setNewretime(new Date());
      po.setDomainId(Long.valueOf(domainId));
      String addType = request.getParameter("addType");
      if ("bulletin".equals(addType)) {
        po.setForumType(Byte.parseByte("2"));
      } else {
        po.setForumType(Byte.parseByte("0"));
      } 
      po.setForumTopicId(0L);
      String forumSign = "";
      String addSign = forumActionForm.getAddSign();
      if ("1".equals(addSign)) {
        forumSign = forumActionForm.getForumSign();
        forumSign = (forumSign == null) ? "" : forumSign;
      } else {
        po.setForumSign("");
      } 
      action = "see";
      String message = (new ForumBD()).checkAdd(po);
      if (!"".equals(message)) {
        request.setAttribute("message", message);
      } else {
        String classId = forumActionForm.getClassId();
        classId = (classId == null) ? "-1" : classId;
        String mainForumId = request.getParameter("mainForumId");
        if (mainForumId == null || "".equals(mainForumId) || "null".equals(mainForumId))
          mainForumId = "none"; 
        message = bd.add(po, classId, mainForumId, curUserId, addSign, forumSign);
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        if ("1".equals(isearchSwitch) && message != null && ifActiveUpdateDelete != null && !"".equals(message) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.addIndex(message, "oa_forum");
        } 
        Long forumId = Long.valueOf(po.getForumTopicId());
        if (forumId.longValue() != 0L)
          (new ForumBD()).updateModifytime(forumId); 
        ForumClassBD fbd = new ForumClassBD();
        ForumClassPO fpo = fbd.load(classId);
        if (0L == po.getForumTopicId()) {
          if ("1".equals(request.getParameter("slot")))
            RemindUtil.addRemind(curUserId.longValue(), Long.parseLong(message), "Forum"); 
        } else if (!"1".equals(fpo.getCheckExamin())) {
          RemindBD remindBD = new RemindBD();
          List<Remind> list = remindBD.searchReminduser(po.getForumTopicId(), "Forum");
          String userIds = "";
          if (!list.isEmpty()) {
            for (int j = 0; j < list.size(); j++) {
              Remind forumRemind = list.get(j);
              if (!curUserId.toString().equals((new Long(forumRemind.getEmp_id())).toString()))
                userIds = String.valueOf(userIds) + forumRemind.getEmp_id() + ","; 
            } 
            String url = "/jsoa/ForumAction.do?action=followList&classId=" + classId + "&mainForumId=" + po.getForumTopicId();
            String title = "您关注的帖子有新的回复";
            String title_author = "您跟踪的帖子有新的回复";
            Calendar tmp = Calendar.getInstance();
            tmp.set(2050, 12, 12);
            Long authorId = bd.getAuthorIdByForumId((new StringBuilder(String.valueOf(po.getForumTopicId()))).toString());
            if (!"".equals(userIds)) {
              if (!authorId.toString().equals("0") && !authorId.toString().equals(curUserId.toString())) {
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
            String userid = "*" + curUserId + "*";
            if (("**" + classOwnerIds).indexOf(userid) > 0) {
              request.setAttribute("checkExamin", "false");
              request.setAttribute("isOwner", "true");
            } else {
              request.setAttribute("checkExamin", "true");
              request.setAttribute("isOwner", "false");
              classOwnerIds = classOwnerIds.substring(1, classOwnerIds.length() - 1);
              String[] classOwnerIdss = classOwnerIds.split("\\*\\*");
              if (classOwnerIdss.length > 0)
                for (int j = 0; j < classOwnerIdss.length; j++) {
                  Calendar tmp = Calendar.getInstance();
                  tmp.set(2050, 12, 12);
                  String title = "您有新的帖子需要审查";
                  String url = "/jsoa/ForumAction.do?action=shenchalist&classId=" + fpo.getId();
                  RemindUtil.sendMessageToUsers1(title, url, classOwnerIdss[j], "ShenChaNo", new Date(), tmp.getTime(), po.getForumAuthor(), Long.valueOf(Long.parseLong(message)));
                }  
            } 
          } 
        } 
        forumActionForm.reset(actionMapping, request);
        String newquick = request.getParameter("newquick");
        if ("newquick".equals(newquick)) {
          int pag = Integer.valueOf(request.getParameter("pag")).intValue();
          int rec = Integer.valueOf(request.getParameter("rec")).intValue();
          rec = pag * rec / pag;
          String canMove = request.getParameter("delRight");
          String curOwnerIds = (new StringBuilder(String.valueOf(po.getForumClass().getClassOwnerIds()))).toString();
          if (curOwnerIds.indexOf("*" + curUserId + "*") != -1)
            canMove = "1"; 
          String url = "/jsoa/ForumAction.do?action=followList&classId=" + classId + "&mainForumId=" + po.getForumTopicId() + "&delRight=" + canMove + "&forumType=" + po.getForumType() + 
            "&sign=0&pager.offset=" + rec;
          request.setAttribute("url", url);
          return actionMapping.findForward("bbs_forward");
        } 
      } 
    } 
    if ("reply".equals(action)) {
      String forumTitle = "";
      forumTitle = bd.getForumTitle(request.getParameter("forumId"));
      forumActionForm.setForumTitle("Re: " + forumTitle);
      String yinyong = request.getParameter("yingyongid");
      String contexeyinyong = "";
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      if (yinyong != null && !"".equals(yinyong)) {
        ForumPO po = new ForumPO();
        po = bd.searchById(Long.parseLong(yinyong));
        String authorString = po.getForumAuthor();
        if ("1".equals((new Byte(po.getAnonymous())).toString()))
          authorString = "匿名作者"; 
        contexeyinyong = "<div style='border:1px solid #abbece' width='80%' height='100%'  align='center' > <table style='background-color: #FEFEF4;'  width='99%'> <tr> <td>引用：</td></tr><tr><td>";
        contexeyinyong = String.valueOf(contexeyinyong) + authorString + "&nbsp;&nbsp;于" + dateFormat.format(po.getForumIssueTime()) + "&nbsp;&nbsp;发表";
        contexeyinyong = String.valueOf(contexeyinyong) + "<br />&nbsp;&nbsp;" + po.getForumContent() + "</td> </tr> </table> </div>";
      } 
      request.setAttribute("contexeyinyong", contexeyinyong);
      action = "see";
    } 
    if ("see".equals(action)) {
      String mySign = "";
      mySign = bd.see(curUserId);
      mySign = (mySign == null || "null".equals(mySign)) ? "" : mySign;
      forumActionForm.setForumSign(mySign);
      String classId = request.getParameter("classId");
      List<ForumClassPO> ret1 = bd.getForumClassPODetails(classId);
      ForumClassPO poclassmm = ret1.get(0);
      request.setAttribute("estopAnonymity", poclassmm.getEstopAnonymity());
      if (relProject == null || "null".equals(relProject) || "".equals(relProject)) {
        request.setAttribute("relProject", "N");
      } else {
        request.setAttribute("relProject", relProject);
      } 
      return actionMapping.findForward("add");
    } 
    if ("followList".equals(action)) {
      String forumId = request.getParameter("mainForumId");
      String delRight = request.getParameter("delRight");
      request.setAttribute("oneforumId", forumId);
      int offset = 0;
      if (request.getParameter("pager.offset") != null && !"null".equals(request.getParameter("pager.offset")))
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      String examinYesOrNot = "";
      String tests = (request.getParameter("examinYesOrNot") == null) ? "" : request.getParameter("examinYesOrNot");
      if (request.getParameter("examinYesOrNot") != null && request.getParameter("examinYesOrNot").equals("notExamin")) {
        examinYesOrNot = "n";
      } else {
        examinYesOrNot = "y";
      } 
      Vector vec = null;
      try {
        vec = bd.followList(forumId, new Integer(offset), request.getParameter("sign"));
        request.setAttribute("oneforumUserId", vec.get(6));
      } catch (Exception e) {
        e.printStackTrace();
        return actionMapping.findForward("error");
      } 
      Vector<String> pageVec = getFollowPage(forumId, new Integer(offset), request.getParameter("sign"), examinYesOrNot);
      if (vec.size() < 1) {
        request.setAttribute("empty", "1");
      } else {
        request.setAttribute("delRight", delRight);
        request.setAttribute("topicTitle", vec.get(0));
        request.setAttribute("mylist", pageVec.get(0));
        String recordCount = pageVec.get(1);
        String pageSize = pageVec.get(2);
        String maxPage = pageVec.get(3);
        request.setAttribute("maxIndex", vec.get(5));
        request.setAttribute("recordCount", recordCount);
        request.setAttribute("maxPageItems", pageSize);
        request.setAttribute("maxPage", maxPage);
      } 
      request.setAttribute("pageParameters", "action,delRight,pager.offset1,moveRight,classId,queryText,queryItem,queryClass,queryMan,startDate,endDate,mainForumId,queryForumType,queryTitle");
      String classId = request.getParameter("classId");
      List<ForumClassPO> ret1 = bd.getForumClassPODetails(classId);
      ForumClassPO poclassmm = ret1.get(0);
      request.setAttribute("banPrint", poclassmm.getBanPrint());
      request.setAttribute("estopAnonymity", poclassmm.getEstopAnonymity());
      String isClassOwner = "0";
      String isCheckExam = "0";
      if ("1".equals(poclassmm.getCheckExamin()))
        isCheckExam = "1"; 
      request.setAttribute("isCheckExam", isCheckExam);
      String classOwnerIds = poclassmm.getClassOwnerIds();
      String userid = "*" + curUserId + "*";
      if (classOwnerIds != null && ("**" + classOwnerIds).indexOf(userid) > 0)
        isClassOwner = "1"; 
      String[] classOwnerIdss = classOwnerIds.split("\\*\\*");
      classOwnerIds = "";
      for (int i = 0; i < classOwnerIdss.length; i++) {
        String tmp = classOwnerIdss[i].replaceAll("\\*", "");
        classOwnerIds = String.valueOf(classOwnerIds) + tmp + ",";
      } 
      classOwnerIds = classOwnerIds.substring(0, classOwnerIds.length() - 1);
      ForumClassBD Cbd = new ForumClassBD();
      String empSimpleNames = Cbd.selectSimpleName(classOwnerIds, domainId);
      request.setAttribute("empSimpleNames", empSimpleNames);
      request.setAttribute("isClassOwner", isClassOwner);
      List<ForumPO> ret2 = bd.getForumPODetails(forumId);
      ForumPO pomm = ret2.get(0);
      request.setAttribute("print", (new StringBuilder(String.valueOf(pomm.getForumNotPrint()))).toString());
      request.setAttribute("flow", (new StringBuilder(String.valueOf(pomm.getForumNotFlow()))).toString());
      request.setAttribute("Upd", (new StringBuilder(String.valueOf(pomm.getForumNotUpd()))).toString());
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
        String username1 = (String)request.getSession().getAttribute("userAccount");
        TblUserBD tblUserBD = new TblUserBD();
        TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
        String status = "false";
        try {
          TblUser tblUser = tblUserBD.findTblUser(username1);
          status = tblUserStatusBD.findstatus(tblUser.getId());
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
        request.setAttribute("iactive", status);
        try {
          List userOnlinList = tblUserStatusBD.findUserOnline();
          request.setAttribute("userOnlinList", userOnlinList);
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
      return actionMapping.findForward("follow");
    } 
    if ("move".equals(action)) {
      String ids = request.getParameter("ids");
      String classId = forumActionForm.getAllForumClass();
      String oriClassId = forumActionForm.getClassId();
      int site = classId.indexOf("^");
      if (site != -1)
        classId = classId.substring(0, site); 
      bd.move(ids, classId, oriClassId);
      ForumClassBD cbd = new ForumClassBD();
      List li = cbd.see(mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "06*02*02", "po.createdOrg", "po.createdEmp"), domainId);
      request.setAttribute("forumClass", li);
      return actionMapping.findForward("move");
    } 
    if ("userlist".equals(action)) {
      String empId = request.getParameter("empId");
      List list = bd.userList(empId);
      request.setAttribute("mylist", list);
      return actionMapping.findForward("userlist");
    } 
    if ("notebook".equals(action)) {
      String forumId = request.getParameter("forumId");
      Vector<String> vec = bd.noteBook(forumId);
      if (vec != null && vec.size() > 2) {
        String noteLink = "/jsoa/ForumAction.do?action=followList";
        noteLink = String.valueOf(noteLink) + "&classId=" + vec.get(0);
        noteLink = String.valueOf(noteLink) + "&mainForumId=" + vec.get(2);
        request.setAttribute("noteLink", noteLink);
        String noteTitle = vec.get(1);
        request.setAttribute("noteTitle", noteTitle);
        request.setAttribute("noteClassName", "论坛");
      } 
      return actionMapping.findForward("notebook");
    } 
    if ("modify".equals(action)) {
      getSingle(request, forumActionForm);
      request.setAttribute("classId", request.getParameter("classId"));
      request.setAttribute("examinYesOrNot", request.getParameter("examinYesOrNot"));
      String classId = request.getParameter("classId");
      if (classId != null && !"".equals(classId) && !"null".equals(classId) && !"none".equals(classId)) {
        List<ForumClassPO> ret1 = bd.getForumClassPODetails(classId);
        ForumClassPO poclassmm = ret1.get(0);
        request.setAttribute("estopAnonymity", poclassmm.getEstopAnonymity());
      } 
      String remind = (request.getParameter("remind") == null) ? "Y" : request.getParameter("remind");
      request.setAttribute("remind", remind);
      return actionMapping.findForward("modify");
    } 
    if ("update".equals(action)) {
      update(request);
      ForumClassBD fbd = new ForumClassBD();
      ForumClassPO fpo = fbd.load(request.getParameter("classId"));
      if (fpo != null && 
        "1".equals(fpo.getCheckExamin())) {
        request.setAttribute("examinYesOrNot", "yesExamin");
        if (fpo.getClassOwnerId() == curUserId.longValue() || fpo.getClassOwnerIds().indexOf("*" + String.valueOf(curUserId) + "*") != -1)
          request.setAttribute("isOwner", "true"); 
      } 
      return actionMapping.findForward("modify");
    } 
    if ("otherbbs".equals(action)) {
      ForumBD fbd = new ForumBD();
      String othertype = request.getParameter("othertype");
      List list = null;
      if ("Attention".equals(othertype))
        list = fbd.searchAttentionByUserid(curUserId.longValue(), "N"); 
      request.setAttribute("myList", list);
      return actionMapping.findForward("otherbbs");
    } 
    return actionMapping.findForward("list");
  }
  
  private void getSingle(HttpServletRequest httpServletRequest, ForumActionForm forumActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    Map forum = (new ForumBD()).getSingle(httpServletRequest.getParameter("forumId"), httpSession.getAttribute("userId").toString());
    if (forum != null) {
      if (forum.get("forumTitle") != null) {
        forumActionForm.setForumTitle(forum.get("forumTitle").toString());
      } else {
        forumActionForm.setForumTitle("");
      } 
      if (forum.get("forumAttachName") != null) {
        httpServletRequest.setAttribute("realFileArray", forum.get("forumAttachName"));
      } else {
        httpServletRequest.setAttribute("realFileArray", new String[0]);
      } 
      if (forum.get("forumAttachSave") != null) {
        httpServletRequest.setAttribute("saveFileArray", forum.get("forumAttachSave"));
      } else {
        httpServletRequest.setAttribute("saveFileArray", new String[0]);
      } 
      if (forum.get("forumType") != null) {
        httpServletRequest.setAttribute("forumType", forum.get("forumType"));
      } else {
        httpServletRequest.setAttribute("forumType", "0");
      } 
      if (forum.get("content") != null) {
        httpServletRequest.setAttribute("content", forum.get("content"));
      } else {
        httpServletRequest.setAttribute("content", "");
      } 
      if (forum.get("anonymous") != null) {
        httpServletRequest.setAttribute("anonymous", forum.get("anonymous"));
      } else {
        httpServletRequest.setAttribute("anonymous", "0");
      } 
      if (forum.get("forumSign") != null) {
        forumActionForm.setForumSign(forum.get("forumSign").toString());
        httpServletRequest.setAttribute("forumSign", forum.get("forumSign").toString());
      } else {
        forumActionForm.setForumSign("");
      } 
    } 
  }
  
  private void update(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    String forumId = httpServletRequest.getParameter("forumId");
    String forumTitle = httpServletRequest.getParameter("forumTitle");
    String forumType = httpServletRequest.getParameter("textType");
    String forumContent = httpServletRequest.getParameter("forumContent1");
    String anonymous = httpServletRequest.getParameter("anonymous");
    String forumSign = httpServletRequest.getParameter("forumSign");
    String forumAttachName = "", forumAttachSave = "";
    String[] accessoryName = httpServletRequest.getParameterValues("accessoryName");
    String[] accessorySaveName = httpServletRequest.getParameterValues("accessorySaveName");
    if (accessoryName != null)
      for (int i = 0; i < accessoryName.length; i++) {
        forumAttachName = String.valueOf(forumAttachName) + accessoryName[i] + "|";
        forumAttachSave = String.valueOf(forumAttachSave) + accessorySaveName[i] + "|";
      }  
    if (!forumAttachName.equals("")) {
      forumAttachName = forumAttachName.substring(0, forumAttachName.length() - 1);
      forumAttachSave = forumAttachSave.substring(0, forumAttachSave.length() - 1);
    } 
    String[] forumPara = { forumId, forumTitle, forumType, forumContent, anonymous, forumSign, forumAttachName, forumAttachSave };
    (new ForumBD()).update(forumPara);
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && forumId != null && ifActiveUpdateDelete != null && !"".equals(forumId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.updateIndex(forumId.toString(), "oa_forum");
    } 
    String remind = httpServletRequest.getParameter("remind");
    Long curUserId = new Long(httpServletRequest.getSession(true).getAttribute("userId").toString());
    if ("Y".equals(remind)) {
      String remindYesOrNo = RemindUtil.searchYesOrNo(curUserId.longValue(), Long.parseLong(forumId), "Forum");
      if ("1".equals(httpServletRequest.getParameter("slot"))) {
        if ("N".equals(remindYesOrNo))
          RemindUtil.addRemind(curUserId.longValue(), Long.parseLong(forumId), "Forum"); 
      } else if ("Y".equals(remindYesOrNo)) {
        RemindUtil.delRemind(curUserId.longValue(), Long.parseLong(forumId), "Forum");
      } 
    } 
    ForumPO forumPO = (new ForumBD()).searchById(Long.parseLong(forumId));
    if (forumPO.getForumClass() != null && 
      "1".equals(forumPO.getForumClass().getCheckExamin())) {
      String classOwnerIds = forumPO.getForumClass().getClassOwnerIds();
      String userid = "*" + curUserId + "*";
      if (("**" + classOwnerIds).indexOf(userid) <= 0 && 
        !"".equals(classOwnerIds)) {
        classOwnerIds = classOwnerIds.substring(1, classOwnerIds.length() - 1);
        String[] classOwnerIdss = classOwnerIds.split("\\*\\*");
        if (classOwnerIdss.length > 0)
          for (int i = 0; i < classOwnerIdss.length; i++) {
            Calendar tmp = Calendar.getInstance();
            tmp.set(2050, 12, 12);
            String title = "您有新的帖子需要审查";
            String url = "/jsoa/ForumAction.do?action=shenchalist&classId=" + forumPO.getForumClass().getId();
            RemindUtil.sendMessageToUsers1(title, url, classOwnerIdss[i], "ShenChaNo", new Date(), tmp.getTime(), forumPO.getForumAuthor(), Long.valueOf(forumPO.getId()));
          }  
      } 
    } 
    httpServletRequest.setAttribute("close", "1");
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
    orderby = String.valueOf(orderby) + "po.forumType desc,po.forumTopOrder desc,po.forumIsSoul desc,po.forumModifyTime desc, ";
    StringBuffer where = new StringBuffer(" where po.forumTopicId=0");
    if ("1".equals(queryForumType)) {
      where.append(" and po.forumType=0").append(" and po.forumIsSoul=1");
    } else if (!"-1".equals(queryForumType) && queryForumType != null) {
      where.append(" and po.forumType=").append(queryForumType).append(" and po.forumIsSoul=0");
    } 
    if (examinYesOrNot != null && examinYesOrNot.equals("n")) {
      where.append(" and ( (po.examinNum=1").append(" and po.forumClass.classOwnerIds like '%*" + userIdStr + "*%' ) ").append(
          " or ( po.examinNum<>1  and po.forumClass.classOwnerIds like '%*" + userIdStr + "*%'" + 
          " and ( select count(*) from com.js.oa.bbs.po.ForumPO xpo where xpo.examinNum=1 and xpo.forumTopicId=po.id )>0)) ");
    } else {
      where.append(" and (po.examinNum=2").append(" or po.examinNum=3 or po.examinNum is null)");
    } 
    if (queryClass != null && !"none".equals(queryClass)) {
      where.append(" and (").append(forumBD.classIdQuery(queryClass)).append(")");
    } else if (!"none".equals(queryClass) && !"none".equals(classId)) {
      where.append(" and po.forumClass.id = ").append(classId);
    } 
    if (queryItem != null && "1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where.append(" and ( po.forumIssueTime between '").append(startDate).append(" 00:00:00").append("' and '").append(endDate).append(" 23:59:59").append("'").append(
            " or  po.forumIssueTime between '").append(endDate).append(" 00:00:00").append("' and '").append(startDate).append(" 23:59:59").append("' )");
      } else {
        where.append(" and ( po.forumIssueTime between JSDB.FN_STRTODATE('").append(startDate).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(endDate).append(" 23:59:59")
          .append("','L')").append(" or  po.forumIssueTime between JSDB.FN_STRTODATE('").append(endDate).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(startDate)
          .append(" 23:59:59").append("','L') )");
      } 
    } 
    if (queryText != null && !"".equals(queryText))
      where.append(" and (").append(" dbms_lob.instr(po.forumContent,'").append(queryText).append("',1,1)>0 ").append(" or po.forumTitle like'%").append(queryText).append("%') "); 
    if (queryTitle != null && !"".equals(queryTitle))
      where.append(" and (po.forumTitle like'%").append(queryTitle).append("%') "); 
    if (queryMan != null && !"".equals(queryMan) && !"匿名".equals(queryMan))
      where.append(" and po.forumAuthor like '%" + queryMan + "%' and po.anonymous <>1 "); 
    if (queryMan != null && !"".equals(queryMan) && "匿名".equals(queryMan))
      where.append(" and (po.forumAuthor like '%" + queryMan + "%' or po.anonymous=1) "); 
    where.append(" and (").append(wherePara).append(")");
    String period = "";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    period = sdf.format(new Date());
    String _period = " and ((poo.createdEmp=" + curUserId + " or (poo.classOwnerId=" + curUserId + " or poo.classOwnerIds like '%*" + curUserId + "*%'))" + 
      " or (poo.fullDay=1 or (poo.fullDay!=1 and poo.startPeriod <= '" + period + "' and '" + period + "' <= poo.endPeriod))) ";
    where.append(_period);
    Page page = new Page();
    if (orderbyreply != null || orderbykit != null || orderbybtime != null) {
      page.init(" po.id,po.forumTitle,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.id,po.forumClass.classHasJunior,po.forumClass.classOwnerIds,po.forumClass.classParentName,po.anonymous,po.forumClass.classOwnerIds,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint,po.forumModifyTime,po.forumTopOrder", 

          
          " com.js.oa.bbs.po.ForumPO po join po.forumClass poo ", where + 
          " and po.domainId=" + domainId + " and poo.relProjectId =0  order by " + orderby + "  po.forumType desc ,  po.id desc ");
    } else {
      page.init(" po.id,po.forumTitle,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.id,po.forumClass.classHasJunior,po.forumClass.classOwnerIds,po.forumClass.classParentName,po.anonymous,po.forumClass.classOwnerIds,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint,po.forumModifyTime,po.forumTopOrder", 

          
          " com.js.oa.bbs.po.ForumPO po join po.forumClass poo ", where + 
          " and po.domainId=" + domainId + " and poo.relProjectId =0  order by po.newretime desc ," + orderby + "  po.forumType desc ,  po.id desc ");
    } 
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    Vector<String> vector = new Vector();
    vector.add((new StringBuilder(String.valueOf(page.getRecordCount()))).toString());
    vector.add(list);
    return vector;
  }
  
  private Vector getFollowPage(String forumId, Integer offset, String sign, String examinYesOrNot) {
    String andWhere = "";
    String order = " order by po.id , po.forumIssueTime ";
    if (examinYesOrNot != null && examinYesOrNot.equals("n")) {
      andWhere = String.valueOf(andWhere) + ") and( po.examinNum=1)";
    } else {
      andWhere = String.valueOf(andWhere) + " ) and  (po.examinNum=2 or po.examinNum=3 or po.examinNum is null )";
    } 
    if ("1".equals(SystemCommon.getForumOrder()))
      order = " order by po.forumTopicId, po.id desc, po.forumIssueTime desc "; 
    int pageSize = 20;
    int currentPage = offset.intValue() / pageSize + 1;
    Page page = new Page(" po.forumTitle,emppo.empName,po.forumAuthorOrg, po.forumIssueTime,po.forumAuthorIp,emppo.empName, po.id, po.forumSign ,po.forumAttachName, po.forumAttachSave,po.forumClass.id ,stat.forumNum, emppo.empSex,po.forumAuthorId,po.anonymous, po.forumClass.classOwnerIds,emppo.userAccounts,emppo.imId,po.forumNotUpd,emppo.empDuty,po.forumModifyTime", 

        
        "  com.js.oa.bbs.po.ForumPO po join po.forumClass  , com.js.oa.bbs.po.PersonalStatPO stat,com.js.system.vo.usermanager.EmployeeVO emppo", " where ( (po.id=" + forumId + 
        " or po.forumTopicId =" + forumId + andWhere + " ) and po.forumAuthorId = stat.empId and emppo.empId=po.forumAuthorId" + 
        order);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list1 = page.getResultList();
    list1 = (new ForumBD()).loadContent(list1);
    Vector<List> vec = new Vector();
    vec.add(list1);
    vec.add((new StringBuilder(String.valueOf(page.getRecordCount()))).toString());
    vec.add((new StringBuilder(String.valueOf(pageSize))).toString());
    int maxPage = page.getRecordCount() - page.getRecordCount() % pageSize;
    vec.add(maxPage);
    return vec;
  }
  
  private void shenchalist(HttpServletRequest httpServletRequest, String classId) throws NumberFormatException, Exception {
    String userIdStr = httpServletRequest.getSession(true).getAttribute("userId").toString();
    Long domainId = (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true).getAttribute("domainId").toString());
    int pageSize = 20;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer where = new StringBuffer(" where po.forumTopicId=0 and poo.id =" + classId);
    where.append(" and ( (po.examinNum=1").append(" and po.forumClass.classOwnerIds like '%*" + userIdStr + "*%' ) ").append(
        " or ( po.examinNum<>1  and po.forumClass.classOwnerIds like '%*" + userIdStr + "*%'" + 
        " and ( select count(*) from com.js.oa.bbs.po.ForumPO xpo where xpo.examinNum=1 and xpo.forumTopicId=po.id )>0)) ");
    Page page = new Page(" po.id,po.forumTitle,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.id,po.forumClass.classHasJunior,po.forumClass.classOwnerIds,po.forumClass.classParentName,po.anonymous,po.forumClass.classOwnerIds,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,poo.banPrint,po.forumModifyTime", 

        
        " com.js.oa.bbs.po.ForumPO po join po.forumClass poo ", where.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,classId");
  }
  
  private Page getMorePage(String whereString) throws Exception {
    Page page = new Page("po.id,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.id,po.forumClass.classUserId,po.forumClass.classUserOrg,po.forumClass.classUserGroup,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.classOwnerIds,po.anonymous,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,po.forumModifyTime,po.forumTopOrder,po.forumClass.banPrint,po.forumClass.className,po.forumTitle,po.forumClass.fullDay,po.forumClass.startPeriod,po.forumClass.endPeriod ", 






        
        "com.js.oa.bbs.po.ForumPO po ", 
        whereString);
    return page;
  }
}
