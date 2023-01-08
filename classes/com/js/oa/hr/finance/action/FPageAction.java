package com.js.oa.hr.finance.action;

import com.js.oa.hr.finance.bean.FTableEJBBean;
import com.js.oa.hr.finance.po.FPage;
import com.js.oa.hr.finance.service.FPageService;
import com.js.oa.hr.finance.service.FTableService;
import com.js.oa.message.service.MsManageBD;
import com.js.util.page.Page;
import com.js.util.util.ParameterFilter;
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

public class FPageAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    FPageActionForm form = (FPageActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    FTableEJBBean bean = new FTableEJBBean();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    FTableService ftableService = new FTableService();
    FPageService fPageService = new FPageService();
    if ("pageList".equals(action)) {
      try {
        pageList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("pageList");
    } 
    if ("forAdd".equals(action)) {
      try {
        String sql = "select po.tableId,po.tableDesname from com.js.oa.hr.finance.po.FTable po";
        List ftableList = bean.getListByYourSQL(sql);
        request.setAttribute("ftableList", ftableList);
        form.setPageIsUse(Long.valueOf(1L));
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("add".equals(action))
      try {
        String flag = null;
        boolean b = fPageService.addPage(form, request);
        if (b) {
          flag = "addsuccess";
        } else {
          flag = "adderror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("addAndContinue".equals(action))
      try {
        String flag = null;
        boolean b = fPageService.addPage(form, request);
        if (b) {
          flag = "addAndContinueSuccess";
        } else {
          flag = "addAndContinueError";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("forModi".equals(action)) {
      try {
        String sql = "select po.tableId,po.tableDesname from com.js.oa.hr.finance.po.FTable po";
        List ftableList = bean.getListByYourSQL(sql);
        request.setAttribute("ftableList", ftableList);
        String pageId = request.getParameter("pageId");
        FPage fpage = fPageService.loadFPage(Long.valueOf(pageId));
        request.setAttribute("pageContent", fpage.getPageContent());
        poToForm(form, fpage);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("modi".equals(action))
      try {
        String flag = null;
        boolean b = fPageService.modiPage(form, request);
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
    if ("checkPageName".equals(action)) {
      String reStr = "0";
      PrintWriter out = null;
      try {
        response.setContentType("text/xml;charset=GBK");
        out = response.getWriter();
        String pageName = request.getParameter("pageName");
        if (!ParameterFilter.checkParameter(pageName)) {
          reStr = "1";
        } else {
          String sqlAjax = "select po.pageId,po.pageName from com.js.oa.hr.finance.po.FPage po where po.pageName=" + pageName;
          if (request.getParameter("pageId") != null)
            sqlAjax = String.valueOf(sqlAjax) + " and po.pageId!=" + Long.parseLong(request.getParameter("pageId")); 
          MsManageBD msBD = new MsManageBD();
          List reList = msBD.getListByYourSQL(sqlAjax);
          if (reList != null)
            reStr = String.valueOf(reList.size()); 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reStr);
      out.close();
      return null;
    } 
    return actionMapping.findForward("taskList");
  }
  
  public void pageList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.pageId,po.pageName,po.pageIsUse,po.tableId,po.tableDesname,po.createDate ";
    String table = " com.js.oa.hr.finance.po.FPage po ";
    String where = " where  1=1 ";
    String orderBy = " order by po.pageName desc ";
    String pageName = request.getParameter("pageName");
    if (pageName != null && !"".equals(pageName))
      where = String.valueOf(where) + " and po.pageName like '%" + pageName + "%'"; 
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
    request.setAttribute("pageParameters", "action,pageName");
    request.setAttribute("myList", myList);
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
  
  public void poToForm(FPageActionForm form, FPage fpage) {
    form.setPageId(fpage.getPageId());
    form.setPageName(fpage.getPageName());
    form.setPageContent(fpage.getPageContent());
    form.setPageIsUse(fpage.getPageIsUse());
    form.setPageFile(fpage.getPageFile());
    form.setTableId(fpage.getTableId());
    form.setTableDesname(fpage.getTableDesname());
    form.setCreateDate(fpage.getCreateDate());
    form.setCreatedEmp(fpage.getCreatedEmp());
    form.setCreatedOrg(fpage.getCreatedOrg());
  }
}
