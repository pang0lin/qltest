package com.js.oa.userdb.statistics.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.userdb.statistics.po.JsfStatisticsManage;
import com.js.oa.userdb.statistics.service.JsfStatisticsManageService;
import com.js.util.page.Page;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class JsfStatisticsManageAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    JsfStatisticsManageForm form = (JsfStatisticsManageForm)actionForm;
    JsfStatisticsManageService service = new JsfStatisticsManageService();
    MsManageBD msBD = new MsManageBD();
    if ("manageLst".equals(action)) {
      manageLst(request);
      return actionMapping.findForward(action);
    } 
    if ("manageForAdd".equals(action)) {
      List ttableList = null;
      String statsTableId = request.getParameter("statsTableId");
      String sql = "";
      sql = "select po.fieldId,po.fieldName,po.fieldDesName from TFieldPO po where po.table.tableId =" + statsTableId;
      request.setAttribute("tFiledsList", msBD.getListByYourSQL(sql));
      form.setStatsStatus("1");
      form.setIsExtendUrl("0");
      form.setStatsTableId(Long.valueOf(statsTableId));
      return actionMapping.findForward(action);
    } 
    if ("manageAdd".equals(action)) {
      String statsBeginTimeTemp = request.getParameter("statsBeginTimeTemp");
      String statsEndTimeTemp = request.getParameter("statsEndTimeTemp");
      form.setStatsBeginTime(timeFormat(statsBeginTimeTemp));
      form.setStatsEndTime(timeFormat(statsEndTimeTemp));
      boolean b = service.manageAdd(form);
      if (b) {
        request.setAttribute("flag", "addsuccess");
      } else {
        request.setAttribute("flag", "adderror");
      } 
      request.setAttribute("statsTableId", form.getStatsTableId().toString());
      return actionMapping.findForward("close");
    } 
    if ("addAndContinue".equals(action)) {
      String statsBeginTimeTemp = request.getParameter("statsBeginTimeTemp");
      String statsEndTimeTemp = request.getParameter("statsEndTimeTemp");
      form.setStatsBeginTime(timeFormat(statsBeginTimeTemp));
      form.setStatsEndTime(timeFormat(statsEndTimeTemp));
      boolean b = service.manageAdd(form);
      if (b) {
        request.setAttribute("flag", "addAndContinueSuccess");
      } else {
        request.setAttribute("flag", "addAndContinueError");
      } 
      request.setAttribute("statsTableId", form.getStatsTableId().toString());
      return actionMapping.findForward("close");
    } 
    if ("manageForUpdate".equals(action)) {
      String id = request.getParameter("id");
      JsfStatisticsManage po = service.loadStatisticsManage(Long.valueOf(id));
      poToform(form, po);
      String sql = "";
      sql = "select po.fieldId,po.fieldName,po.fieldDesName from TFieldPO po where po.table.tableId =" + form.getStatsTableId();
      request.setAttribute("tFiledsList", msBD.getListByYourSQL(sql));
      request.setAttribute("start_date", form.getStatsBeginTime());
      request.setAttribute("end_date", form.getStatsEndTime());
      request.setAttribute("isExtendUrl", form.getIsExtendUrl());
      return actionMapping.findForward(action);
    } 
    if ("manageUpdate".equals(action)) {
      String statsBeginTimeTemp = request.getParameter("statsBeginTimeTemp");
      String statsEndTimeTemp = request.getParameter("statsEndTimeTemp");
      form.setStatsBeginTime(timeFormat(statsBeginTimeTemp));
      form.setStatsEndTime(timeFormat(statsEndTimeTemp));
      boolean b = service.manageUpdate(form);
      if (b) {
        request.setAttribute("flag", "updatesuccess");
      } else {
        request.setAttribute("flag", "updateerror");
      } 
      request.setAttribute("statsTableId", form.getStatsTableId().toString());
      return actionMapping.findForward("close");
    } 
    if ("manageDel".equals(action)) {
      String ids = request.getParameter("ids");
      boolean b = false;
      if (ids != null && !"".equals(ids))
        try {
          b = service.deleteManage(ids);
        } catch (Exception e) {
          e.printStackTrace();
        }  
      String flag = null;
      if (b) {
        flag = "deletesuccess";
      } else {
        flag = "deleteerror";
      } 
      request.setAttribute("flag", flag);
      manageLst(request);
      return actionMapping.findForward("manageLst");
    } 
    return actionMapping.findForward("manageLst");
  }
  
  public void manageLst(HttpServletRequest request) {
    String wherePara = "";
    String statsTitle = request.getParameter("statsTitle");
    String statsTableId = request.getParameter("statsTableId");
    if (statsTitle != null && !"".equals(statsTitle))
      wherePara = String.valueOf(wherePara) + " and po.statsTitle like '%" + statsTitle + "%' "; 
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " where po.statsTableId=" + statsTableId;
    where = String.valueOf(where) + wherePara;
    Page page = new Page(" po.statsId,po.statsTitle,po.statsIndex,po.statsIndexName,po.statsStatus,po.statsChart,po.extendUrl", 
        " com.js.oa.userdb.statistics.po.JsfStatisticsManage po", 
        String.valueOf(where) + " order by po.statsId desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("myList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("statsTableId", statsTableId);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,statsTableId,statsTitle");
  }
  
  public Date timeFormat(String timeStr) throws ParseException {
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
    Date datef = bartDateFormat.parse(timeStr);
    timeStr = format.format(datef);
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format2.parse(timeStr);
    return date;
  }
  
  public String dateFormart(String date) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
  
  public void poToform(JsfStatisticsManageForm form, JsfStatisticsManage po) {
    form.setStatsId(po.getStatsId());
    form.setStatsTableId(po.getStatsTableId());
    form.setStatsTitle(po.getStatsTitle());
    form.setStatsIndex(po.getStatsIndex());
    form.setStatsIndexName(po.getStatsIndexName());
    form.setStatsType(po.getStatsType());
    form.setStatsGroupIndex(po.getStatsGroupIndex());
    form.setStatsOrder(po.getStatsOrder());
    form.setStatsOrderNum(po.getStatsOrderNum());
    form.setStatsBeginTime(po.getStatsBeginTime());
    form.setStatsEndTime(po.getStatsEndTime());
    form.setStatsChart(po.getStatsChart());
    form.setStatsStatus(po.getStatsStatus());
    form.setIsExtendUrl(po.getIsExtendUrl());
    form.setExtendUrl(po.getExtendUrl());
  }
}
