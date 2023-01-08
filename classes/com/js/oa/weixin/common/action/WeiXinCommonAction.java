package com.js.oa.weixin.common.action;

import DBstep.iDBManager2000;
import com.js.oa.crm.po.CstType;
import com.js.oa.crm.po.ProType;
import com.js.oa.crm.service.CstService;
import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.weixin.common.service.WeiXinCommonService;
import com.js.system.manager.service.ManagerService;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.Random;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinCommonAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    String range = request.getParameter("range");
    if (range != null && "-100".equals(range))
      range = session.getAttribute("browseRange").toString(); 
    if ("selectOrgOrUser".equals(action)) {
      String selFrom = request.getParameter("selFrom");
      String html = "";
      if ("user".equals(selFrom)) {
        html = selectUsers(range, session, request);
      } else if ("organization".equals(selFrom)) {
        html = selectOrgan(range, session, request);
      } 
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(html);
    } else if ("weixinFileUpload".equals(action)) {
      weixinFileUpload(request, response);
    } else if ("encode".equals(action)) {
      encodeString(request, response);
    } else if ("fileModel".equals(action)) {
      copyFileModel(request, response);
    } else if ("jsUser".equals(action)) {
      String html = loadJSUsers(request, response);
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(html);
    } else if ("selectOrgOrUserByName".equals(action)) {
      String html = "";
      String nameStr = request.getParameter("nameStr");
      html = getUserAndOrg(nameStr);
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(html);
    } else if ("jsUserByNameStr".equals(action)) {
      String html = "";
      String nameStr = request.getParameter("nameStr");
      html = getUserAndOrgJsflow(nameStr);
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(html);
    } 
    return null;
  }
  
  private String selectOrgan(String range, HttpSession session, HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    String selType = request.getParameter("selType");
    String domainId = (session.getAttribute("domainId") != null) ? session.getAttribute("domainId").toString() : "0";
    String parentId = (request.getParameter("parentId") == null) ? "selList" : request.getParameter("parentId");
    String pId = "0";
    if (!"selList".equals(parentId))
      pId = parentId.split("_")[1]; 
    OrganizationVO vo = getRootDept(domainId);
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = " type=\"radio\" "; 
    String clickCheck = "";
    String tempRange = "";
    if (range != null && !"".equals(range)) {
      tempRange = range.replaceAll("\\*", ",").replaceAll(",,", ",");
      tempRange = tempRange.substring(tempRange.indexOf(",") + 1, tempRange.lastIndexOf(","));
    } 
    List<OrganizationVO> list = getOrgList(domainId, "0", tempRange, pId);
    if (list != null && list.size() > 0) {
      OrganizationVO orgVO = null;
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i) != null) {
          orgVO = list.get(i);
          if (!"0".equals(selType))
            clickCheck = " onclick=\"checkChild(this, 'org_" + orgVO.getOrgId() + "')\" "; 
          sb.append("<div class=\"item\">");
          sb.append("<input id=\"user_" + orgVO.getOrgId() + "\" " + inputType + clickCheck + " name=\"users\" value=\"" + orgVO.getOrgName() + "&" + orgVO.getOrgName() + "\" />");
          sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
          sb.append("<a href=\"javascript:void(0);\" onclick=\"loadItems('" + selType + "', 'organization', '', '" + range + "', '', 'org_" + orgVO.getOrgId() + "');clearThis('user_" + orgVO.getOrgId() + "')\" hidefocus>" + orgVO.getOrgName() + "</a>");
          sb.append("<div id=\"org_" + orgVO.getOrgId() + "\" class=\"children\"></div>");
          sb.append("</div>");
        } 
      } 
    } else {
      sb.append("<div class=\"item\" style=\"height: 40px; line-height: 40px;\">该组织子级组织！</div>");
    } 
    return sb.toString();
  }
  
  private String selectUsers(String range, HttpSession session, HttpServletRequest request) {
    String html = "";
    String selType = request.getParameter("selType");
    String showType = request.getParameter("showType");
    String domainId = (session.getAttribute("domainId") != null) ? session.getAttribute("domainId").toString() : "0";
    String userId = (session.getAttribute("userId") != null) ? session.getAttribute("userId").toString() : "";
    String parentId = (request.getParameter("parentId") == null) ? "selList" : request.getParameter("parentId");
    if (showType != null && !showType.equals("")) {
      if (showType.equals("orgPerson")) {
        if (range.indexOf("$") < 0 && range.indexOf("*") >= 0) {
          html = getOrgPerson(domainId, "0", range, selType, parentId);
        } else {
          html = getOrgPerson(domainId, "0", range, selType, parentId);
        } 
      } else if (showType.equals("privatePerson")) {
        html = getPrivatePerson(userId, domainId, showType, selType, parentId);
      } else if (showType.equals("publicPerson")) {
        html = getPublicPerson(userId, domainId, showType, selType, parentId);
      } else if (showType.equals("groupPerson")) {
        html = getGroupPerson(userId, domainId, range, selType, session);
      } else if (showType.equals("zhiWu")) {
        html = getZWPerson(domainId, range, selType, session);
      } else if (showType.equals("gangWei")) {
        html = getGWPerson(domainId, range, selType, session);
      } else if (showType.equals("downEmp")) {
        html = getDownEmployeeList(userId, selType);
      } else if (showType.equals("customer")) {
        html = getCustomerList();
      } else if (showType.equals("project")) {
        html = getProjectList();
      } 
    } else {
      html = getOrgPerson(domainId, "1", range, selType, parentId);
    } 
    return html;
  }
  
  private String getProjectList() {
    StringBuffer sb = new StringBuffer("");
    CstService cs = new CstService();
    List<ProType> list = cs.getObjList(ProType.class, null);
    try {
      if (list != null && list.size() > 0) {
        ProType pcp = null;
        for (int i = 0; i < list.size(); i++)
          pcp = list.get(i); 
      } else {
        sb.append("<div style=\"width: 100%; margin-top: 20px; text-align: center;\">没有人员可以选择</div>");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  private String getCustomerList() {
    StringBuffer sb = new StringBuffer("");
    CstService cs = new CstService();
    List<CstType> list = cs.getObjList(CstType.class, "1");
    try {
      if (list != null && list.size() > 0) {
        CstType pcp = null;
        for (int i = 0; i < list.size(); i++)
          pcp = list.get(i); 
      } else {
        sb.append("<div style=\"width: 100%; margin-top: 20px; text-align: center;\">没有人员可以选择</div>");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  private String getDownEmployeeList(String userId, String selType) {
    StringBuffer sb = new StringBuffer("");
    WorkLogBD worklogBD = new WorkLogBD();
    List<Object[]> list = worklogBD.getDownEmployeeList(userId);
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    try {
      if (list != null && list.size() > 0) {
        Object[] obj = (Object[])null;
        String nameAndAccounts = "";
        int userCount = 0;
        sb.append("<div class=\"item\">");
        if ("1".equals(selType)) {
          sb.append("<input id=\"org_org\" type=\"checkbox\" name=\"dept\" value=\"\" ");
          sb.append("onchange=\"selectUser(this);checkSelect('dept')\"");
          sb.append(" />");
        } 
        sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
        sb.append("<a href=\"javascript:void(0);\" onclick=\"showUsers('orgDiv_')\" hidefocus>我的下属</a>");
        sb.append("</div>");
        sb.append("<div id=\"orgDiv_\" style=\"width:100%; display: none;\">");
        for (int i = 0; i < list.size(); i++) {
          obj = list.get(i);
          userCount++;
          nameAndAccounts = obj[1] + "&" + obj[7];
          sb.append("<label for=\"user_" + obj[0] + "\">");
          sb.append("<div id=\"org_" + obj[0] + "\" class=\"item\" style=\"widht: 100%;\">");
          sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"user_" + obj[0] + "\" " + inputType + " name=\"org_org\" value=\"" + nameAndAccounts + "\" ");
          if ("1".equals(selType))
            sb.append("onchange=\"checkSelect('org_org')\" "); 
          sb.append("/>");
          sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + obj[7] + "\" title=\"" + obj[1] + "\">");
          sb.append(obj[1]);
          sb.append("</div></label>");
        } 
        if (userCount == 0)
          sb.append("<div class=\"item\" style=\"widht: 100%;\">&nbsp;&nbsp;&nbsp;&nbsp;没有下属人员！</div>"); 
        sb.append("</div>");
      } else {
        sb.append("<div style=\"width: 100%; margin-top: 20px; text-align: center;\">没有人员可以选择</div>");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  private String getGWPerson(String domainId, String range, String selType, HttpSession session) {
    StringBuffer sb = new StringBuffer("");
    String corpId = session.getAttribute("corpId").toString();
    String rangeTemp = range;
    if (rangeTemp.indexOf("*") >= 0) {
      rangeTemp = range.replaceAll("\\*", ",").replaceAll(",,", ",");
      rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
    } 
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    List<StationPO> list = getGWList(domainId, corpId);
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (list != null && list.size() > 0) {
        StationPO stationPO = null;
        String nameAndAccounts = "";
        for (int i = 0; i < list.size(); i++) {
          stationPO = list.get(i);
          sb.append("<div class=\"item\">");
          if ("1".equals(selType)) {
            sb.append("<input id=\"org_" + stationPO.getId() + "\" type=\"checkbox\" name=\"dept\" value=\"" + stationPO.getName() + "\" ");
            sb.append("onchange=\"selectUser(this);checkSelect('dept')\"");
            sb.append(" />");
          } 
          sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"30\" height=\"30\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
          sb.append("<a href=\"javascript:void(0);\" onclick=\"showUsers('orgDiv" + stationPO.getId() + "_')\" hidefocus>" + stationPO.getName() + "</a>");
          sb.append("</div>");
          sql = "SELECT emp.emp_id,emp.empname,emp.useraccounts FROM org_employee emp,ST_STATION st WHERE emp.empPositionId=st.id AND emp.empPositionId<>'' AND emp.empPositionId is not null AND emp.userisactive=1 AND emp.userisdeleted=0 AND emp.useraccounts is not null AND st.id=" + 

            
            stationPO.getId();
          sb.append("<div id=\"orgDiv" + stationPO.getId() + "_\" style=\"width:100%; display: none;\">");
          rs = stmt.executeQuery(sql);
          int userCount = 0;
          String inputName = "org_" + stationPO.getId();
          if ("0".equals(selType))
            inputName = "org_user"; 
          while (rs.next()) {
            userCount++;
            nameAndAccounts = String.valueOf(rs.getString(2)) + "&" + rs.getString(3);
            sb.append("<label for=\"user_" + rs.getString(1) + "\">");
            sb.append("<div id=\"org" + stationPO.getId() + "_" + rs.getString(1) + "\" class=\"item\" style=\"widht: 100%;\">");
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"user_" + rs.getString(1) + "\" " + inputType + " name=\"" + inputName + "\" value=\"" + nameAndAccounts + "\" ");
            if ("1".equals(selType))
              sb.append("onchange=\"checkSelect('org_" + stationPO.getId() + "')\" "); 
            sb.append("/>");
            sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + rs.getString(3) + "\" title=\"" + rs.getString(2) + "\">");
            sb.append(rs.getString(2));
            sb.append("</div></label>");
          } 
          if (userCount == 0)
            sb.append("<div class=\"item\" style=\"widht: 100%;\">&nbsp;&nbsp;&nbsp;&nbsp;该岗位没有相关人员！</div>"); 
          sb.append("</div>");
        } 
      } else {
        sb.append("<div style=\"width: 100%; margin-top: 20px; text-align: center;\">没有人员可以选择</div>");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return sb.toString();
  }
  
  private List getGWList(String domainId, String corpId) {
    List list = null;
    try {
      ManagerService mbean = new ManagerService();
      list = mbean.getGWList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private String getZWPerson(String domainId, String range, String selType, HttpSession session) {
    StringBuffer sb = new StringBuffer("");
    String rangeTemp = range;
    String corpId = session.getAttribute("corpId").toString();
    if (rangeTemp.indexOf("*") >= 0) {
      rangeTemp = range.replaceAll("\\*", ",").replaceAll(",,", ",");
      rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
    } 
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    List<DutyPO> list = getZWList(domainId, corpId);
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (list != null && list.size() > 0) {
        DutyPO dutyPO = null;
        String nameAndAccounts = "";
        for (int i = 0; i < list.size(); i++) {
          dutyPO = list.get(i);
          sb.append("<div class=\"item\">");
          if ("1".equals(selType)) {
            sb.append("<input id=\"org_" + dutyPO.getId() + "\" type=\"checkbox\" name=\"dept\" value=\"" + dutyPO.getDutyName() + "\" ");
            sb.append("onchange=\"selectUser(this);checkSelect('dept')\"");
            sb.append(" />");
          } 
          sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"30\" height=\"30\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
          sb.append("<a href=\"javascript:void(0);\" onclick=\"showUsers('orgDiv" + dutyPO.getId() + "_')\" hidefocus>" + dutyPO.getDutyName() + "</a>");
          sb.append("</div>");
          sql = "SELECT emp.emp_id,emp.empname,emp.useraccounts FROM org_employee emp,OA_DUTY duty WHERE emp.empduty=duty.dutyname AND emp.empduty<>'' AND emp.empduty is not null AND emp.userisactive=1 AND emp.userisdeleted=0 AND emp.useraccounts is not null AND duty.duty_id=" + 

            
            dutyPO.getId();
          sb.append("<div id=\"orgDiv" + dutyPO.getId() + "_\" style=\"width:100%; display: none;\">");
          rs = stmt.executeQuery(sql);
          int userCount = 0;
          String inputName = "org_" + dutyPO.getId();
          if ("0".equals(selType))
            inputName = "org_user"; 
          while (rs.next()) {
            userCount++;
            nameAndAccounts = String.valueOf(rs.getString(2)) + "&" + rs.getString(3);
            sb.append("<label for=\"user_" + rs.getString(1) + "\">");
            sb.append("<div id=\"org" + dutyPO.getId() + "_" + rs.getString(1) + "\" class=\"item\" style=\"widht: 100%;\">");
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"user_" + rs.getString(1) + "\" " + inputType + " name=\"" + inputName + "\" value=\"" + nameAndAccounts + "\" ");
            if ("1".equals(selType))
              sb.append("onchange=\"checkSelect('org_" + dutyPO.getId() + "')\" "); 
            sb.append("/>");
            sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + rs.getString(3) + "\" title=\"" + rs.getString(2) + "\">");
            sb.append(rs.getString(2));
            sb.append("</div></label>");
          } 
          if (userCount == 0)
            sb.append("<div style=\"widht: 100%;\" class=\"item\">&nbsp;&nbsp;&nbsp;&nbsp;该职务没有相关人员！</div>"); 
          sb.append("</div>");
        } 
      } else {
        sb.append("<div style=\"width: 100%; margin-top: 20px; text-align: center;\">没有人员可以选择</div>");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return sb.toString();
  }
  
  private List getZWList(String domainId, String corpId) {
    List list = null;
    try {
      ManagerService mbean = new ManagerService();
      list = mbean.getZWList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private String getGroupPerson(String userId, String domainId, String range, String selType, HttpSession session) {
    StringBuffer sb = new StringBuffer("");
    String orgId = session.getAttribute("orgIdString").toString();
    String groupRange = "";
    if (range != null)
      if (range.indexOf("@") >= 0) {
        groupRange = range.substring(range.indexOf("@") + 1, range.lastIndexOf("@"));
        groupRange = groupRange.replaceAll("\\@", ",").replaceAll(",,", ",");
      } else if (range != null && !range.equals("") && !range.equals("*0*") && !range.equals("0")) {
        groupRange = "-4";
      }  
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    List<GroupVO> list = getPrivateGroupList(userId, domainId, orgId, groupRange);
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (list != null && list.size() > 0) {
        GroupVO gPO = null;
        String nameAndAccounts = "";
        for (int i = 0; i < list.size(); i++) {
          gPO = list.get(i);
          sb.append("<div class=\"item\">");
          if ("1".equals(selType)) {
            sb.append("<input id=\"org_" + gPO.getGroupId() + "\" type=\"checkbox\" name=\"dept\" value=\"" + gPO.getGroupName() + "\" ");
            sb.append("onchange=\"selectUser(this);checkSelect('dept')\"");
            sb.append(" />");
          } 
          sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"30\" height=\"30\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
          sb.append("<a href=\"javascript:void(0);\" onclick=\"showUsers('orgDiv" + gPO.getGroupId() + "_')\" hidefocus>" + gPO.getGroupName() + "</a>");
          sb.append("</div>");
          sql = "SELECT emp.emp_id,emp.empname,emp.useraccounts FROM org_employee emp,ORG_USER_GROUP ug WHERE emp.emp_id=ug.emp_id AND emp.userisactive=1 AND emp.userisdeleted=0 AND emp.useraccounts is not null  AND ug.group_id=" + 
            
            gPO.getGroupId();
          sb.append("<div id=\"orgDiv" + gPO.getGroupId() + "_\" style=\"width:100%; display: none;\">");
          rs = stmt.executeQuery(sql);
          int userCount = 0;
          String inputName = "org_" + gPO.getGroupId();
          if ("0".equals(selType))
            inputName = "org_user"; 
          while (rs.next()) {
            userCount++;
            nameAndAccounts = String.valueOf(rs.getString(2)) + "&" + rs.getString(3);
            sb.append("<label for=\"user_" + rs.getString(1) + "\">");
            sb.append("<div id=\"org" + gPO.getGroupId() + "_" + rs.getString(1) + "\" class=\"item\" style=\"widht: 100%;\">");
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"user_" + rs.getString(1) + "\" " + inputType + " name=\"" + inputName + "\" value=\"" + nameAndAccounts + "\" ");
            if ("1".equals(selType))
              sb.append("onchange=\"checkSelect('org_" + gPO.getGroupId() + "')\" "); 
            sb.append("/>");
            sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + rs.getString(3) + "\" title=\"" + rs.getString(2) + "\">");
            sb.append(rs.getString(2));
            sb.append("</div></label>");
          } 
          if (userCount == 0)
            sb.append("<div class=\"item\" style=\"widht: 100%;\">&nbsp;&nbsp;&nbsp;该分组没有相关人员！</div>"); 
          sb.append("</div>");
        } 
      } else {
        sb.append("<div style=\"width: 100%; margin-top: 20px; text-align: center;\">没有人员可以选择</div>");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return sb.toString();
  }
  
  private List getPrivateGroupList(String userId, String domainId, String orgId, String groupRange) {
    List privateList = null;
    try {
      ManagerService mbean = new ManagerService();
      privateList = mbean.getPrivateGroupList(userId, domainId, orgId, groupRange);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private String getPublicPerson(String userId, String domainId, String showType, String selType, String parentId) {
    StringBuffer sb = new StringBuffer("");
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    String pId = "0";
    if (!"selList".equals(parentId))
      pId = parentId.split("_")[1]; 
    if ("0".equals(pId)) {
      List<PersonClassPO> list = getPublicList(userId, domainId);
      if (list != null && list.size() > 0) {
        PersonClassPO pcPO = null;
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i) != null) {
            pcPO = list.get(i);
            sb.append("<div class=\"item\">");
            sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
            sb.append("<a href=\"javascript:void(0);\" onclick=\"loadItems('" + selType + "', 'user', 'privatePerson', '', '', 'pc_" + pcPO.getId() + "')\" hidefocus>" + pcPO.getClassName() + "</a>");
            sb.append("<div id=\"pc_" + pcPO.getId() + "\" class=\"children\"></div>");
            sb.append("</div>");
          } 
        } 
      } else {
        sb.append("<div class=\"item\" style=\"height: 40px; line-height: 40px;\">没有可选分组！</div>");
      } 
    } else {
      String quertUserSQL = "SELECT linkman_id,linkmanname,linkmanname FROM oa_linkman WHERE class_id=" + pId;
      List<Object[]> userList = getUserList(quertUserSQL);
      if (userList != null && userList.size() > 0) {
        Object[] obj = (Object[])null;
        for (int i = 0; i < userList.size(); i++) {
          obj = userList.get(i);
          sb.append("<label for=\"user_" + obj[0] + "\"><div class=\"item\">");
          sb.append("<input id=\"user_" + obj[0] + "\" " + inputType + " name=\"users\" value=\"" + obj[1] + "&" + obj[2] + "\" />");
          sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + obj[2] + "\" title=\"" + obj[1] + "\">");
          sb.append("<a href=\"javascript:void(0);\" hidefocus>" + obj[1] + "</a>");
          sb.append("</div></label>");
        } 
      } else {
        sb.append("<div class=\"item\" style=\"height: 40px; line-height: 40px;\">该分组中没有可选人员！</div>");
      } 
    } 
    return sb.toString();
  }
  
  private List getPublicList(String userId, String domainId) {
    List privateList = null;
    try {
      WeiXinCommonService wxc = new WeiXinCommonService();
      privateList = wxc.getPublicList(userId, domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private String getPrivatePerson(String userId, String domainId, String showType, String selType, String parentId) {
    StringBuffer sb = new StringBuffer("");
    String inputType = " type=\"checkbox\" ";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    String pId = "0";
    if (!"selList".equals(parentId))
      pId = parentId.split("_")[1]; 
    if ("0".equals(pId)) {
      List<PersonClassPO> list = getPrivateList(userId, domainId);
      if (list != null && list.size() > 0) {
        PersonClassPO pcPO = null;
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i) != null) {
            pcPO = list.get(i);
            sb.append("<div class=\"item\">");
            sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
            sb.append("<a href=\"javascript:void(0);\" onclick=\"loadItems('" + selType + "', 'user', 'privatePerson', '', '', 'pc_" + pcPO.getId() + "')\" hidefocus>" + pcPO.getClassName() + "</a>");
            sb.append("<div id=\"pc_" + pcPO.getId() + "\" class=\"children\"></div>");
            sb.append("</div>");
          } 
        } 
      } else {
        sb.append("<div class=\"item\" style=\"height: 40px; line-height: 40px;\">没有可选分组！</div>");
      } 
    } else {
      String quertUserSQL = "SELECT linkman_id,linkmanname,linkmanname FROM oa_linkman WHERE class_id=" + pId;
      List<Object[]> userList = getUserList(quertUserSQL);
      if (userList != null && userList.size() > 0) {
        Object[] obj = (Object[])null;
        for (int i = 0; i < userList.size(); i++) {
          obj = userList.get(i);
          sb.append("<label for=\"user_" + obj[0] + "\"><div class=\"item\">");
          sb.append("<input id=\"user_" + obj[0] + "\" " + inputType + " name=\"users\" value=\"" + obj[1] + "&" + obj[2] + "\" />");
          sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + obj[2] + "\" title=\"" + obj[1] + "\">");
          sb.append("<a href=\"javascript:void(0);\" hidefocus>" + obj[1] + "</a>");
          sb.append("</div></label>");
        } 
      } else {
        sb.append("<div class=\"item\" style=\"height: 40px; line-height: 40px;\">该分组中没有可选人员！</div>");
      } 
    } 
    return sb.toString();
  }
  
  private List getPrivateList(String userId, String domainId) {
    List privateList = null;
    try {
      WeiXinCommonService wxc = new WeiXinCommonService();
      privateList = wxc.getPrivateList(userId, domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private String getOrgPerson(String domainId, String sele, String range, String selType, String parentId) {
    StringBuffer sb = new StringBuffer();
    String inputType = "type=\"checkbox\"";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    String orgRange = "", userRange = "";
    if (range != null && !"".equals(range))
      if (range.indexOf("*") >= 0 && range.indexOf("$") < 0) {
        orgRange = range.replaceAll("\\*", ",").replaceAll(",,", ",");
        orgRange = orgRange.substring(orgRange.indexOf(",") + 1, orgRange.lastIndexOf(","));
      } else if (range.indexOf("*") >= 0 && range.indexOf("$") >= 0) {
        orgRange = range.substring(range.indexOf("*") + 1, range.lastIndexOf("*"));
        orgRange.replaceAll("\\*", ",").replaceAll(",,", ",");
        userRange = range.substring(range.indexOf("$") + 1, range.lastIndexOf("$"));
        userRange.replaceAll("\\$", ",").replaceAll(",,", ",");
      } else if (range.indexOf("$") >= 0 && range.indexOf("*") < 0) {
        userRange = range.replaceAll("\\$", ",").replaceAll(",,", ",");
        userRange = userRange.substring(userRange.indexOf(",") + 1, userRange.lastIndexOf(","));
      }  
    String pId = "0";
    if (!"selList".equals(parentId)) {
      pId = parentId.split("_")[1];
    } else if (!"".equals(orgRange) && !"0".equals(orgRange)) {
      pId = getParentId(orgRange);
    } 
    int sum = 0;
    OrganizationVO orgVO = null;
    if ("0".equals(pId))
      orgVO = getRootDept(domainId); 
    List<OrganizationVO> orgList = getOrgList(domainId, sele, orgRange, pId);
    if (orgList != null && orgList.size() > 0) {
      sum += orgList.size();
      for (int i = 0; i < orgList.size(); i++) {
        if (orgList.get(i) != null) {
          orgVO = orgList.get(i);
          sb.append("<div class=\"item\">");
          sb.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
          sb.append("<a href=\"javascript:void(0);\" onclick=\"loadItems('" + selType + "', 'user', 'orgPerson', '" + range + "', '', 'org_" + orgVO.getOrgId() + "')\" hidefocus>" + orgVO.getOrgName() + "</a>");
          sb.append("<div id=\"org_" + orgVO.getOrgId() + "\" class=\"children\"></div>");
          sb.append("</div>");
        } 
      } 
    } 
    String quertUserSQL = "select ouser.emp_id,oemp.empname,oemp.useraccounts from org_organization_user ouser,org_employee oemp where oemp.emp_id=ouser.emp_id and oemp.userisformaluser=1 and oemp.userisactive=1 AND oemp.userisdeleted=0 AND oemp.useraccounts is not null and( ouser.org_id=" + 
      
      pId + " or oemp.sidelineOrg like '%*" + pId + "*%') ORDER BY EMPDUTYLEVEL,USERORDERCODE,EMPNAME";
    List<Object[]> userList = getUserList(quertUserSQL);
    if (userList != null && userList.size() > 0 && !"selList".equals(parentId)) {
      sum += userList.size();
      Object[] obj = (Object[])null;
      for (int i = 0; i < userList.size(); i++) {
        obj = userList.get(i);
        sb.append("<label for=\"user_" + obj[0] + "\"><div class=\"item\">");
        sb.append("<input id=\"user_" + obj[0] + "\" " + inputType + " name=\"users\" value=\"" + obj[1] + "&" + obj[2] + "\" />");
        sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + obj[2] + "\" title=\"" + obj[1] + "\">");
        sb.append("<a href=\"javascript:void(0);\" hidefocus>" + obj[1] + "</a>");
        sb.append("</div></label>");
      } 
    } 
    if (sum == 0)
      sb.append("<div class=\"item\" style=\"height: 40px; line-height: 40px;\">该组织中没有可选人员！</div>"); 
    return sb.toString();
  }
  
  private List<OrganizationVO> getOrgList(String domainId, String sele, String range, String pId) {
    List<OrganizationVO> orgList = null;
    try {
      WeiXinCommonService wxc = new WeiXinCommonService();
      orgList = wxc.getOrgList(domainId, range, sele, pId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return orgList;
  }
  
  private List<Object[]> getUserList(String sql) {
    List<Object[]> userList = new ArrayList();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        userList.add(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return userList;
  }
  
  private static OrganizationVO getRootDept(String domainId) {
    ManagerService mbean = new ManagerService();
    return mbean.getRootDept(domainId).get(0);
  }
  
  private void weixinFileUpload(HttpServletRequest request, HttpServletResponse response) {
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload up = new ServletFileUpload((FileItemFactory)factory);
    List list = null;
    String myRandom = "";
    String filePath = "";
    String isEdit = (request.getParameter("isEdit") == null) ? "0" : request.getParameter("isEdit");
    String path = "workflow".equals(request.getParameter("path")) ? "workflow" : "customform";
    String sn = (request.getParameter("sn") == null) ? "" : request.getParameter("sn");
    String selectFilePath = request.getParameter("selectFilePath");
    if (selectFilePath != null && !"".equals(selectFilePath))
      try {
        selectFilePath = URLDecoder.decode(URLDecoder.decode(selectFilePath, "utf-8"), "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    try {
      list = up.parseRequest(request);
      if (list != null && list.size() > 0) {
        Iterator<FileItem> it = list.iterator();
        while (it.hasNext()) {
          FileItem item = it.next();
          if (!item.isFormField() && 
            item.getName() != null && !item.getName().equals("")) {
            Calendar cal = Calendar.getInstance();
            myRandom = (new Random()).getFileRandomName();
            if ("1".equals(isEdit))
              myRandom = myRandom.substring(0, myRandom.length() - 6); 
            File tempFile = new File(item.getName());
            String tempFileName = selectFilePath.substring(selectFilePath.lastIndexOf("\\") + 1);
            String ext = tempFileName.substring(tempFileName.lastIndexOf("."));
            String fileName = String.valueOf(myRandom) + ext;
            String realPath = request.getRealPath("/");
            if ("0".equals(isEdit)) {
              fileName = String.valueOf(cal.get(1)) + "_" + fileName;
              filePath = String.valueOf(realPath) + "upload/" + cal.get(1) + "/" + path + "/";
            } else if ("1".equals(isEdit)) {
              if (sn != null && !"".equals(sn))
                fileName = String.valueOf(sn) + ext; 
              filePath = String.valueOf(realPath) + "upload/information/";
            } 
            File file = new File(filePath, fileName);
            item.write(file);
            if ("1".equals(isEdit))
              saveFileInfotoDB(request, file, myRandom, fileName, ext); 
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(String.valueOf(fileName) + ";" + tempFileName);
          } 
        } 
      } 
    } catch (Exception e) {
      try {
        response.getWriter().print("error-" + e.getMessage());
      } catch (IOException e1) {
        e1.printStackTrace();
      } 
    } 
  }
  
  private static void saveFileInfotoDB(HttpServletRequest request, File file, String myRandom, String fileName, String ext) throws Exception {
    String databaseType = SystemCommon.getDatabaseType();
    String userName = request.getSession(true).getAttribute("userName").toString();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    byte[] data = fileToByteArray(file);
    String sql = "SELECT * FROM Document_File WHERE RecordID='" + myRandom + "'";
    String documentSQL = "";
    String documentFileSQL = "";
    iDBManager2000 DbaObj = new iDBManager2000();
    PreparedStatement ps = null;
    int documentID = 0;
    int fileId = 0;
    if (DbaObj.OpenConnection()) {
      ResultSet result = DbaObj.ExecuteQuery(sql);
      if (result.next()) {
        if ("oracle".equals(databaseType)) {
          documentFileSQL = "update document_file set FileId=?,RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,filePath=?,UserName=?,Descript=? where RecordID=" + myRandom;
          fileId = result.getInt("FileId");
          documentSQL = "update document set DocumentID=?,RecordID=?,Template=?,FileType=?,HtmlPath=?,Status=? where DocumentID=" + myRandom;
          sql = "SELECT * FROM document WHERE DocumentID='" + myRandom + "'";
          result = DbaObj.ExecuteQuery(sql);
          documentID = result.getInt("DocumentID");
        } else if ("mysql".equals(databaseType)) {
          documentSQL = "update document set RecordID=?,Template=?,FileType=?,HtmlPath=?,Status=? where DocumentID=" + myRandom;
          documentFileSQL = "update document_file set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,filePath=?,UserName=?,Descript=? where RecordID=" + myRandom;
        } else {
          documentSQL = "update document set RecordID=?,Template=?,FileType=?,HtmlPath=?,Status=? where DocumentID=" + myRandom;
          documentFileSQL = "update document_file set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,filePath=?,UserName=?,Descript=? where RecordID=" + myRandom;
        } 
      } else if ("oracle".equals(databaseType)) {
        sql = "select max(DocumentID)+1 from document";
        documentID = DbaObj.GetMaxID("document", "DocumentID");
        documentSQL = "insert into document(DocumentID,RecordID,Template,FileType,HtmlPath,Status) values (?,?,?,?,?,?)";
        sql = "select max(FileId)+1 from document_file";
        fileId = DbaObj.GetMaxID("Document_File", "FileId");
        documentFileSQL = "insert into document_file(FileId,RecordID,FileName,FileType,FileSize,FileDate,FileBody,filePath,UserName,Descript) values (?,?,?,?,?,?,?,?,?,?)";
      } else if ("mysql".equals(databaseType)) {
        documentSQL = "insert into document(RecordID,Template,FileType,HtmlPath,Status) values (?,?,?,?,?)";
        documentFileSQL = "insert into document_file(RecordID,FileName,FileType,FileSize,FileDate,FileBody,filePath,UserName,Descript) values (?,?,?,?,?,?,?,?,?)";
      } else {
        documentSQL = "insert into document(RecordID,Template,FileType,HtmlPath,Status) values ('" + myRandom + "','','" + ext + "','','READ')";
        documentFileSQL = "insert into document_file(RecordID,FileName,FileType,FileSize,FileDate,FileBody,filePath,UserName,Descript) values ('" + 
          myRandom + "','" + fileName + "','" + ext + "'," + file.length() + ",'" + sdf.format(new Date()) + "','" + data + "','" + request.getRealPath("/") + "','" + userName + "','通用版本')";
      } 
      result.close();
      if ("oracle".equals(databaseType)) {
        DbaObj.Conn.prepareStatement(sql);
        ps = DbaObj.Conn.prepareStatement(documentSQL);
        ps.setInt(1, documentID);
        ps.setString(2, myRandom);
        ps.setString(3, "");
        ps.setString(4, ext);
        ps.setString(5, "");
        ps.setString(6, "READ");
        ps.execute();
        ps = DbaObj.Conn.prepareStatement(documentFileSQL);
        ps.setInt(1, fileId);
        ps.setString(2, myRandom);
        ps.setString(3, fileName);
        ps.setString(4, ext);
        ps.setLong(5, file.length());
        ps.setDate(6, DbaObj.GetDate());
        ps.setBytes(7, data);
        ps.setString(8, request.getRealPath("/"));
        ps.setString(9, userName);
        ps.setString(10, "通用版本");
        ps.execute();
        ps.close();
      } else if ("mysql".equals(databaseType)) {
        ps = DbaObj.Conn.prepareStatement(documentSQL);
        ps.setString(1, myRandom);
        ps.setString(2, "");
        ps.setString(3, ext);
        ps.setString(4, "");
        ps.setString(5, "READ");
        ps.execute();
        ps = DbaObj.Conn.prepareStatement(documentFileSQL);
        ps.setString(1, myRandom);
        ps.setString(2, fileName);
        ps.setString(3, ext);
        ps.setLong(4, file.length());
        ps.setString(5, sdf.format(new Date()));
        ps.setBytes(6, data);
        ps.setString(7, request.getRealPath("/"));
        ps.setString(8, userName);
        ps.setString(9, "通用版本");
        ps.execute();
        ps.close();
      } else {
        ps = DbaObj.Conn.prepareStatement(documentSQL);
        ps.setString(1, myRandom);
        ps.setString(2, "");
        ps.setString(3, ext);
        ps.setString(4, "");
        ps.setString(5, "READ");
        ps.execute();
        ps = DbaObj.Conn.prepareStatement(documentFileSQL);
        ps.setString(1, myRandom);
        ps.setString(2, fileName);
        ps.setString(3, ext);
        ps.setLong(4, file.length());
        ps.setString(5, sdf.format(new Date()));
        ps.setBytes(6, data);
        ps.setString(7, request.getRealPath("/"));
        ps.setString(8, userName);
        ps.setString(9, "通用版本");
        ps.execute();
        ps.close();
      } 
    } 
    DbaObj.CloseConnection();
  }
  
  private static byte[] fileToByteArray(File file) throws Exception {
    byte[] data = new byte[0];
    ByteArrayOutputStream baos = new ByteArrayOutputStream((int)file.length());
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
    int buf_size = 1024;
    byte[] buffer = new byte[buf_size];
    int len = 0;
    while (-1 != (len = bis.read(buffer, 0, buf_size)))
      baos.write(buffer, 0, len); 
    data = baos.toByteArray();
    bis.close();
    baos.close();
    return data;
  }
  
  private void encodeString(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String name = request.getParameter("fileName");
    String newName = "";
    if (name != null && !"".equals(name))
      newName = URLDecoder.decode(URLDecoder.decode(name, "utf-8"), "utf-8"); 
    if (newName.indexOf("&") >= 0 || newName.indexOf("?") >= 0)
      newName = "error-文件路径中不能包含特殊字符！"; 
    response.setCharacterEncoding("utf-8");
    response.getWriter().print(newName);
  }
  
  private void copyFileModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String filePath = String.valueOf(request.getRealPath("/")) + "upload/information/";
    String ext = request.getParameter("fileType");
    String nowTime = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString();
    File modelFile = new File(String.valueOf(filePath) + "model/", "model" + ext);
    File newFile = new File(filePath, String.valueOf(nowTime) + ext);
    FileInputStream fis = null;
    FileOutputStream fos = null;
    FileChannel ic = null;
    FileChannel oc = null;
    try {
      fis = new FileInputStream(modelFile);
      fos = new FileOutputStream(newFile);
      ic = fis.getChannel();
      oc = fos.getChannel();
      ic.transferTo(0L, ic.size(), oc);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        fis.close();
        ic.close();
        fos.close();
        oc.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    saveFileInfotoDB(request, newFile, nowTime, String.valueOf(nowTime) + ext, ext);
    response.getWriter().print(nowTime);
  }
  
  private String loadJSUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String parentId = (request.getParameter("parentId") != null && !"".equals(request.getParameter("parentId"))) ? request.getParameter("parentId") : "0";
    String type = request.getParameter("type");
    String range = request.getParameter("range");
    String showType = request.getParameter("showType");
    String selType = (request.getParameter("selType") != null) ? request.getParameter("selType") : "1";
    String participantType = request.getParameter("participantType");
    String sql = "";
    String inputType = "type=\"checkbox\"";
    if ("0".equals(selType))
      inputType = "type=\"radio\""; 
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    conn = ds.getConnection();
    stmt = conn.createStatement();
    int sum = 0;
    OrganizationVO orgVO = null;
    StringBuffer buffer = new StringBuffer();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    if ("group".equals(type)) {
      String userId = session.getAttribute("userId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String where = "";
      if ("".equals(orgIdString)) {
        where = " or 1=1";
      } else {
        orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
        String[] orgArr = orgIdString.split("\\$\\$");
        for (int i = 0; i < orgArr.length; i++)
          where = String.valueOf(where) + " or rangeorg like '%*" + orgArr[i] + "*%' "; 
        rs = stmt.executeQuery("select group_id from org_group where groupuserstring like '%$" + userId + "$%'");
        while (rs.next())
          where = String.valueOf(where) + " or rangegroup like '%@" + rs.getString(1) + "@%' "; 
        rs.close();
      } 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "select group_id,group_name from org_group where ((((rangeemp is null and rangeorg is null and rangegroup is null) or (rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$" + userId + "$%' " + where + " or '" + range + "' like concat('%@',group_id,'@%')) and (grouptype=0)) or (grouptype=1 and createdemp=" + userId + ")) and domain_id=" + domainId;
      } else {
        sql = "select group_id,group_name from org_group where ((((rangeemp is null and rangeorg is null and rangegroup is null) or (rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$" + userId + "$%' " + where + " or '" + range + "' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%@',group_id),'@%')) and (grouptype=0)) or (grouptype=1 and createdemp=" + userId + ")) and domain_id=" + domainId;
      } 
      rs = stmt.executeQuery(String.valueOf(sql) + " order by grouporder");
      String groupId = "";
      while (rs.next()) {
        groupId = rs.getString(1);
        buffer.append("<node>")
          .append("<id>").append(groupId).append("</id>")
          .append("<name>").append(rs.getString(2)).append("</name>")
          .append("<parentId>").append("group").append("</parentId>")
          .append("<level>").append(1).append("</level>")
          .append("<childCount>").append(1).append("</childCount>")
          .append("<type>subgroup</type>")
          .append("<hasHref>0</hasHref>");
        if (range.indexOf("@") >= 0) {
          if (range.indexOf("@" + groupId + "@") >= 0) {
            buffer.append("<show>1</show>");
          } else {
            buffer.append("<show>0</show>");
          } 
        } else {
          buffer.append("<show>1</show>");
        } 
        buffer.append("</node>");
      } 
      rs.close();
    } else if ("groupuser".equals(type)) {
      String parentOrgId = request.getParameter("parentOrgId");
      Statement stmtt = conn.createStatement();
      ResultSet rss = stmtt.executeQuery("select a.emp_id,a.empname from org_employee a,org_user_group b,ORG_ORGANIZATION c,org_organization_user d where a.emp_id=b.emp_id and a.emp_id=d.emp_id and d.org_id=c.org_id and b.group_id=" + parentOrgId + " and a.domain_id=" + domainId + " and a.userIsActive=1 and a.userIsDeleted=0 order by a.EMPDUTYLEVEL,c.ORGIDSTRING,a.USERORDERCODE,a.EMPNAME");
      while (rss.next())
        buffer.append("<node>")
          .append("<id>").append(rss.getString(1)).append("</id>")
          .append("<name>").append(rss.getString(2)).append("</name>")
          .append("<parentId>").append(parentOrgId).append("</parentId>")
          .append("<level>").append(2).append("</level>")
          .append("<childCount>").append("0").append("</childCount>")
          .append("<type>groupuser</type>")
          .append("<hasHref>").append(1).append("</hasHref>")
          .append("<show>").append(1).append("</show>")
          .append("</node>"); 
      rss.close();
      stmtt.close();
    } else {
      String orgIds = "";
      String groupIds = "";
      if (range != null && !range.equals("") && !"*0*".equals(range)) {
        String rangeTemp = range.replaceAll("\\*", ",");
        if (rangeTemp.indexOf(",@") > 0) {
          orgIds = rangeTemp.split(",@")[0];
          groupIds = rangeTemp.split(",@")[1];
        } else if (rangeTemp.indexOf(",") > -1) {
          orgIds = rangeTemp;
          groupIds = "0";
        } else if (rangeTemp.indexOf("@") > -1) {
          orgIds = "0";
          groupIds = rangeTemp;
        } 
        if (orgIds.indexOf(",,") >= 0) {
          orgIds = orgIds.replaceAll(",,", ",");
        } else {
          orgIds = orgIds.replaceAll(",", "");
        } 
        if (orgIds.startsWith(","))
          orgIds = orgIds.substring(1); 
        if (orgIds.endsWith(","))
          orgIds = orgIds.substring(0, orgIds.length() - 1); 
        if (groupIds.indexOf("@@") >= 0) {
          groupIds = groupIds.replaceAll("@@", ",");
        } else {
          groupIds = groupIds.replaceAll("@", "");
        } 
        if (groupIds.startsWith("@"))
          groupIds = groupIds.substring(1); 
        if (groupIds.endsWith("@"))
          groupIds = groupIds.substring(0, groupIds.length() - 1); 
      } 
      if (showType.indexOf("org") >= 0 || showType.indexOf("user") >= 0)
        if (!"0".equals(orgIds)) {
          if (!"".equals(orgIds)) {
            String[] tempOrgIds = orgIds.split(",");
            orgIds = "";
            for (int i = 0; i < tempOrgIds.length; i++) {
              sql = "select org_id,ORGIDSTRING from ORG_ORGANIZATION where ORGIDSTRING like '%$" + tempOrgIds[i] + "$%'";
              rs = stmt.executeQuery(sql);
              while (rs.next()) {
                if ("10".equals(participantType) && (
                  "$" + tempOrgIds[i] + "$").indexOf("$" + rs.getLong(1) + "$") < 0)
                  continue; 
                String orgIdString = rs.getString(2);
                if (orgIdString != null && !"".equals(orgIdString)) {
                  String[] ids = orgIdString.split("_");
                  for (int j = 2; j < ids.length; j++) {
                    if (ids[j] != null && ids[j].indexOf("$") > 0)
                      orgIds = String.valueOf(orgIds) + ids[j].split("\\$")[1] + ","; 
                  } 
                } 
              } 
            } 
            if (orgIds.endsWith(","))
              orgIds = orgIds.substring(0, orgIds.length() - 1); 
            sql = " org_id in (" + orgIds + ") and ";
          } 
          sql = "select org_id,orgname from ORG_ORGANIZATION where " + sql + 
            " orgparentorgid=" + parentId + " and orgstatus=0 order by orgidstring";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            sum++;
            buffer.append("<div style=\"margin: 2px 10px;\">");
            buffer.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
            buffer.append("<a href=\"javascript:void(0);\" onclick=\"loadUsers('org_" + rs.getLong(1) + "', '" + 
                type + "', '" + range + "', 'org', '" + rs.getLong(1) + "', '" + participantType + 
                "')\" hidefocus><span id=\"span_org_" + rs.getLong(1) + "\" style=\"padding-right: 5px;\"></span>" + rs.getString(2) + "</a>");
            buffer.append("<div id=\"org_" + rs.getLong(1) + "\" style=\"display: none;\"></div>");
            buffer.append("</div>");
          } 
          String orgManagerId = "";
          String databaseType = SystemCommon.getDatabaseType();
          if ("10".equals(participantType)) {
            sql = "select orgmanagerempid from org_organization where org_id=" + parentId;
            rs = stmt.executeQuery(sql);
            if (rs.next())
              orgManagerId = rs.getString(1); 
            rs.close();
          } 
          sql = "select emp_id,empname,useraccounts from org_employee where sidelineorg like '%*" + parentId + "*%' and userisformaluser=1 ";
          if ("10".equals(participantType))
            if (databaseType.indexOf("mysql") >= 0) {
              sql = String.valueOf(sql) + " and '" + orgManagerId + "' like concat('%$',emp_id,'$%') ";
            } else {
              sql = String.valueOf(sql) + " and '" + orgManagerId + "' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%$',emp_id),'$%') ";
            }  
          sql = String.valueOf(sql) + "and userisactive=1 and userisdeleted=0 and useraccounts is not null order by EMPDUTYLEVEL,USERORDERCODE,EMPNAME";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            sum++;
            buffer.append("<label for=\"user_" + rs.getString(1) + "\"><div style=\"margin: 2px 10px;\">");
            buffer.append("<input id=\"user_" + rs.getString(1) + "\" " + inputType + " name=\"users\" value=\"" + rs.getString(2) + "\" />");
            buffer.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=\"" + rs.getString(3) + "\">");
            buffer.append(rs.getString(2));
            buffer.append("</div></label>");
          } 
          sql = "select ouser.emp_id,oemp.empname,oemp.useraccounts from org_organization_user ouser,org_employee oemp where oemp.emp_id=ouser.emp_id and oemp.userisformaluser=1 and oemp.userisactive=1 AND oemp.userisdeleted=0 AND oemp.useraccounts is not null and ouser.org_id=" + 
            
            parentId;
          if ("10".equals(participantType))
            if (databaseType.indexOf("mysql") >= 0) {
              sql = "select emp_id,empname,useraccounts from org_employee where '" + orgManagerId + "' like concat('%$',emp_id,'$%')";
            } else {
              sql = "select emp_id,empname,useraccounts from org_employee where '" + orgManagerId + "' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%$',emp_id),'$%')";
            }  
          sql = String.valueOf(sql) + " order by EMPDUTYLEVEL,USERORDERCODE,EMPNAME";
          List<Object[]> userList = getUserList(sql);
          if (userList != null && userList.size() > 0) {
            sum += userList.size();
            Object[] obj = (Object[])null;
            for (int i = 0; i < userList.size(); i++) {
              obj = userList.get(i);
              buffer.append("<label for=\"user_" + obj[0] + "\"><div style=\"margin: 2px 10px;\">");
              buffer.append("<input id=\"user_" + obj[0] + "\" " + inputType + " name=\"users\" value=\"" + obj[1] + "\" />");
              buffer.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=\"" + obj[2] + "\">");
              buffer.append(obj[1]);
              buffer.append("</div></label>");
            } 
          } 
          if (sum == 0)
            buffer.append("<div style=\"margin: 2px 10px;\">没有可选人员！</div>"); 
        }  
      if (showType.indexOf("group") >= 0 && !"0".equals(groupIds)) {
        sql = "";
        if ("0".equals(parentId)) {
          if (!"".equals(groupIds))
            sql = " and group_id in (" + groupIds + ")"; 
          String userId = session.getAttribute("userId").toString();
          String orgIdString = session.getAttribute("orgIdString").toString();
          String where = "";
          if ("".equals(orgIdString)) {
            where = " or 1=1";
          } else {
            orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
            String[] orgArr = orgIdString.split("\\$\\$");
            for (int i = 0; i < orgArr.length; i++)
              where = String.valueOf(where) + " or rangeorg like '%*" + orgArr[i] + "*%' "; 
            rs = stmt.executeQuery("select group_id from org_group where groupuserstring like '%$" + userId + "$%'");
            while (rs.next())
              where = String.valueOf(where) + " or rangegroup like '%@" + rs.getString(1) + "@%' "; 
          } 
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "select group_id,group_name from org_group where ((((rangeemp is null and rangeorg is null and rangegroup is null) or (rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$" + 
              userId + "$%' " + where + " or '" + range + 
              "' like concat('%@',group_id,'@%')) and (grouptype=0)) or (grouptype=1 and createdemp=" + userId + ")) and domain_id=" + 
              domainId + " " + sql;
          } else {
            sql = "select group_id,group_name from org_group where ((((rangeemp is null and rangeorg is null and rangegroup is null) or (rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$" + 
              userId + "$%' " + where + " or '" + range + 
              "' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%@',group_id),'@%')) and (grouptype=0)) or (grouptype=1 and createdemp=" + 
              userId + ")) and domain_id=" + domainId + " " + sql;
          } 
          rs = stmt.executeQuery(String.valueOf(sql) + " order by grouporder");
          if (rs.next()) {
            buffer.append("<div style=\"margin: 2px 10px;\">");
            buffer.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
            buffer.append("<a href=\"javascript:void(0);\" onclick=\"loadUsers('group_0', '" + 
                type + "', '" + range + "', 'group', '选择群组', '" + participantType + 
                "')\" hidefocus>选择群组</a>");
            buffer.append("<div id=\"group_0\">");
            buffer.append("<div style=\"margin: 2px 10px;\">");
            buffer.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
            buffer.append("<a href=\"javascript:void(0);\" onclick=\"loadUsers('group_" + rs.getInt(1) + "', '" + 
                type + "', '" + range + "', 'group', '" + rs.getInt(1) + "', '" + participantType + 
                "')\" hidefocus><span id=\"span_group_" + rs.getInt(1) + "\" style=\"padding-right: 5px;\"></span>" + rs.getString(2) + "</a>");
            buffer.append("<div id=\"group_" + rs.getInt(1) + "\" style=\"display: none;\"></div>");
            buffer.append("</div>");
            while (rs.next()) {
              buffer.append("<div style=\"margin: 2px 10px;\">");
              buffer.append("<img style=\"cursor:pointer; vertical-align: middle; margin: 5px;\" border=\"0\" width=\"35\" height=\"35\" src=\"/jsoa/images/weixin/zuzhi.gif\">");
              buffer.append("<a href=\"javascript:void(0);\" onclick=\"loadUsers('group_" + rs.getInt(1) + "', '" + 
                  type + "', '" + range + "', 'group', '" + rs.getInt(1) + "', '" + participantType + 
                  "')\" hidefocus><span id=\"span_group_" + rs.getInt(1) + "\" style=\"padding-right: 5px;\"></span>" + rs.getString(2) + "</a>");
              buffer.append("<div id=\"group_" + rs.getInt(1) + "\" style=\"display: none;\"></div>");
              buffer.append("</div>");
            } 
            buffer.append("</div></div>");
          } 
        } else {
          sum = 0;
          sql = "select g.emp_id,e.empname,e.useraccounts from org_user_group g,org_employee e where g.emp_id=e.emp_id and g.group_id=" + parentId + 
            " order by EMPDUTYLEVEL,USERORDERCODE,EMPNAME";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            sum++;
            buffer.append("<label for=\"guser_" + rs.getString(1) + "\"><div style=\"margin: 2px 10px;\">");
            buffer.append("<input id=\"guser_" + rs.getString(1) + "\" " + inputType + " name=\"users\" value=\"" + rs.getString(2) + "\" />");
            buffer.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=\"" + rs.getString(3) + "\">");
            buffer.append(rs.getString(2));
            buffer.append("</div></label>");
          } 
          if (sum == 0)
            buffer.append("<div style=\"margin: 2px 10px;\">没有可选人员！</div>"); 
        } 
      } 
    } 
    if (rs != null)
      rs.close(); 
    if (stmt != null)
      stmt.close(); 
    if (conn != null)
      conn.close(); 
    return buffer.toString();
  }
  
  private String getGroupUser(String parentId) {
    String ids = "";
    String sql = "select g.emp_id,e.empname,e.useraccounts from org_user_group g,org_employee e where g.emp_id=e.emp_id and g.group_id=" + parentId + 
      " order by EMPDUTYLEVEL,USERORDERCODE,EMPNAME";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next())
        ids = String.valueOf(ids) + rs.getString(1) + ","; 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return ids;
  }
  
  private String getUserAndOrg(String nameStr) {
    StringBuffer sb = new StringBuffer();
    String where = "";
    if (nameStr.indexOf(",") > 0 || nameStr.indexOf("，") > 0) {
      nameStr = nameStr.replace("，", ",");
      String[] arr = nameStr.split(",");
      if (arr.length > 0)
        for (int i = 0; i < arr.length; i++)
          where = String.valueOf(where) + " o.empname like '%" + arr[i] + "%' or";  
      if (where.lastIndexOf("or") == where.length() - 2)
        where = where.substring(0, where.length() - 2); 
    } else if (!"".equals(nameStr)) {
      where = "o.empname like '%" + nameStr + "%'";
    } else {
      where = "1=1";
    } 
    String sql = "SELECT o.emp_id,o.empname,o.useraccounts,g.orgname FROM org_employee o LEFT JOIN org_organization_user r ON o.emp_id=r.emp_id LEFT JOIN org_organization g ON r.org_id =g.org_id where o.userisactive=1 and o.userisdeleted=0 and o.emp_id>0 and " + 
      
      where;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String inputType = "type=\"checkbox\"";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String org = "";
        if (rs.getString(4) != null)
          org = "(" + rs.getString(4) + ")"; 
        sb.append("<label for=\"user_" + rs.getString(1) + "\"><div class=\"item\">");
        sb.append("<input id=\"user_" + rs.getString(1) + "\" " + inputType + " name=\"users\" value=\"" + rs.getString(2) + "&" + rs.getString(3) + "\" />");
        sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + rs.getString(3) + "\" title=\"" + rs.getString(2) + "\">");
        sb.append("<a href=\"javascript:void(0);\" hidefocus>" + rs.getString(2) + org + "</a>");
        sb.append("</div></label>");
      } 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  private String getUserAndOrgJsflow(String nameStr) {
    StringBuffer sb = new StringBuffer();
    String where = "";
    if (nameStr.indexOf(",") > 0 || nameStr.indexOf("，") > 0) {
      nameStr = nameStr.replace("，", ",");
      String[] arr = nameStr.split(",");
      if (arr.length > 0)
        for (int i = 0; i < arr.length; i++)
          where = String.valueOf(where) + " o.empname like '%" + arr[i] + "%' or";  
      if (where.lastIndexOf("or") == where.length() - 2)
        where = where.substring(0, where.length() - 2); 
    } else if (!"".equals(nameStr)) {
      where = "o.empname like '%" + nameStr + "%'";
    } else {
      where = "1=1";
    } 
    String sql = "SELECT o.emp_id,o.empname,o.useraccounts,g.orgname FROM org_employee o LEFT JOIN org_organization_user r ON o.emp_id=r.emp_id LEFT JOIN org_organization g ON r.org_id =g.org_id where o.userisactive=1 and o.userisdeleted=0 and o.emp_id>0 and " + 
      
      where;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String inputType = "type=\"checkbox\"";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String org = "";
        if (rs.getString(4) != null)
          org = "(" + rs.getString(4) + ")"; 
        sb.append("<label for=\"user_" + rs.getString(1) + "\"><div class=\"item\">");
        sb.append("<input id=\"user_" + rs.getString(1) + "\" " + inputType + " name=\"users\" value=\"" + rs.getString(2) + "&" + rs.getString(3) + "\" />");
        sb.append("<img style=\"cursor:pointer; border=0; vertical-align: middle; margin: 5px;\" width=\"30\" height=\"30\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=" + rs.getString(3) + "\" title=\"" + rs.getString(2) + "\">");
        sb.append("<a href=\"javascript:void(0);\" hidefocus>" + rs.getString(2) + org + "</a>");
        sb.append("</div></label>");
      } 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  private String getParentId(String orgRange) {
    String parentId = "0";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "SELECT ORGPARENTORGID FROM ORG_ORGANIZATION WHERE org_id=?";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, orgRange);
      rs = pstmt.executeQuery();
      while (rs.next())
        parentId = rs.getString(1); 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return parentId;
  }
}
