package com.js.oa.personalwork.person.action;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblUserBD;
import com.active.e_uc.user.service.TblUserStatusBD;
import com.js.oa.bbs.bean.ForumEJBBean;
import com.js.oa.personalwork.person.service.PersonInnerBD;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.SysSetupReader;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.MD5;
import com.js.util.util.ParameterFilter;
import com.js.util.util.ReadActiveXml;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class PersonInnerAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    if (!ParameterFilter.checkParameter(request.getParameter("order")) || 
      !ParameterFilter.checkParameter(request.getParameter("order")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    String userAccount = String.valueOf(request.getSession(true).getAttribute("userAccount"));
    Object object = request.getSession(true).getAttribute("browseRange");
    long orgId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("orgId")));
    long userId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("userId")));
    if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
      String username = (String)request.getSession().getAttribute("userAccount");
      TblUserBD tblUserBD = new TblUserBD();
      TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
      String status = "false";
      try {
        TblUser tblUser = tblUserBD.findTblUser(username);
        status = tblUserStatusBD.findstatus(tblUser.getId());
      } catch (Exception e) {
        status = "false";
      } 
      request.setAttribute("iactive", status);
      try {
        List userOnlinList = tblUserStatusBD.findUserOnline();
        request.setAttribute("userOnlinList", userOnlinList);
      } catch (HibernateException e) {
        request.setAttribute("userOnlinList", null);
      } 
    } 
    PersonInnerActionForm personInnerActionForm = (PersonInnerActionForm)actionForm;
    PersonInnerBD bd = new PersonInnerBD();
    String action = request.getParameter("action");
    String curUserId = session.getAttribute("userId").toString();
    if ("show".equals(action)) {
      String curOrgId = session.getAttribute("orgId").toString();
      String[] scopes = getScope(request);
      if (scopes != null) {
        String scope = String.valueOf(scopes[0]) + scopes[2] + scopes[3];
        scope = scope.replace(",,", ",");
        while (scope.startsWith(","))
          scope = scope.substring(1); 
        while (scope.endsWith(","))
          scope = scope.substring(0, scope.length() - 1); 
        if (!scopes[1].equals(""))
          scope = String.valueOf(scope) + "," + curOrgId; 
        request.setAttribute("innerRange", scope);
        request.setAttribute("benren", scopes[1]);
      } else {
        request.setAttribute("innerRange", "-1");
        request.setAttribute("benren", "");
      } 
      List list1 = bd.setValidOrgs(Long.valueOf(session.getAttribute("domainId").toString()));
      request.setAttribute("validOrgs", list1);
      return actionMapping.findForward("show");
    } 
    if ("list".equals(action))
      list(request); 
    if ("load".equals(action)) {
      String key = request.getParameter("key");
      String toMD5 = (new MD5()).toMD5("key-md5" + request.getParameter("editId"));
      if (!toMD5.equals(key))
        return actionMapping.findForward("error"); 
      Map result = bd.load(request.getParameter("editId"));
      if (result != null) {
        if (result.get("employeeVO") != null) {
          EmployeeVO employeeVO = (EmployeeVO)result.get("employeeVO");
          personInnerActionForm.setEmpAddress(employeeVO.getEmpAddress());
          personInnerActionForm.setEmpBusinessFax(employeeVO.getEmpBusinessFax());
          personInnerActionForm.setEmpBusinessPhone(employeeVO.getEmpBusinessPhone());
          personInnerActionForm.setEmpCounty(employeeVO.getEmpCounty());
          personInnerActionForm.setEmpCountry(employeeVO.getEmpCountry());
          personInnerActionForm.setEmpDescribe(employeeVO.getEmpDescribe());
          personInnerActionForm.setEmpDuty(employeeVO.getEmpDuty());
          personInnerActionForm.setEmpEmail(employeeVO.getEmpEmail());
          personInnerActionForm.setEmpEmail2(employeeVO.getEmpEmail2());
          personInnerActionForm.setEmpEmail3(employeeVO.getEmpEmail3());
          personInnerActionForm.setEmpEnglishName(employeeVO.getEmpEnglishName());
          personInnerActionForm.setEmpMobilePhone(employeeVO.getEmpMobilePhone());
          personInnerActionForm.setEmpName(employeeVO.getEmpName());
          personInnerActionForm.setEmpPhone(employeeVO.getEmpPhone());
          personInnerActionForm.setEmpPosition(employeeVO.getEmpPosition());
          personInnerActionForm.setEmpSex(employeeVO.getEmpSex());
          personInnerActionForm.setEmpState(employeeVO.getEmpState());
          personInnerActionForm.setEmpWebAddress(employeeVO.getEmpWebAddress());
          request.setAttribute("myEmpBirth", employeeVO.getEmpBirth());
          request.setAttribute("EmpImg", employeeVO.getEmpLivingPhoto());
          request.setAttribute("empPositionId", employeeVO.getEmpPositionId());
        } 
        if (result.get("userOrganization") != null)
          personInnerActionForm.setUserOrganization(result.get("userOrganization").toString()); 
      } 
      personInnerActionForm.setQueryItem(request.getParameter("queryItem"));
      personInnerActionForm.setQueryText(request.getParameter("queryText"));
      List countries = bd.see();
      request.setAttribute("countries", countries);
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      EmployeeVO empVO = new EmployeeVO();
      empVO.setEmpId(Long.parseLong(personInnerActionForm.getEditId()));
      empVO.setEmpAddress(personInnerActionForm.getEmpAddress());
      empVO.setEmpBusinessFax(personInnerActionForm.getEmpBusinessFax());
      empVO.setEmpBusinessPhone(personInnerActionForm.getEmpBusinessPhone());
      empVO.setEmpCountry(personInnerActionForm.getEmpCountry());
      empVO.setEmpCounty(personInnerActionForm.getEmpCounty());
      empVO.setEmpDescribe(personInnerActionForm.getEmpDescribe());
      empVO.setEmpEmail(personInnerActionForm.getEmpEmail());
      empVO.setEmpEmail2(personInnerActionForm.getEmpEmail2());
      empVO.setEmpEmail3(personInnerActionForm.getEmpEmail3());
      empVO.setEmpEnglishName(personInnerActionForm.getEmpEnglishName());
      empVO.setEmpMobilePhone(personInnerActionForm.getEmpMobilePhone());
      empVO.setEmpName(personInnerActionForm.getEmpName());
      empVO.setEmpPhone(personInnerActionForm.getEmpPhone());
      empVO.setEmpSex(personInnerActionForm.getEmpSex());
      empVO.setEmpState(personInnerActionForm.getEmpState());
      empVO.setEmpWebAddress(personInnerActionForm.getEmpWebAddress());
      empVO.setEmpBirth(new Date(personInnerActionForm.getEmpBirth1().replaceAll("-", "/")));
      bd.update(empVO, userAccount);
      list(request);
    } 
    if ("city".equals(action)) {
      request.setAttribute("cities", bd.city(request.getParameter("country")));
      return actionMapping.findForward("ifmcity");
    } 
    if ("county".equals(action)) {
      String country = request.getParameter("country");
      String city = request.getParameter("city");
      request.setAttribute("counties", bd.county(country, city));
      return actionMapping.findForward("ifmcounty");
    } 
    if ("export".equals(action)) {
      export(request, "");
      return actionMapping.findForward("export");
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String domainId = (String)session.getAttribute("domainId");
    ManagerService mbd = new ManagerService();
    String queryItem = request.getParameter("queryItem");
    String queryText = request.getParameter("queryText");
    String empName = request.getParameter("empName");
    String empSex = request.getParameter("empSex");
    String orgName = request.getParameter("orgName");
    String empEmail = request.getParameter("empEmail");
    String empBusinessPhone = request.getParameter("empBusinessPhone");
    String empMobilePhone = request.getParameter("empMobilePhone");
    String empAddress = request.getParameter("empAddress");
    String orgShow = (request.getParameter("orgShow") == null) ? "" : request.getParameter("orgShow");
    String order = request.getParameter("order");
    String desc = request.getParameter("desc");
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
          if ("".equals(linkmanMaintainSceopString) && "".equals(linkmanViewScopeString))
            orgScopeString = "," + meb.getAllJuniorOrgIdByRange(curOrgIdGetSub) + ","; 
        } 
      } 
    } 
    if ("2".equals(isViewAllLinkman)) {
      String corpId = session.getAttribute("corpId").toString();
      ManagerEJBBean meb = new ManagerEJBBean();
      orgScopeString = "," + meb.getAllorg("*" + corpId + "*");
      caseValue = 1;
    } 
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset / pageSize + 1;
    String sqlOrder = " order by org.orgIdString,po.userOrderCode,po.empDutyLevel,po.empName";
    if ("1".equals(order)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sqlOrder = " order by convert( po.empName using gbk )";
      } else {
        sqlOrder = " ORDER BY NLSSORT(po.empName, 'NLS_SORT=SCHINESE_PINYIN_M') ";
      } 
    } else if ("2".equals(order)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sqlOrder = " order by convert( org.orgNameString using gbk )";
      } else {
        sqlOrder = " ORDER BY NLSSORT(org.orgNameString, 'NLS_SORT=SCHINESE_PINYIN_M') ";
      } 
    } else if ("3".equals(order)) {
      if ("2".equals(desc)) {
        sqlOrder = " order by po.empState desc , po.empCounty";
      } else {
        sqlOrder = " order by po.empState , po.empCounty";
      } 
    } else if ("4".equals(order)) {
      sqlOrder = " order by po.empId";
    } 
    String sqlOrderDisc = "";
    if ("2".equals(desc))
      sqlOrderDisc = " desc"; 
    sqlOrder = String.valueOf(sqlOrder) + sqlOrderDisc;
    StringBuffer where = new StringBuffer("where po.userIsActive=1 and po.userIsDeleted=0 and po.empId<>0 ");
    if ("0".equals(SystemCommon.getShowUnFormalUser()))
      where.append(" and po.userAccounts like '%_%' "); 
    if (queryItem != null && queryText != null && !"".equals(queryItem) && !"".equals(queryText)) {
      if (queryItem != null && !"".equals(queryItem) && !"".equals(queryText)) {
        where.append(" and (");
        if (queryItem.equals("empEmail")) {
          where.append("po.empEmail like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empEmail2 like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empEmail3 like '%")
            .append(queryText)
            .append("%'");
        } else if (queryItem.equals("orgName")) {
          where.append("org.orgIdString like '%$").append(queryText).append("$%'");
        } else if (queryItem.equals("empAddress")) {
          where.append(" po.empCountry like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empState like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empCounty like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empAddress like '%")
            .append(queryText)
            .append("%'");
          if (queryText != null && queryText.indexOf(".") != -1) {
            String tmpState = queryText.substring(0, queryText.indexOf("."));
            String tmpCounty = queryText.substring(queryText.indexOf(".") + 1, queryText.length());
            if (!"".equals(tmpState))
              where.append(" or ")
                .append(" po.empState like '%")
                .append(tmpState).append("%'"); 
            if (!"".equals(tmpCounty))
              where.append(" or ")
                .append(" po.empCounty like '%")
                .append(tmpCounty).append("%'"); 
          } 
        } else {
          where.append("po.").append(queryItem).append(" like '%").append(queryText).append("%'");
        } 
        where.append(")");
      } else if (queryText != null) {
        where.append(" and (");
        where.append("po.empEmail like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empEmail2 like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empEmail3 like '%")
          .append(queryText)
          .append("%' or ")
          .append("org.orgNameString like '%")
          .append(queryText)
          .append("%' or ")
          .append("po.empName like '%")
          .append(queryText)
          .append("%' or ")
          .append("po.empBusinessPhone like '%")
          .append(queryText)
          .append("%' or ")
          .append("po.empMobilePhone like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empCountry like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empState like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empCounty like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empAddress like '%")
          .append(queryText)
          .append("%'");
        if (queryText != null && queryText.indexOf(".") != -1) {
          String tmpState = queryText.substring(0, queryText.indexOf("."));
          String tmpCounty = queryText.substring(queryText.indexOf(".") + 1, queryText.length());
          if (!"".equals(tmpState))
            where.append(" or ")
              .append(" po.empState like '%")
              .append(tmpState).append("%'"); 
          if (!"".equals(tmpCounty))
            where.append(" or ")
              .append(" po.empCounty like '%")
              .append(tmpCounty).append("%'"); 
        } 
        where.append(")");
      } 
    } else {
      if (empEmail != null && !"null".equals(empEmail) && !"".equals(empEmail))
        where.append(" and (po.empEmail like '%")
          .append(empEmail)
          .append("%' or ")
          .append(" po.empEmail2 like '%")
          .append(empEmail)
          .append("%' or ")
          .append(" po.empEmail3 like '%")
          .append(empEmail)
          .append("%' )"); 
      if (orgName != null && !"null".equals(orgName) && !"".equals(orgName))
        where.append(" and org.orgIdString like '%$").append(orgName).append("$%'"); 
      if (empAddress != null && !"null".equals(empAddress) && !"".equals(empAddress)) {
        where.append(" and (po.empCountry like '%")
          .append(empAddress)
          .append("%' or ")
          .append(" po.empState like '%")
          .append(empAddress)
          .append("%' or ")
          .append(" po.empCounty like '%")
          .append(empAddress)
          .append("%' or ")
          .append(" po.empAddress like '%")
          .append(empAddress)
          .append("%'");
        if (empAddress != null && empAddress.indexOf(".") != -1) {
          String tmpState = empAddress.substring(0, empAddress.indexOf("."));
          String tmpCounty = empAddress.substring(empAddress.indexOf(".") + 1, empAddress.length());
          if (!"".equals(tmpState))
            where.append(" or ")
              .append(" po.empState like '%")
              .append(tmpState).append("%'"); 
          if (!"".equals(tmpCounty))
            where.append(" or ")
              .append(" po.empCounty like '%")
              .append(tmpCounty).append("%'"); 
        } 
        where.append(")");
      } 
      if (empName != null && !"null".equals(empName) && !"".equals(empName))
        where.append(" and po.empName").append(" like '%").append(empName).append("%'"); 
      if (empSex != null && !"null".equals(empSex) && !"".equals(empSex))
        where.append(" and po.empSex").append(" like '%").append(empSex).append("%'"); 
      if (empBusinessPhone != null && !"null".equals(empBusinessPhone) && !"".equals(empBusinessPhone))
        where.append(" and po.empBusinessPhone").append(" like '%").append(empBusinessPhone).append("%'"); 
      if (empMobilePhone != null && !"null".equals(empMobilePhone) && !"".equals(empMobilePhone))
        where.append(" and po.empMobilePhone").append(" like '%").append(empMobilePhone).append("%'"); 
    } 
    PersonInnerBD pbd = new PersonInnerBD();
    where.append(" and po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + domainId + " ");
    String show = "";
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    FileInputStream configFileInputStream = new FileInputStream(
        new File(configFile));
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(configFileInputStream);
    Element root = doc.getRootElement();
    Element node = root.getChild("PersonInner");
    show = node.getAttributeValue("show");
    if ("0".equals(show))
      if (request.getParameter("orgtype") != null && !"".equals(request.getParameter("orgtype").toString())) {
        request.setAttribute("orgtype", request.getParameter("orgtype").toString());
        String orgids = mbd.getAllJuniorOrgIdByRange("*" + request.getParameter("orgtype").toString() + "*");
        if (orgids != null && !orgids.equals("")) {
          where.append(" and (");
          String[] tmp = orgids.split(",");
          int max = 500;
          if (tmp.length > max) {
            int t = (tmp.length % max == 0) ? (tmp.length / max) : (tmp.length / max + 1);
            for (int i = 0; i < t; i++) {
              if (i == 0) {
                where.append("org.orgId in (-1");
                for (int j = 0; j < max; j++) {
                  if (i * max + j < tmp.length)
                    where.append(",").append(tmp[i * max + j]); 
                } 
                where.append(")");
              } else {
                where.append(" or org.orgId in (-1");
                for (int j = 0; j < max; j++) {
                  if (i * max + j < tmp.length)
                    where.append(",").append(tmp[i * max + j]); 
                } 
                where.append(")");
              } 
            } 
          } else {
            where.append("org.orgId in (");
            where.append(orgids);
            where.append(")");
          } 
          where.append(")");
        } 
        String wherePara1 = mbd.getRightFinalWhere(curUserId, request.getParameter("orgtype").toString(), "08*03*01", "org.id", "");
        where.append(" and " + wherePara1);
        String wherePara = mbd.getRightFinalWhere(curUserId, session.getAttribute("orgId").toString(), "08*03*01", "org.id", "");
        where.append(" and " + wherePara);
      } else {
        String wherePara = mbd.getRightFinalWhere(curUserId, session.getAttribute("orgId").toString(), "08*03*01", "org.id", "");
        where.append(" and (" + wherePara + ")");
      }  
    if (!orgShow.equals("") && !"0".equals(orgShow) && !"null".equals(orgShow))
      where.append(" and (org.orgIdString like '%$" + orgShow + "$%' or  po.sidelineOrg like '%*" + orgShow + "*%') "); 
    Page page = new Page(
        " po.empId,po.empName,po.empSex,po.empEmail,po.empEmail2,po.empEmail3,po.empBusinessPhone,po.empMobilePhone,po.createdOrg,po.browseRange,org.orgNameString,po.browseRange,org.orgId,po.empCounty,org.orgIdString,po.empState,po.userAccounts,po.empPhone,po.empMobilePhone,po.userOnline,po.empPosition,po.empDuty,po.empPositionId,po.curStatus", 

        
        " com.js.system.vo.usermanager.EmployeeVO po join po.organizations org  ", 
        String.valueOf(where.toString()) + sqlOrder);
    if ("1".equals(isViewAllLinkman) || "2".equals(isViewAllLinkman)) {
      page.setPageSize(1000000);
      page.setcurrentPage(1);
    } else {
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
    } 
    List<Object[]> list = new ArrayList();
    String recordCount = "0";
    String pageCount = "1";
    try {
      list = page.getResultList();
      page.setPageSize(pageSize);
      recordCount = String.valueOf(page.getRecordCount());
      pageCount = String.valueOf(page.getPageCount());
    } catch (Throwable throwable) {}
    List list1 = pbd.setValidOrgs(Long.valueOf(session.getAttribute("domainId").toString()));
    boolean hasRightScope = mbd.hasRightTypeName(curUserId, "内部联系人", "修改");
    if (hasRightScope) {
      List rightList = mbd.getRightScope(curUserId, "内部联系人", "修改");
      request.setAttribute("rightList", rightList);
    } 
    if ("1".equals(isViewAllLinkman) || "2".equals(isViewAllLinkman)) {
      int i, j, k;
      Object[] objects = (Object[])null;
      String[] orgScopeStrings = (String[])null;
      String[] empScopeStrings = (String[])null;
      String[] viewCusOrgScopeStrings = (String[])null;
      String[] viewCusEmpScopeStrings = (String[])null;
      String[] maintainCusOrgScopeStrings = (String[])null;
      String[] maintainCusEmpScopeStrings = (String[])null;
      switch (caseValue) {
        case 1:
          for (i = list.size() - 1; i >= 0; i--) {
            boolean flagCaseOne = false;
            objects = list.get(i);
            if (orgScopeString.length() > 2) {
              orgScopeStrings = orgScopeString.split(",");
              for (int m = 0; m < orgScopeStrings.length; m++) {
                if (objects[12].toString().equals(orgScopeStrings[m])) {
                  flagCaseOne = true;
                  break;
                } 
              } 
            } 
            if (empScopeString.length() > 2)
              if (!flagCaseOne) {
                empScopeStrings = empScopeString.split(",");
                for (int m = 0; m < empScopeStrings.length; m++) {
                  if (objects[0].toString().equals(empScopeStrings[m])) {
                    flagCaseOne = true;
                    break;
                  } 
                } 
              }  
            if (!flagCaseOne)
              list.remove(i); 
          } 
          break;
        case 2:
          for (j = list.size() - 1; j >= 0; j--) {
            boolean flagCaseTwo = false;
            objects = list.get(j);
            if (viewCusOrgScopeString.length() > 2) {
              viewCusOrgScopeStrings = viewCusOrgScopeString.split(",");
              for (int m = 0; m < viewCusOrgScopeStrings.length; m++) {
                if (objects[12].toString().equals(viewCusOrgScopeStrings[m])) {
                  flagCaseTwo = true;
                  break;
                } 
              } 
            } 
            if (viewCusEmpScopeString.length() > 2)
              if (!flagCaseTwo) {
                viewCusEmpScopeStrings = viewCusEmpScopeString.split(",");
                for (int m = 0; m < viewCusEmpScopeStrings.length; m++) {
                  if (objects[0].toString().equals(viewCusEmpScopeStrings[m])) {
                    flagCaseTwo = true;
                    break;
                  } 
                } 
              }  
            if (maintainCusOrgScopeString.length() > 2)
              if (!flagCaseTwo) {
                maintainCusOrgScopeStrings = maintainCusOrgScopeString.split(",");
                for (int m = 0; m < maintainCusOrgScopeStrings.length; m++) {
                  if (objects[12].toString().equals(maintainCusOrgScopeStrings[m])) {
                    flagCaseTwo = true;
                    break;
                  } 
                } 
              }  
            if (maintainCusEmpScopeString.length() > 2)
              if (!flagCaseTwo) {
                maintainCusEmpScopeStrings = maintainCusEmpScopeString.split(",");
                for (int m = 0; m < maintainCusEmpScopeStrings.length; m++) {
                  if (objects[0].toString().equals(maintainCusEmpScopeStrings[m])) {
                    flagCaseTwo = true;
                    break;
                  } 
                } 
              }  
            if (!flagCaseTwo)
              list.remove(j); 
          } 
          break;
        case 3:
          for (k = list.size() - 1; k >= 0; k--) {
            boolean flagCaseThree = false;
            objects = list.get(k);
            if (orgScopeString.length() > 2) {
              orgScopeStrings = orgScopeString.split(",");
              for (int m = 0; m < orgScopeStrings.length; m++) {
                if (objects[12].toString().equals(orgScopeStrings[m])) {
                  flagCaseThree = true;
                  break;
                } 
              } 
            } 
            if (empScopeString.length() > 2)
              if (!flagCaseThree) {
                empScopeStrings = empScopeString.split(",");
                for (int m = 0; m < empScopeStrings.length; m++) {
                  if (objects[0].toString().equals(empScopeStrings[m])) {
                    flagCaseThree = true;
                    break;
                  } 
                } 
              }  
            if (viewCusOrgScopeString.length() > 2)
              if (!flagCaseThree) {
                viewCusOrgScopeStrings = viewCusOrgScopeString.split(",");
                for (int m = 0; m < viewCusOrgScopeStrings.length; m++) {
                  if (objects[12].toString().equals(viewCusOrgScopeStrings[m])) {
                    flagCaseThree = true;
                    break;
                  } 
                } 
              }  
            if (viewCusEmpScopeString.length() > 2)
              if (!flagCaseThree) {
                viewCusEmpScopeStrings = viewCusEmpScopeString.split(",");
                for (int m = 0; m < viewCusEmpScopeStrings.length; m++) {
                  if (objects[0].toString().equals(viewCusEmpScopeStrings[m])) {
                    flagCaseThree = true;
                    break;
                  } 
                } 
              }  
            if (maintainCusOrgScopeString.length() > 2)
              if (!flagCaseThree) {
                maintainCusOrgScopeStrings = maintainCusOrgScopeString.split(",");
                for (int m = 0; m < maintainCusOrgScopeStrings.length; m++) {
                  if (objects[12].toString().equals(maintainCusOrgScopeStrings[m])) {
                    flagCaseThree = true;
                    break;
                  } 
                } 
              }  
            if (maintainCusEmpScopeString.length() > 2)
              if (!flagCaseThree) {
                maintainCusEmpScopeStrings = maintainCusEmpScopeString.split(",");
                for (int m = 0; m < maintainCusEmpScopeStrings.length; m++) {
                  if (objects[0].toString().equals(maintainCusEmpScopeStrings[m])) {
                    flagCaseThree = true;
                    break;
                  } 
                } 
              }  
            if (!flagCaseThree)
              list.remove(k); 
          } 
          break;
      } 
      String recordCountString = String.valueOf(list.size());
      request.setAttribute("recordCount", recordCountString);
      if (list.size() >= currentPage * pageSize) {
        list = list.subList((currentPage - 1) * pageSize, currentPage * pageSize);
      } else {
        list = list.subList((currentPage - 1) * pageSize, list.size());
      } 
    } 
    request.setAttribute("validOrgs", list1);
    request.setAttribute("mylist", list);
    if ("0".equals(isViewAllLinkman))
      request.setAttribute("recordCount", recordCount); 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,order,desc,queryItem,queryText,color,orgtype,empName,empSex,orgName,empEmail,empBusinessPhone,empMobilePhone,empAddress,orgShow");
  }
  
  private void export(HttpServletRequest request, String swhere) {
    HttpSession session = request.getSession(true);
    ManagerService mbd = new ManagerService();
    String queryItem = request.getParameter("queryItem");
    String queryText = request.getParameter("queryText");
    String empName = request.getParameter("empName");
    String empSex = request.getParameter("empSex");
    String orgName = request.getParameter("orgName");
    String empEmail = request.getParameter("empEmail");
    String empBusinessPhone = request.getParameter("empBusinessPhone");
    String empMobilePhone = request.getParameter("empMobilePhone");
    String empAddress = request.getParameter("empAddress");
    String personids = request.getParameter("personids");
    String order = request.getParameter("order");
    String desc = request.getParameter("desc");
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    if (request.getParameter("orgtype") != null && !request.getParameter("orgtype").toString().equals(""))
      request.setAttribute("orgtype", request.getParameter("orgtype").toString()); 
    int pageSize = 1000000;
    int currentPage = offset / pageSize + 1;
    String sqlOrder = " order by org.orgIdString,po.userOrderCode ";
    if ("1".equals(order)) {
      sqlOrder = " order by po.empName";
    } else if ("2".equals(order)) {
      sqlOrder = " order by org.orgId";
    } else if ("3".equals(order)) {
      if ("2".equals(desc)) {
        sqlOrder = " order by po.empState desc , po.empCounty";
      } else {
        sqlOrder = " order by po.empState , po.empCounty";
      } 
    } else if ("4".equals(order)) {
      sqlOrder = " order by po.empId";
    } 
    String sqlOrderDisc = "";
    if ("2".equals(desc))
      sqlOrderDisc = " desc"; 
    sqlOrder = String.valueOf(sqlOrder) + sqlOrderDisc;
    StringBuffer where = new StringBuffer("where 1=1 and po.userIsActive=1 and po.userIsDeleted=0 and po.empId<>0");
    if (queryItem != null && queryText != null && !"".equals(queryItem) && !"".equals(queryText)) {
      if (queryItem != null && !"".equals(queryItem.trim()) && !"".equals(queryText.trim())) {
        where.append(" and (");
        if (queryItem.equals("empEmail")) {
          where.append("po.empEmail like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empEmail2 like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empEmail3 like '%")
            .append(queryText)
            .append("%'");
        } else if (queryItem.equals("orgName")) {
          where.append("org.orgIdString like '%$").append(queryText).append("$%'");
        } else if (queryItem.equals("empAddress")) {
          where.append(" po.empCountry like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empState like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empCounty like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.empAddress like '%")
            .append(queryText)
            .append("%'");
          if (queryText != null && queryText.indexOf(".") != -1) {
            String tmpState = queryText.substring(0, queryText.indexOf("."));
            String tmpCounty = queryText.substring(queryText.indexOf(".") + 1, queryText.length());
            if (!"".equals(tmpState))
              where.append(" or ")
                .append(" po.empState like '%")
                .append(tmpState).append("%'"); 
            if (!"".equals(tmpCounty))
              where.append(" or ")
                .append(" po.empCounty like '%")
                .append(tmpCounty).append("%'"); 
          } 
        } else {
          where.append("po.").append(queryItem).append(" like '%").append(queryText).append("%'");
        } 
        where.append(")");
      } else if (queryText != null && !queryText.trim().equals("")) {
        where.append(" and org.orgIdString like '%$").append((request.getParameter("orgtype") == null || request.getParameter("orgtype").trim().equals("") || request.getParameter("orgtype").trim().equals("null")) ? "" : request.getParameter("orgtype")).append("$%'");
        where.append(" and (");
        where.append("po.empEmail like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empEmail2 like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empEmail3 like '%")
          .append(queryText)
          .append("%' or ")
          .append("org.orgNameString like '%")
          .append(queryText)
          .append("%' or ")
          .append("po.empName like '%")
          .append(queryText)
          .append("%' or ")
          .append("po.empBusinessPhone like '%")
          .append(queryText)
          .append("%' or ")
          .append("po.empMobilePhone like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empCountry like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empState like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empCounty like '%")
          .append(queryText)
          .append("%' or ")
          .append(" po.empAddress like '%")
          .append(queryText)
          .append("%'");
        if (queryText != null && queryText.indexOf(".") != -1) {
          String tmpState = queryText.substring(0, queryText.indexOf("."));
          String tmpCounty = queryText.substring(queryText.indexOf(".") + 1, queryText.length());
          if (!"".equals(tmpState))
            where.append(" or ")
              .append(" po.empState like '%")
              .append(tmpState).append("%'"); 
          if (!"".equals(tmpCounty))
            where.append(" or ")
              .append(" po.empCounty like '%")
              .append(tmpCounty).append("%'"); 
        } 
        where.append(")");
      } 
    } else {
      if (empEmail != null && !"null".equals(empEmail) && !"".equals(empEmail))
        where.append(" and (po.empEmail like '%")
          .append(empEmail)
          .append("%' or ")
          .append(" po.empEmail2 like '%")
          .append(empEmail)
          .append("%' or ")
          .append(" po.empEmail3 like '%")
          .append(empEmail)
          .append("%' )"); 
      if (orgName != null && !"null".equals(orgName) && !"".equals(orgName))
        where.append(" and org.orgIdString like '%$").append(orgName).append("$%'"); 
      if (empAddress != null && !"null".equals(empAddress) && !"".equals(empAddress)) {
        where.append(" and (po.empCountry like '%")
          .append(empAddress)
          .append("%' or ")
          .append(" po.empState like '%")
          .append(empAddress)
          .append("%' or ")
          .append(" po.empCounty like '%")
          .append(empAddress)
          .append("%' or ")
          .append(" po.empAddress like '%")
          .append(empAddress)
          .append("%'");
        if (empAddress != null && empAddress.indexOf(".") != -1) {
          String tmpState = empAddress.substring(0, empAddress.indexOf("."));
          String tmpCounty = empAddress.substring(empAddress.indexOf(".") + 1, empAddress.length());
          if (!"".equals(tmpState))
            where.append(" or ")
              .append(" po.empState like '%")
              .append(tmpState).append("%'"); 
          if (!"".equals(tmpCounty))
            where.append(" or ")
              .append(" po.empCounty like '%")
              .append(tmpCounty).append("%'"); 
        } 
        where.append(")");
      } 
      if (empName != null && !"null".equals(empName) && !"".equals(empName))
        where.append(" and po.empName").append(" like '%").append(empName).append("%'"); 
      if (empSex != null && !"null".equals(empSex) && !"".equals(empSex))
        where.append(" and po.empSex").append(" like '%").append(empSex).append("%'"); 
      if (empBusinessPhone != null && !"null".equals(empBusinessPhone) && !"".equals(empBusinessPhone))
        where.append(" and po.empBusinessPhone").append(" like '%").append(empBusinessPhone).append("%'"); 
      if (empMobilePhone != null && !"null".equals(empMobilePhone) && !"".equals(empMobilePhone))
        where.append(" and po.empMobilePhone").append(" like '%").append(empMobilePhone).append("%'"); 
    } 
    PersonInnerBD pbd = new PersonInnerBD();
    where.append(" and po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + session.getAttribute("domainId") + " ");
    ManagerEJBBean meb = new ManagerEJBBean();
    String browserRange = session.getAttribute("browseRange").toString();
    if ("0".equals(SystemCommon.getBrowseInner()))
      browserRange = "*0*"; 
    String corpId = session.getAttribute("corpId").toString();
    String departId = session.getAttribute("departId").toString();
    String domainId = session.getAttribute("domainId").toString();
    SysSetupReader ssReader = SysSetupReader.getInstance();
    String isViewAllLinkman = SysSetupReader.getLinkmanScope(domainId);
    String linkmanViewScopeString = "";
    String linkmanMaintainSceopString = "";
    if ("1".equals(isViewAllLinkman))
      if ("1".equals(SystemCommon.getUseBrowseRange()) && !"*0*".equals(browserRange)) {
        String orgScopeString = "";
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
        where.append(" and org.id in (" + orgScopeString.substring(1, orgScopeString.length() - 1) + ") ");
      } else {
        String wherePara = mbd.getRightFinalWhere(curUserId, session.getAttribute("orgId").toString(), "08*03*01", "org.id", "");
        if (request.getParameter("orgtype") != null && !"".equals(request.getParameter("orgtype").toString())) {
          request.setAttribute("orgtype", request.getParameter("orgtype").toString());
          String orgids = mbd.getAllJuniorOrgIdByRange("*" + request.getParameter("orgtype").toString() + "*");
          if (orgids != null && !orgids.equals("")) {
            where.append(" and (");
            where.append("org.orgId in (").append(orgids);
            where.append(") )");
          } 
          String wherePara1 = mbd.getRightFinalWhere(curUserId, request.getParameter("orgtype").toString(), "08*03*01", "org.id", "");
          where.append(" and " + wherePara1);
        } else {
          String wherePara1 = mbd.getRightFinalWhere(curUserId, session.getAttribute("orgId").toString(), "08*03*01", "org.id", "");
          where.append(" and " + wherePara1);
        } 
      }  
    if ("2".equals(isViewAllLinkman)) {
      corpId = session.getAttribute("corpId").toString();
      ManagerEJBBean mebc = new ManagerEJBBean();
      String orgScopeString = "," + mebc.getAllorg("*" + corpId + "*");
      where.append(" and org.id in (" + orgScopeString.substring(1, orgScopeString.length() - 1) + ") ");
    } 
    String personIdWhere = " ";
    if (!StringUtils.isEmpty(personids))
      personIdWhere = " and po.id in (" + personids + ")"; 
    Page page = new Page(
        " po.empId,po.empName,po.empSex,po.empEmail,po.empEmail2,po.empEmail3,po.empBusinessPhone,po.empMobilePhone,po.createdOrg,po.browseRange,org.orgNameString,po.browseRange,org.orgId,po.empCounty,org.orgIdString,po.empState,po.userAccounts,po.empPhone,po.empMobilePhone,po.userOnline,po.empPosition,po.empDuty", 
        " com.js.system.vo.usermanager.EmployeeVO po join po.organizations org  ", 
        String.valueOf(where.toString()) + personIdWhere + sqlOrder);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    List list1 = pbd.setValidOrgs(Long.valueOf(session.getAttribute("domainId").toString()));
    boolean hasRightScope = mbd.hasRightTypeName(curUserId, "内部联系人", "修改");
    if (hasRightScope) {
      List rightList = mbd.getRightScope(curUserId, "内部联系人", "修改");
      request.setAttribute("rightList", rightList);
    } 
    request.setAttribute("validOrgs", list1);
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,order,desc,queryItem,queryText,color,orgtype,empName,empSex,orgName,empEmail,empBusinessPhone,empMobilePhone,empAddress");
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
    if (isViewAllLinkman.equals("1")) {
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
              String viewCusScopeString = feb.getCustomScope(curUserId, "08*03*01", "0", "1");
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
    } else if (isViewAllLinkman.equals("2")) {
      String corpId = session.getAttribute("corpId").toString();
      ManagerEJBBean meb = new ManagerEJBBean();
      orgScopeString = "," + meb.getAllorg("*" + corpId + "*");
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
}
