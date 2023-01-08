package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.EmpAttendancePO;
import com.js.oa.hr.personnelmanager.service.EmpAttendanceBD;
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

public class EmpAttendanceAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    EmpAttendanceActionForm form = (EmpAttendanceActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    httpServletRequest.setAttribute("rang", getManagerRange(session));
    if ("new".equals(action))
      action = "add"; 
    if ("save".equals(action)) {
      action = "add";
      EmpAttendanceBD bd = new EmpAttendanceBD();
      if (bd.checkExists(new Long(form.getEmpID()), null, String.valueOf(form.getYear()) + form.getMonth())) {
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
      EmpAttendanceBD bd = new EmpAttendanceBD();
      if (bd.checkExists(new Long(form.getEmpID()), form.getId(), 
          String.valueOf(form.getYear()) + form.getMonth())) {
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
      (new EmpAttendanceBD()).delete(new Long(httpServletRequest.getParameter(
              "id")));
      action = "list";
    } 
    if ("batchDel".equals(action)) {
      (new EmpAttendanceBD()).batchDel(httpServletRequest.getParameter("ids"));
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
    String yearMonth = request.getParameter("yearMonth");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*06", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.empName,po.yearMonth,po.planHours,po.realHours,po.delayHours,po.dbrestHours,po.officialHours,po.leaveHours,po.offworkHours,po.offtuneHours,po.uncardHours,po.lateHours,po.descriptions,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpAttendancePO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(yearMonth)) {
      whereSQL = String.valueOf(whereSQL) + " and po.yearMonth like '%" + yearMonth + "%'";
      request.setAttribute("yearMonth", yearMonth);
    } 
    String dataType = SystemCommon.getDatabaseType();
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    if (dataType.indexOf("oracle") > -1) {
      whereSQL = String.valueOf(whereSQL) + " order by po.yearMonth desc";
    } else if (dataType.indexOf("db2") >= 0) {
      whereSQL = String.valueOf(whereSQL) + " order by po.yearMonth desc";
    } else if (dataType.indexOf("sqlserver") >= 0) {
      whereSQL = String.valueOf(whereSQL) + " order by po.yearMonth desc";
    } else {
      whereSQL = String.valueOf(whereSQL) + " order by po.yearMonth desc";
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
    request.setAttribute("pageParameters", "action,empName,yearMonth");
  }
  
  private void export(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String empName = request.getParameter("empName");
    String yearMonth = request.getParameter("yearMonth");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*06", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.empName,po.yearMonth,po.planHours,po.realHours,po.delayHours,po.dbrestHours,po.officialHours,po.leaveHours,po.offworkHours,po.offtuneHours,po.uncardHours,po.lateHours,po.descriptions,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpAttendancePO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(yearMonth)) {
      whereSQL = String.valueOf(whereSQL) + " and po.yearMonth like '%" + yearMonth + "%'";
      request.setAttribute("yearMonth", yearMonth);
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.yearMonth desc";
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
    request.setAttribute("pageParameters", "action,empName,yearMonth");
  }
  
  private void listCard(HttpServletRequest request) {
    String viewSQL = 
      "po.id,po.empName,po.yearMonth,po.planHours,po.realHours,po.delayHours,po.dbrestHours,po.officialHours,po.leaveHours,po.offworkHours,po.offtuneHours,po.uncardHours,po.lateHours,po.descriptions,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpAttendancePO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    whereSQL = String.valueOf(whereSQL) + " and ppo.empId = " + request.getParameter("empID");
    whereSQL = String.valueOf(whereSQL) + " order by po.yearMonth desc";
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
  
  private boolean save(EmpAttendanceActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EmpAttendancePO po = new EmpAttendancePO();
    EmployeeVO emp = new EmployeeVO();
    emp.setEmpId(Long.parseLong(form.getEmpID()));
    po.setEmp(emp);
    UserBD ubd = new UserBD();
    List<Object[]> list = ubd.getUserInfo(new Long(form.getEmpID()));
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      po.setEmpOrg((obj[8] != null) ? new Long(obj[8].toString()) : null);
    } 
    po.setYearMonth(String.valueOf(form.getYear()) + form.getMonth());
    po.setEmpName(form.getEmpName());
    if (!isEmpty(form.getPlanHours()))
      po.setPlanHours(new Float(form.getPlanHours())); 
    if (!isEmpty(form.getRealHours()))
      po.setRealHours(new Float(form.getRealHours())); 
    if (!isEmpty(form.getDelayHours()))
      po.setDelayHours(new Float(form.getDelayHours())); 
    if (!isEmpty(form.getDbrestHours()))
      po.setDbrestHours(new Float(form.getDbrestHours())); 
    if (!isEmpty(form.getOfficialHours()))
      po.setOfficialHours(new Float(form.getOfficialHours())); 
    if (!isEmpty(form.getLeaveHours()))
      po.setLeaveHours(new Float(form.getLeaveHours())); 
    if (!isEmpty(form.getOffworkHours()))
      po.setOffworkHours(new Float(form.getOffworkHours())); 
    if (!isEmpty(form.getOfftuneHours()))
      po.setOfftuneHours(new Float(form.getOfftuneHours())); 
    if (!isEmpty(form.getUncardHours()))
      po.setUncardHours(new Float(form.getUncardHours())); 
    if (!isEmpty(form.getLateHours()))
      po.setLateHours(new Float(form.getLateHours())); 
    po.setDescriptions(form.getDescriptions());
    retflag = (new EmpAttendanceBD()).save(po).booleanValue();
    return retflag;
  }
  
  private boolean update(EmpAttendanceActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EmpAttendancePO po = new EmpAttendancePO();
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
    po.setYearMonth(String.valueOf(form.getYear()) + form.getMonth());
    po.setEmpName(form.getEmpName());
    if (!isEmpty(form.getPlanHours()))
      po.setPlanHours(new Float(form.getPlanHours())); 
    if (!isEmpty(form.getRealHours()))
      po.setRealHours(new Float(form.getRealHours())); 
    if (!isEmpty(form.getDelayHours()))
      po.setDelayHours(new Float(form.getDelayHours())); 
    if (!isEmpty(form.getDbrestHours()))
      po.setDbrestHours(new Float(form.getDbrestHours())); 
    if (!isEmpty(form.getOfficialHours()))
      po.setOfficialHours(new Float(form.getOfficialHours())); 
    if (!isEmpty(form.getLeaveHours()))
      po.setLeaveHours(new Float(form.getLeaveHours())); 
    if (!isEmpty(form.getOffworkHours()))
      po.setOffworkHours(new Float(form.getOffworkHours())); 
    if (!isEmpty(form.getOfftuneHours()))
      po.setOfftuneHours(new Float(form.getOfftuneHours())); 
    if (!isEmpty(form.getUncardHours()))
      po.setUncardHours(new Float(form.getUncardHours())); 
    if (!isEmpty(form.getLateHours()))
      po.setLateHours(new Float(form.getLateHours())); 
    po.setDescriptions(form.getDescriptions());
    retflag = (new EmpAttendanceBD()).modify(po).booleanValue();
    return retflag;
  }
  
  private void load(EmpAttendanceActionForm form, HttpServletRequest request) {
    EmpAttendancePO po = (new EmpAttendanceBD()).load(new Long(request.getParameter(
            "id")));
    form.setEmpID((new StringBuilder(String.valueOf(po.getEmp().getEmpId()))).toString());
    form.setEmpName(po.getEmp().getEmpName());
    form.setYear(po.getYearMonth().substring(0, 4));
    form.setMonth(po.getYearMonth().substring(4, 6));
    po.setEmpName(form.getEmpName());
    if (!isEmpty(po.getPlanHours()))
      form.setPlanHours(po.getPlanHours().toString()); 
    if (!isEmpty(po.getRealHours()))
      form.setRealHours(po.getRealHours().toString()); 
    if (!isEmpty(po.getDelayHours()))
      form.setDelayHours(po.getDelayHours().toString()); 
    if (!isEmpty(po.getDbrestHours()))
      form.setDbrestHours(po.getDbrestHours().toString()); 
    if (!isEmpty(po.getOfficialHours()))
      form.setOfficialHours(po.getOfficialHours().toString()); 
    if (!isEmpty(po.getLeaveHours()))
      form.setLeaveHours(po.getLeaveHours().toString()); 
    if (!isEmpty(po.getOffworkHours()))
      form.setOffworkHours(po.getOffworkHours().toString()); 
    if (!isEmpty(po.getOfftuneHours()))
      form.setOfftuneHours(po.getOfftuneHours().toString()); 
    if (!isEmpty(po.getUncardHours()))
      form.setUncardHours(po.getUncardHours().toString()); 
    if (!isEmpty(po.getLateHours()))
      form.setLateHours(po.getLateHours().toString()); 
    form.setDescriptions(po.getDescriptions());
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
