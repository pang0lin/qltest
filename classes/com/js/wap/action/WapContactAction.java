package com.js.wap.action;

import com.js.oa.bbs.bean.ForumEJBBean;
import com.js.oa.personalwork.person.po.PersonPO;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import com.js.wap.service.WapContactBD;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WapContactAction extends DispatchAction {
  public ActionForward personalInner(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    WapContactBD wapContactBD = new WapContactBD();
    String type = request.getParameter("type").toString();
    String isFromQuery = request.getParameter("fromQuery");
    if (isFromQuery == null)
      isFromQuery = "false"; 
    String empName = request.getParameter("empName");
    if (empName == null)
      empName = ""; 
    empName = URLDecoder.decode(empName, "utf-8");
    String orgId = request.getParameter("orgId");
    if (orgId == null)
      orgId = ""; 
    String orgQueryStr = "";
    if (!"".equals(orgId))
      orgQueryStr = " and org.orgId = " + orgId + " "; 
    String where = getWhere(request);
    String hql = "select po.empId,po.empName,po.empSex,org.orgNameString from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org where po.userIsActive=1 and po.userIsDeleted=0 and po.empId<>0 and po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + 
      
      domainId + where + " and po.empName like '%" + empName + "%' " + orgQueryStr + 
      " order by org.orgIdString,po.userOrderCode,po.empName";
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    System.out.println("==============" + hql);
    Map map = wapContactBD.getPersonalInner(hql, beginIndex, limit);
    request.setAttribute("person", map.get("list"));
    request.setAttribute("size", map.get("size"));
    request.setAttribute("empName", empName);
    request.setAttribute("isFromQuery", isFromQuery);
    request.setAttribute("orgId", orgId);
    request.setAttribute("type", type);
    return mapping.findForward("linkMan_3g");
  }
  
  public ActionForward innerMan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    WapContactBD wapContactBD = new WapContactBD();
    String empId = request.getParameter("empId").toString();
    String type = request.getParameter("type").toString();
    String hql = "select po.empName,po.empSex,po.empLivingPhoto,po.empBirth,po.empMobilePhone,po.empPhone,po.empEmail,po.empEmail2,po.empEmail3,po.empDuty,po.empAddress,po.empDescribe from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
      
      empId;
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map map = wapContactBD.getEmployee(hql, empId);
    request.setAttribute("person", map.get("person"));
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    request.setAttribute("type", type);
    request.setAttribute("userOrg", map.get("userOrganization"));
    return mapping.findForward("innerMan_3g");
  }
  
  public ActionForward commonLinkMan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    String orgIdString = (String)session.getAttribute("orgIdString");
    String userId = (String)session.getAttribute("userId");
    String type = request.getParameter("type").toString();
    String where = "";
    if (orgIdString.indexOf("$$") >= 0 && type.equals("1")) {
      where = " or po.viewScope like '%$" + userId + "$%'";
      String[] orgIdStrings = orgIdString.substring(1, orgIdString.length() - 1).split("\\$\\$");
      for (int i = 0; i < orgIdStrings.length; i++)
        where = String.valueOf(where) + " or po.viewScope like '%*" + orgIdStrings[i] + "*%'"; 
    } 
    if (type.equals("0"))
      where = " and po.createdEmpId=" + userId; 
    WapContactBD wapContactBD = new WapContactBD();
    String hql = "select po.id,po.linkManName,po.linkManSex,po.linkManClass.className,po.linkManUnit from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=" + type + " and viewscope='0' and po.domainId=" + domainId + where;
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    Map map = wapContactBD.getCommonLinkMan(hql, beginIndex, limit);
    request.setAttribute("person", map.get("list"));
    request.setAttribute("size", map.get("size"));
    request.setAttribute("type", type);
    return mapping.findForward("linkMan_3g");
  }
  
  public ActionForward linkMan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String linkManId = request.getParameter("id").toString();
    String type = request.getParameter("type").toString();
    String beginIndex = request.getParameter("beginIndex").toString();
    WapContactBD wapContactBD = new WapContactBD();
    String hql = "select po from com.js.oa.personalwork.person.po.PersonPO po where po.id=" + linkManId;
    PersonPO personPO = wapContactBD.getPersonPO(hql);
    request.setAttribute("person", personPO);
    request.setAttribute("type", type);
    if (modityRight(request, String.valueOf(personPO.getCreatedOrg()), String.valueOf(personPO.getCreatedEmpId()))) {
      request.setAttribute("modity", "yes");
    } else {
      request.setAttribute("modity", "no");
    } 
    request.setAttribute("beginIndex", beginIndex);
    return mapping.findForward("linkManinfo_3g");
  }
  
  public ActionForward addLinkMan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    String userId = (String)session.getAttribute("userId");
    WapContactBD wapContactBD = new WapContactBD();
    String type = request.getParameter("type");
    String beginIndex = request.getParameter("beginIndex");
    List list = wapContactBD.getSort(type, domainId, userId);
    if (request.getParameter("modity") != null) {
      String linkManId = request.getParameter("linkManId");
      String hql = "select po from com.js.oa.personalwork.person.po.PersonPO po where po.id=" + linkManId;
      PersonPO personPO = wapContactBD.getPersonPO(hql);
      request.setAttribute("person", personPO);
    } 
    request.setAttribute("beginIndex", beginIndex);
    request.setAttribute("list", list);
    request.setAttribute("type", type);
    return mapping.findForward("addLinkMan_3g");
  }
  
  public ActionForward classList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String id = (request.getParameter("id") == null) ? "" : request.getParameter("id");
    String domainId = (String)session.getAttribute("domainId");
    String userId = (String)session.getAttribute("userId");
    WapContactBD wapContactBD = new WapContactBD();
    String type = request.getParameter("type").toString();
    String hql = "select po from com.js.oa.personalwork.person.po.PersonClassPO po where po.empId=" + userId + 
      " and po.classType=" + type + " and po.domainId=" + domainId + " order by po.id";
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    Map map = wapContactBD.getClassList(hql, beginIndex, limit);
    request.setAttribute("linkClass", map.get("list"));
    request.setAttribute("size", map.get("size"));
    request.setAttribute("type", type);
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    request.setAttribute("id", id);
    return mapping.findForward("classList_3g");
  }
  
  public ActionForward classListMore(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    WapContactBD wapContactBD = new WapContactBD();
    String beginIndex = request.getParameter("beginIndex");
    String id = request.getParameter("classId");
    String type = request.getParameter("type");
    String personId = request.getParameter("id");
    String[] more = wapContactBD.getClassMore(id, type);
    request.setAttribute("type", type);
    request.setAttribute("classId", id);
    request.setAttribute("className", more[0]);
    request.setAttribute("classDescribe", more[1]);
    request.setAttribute("beginIndex", beginIndex);
    request.setAttribute("id", personId);
    return mapping.findForward("classMore_3g");
  }
  
  public String getWhere(HttpServletRequest request) throws Exception {
    String where = "";
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    String curUserId = request.getSession(true).getAttribute("userId").toString();
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
    boolean isViewAllLinkman = "0".equals(SysSetupReader.getLinkmanScope(domainId));
    String linkmanViewScopeString = "";
    String linkmanMaintainSceopString = "";
    if (!isViewAllLinkman) {
      ManagerEJBBean meb = new ManagerEJBBean();
      String browserRange = session.getAttribute("browseRange").toString();
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
    empScopeString = ("".equals(empScopeString) || "null".equalsIgnoreCase(empScopeString)) ? "" : empScopeString.substring(1, empScopeString.length() - 1);
    orgScopeString = ("".equals(orgScopeString) || "null".equalsIgnoreCase(orgScopeString)) ? "" : orgScopeString.substring(0, orgScopeString.length() - 1);
    viewCusOrgScopeString = ("".equals(viewCusOrgScopeString) || "null".equalsIgnoreCase(viewCusOrgScopeString)) ? "" : viewCusOrgScopeString.substring(0, viewCusOrgScopeString.length() - 1);
    maintainCusOrgScopeString = ("".equals(maintainCusOrgScopeString) || "null".equalsIgnoreCase(maintainCusOrgScopeString)) ? "" : maintainCusOrgScopeString.substring(0, maintainCusOrgScopeString.length() - 1);
    viewCusEmpScopeString = ("".equals(viewCusEmpScopeString) || "null".equalsIgnoreCase(viewCusEmpScopeString)) ? "" : viewCusEmpScopeString.substring(0, viewCusEmpScopeString.length() - 1);
    maintainCusEmpScopeString = ("".equals(maintainCusEmpScopeString) || "null".equalsIgnoreCase(maintainCusEmpScopeString)) ? "" : maintainCusEmpScopeString.substring(0, maintainCusEmpScopeString.length() - 1);
    StringBuffer org = new StringBuffer("");
    org.append(orgScopeString).append(viewCusOrgScopeString).append(maintainCusOrgScopeString).append(viewCusEmpScopeString).append(maintainCusEmpScopeString);
    if (!"".equals(empScopeString) && !"".equals(org.toString())) {
      where = String.valueOf(where) + " and org.empId in (" + empScopeString + ") or org.orgId in (" + org.toString().substring(1) + ")";
    } else if ("".equals(empScopeString) && !"".equals(org.toString())) {
      where = String.valueOf(where) + " and org.orgId in (" + org.toString().substring(1) + ")";
    } else if (!"".equals(empScopeString) && "".equals(org.toString())) {
      where = String.valueOf(where) + " and po.empId in (" + empScopeString + ")";
    } 
    return where;
  }
  
  public boolean modityRight(HttpServletRequest request, String createOrg, String createUser) {
    WapContactBD wapContactBD = new WapContactBD();
    boolean flag = false;
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String sql = "select rightscopetype,rightscopescope from org_rightscope where emp_id=" + userId + 
      " and right_id=(select right_id from org_right where rightcode='08*01*02' and domain_Id=" + 
      domainId + ") and domain_Id=" + domainId;
    String[] right = wapContactBD.getRight(sql);
    if (!"".equals(right[0]))
      if ("0".equals(right[0])) {
        flag = true;
      } else if ("2".equals(right[0])) {
        String orgsql = "select org_id from org_organization where org_id=" + orgId + " or orgparentorgid=" + orgId + " and domain_id=" + domainId;
        if (wapContactBD.getOrg(orgsql).indexOf(createOrg) >= 0)
          flag = true; 
      } else if ("3".equals(right[0])) {
        if (orgId.equals(createOrg))
          flag = true; 
      } else if ("1".equals(right[0])) {
        if (createUser.equals(userId))
          flag = true; 
      } else if (right[1].indexOf(createOrg) >= 0) {
        flag = true;
      }  
    return flag;
  }
}
