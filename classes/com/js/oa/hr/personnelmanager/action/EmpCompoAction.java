package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.EmpCompoPO;
import com.js.oa.hr.personnelmanager.service.EmpCompoBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmpCompoAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    EmpCompoActionForm form = (EmpCompoActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    if ("new".equals(action)) {
      httpServletRequest.setAttribute("rang", getManagerRange(session));
      action = "add";
    } 
    if ("save".equals(action)) {
      save(form, httpServletRequest);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
      action = "add";
    } 
    if ("load".equals(action) || "view".equals(action)) {
      httpServletRequest.setAttribute("rang", getManagerRange(session));
      load(form, httpServletRequest);
      httpServletRequest.setAttribute("action", action);
      action = "modify";
    } 
    if ("update".equals(action)) {
      update(form, httpServletRequest);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
      action = "modify";
    } 
    if ("delete".equals(action)) {
      (new EmpCompoBD()).delete(new Long(httpServletRequest.getParameter("id")));
      action = "list";
    } 
    if ("batchDel".equals(action)) {
      (new EmpCompoBD()).batchDel(httpServletRequest.getParameter("ids"));
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
    String accident = request.getParameter("accident");
    String isDate = request.getParameter("isDate");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*02", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.occurDate,po.accident,po.compensateUnit,po.insuranceCompany,po.compensateMoney,po.appraisalLevel,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpCompoPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(accident)) {
      whereSQL = String.valueOf(whereSQL) + " and po.accident like '%" + accident + "%'";
      request.setAttribute("accident", accident);
    } 
    if (isDate != null && "1".equals(isDate)) {
      String sDate = request.getParameter("start_date");
      String sY = sDate.substring(0, sDate.indexOf("/"));
      int sm = Integer.parseInt(sDate.substring(sDate.indexOf("/") + 1, 
            sDate.lastIndexOf("/")));
      String sM = (sm < 10) ? ("0" + sm) : sm;
      int sd = Integer.parseInt(sDate.substring(sDate.lastIndexOf("/") + 
            1));
      String sD = (sd < 10) ? ("0" + sd) : sd;
      String eDate = request.getParameter("end_date");
      String eY = eDate.substring(0, eDate.indexOf("/"));
      int em = Integer.parseInt(eDate.substring(eDate.indexOf("/") + 1, 
            eDate.lastIndexOf("/")));
      String eM = (em < 10) ? ("0" + em) : em;
      int ed = Integer.parseInt(eDate.substring(eDate.lastIndexOf("/") + 
            1));
      String eD = (ed < 10) ? ("0" + ed) : ed;
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") > -1) {
        whereSQL = String.valueOf(whereSQL) + " and to_char(po.occurDate, 'yyyy-MM-dd')>= '" + 
          sY + "-" + sM + "-" + sD + "'";
        whereSQL = String.valueOf(whereSQL) + " and to_char(po.occurDate, 'yyyy-MM-dd')<= '" + 
          eY + "-" + eM + "-" + eD + "'";
      } else if (databaseType.indexOf("mysql") > -1) {
        whereSQL = String.valueOf(whereSQL) + " and substr(concat(po.occurDate),1,10)>= '" + 
          sY + "-" + sM + "-" + sD + "'";
        whereSQL = String.valueOf(whereSQL) + " and substr(concat(po.occurDate),1,10)<= '" + 
          eY + "-" + eM + "-" + eD + "'";
      } else {
        whereSQL = String.valueOf(whereSQL) + " and convert(char(10),po.occurDate,20) >= '" + 
          sY + "-" + sM + "-" + sD + "'";
        whereSQL = String.valueOf(whereSQL) + " and convert(char(10),po.occurDate,20) <= '" + 
          eY + "-" + eM + "-" + eD + "'";
      } 
      request.setAttribute("start_date", sDate);
      request.setAttribute("end_date", eDate);
      request.setAttribute("isDate", isDate);
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.occurDate desc ";
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
    request.setAttribute("pageParameters", "action,empName,accident,start_date,end_date,isDate");
  }
  
  private void export(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String empName = request.getParameter("empName");
    String accident = request.getParameter("accident");
    String isDate = request.getParameter("isDate");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*02", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.occurDate,po.accident,po.compensateUnit,po.insuranceCompany,po.compensateMoney,po.appraisalLevel,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpCompoPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(accident)) {
      whereSQL = String.valueOf(whereSQL) + " and po.accident like '%" + accident + "%'";
      request.setAttribute("accident", accident);
    } 
    if (isDate != null && "1".equals(isDate)) {
      String sDate = request.getParameter("start_date");
      String sY = sDate.substring(0, sDate.indexOf("/"));
      int sm = Integer.parseInt(sDate.substring(sDate.indexOf("/") + 1, 
            sDate.lastIndexOf("/")));
      String sM = (sm < 10) ? ("0" + sm) : sm;
      int sd = Integer.parseInt(sDate.substring(sDate.lastIndexOf("/") + 
            1));
      String sD = (sd < 10) ? ("0" + sd) : sd;
      String eDate = request.getParameter("end_date");
      String eY = eDate.substring(0, eDate.indexOf("/"));
      int em = Integer.parseInt(eDate.substring(eDate.indexOf("/") + 1, 
            eDate.lastIndexOf("/")));
      String eM = (em < 10) ? ("0" + em) : em;
      int ed = Integer.parseInt(eDate.substring(eDate.lastIndexOf("/") + 
            1));
      String eD = (ed < 10) ? ("0" + ed) : ed;
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") > -1) {
        whereSQL = String.valueOf(whereSQL) + " and to_char(po.occurDate, 'yyyy-MM-dd')>= '" + 
          sY + "-" + sM + "-" + sD + "'";
        whereSQL = String.valueOf(whereSQL) + " and to_char(po.occurDate, 'yyyy-MM-dd')<= '" + 
          eY + "-" + eM + "-" + eD + "'";
      } else if (databaseType.indexOf("mysql") > -1) {
        whereSQL = String.valueOf(whereSQL) + " and substr(concat(po.occurDate),1,10)>= '" + 
          sY + "-" + sM + "-" + sD + "'";
        whereSQL = String.valueOf(whereSQL) + " and substr(concat(po.occurDate),1,10)<= '" + 
          eY + "-" + eM + "-" + eD + "'";
      } else {
        whereSQL = String.valueOf(whereSQL) + " and convert(char(10),po.occurDate,20) >= '" + 
          sY + "-" + sM + "-" + sD + "'";
        whereSQL = String.valueOf(whereSQL) + " and convert(char(10),po.occurDate,20) <= '" + 
          eY + "-" + eM + "-" + eD + "'";
      } 
      request.setAttribute("start_date", sDate);
      request.setAttribute("end_date", eDate);
      request.setAttribute("isDate", isDate);
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.occurDate desc ";
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
    request.setAttribute("pageParameters", "action,empName,accident,start_date,end_date,isDate");
  }
  
  private void listCard(HttpServletRequest request) {
    String viewSQL = 
      "po.id,po.occurDate,po.accident,po.compensateUnit,po.insuranceCompany,po.compensateMoney,po.appraisalLevel,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpCompoPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    whereSQL = String.valueOf(whereSQL) + " and ppo.empId = " + request.getParameter("empID");
    whereSQL = String.valueOf(whereSQL) + " order by po.occurDate desc ";
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
  
  private boolean save(EmpCompoActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EmpCompoPO po = new EmpCompoPO();
    EmployeeVO emp = new EmployeeVO();
    emp.setEmpId(Long.parseLong(form.getEmpID()));
    po.setEmp(emp);
    UserBD ubd = new UserBD();
    List<Object[]> list = ubd.getUserInfo(new Long(form.getEmpID()));
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      po.setEmpOrg((obj[8] != null) ? new Long(obj[8].toString()) : null);
    } 
    po.setOccurDate(new Date(request.getParameter("occurDate")));
    po.setAccident(form.getAccident());
    po.setCompensateUnit(form.getCompensateUnit());
    po.setInsuranceCompany(form.getInsuranceCompany());
    if (form.getCompensateMoney() != null && !"".equals(form.getCompensateMoney()))
      po.setCompensateMoney(new Double(form.getCompensateMoney())); 
    po.setAppraisalLevel(form.getAppraisalLevel());
    retflag = (new EmpCompoBD()).save(po).booleanValue();
    return retflag;
  }
  
  private boolean update(EmpCompoActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EmpCompoPO po = new EmpCompoPO();
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
    po.setOccurDate(new Date(request.getParameter("occurDate")));
    po.setAccident(form.getAccident());
    po.setCompensateUnit(form.getCompensateUnit());
    po.setInsuranceCompany(form.getInsuranceCompany());
    if (form.getCompensateMoney() != null && !"".equals(form.getCompensateMoney()))
      po.setCompensateMoney(new Double(form.getCompensateMoney())); 
    po.setAppraisalLevel(form.getAppraisalLevel());
    retflag = (new EmpCompoBD()).modify(po).booleanValue();
    return retflag;
  }
  
  private void load(EmpCompoActionForm form, HttpServletRequest request) {
    EmpCompoPO po = (new EmpCompoBD()).load(new Long(request.getParameter("id")));
    form.setEmpID((new StringBuilder(String.valueOf(po.getEmp().getEmpId()))).toString());
    form.setEmpName(po.getEmp().getEmpName());
    form.setAccident(po.getAccident());
    form.setCompensateUnit(po.getCompensateUnit());
    form.setInsuranceCompany(po.getInsuranceCompany());
    if (po.getCompensateMoney() != null)
      form.setCompensateMoney((String)po.getCompensateMoney()); 
    form.setAppraisalLevel(po.getAppraisalLevel());
    request.setAttribute("occurDate", po.getOccurDate());
  }
  
  private String getManagerRange(HttpSession session) {
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    List<Object[]> rightlist = (new ManagerService()).getRightScope(session
        .getAttribute("userId").toString(), "07*55*02");
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
