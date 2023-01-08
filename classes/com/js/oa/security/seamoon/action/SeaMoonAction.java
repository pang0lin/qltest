package com.js.oa.security.seamoon.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.security.seamoon.po.SecSeaMoon;
import com.js.oa.security.seamoon.service.SeaMoonService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SeaMoonAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    SeaMoonActionForm form = (SeaMoonActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    SeaMoonService seaMoonService = new SeaMoonService();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    if ("seaMoonList".equals(action)) {
      try {
        seaMoonList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("forSeaMoonAdd".equals(action)) {
      try {
        form.setSecStatus("1");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("seaMoonAdd");
    } 
    if ("SeaMoonAdd".equals(action))
      try {
        String id = null;
        SecSeaMoon po = (SecSeaMoon)FillBean.transformOTO(form, SecSeaMoon.class);
        id = seaMoonService.addSeaMoon(po);
        String flag = null;
        if (id != null) {
          flag = "addsuccess";
        } else {
          flag = "adderror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("SeaMoonAddAndContinue".equals(action))
      try {
        String id = null;
        SecSeaMoon po = (SecSeaMoon)FillBean.transformOTO(form, SecSeaMoon.class);
        id = seaMoonService.addSeaMoon(po);
        String flag = null;
        if (id != null) {
          flag = "addAndContinueSuccess";
        } else {
          flag = "addAndContinueError";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("forSeaMoonModi".equals(action))
      try {
        String sn = request.getParameter("id");
        if (sn != null && !"".equals(sn)) {
          SecSeaMoon po = seaMoonService.loadSeaMoon(sn);
          request.setAttribute("sn", po.getSn());
          request.setAttribute("userId", po.getUserId());
          poToForm(form, po);
          return actionMapping.findForward("seaMoonModi");
        } 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("seaMoonModi".equals(action))
      try {
        SecSeaMoon po = (SecSeaMoon)FillBean.transformOTO(form, SecSeaMoon.class);
        String oldSn = request.getParameter("oldSn");
        boolean b = false;
        if (oldSn != null && oldSn.equals(po.getSn())) {
          b = seaMoonService.updateSeaMoon(po);
        } else {
          b = seaMoonService.deleteSeaMoon(oldSn);
          String sn = seaMoonService.addSeaMoon(po);
          if (sn == null)
            b = false; 
        } 
        String flag = null;
        if (b) {
          flag = "updatesuccess";
        } else {
          flag = "updateerror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("forSeaMoonTest".equals(action))
      return actionMapping.findForward(action); 
    if ("forSeaMoonSyn".equals(action))
      return actionMapping.findForward(action); 
    if ("seaMoonDelete".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        boolean b = false;
        if (ids != null && !"".equals(ids))
          try {
            b = seaMoonService.deleteSeaMoon(ids);
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
        seaMoonList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("seaMoonList");
    } 
    if ("updateSeaMoonEnable".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String inuse = request.getParameter("inuse");
        boolean b = false;
        if (ids != null)
          "".equals(ids); 
        seaMoonList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("taskList");
    } 
    if ("checkUser".equals(action) || "checkSN".equals(action)) {
      String reStr = "0";
      PrintWriter out = null;
      try {
        response.setContentType("text/xml;charset=GBK");
        out = response.getWriter();
        String sqlAjax = "";
        if ("checkUser".equals(action)) {
          long curUserId = Long.parseLong(request.getParameter("id"));
          sqlAjax = "select po.sn,po.userId from com.js.oa.security.seamoon.po.SecSeaMoon po where po.userId=" + curUserId;
        } 
        MsManageBD msBD = new MsManageBD();
        List reList = msBD.getListByYourSQL(sqlAjax);
        if (reList != null)
          reStr = String.valueOf(reList.size()); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reStr);
      out.close();
      return null;
    } 
    return actionMapping.findForward("taskList");
  }
  
  public void poToForm(SeaMoonActionForm form, SecSeaMoon po) {
    form.setSn(po.getSn());
    form.setSnKey(po.getSnKey());
    form.setUserId(po.getUserId());
    form.setUserName(po.getUserName());
    form.setSecStatus(po.getSecStatus());
    form.setBeginTime(po.getBeginTime());
    form.setEndTime(po.getEndTime());
  }
  
  public void seaMoonList(HttpServletRequest request) throws Exception {
    int offset_page = 0;
    String test = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.sn,po.snKey,po.userId,po.userName,po.secStatus,po.beginTime,po.endTime";
    String table = " com.js.oa.security.seamoon.po.SecSeaMoon po ";
    String where = " where 1=1 ";
    String userName = request.getParameter("userName");
    if (userName != null && !"".equals(userName))
      where = String.valueOf(where) + " and po.userName like '%" + userName + "%' "; 
    String sn = request.getParameter("sn");
    if (sn != null && !"".equals(sn))
      where = String.valueOf(where) + " and po.sn like '%" + sn + "%' "; 
    String orderBy = " order by po.sn desc,po.userName";
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List myList = page_ol.getResultList();
    int recordCount = page_ol.getRecordCount();
    if (offset_page >= recordCount) {
      offset_page = (recordCount - pageSize) / pageSize;
      currentPage = offset_page + 1;
      offset_page *= pageSize;
      page_ol.setcurrentPage(currentPage);
      myList = page_ol.getResultList();
      recordCount = page_ol.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset_page));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", "action,userName");
    request.setAttribute("myList", myList);
  }
  
  public static String replaceAll(String str, String befor, String after) {
    StringBuffer buffer = new StringBuffer();
    int i = 0, preIndex = 0, lastIndex = 0;
    int strLen = str.length();
    int litLen = befor.length();
    if (str.endsWith(befor))
      str = String.valueOf(str.substring(0, strLen - litLen)) + after; 
    if (str.startsWith(befor))
      str = String.valueOf(after) + str.substring(litLen); 
    preIndex = str.indexOf(befor);
    String tmp = "";
    while (preIndex >= 0) {
      if (preIndex == 0) {
        if (str.length() > 1) {
          str = str.substring(befor.length(), str.length());
          preIndex = str.indexOf(befor);
        } else {
          preIndex = -1;
        } 
        buffer.append(after);
      } else {
        if (i == 0) {
          buffer.append(str.substring(0, preIndex));
        } else {
          buffer.append(after).append(str.substring(0, preIndex));
        } 
        str = str.substring(preIndex + befor.length());
        preIndex = str.indexOf(befor);
      } 
      i++;
    } 
    if (!"".equals(str)) {
      if (i > 0)
        buffer.append(after); 
      buffer.append(str);
    } 
    return buffer.toString();
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
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
