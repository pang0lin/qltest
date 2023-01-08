package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqHolidayPO;
import com.js.oa.hr.kq.service.KqHolidayBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqHolidayAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    Long domainId = (httpServletRequest.getSession().getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession().getAttribute("domainId").toString());
    KqHolidayBD kqHolidayBD = new KqHolidayBD();
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      httpServletRequest.setAttribute("paibanList", kqHolidayBD.getPaibanList());
      tag = "add";
    } else if (action.equals("save")) {
      KqHolidayPO kqHolidayPO = new KqHolidayPO();
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = String.valueOf(httpServletRequest.getParameter("beginTime")) + ":00";
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = String.valueOf(httpServletRequest.getParameter("endTime")) + ":59";
      String saveType = httpServletRequest.getParameter("saveType");
      int type = Integer.valueOf(httpServletRequest.getParameter("type")).intValue();
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqHolidayPO.setBeginDate(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqHolidayPO.setEndDate(calendar.getTime());
      String corpId = httpServletRequest.getSession().getAttribute("corpId").toString();
      String reduceAnnual = (httpServletRequest.getParameter("reduceAnnual") == null) ? "0" : httpServletRequest.getParameter("reduceAnnual");
      String reduceTrial = (httpServletRequest.getParameter("reduceTrial") == null) ? "0" : httpServletRequest.getParameter("reduceTrial");
      kqHolidayPO.setDomainId(domainId.longValue());
      kqHolidayPO.setHolidayName(httpServletRequest.getParameter("holidayName"));
      kqHolidayPO.setMemo(httpServletRequest.getParameter("memo"));
      kqHolidayPO.setType(type);
      kqHolidayPO.setPaibanSet(httpServletRequest.getParameter("paibanSet"));
      kqHolidayPO.setCorpId(corpId);
      kqHolidayPO.setReduceAnnual(reduceAnnual);
      kqHolidayPO.setReduceTrial(reduceTrial);
      kqHolidayBD.add(kqHolidayPO);
      httpServletRequest.setAttribute("saveType", saveType);
      tag = "add";
    } else if (action.equals("del")) {
      String holidayid = httpServletRequest.getParameter("holidayid");
      kqHolidayBD.del((new Long(holidayid)).longValue());
      list(httpServletRequest);
      httpServletRequest.setAttribute("reload", "yes");
      tag = "list";
    } else if (action.equals("update")) {
      String holidayid = httpServletRequest.getParameter("holidayid");
      KqHolidayPO kqHolidayPO = new KqHolidayPO();
      kqHolidayPO = kqHolidayBD.searchById((new Long(holidayid)).longValue());
      httpServletRequest.setAttribute("kqHolidayPO", kqHolidayPO);
      httpServletRequest.setAttribute("paibanList", kqHolidayBD.getPaibanList());
      tag = "update";
    } else if (action.equals("updatesave")) {
      String holidayid = httpServletRequest.getParameter("holidayid");
      KqHolidayPO kqHolidayPO = new KqHolidayPO();
      kqHolidayPO = kqHolidayBD.searchById((new Long(holidayid)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = String.valueOf(httpServletRequest.getParameter("beginTime")) + ":00";
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = String.valueOf(httpServletRequest.getParameter("endTime")) + ":59";
      int type = Integer.valueOf(httpServletRequest.getParameter("type")).intValue();
      String reduceAnnual = (httpServletRequest.getParameter("reduceAnnual") == null) ? "0" : httpServletRequest.getParameter("reduceAnnual");
      String reduceTrial = (httpServletRequest.getParameter("reduceTrial") == null) ? "0" : httpServletRequest.getParameter("reduceTrial");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqHolidayPO.setBeginDate(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqHolidayPO.setEndDate(calendar.getTime());
      kqHolidayPO.setDomainId(domainId.longValue());
      kqHolidayPO.setHolidayName(httpServletRequest.getParameter("holidayName"));
      kqHolidayPO.setMemo(httpServletRequest.getParameter("memo"));
      kqHolidayPO.setType(type);
      kqHolidayPO.setPaibanSet(httpServletRequest.getParameter("paibanSet"));
      kqHolidayPO.setReduceAnnual(reduceAnnual);
      kqHolidayPO.setReduceTrial(reduceTrial);
      kqHolidayBD.update(kqHolidayPO);
      httpServletRequest.setAttribute("saveType", "saveandexit");
      tag = "add";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String whereSql = " where po.domainId =" + domainId;
    if (SystemCommon.getMultiDepart() == 1)
      whereSql = String.valueOf(whereSql) + " and po.corpId=" + httpServletRequest.getSession(true).getAttribute("corpId").toString(); 
    Page page = new Page(
        " po.id,po.holidayName,po.beginDate,po.endDate,po.memo", 
        "com.js.oa.hr.kq.po.KqHolidayPO po ", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,status,userId,orgId,userName,orgName,searchDate,start_date,end_date");
  }
}
