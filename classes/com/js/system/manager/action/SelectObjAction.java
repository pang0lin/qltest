package com.js.system.manager.action;

import com.js.oa.crm.service.CstService;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SelectObjAction extends DispatchAction {
  public ActionForward doQueryUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = String.valueOf(request.getSession().getAttribute("domainId"));
    String userId = request.getSession().getAttribute("userId").toString();
    String orgId = request.getSession().getAttribute("orgIdString").toString();
    String condition = request.getParameter("condition");
    String text = request.getParameter("text");
    String queryType = request.getParameter("queryType");
    String inputType = request.getParameter("inputType");
    String type = request.getParameter("type");
    String radio = request.getParameter("radioType");
    String range = request.getParameter("range");
    String option = request.getParameter("option");
    ManagerService managerBD = new ManagerService();
    StringBuffer sb = new StringBuffer();
    List userList = null;
    if (queryType.equals("1")) {
      List<GroupVO> list = managerBD.getGroupList(userId, domainId, orgId, "");
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          GroupVO gv = list.get(i);
          sb.append(gv.getGroupId() + ",");
        }  
    } 
    if (!radio.equals("") && radio.equals("6")) {
      WorkLogBD worklogBD = new WorkLogBD();
      List list = worklogBD.getDownEmployeeList(userId);
      userList = managerBD.getQueryDownDownEmp(list, text);
    } else if (queryType.equals("customer")) {
      userList = managerBD.getQueryCst(text);
    } else if (queryType.equals("project")) {
      userList = managerBD.getQueryCstSell(text);
    } else {
      userList = managerBD.getQueryUserList(condition, text, queryType, sb.toString(), range, option);
    } 
    request.setAttribute("condition", condition);
    request.setAttribute("userList", userList);
    request.setAttribute("inputType", inputType);
    request.setAttribute("allowId", request.getParameter("allowId"));
    request.setAttribute("type", type);
    request.setAttribute("inorQuery", "1");
    return mapping.findForward("leftBotton");
  }
  
  public ActionForward getOrgUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = String.valueOf(request.getSession().getAttribute("domainId"));
    String condition = request.getParameter("condition");
    String orgId = request.getParameter("orgId");
    String orgName = request.getParameter("orgName");
    String text = request.getParameter("text");
    String type = request.getParameter("type");
    String orgInputType = request.getParameter("orgInputType");
    String range = request.getParameter("range");
    ManagerService managerBD = new ManagerService();
    if (orgName.indexOf(".") >= 0)
      orgName = orgName.substring(orgName.lastIndexOf(".") + 1, orgName.length()); 
    StringBuffer deptIdBuffer = new StringBuffer("");
    StringBuffer deptNameBuffer = new StringBuffer("");
    if (range.indexOf("$") < 0 && orgId.replaceAll("\\*", "").equals(StaticParam.getRootDeptId())) {
      List<OrganizationVO> orgList = null;
      if (orgList != null && orgList.size() > 0)
        for (int i = 0; i < orgList.size(); i++) {
          OrganizationVO ov = orgList.get(i);
          deptIdBuffer.append(String.valueOf(ov.getOrgId().toString()) + ",");
          deptNameBuffer.append(String.valueOf(ov.getOrgName()) + ",");
        }  
      request.setAttribute("orgId", "-1");
    } else {
      request.setAttribute("orgId", orgId);
    } 
    StringBuffer allDeptIdBuffer = new StringBuffer("");
    StringBuffer allDeptNameBuffer = new StringBuffer("");
    if (range.indexOf("$") < 0) {
      List<OrganizationVO> orgList = managerBD.getChildOrgList(domainId, range, orgId);
      if (orgList != null && orgList.size() > 0)
        for (int i = 0; i < orgList.size(); i++) {
          OrganizationVO ov = orgList.get(i);
          allDeptIdBuffer.append(String.valueOf(ov.getOrgId().toString()) + ",");
          allDeptNameBuffer.append(String.valueOf(ov.getOrgName()) + ",");
        }  
      request.setAttribute("allDeptIdBuffer", !allDeptIdBuffer.toString().equals("") ? allDeptIdBuffer.toString().substring(0, allDeptIdBuffer.toString().length() - 1) : "");
      request.setAttribute("allDeptNameBuffer", !allDeptNameBuffer.toString().equals("") ? allDeptNameBuffer.toString().substring(0, allDeptNameBuffer.toString().length() - 1) : "");
    } 
    String typeTemp = "true";
    if (type.indexOf("private") >= 0 || type.indexOf("public") >= 0)
      typeTemp = "false"; 
    request.setAttribute("orgIdString", StaticParam.getDepOrgIdString(orgId));
    request.setAttribute("condition", condition);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", type);
    request.setAttribute("allowId", request.getParameter("allowId"));
    String inputType = request.getParameter("inputType");
    request.setAttribute("orgName", orgName);
    request.setAttribute("inputType", inputType);
    if (orgInputType.indexOf("only") < 0) {
      List userList = null;
      if (range.indexOf("$") < 0 && range.indexOf("*") >= 0)
        userList = managerBD.getUserInOrgList(orgId, condition, text, typeTemp); 
      if (range.indexOf("$") >= 0 && range.indexOf("*") >= 0 && orgId.equals("2012")) {
        range = range.substring(range.indexOf("$") + 1, range.lastIndexOf("$"));
        range = range.replaceAll("\\$", ",").replaceAll(",,", ",");
        userList = managerBD.getUserInRange(range, condition);
      } 
      if (range.indexOf("$") >= 0 && range.indexOf("*") < 0 && orgId.equals("2012")) {
        range = range.substring(range.indexOf("$") + 1, range.lastIndexOf("$"));
        range = range.replaceAll("\\$", ",").replaceAll(",,", ",");
        userList = managerBD.getUserInRange(range, condition);
      } 
      if (range.indexOf("$") >= 0 && range.indexOf("*") >= 0 && !orgId.equals("2012"))
        if (range.indexOf("$") >= 0 && range.indexOf("*") >= 0) {
          range = range.substring(range.indexOf("*") + 1, range.lastIndexOf("*"));
          range = range.replaceAll("\\*", ",").replaceAll(",,", ",");
          userList = managerBD.getUserInOrgList(orgId, condition, text, typeTemp);
        } else {
          userList = managerBD.getUserInOrgList(orgId, condition, text, typeTemp);
        }  
      request.setAttribute("userList", userList);
      return mapping.findForward("leftBotton");
    } 
    return mapping.findForward("rightTop");
  }
  
  public ActionForward getPPUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = request.getSession().getAttribute("userId").toString();
    String orgId = request.getSession().getAttribute("orgId").toString();
    String condition = request.getParameter("condition");
    String classId = request.getParameter("classId");
    String className = request.getParameter("className");
    String type = request.getParameter("type");
    String text = request.getParameter("text");
    String inputType = request.getParameter("inputType");
    String orgInputType = request.getParameter("orgInputType");
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("orgId", classId);
    request.setAttribute("orgName", className);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", request.getParameter("type"));
    request.setAttribute("allowId", request.getParameter("allowId"));
    if (orgInputType.indexOf("only") < 0) {
      ManagerService managerBD = new ManagerService();
      List userList = managerBD.getUserInPrivateList(classId, condition, text, type, userId, orgId);
      request.setAttribute("userList", userList);
      return mapping.findForward("leftBotton");
    } 
    return mapping.findForward("rightTop");
  }
  
  public ActionForward getGroupUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = String.valueOf(request.getSession().getAttribute("domainId"));
    String userId = request.getSession().getAttribute("userId").toString();
    String orgId = request.getSession().getAttribute("orgIdString").toString();
    ManagerService managerBD = new ManagerService();
    String condition = request.getParameter("condition");
    String groupId = request.getParameter("groupId");
    String groupName = request.getParameter("groupName");
    String groupRange = request.getParameter("range");
    String sop = request.getParameter("sop");
    String text = request.getParameter("text");
    String inputType = request.getParameter("inputType");
    String orgInputType = request.getParameter("orgInputType");
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("orgId", groupId);
    if (sop != null && sop.equals("1"))
      request.setAttribute("orgName", String.valueOf(groupName) + "[系统]"); 
    if (sop != null && sop.equals("0"))
      request.setAttribute("orgName", String.valueOf(groupName) + "[个人]"); 
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", request.getParameter("type"));
    request.setAttribute("allowId", request.getParameter("allowId"));
    StringBuffer deptIdBuffer = new StringBuffer();
    StringBuffer deptNameBuffer = new StringBuffer();
    if (groupId != null && groupId.equals("0")) {
      List<GroupVO> orgList = null;
      if (groupRange != null && !groupRange.equals("")) {
        orgList = managerBD.getGroupListByRange(userId, domainId, groupRange, orgId);
      } else {
        orgList = managerBD.getGroupList(userId, domainId, orgId, "");
      } 
      if (orgList != null && orgList.size() > 0) {
        for (int i = 0; i < orgList.size(); i++) {
          GroupVO ov = orgList.get(i);
          deptIdBuffer.append(String.valueOf(ov.getGroupId().toString()) + ",");
          deptNameBuffer.append(String.valueOf(ov.getGroupName()) + ",");
        } 
        request.setAttribute("deptIdBuffer", !deptIdBuffer.toString().equals("") ? deptIdBuffer.toString().substring(0, deptIdBuffer.toString().length() - 1) : "");
        request.setAttribute("deptNameBuffer", !deptNameBuffer.toString().equals("") ? deptNameBuffer.toString().substring(0, deptNameBuffer.toString().length() - 1) : "");
      } 
    } 
    if (orgInputType.indexOf("only") < 0) {
      List userList = managerBD.getUserInGroupList(groupId, condition, text);
      request.setAttribute("userList", userList);
      return mapping.findForward("leftBotton");
    } 
    return mapping.findForward("rightTop");
  }
  
  public ActionForward getZWUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String condition = request.getParameter("condition");
    String classId = request.getParameter("classId");
    String className = request.getParameter("className");
    String range = request.getParameter("range");
    String inputType = request.getParameter("inputType");
    String orgInputType = request.getParameter("orgInputType");
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("orgId", classId);
    request.setAttribute("orgName", className);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", request.getParameter("type"));
    request.setAttribute("allowId", request.getParameter("allowId"));
    request.setAttribute("inorQuery", "1");
    if (orgInputType.indexOf("only") < 0) {
      ManagerService managerBD = new ManagerService();
      List userList = managerBD.getUserInGZWList(range, condition, className, "0");
      request.setAttribute("userList", userList);
      return mapping.findForward("leftBotton");
    } 
    return mapping.findForward("rightTop");
  }
  
  public ActionForward getGWUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String condition = request.getParameter("condition");
    String classId = request.getParameter("classId");
    String className = request.getParameter("className");
    String range = request.getParameter("range");
    String inputType = request.getParameter("inputType");
    String orgInputType = request.getParameter("orgInputType");
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("orgId", classId);
    request.setAttribute("orgName", className);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", request.getParameter("type"));
    request.setAttribute("allowId", request.getParameter("allowId"));
    request.setAttribute("inorQuery", "1");
    if (orgInputType.indexOf("only") < 0) {
      ManagerService managerBD = new ManagerService();
      List userList = managerBD.getUserInGZWList(range, condition, classId, "1");
      request.setAttribute("userList", userList);
      return mapping.findForward("leftBotton");
    } 
    return mapping.findForward("rightTop");
  }
  
  public ActionForward setType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String condition = request.getParameter("condition");
    String range = request.getParameter("range");
    String single = request.getParameter("single");
    String orgInputType = request.getParameter("orgInputType");
    String type = request.getParameter("type");
    String typeTemp = request.getParameter("typeTemp");
    request.setAttribute("condition", condition);
    request.setAttribute("single", single);
    request.setAttribute("range", range);
    request.setAttribute("type", type);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("allowId", request.getParameter("allowId"));
    return mapping.findForward("leftTop");
  }
  
  public ActionForward getDownEmployeeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String condition = request.getParameter("condition");
    String range = request.getParameter("range");
    String orgInputType = request.getParameter("orgInputType");
    String inputType = request.getParameter("inputType");
    String type = request.getParameter("type");
    String allowId = request.getParameter("allowId");
    String leaderName = request.getParameter("leaderName");
    String leaderId = request.getParameter("leaderId");
    WorkLogBD worklogBD = new WorkLogBD();
    List list = worklogBD.getDownEmployeeList(leaderId);
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("range", range);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", type);
    request.setAttribute("allowId", allowId);
    request.setAttribute("orgId", leaderId);
    request.setAttribute("orgName", leaderName);
    request.setAttribute("userList", list);
    if (orgInputType.indexOf("only") < 0)
      return mapping.findForward("leftBotton"); 
    return mapping.findForward("rightTop");
  }
  
  public ActionForward getCstList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String condition = request.getParameter("condition");
    String range = request.getParameter("range");
    String orgInputType = request.getParameter("orgInputType");
    String inputType = request.getParameter("inputType");
    String type = request.getParameter("type");
    String allowId = request.getParameter("allowId");
    String leaderName = request.getParameter("leaderName");
    String leaderId = request.getParameter("leaderId");
    CstService cs = new CstService();
    List list = cs.getCstList(leaderId);
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("range", range);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", type);
    request.setAttribute("allowId", allowId);
    request.setAttribute("orgId", leaderId);
    request.setAttribute("orgName", leaderName);
    request.setAttribute("userList", list);
    return mapping.findForward("leftBotton");
  }
  
  public ActionForward getProList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String condition = request.getParameter("condition");
    String range = request.getParameter("range");
    String orgInputType = request.getParameter("orgInputType");
    String inputType = request.getParameter("inputType");
    String type = request.getParameter("type");
    String allowId = request.getParameter("allowId");
    String leaderName = request.getParameter("leaderName");
    String leaderId = request.getParameter("leaderId");
    CstService cs = new CstService();
    List list = cs.getCstSellList(leaderId);
    request.setAttribute("condition", condition);
    request.setAttribute("inputType", inputType);
    request.setAttribute("range", range);
    request.setAttribute("orgInputType", orgInputType);
    request.setAttribute("type", type);
    request.setAttribute("allowId", allowId);
    request.setAttribute("orgId", leaderId);
    request.setAttribute("orgName", leaderName);
    request.setAttribute("userList", list);
    return mapping.findForward("leftBotton");
  }
  
  public ActionForward getOrgName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    StringBuffer xml = new StringBuffer(1024);
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    String orgId = request.getParameter("orgId");
    String name = StaticParam.getOrgNameByOrgId(orgId);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <name>" + name + "</name>\n");
    xml.append("</result>\n");
    out.print(xml.toString());
    out.close();
    return null;
  }
  
  public ActionForward getChildOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String domainId = String.valueOf(request.getSession().getAttribute("domainId"));
    StringBuffer xml = new StringBuffer(1024);
    ManagerService managerBD = new ManagerService();
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    String orgId = request.getParameter("orgId");
    List<OrganizationVO> list = managerBD.getChildOrgList(domainId, null, orgId);
    StringBuffer idsStr = new StringBuffer("");
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        OrganizationVO po = list.get(i);
        idsStr.append(po.getOrgId()).append(",");
      }  
    String ids = idsStr.toString();
    ids = ids.equals("") ? "null" : ids.substring(0, ids.length() - 1);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <ids>" + ids + "</ids>\n");
    xml.append("</result>\n");
    out.print(xml.toString());
    out.close();
    return null;
  }
  
  public ActionForward getOrgIdString(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String domainId = String.valueOf(request.getSession().getAttribute("domainId"));
    StringBuffer xml = new StringBuffer(1024);
    ManagerService managerBD = new ManagerService();
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    String orgId = request.getParameter("orgId");
    String parentOrgId = request.getParameter("parentOrgId");
    String returnStr = managerBD.getOrgIdString(domainId, parentOrgId, orgId);
    if (returnStr != null && !returnStr.equals("null"))
      returnStr = returnStr.substring(0, returnStr.lastIndexOf("_")); 
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <ids>" + returnStr + "</ids>\n");
    xml.append("</result>\n");
    out.print(xml.toString());
    out.close();
    return null;
  }
}
