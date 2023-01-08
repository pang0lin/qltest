package com.js.oa.info.infomanager.action;

import com.js.util.page.Page;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoCountFileAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    List list = null;
    HttpSession session = httpServletRequest.getSession(true);
    String tag = "";
    String action = "";
    action = httpServletRequest.getParameter("action");
    if (action.equals("first")) {
      tag = "success";
      List searchList = new ArrayList();
      httpServletRequest.setAttribute("searchList", searchList);
    } 
    String searchDate = httpServletRequest.getParameter("searchDate");
    String searchBeginDate = httpServletRequest.getParameter("searchBeginDate");
    httpServletRequest.setAttribute("searchBeginDate", searchBeginDate);
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    httpServletRequest.setAttribute("searchEndDate", searchEndDate);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    if ("search".equals(action)) {
      String informationReader = httpServletRequest.getParameter("informationReader");
      String informationReaderOrg = httpServletRequest.getParameter("informationReaderOrg");
      String informationReaderGroup = httpServletRequest.getParameter("informationReaderGroup");
      String viewSQL = "a.empName,b.orgSerial,a.empId,b.orgId,b.orgIdString";
      String fromSQL = "com.js.system.vo.usermanager.EmployeeVO a join a.organizations b";
      String whereSQL = "";
      if (!informationReader.equals("")) {
        whereSQL = String.valueOf(whereSQL) + "where (1=2";
        String[] tmp = informationReader.split("\\$");
        whereSQL = String.valueOf(whereSQL) + " or a.empId=" + tmp[1];
        for (int i = 3; i < tmp.length; i += 2)
          whereSQL = String.valueOf(whereSQL) + " or a.empId=" + tmp[i]; 
      } 
      if (!informationReaderOrg.equals(""))
        if (!informationReader.equals("")) {
          String[] tmps = informationReaderOrg.split("\\*");
          whereSQL = String.valueOf(whereSQL) + " or b.orgIdString like '%$" + tmps[1] + "$%'";
          for (int i = 3; i < tmps.length; i += 2)
            whereSQL = String.valueOf(whereSQL) + " or b.orgIdString like '%$" + tmps[i] + "$%'"; 
        } else {
          whereSQL = String.valueOf(whereSQL) + "where (1=2";
          String[] tmps = informationReaderOrg.split("\\*");
          whereSQL = String.valueOf(whereSQL) + " or b.orgIdString like '%$" + tmps[1] + "$%'";
          for (int i = 3; i < tmps.length; i += 2)
            whereSQL = String.valueOf(whereSQL) + " or b.orgIdString like '%$" + tmps[i] + "$%'"; 
        }  
      if (!informationReader.equals("") || !informationReaderOrg.equals(""))
        whereSQL = String.valueOf(whereSQL) + ")"; 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSQL, fromSQL, whereSQL);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      tag = "searchsuccess";
      httpServletRequest.setAttribute("searchList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,informationReader,informationReaderOrg,searchDate,searchBeginDate,searchEndDate,searchtype,year,month");
    } 
    return actionMapping.findForward(tag);
  }
}
