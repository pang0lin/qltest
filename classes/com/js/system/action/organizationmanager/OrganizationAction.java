package com.js.system.action.organizationmanager;

import com.active.e_uc.user.po.TblDepartment;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import com.js.util.util.ReadActiveXml;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OrganizationAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    String returns = httpServletRequest.getParameter("returns");
    String domainId = session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    OrganizationForm organizationForm = (OrganizationForm)actionForm;
    OrganizationBD organizationBD = new OrganizationBD();
    if ("checkSerial".equals(action)) {
      String orgParentOrgId = httpServletRequest.getParameter("orgParentOrgId");
      String orgId = httpServletRequest.getParameter("orgId");
      boolean b = (new OrganizationBD()).isSubOrg(orgParentOrgId, orgId);
      Integer serial = Integer.valueOf(0);
      Integer name = Integer.valueOf(0);
      String rename = "_0_";
      if (!b) {
        String orgSerial = URLDecoder.decode(httpServletRequest.getParameter("orgSerial"), "utf-8");
        String orgName = URLDecoder.decode(httpServletRequest.getParameter("orgName"), "utf-8");
        if (orgParentOrgId == null) {
          serial = organizationBD.checkOrganizationSerial(orgId, orgSerial, "true");
        } else {
          serial = organizationBD.checkOrganizationSerial(orgId, orgSerial, "false");
        } 
        name = organizationBD.checkOrganizationName(orgId, orgName);
        if (serial.intValue() == 1)
          rename = StaticParam.getorgNameBySerial(orgSerial); 
      } else {
        serial = Integer.valueOf(12);
      } 
      StringBuffer xml = new StringBuffer(1024);
      httpServletResponse.setContentType("text/xml;charset=GBK");
      PrintWriter out = httpServletResponse.getWriter();
      xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
      xml.append("<result>\n");
      xml.append("  <serial>" + serial + "</serial>\n");
      xml.append("  <name>" + name + "</name>\n");
      xml.append("  <rename>" + rename + "</rename>\n");
      xml.append("</result>\n");
      out.print(xml.toString());
      out.close();
      return null;
    } 
    if ("getOrgDetail".equals(action)) {
      String orgId = httpServletRequest.getParameter("orgId");
      String type = httpServletRequest.getParameter("type");
      String range = httpServletRequest.getParameter("range");
      OrganizationVO vo = null;
      if (orgId != null && !"null".equals(orgId) && !"".equals(orgId)) {
        if (orgId.equals("0"))
          orgId = StaticParam.getRootDeptId(); 
        vo = organizationBD.getOrgByOrgId(orgId);
        BeanUtils.copyProperties(organizationForm, vo);
      } 
      String id = String.valueOf(organizationForm.getOrgParentOrgId());
      List<OrganizationVO> list = organizationBD.getParentOrgListTemp(id, domainId, range);
      if (vo != null) {
        httpServletRequest.setAttribute("codeTemp", Integer.valueOf(vo.getOrgOrderCode()));
        httpServletRequest.setAttribute("orgIdTemp", vo.getOrgId().toString());
      } 
      httpServletRequest.setAttribute("orgList", list);
      int selectedValue = 0;
      if (list.size() > 0)
        selectedValue = ((OrganizationVO)list.get(list.size() - 1)).getOrgOrderCode(); 
      httpServletRequest.setAttribute("selectedValue", Integer.valueOf(selectedValue));
      httpServletRequest.setAttribute("parentIdString", StaticParam.getDepOrgIdString(orgId));
      httpServletRequest.setAttribute("range", httpServletRequest.getParameter("range"));
      httpServletRequest.setAttribute("type", type);
      return actionMapping.findForward("orgDetail");
    } 
    if ("mod".equals(action)) {
      OrganizationVO vo = null;
      String orgId = httpServletRequest.getParameter("orgId");
      String range = httpServletRequest.getParameter("range");
      if (orgId != null && !"null".equals(orgId) && !"".equals(orgId)) {
        vo = organizationBD.getOrgByOrgId(orgId);
        BeanUtils.copyProperties(organizationForm, vo);
      } 
      String id = String.valueOf(organizationForm.getOrgParentOrgId());
      List list = organizationBD.getParentOrgListTemp(id, domainId, range);
      httpServletRequest.setAttribute("range", httpServletRequest.getParameter("range"));
      httpServletRequest.setAttribute("orgList", list);
      httpServletRequest.setAttribute("codeTemp", Integer.valueOf(vo.getOrgOrderCode()));
      return actionMapping.findForward("mod");
    } 
    int orgChannelStyle = organizationForm.getOrgChannelStyle();
    int orgHasChannel = organizationForm.getOrgHasChannel();
    if (orgHasChannel == 1 && orgChannelStyle > 1)
      organizationForm.setOrgHasChannel(orgChannelStyle); 
    TblDepartment tblDepartment = new TblDepartment();
    TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
    if ("add".equals(action)) {
      OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOneToOne(organizationForm, OrganizationVO.class);
      String currentOrderCode = organizationForm.getCurrentOrderCode();
      String parentIdString = organizationForm.getParentIdString();
      Integer sort = new Integer(organizationForm.getOrgSort());
      organizationVO.setDomainId(session.getAttribute("domainId").toString());
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          int b = (int)organizationVO.getOrgParentOrgId();
          if (b != 0) {
            String sa = organizationBD.findOrgSerial(b);
            tblDepartment.setPid(tblDepartmentBD.findID(sa));
          } else {
            tblDepartment.setPid(0);
          } 
          tblDepartment.setName(organizationVO.getOrgName());
          tblDepartment.setOrgid(1);
          tblDepartment.setGrade(0);
          tblDepartment.setUrl(organizationVO.getOrgSerial());
          tblDepartment.setShowChildUser((byte)1);
          try {
            tblDepartmentBD.addDepartment(tblDepartment);
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
        } 
        boolean flag = false;
        if (organizationVO.getOrgId() == null || organizationVO.getOrgId().equals("")) {
          flag = organizationBD.add(organizationVO, currentOrderCode, parentIdString, sort);
        } else {
          organizationVO.setOrgParentOrgId(organizationVO.getOrgId().longValue());
          flag = organizationBD.add(organizationVO, currentOrderCode, parentIdString, sort);
        } 
        updateLeader(organizationVO.getOrgManagerEmpId());
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
          if (organizationVO.getOrgId() == null || organizationVO.getOrgId().equals("")) {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "3", "autoAudit" };
            flag = organizationBD.add(organizationVO, currentOrderCode, parentIdString, sort, log);
          } else {
            organizationVO.setOrgParentOrgId(organizationVO.getOrgId().longValue());
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "3", "autoAudit" };
            flag = organizationBD.add(organizationVO, currentOrderCode, parentIdString, sort, log);
          }  
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        boolean flag = false;
        if (organizationVO.getOrgId() == null || organizationVO.getOrgId().equals("")) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3" };
          flag = organizationBD.add(organizationVO, currentOrderCode, parentIdString, sort, log);
        } else {
          organizationVO.setOrgParentOrgId(organizationVO.getOrgId().longValue());
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3" };
          flag = organizationBD.add(organizationVO, currentOrderCode, parentIdString, sort, log);
        } 
      } 
      httpServletRequest.setAttribute("range", httpServletRequest.getParameter("range"));
      httpServletRequest.setAttribute("orgId", httpServletRequest.getParameter("orgId"));
      httpServletRequest.setAttribute("orgName", httpServletRequest.getParameter("oldParnetName"));
      if (SystemCommon.getAudit() == 0) {
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_org", "系统管理", date, date, "1", organizationVO.getOrgName(), session.getAttribute("userIP").toString(), domainId);
      } 
      action = "proAdd";
    } 
    if ("proAdd".equals(action)) {
      String type = httpServletRequest.getParameter("type");
      String orgId = httpServletRequest.getParameter("orgId");
      String range = httpServletRequest.getParameter("range");
      List<OrganizationVO> list = organizationBD.getParentOrgListTemp(orgId, domainId, range);
      httpServletRequest.setAttribute("range", httpServletRequest.getParameter("range"));
      httpServletRequest.setAttribute("orgList", list);
      httpServletRequest.setAttribute("type", type);
      int selectedValue = 0;
      if (list.size() > 0)
        selectedValue = ((OrganizationVO)list.get(list.size() - 1)).getOrgOrderCode(); 
      httpServletRequest.setAttribute("selectedValue", Integer.valueOf(selectedValue));
      httpServletRequest.setAttribute("orgNameTemp", httpServletRequest.getParameter("oldParnetName"));
      httpServletRequest.setAttribute("parentIdString", StaticParam.getDepOrgIdString(orgId));
      return actionMapping.findForward("add");
    } 
    if ("stopDep".equals(action)) {
      String name = "";
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          String sa = organizationBD.findOrgSerial(organizationForm.getOrgId());
          tblDepartmentBD.delDepartment(sa);
        } 
        name = organizationBD.delete(organizationForm.getOrgId(), "0");
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_org", "系统管理", date, date, "3", name, session.getAttribute("userIP").toString(), domainId);
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3", "autoAudit" };
          name = organizationBD.delete(organizationForm.getOrgId(), "0", log);
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "3" };
        name = organizationBD.delete(organizationForm.getOrgId(), "0", log);
      } 
      action = "view";
    } else if ("delete".equals(action)) {
      String orgId = httpServletRequest.getParameter("orgId");
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3", "autoAudit" };
          organizationBD.delete(organizationForm.getOrgId(), "1", log);
        } 
        organizationBD.delete(Long.parseLong(orgId), "1");
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "3" };
        organizationBD.delete(organizationForm.getOrgId(), "1", log);
      } 
      action = "view";
    } else if ("reDept".equals(action)) {
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3", "autoAudit" };
          organizationBD.reDept(organizationForm.getOrgId(), log);
        } 
        String name = organizationBD.reDept(organizationForm.getOrgId());
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_org", "系统管理", date, date, "3", name, session.getAttribute("userIP").toString(), domainId);
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "3" };
        String str = organizationBD.reDept(organizationForm.getOrgId(), log);
      } 
      action = "view";
    } else if ("updateRootDept".equals(action)) {
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOneToOne(organizationForm, OrganizationVO.class);
        organizationBD.updateRootDept(organizationVO);
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3", "autoAudit" };
          organizationBD.updateRootDept(organizationVO, log);
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOneToOne(organizationForm, OrganizationVO.class);
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "3" };
        organizationBD.updateRootDept(organizationVO, log);
      } 
      action = "view";
    } else if ("update".equals(action)) {
      OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOneToOne(organizationForm, OrganizationVO.class);
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          String sa = organizationBD.findOrgSerial(organizationVO.getOrgId().longValue());
          try {
            tblDepartment = tblDepartmentBD.findTblDepartment(sa);
            int b = (int)organizationVO.getOrgParentOrgId();
            if (b != 0) {
              String sa2 = organizationBD.findOrgSerial(b);
              tblDepartment.setPid(tblDepartmentBD.findID(sa2));
            } else {
              tblDepartment.setPid(0);
            } 
            tblDepartment.setName(organizationVO.getOrgName());
            tblDepartment.setOrgid(1);
            tblDepartment.setGrade(0);
            tblDepartment.setUrl(organizationVO.getOrgSerial());
            tblDepartment.setShowChildUser((byte)1);
            tblDepartmentBD.updateDepartment(tblDepartment);
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
        } 
        String currentOrderCode = organizationForm.getCurrentOrderCode();
        String parentIdString = organizationForm.getParentIdString();
        Integer sort = new Integer(organizationForm.getOrgSort());
        String hasChanged = httpServletRequest.getParameter("hasChanged");
        organizationBD.update(organizationVO, currentOrderCode, parentIdString, sort, hasChanged);
        updateLeader(organizationVO.getOrgManagerEmpId());
        organizationForm.setOrgName("");
        organizationForm.setOrgDescripte("");
        action = "view";
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_org", "系统管理", date, date, "2", organizationVO.getOrgName(), session.getAttribute("userIP").toString(), domainId);
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          currentOrderCode = organizationForm.getCurrentOrderCode();
          parentIdString = organizationForm.getParentIdString();
          sort = new Integer(organizationForm.getOrgSort());
          hasChanged = httpServletRequest.getParameter("hasChanged");
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "3", "autoAudit" };
          organizationBD.update(organizationVO, currentOrderCode, parentIdString, sort, hasChanged, log);
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String currentOrderCode = organizationForm.getCurrentOrderCode();
        String parentIdString = organizationForm.getParentIdString();
        Integer sort = new Integer(organizationForm.getOrgSort());
        String hasChanged = httpServletRequest.getParameter("hasChanged");
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "3" };
        organizationBD.update(organizationVO, currentOrderCode, parentIdString, sort, hasChanged, log);
      } 
      action = "view";
    } else if ("departmentView".equals(action)) {
      String tag = "departmentView";
      departmentView(httpServletRequest);
      return actionMapping.findForward(tag);
    } 
    if ("view".equals(action)) {
      ManagerService managerBD = new ManagerService();
      String range = "0";
      String rightCode = "00*01*02";
      if ("1".equals(session.getAttribute("sysManager").toString()))
        rightCode = "00*01*01"; 
      if (rightCode.equals("00*01*01")) {
        range = "0";
      } else {
        List<Object[]> list = managerBD.getRightScope(session.getAttribute("userId").toString(), rightCode);
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        if ("0".equals(scopeType)) {
          range = "0";
        } else if (!"1".equals(scopeType)) {
          if ("2".equals(scopeType)) {
            range = "*" + session.getAttribute("orgId") + "*";
          } else if ("3".equals(scopeType)) {
            range = "*" + session.getAttribute("orgId") + "*";
          } else if ("4".equals(scopeType)) {
            range = obj[1].toString();
          } 
        } 
      } 
      httpServletRequest.setAttribute("range", range);
      String tag = "view";
      if (returns != null && returns.equals("1"))
        tag = "tree"; 
      return actionMapping.findForward(tag);
    } 
    return null;
  }
  
  private void updateLeader(String empIds) {
    if (empIds.length() > 0) {
      empIds = empIds.replace("$$", ",").replace("$", "");
      OrganizationBD bd = new OrganizationBD();
      bd.updateLeader(empIds);
    } 
  }
  
  public void departmentView(HttpServletRequest httpServletRequest) {
    String viewSql = "org.orgId,org.orgName,org.orgNameString,org.orgStatus";
    String fromSql = "com.js.system.vo.organizationmanager.OrganizationVO org";
    String whereSql = "where 1=1 ";
    String org_id = "";
    String orgname = "";
    org_id = httpServletRequest.getParameter("departmentNo");
    orgname = httpServletRequest.getParameter("departmentName");
    if (org_id != null && !"null".equals(org_id) && !"".equals(org_id))
      whereSql = String.valueOf(whereSql) + " and org.orgId like '%" + org_id + "%'"; 
    if (orgname != null && !"null".equals(orgname) && !"".equals(orgname))
      whereSql = String.valueOf(whereSql) + " and org.orgName like '%" + orgname + "%'"; 
    whereSql = String.valueOf(whereSql) + " order by org.orgId desc";
    departmentList(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void departmentList(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
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
      httpServletRequest.setAttribute("pager.offset", String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("departmentList", list);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,departmentNo,departmentName");
  }
}
