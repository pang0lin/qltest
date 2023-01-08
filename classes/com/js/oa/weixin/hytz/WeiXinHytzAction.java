package com.js.oa.weixin.hytz;

import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.wap.util.WapUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinHytzAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String method = request.getParameter("action");
    if ("list".equals(method)) {
      list(request, session);
      return mapping.findForward("list");
    } 
    if ("listweek".equals(method)) {
      listweek(request, session);
      return mapping.findForward("listweek");
    } 
    return null;
  }
  
  public void list(HttpServletRequest request, HttpSession session) {
    String userID = session.getAttribute("userId").toString();
    String type = request.getParameter("type");
    String sort = "1";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String destineDate = dateFormat.format(new Date());
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    StringBuffer para = new StringBuffer("boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomApplyPO.applyEmpName,");
    para.append("boardRoomApplyPO.attendeeLeader,boardRoomApplyPO.emceeName,boardRoomPO.name,boardRoomPO.location,");
    para.append("poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum ");
    StringBuffer from = new StringBuffer(" com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo ");
    String where = "";
    int domainId = 0;
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
        if (keyword != null && !"".equals(keyword))
          where = String.valueOf(where) + " and boardRoomApplyPO.motif like '%" + keyword + "%' "; 
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
        if (keyword != null && !"".equals(keyword))
          where = String.valueOf(where) + " and boardRoomApplyPO.motif like '%" + keyword + "%' "; 
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
      if (keyword != null && !"".equals(keyword))
        where = String.valueOf(where) + " and boardRoomApplyPO.motif like '%" + keyword + "%' "; 
      String databaseType = SystemCommon.getDatabaseType();
      where = String.valueOf(where) + " and poo.beginLong<" + ((new Date()).getTime() + 2592000000L);
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
    int pageSize = WapUtil.LIMITED;
    int offset = 0;
    if (request.getParameter("beginIndex") != null && !request.getParameter("beginIndex").equals(""))
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para.toString(), from.toString(), where.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("type", type);
    request.setAttribute("keyword", keyword);
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
  }
  
  public void listweek(HttpServletRequest request, HttpSession session) {
    Calendar nowDate = Calendar.getInstance();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    StringTokenizer stt = new StringTokenizer(orgId, "$");
    int domainId = 0;
    String[] orgIdArr = new String[stt.countTokens()];
    int i = 0;
    while (stt.hasMoreTokens()) {
      orgIdArr[i] = stt.nextToken();
      i++;
    } 
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String databaseType = 
      SystemCommon.getDatabaseType();
    String viewSql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum,boardRoomApplyPO.applyDate";
    String fromSql = "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo";
    String whereSql = "";
    int dayOfWeek = nowDate.get(7);
    Calendar monday = Calendar.getInstance();
    monday.setTime(nowDate.getTime());
    monday.add(5, 2 - dayOfWeek);
    Calendar sunday = Calendar.getInstance();
    sunday.setTime(nowDate.getTime());
    sunday.add(5, 8 - dayOfWeek);
    if (databaseType.indexOf("mysql") >= 0) {
      whereSql = "where poo.status=0 and poo.meetingDate between '" + 
        monday.get(1) + "-" + (
        monday.get(2) + 1) + "-" + 
        monday.get(5) + 
        " 00:00:00' and '" + 
        sunday.get(1) + "-" + (
        sunday.get(2) + 1) + "-" + 
        sunday.get(5) + 
        " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
        userId + 
        "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
        userId + "%' ";
    } else {
      whereSql = 
        "where poo.status=0 and poo.meetingDate between JSDB.FN_STRTODATE('" + 
        monday.get(1) + "-" + (
        monday.get(2) + 1) + "-" + 
        monday.get(5) + 
        " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
        sunday.get(1) + "-" + (
        sunday.get(2) + 1) + "-" + 
        sunday.get(5) + 
        " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
        userId + 
        "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
        userId + "%' ";
    } 
    for (int p = 0; i < orgIdArr.length; p++) {
      if (orgIdArr[p] != null && orgIdArr[p].length() > 1)
        whereSql = String.valueOf(whereSql) + " or boardRoomApplyPO.attendeeEmpId like '%*" + 
          orgIdArr[p] + "*%' "; 
      if (p + 1 < orgIdArr.length && orgIdArr[p] != null && 
        orgIdArr[p].length() > 1)
        whereSql = String.valueOf(whereSql) + " or boardRoomApplyPO.attendeeEmpId like '%*" + 
          orgIdArr[p + 1] + "*%' "; 
    } 
    whereSql = String.valueOf(whereSql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
      userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
      userId + 
      "$%' or boardRoomApplyPO.notePersonId like '%$" + userId + "$%') " + 
      "and boardRoomApplyPO.status=0 and boardRoomPO.domainId=" + 
      domainId + " ";
    if (keyword != null && !"".equals(keyword))
      whereSql = String.valueOf(whereSql) + " and boardRoomApplyPO.motif like '%" + keyword + "%' "; 
    whereSql = String.valueOf(whereSql) + " order by poo.startTime desc";
    int pageSize = WapUtil.LIMITED;
    int offset = 0;
    if (request.getParameter("beginIndex") != null && !request.getParameter("beginIndex").equals(""))
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql.toString(), fromSql.toString(), whereSql.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("keyword", keyword);
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
  }
}
