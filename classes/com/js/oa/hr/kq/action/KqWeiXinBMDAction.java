package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqWeiXinBMDPO;
import com.js.oa.hr.kq.service.KqWeiXinBMDBD;
import com.js.system.service.usermanager.UserBD;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqWeiXinBMDAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? "list" : 
      httpServletRequest.getParameter("action");
    KqWeiXinBMDBD kqWeiXinBMDBD = new KqWeiXinBMDBD();
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("save")) {
      KqWeiXinBMDPO kqWeiXinBMDPO = new KqWeiXinBMDPO();
      String saveType = httpServletRequest.getParameter("saveType");
      String emp_id = httpServletRequest.getParameter("userId");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String endTime = httpServletRequest.getParameter("endTime");
      kqWeiXinBMDPO.setEmp_id(Long.valueOf(emp_id).longValue());
      kqWeiXinBMDPO.setBeginTime(new Date(beginTime));
      kqWeiXinBMDPO.setEndTime(new Date(String.valueOf(endTime) + " 23:59:59"));
      kqWeiXinBMDBD.add(kqWeiXinBMDPO);
      httpServletRequest.setAttribute("saveType", saveType);
      tag = "add";
    } else if (action.equals("del")) {
      String bmdid = httpServletRequest.getParameter("bmdid");
      kqWeiXinBMDBD.del((new Long(bmdid)).longValue());
      list(httpServletRequest);
      tag = "list";
    } else if (action.equals("update")) {
      String bmdid = httpServletRequest.getParameter("bmdid");
      KqWeiXinBMDPO kqWeiXinBMDPO = new KqWeiXinBMDPO();
      kqWeiXinBMDPO = kqWeiXinBMDBD.searchById((new Long(bmdid)).longValue());
      httpServletRequest.setAttribute("kqWeiXinBMDPO", kqWeiXinBMDPO);
      httpServletRequest.setAttribute("empName", (new UserBD()).getUserNameById((new StringBuilder(String.valueOf(kqWeiXinBMDPO.getEmp_id()))).toString()).replaceAll(",", ""));
      tag = "update";
    } else if (action.equals("updatesave")) {
      String bmdid = httpServletRequest.getParameter("bmdid");
      KqWeiXinBMDPO kqWeiXinBMDPO = new KqWeiXinBMDPO();
      kqWeiXinBMDPO = kqWeiXinBMDBD.searchById((new Long(bmdid)).longValue());
      String emp_id = httpServletRequest.getParameter("userId");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String endTime = httpServletRequest.getParameter("endTime");
      kqWeiXinBMDPO.setEmp_id(Long.valueOf(emp_id).longValue());
      kqWeiXinBMDPO.setBeginTime(new Date(beginTime));
      kqWeiXinBMDPO.setEndTime(new Date(String.valueOf(endTime) + " 23:59:59"));
      kqWeiXinBMDBD.update(kqWeiXinBMDPO);
      httpServletRequest.setAttribute("saveType", "saveandexit");
      tag = "add";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        " po.emp_id,po.beginTime,po.endTime,emp.empName,po.id ", 
        "com.js.oa.hr.kq.po.KqWeiXinBMDPO po,com.js.system.vo.usermanager.EmployeeVO emp ", 
        "where po.emp_id = emp.empId");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest
      .setAttribute("pageParameters", 
        "action,status,userId,orgId,userName,orgName,searchDate,start_date,end_date");
  }
}
