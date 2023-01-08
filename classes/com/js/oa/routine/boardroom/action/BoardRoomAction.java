package com.js.oa.routine.boardroom.action;

import com.js.message.RealTimeUtil;
import com.js.oa.form.BoardRoomWorkFlow;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.routine.boardroom.po.BdroomAppAccessoryPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO;
import com.js.oa.routine.boardroom.po.BoardRoomDiscussionPO;
import com.js.oa.routine.boardroom.po.BoardRoomPO;
import com.js.oa.routine.boardroom.po.BoardroomExecuteStatusPO;
import com.js.oa.routine.boardroom.po.BoardroomPersons;
import com.js.oa.routine.boardroom.service.BoardRoomBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.page.sql.Page;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import rtx.RTXSvrApi;

public class BoardRoomAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    String action = httpServletRequest.getParameter("action");
    BoardRoomActionForm boardRoomActionForm = 
      (BoardRoomActionForm)actionForm;
    httpServletRequest.getRequestURI();
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String userName = session.getAttribute("userName").toString();
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String orgName = session.getAttribute("orgName").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    httpServletRequest.setAttribute("send", httpServletRequest.getParameter("send"));
    if (!hasAddRight(httpServletRequest))
      httpServletRequest.setAttribute("noAddRight", "1"); 
    String scopeWhere = "";
    if ("boardRoomView".equals(action)) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      String sdate = httpServletRequest.getParameter("searchStartDate");
      if (sdate != null) {
        sdate = sdate.replaceAll("/", "-");
      } else {
        sdate = sf.format(new Date());
      } 
      if ("weeklyView".equals(httpServletRequest.getParameter("type"))) {
        httpServletRequest.setAttribute("day0", getWeek(sdate, "1"));
        httpServletRequest.setAttribute("day1", getWeek(sdate, "2"));
        httpServletRequest.setAttribute("day2", getWeek(sdate, "3"));
        httpServletRequest.setAttribute("day3", getWeek(sdate, "4"));
        httpServletRequest.setAttribute("day4", getWeek(sdate, "5"));
        httpServletRequest.setAttribute("day5", getWeek(sdate, "6"));
        httpServletRequest.setAttribute("day6", getWeek(sdate, "0"));
        Calendar prev = Calendar.getInstance();
        prev.setTime(strToDate(sdate));
        prev.add(5, -7);
        String y = String.valueOf(prev.get(1));
        String m = String.valueOf(prev.get(2) + 1);
        String d = String.valueOf(prev.get(5));
        httpServletRequest.setAttribute("prevWeek", String.valueOf(y) + "/" + m + "/" + d);
        Calendar next = Calendar.getInstance();
        next.setTime(strToDate(sdate));
        next.add(5, 7);
        y = String.valueOf(next.get(1));
        m = String.valueOf(next.get(2) + 1);
        d = String.valueOf(next.get(5));
        httpServletRequest.setAttribute("nextWeek", String.valueOf(y) + "/" + m + "/" + d);
      } else {
        Calendar prev = Calendar.getInstance();
        prev.setTime(strToDate(sdate));
        prev.add(5, -1);
        String y = String.valueOf(prev.get(1));
        String m = String.valueOf(prev.get(2) + 1);
        String d = String.valueOf(prev.get(5));
        httpServletRequest.setAttribute("prevDay", String.valueOf(y) + "/" + m + "/" + d);
        Calendar next = Calendar.getInstance();
        next.setTime(strToDate(sdate));
        next.add(5, 1);
        y = String.valueOf(next.get(1));
        m = String.valueOf(next.get(2) + 1);
        d = String.valueOf(next.get(5));
        httpServletRequest.setAttribute("nextDay", String.valueOf(y) + "/" + m + "/" + d);
        SimpleDateFormat sf0 = new SimpleDateFormat("yyyy-M-d");
        httpServletRequest.setAttribute("currentDay", sf0.format(new Date()).toString().replaceAll("-", "/"));
      } 
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + 
        " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      if ((new ManagerService()).hasRightTypeName((String)userID, "会议管理", "审核")) {
        scopeWhere = " 1=1 ";
      } else {
        scopeWhere = " 1<>1 ";
      } 
      String auditingBoardRoomIds = boardRoomBD.maintenanceBoardRoom(
          scopeWhere);
      httpServletRequest.setAttribute("auditingBoardRoomIds", 
          auditingBoardRoomIds);
      boardRoomList(httpServletRequest, maintenanceBoardRoomIds);
      if (httpServletRequest.getParameter("init") != null && 
        "0".equals(httpServletRequest.getParameter("init"))) {
        if ("weeklyView".equals(httpServletRequest.getParameter("type")))
          return actionMapping.findForward("weeklyView"); 
        return actionMapping.findForward("init");
      } 
      return actionMapping.findForward("boardroomList");
    } 
    if ("discussion".equals(action)) {
      String[] users = boardRoomBD.boradroomUser(httpServletRequest.getParameter("boardroomApplyId"));
      httpServletRequest.setAttribute("zhuchiren", users[0]);
      httpServletRequest.setAttribute("canyuren", String.valueOf(users[0]) + users[1] + users[2] + users[3] + users[4] + users[5]);
      if (httpServletRequest.getParameter("pagenum") == null) {
        discussionList(httpServletRequest, SystemCommon.getMeetingNum(), "-1");
        httpServletRequest.setAttribute("pageParameters", "action,boardroomApplyId");
        return actionMapping.findForward("discussion");
      } 
      discussionList(httpServletRequest, SystemCommon.getMeetingListNum(), httpServletRequest.getParameter("pagenum"));
      httpServletRequest.setAttribute("pageParameters", "action,boardroomApplyId,pagenum");
      return actionMapping.findForward("pagenum");
    } 
    if ("addPage".equals(action))
      return actionMapping.findForward("addPage"); 
    if ("addDiscussion".equals(action)) {
      BoardRoomDiscussionPO po = new BoardRoomDiscussionPO();
      po.setApplyId(Long.valueOf(httpServletRequest.getParameter("boardroomApplyId")));
      po.setDiscussionContext(httpServletRequest.getParameter("context"));
      po.setDiscussionDate(new Date());
      po.setDiscussionEmpId(userID);
      po.setDiscussionEmpName(userName);
      po.setFlag(Integer.valueOf(0));
      po.setOrgId(orgId);
      boardRoomBD.saveDiscussion(po);
      String url = "/jsoa/BoardRoomAction.do?" + httpServletRequest.getQueryString().replace("action=addDiscussion", "action=discussion");
      SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String[] users = boardRoomBD.boradroomUser(httpServletRequest.getParameter("boardroomApplyId"));
      if (!"".equals(String.valueOf(users[0]) + users[1] + users[3] + users[5]) && !"null".equalsIgnoreCase(String.valueOf(users[0]) + users[1] + users[3] + users[5])) {
        String chuxiUser = ",";
        String[] chuxi = (String.valueOf(users[0]) + users[1] + users[3] + users[5]).replaceAll("\\$\\$", ",").replaceAll("null", ",").replaceAll("\\$", "").split(",");
        for (int i = 0; i < chuxi.length; i++) {
          if (!chuxiUser.contains("," + chuxi[i] + ",") && !userID.equals(chuxi[i]))
            chuxiUser = String.valueOf(chuxiUser) + chuxi[i] + ","; 
        } 
        RemindUtil.sendMessageToUsers2("您出席的 " + users[6] + " 有新消息！", url, chuxiUser.substring(1), "Meeting", new Date(), 
            dateTimeFormat.parse("2050-01-01 00:00:00"), userName, Long.valueOf(0L), 1);
      } 
      if (!"".equals(users[4]) && !"null".equalsIgnoreCase(users[4])) {
        String liexiUser = ",";
        String[] liexi = users[4].replaceAll("\\$\\$", ",").replaceAll("null", ",").replaceAll("\\$", "").split(",");
        for (int i = 0; i < liexi.length; i++) {
          if (!liexiUser.contains("," + liexi[i] + ",") && !userID.equals(liexi[i]))
            liexiUser = String.valueOf(liexiUser) + liexi[i] + ","; 
        } 
        RemindUtil.sendMessageToUsers2("您列席的 " + users[6] + " 有新消息！", url, liexiUser.substring(1), "Meeting", new Date(), 
            dateTimeFormat.parse("2050-01-01 00:00:00"), userName, Long.valueOf(0L), 1);
      } 
      httpServletRequest.setAttribute("flag", "discussion");
      return actionMapping.findForward("close");
    } 
    if ("modifyBoardroomApply".equals(action)) {
      (new BoardRoomWorkFlow()).update(httpServletRequest);
      httpServletRequest.setAttribute("close", "1");
      action = "selectBoardroomApplyModify";
      if ("1".equals(httpServletRequest.getParameter("realTime"))) {
        Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
              "boardroomId"));
        RealTimeUtil util = new RealTimeUtil();
        String receivers = String.valueOf(httpServletRequest.getParameter("emcee")) + 
          httpServletRequest.getParameter(
            "attendeeEmpId") + (
          (httpServletRequest.getParameter(
            "nonvotingEmpId") == null) ? 
          "" : 
          httpServletRequest.getParameter(
            "nonvotingEmpId")) + (
          (httpServletRequest.getParameter(
            "attendeeLeaderId") == null) ? 
          "" : 
          httpServletRequest.getParameter(
            "attendeeLeaderId"));
        Date destineDate = new Date(httpServletRequest.getParameter("destineDate"));
        SimpleDateFormat fomatter = new SimpleDateFormat("yy年MM月dd日");
        SimpleDateFormat fomatter1 = new SimpleDateFormat(
            "yy年MM月dd日   HH:mm分");
        int destineDateBeginTime = Integer.parseInt(httpServletRequest
            .getParameter("destineDateBeginTime"));
        int destineDateEndTime = Integer.parseInt(httpServletRequest
            .getParameter(
              "destineDateEndTime"));
        String motif = httpServletRequest.getParameter("motif");
        String boardRoomName = boardRoomBD.selectBoardroom(boardroomId)
          .getName();
        String message = String.valueOf(fomatter.format(destineDate)) + (
          destineDateBeginTime / 3600) + ":" + (
          destineDateBeginTime % 3600 / 60) + "-" + (
          destineDateEndTime / 3600) + ":" + (
          destineDateEndTime % 3600 / 60) + "在" + 
          boardRoomName + 
          "召开" + motif + 
          "                                " + 
          fomatter1.format(new Date());
        util.sendNotify((new UserBD()).getUserAccountByIds(receivers), 
            "会议通知", message, "0", 
            "0");
      } 
    } 
    if ("addBoardroomView".equals(action))
      return actionMapping.findForward("boardroomAdd"); 
    if ("addBoardroom".equals(action)) {
      BoardRoomPO boardroomPO = (BoardRoomPO)FillBean.transformOneToOne(
          boardRoomActionForm, BoardRoomPO.class);
      if (boardRoomActionForm.getPrincipal() != null && 
        !"".equals(boardRoomActionForm.getPrincipal()) && 
        !"null".equals(boardRoomActionForm.getPrincipal())) {
        boardroomPO.setLimit(new Integer(1));
        ConversionString conversionString = new ConversionString(
            boardRoomActionForm.getPrincipal());
        boardroomPO.setScopeEmp(conversionString.getUserString());
        boardroomPO.setScopeOrg(conversionString.getOrgString());
        boardroomPO.setScopeGroup(conversionString.getGroupString());
        boardroomPO.setScope(boardRoomActionForm.getPrincipalName());
      } else {
        boardroomPO.setLimit(new Integer(0));
        boardroomPO.setScopeEmp("");
        boardroomPO.setScopeOrg("");
        boardroomPO.setScopeGroup("");
        boardroomPO.setScope("");
      } 
      boardroomPO.setCreatedEmp(userID);
      boardroomPO.setCreatedOrg(orgId);
      boardroomPO.setDomainId(domainId);
      boardroomPO.setBoardRoomLayout((new StringBuilder(String.valueOf(httpServletRequest.getParameter("boardRoomLayout")))).toString());
      boardroomPO.setCircleType((new StringBuilder(String.valueOf(httpServletRequest.getParameter("circleType")))).toString());
      boardroomPO.setReserveType((new StringBuilder(String.valueOf(httpServletRequest.getParameter("reserveType")))).toString());
      boardroomPO.setChargeType((new StringBuilder(String.valueOf(httpServletRequest.getParameter("chargeType")))).toString());
      boardroomPO.setUseScope(httpServletRequest.getParameter("useScope"));
      boardroomPO.setUseScopeId(httpServletRequest.getParameter("useScopeId"));
      String[] equName = httpServletRequest.getParameterValues("equname");
      String[] equDescribe = httpServletRequest.getParameterValues(
          "equDescribe");
      Object[] para = { equName, equDescribe };
      boolean result = boardRoomBD.addBoardroom(boardroomPO, para);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        boardRoomActionForm.reset(actionMapping, httpServletRequest);
        boardRoomActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("boardroomAdd");
      } 
      if ("saveAndExit".equals(saveType)) {
        boardRoomActionForm.reset(actionMapping, httpServletRequest);
        boardRoomActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("boardroomAdd");
      } 
    } 
    if ("deleteBoardroom".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
            "boardroomId"));
      scopeWhere = scopeWhere(httpServletRequest, "会议管理", "维护");
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(
          scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", 
          maintenanceBoardRoomIds);
      if ((new ManagerService()).hasRightTypeName((String)userID, "会议管理", "审核")) {
        scopeWhere = " 1=1 ";
      } else {
        scopeWhere = " 1<>1 ";
      } 
      String auditingBoardRoomIds = boardRoomBD.maintenanceBoardRoom(
          scopeWhere);
      httpServletRequest.setAttribute("auditingBoardRoomIds", 
          auditingBoardRoomIds);
      ProcessBD procbd = new ProcessBD();
      List list = new ArrayList();
      Object tmpl = procbd.getUserProcess(session.getAttribute("userId")
          .toString(), 
          session
          .getAttribute("orgIdString")
          .toString(), "15");
      if (tmpl != null)
        list = (List)tmpl; 
      httpServletRequest.setAttribute("voitureFlowlist", list);
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = boardRoomBD.deleteBoardroom(boardroomId);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        boardRoomList(httpServletRequest, offsetCopy, 
            maintenanceBoardRoomIds);
      } else {
        boardRoomList(httpServletRequest, maintenanceBoardRoomIds);
      } 
      boardRoomActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("boardroomList");
    } 
    if ("deleteBatchBoardroom".equals(action)) {
      String boardroomIds = "";
      boolean result = true;
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids"))) {
        boardroomIds = httpServletRequest.getParameter("ids");
        result = boardRoomBD.deleteBatchBoardroom(boardroomIds);
      } 
      scopeWhere = scopeWhere(httpServletRequest, "会议管理", "维护");
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(
          scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", 
          maintenanceBoardRoomIds);
      if ((new ManagerService()).hasRightTypeName((String)userID, "会议管理", "审核")) {
        scopeWhere = " 1=1 ";
      } else {
        scopeWhere = " 1<>1 ";
      } 
      String auditingBoardRoomIds = boardRoomBD.maintenanceBoardRoom(
          scopeWhere);
      httpServletRequest.setAttribute("auditingBoardRoomIds", 
          auditingBoardRoomIds);
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        boardRoomList(httpServletRequest, offsetCopy, 
            maintenanceBoardRoomIds);
      } else {
        boardRoomList(httpServletRequest, maintenanceBoardRoomIds);
      } 
      boardRoomActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("boardroomList");
    } 
    if ("selectBoardroomView".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter("boardroomId"));
      String type = httpServletRequest.getParameter("type");
      BoardRoomPO boardroomPO = boardRoomBD.selectBoardroom(boardroomId);
      boardRoomActionForm.setName(boardroomPO.getName());
      boardRoomActionForm.setDepict(boardroomPO.getDepict());
      boardRoomActionForm.setLocation(boardroomPO.getLocation());
      boardRoomActionForm.setCapacitance(boardroomPO.getCapacitance());
      boardRoomActionForm.setBoardroomType(boardroomPO.getBoardroomType());
      boardRoomActionForm.setCost(boardroomPO.getCost());
      boardRoomActionForm.setEmphasis(boardroomPO.getEmphasis());
      boardRoomActionForm.setCreatedEmp(boardroomPO.getCreatedEmp());
      boardRoomActionForm.setCreatedOrg(boardroomPO.getCreatedOrg());
      boardRoomActionForm.setControlNumber(boardroomPO.getControlNumber());
      boardRoomActionForm.setDoorNumber(boardroomPO.getDoorNumber());
      boardRoomActionForm.setIsVideo(boardroomPO.getIsVideo());
      boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
      boardRoomActionForm.setWorkaddress(boardroomPO.getWorkaddress());
      boardRoomActionForm.setManageOrg(boardroomPO.getManageOrg());
      boardRoomActionForm.setManageOrgName(boardroomPO.getManageOrgName());
      boardRoomActionForm.setApplyPhone(boardroomPO.getApplyPhone());
      httpServletRequest.setAttribute("isVideo", boardroomPO.getIsVideo());
      boardRoomActionForm.setBoardroomOrder(boardroomPO.getBoardroomOrder());
      httpServletRequest.setAttribute("boardRoomLayout", boardroomPO.getBoardRoomLayout());
      httpServletRequest.setAttribute("reserveType", boardroomPO.getReserveType());
      httpServletRequest.setAttribute("circleType", boardroomPO.getCircleType());
      httpServletRequest.setAttribute("chargeType", boardroomPO.getChargeType());
      httpServletRequest.setAttribute("boardName", boardroomPO.getName());
      Set set = boardroomPO.getBoardRoomEquipment();
      httpServletRequest.setAttribute("bdRoomEqu", set);
      if (boardroomPO.getLimit().equals(new Integer(1))) {
        String userIds = boardroomPO.getScopeEmp();
        if (userIds == null || "null".equals(userIds))
          userIds = ""; 
        String orgIds = boardroomPO.getScopeOrg();
        if (orgIds == null || "null".equals(orgIds))
          orgIds = ""; 
        String groupIds = boardroomPO.getScopeGroup();
        if (groupIds == null || "null".equals(groupIds))
          groupIds = ""; 
        boardRoomActionForm.setPrincipal(String.valueOf(userIds) + orgIds + groupIds);
        boardRoomActionForm.setPrincipalName(boardroomPO.getScope());
      } 
      boardRoomActionForm.setRemark(boardroomPO.getRemark());
      httpServletRequest.setAttribute("limit", boardroomPO.getLimit());
      httpServletRequest.setAttribute("boardroomId", boardroomId);
      if ("view".equals(type))
        return actionMapping.findForward("boardroomView"); 
      if ("modi".equals(type)) {
        httpServletRequest.setAttribute("useScope", boardroomPO.getUseScope());
        httpServletRequest.setAttribute("useScopeId", boardroomPO.getUseScopeId());
        return actionMapping.findForward("boardroomModi");
      } 
      if ("detail".equals(type))
        return actionMapping.findForward("boardroomDetail"); 
    } 
    if ("modiBoardroom".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter("boardroomId"));
      BoardRoomPO boardroomPO = (BoardRoomPO)FillBean.transformOneToOne(
          boardRoomActionForm, BoardRoomPO.class);
      boardroomPO.setBoardroomId(boardroomId);
      if (boardRoomActionForm.getPrincipal() != null && 
        !"".equals(boardRoomActionForm.getPrincipal()) && 
        !"null".equals(boardRoomActionForm.getPrincipal())) {
        boardroomPO.setLimit(new Integer(1));
        ConversionString conversionString = new ConversionString(
            boardRoomActionForm.getPrincipal());
        boardroomPO.setScopeEmp(conversionString.getUserString());
        boardroomPO.setScopeOrg(conversionString.getOrgString());
        boardroomPO.setScopeGroup(conversionString.getGroupString());
        boardroomPO.setScope(boardRoomActionForm.getPrincipalName());
        boardroomPO.setDoorNumber(boardRoomActionForm.getDoorNumber());
      } else {
        boardroomPO.setLimit(new Integer(0));
        boardroomPO.setScopeEmp("");
        boardroomPO.setScopeOrg("");
        boardroomPO.setScopeGroup("");
        boardroomPO.setScope("");
      } 
      boardroomPO.setCircleType((new StringBuilder(String.valueOf(httpServletRequest.getParameter("circleType")))).toString());
      boardroomPO.setReserveType((new StringBuilder(String.valueOf(httpServletRequest.getParameter("reserveType")))).toString());
      boardroomPO.setChargeType((new StringBuilder(String.valueOf(httpServletRequest.getParameter("chargeType")))).toString());
      boardroomPO.setBoardRoomLayout(httpServletRequest.getParameter("boardRoomLayout"));
      boardroomPO.setUseScope(httpServletRequest.getParameter("useScope"));
      boardroomPO.setUseScopeId(httpServletRequest.getParameter("useScopeId"));
      String[] equName = httpServletRequest.getParameterValues("equname");
      String[] equDescribe = httpServletRequest.getParameterValues("equDescribe");
      Object[] para = { equName, equDescribe };
      boolean result = boardRoomBD.modiBoardroom(boardroomPO, para);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        boardRoomActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("boardroomId", boardroomId);
        httpServletRequest.setAttribute("limit", boardroomPO.getLimit());
        return actionMapping.findForward("boardroomModi");
      } 
    } 
    if ("addBoardroomApplyView".equals(action)) {
      httpServletRequest.setAttribute("remindField", "$motif$");
      String[] phones = StaticParam.getPhoneByUserId((String)userID);
      String phone = phones[0];
      if ("".equals(phone))
        phone = phones[1]; 
      boardRoomActionForm.setLinkTelephone(phone);
      BoardRoomPO boardroomPO = new BoardRoomPO();
      Long boardroomId = new Long(-1L);
      boolean flag = false;
      if (httpServletRequest.getParameter("boardroomId") != null && 
        !"".equals(httpServletRequest.getParameter("boardroomId")) && 
        !"null".equals(httpServletRequest.getParameter("boardroomId"))) {
        boardroomId = Long.valueOf(httpServletRequest.getParameter("boardroomId"));
        boardroomPO = boardRoomBD.selectBoardroom(boardroomId);
        flag = true;
      } else {
        httpServletRequest.setAttribute("isVideo", "0");
      } 
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      if ((new ManagerService()).hasRightTypeName((String)userID, "会议管理", "审核")) {
        scopeWhere = " 1=1 ";
      } else {
        scopeWhere = " 1<>1 ";
      } 
      String auditingBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("auditingBoardRoomIds", auditingBoardRoomIds);
      boardRoomList(httpServletRequest);
      httpServletRequest.setAttribute("bdequSet", boardroomPO.getBoardRoomEquipment());
      httpServletRequest.setAttribute("boardroomName", boardroomPO.getName());
      httpServletRequest.setAttribute("boardRoomLayout", boardroomPO.getBoardRoomLayout());
      httpServletRequest.setAttribute("circleType", boardroomPO.getCircleType());
      httpServletRequest.setAttribute("reserveType", boardroomPO.getReserveType());
      if (httpServletRequest.getParameter("processId") == null) {
        ProcessBD procbd = new ProcessBD();
        List<Object[]> list = new ArrayList();
        Object tmpl = procbd.getUserProcess(session.getAttribute("userId").toString(), session.getAttribute("orgIdString").toString(), "15");
        if (tmpl != null)
          list = (List)tmpl; 
        if (list != null && list.size() == 1) {
          Object[] rfObj = list.get(0);
          Object object1 = rfObj[2];
          Object object2 = rfObj[3];
          Object object3 = rfObj[5];
          Object object4 = rfObj[4];
          Object object5 = (rfObj[6] == null) ? "" : rfObj[6];
          String linkValue = "processType=" + object3 + 
            "&remindField=" + object5 + 
            "&processId=" + object1 + "&tableId=" + 
            object4 + "&processName=" + object2;
          if (flag) {
            boardRoomActionForm.setName(boardroomPO.getName());
            boardRoomActionForm.setEmphasis(boardroomPO.getEmphasis());
            boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
            httpServletRequest.setAttribute("isVideo", boardroomPO.getIsVideo());
            httpServletRequest.setAttribute("maxNumber", boardroomPO.getMaxNumber());
            httpServletRequest.setAttribute("boardroomId", boardroomId);
          } 
          List list2 = boardRoomBD.selectBoardroomApplyType(domainId);
          httpServletRequest.setAttribute("boardroomApplyTypeList", list2);
          httpServletRequest.setAttribute("emphasis", boardroomPO.getEmphasis());
          return new ActionForward(
              "/BoardRoomAction.do?action=addBoardroomApplyView&boardroomId=" + 
              httpServletRequest.getParameter("boardroomId") + 
              "&boardroomName=" + httpServletRequest.getParameter("boardroomName") + 
              "&" + linkValue + "&moduleId=15");
        } 
        if (list != null && list.size() > 1) {
          httpServletRequest.setAttribute("boradRoomApplyFlowlist", list);
          return actionMapping.findForward("selectWorkFlow");
        } 
        if (flag) {
          boardRoomActionForm.setName(boardroomPO.getName());
          boardRoomActionForm.setEmphasis(boardroomPO.getEmphasis());
          boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
          httpServletRequest.setAttribute("isVideo", boardroomPO.getIsVideo());
          httpServletRequest.setAttribute("maxNumber", boardroomPO.getMaxNumber());
          httpServletRequest.setAttribute("boardroomId", boardroomId);
        } 
        List list1 = boardRoomBD.selectBoardroomApplyType(domainId);
        httpServletRequest.setAttribute("boardroomApplyTypeList", list1);
        httpServletRequest.setAttribute("emphasis", boardroomPO.getEmphasis());
        httpServletRequest.setAttribute("noWorkFlow", "1");
        return actionMapping.findForward("boardroomApplyAdd");
      } 
      if (flag) {
        boardRoomActionForm.setName(boardroomPO.getName());
        boardRoomActionForm.setEmphasis(boardroomPO.getEmphasis());
        boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
        httpServletRequest.setAttribute("isVideo", boardroomPO.getIsVideo());
        httpServletRequest.setAttribute("maxNumber", boardroomPO.getMaxNumber());
        httpServletRequest.setAttribute("boardroomId", boardroomId);
      } 
      List<Object[]> boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      if ("haier".equals(SystemCommon.getCustomerName())) {
        List<Object[]> newBoardroomApplyType = new ArrayList();
        for (int i = 0; i < boardroomApplyType.size(); i++) {
          Object[] obj = boardroomApplyType.get(i);
          if (obj[4] != null && !"".equals(obj[4]) && !"null".equals(obj[4]) && 
            obj[4].toString().indexOf(String.valueOf(userID)) > -1)
            newBoardroomApplyType.add(obj); 
          if (obj[5] != null && !"".equals(obj[5]) && !"null".equals(obj[4])) {
            String usedScopeOrgId = obj[5].toString();
            usedScopeOrgId = usedScopeOrgId.replace("**", ",").replace("*", "");
            String[] split = usedScopeOrgId.split(",");
            for (int j = 0; j < split.length; j++) {
              if (orgIdString.indexOf(split[j]) > -1) {
                newBoardroomApplyType.add(obj);
                break;
              } 
            } 
          } 
        } 
        httpServletRequest.setAttribute("boardroomApplyTypeList", newBoardroomApplyType);
      } else {
        httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      } 
      httpServletRequest.setAttribute("emphasis", boardroomPO.getEmphasis());
      return actionMapping.findForward("boardroomApplyAdd");
    } 
    if ("changeBoardRoom".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter("boardroomId"));
      BoardRoomPO boardroomPO = boardRoomBD.selectBoardroom(boardroomId);
      boardRoomList(httpServletRequest);
      httpServletRequest.setAttribute("bdequSet", boardroomPO.getBoardRoomEquipment());
      httpServletRequest.setAttribute("boardRoomLayout", boardroomPO.getBoardRoomLayout());
      boardRoomActionForm.setName(boardroomPO.getName());
      boardRoomActionForm.setEmphasis(boardroomPO.getEmphasis());
      boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
      httpServletRequest.setAttribute("isVideo", boardroomPO.getIsVideo());
      httpServletRequest.setAttribute("maxNumber", boardroomPO.getMaxNumber());
      httpServletRequest.setAttribute("boardroomId", boardroomId);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      httpServletRequest.setAttribute("emphasis", boardroomPO.getEmphasis());
      return actionMapping.findForward("boardroomApplyAdd");
    } 
    if ("openSummary".equals(action)) {
      List assList = new ArrayList();
      long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId")).longValue();
      String type = httpServletRequest.getParameter("type");
      BoardRoomApplyPO boardRoomApplyPO = new BoardRoomApplyPO();
      boardRoomApplyPO = boardRoomBD.getBoardRoomApplyPObyId(Long.valueOf(boardroomApplyId));
      assList = boardRoomBD.getboardRoomApplyAssPOList(Long.valueOf(boardroomApplyId));
      httpServletRequest.setAttribute("boardRoomApplyPO", boardRoomApplyPO);
      httpServletRequest.setAttribute("assList", assList);
      httpServletRequest.setAttribute("type", type);
      return actionMapping.findForward("openSummary");
    } 
    if ("addSummary".equals(action)) {
      long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId")).longValue();
      String[] saveName = httpServletRequest.getParameterValues("summarySaveName");
      String[] fileName = httpServletRequest.getParameterValues("summaryName");
      String summary = httpServletRequest.getParameter("summary");
      boardRoomBD.addSummary(Long.valueOf(boardroomApplyId), summary, saveName, fileName);
      httpServletRequest.setAttribute("flag", "addSummary");
      return actionMapping.findForward("close");
    } 
    if ("addBoardroomApplyNew".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter("boardroomId"));
      Long boardroomApplyTypeId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyType"));
      boolean flag = true;
      if (flag) {
        Long applyId = (new BoardRoomWorkFlow()).save(httpServletRequest);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] users = boardRoomBD.boradroomUser((String)applyId);
        String url = "BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + applyId + 
          "&boardroomName=" + users[7] + "&type=view&executeStatus=false";
        if (!"".equals(String.valueOf(users[0]) + users[1] + users[3] + users[5]) && !"null".equalsIgnoreCase(String.valueOf(users[0]) + users[1] + users[3] + users[5])) {
          String chuxiUser = ",";
          String[] chuxi = (String.valueOf(users[0]) + users[1] + users[3] + users[5]).replaceAll("\\$\\$", ",").replaceAll("null", ",").replaceAll("\\$", "").split(",");
          for (int i = 0; i < chuxi.length; i++) {
            if (!chuxiUser.contains("," + chuxi[i] + ",") && !userID.equals(chuxi[i]))
              chuxiUser = String.valueOf(chuxiUser) + chuxi[i] + ","; 
          } 
          RemindUtil.sendMessageToUsers2("您出席的 " + users[6] + " 已经被安排！", url, chuxiUser.substring(1), "Meeting", new Date(), 
              dateTimeFormat.parse("2050-01-01 00:00:00"), userName, Long.valueOf(0L), 1);
        } 
        if (!"".equals(users[4]) && !"null".equalsIgnoreCase(users[4])) {
          String liexiUser = ",";
          String[] liexi = users[4].replaceAll("\\$\\$", ",").replaceAll("null", ",").replaceAll("\\$", "").split(",");
          for (int i = 0; i < liexi.length; i++) {
            if (!liexiUser.contains("," + liexi[i] + ",") && !userID.equals(liexi[i]))
              liexiUser = String.valueOf(liexiUser) + liexi[i] + ","; 
          } 
          RemindUtil.sendMessageToUsers2("您列席的 " + users[6] + " 已经被安排！", url, liexiUser.substring(1), "Meeting", new Date(), 
              dateTimeFormat.parse("2050-01-01 00:00:00"), userName, Long.valueOf(0L), 1);
        } 
        httpServletRequest.setAttribute("flag", "addBoardroomApplyNew");
      } 
      return actionMapping.findForward("close");
    } 
    if ("addBoardroomApply".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter("boardroomId"));
      Date destineDate = new Date(httpServletRequest.getParameter("destineDate"));
      Integer emphasis = Integer.valueOf(httpServletRequest.getParameter("emphasis"));
      String destineDateBeginTime = httpServletRequest.getParameter("destineDateBeginTime");
      String destineDateEndTime = httpServletRequest.getParameter("destineDateEndTime");
      BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)FillBean.transformOneToOne(
          boardRoomActionForm, BoardRoomApplyPO.class);
      boardroomApplyPO.setMotif(boardroomApplyPO.getMotif());
      if (emphasis.equals(new Integer(1))) {
        boardroomApplyPO.setStatus(new Integer(1));
      } else {
        boardroomApplyPO.setStatus(new Integer(0));
      } 
      ConversionString con = new ConversionString(boardRoomActionForm.getAttendeeEmpId());
      String userIdStr = String.valueOf(con.getUserIdString()) + ",";
      userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
      userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
      boardroomApplyPO.setAttendeeEmpId("$" + userIdStr.replace(',', '$'));
      boardroomApplyPO.setDestineDate(destineDate);
      boardroomApplyPO.setStartTime(destineDateBeginTime);
      boardroomApplyPO.setEndTime(destineDateEndTime);
      boardroomApplyPO.setDomainId(domainId);
      Long boardroomApplyTypeId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyType"));
      HashSet<BdroomAppAccessoryPO> Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues("boardroomFileName");
      String[] saveName = httpServletRequest.getParameterValues("boardroomSaveName");
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            BdroomAppAccessoryPO bdroomAppAccessoryPO = new BdroomAppAccessoryPO();
            bdroomAppAccessoryPO.setAccessoryname(fileName[i]);
            bdroomAppAccessoryPO.setAccessorysavename(saveName[i]);
            Accessory.add(bdroomAppAccessoryPO);
          } 
        }  
      boardroomApplyPO.setBdRoomAppAccessory(Accessory);
      String isImpropriateTime = boardRoomBD.isImpropriateTime(boardroomApplyPO, boardroomId);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      if ("0".equals(isImpropriateTime)) {
        Long result = boardRoomBD.addBoardroomApply(boardroomApplyPO, boardroomId, boardroomApplyTypeId);
        if (emphasis.equals(new Integer(1)))
          saveProcess(httpServletRequest, result.toString()); 
        String userId = "";
        BoardRoomPO brpo = boardRoomBD.selectBoardroom(boardroomId);
        if (emphasis.equals(new Integer(0)) && 
          boardroomApplyPO.getMsg().equals(new Integer(1))) {
          ModelSendMsg sendMsg = new ModelSendMsg();
          userId = String.valueOf(boardroomApplyPO.getStartTime()) + "-" + 
            boardroomApplyPO.getEndTime() + 
            " 在" + 
            brpo.getLocation() + 
            brpo.getName() + 
            "召开由" + boardroomApplyPO.getEmceeName() + 
            "主持的" + boardroomApplyPO.getMotif() + 
            " (" + boardroomApplyPO.getDestineDate() + 
            ")";
          boolean falg = sendMsg.sendSystemMessage("新会议通知到达", 
              boardroomApplyPO.getMotif(), userId, "", 
              httpServletRequest);
          if (falg) {
            System.out.println("会议管理 短信提醒发送成功！");
          } else {
            System.out.println("会议管理 短信提醒发送未成功！");
          } 
        } 
        if (result.intValue() == -1)
          return actionMapping.findForward("failure"); 
        if ("saveAndContinue".equals(saveType)) {
          boardRoomActionForm.reset(actionMapping, httpServletRequest);
          BoardRoomPO boardroomPO = boardRoomBD.selectBoardroom(
              boardroomId);
          boardRoomActionForm.setName(boardroomPO.getName());
          boardRoomActionForm.setEmphasis(boardroomPO.getEmphasis());
          boardRoomActionForm.setSaveType("saveAndContinue");
          httpServletRequest.setAttribute("boardroomId", boardroomId);
          return actionMapping.findForward("boardroomApplyAdd");
        } 
        if ("saveAndExit".equals(saveType)) {
          boardRoomActionForm.reset(actionMapping, httpServletRequest);
          boardRoomActionForm.setSaveType("saveAndExit");
          httpServletRequest.setAttribute("boardroomId", boardroomId);
          return actionMapping.findForward("boardroomApplyAdd");
        } 
      } else {
        boardRoomActionForm.setSaveType("isImpropriateTimeNoSave");
        httpServletRequest.setAttribute("boardroomId", boardroomId);
        return actionMapping.findForward("boardroomApplyAdd");
      } 
    } 
    if ("deleteBoardroomApply".equals(action)) {
      boolean result = true;
      if (httpServletRequest.getParameter("boardroomApplyId") != null) {
        String meetingTimeId = httpServletRequest.getParameter("meetingTimeId");
        Long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId"));
        result = boardRoomBD.deleteBoardroomApply(boardroomApplyId, meetingTimeId);
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      boardRoomApplyList(httpServletRequest);
      return actionMapping.findForward("boardroomApplyList");
    } 
    if ("cancelBoardroomApply".equals(action)) {
      String gzw = httpServletRequest.getParameter("gzw");
      boolean result = true;
      if (httpServletRequest.getParameter("boardroomApplyId") != null) {
        Long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId"));
        result = boardRoomBD.deleteBoardroomApply(boardroomApplyId);
      } 
      if (httpServletRequest.getParameter("flag") != null) {
        if (!StringUtils.isEmpty(gzw) && "1".equals(gzw)) {
          httpServletResponse.getWriter().write("<script>window.close();opener.location.reload();</script>");
          return null;
        } 
        return actionMapping.findForward("close");
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      boardroomUseList(httpServletRequest);
      return actionMapping.findForward("boardroomUseList");
    } 
    if ("lookStateBoardroomApply".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
            "boardroomId"));
      List lookStateBoardroomApplyList = boardRoomBD
        .lookStateBoardroomApply(
          boardroomId);
      httpServletRequest.setAttribute("lookStateBoardroomApplyList", 
          lookStateBoardroomApplyList);
      long currentDate = System.currentTimeMillis();
      long[] weekDay = new long[7];
      GregorianCalendar calendar = new GregorianCalendar(Locale.CHINESE);
      calendar.setTimeInMillis(currentDate);
      if (calendar.get(7) == 1) {
        for (int i = 0; i < 7; i++)
          weekDay[i] = calendar.getTimeInMillis() - ((
            6 - i) * 3600 * 24 * 1000); 
      } else {
        int i;
        for (i = 1; i < calendar.get(7); i++)
          weekDay[i - 
              1] = calendar.getTimeInMillis() - ((
            calendar.get(7) - i + 1) * 
            3600 * 24 * 1000); 
        for (i = calendar.get(7); i < 8; i++)
          weekDay[i - 
              1] = calendar.getTimeInMillis() + ((
            i - calendar.get(7) + 1) * 
            3600 * 24 * 1000); 
      } 
      httpServletRequest.setAttribute("weekDay", weekDay);
      return actionMapping.findForward("boardroomApplyLookState");
    } 
    if ("manageBoardroomApplyView".equals(action)) {
      boardRoomApplyList(httpServletRequest);
      return actionMapping.findForward("boardroomApplyList");
    } 
    if ("useBoardroomView".equals(action)) {
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + 
        " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      boardroomUseList(httpServletRequest);
      if (httpServletRequest.getParameter("flag") == null)
        return actionMapping.findForward("boardroomUseList_last"); 
      return actionMapping.findForward("boardroomUseList");
    } 
    if ("boardroomRegularView".equals(action)) {
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + 
        " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      boardroomRegularList(httpServletRequest);
      return actionMapping.findForward("boardroomRegularList");
    } 
    if ("deleteRegularApply".equals(action)) {
      String applyId = httpServletRequest.getParameter("boardroomApplyId");
      boardRoomBD.deleteRegularApply(applyId);
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + 
        " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      boardroomRegularList(httpServletRequest);
      return actionMapping.findForward("boardroomRegularList");
    } 
    if ("selectBoardroomApplyView".equals(action) || 
      "selectBoardroomApplyModify".equals(action)) {
      String type = httpServletRequest.getParameter("type");
      httpServletRequest.setAttribute("remindField", "$motif$");
      Long boardroomApplyId = null;
      if (httpServletRequest.getParameter("boardroomApplyId") != null) {
        boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId"));
      } else {
        boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("record"));
      } 
      String boardroomName = httpServletRequest.getParameter("boardroomName");
      Map map = boardRoomBD.selectBoardroomApply(boardroomApplyId);
      BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)map.get("boardroomApplyPO");
      if (boardroomApplyPO == null) {
        httpServletRequest.setAttribute("errorType", "0");
        return actionMapping.findForward("closeWindow");
      } 
      List accessoryList = (List)map.get("accessory");
      boardRoomActionForm.setDepict(boardroomApplyPO.getDepict());
      boardRoomActionForm.setBoardroomCode(boardroomApplyPO.getBoardroomCode());
      boardRoomActionForm.setName(boardroomName);
      boardRoomActionForm.setApplyEmp(boardroomApplyPO.getApplyEmp());
      boardRoomActionForm.setApplyEmpName(boardroomApplyPO.getApplyEmpName());
      boardRoomActionForm.setApplyOrgName(boardroomApplyPO.getApplyOrgName());
      boardRoomActionForm.setApplyOrg(boardroomApplyPO.getApplyOrg());
      boardRoomActionForm.setEmcee(boardroomApplyPO.getEmcee());
      boardRoomActionForm.setEmceeName(boardroomApplyPO.getEmceeName());
      boardRoomActionForm.setMotif(boardroomApplyPO.getMotif());
      boardRoomActionForm.setAttendee(boardroomApplyPO.getAttendee());
      boardRoomActionForm.setAttendeeEmpId(boardroomApplyPO.getAttendeeEmpId());
      boardRoomActionForm.setAttendeeLeader(boardroomApplyPO.getAttendeeLeader());
      boardRoomActionForm.setAttendeeLeaderId(boardroomApplyPO.getAttendeeLeaderId());
      boardRoomActionForm.setNonvoting(boardroomApplyPO.getNonvoting());
      boardRoomActionForm.setNonvotingEmpId(boardroomApplyPO.getNonvotingEmpId());
      boardRoomActionForm.setNotePerson(boardroomApplyPO.getNotePerson());
      boardRoomActionForm.setNotePersonId(boardroomApplyPO.getNotePersonId());
      boardRoomActionForm.setSenderName(boardroomApplyPO.getSenderName());
      boardRoomActionForm.setFileNumber(boardroomApplyPO.getFileNumber());
      boardRoomActionForm.setMsg(boardroomApplyPO.getMsg());
      boardRoomActionForm.setStatus(boardroomApplyPO.getStatus().toString());
      boardRoomActionForm.setPoints(boardroomApplyPO.getPoints());
      boardRoomActionForm.setLinkTelephone(boardroomApplyPO.getLinkTelephone());
      if (boardroomApplyPO.getPersonNum() != null)
        boardRoomActionForm.setPersonNum((String)boardroomApplyPO.getPersonNum()); 
      Long bdroomAppTypeId = new Long(0L);
      if (boardroomApplyPO.getBdroomAppType() != null)
        bdroomAppTypeId = boardroomApplyPO.getBdroomAppType().getBdroomAppTypeId(); 
      if (boardroomApplyPO.getMeetingTime() != null) {
        httpServletRequest.setAttribute("meetingTime", boardroomApplyPO.getMeetingTime());
      } else {
        httpServletRequest.setAttribute("meetingTime", null);
      } 
      httpServletRequest.setAttribute("ifRegular", boardroomApplyPO.getIfRegular());
      httpServletRequest.setAttribute("applyDate", boardroomApplyPO.getApplyDate());
      httpServletRequest.setAttribute("bdroomAppTypeId", bdroomAppTypeId);
      httpServletRequest.setAttribute("boardroomApplyId", boardroomApplyId);
      httpServletRequest.setAttribute("accessoryList", accessoryList);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      httpServletRequest.setAttribute("curWriteFields", (new WorkflowCommon()).getCurActivityWriteField(httpServletRequest));
      httpServletRequest.setAttribute("myform", boardRoomActionForm);
      httpServletRequest.setAttribute("boardEquipment", boardroomApplyPO.getBoardEquipment());
      if (boardroomApplyPO.getRegularPO() != null)
        httpServletRequest.setAttribute("boardroomRegular", boardroomApplyPO.getRegularPO()); 
      httpServletRequest.setAttribute("boardLayout", boardroomApplyPO.getBoardLayout());
      httpServletRequest.setAttribute("relProjectId", boardroomApplyPO.getRelProjectId());
      httpServletRequest.setAttribute("boardroomName", boardroomName);
      if (httpServletRequest.getParameter("changeBoardRoom") != null && 
        "1".equals(httpServletRequest.getParameter("changeBoardRoom"))) {
        httpServletRequest.setAttribute("boardroomId", httpServletRequest.getParameter("boardroomId"));
        BoardRoomPO boardroomPO = boardRoomBD.selectBoardroom(new Long(httpServletRequest.getParameter("boardroomId")));
        boardRoomList(httpServletRequest);
        boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
        httpServletRequest.setAttribute("isVideo", 
            boardroomPO.getIsVideo());
        httpServletRequest.setAttribute("maxNumber", boardroomPO.getMaxNumber());
        httpServletRequest.setAttribute("bdequSet", boardroomPO.getBoardRoomEquipment());
        httpServletRequest.setAttribute("boardRoomLayout", boardroomPO.getBoardRoomLayout());
      } else {
        httpServletRequest.setAttribute("boardroomId", !isEmpty(boardroomApplyPO.getBoardroom()) ? 
            boardroomApplyPO.getBoardroom().getBoardroomId() : "0");
        BoardRoomPO boardroomPO = boardRoomBD.selectBoardroom(!isEmpty(boardroomApplyPO.getBoardroom()) ? 
            boardroomApplyPO.getBoardroom().getBoardroomId() : new Long(0L));
        boardRoomList(httpServletRequest);
        boardRoomActionForm.setMaxNumber(boardroomPO.getMaxNumber());
        httpServletRequest.setAttribute("isVideo", boardroomPO.getIsVideo());
        httpServletRequest.setAttribute("maxNumber", boardroomPO.getMaxNumber());
        httpServletRequest.setAttribute("bdequSet", boardroomPO.getBoardRoomEquipment());
        httpServletRequest.setAttribute("boardRoomLayout", boardroomPO.getBoardRoomLayout());
      } 
      if ("selectBoardroomApplyModify".equals(action))
        return actionMapping.findForward("boardroomApplyModify"); 
      if ("view".equals(type)) {
        String statusa = (httpServletRequest.getParameter("statusa") == null) ? "0" : httpServletRequest.getParameter("statusa");
        httpServletRequest.setAttribute("statusa", statusa);
        if (httpServletRequest.getParameter("boardroomApplyId") != null) {
          boardroomApplyId = Long.valueOf(httpServletRequest
              .getParameter(
                "boardroomApplyId"));
          BoardRoomApplyPO brap = new BoardRoomApplyPO();
          brap.setBoardroomApplyId(boardroomApplyId);
          BoardroomPersons bp = new BoardroomPersons();
          bp.setApply(brap);
          bp.setEmpId(userID);
          bp.setEmpName(userName);
          bp.setOrgId(orgId);
          bp.setOrgName(orgName);
          bp.setStatus("100000");
          bp.setReplyDate(new Date());
          boardRoomBD.addBoardroomPersons(bp);
        } 
        return actionMapping.findForward("boardroomApplyView");
      } 
      if ("modi".equals(type))
        return actionMapping.findForward("boardroomApplyModi"); 
    } 
    if ("boardRoomUseSearch".equals(action)) {
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = boardRoomBD.maintenanceBoardRoom(scopeWhere);
      httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", boardroomApplyType);
      boardroomUseList(httpServletRequest);
      if (httpServletRequest.getParameter("flag") == null)
        return actionMapping.findForward("boardroomUseList_last"); 
      return actionMapping.findForward("boardroomUseList");
    } 
    if ("boardroomAppTypeView".equals(action)) {
      boardRoomAppTypeList(httpServletRequest);
      return actionMapping.findForward("boardroomAppTypeList");
    } 
    if ("addBoardroomAppTypeView".equals(action))
      return actionMapping.findForward("boardroomAppTypeAdd"); 
    if ("addBoardroomAppType".equals(action)) {
      BoardRoomApplyTypePO boardRoomAppTypePO = 
        (BoardRoomApplyTypePO)FillBean.transformOneToOne(boardRoomActionForm, 
          BoardRoomApplyTypePO.class);
      boardRoomAppTypePO.setCreatedEmp(userID);
      boardRoomAppTypePO.setCreatedOrg(orgId);
      boardRoomAppTypePO.setDomainId(domainId);
      boardRoomAppTypePO.setApplyType(httpServletRequest.getParameter("applyType"));
      boardRoomAppTypePO.setTypeDefault("0");
      boardRoomAppTypePO.setApplyFirst((httpServletRequest.getParameter("applyFirst") == null) ? "0" : httpServletRequest.getParameter("applyFirst"));
      ConversionString conversionString = null;
      if (boardRoomActionForm.getUseScopeId() == null || "".equals(boardRoomActionForm.getUseScopeId())) {
        conversionString = new ConversionString("*-1*");
      } else {
        conversionString = new ConversionString(
            boardRoomActionForm.getUseScopeId());
      } 
      boardRoomAppTypePO.setUsedScopeId(conversionString.getUserString());
      boardRoomAppTypePO.setUsedScopeOrgId(conversionString.getOrgString());
      boardRoomAppTypePO.setUsedScopeGroupId(conversionString.getGroupString());
      boardRoomAppTypePO.setUsedScopeName(boardRoomActionForm.getUseScope());
      boolean result = boardRoomBD.addBoardroomAppType(boardRoomAppTypePO);
      if (!result) {
        boardRoomActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("boardroomAppTypeAdd");
      } 
      if ("saveAndContinue".equals(saveType)) {
        boardRoomActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("boardroomAppTypeAdd");
      } 
      if ("saveAndExit".equals(saveType)) {
        boardRoomActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("boardroomAppTypeAdd");
      } 
    } 
    if ("deleteBoardroomAppType".equals(action)) {
      Long bdroomAppTypeId = new Long(0L);
      boolean result = true;
      if (httpServletRequest.getParameter(
          "bdroomAppTypeId") != null)
        if (!"".equals(httpServletRequest.getParameter(
              "bdroomAppTypeId"))) {
          bdroomAppTypeId = Long.valueOf(httpServletRequest.getParameter(
                "bdroomAppTypeId"));
          result = boardRoomBD.deleteBoardroomAppType(bdroomAppTypeId);
        }  
      if (!result) {
        httpServletRequest.setAttribute("result", "1");
      } else {
        httpServletRequest.setAttribute("result", "2");
      } 
      httpServletRequest.setAttribute("flag", "typeDel");
      return actionMapping.findForward("close");
    } 
    if ("typeDefalut".equals(action)) {
      boardRoomBD.boardroomAppTypeDefault(httpServletRequest.getParameter("bdroomAppTypeId"));
      httpServletRequest.setAttribute("flag", "typeDefault");
      return actionMapping.findForward("close");
    } 
    if ("deleteBatchBoardroomAppType".equals(action)) {
      String bdroomAppTypeIds = "";
      boolean result = true;
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids"))) {
        bdroomAppTypeIds = httpServletRequest.getParameter("ids");
        result = boardRoomBD.deleteBatchBoardroomAppType(
            bdroomAppTypeIds);
      } 
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        boardRoomAppTypeList(httpServletRequest, offsetCopy);
      } else {
        boardRoomAppTypeList(httpServletRequest);
      } 
      boardRoomActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("boardroomAppTypeList");
    } 
    if ("selectBoardroomAppTypeView".equals(action)) {
      String type = httpServletRequest.getParameter("type");
      Long bdroomAppTypeId = Long.valueOf(httpServletRequest.getParameter(
            "bdroomAppTypeId"));
      BoardRoomApplyTypePO boardRoomAppTypePO = boardRoomBD
        .selectBoardroomAppType(bdroomAppTypeId);
      boardRoomActionForm.setType(boardRoomAppTypePO.getType());
      boardRoomActionForm.setUseScope(boardRoomAppTypePO.getUsedScopeName());
      String userIds = boardRoomAppTypePO.getUsedScopeId();
      if (userIds == null || "null".equals(userIds))
        userIds = ""; 
      String orgIds = boardRoomAppTypePO.getUsedScopeOrgId();
      if (orgIds == null || "null".equals(orgIds))
        orgIds = ""; 
      String groupIds = boardRoomAppTypePO.getUsedScopeGroupId();
      if (groupIds == null || "null".equals(groupIds))
        groupIds = ""; 
      boardRoomActionForm.setUseScopeId(String.valueOf(userIds) + orgIds + groupIds);
      httpServletRequest.setAttribute("applyType", boardRoomAppTypePO.getApplyType());
      httpServletRequest.setAttribute("applyFirst", boardRoomAppTypePO.getApplyFirst());
      if ("view".equals(type))
        return actionMapping.findForward("boardroomAppTypeView"); 
      if ("modi".equals(type))
        return actionMapping.findForward("boardroomAppTypeModi"); 
    } 
    if ("modiBoardroomAppType".equals(action)) {
      Long bdroomAppTypeId = Long.valueOf(httpServletRequest.getParameter(
            "bdroomAppTypeId"));
      BoardRoomApplyTypePO boardRoomAppTypePO = 
        (BoardRoomApplyTypePO)FillBean.transformOneToOne(boardRoomActionForm, 
          BoardRoomApplyTypePO.class);
      boardRoomAppTypePO.setBdroomAppTypeId(bdroomAppTypeId);
      boardRoomAppTypePO.setDomainId(domainId);
      boardRoomAppTypePO.setApplyType((httpServletRequest.getParameter("applyType") == null) ? "0" : 
          httpServletRequest.getParameter("applyType"));
      ConversionString conversionString = null;
      if (boardRoomActionForm.getUseScopeId() == null || "".equals(boardRoomActionForm.getUseScopeId())) {
        conversionString = new ConversionString("*-1*");
      } else {
        conversionString = new ConversionString(
            boardRoomActionForm.getUseScopeId());
      } 
      boardRoomAppTypePO.setUsedScopeId(conversionString.getUserString());
      boardRoomAppTypePO.setUsedScopeOrgId(conversionString.getOrgString());
      boardRoomAppTypePO.setUsedScopeGroupId(conversionString.getGroupString());
      boardRoomAppTypePO.setUsedScopeName(boardRoomActionForm.getUseScope());
      boardRoomAppTypePO.setApplyFirst((httpServletRequest.getParameter("applyFirst") == null) ? "0" : 
          httpServletRequest.getParameter("applyFirst"));
      boolean result = boardRoomBD.modiBoardroomAppType(
          boardRoomAppTypePO);
      if (!result) {
        boardRoomActionForm.setSaveType("isRepeatName");
        httpServletRequest.setAttribute("bdroomAppTypeId", 
            bdroomAppTypeId);
        return actionMapping.findForward("boardroomAppTypeModi");
      } 
      if ("updateAndExit".equals(saveType)) {
        boardRoomActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("bdroomAppTypeId", 
            bdroomAppTypeId);
        return actionMapping.findForward("boardroomAppTypeModi");
      } 
    } 
    if ("meetingInformView".equals(action)) {
      meetingInformList(httpServletRequest);
      return actionMapping.findForward("meetingInformList");
    } 
    if ("meetingInformControl".equals(action)) {
      httpServletRequest.setAttribute("action", action);
      meetingSelectList(httpServletRequest);
      return actionMapping.findForward("meetingControl");
    } 
    if ("meetingInformBrow".equals(action)) {
      httpServletRequest.setAttribute("action", action);
      meetingSelectList(httpServletRequest);
      return actionMapping.findForward("meetingControl");
    } 
    if ("meetingInformWeekView".equals(action)) {
      Map weekMeeting = boardRoomBD.getWeekMeeting(userID, 
          Calendar.getInstance(), domainId, 
          session.getAttribute("orgIdString").toString());
      httpServletRequest.setAttribute("mondayList", 
          weekMeeting.get("monday"));
      httpServletRequest.setAttribute("tuesdayList", 
          weekMeeting.get("tuesday"));
      httpServletRequest.setAttribute("wednesdayList", 
          weekMeeting.get("wednesday"));
      httpServletRequest.setAttribute("thursdayList", 
          weekMeeting.get("thursday"));
      httpServletRequest.setAttribute("fridayList", 
          weekMeeting.get("friday"));
      httpServletRequest.setAttribute("saturdayList", 
          weekMeeting.get("saturday"));
      httpServletRequest.setAttribute("sundayList", 
          weekMeeting.get("sunday"));
      return actionMapping.findForward("meetingInformWeekList");
    } 
    if ("meetingInformSearch".equals(action)) {
      meetingInformList(httpServletRequest);
      return actionMapping.findForward("meetingInformList");
    } 
    if ("next".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
            "boardroomId"));
      BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
      boardroomApplyPO.setBoardroomApplyId(Long.valueOf(
            httpServletRequest.getParameter("boardroomApplyId")));
      boardroomApplyPO.setStartTime(httpServletRequest.getParameter(
            "destineDateBeginTime"));
      boardroomApplyPO.setEndTime(httpServletRequest.getParameter(
            "destineDateEndTime"));
      boardroomApplyPO.setDestineDate(new Date(httpServletRequest
            .getParameter("destineDate")));
      String isImpropriateTime = boardRoomBD.isImpropriateTime(
          boardroomApplyPO, boardroomId);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(
          domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", 
          boardroomApplyType);
      if ("0".equals(isImpropriateTime)) {
        getNextStep(httpServletRequest);
        return actionMapping.findForward("next");
      } 
      Long boardroomApplyId = Long.valueOf(httpServletRequest
          .getParameter("boardroomApplyId"));
      String boardroomName = httpServletRequest.getParameter(
          "boardroomName");
      Map map = boardRoomBD.selectBoardroomApply(boardroomApplyId);
      boardroomApplyPO = (BoardRoomApplyPO)map.get(
          "boardroomApplyPO");
      List accessoryList = (List)map.get("accessory");
      boardRoomActionForm.setDepict(boardroomApplyPO.getDepict());
      boardRoomActionForm.setName(boardroomName);
      boardRoomActionForm.setApplyEmpName(boardroomApplyPO
          .getApplyEmpName());
      boardRoomActionForm.setApplyOrgName(boardroomApplyPO
          .getApplyOrgName());
      boardRoomActionForm.setApplyOrg(boardroomApplyPO.getApplyOrg());
      boardRoomActionForm.setEmcee(boardroomApplyPO.getEmcee());
      boardRoomActionForm.setEmceeName(boardroomApplyPO.getEmceeName());
      boardRoomActionForm.setMotif(boardroomApplyPO.getMotif());
      boardRoomActionForm.setAttendee(boardroomApplyPO.getAttendee());
      boardRoomActionForm.setAttendeeEmpId(boardroomApplyPO
          .getAttendeeEmpId());
      boardRoomActionForm.setAttendeeLeader(boardroomApplyPO
          .getAttendeeLeader());
      boardRoomActionForm.setAttendeeLeaderId(boardroomApplyPO
          .getAttendeeLeaderId());
      boardRoomActionForm.setNonvoting(boardroomApplyPO.getNonvoting());
      boardRoomActionForm.setNonvotingEmpId(boardroomApplyPO
          .getNonvotingEmpId());
      boardRoomActionForm.setNotePerson(boardroomApplyPO
          .getNotePerson());
      boardRoomActionForm.setSenderName(boardroomApplyPO
          .getSenderName());
      boardRoomActionForm.setFileNumber(boardroomApplyPO
          .getFileNumber());
      Long bdroomAppTypeId = boardroomApplyPO.getBdroomAppType()
        .getBdroomAppTypeId();
      httpServletRequest.setAttribute("destineDate", 
          boardroomApplyPO.getDestineDate());
      httpServletRequest.setAttribute("startTime", 
          boardroomApplyPO.getStartTime());
      httpServletRequest.setAttribute("endTime", 
          boardroomApplyPO.getEndTime());
      httpServletRequest.setAttribute("bdroomAppTypeId", 
          bdroomAppTypeId);
      httpServletRequest.setAttribute("boardroomApplyId", 
          boardroomApplyId);
      httpServletRequest.setAttribute("boardroomId", 
          !isEmpty(boardroomApplyPO.getBoardroom()) ? boardroomApplyPO.getBoardroom()
          .getBoardroomId() : "0");
      httpServletRequest.setAttribute("accessoryList", accessoryList);
      boardRoomActionForm.setSaveType("isImpropriateTimeNoSave");
      return actionMapping.findForward("boardroomApplyModi");
    } 
    if ("oper".equals(action)) {
      oper(httpServletRequest);
      return actionMapping.findForward("next");
    } 
    if ("comp".equals(action)) {
      comp(httpServletRequest);
      return actionMapping.findForward("next");
    } 
    if ("reSub".equals(action)) {
      Long boardroomApplyId = Long.valueOf(httpServletRequest
          .getParameter(
            "boardroomApplyId"));
      boardRoomBD.deleteBoardroomApply(boardroomApplyId);
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
            "boardroomId"));
      BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
      boardroomApplyPO.setStartTime(httpServletRequest.getParameter(
            "destineDateBeginTime"));
      boardroomApplyPO.setEndTime(httpServletRequest.getParameter(
            "destineDateEndTime"));
      boardroomApplyPO.setDestineDate(new Date(httpServletRequest
            .getParameter("destineDate")));
      String isImpropriateTime = boardRoomBD.isImpropriateTime(
          boardroomApplyPO, boardroomId);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(
          domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", 
          boardroomApplyType);
      if ("0".equals(isImpropriateTime)) {
        reSub(httpServletRequest, boardRoomActionForm);
        return actionMapping.findForward("next");
      } 
      boardRoomActionForm.setSaveType("isImpropriateTimeNoSave");
      return actionMapping.findForward("boardroomApplyModi");
    } 
    if ("trans".equals(action)) {
      Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
            "boardroomId"));
      Long bdroomAppTypeId = Long.valueOf(httpServletRequest.getParameter(
            "boardroomApplyType"));
      BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
      boardroomApplyPO.setBoardroomApplyId(Long.valueOf(
            httpServletRequest.getParameter("boardroomApplyId")));
      boardroomApplyPO.setStartTime(httpServletRequest.getParameter(
            "destineDateBeginTime"));
      boardroomApplyPO.setEndTime(httpServletRequest.getParameter(
            "destineDateEndTime"));
      boardroomApplyPO.setDestineDate(new Date(httpServletRequest
            .getParameter("destineDate")));
      String isImpropriateTime = boardRoomBD.isImpropriateTime(
          boardroomApplyPO, boardroomId);
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(
          domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", 
          boardroomApplyType);
      if ("0".equals(isImpropriateTime)) {
        trans(httpServletRequest, boardRoomActionForm);
        boardRoomActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("bdroomAppTypeId", 
            bdroomAppTypeId);
        httpServletRequest.setAttribute("boardroomApplyId", 
            httpServletRequest.getParameter(
              "boardroomApplyId"));
        httpServletRequest.setAttribute("boardroomId", boardroomId);
        httpServletRequest.setAttribute("destineDate", 
            new Date(httpServletRequest
              .getParameter("destineDate")));
        httpServletRequest.setAttribute("startTime", 
            httpServletRequest
            .getParameter(
              "destineDateBeginTime"));
        httpServletRequest.setAttribute("endTime", 
            httpServletRequest
            .getParameter(
              "destineDateEndTime"));
        return actionMapping.findForward("boardroomApplyModi");
      } 
      Long boardroomApplyId = Long.valueOf(httpServletRequest
          .getParameter("boardroomApplyId"));
      String boardroomName = httpServletRequest.getParameter(
          "boardroomName");
      Map map = boardRoomBD.selectBoardroomApply(boardroomApplyId);
      boardroomApplyPO = (BoardRoomApplyPO)map.get(
          "boardroomApplyPO");
      List accessoryList = (List)map.get("accessory");
      boardRoomActionForm.setDepict(boardroomApplyPO.getDepict());
      boardRoomActionForm.setName(boardroomName);
      boardRoomActionForm.setApplyEmpName(boardroomApplyPO
          .getApplyEmpName());
      boardRoomActionForm.setApplyOrgName(boardroomApplyPO
          .getApplyOrgName());
      boardRoomActionForm.setApplyOrg(boardroomApplyPO.getApplyOrg());
      boardRoomActionForm.setEmcee(boardroomApplyPO.getEmcee());
      boardRoomActionForm.setEmceeName(boardroomApplyPO.getEmceeName());
      boardRoomActionForm.setMotif(boardroomApplyPO.getMotif());
      boardRoomActionForm.setAttendee(boardroomApplyPO.getAttendee());
      boardRoomActionForm.setAttendeeEmpId(boardroomApplyPO
          .getAttendeeEmpId());
      boardRoomActionForm.setAttendeeLeader(boardroomApplyPO
          .getAttendeeLeader());
      boardRoomActionForm.setAttendeeLeaderId(boardroomApplyPO
          .getAttendeeLeaderId());
      boardRoomActionForm.setNonvoting(boardroomApplyPO.getNonvoting());
      boardRoomActionForm.setNonvotingEmpId(boardroomApplyPO
          .getNonvotingEmpId());
      boardRoomActionForm.setNotePerson(boardroomApplyPO
          .getNotePerson());
      boardRoomActionForm.setSenderName(boardroomApplyPO
          .getSenderName());
      boardRoomActionForm.setFileNumber(boardroomApplyPO
          .getFileNumber());
      httpServletRequest.setAttribute("destineDate", 
          boardroomApplyPO.getDestineDate());
      httpServletRequest.setAttribute("startTime", 
          boardroomApplyPO.getStartTime());
      httpServletRequest.setAttribute("endTime", 
          boardroomApplyPO.getEndTime());
      httpServletRequest.setAttribute("bdroomAppTypeId", 
          bdroomAppTypeId);
      httpServletRequest.setAttribute("boardroomApplyId", 
          boardroomApplyId);
      httpServletRequest.setAttribute("boardroomId", 
          !isEmpty(boardroomApplyPO.getBoardroom()) ? boardroomApplyPO.getBoardroom()
          .getBoardroomId() : "0");
      httpServletRequest.setAttribute("accessoryList", accessoryList);
      boardRoomActionForm.setSaveType("isImpropriateTimeNoSave");
      return actionMapping.findForward("boardroomApplyModi");
    } 
    if ("untread".equals(action)) {
      untread(httpServletRequest);
      boardRoomActionForm.setSaveType("updateAndExit");
      Long boardroomApplyId = Long.valueOf(httpServletRequest
          .getParameter(
            "boardroomApplyId"));
      String boardroomName = httpServletRequest.getParameter(
          "boardroomName");
      Map map = boardRoomBD.selectBoardroomApply(boardroomApplyId);
      BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)map.get(
          "boardroomApplyPO");
      List accessoryList = (List)map.get("accessory");
      boardRoomActionForm.setDepict(boardroomApplyPO.getDepict());
      boardRoomActionForm.setName(boardroomName);
      boardRoomActionForm.setApplyEmpName(boardroomApplyPO
          .getApplyEmpName());
      boardRoomActionForm.setApplyOrgName(boardroomApplyPO
          .getApplyOrgName());
      boardRoomActionForm.setApplyOrg(boardroomApplyPO.getApplyOrg());
      boardRoomActionForm.setEmcee(boardroomApplyPO.getEmcee());
      boardRoomActionForm.setEmceeName(boardroomApplyPO.getEmceeName());
      boardRoomActionForm.setMotif(boardroomApplyPO.getMotif());
      boardRoomActionForm.setAttendee(boardroomApplyPO.getAttendee());
      boardRoomActionForm.setAttendeeEmpId(boardroomApplyPO
          .getAttendeeEmpId());
      boardRoomActionForm.setAttendeeLeader(boardroomApplyPO
          .getAttendeeLeader());
      boardRoomActionForm.setAttendeeLeaderId(boardroomApplyPO
          .getAttendeeLeaderId());
      boardRoomActionForm.setNonvoting(boardroomApplyPO.getNonvoting());
      boardRoomActionForm.setNonvotingEmpId(boardroomApplyPO
          .getNonvotingEmpId());
      boardRoomActionForm.setNotePerson(boardroomApplyPO.getNotePerson());
      boardRoomActionForm.setSenderName(boardroomApplyPO.getSenderName());
      boardRoomActionForm.setFileNumber(boardroomApplyPO.getFileNumber());
      Long bdroomAppTypeId = boardroomApplyPO.getBdroomAppType()
        .getBdroomAppTypeId();
      List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(
          domainId);
      httpServletRequest.setAttribute("boardroomApplyTypeList", 
          boardroomApplyType);
      httpServletRequest.setAttribute("destineDate", 
          boardroomApplyPO.getDestineDate());
      httpServletRequest.setAttribute("startTime", 
          boardroomApplyPO.getStartTime());
      httpServletRequest.setAttribute("endTime", 
          boardroomApplyPO.getEndTime());
      httpServletRequest.setAttribute("bdroomAppTypeId", bdroomAppTypeId);
      httpServletRequest.setAttribute("boardroomApplyId", 
          boardroomApplyId);
      httpServletRequest.setAttribute("boardroomId", 
          !isEmpty(boardroomApplyPO.getBoardroom()) ? boardroomApplyPO.getBoardroom()
          .getBoardroomId() : "0");
      httpServletRequest.setAttribute("accessoryList", accessoryList);
      return actionMapping.findForward("boardroomApplyModi");
    } 
    if ("creatorCancel".equals(action)) {
      String cancelReason = httpServletRequest.getParameter(
          "cancelReason");
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String processName = httpServletRequest.getParameter("processName");
      String workId = httpServletRequest.getParameter("workId");
      String processId = httpServletRequest.getParameter("processId");
      String remindValue = "";
      if (httpServletRequest.getParameter("remindValue") != null)
        remindValue = httpServletRequest.getParameter("remindValue"); 
      WorkFlowBD workFlowBD = new WorkFlowBD();
      List alist = workFlowBD.getWorkUserLogin(tableId, recordId, 
          processId);
      ArrayList<String> sqlList = new ArrayList();
      sqlList.add("delete JSF_WORK where worktable_id = " + tableId + 
          " and workrecord_id = " + 
          recordId + " and workProcess_id = " + processId + 
          " and wf_work_id <> " + workId);
      if (httpServletRequest.getParameter("channelType") != null) {
        sqlList.add("update JSF_WORK set workTitle = '您已取消了您的" + 
            remindValue + processName + 
            "', workStatus = -2, workCancelReason = '" + 
            cancelReason + 
            "', workMainLinkFile = '/jsoa/InfoProcAction.do?channelType=" + 
            httpServletRequest.getParameter("channelType") + 
            "&informationType=" + 
            httpServletRequest.getParameter("informationType") + 
            "&redhead=" + 
            httpServletRequest.getParameter("redhead") + 
            "' where wf_work_id = " + workId);
      } else {
        sqlList.add("update JSF_WORK set workTitle = '您已取消了您的" + 
            remindValue + processName + 
            "', workStatus = -2, workCancelReason = '" + 
            cancelReason + 
            "', workMainLinkFile = '/jsoa/WorkFlowReSubmitAction.do?pp=1' where wf_work_id = " + 
            workId);
      } 
      workFlowBD.updateTable(sqlList);
      boardRoomBD.deleteBoardroomApply(new Long(recordId));
      return actionMapping.findForward("cancel");
    } 
    if ("statuDetail".equals(action)) {
      boardRoomInfo(httpServletRequest, boardRoomActionForm);
      return actionMapping.findForward("statusDetail");
    } 
    if ("boardRoomStatus".equals(action)) {
      BoardRoomBD roomBD = new BoardRoomBD();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String boardRoomId = httpServletRequest.getParameter("boardRoomId");
      httpServletRequest.setAttribute("boardRoomId", boardRoomId);
      List searchDate = new ArrayList();
      String roomNum = (httpServletRequest.getParameter("roomNum") == null) ? "" : httpServletRequest.getParameter("roomNum");
      String roomNumCond = (httpServletRequest.getParameter("roomNumCond") == null) ? "3" : httpServletRequest.getParameter("roomNumCond");
      Date searchStartDate = new Date();
      Date searchEndDate = new Date();
      if (httpServletRequest.getParameter("searchStartDate") != null)
        searchStartDate = new Date(httpServletRequest.getParameter("searchStartDate")); 
      scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + " and boardRoomPO.domainId=" + domainId;
      String maintenanceBoardRoomIds = roomBD.maintenanceBoardRoom(scopeWhere);
      if ((new ManagerService()).hasRightTypeName((String)userID, "会议管理", "审核")) {
        scopeWhere = " 1=1 ";
      } else {
        scopeWhere = " 1<>1 ";
      } 
      String auditingBoardRoomIds = roomBD.maintenanceBoardRoom(scopeWhere);
      String tmp = "";
      if (maintenanceBoardRoomIds != null && 
        !"".equals(maintenanceBoardRoomIds)) {
        if (maintenanceBoardRoomIds.endsWith(","))
          maintenanceBoardRoomIds = maintenanceBoardRoomIds.substring(0, 
              maintenanceBoardRoomIds.length() - 1); 
        tmp = " or po.boardroomId in (" + maintenanceBoardRoomIds + 
          ") ";
      } 
      String useScopeIds = " and (po.useScopeId like '%$" + userID + "$%'";
      String orgIds = roomBD.getOrgIdsByUserId((String)userID);
      String org_sql = "";
      if (orgIds != null && orgIds.length() > 0) {
        String[] tt = orgIds.substring(1, orgIds.length() - 1).split("\\*\\*");
        for (int i = 0; i < tt.length; i++) {
          org_sql = String.valueOf(org_sql) + " or po.scopeOrg like '%*" + tt[i] + "*%' ";
          useScopeIds = String.valueOf(useScopeIds) + " or po.useScopeId like '%*" + tt[i] + "*%'  ";
        } 
      } 
      String groupIds = roomBD.getGroupIdsByUserId((String)userID);
      String group_sql = "";
      if (groupIds != null && groupIds.length() > 0) {
        String[] tt = groupIds.substring(1, groupIds.length() - 1).split("\\@\\@");
        for (int i = 0; i < tt.length; i++) {
          group_sql = String.valueOf(group_sql) + " or po.scopeGroup like '%@" + tt[i] + "@%' ";
          useScopeIds = String.valueOf(useScopeIds) + " or po.useScopeId like '%@" + tt[i] + "@%'  ";
        } 
      } 
      useScopeIds = String.valueOf(useScopeIds) + " or createdEmp=" + userID + " )";
      String where = " where  (po.scopeEmp like '%$" + userID + "$%' or po.limit=0 " + tmp + org_sql + group_sql + ") and po.domainId=" + domainId + useScopeIds;
      Object[][] obj = roomBD.getBoardRoomSatus(formatter.format(searchStartDate), roomNum, roomNumCond, where);
      httpServletRequest.setAttribute("searchDate", searchDate);
      httpServletRequest.setAttribute("roomNum", roomNum);
      httpServletRequest.setAttribute("roomNumCond", roomNumCond);
      httpServletRequest.setAttribute("roomList", obj);
      return actionMapping.findForward("boardRoomStatus");
    } 
    if ("applyReportList".equals(action)) {
      String boardroomApplyId = httpServletRequest.getParameter("boardroomApplyId");
      Map map = boardRoomBD.selectBoardroomApply(new Long(boardroomApplyId));
      BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)map.get("boardroomApplyPO");
      httpServletRequest.setAttribute("applyReportList", boardroomApplyPO.getPersons());
      return actionMapping.findForward(action);
    } 
    if ("applyReportAdd".equals(action)) {
      String boardroomApplyId = httpServletRequest.getParameter("boardroomApplyId");
      BoardRoomApplyPO brap = new BoardRoomApplyPO();
      brap.setBoardroomApplyId(new Long(boardroomApplyId));
      BoardroomPersons bp = new BoardroomPersons();
      bp.setApply(brap);
      bp.setEmpId(userID);
      bp.setEmpName(userName);
      bp.setOrgId(orgId);
      bp.setOrgName(orgName);
      bp.setStatus(httpServletRequest.getParameter("status"));
      bp.setContent(httpServletRequest.getParameter("content"));
      bp.setReplyDate(new Date());
      boardRoomBD.addBoardroomPersons(bp);
      httpServletRequest.setAttribute("refresh", "true");
      httpServletRequest.setAttribute("flag", "applyReportAdd");
      return actionMapping.findForward("close");
    } 
    if ("finishMeeting".equals(action)) {
      String meetingId = httpServletRequest.getParameter("meetingId");
      String boardroomApplyId = httpServletRequest.getParameter("boardroomApplyId");
      String endHour = httpServletRequest.getParameter("endHour");
      String endMinutes = httpServletRequest.getParameter("endMinutes");
      int endH = Integer.parseInt(endHour) * 3600;
      int endM = Integer.parseInt(endMinutes) * 60;
      boardRoomBD.modifyBoardroomMeetingTimePO(meetingId, boardroomApplyId, new Integer(endH + endM));
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
      return actionMapping.findForward("finishMeeting");
    } 
    if ("boardRoomUseInfo".equals(action)) {
      boardRoomUseInfo(httpServletRequest);
      return actionMapping.findForward("boardRoomUseInfo");
    } 
    if ("task".equals(action)) {
      String applyId = httpServletRequest.getParameter("boardroomApplyId");
      String[] users = boardRoomBD.boradroomUser(applyId);
      httpServletRequest.setAttribute("caozuo", (String.valueOf(users[0]) + users[5]).replace("null", ""));
      meetingTaskList(httpServletRequest, applyId);
      return actionMapping.findForward("task");
    } 
    if ("viewUsers".equals(action)) {
      viewUsers(httpServletRequest);
      return actionMapping.findForward(action);
    } 
    if ("unviewUsers".equals(action)) {
      unviewUsers(httpServletRequest);
      return actionMapping.findForward(action);
    } 
    if ("saveExecuteStatus".equals(action)) {
      saveExecuteStatus(httpServletRequest);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
      httpServletRequest.setAttribute("flag", "saveExecuteStatus");
      return actionMapping.findForward("close");
    } 
    if ("executeStatus".equals(action)) {
      executeStatus(httpServletRequest);
      return actionMapping.findForward(action);
    } 
    if ("boardRoomWeekShow".equals(action)) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String beginDate = (httpServletRequest.getParameter("beginDate") == null) ? "" : httpServletRequest.getParameter("beginDate");
      Calendar week = Calendar.getInstance();
      String flag = httpServletRequest.getParameter("flag");
      if ("".equals(beginDate)) {
        week.setTime(new Date());
        week.set(7, 2);
      } else if (flag != null) {
        beginDate = beginDate.replace("/", "-");
        if ("1".equals(flag)) {
          week.setTime(dateFormat.parse(beginDate));
          week.set(7, 2);
        } else {
          week.setTime(dateFormat.parse(beginDate));
        } 
      } else {
        week.setTime(dateFormat.parse(beginDate));
      } 
      Date weekBeginDate = timeFormat.parse(String.valueOf(dateFormat.format(week.getTime())) + " 00:00:00");
      httpServletRequest.setAttribute("weekBeginDate", format.format(weekBeginDate));
      week.set(5, week.get(5) + 6);
      Date weekEndDate = timeFormat.parse(String.valueOf(dateFormat.format(week.getTime())) + " 23:59:59");
      httpServletRequest.setAttribute("weekEndDate", format.format(weekEndDate).substring(5));
      String roomId = httpServletRequest.getParameter("boardRoomId");
      List<String[][]> weekList = null;
      if (flag != null && "2".equals(flag)) {
        weekList = (new BoardRoomBD()).getWeekRoomShow(roomId, weekBeginDate, 1);
        httpServletRequest.setAttribute("flag", flag);
      } else {
        weekList = (new BoardRoomBD()).getWeekRoomShow(roomId, weekBeginDate);
      } 
      httpServletRequest.setAttribute("weekList", weekList);
      week.set(5, week.get(5) + 1);
      httpServletRequest.setAttribute("nextBeginDate", dateFormat.format(week.getTime()));
      week.set(5, week.get(5) - 14);
      httpServletRequest.setAttribute("lastBeginDate", dateFormat.format(week.getTime()));
      return actionMapping.findForward(action);
    } 
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
  
  private boolean hasAddRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).hasRightTypeName(
        httpSession.getAttribute("userId").toString(), "会议管理", "维护");
  }
  
  private String scopeWhere(HttpServletRequest httpServletRequest, String rightType, String rightName) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).getRightFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        rightType, 
        rightName, 
        "boardRoomPO.createdOrg", 
        "boardRoomPO.createdEmp");
  }
  
  public void meetingTaskList(HttpServletRequest httpServletRequest, String meetingId) {
    String where = " where po.meetingId=" + meetingId;
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(" po ", 
        "com.js.oa.scheme.taskcenter.po.TaskPO po", 
        String.valueOf(where) + " order by po.taskIdStrings");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("taskList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void boardRoomList(HttpServletRequest httpServletRequest, String maintenanceBoardRoomIds) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String tmp = "";
    if (maintenanceBoardRoomIds != null && 
      !"".equals(maintenanceBoardRoomIds)) {
      if (maintenanceBoardRoomIds.endsWith(","))
        maintenanceBoardRoomIds = maintenanceBoardRoomIds.substring(0, 
            maintenanceBoardRoomIds.length() - 1); 
      tmp = " or boardRoomPO.boardroomId in (" + maintenanceBoardRoomIds + 
        ") ";
    } 
    String useScopeIds = " and (boardRoomPO.useScopeId like '%$" + userID + "$%'";
    BoardRoomBD rbd = new BoardRoomBD();
    String orgIds = rbd.getOrgIdsByUserId((String)userID);
    String org_sql = "";
    if (orgIds != null && orgIds.length() > 0) {
      String[] tt = orgIds.substring(1, orgIds.length() - 1).split("\\*\\*");
      for (int i = 0; i < tt.length; i++) {
        org_sql = String.valueOf(org_sql) + " or boardRoomPO.scopeOrg like '%*" + tt[i] + "*%' ";
        useScopeIds = String.valueOf(useScopeIds) + " or boardRoomPO.useScopeId like '%*" + tt[i] + "*%'  ";
      } 
    } 
    String groupIds = rbd.getGroupIdsByUserId((String)userID);
    String group_sql = "";
    if (groupIds != null && groupIds.length() > 0) {
      String[] tt = groupIds.substring(1, groupIds.length() - 1).split("\\@\\@");
      for (int i = 0; i < tt.length; i++) {
        group_sql = String.valueOf(group_sql) + " or boardRoomPO.scopeGroup like '%@" + tt[i] + "@%' ";
        useScopeIds = String.valueOf(useScopeIds) + " or boardRoomPO.useScopeId like '%@" + tt[i] + "@%'  ";
      } 
    } 
    useScopeIds = String.valueOf(useScopeIds) + " or createdEmp=" + userID + " )";
    String where = " where (boardRoomPO.scopeEmp like '%$" + userID + 
      "$%' or boardRoomPO.limit=0 " + tmp + org_sql + group_sql + 
      ") and boardRoomPO.domainId=" + domainId + useScopeIds;
    String databaseType = SystemCommon.getDatabaseType();
    if ("mysql".equals(databaseType)) {
      where = String.valueOf(where) + " order by boardRoomPO.boardroomOrder asc , CONVERT(boardRoomPO.name USING gbk) desc";
    } else {
      where = String.valueOf(where) + " order by boardRoomPO.boardroomOrder asc , boardRoomPO.name desc";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomPO.boardroomId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomPO.cost,boardRoomPO.emphasis,boardRoomPO.doorNumber,boardRoomPO.boardRoomLayout,boardRoomPO.chargeType, boardRoomPO.boardroomOrder ", 
        
        "com.js.oa.routine.boardroom.po.BoardRoomPO boardRoomPO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardRoomList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,boardroomId,boardroomIds,init,searchStartDate,type");
  }
  
  public void discussionList(HttpServletRequest request, int pageSize, String pageNum) {
    String applyId = request.getParameter("boardroomApplyId");
    String where = " where po.applyId= " + applyId + " order by po.discussionDate";
    if ("-1".equals(pageNum))
      where = String.valueOf(where) + " desc"; 
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page("po", "com.js.oa.routine.boardroom.po.BoardRoomDiscussionPO po", where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("discussionList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
  }
  
  public void boardRoomList(HttpServletRequest httpServletRequest, int offsetCopy, String maintenanceBoardRoomIds) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String tmp = "";
    if (maintenanceBoardRoomIds != null && 
      !"".equals(maintenanceBoardRoomIds)) {
      if (maintenanceBoardRoomIds.endsWith(","))
        maintenanceBoardRoomIds = maintenanceBoardRoomIds.substring(0, 
            maintenanceBoardRoomIds.length() - 1); 
      tmp = " or boardRoomPO.boardroomId in (" + maintenanceBoardRoomIds + 
        ") ";
    } 
    BoardRoomBD rbd = new BoardRoomBD();
    String orgIds = rbd.getOrgIdsByUserId((String)userID);
    String org_sql = "";
    if (orgIds != null && orgIds.length() > 0) {
      String[] tt = orgIds.substring(1, 
          orgIds.length() - 1).split("\\*\\*");
      for (int i = 0; i < tt.length; i++)
        org_sql = String.valueOf(org_sql) + " or boardRoomPO.scopeOrg like '%*" + tt[i] + "*%' "; 
    } 
    String groupIds = rbd.getGroupIdsByUserId((String)userID);
    String group_sql = "";
    if (groupIds != null && groupIds.length() > 0) {
      String[] tt = groupIds.substring(1, 
          groupIds.length() - 1).split("\\@\\@");
      for (int i = 0; i < tt.length; i++)
        group_sql = String.valueOf(group_sql) + " or boardRoomPO.scopeGroup like '%@" + tt[i] + "@%' "; 
    } 
    String where = " where (boardRoomPO.scopeEmp like '%$" + userID + 
      "$%' or boardRoomPO.limit=0 " + tmp + org_sql + group_sql + 
      ") and boardRoomPO.domainId=" + domainId + 
      " order by boardRoomPO.boardroomId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomPO.boardroomId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomPO.cost,boardRoomPO.emphasis", 
        "com.js.oa.routine.boardroom.po.BoardRoomPO boardRoomPO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardRoomList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,boardroomId,boardroomIds");
  }
  
  public void boardRoomApplyList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String boardroomId = httpServletRequest.getParameter("boardroomId");
    String where = " where boardRoomPO.boardroomId=" + boardroomId + 
      " order by boardRoomApplyPO.destineDate desc,boardRoomApplyPO.startTime desc,poo.meetingDate desc,poo.startTime desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomApplyPO.boardroomApplyId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomApplyPO.status,boardRoomApplyPO.applyEmpName,boardRoomApplyPO.applyOrgName,poo.meetingDate,poo.startTime,poo.endTime,boardRoomApplyPO.motif,poo.id", 
        "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.bdroomAppType po join boardRoomApplyPO.meetingTime poo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardRoomApplyList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,boardroomId,name,type,place");
  }
  
  public void boardroomUseList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String saveType = (new StringBuilder(String.valueOf(httpServletRequest.getParameter("saveType")))).toString();
    String searchBoardRomeName = httpServletRequest.getParameter("searchBoardRomeName");
    String searchBoardRomeEmcee = httpServletRequest.getParameter("searchBoardRomeEmcee");
    String searchBoardRomeMotif = httpServletRequest.getParameter("searchBoardRomeMotif");
    String searchDatecheckbox = httpServletRequest.getParameter("searchDatecheckbox");
    String searchStartDate = String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + " 23:59:59";
    String searchApplyName = httpServletRequest.getParameter("searchApplyName");
    String boardroomApplyType = httpServletRequest.getParameter("boardroomApplyType");
    String databaseType = SystemCommon.getDatabaseType();
    String where = " where boardRoomApplyPO.status = 0";
    if ((new ManagerService()).hasRightTypeName(curUserId, "会议管理", "维护")) {
      where = String.valueOf(where) + " and 1=1 ";
    } else {
      where = String.valueOf(where) + " and (boardRoomApplyPO.emcee like '%$" + curUserId + "$%' or " + 
        "boardRoomApplyPO.attendeeEmpId like '%$" + curUserId + "$%' or " + 
        "boardRoomApplyPO.applyEmp='" + curUserId + "' or " + 
        "boardRoomApplyPO.attendeeLeaderId like '%$" + curUserId + "$%' or " + 
        "boardRoomApplyPO.nonvotingEmpId like '%$" + curUserId + "$%'" + 
        " or boardRoomApplyPO.notePersonId like'%$" + curUserId + "$%' ) ";
    } 
    if ("boardRoomUseSearch".equals(saveType)) {
      if (!"".equals(searchBoardRomeName))
        where = String.valueOf(where) + " and boardRoomPO.name like '%" + searchBoardRomeName + "%'"; 
      if (!"".equals(searchBoardRomeEmcee))
        where = String.valueOf(where) + " and boardRoomApplyPO.emceeName like '%" + searchBoardRomeEmcee + "%'"; 
      if (!"".equals(searchBoardRomeMotif))
        where = String.valueOf(where) + " and boardRoomApplyPO.motif like '%" + searchBoardRomeMotif + "%'"; 
      if ("1".equals(searchDatecheckbox))
        if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and poo.meetingDate between '" + searchStartDate + "' and '" + searchEndDate + "'";
        } else {
          where = String.valueOf(where) + " and poo.meetingDate between JSDB.FN_STRTODATE('" + searchStartDate + "','L') and JSDB.FN_STRTODATE('" + searchEndDate + "','L')";
        }  
      if (searchApplyName != null && !"".equals(searchApplyName))
        where = String.valueOf(where) + " and boardRoomApplyPO.applyEmpName like '%" + searchApplyName + "%'"; 
      if (boardroomApplyType != null && !"0".equals(boardroomApplyType)) {
        httpServletRequest.setAttribute("boardroomApplyType", boardroomApplyType);
        where = String.valueOf(where) + " and boardRoomApplyPO.bdroomAppType.bdroomAppTypeId = " + boardroomApplyType;
      } 
      if (httpServletRequest.getParameter("flag") == null) {
        long nowTime = (new Date()).getTime();
        where = String.valueOf(where) + " and poo.beginLong>=" + nowTime + " and poo.beginLong<=" + (nowTime + 2592000000L);
        httpServletRequest.setAttribute("flag", httpServletRequest.getAttribute("flag"));
      } 
    } else if (httpServletRequest.getParameter("flag") == null) {
      long nowTime = (new Date()).getTime();
      where = String.valueOf(where) + " and poo.beginLong>=" + nowTime + " and poo.beginLong<=" + (nowTime + 2592000000L);
      httpServletRequest.setAttribute("flag", httpServletRequest.getAttribute("flag"));
    } 
    where = String.valueOf(where) + " and boardRoomApplyPO.domainId=" + domainId;
    if (httpServletRequest.getParameter("orderbytime") != null) {
      if (httpServletRequest.getParameter("orderbytime").equals("0")) {
        if (databaseType.indexOf("oracle") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate, to_number(poo.startTime)";
        } else if (databaseType.indexOf("db2") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate, cast(poo.startTime as int)";
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate, poo.startTime";
        } else {
          where = String.valueOf(where) + " order by poo.meetingDate, convert(int,poo.startTime)";
        } 
      } else if (databaseType.indexOf("oracle") >= 0) {
        where = String.valueOf(where) + " order by poo.meetingDate desc, to_number(poo.startTime) desc";
      } else if (databaseType.indexOf("db2") >= 0) {
        where = String.valueOf(where) + " order by poo.meetingDate desc, cast(poo.startTime as int) desc";
      } else if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " order by poo.meetingDate desc, poo.startTime desc";
      } else {
        where = String.valueOf(where) + " order by poo.meetingDate desc, convert(int,poo.startTime) desc";
      } 
    } else if (httpServletRequest.getParameter("orderbystatus") != null) {
      if (httpServletRequest.getParameter("orderbystatus").equals("0")) {
        where = String.valueOf(where) + " order by boardRoomPO.name  ";
      } else {
        where = String.valueOf(where) + " order by boardRoomPO.name desc  ";
      } 
    } else {
      where = String.valueOf(where) + " order by poo.meetingDate desc,poo.startTime desc";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomApplyPO.boardroomApplyId,boardRoomPO.boardroomId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomApplyPO.applyEmpName,boardRoomApplyPO.applyOrgName,poo.meetingDate,poo.startTime,poo.endTime,boardRoomApplyPO.emceeName,boardRoomApplyPO.motif,boardRoomApplyPO.bdroomAppType,boardRoomApplyPO.applyEmp,poo.id,poo.status,poo.sortNum,boardRoomApplyPO.notePersonId,boardRoomApplyPO.emcee,poo.beginLong,poo.endLong ", 


        
        "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.bdroomAppType po join boardRoomApplyPO.meetingTime poo ", 
        
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardroomUseList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchBoardRomeName,searchBoardRomeEmcee,searchBoardRomeMotif,searchDatecheckbox,searchStartDate,searchEndDate,saveType,orderbytime,orderbystatus,searchApplyName,boardroomApplyType,flag");
  }
  
  public void boardroomRegularList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String saveType = httpServletRequest.getParameter("saveType");
    String searchBoardRomeName = httpServletRequest.getParameter("searchBoardRomeName");
    String searchBoardRomeEmcee = httpServletRequest.getParameter("searchBoardRomeEmcee");
    String searchBoardRomeMotif = httpServletRequest.getParameter("searchBoardRomeMotif");
    String searchApplyName = httpServletRequest.getParameter("searchApplyName");
    String boardroomApplyType = httpServletRequest.getParameter("boardroomApplyType");
    String databaseType = SystemCommon.getDatabaseType();
    String where = " where boardRoomApplyPO.status = 0";
    if ((new ManagerService()).hasRightTypeName(curUserId, "会议管理", "维护")) {
      where = String.valueOf(where) + " and 1=1 ";
    } else {
      where = String.valueOf(where) + " and (boardRoomApplyPO.emcee like '%$" + curUserId + "$%' or " + 
        "boardRoomApplyPO.attendeeEmpId like '%$" + curUserId + "$%' or " + 
        "boardRoomApplyPO.applyEmp='" + curUserId + "' or " + 
        "boardRoomApplyPO.attendeeLeaderId like '%$" + curUserId + "$%' or " + 
        "boardRoomApplyPO.nonvotingEmpId like '%$" + curUserId + "$%'" + 
        " or boardRoomApplyPO.notePersonId like'%$" + curUserId + "$%' ) ";
    } 
    if ("boardRoomUseSearch".equals(saveType)) {
      if (!"".equals(searchBoardRomeName))
        where = String.valueOf(where) + " and boardRoomPO.name like '%" + searchBoardRomeName + "%'"; 
      if (!"".equals(searchBoardRomeEmcee))
        where = String.valueOf(where) + " and boardRoomApplyPO.emceeName like '%" + searchBoardRomeEmcee + "%'"; 
      if (!"".equals(searchBoardRomeMotif))
        where = String.valueOf(where) + " and boardRoomApplyPO.motif like '%" + searchBoardRomeMotif + "%'"; 
      if (searchApplyName != null && !"".equals(searchApplyName))
        where = String.valueOf(where) + " and boardRoomApplyPO.applyEmpName like '%" + searchApplyName + "%'"; 
      if (boardroomApplyType != null && !"0".equals(boardroomApplyType)) {
        httpServletRequest.setAttribute("boardroomApplyType", boardroomApplyType);
        where = String.valueOf(where) + " and boardRoomApplyPO.bdroomAppType.bdroomAppTypeId = " + boardroomApplyType;
      } 
    } 
    where = String.valueOf(where) + " and boardRoomApplyPO.regularPO>0 and boardRoomApplyPO.domainId=" + domainId;
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && !"null".equals(httpServletRequest.getParameter("pager.offset")))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomApplyPO.boardroomApplyId,boardRoomPO.boardroomId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomApplyPO.applyEmpName,boardRoomApplyPO.applyOrgName,boardRoomApplyPO.emceeName,boardRoomApplyPO.motif,boardRoomApplyPO.bdroomAppType,boardRoomApplyPO.applyEmp,boardRoomApplyPO.notePersonId,boardRoomApplyPO.emcee,boardRoomApplyPO.regularPO ", 

        
        "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.bdroomAppType po ", 
        String.valueOf(where) + " order by boardRoomApplyPO.boardroomApplyId desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardroomRegularList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,saveType,searchBoardRomeName,searchBoardRomeEmcee,searchBoardRomeMotif,searchDatecheckbox,searchApplyName,boardroomApplyType");
  }
  
  public void boardRoomAppTypeList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where boardRoomAppTypePO.domainId=" + domainId + 
      " order by boardRoomAppTypePO.bdroomAppTypeId desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomAppTypePO.bdroomAppTypeId,boardRoomAppTypePO.type,boardRoomAppTypePO.applyType,boardRoomAppTypePO.typeDefault", 
        "com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO boardRoomAppTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardRoomAppTypeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void boardRoomAppTypeList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where boardRoomAppTypePO.domainId=" + domainId + 
      " order by boardRoomAppTypePO.bdroomAppTypeId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomAppTypePO.bdroomAppTypeId,boardRoomAppTypePO.type", 
        "com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO boardRoomAppTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardRoomAppTypeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void meetingInformList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    Long userID = new Long(session.getAttribute("userId").toString());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String destineDate = dateFormat.format(new Date());
    String type = httpServletRequest.getParameter("type");
    String sort = httpServletRequest.getParameter("sort");
    String saveType = httpServletRequest.getParameter("saveType");
    String where = "";
    String searchMotif = httpServletRequest.getParameter("searchMotif");
    String searchBoardName = httpServletRequest.getParameter("searchBoardName");
    String searchBoardRomeLocation = httpServletRequest.getParameter("searchBoardRomeLocation");
    String searchDatecheckbox = httpServletRequest.getParameter("searchDatecheckbox");
    String searchStartDate = String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + " 23:59:59";
    if ("day".equals(type)) {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = " where (boardRoomApplyPO.emcee like '%$" + userID + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%$" + userID + 





          
          "$%' or boardRoomApplyPO.attendeeLeaderId like '%$" + userID + 
          "$%' or boardRoomApplyPO.nonvotingEmpId like '%" + userID + 
          "%' or boardRoomApplyPO.notePersonId like '%$" + userID + "$%') " + 
          "and boardRoomApplyPO.status =0 and poo.meetingDate = '" + 
          destineDate + "' and boardRoomApplyPO.domainId=" + 
          domainId + " and poo.status=0 ";
        if ("1".equals(sort)) {
          where = String.valueOf(where) + " order by poo.meetingDate, poo.startTime";
        } else {
          where = String.valueOf(where) + " order by poo.meetingDate, poo.startTime desc";
        } 
      } else {
        where = " where (boardRoomApplyPO.emcee like '%$" + userID + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%$" + userID + 





          
          "$%' or boardRoomApplyPO.attendeeLeaderId like '%" + userID + 
          "%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
          userID + "$%' or boardRoomApplyPO.notePersonId like '%$" + userID + "$%'" + 
          ") and boardRoomApplyPO.status =0 and poo.meetingDate = JSDB.FN_STRTODATE('" + 
          destineDate + "','S') and boardRoomApplyPO.domainId=" + 
          domainId + " and poo.status=0 ";
        if ("1".equals(sort)) {
          if (databaseType.indexOf("oracle") >= 0) {
            where = String.valueOf(where) + " order by poo.meetingDate, to_number(poo.startTime)";
          } else if (databaseType.indexOf("db2") >= 0) {
            where = String.valueOf(where) + " order by poo.meetingDate, cast(poo.startTime, int)";
          } else {
            where = String.valueOf(where) + " order by poo.meetingDate, convert(int,poo.startTime)";
          } 
        } else if (databaseType.indexOf("oracle") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate desc, to_number(poo.startTime) desc";
        } else if (databaseType.indexOf("db2") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate desc, cast(poo.startTime as int) desc ";
        } else {
          where = String.valueOf(where) + " order by poo.meetingDate desc, convert(int,poo.startTime) desc ";
        } 
      } 
    } else if ("list".equals(type)) {
      where = " where (boardRoomApplyPO.emcee like '%$" + userID + 
        "$%' or boardRoomApplyPO.attendeeEmpId like '%" + userID + 





        
        "%' or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userID + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + userID + 
        "$%' or boardRoomApplyPO.notePersonId like '%$" + userID + "$%' ) " + 
        "and boardRoomApplyPO.status=0 and boardRoomApplyPO.domainId=" + 
        domainId + " and poo.status=0 ";
      String databaseType = SystemCommon.getDatabaseType();
      if ("meetingInformSearch".equals(saveType)) {
        if (!"".equals(searchMotif))
          where = String.valueOf(where) + " and boardRoomApplyPO.motif like '%" + searchMotif + "%'"; 
        if (!"".equals(searchBoardName))
          where = String.valueOf(where) + " and boardRoomPO.name like '%" + searchBoardName + "%'"; 
        if (!"".equals(searchBoardRomeLocation))
          where = String.valueOf(where) + " and boardRoomPO.location like '%" + searchBoardRomeLocation + "%'"; 
        if ("checked".equals(searchDatecheckbox))
          if (databaseType.indexOf("mysql") >= 0) {
            where = String.valueOf(where) + " and poo.meetingDate between '" + searchStartDate + "' and '" + searchEndDate + "'";
          } else {
            where = String.valueOf(where) + " and poo.meetingDate between JSDB.FN_STRTODATE('" + searchStartDate + "','L') and JSDB.FN_STRTODATE('" + searchEndDate + "','L')";
          }  
      } else {
        where = String.valueOf(where) + " and poo.beginLong<" + ((new Date()).getTime() + 2592000000L);
      } 
      if ("1".equals(sort)) {
        if (databaseType.indexOf("oracle") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate, to_number(poo.startTime)";
        } else if (databaseType.indexOf("db2") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate, cast(poo.startTime as int)";
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " order by poo.meetingDate, poo.startTime";
        } else {
          where = String.valueOf(where) + " order by poo.meetingDate, convert(int,poo.startTime)";
        } 
      } else if (databaseType.indexOf("oracle") >= 0) {
        where = String.valueOf(where) + " order by poo.meetingDate desc, to_number(poo.startTime) desc";
      } else if (databaseType.indexOf("db2") >= 0) {
        where = String.valueOf(where) + " order by poo.meetingDate desc, cast(poo.startTime as int) desc";
      } else if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " order by poo.meetingDate desc, poo.startTime desc";
      } else {
        where = String.valueOf(where) + " order by poo.meetingDate desc, convert(int,poo.startTime) desc";
      } 
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum,boardRoomApplyPO.notePersonId,boardRoomApplyPO.emcee,boardRoomApplyPO.applyEmp,poo.beginLong,poo.endLong", 
        
        "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchMotif", searchMotif);
    httpServletRequest.setAttribute("searchBoardName", searchBoardName);
    httpServletRequest.setAttribute("searchBoardRomeLocation", searchBoardRomeLocation);
    httpServletRequest.setAttribute("searchDatecheckbox", searchDatecheckbox);
    httpServletRequest.setAttribute("searchStartDate", httpServletRequest.getParameter("searchStartDate"));
    httpServletRequest.setAttribute("searchEndDate", httpServletRequest.getParameter("searchEndDate"));
    httpServletRequest.setAttribute("meetingInformList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchMotif,searchBoardName,searchBoardRomeLocation,searchDatecheckbox,searchStartDate,searchEndDate,type,saveType");
  }
  
  public void meetingSelectList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String workstatus = "100";
    workstatus = httpServletRequest.getParameter("workstatus");
    int offset_page = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && !"".equals(httpServletRequest.getParameter("pager.offset")))
      offset_page = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    String searchMotif = httpServletRequest.getParameter("searchMotif");
    Page page_ol = null;
    String sqlHead = " apply.BDROOMAPPTYPEID,apply.motif,room.name,room.location,apply.emceeName ";
    sqlHead = String.valueOf(sqlHead) + ", work.WORKMAINLINKFILE,work.WF_WORK_ID,work.WORKTYPE,work.WORKACTIVITY, work.WORKTABLE_ID ";
    sqlHead = String.valueOf(sqlHead) + ",work.WORKRECORD_ID,work.WORKFILETYPE,work.WORKSUBMITPERSON,work.WF_SUBMITEMPLOYEE_ID,work.WORKCURSTEP,work.WORKSUBMITTIME,work.WORKPROCESS_ID";
    sqlHead = String.valueOf(sqlHead) + ",apply.APPLYEMPNAME,apply.APPLYORGNAME,time.meetingtime,time.beginlong,time.endlong";
    String table = "  oa_boardroomapply apply ";
    table = String.valueOf(table) + " LEFT JOIN jsf_work work ON apply.BOARDROOMAPPLYID=work.WORKRECORD_ID ";
    table = String.valueOf(table) + " LEFT JOIN oa_boardroom room ON room.BOARDROOMID=apply.BOARDROOMID ";
    table = String.valueOf(table) + " LEFT JOIN oa_boardroom_meetingtime time ON apply.BOARDROOMAPPLYID=time.applyid ";
    String processSql = getMeetingProcessSql(httpServletRequest);
    String where = "WHERE work.workstatus=" + workstatus + " AND " + processSql;
    if (searchMotif != null && !"".equals(searchMotif))
      where = String.valueOf(where) + " and apply.motif like '%" + searchMotif + "%'"; 
    String orderBy = " ORDER BY room.name,time.meetingtime,time.beginlong ASC\t";
    String databaseType = SystemCommon.getDatabaseType();
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List list = page_ol.getResultList();
    httpServletRequest.setAttribute("meetingSelectList", list);
    httpServletRequest.setAttribute("workstatus", workstatus);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("recordCount", String.valueOf(page_ol.getRecordCount()));
    httpServletRequest.setAttribute("pageParameters", "action,workstatus,searchMotif");
  }
  
  private void saveProcess(HttpServletRequest httpServletRequest, String recordId) {
    int moduleId = 10, formType = 1;
    String mainLinkFile = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + 
      recordId + "&boardroomName=" + 
      httpServletRequest.getParameter("name") + 
      "&type=modi";
    String cancelFile = "window.open('/jsoa/routine/boardroom/boardRoomApplyCancel.jsp?workStatus=1&workId=workIdValue&tableId=tableIdValue&processName=" + 
      httpServletRequest.getParameter("fileType") + 
      "&recordId=" + recordId + "&processId=" + 
      httpServletRequest.getParameter("processId") + 
      "&remindValue=&" + 
      "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=0,height=0')";
    (new ProcessSubmit()).saveProcess(httpServletRequest, 
        recordId, 
        moduleId, 
        formType, 
        mainLinkFile, 
        cancelFile);
  }
  
  private void getNextStep(HttpServletRequest httpServletRequest) {
    (new ProcessSubmit()).getNextStep(httpServletRequest);
  }
  
  private void oper(HttpServletRequest httpServletRequest) {
    Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomId"));
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO.setBoardroomApplyId(Long.valueOf(httpServletRequest
          .getParameter("boardroomApplyId")));
    boardroomApplyPO.setStartTime(httpServletRequest.getParameter(
          "destineDateBeginTime"));
    boardroomApplyPO.setEndTime(httpServletRequest.getParameter(
          "destineDateEndTime"));
    boardroomApplyPO.setDepict(httpServletRequest.getParameter("depict"));
    boardroomApplyPO.setDestineDate(new Date(httpServletRequest
          .getParameter("destineDate")));
    boardroomApplyPO.setApplyEmp(Long.valueOf(httpServletRequest
          .getParameter("applyEmp")));
    boardroomApplyPO.setApplyOrgName(httpServletRequest.getParameter(
          "applyOrgName"));
    boardroomApplyPO.setApplyOrg(new Long(httpServletRequest.getParameter(
            "applyOrg")));
    boardroomApplyPO.setEmceeName(httpServletRequest.getParameter(
          "emceeName"));
    boardroomApplyPO.setEmcee(httpServletRequest.getParameter("emcee"));
    boardroomApplyPO.setMotif(httpServletRequest.getParameter("motif"));
    boardroomApplyPO.setAttendeeLeader(httpServletRequest.getParameter(
          "attendeeLeader"));
    boardroomApplyPO.setAttendeeLeaderId(httpServletRequest.getParameter(
          "attendeeLeaderId"));
    boardroomApplyPO.setAttendee(httpServletRequest.getParameter("attendee"));
    boardroomApplyPO.setAttendeeEmpId(httpServletRequest.getParameter(
          "attendeeEmpId"));
    boardroomApplyPO.setNonvoting(httpServletRequest.getParameter(
          "nonvoting"));
    boardroomApplyPO.setNonvotingEmpId(httpServletRequest.getParameter(
          "nonvotingEmpId"));
    boardroomApplyPO.setNotePerson(httpServletRequest.getParameter(
          "notePerson"));
    boardroomApplyPO.setFileNumber(httpServletRequest.getParameter(
          "fileNumber"));
    boardroomApplyPO.setSenderName(httpServletRequest.getParameter(
          "senderName"));
    boardroomApplyPO.setMsg(new Integer(httpServletRequest.getParameter(
            "msg")));
    boardroomApplyPO.setStatus(new Integer(1));
    Long boardroomApplyTypeId = Long.valueOf(httpServletRequest
        .getParameter(
          "boardroomApplyType"));
    Long boardroomApplyType = new Long((httpServletRequest.getParameter(
          "boardroomApplyType") == null) ? "0" : 
        httpServletRequest.getParameter(
          "boardroomApplyType"));
    boolean result = boardRoomBD.modiBoardroomApply(boardroomApplyPO, 
        boardroomApplyTypeId, boardroomId, boardroomApplyType);
    String toMainFile = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + 
      httpServletRequest.getParameter("recordId") + 
      "&boardroomName=" + 
      httpServletRequest.getParameter("name") + 
      "&type=modi";
    (new ProcessSubmit()).operate(httpServletRequest, toMainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void comp(HttpServletRequest httpServletRequest) {
    Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomId"));
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO.setBoardroomApplyId(Long.valueOf(httpServletRequest
          .getParameter("boardroomApplyId")));
    boardroomApplyPO.setStartTime(httpServletRequest.getParameter(
          "destineDateBeginTime"));
    boardroomApplyPO.setEndTime(httpServletRequest.getParameter(
          "destineDateEndTime"));
    boardroomApplyPO.setDepict(httpServletRequest.getParameter("depict"));
    boardroomApplyPO.setDestineDate(new Date(httpServletRequest
          .getParameter("destineDate")));
    boardroomApplyPO.setApplyEmp(Long.valueOf(httpServletRequest
          .getParameter("applyEmp")));
    boardroomApplyPO.setApplyOrgName(httpServletRequest.getParameter(
          "applyOrgName"));
    boardroomApplyPO.setApplyOrg(new Long(httpServletRequest.getParameter(
            "applyOrg")));
    boardroomApplyPO.setEmceeName(httpServletRequest.getParameter(
          "emceeName"));
    boardroomApplyPO.setEmcee(httpServletRequest.getParameter("emcee"));
    boardroomApplyPO.setMotif(httpServletRequest.getParameter("motif"));
    boardroomApplyPO.setAttendeeLeader(httpServletRequest.getParameter(
          "attendeeLeader"));
    boardroomApplyPO.setAttendeeLeaderId(httpServletRequest.getParameter(
          "attendeeLeaderId"));
    boardroomApplyPO.setAttendee(httpServletRequest.getParameter("attendee"));
    boardroomApplyPO.setAttendeeEmpId(httpServletRequest.getParameter(
          "attendeeEmpId"));
    boardroomApplyPO.setNonvoting(httpServletRequest.getParameter(
          "nonvoting"));
    boardroomApplyPO.setNonvotingEmpId(httpServletRequest.getParameter(
          "nonvotingEmpId"));
    boardroomApplyPO.setNotePerson(httpServletRequest.getParameter(
          "notePerson"));
    boardroomApplyPO.setFileNumber(httpServletRequest.getParameter(
          "fileNumber"));
    boardroomApplyPO.setSenderName(httpServletRequest.getParameter(
          "senderName"));
    if (httpServletRequest.getParameter("msg") != null) {
      boardroomApplyPO.setMsg(new Integer(httpServletRequest.getParameter(
              "msg")));
    } else {
      boardroomApplyPO.setMsg(new Integer(0));
    } 
    boardroomApplyPO.setStatus(new Integer(0));
    Long boardroomApplyTypeId = Long.valueOf(httpServletRequest
        .getParameter(
          "boardroomApplyType"));
    Long boardroomApplyType = new Long((httpServletRequest.getParameter(
          "boardroomApplyType") == null) ? "0" : 
        httpServletRequest.getParameter(
          "boardroomApplyType"));
    boolean result = boardRoomBD.modiBoardroomApply(boardroomApplyPO, 
        boardroomApplyTypeId, boardroomId, boardroomApplyType);
    String userId = "";
    if (boardroomApplyPO.getMsg().equals(new Integer(1))) {
      ModelSendMsg sendMsg = new ModelSendMsg();
      userId = (String.valueOf(boardroomApplyPO.getStartTime()) + "-" + 
        boardroomApplyPO.getEndTime() + 
        " 在" + 
        boardroomApplyPO.getBoardroom() != null) ? boardroomApplyPO.getBoardroom().getLocation() : (
        (boardroomApplyPO.getBoardroom() != null) ? boardroomApplyPO.getBoardroom().getName() : ("召开由" + 
        boardroomApplyPO.getEmceeName() + 
        "主持的" + boardroomApplyPO.getDepict() + 
        " (" + boardroomApplyPO.getDestineDate() + 
        ")"));
      boolean falg = sendMsg.sendSystemMessage("新会议通知到达", 
          boardroomApplyPO.getMotif(), userId, "", httpServletRequest);
      if (falg) {
        System.out.println("会议管理 短信提醒发送成功！");
      } else {
        System.out.println("会议管理 短信提醒发送未成功！");
      } 
      RTXSvrApi api = new RTXSvrApi();
      if (api.Init()) {
        sendRTXRemind(String.valueOf(boardroomApplyPO.getEmcee()) + 
            boardroomApplyPO.getAttendeeEmpId() + 
            boardroomApplyPO.getAttendeeLeaderId(), "新会议通知到达", 
            userId);
        api.UnInit();
      } 
    } 
    httpServletRequest.setAttribute("close", "1");
    (new ProcessSubmit()).comp(httpServletRequest);
  }
  
  private void reSub(HttpServletRequest httpServletRequest, BoardRoomActionForm boardRoomActionForm) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomId"));
    Date destineDate = new Date(httpServletRequest.getParameter(
          "destineDate"));
    String destineDateBeginTime = httpServletRequest.getParameter(
        "destineDateBeginTime");
    String destineDateEndTime = httpServletRequest.getParameter(
        "destineDateEndTime");
    BoardRoomApplyPO boardroomApplyPO = 
      (BoardRoomApplyPO)FillBean.transformOneToOne(
        boardRoomActionForm, BoardRoomApplyPO.class);
    boardroomApplyPO.setStatus(new Integer(1));
    boardroomApplyPO.setDestineDate(destineDate);
    boardroomApplyPO.setStartTime(destineDateBeginTime);
    boardroomApplyPO.setEndTime(destineDateEndTime);
    Long boardroomApplyTypeId = Long.valueOf(httpServletRequest
        .getParameter(
          "boardroomApplyType"));
    HashSet<BdroomAppAccessoryPO> Accessory = new HashSet();
    String[] fileName = httpServletRequest.getParameterValues(
        "boardroomFileName");
    String[] saveName = httpServletRequest.getParameterValues(
        "boardroomSaveName");
    if (fileName != null)
      for (int i = 0; i < fileName.length; i++) {
        if (!"".equals(fileName[i])) {
          BdroomAppAccessoryPO bdroomAppAccessoryPO = 
            new BdroomAppAccessoryPO();
          bdroomAppAccessoryPO.setAccessoryname(fileName[i]);
          bdroomAppAccessoryPO.setAccessorysavename(saveName[i]);
          Accessory.add(bdroomAppAccessoryPO);
        } 
      }  
    boardroomApplyPO.setBdRoomAppAccessory(Accessory);
    List boardroomApplyType = boardRoomBD.selectBoardroomApplyType(domainId);
    httpServletRequest.setAttribute("boardroomApplyTypeList", 
        boardroomApplyType);
    Long result = boardRoomBD.addBoardroomApply(boardroomApplyPO, 
        boardroomId, boardroomApplyTypeId);
    saveProcess(httpServletRequest, result.toString());
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void trans(HttpServletRequest httpServletRequest, BoardRoomActionForm boardRoomActionForm) {
    Long boardroomId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomId"));
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO.setBoardroomApplyId(Long.valueOf(httpServletRequest
          .getParameter("boardroomApplyId")));
    boardroomApplyPO.setStartTime(httpServletRequest.getParameter(
          "destineDateBeginTime"));
    boardroomApplyPO.setEndTime(httpServletRequest.getParameter(
          "destineDateEndTime"));
    boardroomApplyPO.setDepict(httpServletRequest.getParameter("depict"));
    boardroomApplyPO.setDestineDate(new Date(httpServletRequest
          .getParameter("destineDate")));
    boardroomApplyPO.setApplyEmp(Long.valueOf(httpServletRequest
          .getParameter("applyEmp")));
    boardroomApplyPO.setApplyOrgName(httpServletRequest.getParameter(
          "applyOrgName"));
    boardroomApplyPO.setApplyOrg(new Long(httpServletRequest.getParameter(
            "applyOrg")));
    boardroomApplyPO.setEmceeName(httpServletRequest.getParameter(
          "emceeName"));
    boardroomApplyPO.setEmcee(httpServletRequest.getParameter("emcee"));
    boardroomApplyPO.setMotif(httpServletRequest.getParameter("motif"));
    boardroomApplyPO.setAttendeeLeader(httpServletRequest.getParameter(
          "attendeeLeader"));
    boardroomApplyPO.setAttendeeLeaderId(httpServletRequest.getParameter(
          "attendeeLeaderId"));
    boardroomApplyPO.setAttendee(httpServletRequest.getParameter("attendee"));
    boardroomApplyPO.setAttendeeEmpId(httpServletRequest.getParameter(
          "attendeeEmpId"));
    boardroomApplyPO.setNonvoting(httpServletRequest.getParameter(
          "nonvoting"));
    boardroomApplyPO.setNonvotingEmpId(httpServletRequest.getParameter(
          "nonvotingEmpId"));
    boardroomApplyPO.setNotePerson(httpServletRequest.getParameter(
          "notePerson"));
    boardroomApplyPO.setFileNumber(httpServletRequest.getParameter(
          "fileNumber"));
    boardroomApplyPO.setSenderName(httpServletRequest.getParameter(
          "senderName"));
    boardroomApplyPO.setMsg(new Integer(httpServletRequest.getParameter(
            "msg")));
    boardroomApplyPO.setStatus(new Integer(1));
    Long boardroomApplyTypeId = Long.valueOf(httpServletRequest
        .getParameter(
          "boardroomApplyType"));
    Long boardroomApplyType = new Long((httpServletRequest.getParameter(
          "boardroomApplyType") == null) ? "0" : 
        httpServletRequest.getParameter(
          "boardroomApplyType"));
    boolean result = boardRoomBD.modiBoardroomApply(boardroomApplyPO, 
        boardroomApplyTypeId, boardroomId, boardroomApplyType);
    String mainFile = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + 
      httpServletRequest.getParameter("recordId") + 
      "&boardroomName=" + 
      httpServletRequest.getParameter("name") + 
      "&type=modi";
    (new ProcessSubmit()).transition(httpServletRequest, mainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void untread(HttpServletRequest httpServletRequest) {
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    boolean result = boardRoomBD.dissentBoardroomApply(Long.valueOf(
          httpServletRequest.getParameter("boardroomApplyId")));
    (new ProcessSubmit()).Untread(httpServletRequest);
  }
  
  public void boardRoomInfo(HttpServletRequest httpServletRequest, BoardRoomActionForm voitureApplyActionForm) {
    BoardRoomBD vmbd = new BoardRoomBD();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String boardRoomId = httpServletRequest.getParameter("boardRoomId");
    httpServletRequest.setAttribute("boardRoomId", boardRoomId);
    List<Date> searchDate = new ArrayList();
    List<String> placeList = new ArrayList();
    Date searchStartDate = new Date();
    Date searchEndDate = new Date();
    if (httpServletRequest.getParameter("searchStartDate") != null)
      searchStartDate = new Date(httpServletRequest.getParameter("searchStartDate")); 
    long day = 3L;
    searchDate.add(searchStartDate);
    placeList.add(vmbd.getBoardRoomInfo(boardRoomId, formatter.format(searchStartDate)));
    for (int i = 1; i < day; i++) {
      Date searchNextDate = new Date();
      long myTime = searchStartDate.getTime() / 1000L + (86400 * i);
      searchNextDate.setTime(myTime * 1000L);
      searchDate.add(searchNextDate);
      placeList.add(vmbd.getBoardRoomInfo(boardRoomId, formatter.format(searchNextDate)));
    } 
    httpServletRequest.setAttribute("searchDate", searchDate);
    httpServletRequest.setAttribute("voiturePlace", placeList);
  }
  
  private String getUserByOrg(String orgIdStr) {
    String orgIds = "";
    if (orgIdStr == null || orgIdStr.length() < 1)
      return orgIds; 
    String[] orgIdArr = orgIdStr.split(",");
    DbOpt dbopt = null;
    ResultSet rs = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < orgIdArr.length; i++) {
        String orgCode = dbopt.executeQueryToStr(
            "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
            orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
            orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && 
              orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    return orgIds;
  }
  
  private String getUserByGroup(String groupIdStr) {
    String userStr = "";
    if (groupIdStr == null || groupIdStr.length() < 1)
      return userStr; 
    String[] groupIdArr = groupIdStr.split(",");
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < groupIdArr.length; i++) {
        String empIdStr = dbopt.executeQueryToStr(
            "select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + 
            groupIdArr[i]);
        if (empIdStr != null && empIdStr.length() > 1)
          userStr = String.valueOf(userStr) + empIdStr; 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    if (userStr != null && userStr.length() > 1)
      userStr = userStr.substring(1, 
          userStr.length() - 
          1).replaceAll("$$", ","); 
    return userStr;
  }
  
  private void sendRTXRemind(String userIds, String title, String content) {
    DataSource ds = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    ConversionString con = new ConversionString(userIds);
    String userIdStr = String.valueOf(con.getUserIdString()) + ",";
    userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
    userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
    StringBuffer allUserIdBuffer = new StringBuffer();
    userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
    while (userIdStr.startsWith(","))
      userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
    while (userIdStr.endsWith(","))
      userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
    try {
      ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(
          "select useraccounts from org_employee where emp_id in (" + 
          userIdStr + ")");
      allUserIdBuffer = new StringBuffer();
      while (rs.next())
        allUserIdBuffer.append(rs.getString(1)).append(","); 
      rs.next();
      stmt.close();
      conn.close();
      if (allUserIdBuffer.length() > 1)
        allUserIdBuffer = allUserIdBuffer.deleteCharAt(allUserIdBuffer
            .length() - 1); 
      RealTimeUtil util = new RealTimeUtil();
      util.sendNotify(allUserIdBuffer.toString(), title, content, "0", 
          "0");
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException sQLException) {} 
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
  }
  
  public void boardRoomList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    BoardRoomBD rbd = new BoardRoomBD();
    String scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "会议管理", "维护")) + " and boardRoomPO.domainId=" + domainId;
    String maintenanceBoardRoomIds = rbd.maintenanceBoardRoom(scopeWhere);
    httpServletRequest.setAttribute("maintenanceBoardRoomIds", maintenanceBoardRoomIds);
    if ((new ManagerService()).hasRightTypeName((String)userID, "会议管理", "审核")) {
      scopeWhere = " 1=1 ";
    } else {
      scopeWhere = " 1<>1 ";
    } 
    String auditingBoardRoomIds = rbd.maintenanceBoardRoom(scopeWhere);
    httpServletRequest.setAttribute("auditingBoardRoomIds", auditingBoardRoomIds);
    String tmp = "";
    if (maintenanceBoardRoomIds != null && 
      !"".equals(maintenanceBoardRoomIds)) {
      if (maintenanceBoardRoomIds.endsWith(","))
        maintenanceBoardRoomIds = maintenanceBoardRoomIds.substring(0, 
            maintenanceBoardRoomIds.length() - 1); 
      tmp = " or boardRoomPO.boardroomId in (" + maintenanceBoardRoomIds + 
        ") ";
    } 
    String useScopeIds = " and (boardRoomPO.useScopeId like '%$" + userID + "$%'";
    String orgIds = rbd.getOrgIdsByUserId((String)userID);
    String org_sql = "";
    if (orgIds != null && orgIds.length() > 0) {
      String[] tt = orgIds.substring(1, orgIds.length() - 1).split("\\*\\*");
      for (int i = 0; i < tt.length; i++) {
        org_sql = String.valueOf(org_sql) + " or boardRoomPO.scopeOrg like '%*" + tt[i] + "*%' ";
        useScopeIds = String.valueOf(useScopeIds) + " or boardRoomPO.useScopeId like '%*" + tt[i] + "*%'  ";
      } 
    } 
    String groupIds = rbd.getGroupIdsByUserId((String)userID);
    String group_sql = "";
    if (groupIds != null && groupIds.length() > 0) {
      String[] tt = groupIds.substring(1, groupIds.length() - 1).split("\\@\\@");
      for (int i = 0; i < tt.length; i++) {
        group_sql = String.valueOf(group_sql) + " or boardRoomPO.scopeGroup like '%@" + tt[i] + "@%' ";
        useScopeIds = String.valueOf(useScopeIds) + " or boardRoomPO.useScopeId like '%@" + tt[i] + "@%'  ";
      } 
    } 
    useScopeIds = String.valueOf(useScopeIds) + " or createdEmp=" + userID + " )";
    String where = " where (boardRoomPO.scopeEmp like '%$" + userID + 
      "$%' or boardRoomPO.limit=0 " + tmp + org_sql + group_sql + 
      ") and boardRoomPO.domainId=" + domainId + useScopeIds;
    String databaseType = SystemCommon.getDatabaseType();
    if ("mysql".equals(databaseType)) {
      where = String.valueOf(where) + " order by CONVERT(boardRoomPO.name USING gbk),boardRoomPO.boardroomId desc";
    } else {
      where = String.valueOf(where) + " order by boardRoomPO.name,boardRoomPO.boardroomId desc";
    } 
    int pageSize = 999999;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomPO.boardroomId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomPO.cost,boardRoomPO.emphasis,boardRoomPO.doorNumber,boardRoomPO.chargeType ", 
        "com.js.oa.routine.boardroom.po.BoardRoomPO boardRoomPO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardRoomList", list);
  }
  
  public void boardRoomUseInfo(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    String databaseType = 
      SystemCommon.getDatabaseType();
    String where = " where boardRoomApplyPO.status = 0";
    if ((new ManagerService()).hasRightTypeName(curUserId, "会议管理", "维护")) {
      where = String.valueOf(where) + " and 1=1 ";
    } else {
      where = String.valueOf(where) + " and (boardRoomApplyPO.emcee like '%$" + curUserId + 
        "$%' or " + 
        "boardRoomApplyPO.attendeeEmpId like '%$" + curUserId + 
        "$%' or " + 
        "boardRoomApplyPO.applyEmp='" + curUserId + "' or " + 
        "boardRoomApplyPO.attendeeLeaderId like '%$" + curUserId + 
        "$%' or " + 
        "boardRoomApplyPO.nonvotingEmpId like '%$" + curUserId + 
        "$%') ";
    } 
    if (databaseType.indexOf("mysql") >= 0) {
      where = String.valueOf(where) + 
        " and poo.meetingDate between '" + 
        searchStartDate + "' and '" + 
        searchEndDate + "'";
    } else {
      where = String.valueOf(where) + 
        " and poo.meetingDate between JSDB.FN_STRTODATE('" + 
        searchStartDate + 
        "','L') and JSDB.FN_STRTODATE('" + 
        searchEndDate + "','L')";
    } 
    where = String.valueOf(where) + " and boardRoomApplyPO.domainId=" + domainId;
    if (httpServletRequest.getParameter("orderbytime") != null) {
      if (httpServletRequest.getParameter("orderbytime").equals("0")) {
        if (databaseType.indexOf("oracle") >= 0) {
          where = String.valueOf(where) + 
            " order by poo.meetingDate, to_number(poo.startTime)";
        } else if (databaseType.indexOf("db2") >= 0) {
          where = String.valueOf(where) + 
            " order by poo.meetingDate, cast(poo.startTime as int)";
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + 
            " order by poo.meetingDate, poo.startTime";
        } else {
          where = String.valueOf(where) + 
            " order by poo.meetingDate, convert(int,poo.startTime)";
        } 
      } else if (databaseType.indexOf("oracle") >= 0) {
        where = String.valueOf(where) + 
          " order by poo.meetingDate desc, to_number(poo.startTime) desc";
      } else if (databaseType.indexOf("db2") >= 0) {
        where = String.valueOf(where) + 
          " order by poo.meetingDate desc, cast(poo.startTime as int) desc";
      } else if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + 
          " order by poo.meetingDate, poo.startTime desc";
      } else {
        where = String.valueOf(where) + 
          " order by poo.meetingDate desc, convert(int,poo.startTime) desc";
      } 
    } else if (httpServletRequest.getParameter("orderbystatus") != null) {
      if (httpServletRequest.getParameter("orderbystatus").equals("0")) {
        where = String.valueOf(where) + " order by boardRoomPO.name  ";
      } else {
        where = String.valueOf(where) + " order by boardRoomPO.name desc  ";
      } 
    } else {
      where = String.valueOf(where) + 
        " order by poo.meetingDate desc,poo.startTime desc";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "boardRoomApplyPO.boardroomApplyId,boardRoomPO.boardroomId,boardRoomPO.name,boardRoomPO.boardroomType,boardRoomPO.location,boardRoomApplyPO.applyEmpName,boardRoomApplyPO.applyOrgName,poo.meetingDate,poo.startTime,poo.endTime,boardRoomApplyPO.emceeName,boardRoomApplyPO.motif,boardRoomApplyPO.bdroomAppType,boardRoomApplyPO.applyEmp,poo.id,poo.status,boardRoomApplyPO.attendee,boardRoomApplyPO.attendeeLeader,boardRoomApplyPO.nonvoting,boardRoomApplyPO.notePerson,poo.sortNum", 

        
        "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.bdroomAppType po join boardRoomApplyPO.meetingTime poo ", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("boardroomUseList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchStartDate,searchEndDate,orderbytime,orderbystatus");
  }
  
  private void viewUsers(HttpServletRequest request) {
    String boardroomApplyId = request.getParameter("boardroomApplyId");
    String empName = request.getParameter("empName");
    String viewSQL = "po.empId,po.empName,po.orgId,po.orgName,po.replyDate,empPO.userAccounts";
    String fromSQL = "com.js.oa.routine.boardroom.po.BoardroomPersons po,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO ";
    String whereSQL = " where po.empId=empPO.empId and po.apply.boardroomApplyId=" + boardroomApplyId + 
      " and po.status='100000' ";
    if (!isEmpty(empName))
      whereSQL = String.valueOf(whereSQL) + " and po.empName like '%" + empName + "%' "; 
    whereSQL = String.valueOf(whereSQL) + " order by orgPO.orgIdString";
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(99999999);
    page.setcurrentPage(1);
    List<Object[]> list = page.getResultList();
    String havingSQL = " having po.orgId in (-1";
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        havingSQL = String.valueOf(havingSQL) + "," + obj[2].toString();
      }  
    havingSQL = String.valueOf(havingSQL) + ") ";
    Page page_2 = new Page("distinct po.orgId", "com.js.oa.routine.boardroom.po.BoardroomPersons po ", "group by po.orgId,po.orgName " + havingSQL + " order by po.orgId");
    page_2.setPageSize(pageSize);
    page_2.setcurrentPage(currentPage);
    List list_2 = page_2.getResultList();
    String recordCount = String.valueOf(page_2.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("list2", list_2);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,boardroomApplyId,empName");
  }
  
  public String getorgName(int orgId) {
    String orgName = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "select orgname  from OA_BOARDROOM_PERSONS where replydate in (select max(replydate) from OA_BOARDROOM_PERSONS where orgid=" + orgId + ")";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        orgName = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return orgName;
  }
  
  private void unviewUsers(HttpServletRequest request) {
    String boardroomApplyId = request.getParameter("boardroomApplyId");
    String empName = request.getParameter("empName");
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Map map = boardRoomBD.selectBoardroomApply(new Long(boardroomApplyId));
    BoardRoomApplyPO po = (BoardRoomApplyPO)map.get("boardroomApplyPO");
    String emcee = po.getEmcee();
    String attendeeLeader = po.getAttendeeLeaderId();
    String notePerson = po.getNotePerson();
    String attendee = po.getAttendeeEmpId();
    String nonvoting = po.getNonvotingEmpId();
    Long applyEmp = po.getApplyEmp();
    String idStr = "";
    if (!isEmpty(emcee))
      idStr = String.valueOf(idStr) + emcee; 
    if (!isEmpty(attendeeLeader))
      idStr = String.valueOf(idStr) + attendeeLeader; 
    if (!isEmpty(attendee))
      idStr = String.valueOf(idStr) + attendee; 
    if (!isEmpty(nonvoting))
      idStr = String.valueOf(idStr) + nonvoting; 
    if (!isEmpty(applyEmp))
      idStr = String.valueOf(idStr) + "$" + applyEmp + "$"; 
    Object object = null;
    if (!isEmpty(notePerson)) {
      Page page_1 = new Page("po.empId,po.empName", 
          "com.js.system.vo.usermanager.EmployeeVO po", 
          "where po.empName = '" + 
          notePerson + "'");
      page_1.setPageSize(1);
      page_1.setcurrentPage(1);
      List<Object[]> list_1 = page_1.getResultList();
      if (list_1 != null && list_1.size() > 0) {
        Object[] obj = list_1.get(0);
        object = obj[0];
      } 
    } 
    if (!isEmpty(object))
      idStr = String.valueOf(idStr) + "$" + object + "$"; 
    String viewSQL_2 = "po.empId,po.empName";
    String fromSQL_2 = 
      "com.js.oa.routine.boardroom.po.BoardroomPersons po";
    String whereSQL_2 = " where po.apply.boardroomApplyId=" + 
      boardroomApplyId + 
      " and po.status='100000'";
    Page page_2 = new Page(viewSQL_2, fromSQL_2, whereSQL_2);
    page_2.setPageSize(9999999);
    page_2.setcurrentPage(1);
    List<Object[]> list_2 = page_2.getResultList();
    if (list_2 != null && list_2.size() > 0)
      for (int i = 0; i < list_2.size(); i++) {
        Object[] obj = list_2.get(i);
        if (idStr.indexOf("$" + obj[0] + "$") != -1)
          idStr = idStr.replaceAll("\\$" + obj[0] + "\\$", ""); 
      }  
    String empIds = "-1";
    if (!isEmpty(idStr)) {
      idStr = "$" + idStr + "$";
      String[] ids = idStr.split("\\$\\$");
      for (int i = 0; i < ids.length; i++) {
        if (!isEmpty(ids[i]))
          empIds = String.valueOf(empIds) + "," + ids[i]; 
      } 
    } 
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String viewSQL = "po.empId,po.empName,poo.orgId,poo.orgName,po.empId,po.userAccounts";
    String fromSQL = 
      "com.js.system.vo.usermanager.EmployeeVO po join po.organizations poo";
    String whereSQL = " where po.empId in(" + empIds + ")";
    if (!isEmpty(empName))
      whereSQL = String.valueOf(whereSQL) + " and po.empName like '%" + empName + "%' "; 
    whereSQL = String.valueOf(whereSQL) + " order by poo.orgIdString ";
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(99999999);
    page.setcurrentPage(1);
    List<Object[]> list = page.getResultList();
    String havingSQL = " having po.orgId in (-1";
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        havingSQL = String.valueOf(havingSQL) + "," + obj[2].toString();
      }  
    havingSQL = String.valueOf(havingSQL) + ") ";
    Page page_3 = new Page("po.orgId,po.orgName", 
        "com.js.system.vo.usermanager.EmployeeVO poo join poo.organizations po", 
        " group by po.orgId,po.orgName " + havingSQL + 
        " order by po.orgId");
    page_3.setPageSize(pageSize);
    page_3.setcurrentPage(currentPage);
    List list_3 = page_3.getResultList();
    String recordCount = String.valueOf(page_3.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("list2", list_3);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", 
        "action,boardroomApplyId,empName");
  }
  
  private void executeStatus(HttpServletRequest request) {
    String boardroomApplyId = request.getParameter("boardroomApplyId");
    String meetingId = request.getParameter("meetingId");
    request.setAttribute("meetingId", meetingId);
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Map map = boardRoomBD.selectBoardroomApply(new Long(boardroomApplyId));
    BoardRoomApplyPO po = (BoardRoomApplyPO)map.get("boardroomApplyPO");
    request.setAttribute("boardroomApplyPO", po);
    request.setAttribute("meetingTime", po.getMeetingTime());
    request.setAttribute("notePerson", String.valueOf(po.getEmcee()) + po.getNotePersonId());
    String viewSQL = "";
    String fromSQL = "";
    String whereSQL = "";
    whereSQL = (new StringBuilder(String.valueOf(whereSQL))).toString();
    List statusPOList = boardRoomBD.getExecuteStatusList(new Long(boardroomApplyId), new Long(meetingId));
    if (statusPOList == null || statusPOList.size() == 0) {
      String emcee = po.getEmcee();
      String attendeeLeader = po.getAttendeeLeaderId();
      String notePerson = po.getNotePerson();
      String attendee = po.getAttendeeEmpId();
      String nonvoting = po.getNonvotingEmpId();
      Long applyEmp = po.getApplyEmp();
      String idStr = "";
      if (!isEmpty(emcee))
        idStr = String.valueOf(idStr) + emcee; 
      if (!isEmpty(attendeeLeader))
        idStr = String.valueOf(idStr) + attendeeLeader; 
      if (!isEmpty(attendee))
        idStr = String.valueOf(idStr) + attendee; 
      if (!isEmpty(nonvoting))
        idStr = String.valueOf(idStr) + nonvoting; 
      if (!isEmpty(applyEmp))
        idStr = String.valueOf(idStr) + "$" + applyEmp + "$"; 
      Object object = null;
      if (!isEmpty(notePerson)) {
        Page page_1 = new Page("po.empId,po.empName", 
            "com.js.system.vo.usermanager.EmployeeVO po", 
            "where po.empName = '" + 
            notePerson + "'");
        page_1.setPageSize(1);
        page_1.setcurrentPage(1);
        List<Object[]> list_1 = page_1.getResultList();
        if (list_1 != null && list_1.size() > 0) {
          Object[] obj = list_1.get(0);
          object = obj[0];
        } 
      } 
      if (!isEmpty(object))
        idStr = String.valueOf(idStr) + "$" + object + "$"; 
      String empIds = "-1";
      if (!isEmpty(idStr)) {
        idStr = "$" + idStr + "$";
        String[] ids = idStr.split("\\$\\$");
        for (int i = 0; i < ids.length; i++) {
          if (!isEmpty(ids[i]))
            empIds = String.valueOf(empIds) + "," + ids[i]; 
        } 
      } 
      viewSQL = "po.empId,po.empName,poo.orgId,poo.orgName,po.empId,po.empId,po.empId";
      fromSQL = "com.js.system.vo.usermanager.EmployeeVO po join po.organizations poo";
      whereSQL = " where po.empId in(" + empIds + ")";
      whereSQL = String.valueOf(whereSQL) + " order by poo.orgIdString ";
      Page page_2 = new Page(viewSQL, fromSQL, whereSQL);
      page_2.setPageSize(999999);
      page_2.setcurrentPage(1);
      List<Object[]> list_2 = page_2.getResultList();
      if (list_2 != null && list_2.size() > 0) {
        List<BoardroomExecuteStatusPO> ls = new ArrayList();
        for (int i = 0; i < list_2.size(); i++) {
          Object[] obj = list_2.get(i);
          BoardroomExecuteStatusPO bespo = 
            new BoardroomExecuteStatusPO();
          bespo.setApplyId(new Long(boardroomApplyId));
          bespo.setMeetingId(new Long(meetingId));
          bespo.setEmpId(new Long((String)obj[0]));
          bespo.setEmpName((String)obj[1]);
          bespo.setOrgId(new Long((String)obj[2]));
          bespo.setOrgName((String)obj[3]);
          bespo.setIsJoined("1");
          bespo.setSortNum(new Integer(i));
          ls.add(bespo);
        } 
        boardRoomBD.saveExecuteStatus(new Long(boardroomApplyId), new Long(meetingId), ls, null, null);
      } 
      request.setAttribute("isNew", "true");
    } 
    viewSQL = "po.empId,po.empName,po.orgId,po.orgName,po.isJoined,po.memos,po.id";
    fromSQL = "com.js.oa.routine.boardroom.po.BoardroomExecuteStatusPO po";
    whereSQL = " where po.applyId=" + boardroomApplyId + " and po.meetingId=" + meetingId;
    whereSQL = String.valueOf(whereSQL) + " order by po.sortNum ";
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,boardroomApplyId,meetingId");
  }
  
  private void saveExecuteStatus(HttpServletRequest request) {
    String boardroomApplyId = request.getParameter("boardroomApplyId");
    String meetingId = request.getParameter("meetingId");
    request.setAttribute("meetingId", meetingId);
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Map map = boardRoomBD.selectBoardroomApply(new Long(boardroomApplyId));
    BoardRoomApplyPO po = (BoardRoomApplyPO)map.get("boardroomApplyPO");
    request.setAttribute("boardroomApplyPO", po);
    request.setAttribute("meetingTime", po.getMeetingTime());
    List<BoardroomExecuteStatusPO> list = new ArrayList();
    BoardroomExecuteStatusPO bespo = null;
    String description = request.getParameter("descriptions");
    String[] bespoIds = request.getParameterValues("bespoId");
    String bespoIds_1 = "-1";
    if (bespoIds != null && bespoIds.length > 0)
      for (int i = 0; i < bespoIds.length; i++) {
        if (!isEmpty(bespoIds[i]))
          bespoIds_1 = String.valueOf(bespoIds_1) + "," + bespoIds[i].trim(); 
      }  
    String pageOffSet = request.getParameter("pageOffSet");
    int __pageOffSet = Integer.parseInt(pageOffSet);
    String[] empIds = request.getParameterValues("empId");
    String[] empNames = request.getParameterValues("empName");
    String[] orgNames = request.getParameterValues("orgName");
    String[] orgIds = request.getParameterValues("orgId");
    String[] isjoineds = request.getParameterValues("isjoined_val");
    String[] memos = request.getParameterValues("memo");
    if (empNames != null && empNames.length > 0)
      for (int i = 0; i < empNames.length; i++) {
        bespo = new BoardroomExecuteStatusPO();
        bespo.setApplyId(new Long(boardroomApplyId));
        bespo.setMeetingId(new Long(meetingId));
        if (!isEmpty(empIds[i]))
          bespo.setEmpId(new Long(empIds[i])); 
        bespo.setEmpName(empNames[i]);
        if (!isEmpty(orgIds[i]))
          bespo.setOrgId(new Long(orgIds[i])); 
        bespo.setOrgName(orgNames[i]);
        bespo.setIsJoined(isjoineds[i]);
        bespo.setMemos(memos[i]);
        bespo.setSortNum(new Integer(__pageOffSet + i));
        list.add(bespo);
      }  
    int j = 8001;
    String[] empNames_1 = request.getParameterValues("empName_1");
    String[] orgNames_1 = request.getParameterValues("orgName_1");
    String[] isjoineds_1 = request.getParameterValues("isjoined_val_1");
    String[] memos_1 = request.getParameterValues("memo_1");
    if (empNames_1 != null && empNames_1.length > 0)
      for (int i = 0; i < empNames_1.length; i++) {
        bespo = new BoardroomExecuteStatusPO();
        bespo.setApplyId(new Long(boardroomApplyId));
        bespo.setMeetingId(new Long(meetingId));
        bespo.setEmpName(empNames_1[i]);
        bespo.setOrgName(orgNames_1[i]);
        bespo.setIsJoined(isjoineds_1[i]);
        bespo.setMemos(memos_1[i]);
        bespo.setSortNum(new Integer(j + i));
        list.add(bespo);
      }  
    boardRoomBD.saveExecuteStatus(new Long(boardroomApplyId), new Long(meetingId), list, description, bespoIds_1);
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
  
  public static String getWeek(String sdate, String num) {
    Date dd = strToDate(sdate);
    Calendar c = Calendar.getInstance();
    c.setTime(dd);
    if (c.get(7) == 1)
      c.add(5, -7); 
    if (num.equals("1")) {
      c.set(7, 2);
    } else if (num.equals("2")) {
      c.set(7, 3);
    } else if (num.equals("3")) {
      c.set(7, 4);
    } else if (num.equals("4")) {
      c.set(7, 5);
    } else if (num.equals("5")) {
      c.set(7, 6);
    } else if (num.equals("6")) {
      c.set(7, 7);
    } else if (num.equals("0")) {
      c.set(7, 1);
      c.add(5, 7);
    } 
    return (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
  }
  
  public boolean isExitMeetApplay(HttpServletRequest httpServletRequest, Long boardroomId, Long boardroomApplyTypeId) {
    boolean flag = false;
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Date destineDate = new Date(httpServletRequest.getParameter("destineDate"));
    String destineDateBeginTime = httpServletRequest.getParameter("destineDateBeginTime");
    String destineDateEndTime = httpServletRequest.getParameter("destineDateEndTime");
    Date endDate = (httpServletRequest.getParameter("endDate") == null || "".equals(httpServletRequest.getParameter("endDate")) || 
      "null".equals(httpServletRequest.getParameter("endDate"))) ? 
      new Date(httpServletRequest.getParameter("destineDate")) : new Date(httpServletRequest.getParameter("endDate"));
    Long beginDateLong = Long.valueOf(destineDate.getTime() + Long.valueOf(destineDateBeginTime).longValue() * 1000L);
    Long endDateLong = Long.valueOf(endDate.getTime() + Long.valueOf(destineDateEndTime).longValue() * 1000L);
    String boardroomApplyId = (httpServletRequest.getParameter("boardroomApplyId") == null || "".equals(httpServletRequest.getParameter("boardroomApplyId")) || 
      "null".equals(httpServletRequest.getParameter("boardroomApplyId"))) ? "-1" : httpServletRequest.getParameter("boardroomApplyId");
    flag = boardRoomBD.applyTime((String)boardroomId, boardroomApplyId, beginDateLong, endDateLong);
    return flag;
  }
  
  protected boolean isEmpty(Object o) {
    if (o == null)
      return true; 
    String s = null;
    if (!(o instanceof String)) {
      s = o.toString();
    } else {
      s = (String)o;
    } 
    if (s == null || s.trim().length() == 0 || "null".equals(s))
      return true; 
    return false;
  }
  
  private String getMeetingProcessSql(HttpServletRequest request) {
    StringBuffer processSql = new StringBuffer("(1>2");
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String meetDatabaseId = "-1";
      ResultSet rs = stmt.executeQuery("SELECT wf_immoform_id FROM jsf_immobilityform WHERE wf_module_id=15");
      if (rs.next())
        meetDatabaseId = rs.getString(1); 
      rs.close();
      rs = stmt.executeQuery("SELECT wf_workflowprocess_id FROM jsf_workflowprocess WHERE ACCESSDATABASEID=" + meetDatabaseId);
      while (rs.next())
        processSql.append(" or work.WORKPROCESS_ID=").append(rs.getString(1)); 
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
    processSql.append(")");
    return processSql.toString();
  }
}
