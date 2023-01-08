package com.js.oa.online.action;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblUserBD;
import com.active.e_uc.user.service.TblUserStatusBD;
import com.js.oa.bbs.bean.ForumEJBBean;
import com.js.oa.online.bean.OnlinerEJBBean;
import com.js.oa.webmail.util.WebMailAccManager;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.util.StaticParam;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ReadActiveXml;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import rtx.RTXSvrApi;

public class OnlinerAction extends DispatchAction {
  public ActionForward showOnliner(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession();
    String domainId = (String)session.getAttribute("domainId");
    SysSetupReader ssReader = SysSetupReader.getInstance();
    String emailType = ssReader.emailType();
    String action = request.getParameter("action");
    String orgShow = request.getParameter("orgShow");
    String onLine = request.getParameter("onLine");
    if ("on".equals(onLine))
      onLine = "true"; 
    String isSetWebEmail = "0";
    if ("1".equals(emailType)) {
      List list = new ArrayList();
      String userId = session.getAttribute("userId").toString();
      list = WebMailAccManager.getInstance().getMyAccList(Long.valueOf(Long.parseLong(userId)));
      if (!list.isEmpty())
        isSetWebEmail = "1"; 
    } 
    String curOrgId = session.getAttribute("orgId").toString();
    String[] scopes = getScope(request);
    String scope = String.valueOf(scopes[0]) + scopes[2] + scopes[3];
    scope = scope.replace(",,", ",");
    while (scope.startsWith(","))
      scope = scope.substring(1); 
    while (scope.endsWith(","))
      scope = scope.substring(0, scope.length() - 1); 
    if (!scopes[1].equals(""))
      scope = String.valueOf(scope) + "," + curOrgId; 
    request.setAttribute("benren", scopes[1]);
    request.setAttribute("innerRange", scope);
    OnlinerEJBBean onlinerEJBBean = new OnlinerEJBBean();
    List listOnline = onlinerEJBBean.getAllOnliner();
    request.setAttribute("listOnline", Integer.valueOf(listOnline.size()));
    String innerRange = (scope == null || "".equals(scope.toString())) ? 
      "0" : request.getAttribute("innerRange").toString();
    String rangString = onlinerEJBBean.getAllOnlinerRang(innerRange, "");
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    String username = request.getParameter("username");
    if (username == null || username.equalsIgnoreCase("null"))
      username = ""; 
    String bumen = request.getParameter("bumen");
    if (bumen == null || bumen.equalsIgnoreCase("null"))
      bumen = ""; 
    Page page_ol = null;
    String sqlHead = "distinct  emp.EMP_ID,emp.EMPNAME as p1,bbb.ORGNAME,emp.EMPDUTY,emp.USERACCOUNTS as a,emp.EMPEMAIL, ";
    sqlHead = String.valueOf(sqlHead) + "emp.EMPMOBILEPHONE,emp.useraccounts as b,bbb.ORGIDSTRING,emp.EMPDUTYLEVEL,emp.USERORDERCODE, ";
    sqlHead = String.valueOf(sqlHead) + "emp.EMPNAME as p2,onl.USER_ID,emp.empposition ";
    String table = "  ORG_EMPLOYEE  emp ";
    if ("true".equals(onLine)) {
      table = String.valueOf(table) + " LEFT JOIN sec_onlineuser  onl ON onl.user_id=emp.emp_id ";
      table = String.valueOf(table) + " LEFT JOIN org_organization_user  aaa ON aaa.EMP_ID=emp.emp_id ";
      table = String.valueOf(table) + " LEFT JOIN org_organization  bbb ON bbb.ORG_ID=aaa.ORG_ID ";
    } else {
      table = String.valueOf(table) + " JOIN sec_onlineuser  onl ON onl.user_id=emp.emp_id ";
      table = String.valueOf(table) + " JOIN org_organization_user  aaa ON aaa.EMP_ID=emp.emp_id ";
      table = String.valueOf(table) + " JOIN org_organization  bbb ON bbb.ORG_ID=aaa.ORG_ID ";
    } 
    String where = "WHERE 1=1 ";
    if ("0".equals(SystemCommon.getShowUnFormalUser()))
      where = String.valueOf(where) + " and emp.useraccounts like '%_%' "; 
    if (!"".equals(scopes[1]) && "".equals(scopes[0]) && "".equals(scopes[2]) && "".equals(scopes[3]))
      where = String.valueOf(where) + " and emp.EMP_ID=" + scopes[1].replace(",", ""); 
    where = String.valueOf(where) + " and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.EMP_ID>0 and emp.DOMAIN_ID=" + domainId + " ";
    for (; scope.startsWith(","); scope = scope.substring(1));
    if (!"".equals(scope) && !"0".equals(scope)) {
      String[] scopeTmp = scope.split(",");
      if (scopeTmp.length > 999) {
        int arrCnt = (scopeTmp.length % 990 == 0) ? (scopeTmp.length / 990) : (scopeTmp.length / 990 + 1);
        String[] subArr = new String[arrCnt];
        String tempString = "";
        for (int i = 0; i < scopeTmp.length; i++) {
          if (i % 990 == 0) {
            tempString = String.valueOf(scopeTmp[i]) + ",";
          } else if ((i + 1) % 990 == 0) {
            tempString = String.valueOf(tempString) + scopeTmp[i];
            subArr[i / 990] = tempString;
          } else {
            tempString = String.valueOf(tempString) + scopeTmp[i] + ",";
          } 
        } 
        if (!"".equals(tempString)) {
          if (tempString.endsWith(","))
            tempString = tempString.substring(0, tempString.length() - 1); 
          subArr[arrCnt - 1] = tempString;
        } 
        String tempSql = " and (";
        for (int j = 0; j < subArr.length; j++) {
          if (j > 0)
            tempSql = String.valueOf(tempSql) + " or "; 
          tempSql = String.valueOf(tempSql) + " bbb.ORG_ID in (" + subArr[j] + ") ";
        } 
        where = String.valueOf(where) + tempSql + ") ";
      } else {
        where = String.valueOf(where) + " and bbb.ORG_ID in (" + scope + ")";
      } 
    } 
    String orderBy = "  ORDER BY bbb.ORGIDSTRING,emp.USERORDERCODE,emp.EMPDUTYLEVEL,emp.empName,onl.USER_ID DESC\t";
    if (!username.equals("") && ("".equals(bumen) || bumen == null))
      where = String.valueOf(where) + " and emp.EMPNAME like '%" + username + "%'"; 
    if (username.equals("") && !bumen.equals("")) {
      String orgname = (String)session.getAttribute("orgName");
      where = String.valueOf(where) + " and bbb.ORGNAME like '%" + orgname + "%'";
    } 
    if (orgShow != null && !orgShow.equals("") && !orgShow.equals("null")) {
      String orgname = (String)session.getAttribute("orgName");
      if ("0".equals(orgShow))
        orgShow = "-1"; 
      where = String.valueOf(where) + " and (aaa.org_Id=" + orgShow + " or bbb.orgIdString like '%$" + orgShow + "$%' or emp.sidelineOrg like '%*" + orgShow + "*%') ";
    } 
    String browseRange = session.getAttribute("browseRange").toString();
    if (!"*0*".equals(browseRange)) {
      browseRange = "*" + browseRange + "*";
      String[] ranges = browseRange.split("\\*\\*");
      String whereTmp = "";
      for (int i = 0; i < ranges.length; i++) {
        if (!"".equals(ranges[i]))
          if ("".equals(whereTmp)) {
            whereTmp = " and (bbb.orgIdString like '%$" + ranges[i] + "$%'";
          } else {
            whereTmp = String.valueOf(whereTmp) + " or bbb.orgIdString like '%$" + ranges[i] + "$%'";
          }  
      } 
      if (!"".equals(whereTmp))
        where = String.valueOf(where) + whereTmp + ")"; 
    } 
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List onlinerList = page_ol.getResultList();
    String us = (String)request.getSession().getAttribute("userAccount");
    List userOnlinList = null;
    String usd = ReadActiveXml.getReadActive().getUse();
    String status1 = "";
    String rtxstatusx = "";
    if ("rtx".equals(usd)) {
      RTXSvrApi rtxApi = new RTXSvrApi();
      int rtxstatus = rtxApi.userIsOnline(us);
      if (rtxstatus == 1 || rtxstatus == 2)
        rtxstatusx = "true"; 
    } 
    if ("iactive".equals(usd)) {
      TblUserBD tblUserBD = new TblUserBD();
      TblUser tblUser = new TblUser();
      TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
      tblUser = tblUserBD.findTblUser(us);
      status1 = tblUserStatusBD.findstatus(tblUser.getId());
      userOnlinList = tblUserStatusBD.findUserOnline();
    } else {
      status1 = "false";
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(page_ol.getRecordCount()));
    request.setAttribute("pageParameters", "method,username,bumen,action,orgShow,onLine");
    request.setAttribute("us", us);
    request.setAttribute("showOnline", onLine);
    request.setAttribute("usd", usd);
    request.setAttribute("status1", status1);
    request.setAttribute("isSetWebEmail", isSetWebEmail);
    request.setAttribute("emailType", emailType);
    request.setAttribute("username", username);
    request.setAttribute("rtxstatusx", rtxstatusx);
    request.setAttribute("onlinerList", onlinerList);
    request.setAttribute("userOnlinList", userOnlinList);
    if ("showOnline".equals(action))
      return mapping.findForward("showOnline"); 
    if ("showTree".equals(action))
      return mapping.findForward("showTree"); 
    return mapping.findForward("showOnline");
  }
  
  public String[] getScope(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String curOrgIdGetSub = "*" + curOrgId + "*";
    int caseValue = 0;
    String orgScopeString = "";
    String empScopeString = "";
    String viewCusOrgScopeString = "";
    String maintainCusOrgScopeString = "";
    String viewCusEmpScopeString = "";
    String maintainCusEmpScopeString = "";
    SysSetupReader ssReader = SysSetupReader.getInstance();
    String isViewAllLinkman = SysSetupReader.getLinkmanScope(domainId);
    String linkmanViewScopeString = "";
    String linkmanMaintainSceopString = "";
    if ("1".equals(isViewAllLinkman)) {
      ManagerEJBBean meb = new ManagerEJBBean();
      String browserRange = session.getAttribute("browseRange").toString();
      if ("0".equals(SystemCommon.getBrowseInner()))
        browserRange = "*0*"; 
      String corpId = session.getAttribute("corpId").toString();
      String departId = session.getAttribute("departId").toString();
      if ("1".equals(SystemCommon.getUseBrowseRange()) && !"*0*".equals(browserRange)) {
        caseValue = 1;
        if (browserRange.equals("*" + corpId + "*")) {
          orgScopeString = "," + meb.getAllorg("*" + corpId + "*");
        } else if (browserRange.equals("*" + departId + "*")) {
          orgScopeString = "," + meb.getAllorg("*" + departId + "*");
        } else {
          orgScopeString = browserRange.replaceAll("\\*\\*", "\\*");
          String[] orgStrings = orgScopeString.split("\\*");
          orgScopeString = ",";
          for (int i = 1; i < orgStrings.length; i++) {
            if (orgStrings[i] != null || !orgStrings[i].equals(""))
              orgScopeString = String.valueOf(orgScopeString) + meb.getAllorg("*" + orgStrings[i] + "*"); 
          } 
        } 
      } else {
        String rightCodeString = "08*03*01";
        Boolean isHasRight = meb.hasRight(curUserId, rightCodeString);
        if (isHasRight.booleanValue()) {
          List<Object[]> scopeList = meb.getRightScope(curUserId, rightCodeString);
          if (scopeList.size() > 0) {
            Object[] obj = scopeList.get(0);
            linkmanViewScopeString = obj[0].toString();
          } 
        } else {
          linkmanViewScopeString = "2";
        } 
        rightCodeString = "08*03*02";
        isHasRight = meb.hasRight(curUserId, rightCodeString);
        if (isHasRight.booleanValue()) {
          List<Object[]> scopeList = meb.getRightScope(curUserId, rightCodeString);
          if (scopeList.size() > 0) {
            Object[] obj = scopeList.get(0);
            linkmanMaintainSceopString = obj[0].toString();
          } 
        } 
        if (!"".equals(linkmanViewScopeString) && 
          !"4".equals(linkmanViewScopeString) && 
          !"".equals(linkmanMaintainSceopString) && 
          !"4".equals(linkmanMaintainSceopString) && 
          !"0".equals(linkmanViewScopeString) && 
          !"0".equals(linkmanMaintainSceopString)) {
          caseValue = 1;
          if ("2".equals(linkmanViewScopeString) || "2".equals(linkmanMaintainSceopString)) {
            orgScopeString = "," + meb.getAllJuniorOrgIdByRange(curOrgIdGetSub) + ",";
          } else if ("3".equals(linkmanViewScopeString) || "3".equals(linkmanMaintainSceopString)) {
            orgScopeString = "," + curOrgId + ",";
          } else {
            empScopeString = "," + curUserId + ",";
          } 
        } else if ("4".equals(linkmanViewScopeString) && "4".equals(linkmanMaintainSceopString)) {
          caseValue = 2;
          ForumEJBBean feb = new ForumEJBBean();
          String viewCusScopeString = feb.getCustomScope(curUserId, "08*03*01", "0");
          if (viewCusScopeString.indexOf("-") != 0) {
            viewCusOrgScopeString = viewCusScopeString.split("-")[0];
            viewCusOrgScopeString = viewCusOrgScopeString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
          } 
          if (viewCusScopeString.indexOf("-") != viewCusScopeString.length() - 1) {
            if ((viewCusScopeString.split("-")).length > 1) {
              viewCusEmpScopeString = viewCusScopeString.split("-")[1];
            } else {
              viewCusEmpScopeString = viewCusScopeString.split("-")[0];
            } 
            viewCusEmpScopeString = viewCusEmpScopeString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
          } 
          String maintainCusScopeString = feb.getCustomScope(curUserId, "08*03*02", "0");
          if (maintainCusScopeString.indexOf("-") != 0) {
            maintainCusOrgScopeString = maintainCusScopeString.split("-")[0];
            maintainCusOrgScopeString = maintainCusOrgScopeString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
          } 
          if (maintainCusScopeString.indexOf("-") != maintainCusScopeString.length() - 1) {
            if ((maintainCusScopeString.split("-")).length > 1) {
              maintainCusEmpScopeString = maintainCusScopeString.split("-")[1];
            } else {
              maintainCusEmpScopeString = maintainCusScopeString.split("-")[0];
            } 
            maintainCusScopeString = maintainCusScopeString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
          } 
        } else if (!"0".equals(linkmanViewScopeString) && !"0".equals(linkmanMaintainSceopString)) {
          caseValue = 3;
          if (!"".equals(linkmanViewScopeString))
            if (linkmanViewScopeString.equals("1")) {
              empScopeString = "," + curUserId + ",";
            } else if (linkmanViewScopeString.equals("2")) {
              orgScopeString = meb.getAllJuniorOrgIdByRange(curOrgIdGetSub);
            } else if (linkmanViewScopeString.equals("3")) {
              orgScopeString = "," + curOrgId + ",";
            } else if (linkmanViewScopeString.equals("4")) {
              ForumEJBBean feb = new ForumEJBBean();
              String viewCusScopeString = feb.getCustomScope(curUserId, "08*03*01", "0");
              if (viewCusScopeString.indexOf("-") != 0) {
                viewCusOrgScopeString = viewCusScopeString.split("-")[0];
                viewCusOrgScopeString = viewCusOrgScopeString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
              } 
              if (viewCusScopeString.indexOf("-") != viewCusScopeString.length() - 1) {
                if ((viewCusScopeString.split("-")).length > 1) {
                  viewCusEmpScopeString = viewCusScopeString.split("-")[1];
                } else {
                  viewCusEmpScopeString = viewCusScopeString.split("-")[0];
                } 
                viewCusEmpScopeString = viewCusEmpScopeString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
              } 
            }  
          if (!"".equals(linkmanMaintainSceopString))
            if (linkmanMaintainSceopString.equals("1")) {
              empScopeString = "," + curUserId + ",";
            } else if (linkmanMaintainSceopString.equals("2")) {
              orgScopeString = "," + meb.getAllJuniorOrgIdByRange(curOrgIdGetSub) + ",";
            } else if (linkmanMaintainSceopString.equals("3")) {
              orgScopeString = "," + curOrgId + ",";
            } else if (linkmanMaintainSceopString.equals("4")) {
              ForumEJBBean feb = new ForumEJBBean();
              String maintainCusScopeString = feb.getCustomScope(curUserId, "08*03*02", "0");
              if (maintainCusScopeString.indexOf("-") != 0) {
                maintainCusOrgScopeString = maintainCusScopeString.split("-")[0];
                maintainCusOrgScopeString = maintainCusOrgScopeString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
              } 
              maintainCusOrgScopeString = StaticParam.orgIdsByOrgId(maintainCusOrgScopeString);
              if (maintainCusScopeString.indexOf("-") != maintainCusScopeString.length() - 1) {
                if ((maintainCusScopeString.split("-")).length > 1) {
                  maintainCusEmpScopeString = maintainCusScopeString.split("-")[1];
                } else {
                  maintainCusEmpScopeString = maintainCusScopeString.split("-")[0];
                } 
                maintainCusScopeString = maintainCusScopeString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
              } 
            }  
        } 
      } 
    } 
    String[] scopes = new String[6];
    scopes[0] = orgScopeString;
    scopes[1] = empScopeString;
    scopes[2] = viewCusOrgScopeString;
    scopes[3] = maintainCusOrgScopeString;
    scopes[4] = viewCusEmpScopeString;
    scopes[5] = maintainCusEmpScopeString;
    return scopes;
  }
  
  public Map<String, String> queryUserStatus(String userIds) {
    Map<String, String> userStatusMap = null;
    String sql = "select user_id, user_status from sec_onlineuser where user_id in (USER_ID_S)";
    sql = sql.replace("USER_ID_S", userIds);
    if (userIds != null && !"".equals(userIds))
      userStatusMap = executeSelectSql(sql); 
    return userStatusMap;
  }
  
  private Map<String, String> executeSelectSql(String sql) {
    Map<String, String> map = new HashMap<String, String>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String key = rs.getString("user_id");
        String value = rs.getString("user_status");
        map.put(key, value);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException sQLException) {} 
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
    return map;
  }
  
  public void switchUserStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userStatus = request.getParameter("userStatus");
    HttpSession session = request.getSession();
    String userId = (String)session.getAttribute("userId");
    String sql = "update sec_onlineuser set user_status = USER_STATUS where user_id = USER_ID";
    sql = sql.replace("USER_STATUS", userStatus);
    sql = sql.replace("USER_ID", userId);
    boolean isSuccess = executeUpdateSql(sql);
    if (isSuccess) {
      response.setContentType("text/xml");
      response.setCharacterEncoding("GBK");
      PrintWriter out = response.getWriter();
      out.println("success");
    } 
  }
  
  private boolean executeUpdateSql(String sql) {
    boolean result = false;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      result = true;
    } catch (SQLException e) {
      e.printStackTrace();
      result = false;
    } finally {
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
    return result;
  }
}
