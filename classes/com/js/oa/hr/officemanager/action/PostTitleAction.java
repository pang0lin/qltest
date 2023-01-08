package com.js.oa.hr.officemanager.action;

import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.oa.hr.officemanager.service.PostTitleBD;
import com.js.oa.hr.personnelmanager.service.NewDutyBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PostTitleAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    PostTitleActionForm postTitleActionForm = 
      (PostTitleActionForm)actionForm;
    PostTitleBD postTitleBD = new PostTitleBD();
    String action = httpServletRequest.getParameter("action");
    String tag = "";
    if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("view")) {
      tag = "view";
      list(actionMapping, actionForm, httpServletRequest, 
          httpServletResponse);
    } else if (action.equals("saveclose") || action.equals("savecontinue")) {
      String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
      PostTitlePO postTitlePO = new PostTitlePO();
      String postTitle = postTitleActionForm.getPostTitle();
      String postTitleSeries = postTitleActionForm.getPostTitleSeries();
      postTitlePO.setPostTitle(postTitle);
      postTitlePO.setPostTitleSeries(postTitleSeries);
      postTitlePO.setDomainId(domainId);
      int result = postTitleBD.add(postTitlePO);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", String.valueOf(result));
      } else {
        postTitleActionForm.setPostTitle("");
        postTitleActionForm.setPostTitleSeries("");
      } 
      if (action.equals("saveclose")) {
        tag = "saveclose";
        httpServletRequest.setAttribute("shouldClose", "1");
      } else if (action.equals("savecontinue")) {
        tag = "savecontinue";
      } 
    } else if (action.equals("del")) {
      String[] ids = { "" };
      if (httpServletRequest.getParameterValues("batchDel") != null) {
        ids = httpServletRequest.getParameterValues("batchDel");
      } else {
        ids[0] = httpServletRequest.getParameter("id");
      } 
      postTitleBD.del(ids);
      tag = "view";
      list(actionMapping, actionForm, httpServletRequest, 
          httpServletResponse);
    } else if (action.equals("modify")) {
      tag = "add";
      PostTitlePO postTitlePO = (new NewDutyBD()).getSinglePost(httpServletRequest.getParameter("postTitleId"));
      postTitleActionForm.setPostTitle(postTitlePO.getPostTitle());
      postTitleActionForm.setPostTitleSeries(postTitlePO.getPostTitleSeries());
    } else if (action.equals("update")) {
      tag = "add";
      PostTitlePO postTitlePO = new PostTitlePO();
      String postTitle = postTitleActionForm.getPostTitle();
      String postTitleSeries = postTitleActionForm.getPostTitleSeries();
      postTitlePO.setPostTitle(postTitle);
      postTitlePO.setPostTitleSeries(postTitleSeries);
      postTitlePO.setId(Long.parseLong(httpServletRequest.getParameter("postTitleId")));
      int result = (new NewDutyBD()).updatePost(postTitlePO);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", String.valueOf(result));
      } else {
        postTitleActionForm.setPostTitle("");
        postTitleActionForm.setPostTitleSeries("");
      } 
      httpServletRequest.setAttribute("shouldClose", "1");
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.id,po.postTitle,po.postTitleSeries", 
        "com.js.oa.hr.officemanager.po.PostTitlePO po", 
        " where po.domainId=" + domainId + " order by po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("postTitleList", list);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
