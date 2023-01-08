package com.js.wap.action;

import com.js.system.manager.service.ManagerService;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.util.DataSourceBase;
import com.js.wap.service.WapCommonService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WapCommonAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    if ("selectUser".equals(action)) {
      String html = selectUsers("", session, request);
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(html);
    } 
    return null;
  }
  
  private String selectUsers(String range, HttpSession session, HttpServletRequest request) {
    String html = "";
    String domainId = (session.getAttribute("domainId") != null) ? session.getAttribute("domainId").toString() : "0";
    String parentId = (request.getParameter("parentId") == null) ? "selList" : request.getParameter("parentId");
    String ids = (request.getParameter("ids") != null) ? request.getParameter("ids") : "userIds";
    html = getOrgPerson(domainId, parentId, ids);
    return html;
  }
  
  private String getOrgPerson(String domainId, String parentId, String ids) {
    StringBuffer sb = new StringBuffer();
    String inputType = "type=\"checkbox\"";
    String style = "";
    String pId = "0";
    if (!"orgUserList".equals(parentId)) {
      pId = parentId.split("_")[1];
    } else {
      style = "underline";
    } 
    int sum = 0;
    OrganizationVO orgVO = null;
    if ("0".equals(pId)) {
      orgVO = getRootDept(domainId);
      if (orgVO != null)
        sb.append("<div class='listHead'>" + orgVO.getOrgName() + "</div>"); 
    } 
    List<OrganizationVO> orgList = getOrgList(domainId, pId);
    if (orgList != null && orgList.size() > 0) {
      sum += orgList.size();
      for (int i = 0; i < orgList.size(); i++) {
        if (orgList.get(i) != null) {
          orgVO = orgList.get(i);
          sb.append("<div class=\"item " + style + "\">");
          sb.append("<a href=\"javascript:void(0);\" onclick=\"loadItems('org_" + orgVO.getOrgId() + "', '" + ids + "')\" hidefocus>");
          sb.append("<span id=\"span_" + orgVO.getOrgId() + "\">+</span>" + orgVO.getOrgName() + "</a>");
          sb.append("<div id=\"org_" + orgVO.getOrgId() + "\" class=\"children\"></div>");
          sb.append("</div>");
        } 
      } 
    } 
    String quertUserSQL = "select ouser.emp_id,oemp.empname from org_organization_user ouser,org_employee oemp where oemp.emp_id=ouser.emp_id and oemp.userisformaluser=1 and oemp.userisactive=1 AND oemp.userisdeleted=0 AND oemp.useraccounts is not null and ouser.org_id=" + 
      pId;
    List<Object[]> userList = getUserList(quertUserSQL);
    if (userList != null && userList.size() > 0) {
      sum += userList.size();
      Object[] obj = (Object[])null;
      for (int i = 0; i < userList.size(); i++) {
        obj = userList.get(i);
        sb.append("<label for=\"user_" + obj[0] + "\"><div class=\"userItem " + style + "\">");
        sb.append("<input id=\"user_" + obj[0] + "\" " + inputType + " name=\"users\" value=\"" + obj[1] + "\" />");
        sb.append(obj[1]);
        sb.append("</div></label>");
      } 
    } 
    if (sum == 0)
      sb.append("<div class=\"item\" style=\"height: 20px; line-height: 20px;\">该组织中没有可选人员！</div>"); 
    return sb.toString();
  }
  
  private List<OrganizationVO> getOrgList(String domainId, String pId) {
    List<OrganizationVO> orgList = null;
    try {
      WapCommonService wcs = new WapCommonService();
      orgList = wcs.getOrgList(domainId, pId);
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
        userList.add(new Object[] { rs.getString(1), rs.getString(2) });
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
}
