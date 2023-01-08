package com.js.wap.action;

import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.wap.util.WapStringTool;
import com.js.wap.util.WapUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WapMeetingAction extends DispatchAction {
  public ActionForward getMeetList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String version = (String)session.getAttribute("wapVersion");
    list(request);
    return actionMapping.findForward(WapStringTool.getForwardStr(version, "list"));
  }
  
  public void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String destineDate = dateFormat.format(new Date());
    StringBuffer para = new StringBuffer("boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomApplyPO.applyEmpName,");
    para.append("boardRoomApplyPO.attendeeLeader,boardRoomApplyPO.emceeName,boardRoomPO.name,boardRoomPO.location,");
    para.append("poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum ");
    StringBuffer from = new StringBuffer(" BoardRoomApplyPO boardRoomApplyPO ");
    from.append(" join boardRoomApplyPO.boardroom boardRoomPO ");
    from.append(" join boardRoomApplyPO.meetingTime poo  ");
    StringBuffer where = new StringBuffer(" where (boardRoomApplyPO.emcee like '%$" + userId + "$%' ");
    where.append(" or boardRoomApplyPO.attendeeEmpId like '%$" + userId + "$%' ");
    where.append(" or boardRoomApplyPO.attendeeLeaderId like '%$" + userId + "$%' ");
    where.append(" or boardRoomApplyPO.nonvotingEmpId like '%$" + userId + "$%') ");
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where.append(" and boardRoomApplyPO.status =0");
      where.append(" and poo.meetingDate >= '" + destineDate + " 00:00:00' and poo.meetingDate<'" + destineDate + " 23:59:59'");
    } else {
      where.append(" and boardRoomApplyPO.status =0 and poo.meetingDate = JSDB.FN_STRTODATE('" + 
          destineDate + "','S') ");
    } 
    where.append(" and boardRoomApplyPO.domainId=0 order by poo.meetingDate, poo.startTime desc ");
    int pageSize = WapUtil.LIMITED;
    int offset = 0;
    if (request.getParameter("beginIndex") != null && request.getParameter("beginIndex").equals(""))
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para.toString(), from.toString(), where.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    for (int i = 0; i < list.size(); i++)
      Object[] obj = list.get(i); 
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
  }
}
