package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqDutySetPO;
import com.js.oa.hr.kq.service.KqDutySetBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.StringSplit;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqDutySetAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    Long domainId = (httpServletRequest.getSession().getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession().getAttribute("domainId").toString());
    KqDutySetBD kqDutySetBD = new KqDutySetBD();
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("save")) {
      String corpId = httpServletRequest.getSession().getAttribute("corpId").toString();
      String[] ifPunchs = httpServletRequest.getParameterValues("ifPunch");
      String[] minNums = httpServletRequest.getParameterValues("minNum");
      String[] minNumDowns = httpServletRequest.getParameterValues("minNumDown");
      String ifPunch = "";
      String minNum = "";
      String minNumDown = "";
      for (int i = 0; i < minNums.length; i++) {
        ifPunch = String.valueOf(ifPunch) + ifPunchs[i] + ",";
        minNum = String.valueOf(minNum) + minNums[i] + ",";
        minNumDown = String.valueOf(minNumDown) + minNumDowns[i] + ",";
      } 
      KqDutySetPO kqDutySetPO = new KqDutySetPO();
      kqDutySetPO.setDomainId(domainId.longValue());
      kqDutySetPO.setCorpId(corpId);
      kqDutySetPO.setUserNames(httpServletRequest.getParameter("userName"));
      kqDutySetPO.setDutyName(httpServletRequest.getParameter("dutyName"));
      String userOrgGroup = httpServletRequest.getParameter("userId");
      kqDutySetPO.setUserIds(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      kqDutySetPO.setOrgIds(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      kqDutySetPO.setGroupIds(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      String workday = "";
      String workday1 = (httpServletRequest.getParameter("workday1") == null) ? "0" : httpServletRequest.getParameter("workday1").toString();
      workday = String.valueOf(workday) + workday1;
      String workday2 = (httpServletRequest.getParameter("workday2") == null) ? "0" : httpServletRequest.getParameter("workday2").toString();
      workday = String.valueOf(workday) + workday2;
      String workday3 = (httpServletRequest.getParameter("workday3") == null) ? "0" : httpServletRequest.getParameter("workday3").toString();
      workday = String.valueOf(workday) + workday3;
      String workday4 = (httpServletRequest.getParameter("workday4") == null) ? "0" : httpServletRequest.getParameter("workday4").toString();
      workday = String.valueOf(workday) + workday4;
      String workday5 = (httpServletRequest.getParameter("workday5") == null) ? "0" : httpServletRequest.getParameter("workday5").toString();
      workday = String.valueOf(workday) + workday5;
      String workday6 = (httpServletRequest.getParameter("workday6") == null) ? "0" : httpServletRequest.getParameter("workday6").toString();
      workday = String.valueOf(workday) + workday6;
      String workday7 = (httpServletRequest.getParameter("workday7") == null) ? "0" : httpServletRequest.getParameter("workday7").toString();
      workday = String.valueOf(workday) + workday7;
      kqDutySetPO.setWorkday(workday);
      kqDutySetPO.setIfPunch(ifPunch);
      kqDutySetPO.setMinNum(minNum);
      kqDutySetPO.setMinNumDown(minNumDown);
      kqDutySetPO.setDutyType1(1);
      kqDutySetPO.setDutyTime1(httpServletRequest.getParameter("dutyTime1"));
      kqDutySetPO.setDutyType2(2);
      kqDutySetPO.setDutyTime2(httpServletRequest.getParameter("dutyTime2"));
      kqDutySetPO.setDutyType3(1);
      kqDutySetPO.setDutyTime3(httpServletRequest.getParameter("dutyTime3"));
      kqDutySetPO.setDutyType4(2);
      kqDutySetPO.setDutyTime4(httpServletRequest.getParameter("dutyTime4"));
      kqDutySetPO.setDutyType5(1);
      kqDutySetPO.setDutyTime5(httpServletRequest.getParameter("dutyTime5"));
      kqDutySetPO.setDutyType6(2);
      kqDutySetPO.setDutyTime6(httpServletRequest.getParameter("dutyTime6"));
      kqDutySetPO.setDutyHour(Float.valueOf(httpServletRequest.getParameter("dutyHour")).floatValue());
      kqDutySetBD.add(kqDutySetPO);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
      tag = "add";
    } else if (action.equals("del")) {
      String dutysetid = httpServletRequest.getParameter("dutysetid");
      kqDutySetBD.del((new Long(dutysetid)).longValue());
      list(httpServletRequest);
      httpServletRequest.setAttribute("reload", "yes");
      tag = "list";
    } else if (action.equals("update")) {
      String dutysetid = httpServletRequest.getParameter("dutysetid");
      KqDutySetPO kqDutySetPO = new KqDutySetPO();
      kqDutySetPO = kqDutySetBD.searchById((new Long(dutysetid)).longValue());
      httpServletRequest.setAttribute("kqDutySetPO", kqDutySetPO);
      tag = "update";
    } else if (action.equals("updatesave")) {
      String[] ifPunchs = httpServletRequest.getParameterValues("ifPunch");
      String[] minNums = httpServletRequest.getParameterValues("minNum");
      String[] minNumDowns = httpServletRequest.getParameterValues("minNumDown");
      String ifPunch = "";
      String minNum = "";
      String minNumDown = "";
      for (int i = 0; i < minNums.length; i++) {
        ifPunch = String.valueOf(ifPunch) + ifPunchs[i] + ",";
        minNum = String.valueOf(minNum) + minNums[i] + ",";
        minNumDown = String.valueOf(minNumDown) + minNumDowns[i] + ",";
      } 
      String dutysetid = httpServletRequest.getParameter("dutysetid");
      KqDutySetPO kqDutySetPO = new KqDutySetPO();
      kqDutySetPO = kqDutySetBD.searchById((new Long(dutysetid)).longValue());
      kqDutySetPO.setUserNames(httpServletRequest.getParameter("userName"));
      kqDutySetPO.setDutyName(httpServletRequest.getParameter("dutyName"));
      String userOrgGroup = httpServletRequest.getParameter("userId");
      kqDutySetPO.setUserIds(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      kqDutySetPO.setOrgIds(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      kqDutySetPO.setGroupIds(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      String workday = "";
      String workday1 = (httpServletRequest.getParameter("workday1") == null) ? "0" : httpServletRequest.getParameter("workday1").toString();
      workday = String.valueOf(workday) + workday1;
      String workday2 = (httpServletRequest.getParameter("workday2") == null) ? "0" : httpServletRequest.getParameter("workday2").toString();
      workday = String.valueOf(workday) + workday2;
      String workday3 = (httpServletRequest.getParameter("workday3") == null) ? "0" : httpServletRequest.getParameter("workday3").toString();
      workday = String.valueOf(workday) + workday3;
      String workday4 = (httpServletRequest.getParameter("workday4") == null) ? "0" : httpServletRequest.getParameter("workday4").toString();
      workday = String.valueOf(workday) + workday4;
      String workday5 = (httpServletRequest.getParameter("workday5") == null) ? "0" : httpServletRequest.getParameter("workday5").toString();
      workday = String.valueOf(workday) + workday5;
      String workday6 = (httpServletRequest.getParameter("workday6") == null) ? "0" : httpServletRequest.getParameter("workday6").toString();
      workday = String.valueOf(workday) + workday6;
      String workday7 = (httpServletRequest.getParameter("workday7") == null) ? "0" : httpServletRequest.getParameter("workday7").toString();
      workday = String.valueOf(workday) + workday7;
      kqDutySetPO.setWorkday(workday);
      kqDutySetPO.setIfPunch(ifPunch);
      kqDutySetPO.setMinNum(minNum);
      kqDutySetPO.setMinNumDown(minNumDown);
      kqDutySetPO.setDutyTime1(httpServletRequest.getParameter("dutyTime1"));
      kqDutySetPO.setDutyTime2(httpServletRequest.getParameter("dutyTime2"));
      kqDutySetPO.setDutyTime3(httpServletRequest.getParameter("dutyTime3"));
      kqDutySetPO.setDutyTime4(httpServletRequest.getParameter("dutyTime4"));
      kqDutySetPO.setDutyTime5(httpServletRequest.getParameter("dutyTime5"));
      kqDutySetPO.setDutyTime6(httpServletRequest.getParameter("dutyTime6"));
      kqDutySetPO.setDutyHour(Float.valueOf(httpServletRequest.getParameter("dutyHour")).floatValue());
      kqDutySetBD.update(kqDutySetPO);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
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
        " po.id,po.dutyName,po.userNames,po.dutyTime1,po.dutyTime2,po.dutyTime3,po.dutyTime4,po.dutyTime5,po.dutyTime6", 
        "com.js.oa.hr.kq.po.KqDutySetPO po ", 
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
        "action");
  }
}
