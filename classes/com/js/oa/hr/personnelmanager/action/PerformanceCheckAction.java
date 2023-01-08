package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.PerformanceCheckPO;
import com.js.oa.hr.personnelmanager.service.PerformanceCheckBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PerformanceCheckAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    PerformanceCheckActionForm form = (PerformanceCheckActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    httpServletRequest.setAttribute("rang", getManagerRange(session));
    if ("new".equals(action))
      action = "add"; 
    if ("save".equals(action)) {
      action = "add";
      PerformanceCheckBD bd = new PerformanceCheckBD();
      if (bd.checkExists(new Long(form.getEmpID()), null, form.getCheckYear(), form.getCheckMonth())) {
        httpServletRequest.setAttribute("isExist", "true");
        return actionMapping.findForward(action);
      } 
      save(form, httpServletRequest);
      httpServletRequest.setAttribute("saveType", 
          httpServletRequest
          .getParameter("saveType"));
    } 
    if ("load".equals(action) || "view".equals(action)) {
      load(form, httpServletRequest);
      httpServletRequest.setAttribute("action", action);
      action = "modify";
    } 
    if ("update".equals(action)) {
      action = "modify";
      PerformanceCheckBD bd = new PerformanceCheckBD();
      if (bd.checkExists(new Long(form.getEmpID()), form.getId(), 
          form.getCheckYear(), form.getCheckMonth())) {
        load(form, httpServletRequest);
        httpServletRequest.setAttribute("action", action);
        httpServletRequest.setAttribute("isExist", "true");
        return actionMapping.findForward(action);
      } 
      update(form, httpServletRequest);
      httpServletRequest.setAttribute("saveType", 
          httpServletRequest
          .getParameter("saveType"));
    } 
    if ("delete".equals(action)) {
      (new PerformanceCheckBD()).delete(new Long(httpServletRequest.getParameter(
              "id")));
      action = "list";
    } 
    if ("batchDel".equals(action)) {
      (new PerformanceCheckBD()).batchDel(httpServletRequest.getParameter("ids"));
      action = "list";
    } 
    if ("list".equals(action))
      list(httpServletRequest); 
    if ("listCard".equals(action))
      listCard(httpServletRequest); 
    if ("export".equals(action))
      export(httpServletRequest); 
    return actionMapping.findForward(action);
  }
  
  private void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String empName = request.getParameter("empName");
    String checkYear = request.getParameter("checkYear");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*01", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.checkYear,po.checkMonth,po.checkMark,po.addMark,po.addReason,po.deductMark,po.deductReason,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.PerformanceCheckPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(checkYear)) {
      whereSQL = String.valueOf(whereSQL) + " and po.checkYear like '%" + checkYear + "%'";
      request.setAttribute("checkYear", checkYear);
    } 
    String dataType = SystemCommon.getDatabaseType();
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    if (dataType.indexOf("oracle") > -1) {
      whereSQL = String.valueOf(whereSQL) + " order by po.checkYear desc, to_number(po.checkMonth) desc ";
    } else if (dataType.indexOf("db2") >= 0) {
      whereSQL = String.valueOf(whereSQL) + " order by po.checkYear desc, cast(po.checkMonth as int) desc ";
    } else if (dataType.indexOf("sqlserver") >= 0) {
      whereSQL = String.valueOf(whereSQL) + " order by po.checkYear desc, convert(int,po.checkMonth) desc ";
    } else {
      whereSQL = String.valueOf(whereSQL) + " order by po.checkYear desc, po.checkMonth desc ";
    } 
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      request.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,empName,checkYear");
  }
  
  private void export(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String empName = request.getParameter("empName");
    String checkYear = request.getParameter("checkYear");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*01", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.checkYear,po.checkMonth,po.checkMark,po.addMark,po.addReason,po.deductMark,po.deductReason,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.PerformanceCheckPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(checkYear)) {
      whereSQL = String.valueOf(whereSQL) + " and po.checkYear like '%" + checkYear + "%'";
      request.setAttribute("checkYear", checkYear);
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.checkYear desc, po.checkMonth desc ";
    int pageSize = 999999;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      request.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,empName,checkYear");
  }
  
  private void listCard(HttpServletRequest request) {
    String viewSQL = 
      "po.id,po.checkYear,po.checkMonth,po.checkMark,po.addMark,po.addReason,po.deductMark,po.deductReason,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.PerformanceCheckPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    whereSQL = String.valueOf(whereSQL) + " and ppo.empId = " + request.getParameter("empID");
    whereSQL = String.valueOf(whereSQL) + " order by po.checkYear desc, po.checkMonth desc ";
    int pageSize = 999999;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(PerformanceCheckActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    PerformanceCheckPO po = new PerformanceCheckPO();
    EmployeeVO emp = new EmployeeVO();
    emp.setEmpId(Long.parseLong(form.getEmpID()));
    po.setEmp(emp);
    UserBD ubd = new UserBD();
    List<Object[]> list = ubd.getUserInfo(new Long(form.getEmpID()));
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      po.setEmpOrg((obj[8] != null) ? new Long(obj[8].toString()) : null);
    } 
    po.setCheckYear(form.getCheckYear());
    po.setCheckMonth(form.getCheckMonth());
    po.setCheckMark(new Float(form.getCheckMark()));
    if (!isEmpty(form.getAddMark()))
      po.setAddMark(new Float(form.getAddMark())); 
    po.setAddReason(form.getAddReason());
    if (!isEmpty(form.getDeductMark()))
      po.setDeductMark(new Float(form.getDeductMark())); 
    po.setDeductReason(form.getDeductReason());
    retflag = (new PerformanceCheckBD()).save(po).booleanValue();
    return retflag;
  }
  
  private boolean update(PerformanceCheckActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    PerformanceCheckPO po = new PerformanceCheckPO();
    po.setId(form.getId());
    EmployeeVO emp = new EmployeeVO();
    emp.setEmpId(Long.parseLong(form.getEmpID()));
    po.setEmp(emp);
    UserBD ubd = new UserBD();
    List<Object[]> list = ubd.getUserInfo(new Long(form.getEmpID()));
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      po.setEmpOrg((obj[8] != null) ? new Long(obj[8].toString()) : null);
    } 
    po.setCheckYear(form.getCheckYear());
    po.setCheckMonth(form.getCheckMonth());
    po.setCheckMark(new Float(form.getCheckMark()));
    if (!isEmpty(form.getAddMark()))
      po.setAddMark(new Float(form.getAddMark())); 
    po.setAddReason(form.getAddReason());
    if (!isEmpty(form.getDeductMark()))
      po.setDeductMark(new Float(form.getDeductMark())); 
    po.setDeductReason(form.getDeductReason());
    retflag = (new PerformanceCheckBD()).modify(po).booleanValue();
    return retflag;
  }
  
  private void load(PerformanceCheckActionForm form, HttpServletRequest request) {
    PerformanceCheckPO po = (new PerformanceCheckBD()).load(new Long(request.getParameter(
            "id")));
    form.setEmpID((new StringBuilder(String.valueOf(po.getEmp().getEmpId()))).toString());
    form.setEmpName(po.getEmp().getEmpName());
    form.setCheckYear(po.getCheckYear());
    form.setCheckMonth(po.getCheckMonth());
    form.setCheckMark((String)po.getCheckMark());
    if (po.getAddMark() != null)
      form.setAddMark((String)po.getAddMark()); 
    form.setAddReason(po.getAddReason());
    if (po.getDeductMark() != null)
      form.setDeductMark((String)po.getDeductMark()); 
    form.setDeductReason(po.getDeductReason());
  }
  
  private String getManagerRange(HttpSession session) {
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    List<Object[]> rightlist = (new ManagerService()).getRightScope(session
        .getAttribute("userId").toString(), "07*55*01");
    if (rightlist == null || rightlist.size() == 0) {
      managerScope = "*AAAA*";
    } else {
      Object[] obj = rightlist.get(
          0);
      String type = obj[0].toString();
      if ("4".equals(type)) {
        if (obj[1] != null)
          managerScope = obj[1].toString(); 
      } else if ("1".equals(type)) {
        managerScope = "*AAAA*";
      } else if ("0".equals(type)) {
        managerScope = "*0*";
      } 
    } 
    return managerScope;
  }
  
  protected boolean isEmpty(Object o) {
    if (o == null)
      return true; 
    String s = null;
    if (!(o instanceof String)) {
      s = o.toString();
    } else {
      s = (String)o;
    } 
    if (s == null || s.trim().length() == 0 || "null".equals(s))
      return true; 
    return false;
  }
}
