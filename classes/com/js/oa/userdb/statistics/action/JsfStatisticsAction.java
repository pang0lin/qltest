package com.js.oa.userdb.statistics.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.userdb.statistics.po.JsfStatisticsManage;
import com.js.oa.userdb.statistics.service.JsfStatisticsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class JsfStatisticsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    JsfStatisticsManageForm form = (JsfStatisticsManageForm)actionForm;
    JsfStatisticsService service = new JsfStatisticsService();
    MsManageBD msBD = new MsManageBD();
    if ("statisticsData".equals(action)) {
      int pageSize = 15;
      Map reMap = service.queryStatisticsData(request, pageSize);
      List dataList = (List)reMap.get("reList");
      int recordCount = ((Integer)reMap.get("recordCount")).intValue();
      request.setAttribute("menuId", request.getParameter("menuId"));
      request.setAttribute("dataList", dataList);
      request.setAttribute("recordCount", String.valueOf(recordCount));
      return actionMapping.findForward(action);
    } 
    if ("statisticsOnlyData".equals(action)) {
      int pageSize = 15;
      Map reMap = service.queryStatisticsData(request, pageSize);
      request.setAttribute("groupIndexName", reMap.get("fieldName"));
      request.setAttribute("t_tableName", reMap.get("tableName"));
      List dataList = (List)reMap.get("reList");
      int recordCount = ((Integer)reMap.get("recordCount")).intValue();
      request.setAttribute("menuId", request.getParameter("menuId"));
      request.setAttribute("dataList", dataList);
      request.setAttribute("recordCount", String.valueOf(recordCount));
      return actionMapping.findForward(action);
    } 
    if ("statisticsDataExport".equals(action)) {
      int pageSize = 9999999;
      Map reMap = service.queryStatisticsData(request, pageSize);
      List dataList = (List)reMap.get("reList");
      int recordCount = ((Integer)reMap.get("recordCount")).intValue();
      request.setAttribute("dataList", dataList);
      request.setAttribute("recordCount", String.valueOf(recordCount));
      return actionMapping.findForward(action);
    } 
    if ("statisticsDataChart".equals(action)) {
      String statsId = request.getParameter("statsId");
      String rightType = request.getParameter("rightType");
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter(
              "pager.offset")); 
      request.setAttribute("offset", Integer.valueOf(offset));
      request.setAttribute("statsId", statsId);
      request.setAttribute("rightType", rightType);
      request.setAttribute("menuId", request.getParameter("menuId"));
      request.setAttribute("fileName", service.queryStatisticsDataChart(request, 9999999, 60, 45));
      return actionMapping.findForward(action);
    } 
    return actionMapping.findForward("manageLst");
  }
  
  public Date timeFormat(String timeStr, String flag) throws ParseException {
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date datef = bartDateFormat.parse(timeStr);
    timeStr = format.format(datef);
    if ("0".equals(flag)) {
      timeStr = String.valueOf(timeStr) + " 00:00:00";
    } else {
      timeStr = String.valueOf(timeStr) + " 23:59:59";
    } 
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
