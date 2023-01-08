package com.js.oa.info.channelmanager.action;

import com.js.oa.info.channelmanager.po.UserChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.security.log.service.LogBD;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UserChannelAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    UserChannelActionForm userChannelActionForm = (UserChannelActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String action = httpServletRequest.getParameter("action");
    if (action == null)
      action = "view"; 
    String tag = "view";
    if (action.equals("view")) {
      view(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("close") || action.equals("continue")) {
      tag = action;
      ChannelBD channelBD = new ChannelBD();
      UserChannelPO userChannelPO = (UserChannelPO)FillBean.transformOneToOne(actionForm, UserChannelPO.class);
      userChannelPO.setDomainId(Long.valueOf(domainId));
      channelBD.addUserChannel(userChannelPO);
      LogBD logBD = new LogBD();
      Date startDate = new Date();
      String moduleCode = "system_userchannel";
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "1", userChannelPO.getUserChannelName(), httpServletRequest.getRemoteAddr(), session.getAttribute("domainId").toString());
      userChannelActionForm.setUserChannelName("");
      userChannelActionForm.setUserChannelOrder("");
    } else if (action.equals("delete")) {
      String userChannelId = httpServletRequest.getParameter("userChannelId");
      ChannelBD channelBD = new ChannelBD();
      String userChannelName = channelBD.getUserChannelName(userChannelId);
      if (channelBD.deleteUserChannel(userChannelId)) {
        tag = "view";
        LogBD logBD = new LogBD();
        Date startDate = new Date();
        String moduleCode = "system_userchannel";
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "3", 
            userChannelName, httpServletRequest.getRemoteAddr(), 
            session.getAttribute("domainId").toString());
        view(httpServletRequest);
      } else {
        tag = "deleteFailed";
        view(httpServletRequest);
      } 
    } else if (action.equals("modify")) {
      String userChannelId = httpServletRequest.getParameter("userChannelId");
      userChannelActionForm.setUserChannelId(userChannelId);
      ChannelBD channelBD = new ChannelBD();
      UserChannelPO userChannelPO = channelBD.getUserChannel(userChannelId);
      if (userChannelPO != null) {
        userChannelActionForm.setUserChannelName(userChannelPO.getUserChannelName());
        userChannelActionForm.setUserChannelOrder(userChannelPO.getUserChannelOrder());
        userChannelActionForm.setChannelReader(userChannelPO.getChannelReader());
        userChannelActionForm.setChannelReadGroup(userChannelPO.getChannelReadGroup());
        userChannelActionForm.setChannelReadOrg(userChannelPO.getChannelReadOrg());
        userChannelActionForm.setChannelReadName(userChannelPO.getChannelReadName());
      } else {
        userChannelActionForm.setUserChannelName("");
        userChannelActionForm.setUserChannelOrder("");
        userChannelActionForm.setChannelReader("");
        userChannelActionForm.setChannelReadGroup("");
        userChannelActionForm.setChannelReadOrg("");
        userChannelActionForm.setChannelReadName("");
      } 
      tag = "modify";
    } else if (action.equals("trueModify")) {
      ChannelBD channelBD = new ChannelBD();
      UserChannelPO userChannelPO = new UserChannelPO();
      userChannelPO.setUserChannelId(Long.valueOf(userChannelActionForm.getUserChannelId()));
      userChannelPO.setDomainId(Long.valueOf(domainId));
      userChannelPO.setUserChannelName(userChannelActionForm.getUserChannelName());
      userChannelPO.setUserChannelOrder(userChannelActionForm.getUserChannelOrder());
      userChannelPO.setChannelReader(userChannelActionForm.getChannelReader());
      userChannelPO.setChannelReadOrg(userChannelActionForm.getChannelReadOrg());
      userChannelPO.setChannelReadGroup(userChannelActionForm.getChannelReadGroup());
      userChannelPO.setChannelReadName(userChannelActionForm.getChannelReadName());
      channelBD.updateUserChannel(userChannelPO);
      LogBD logBD = new LogBD();
      Date startDate = new Date();
      String moduleCode = "system_userchannel";
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "2", 
          userChannelActionForm.getUserChannelName(), httpServletRequest.getRemoteAddr(), 
          session.getAttribute("domainId").toString());
      tag = "close";
    } else if (action.equals("search")) {
      tag = "view";
      String viewSql = " select aaa.userChannelId, aaa.userChannelName, aaa.userChannelOrder,aaa.channelReadName ";
      String fromSql = " com.js.oa.info.channelmanager.po.UserChannelPO aaa";
      String whereSql = " where aaa.userChannelName like '%" + httpServletRequest.getParameter("userChannelName") + "%' " + 
        " and aaa.domainId = " + domainId + " order by aaa.userChannelOrder ";
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } else {
      view(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  public void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String viewSql = " select aaa.userChannelId, aaa.userChannelName, aaa.userChannelOrder,aaa.channelReadName ";
    String fromSql = " com.js.oa.info.channelmanager.po.UserChannelPO aaa";
    String whereSql = " where aaa.domainId=" + domainId + " order by aaa.userChannelOrder ";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,userChannelName");
    httpServletRequest.setAttribute("userChannel", list);
  }
}
